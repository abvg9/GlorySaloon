package es.ucm.fdi.iw.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.common.enums.Nacionalidades;
import es.ucm.fdi.iw.common.enums.Temas;
import es.ucm.fdi.iw.common.utils.Utils;
import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.ComentarioForo;
import es.ucm.fdi.iw.model.Item;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;

@Controller
@RequestMapping("user")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private LocalData localData;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EntityManager entityManager;

	@ModelAttribute
	public void addAttributes(Model model, HttpSession session) {
		model.addAttribute("s", "/static");
	}

	/*
	 * ######################################################################
	 * ### 					         LOGIN     						      ###
	 * ######################################################################
	 */

	/**
	 * Operaciones: 1º Crear Cuenta
	 */

	/**
	 * Operacion creadora: Crea un nuevo usuario.
	 * 
	 * @param nombre:
	 *            Nombre del usuario.
	 * @param cont:
	 *            Contrasena del usuario.
	 * @param email:
	 *            Email del usuario.
	 * @param nacion:
	 *            Pais del usuario.
	 * @return Si todo a salido bien, te redirige a login, si no, carga en sesion un
	 *         mensaje de error y te redirige a crearCuenta.
	 */
	@RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(@RequestParam String nombre, @RequestParam String cont,
							  @RequestParam String email, @RequestParam Nacionalidades nacion,
							  HttpSession session) {

		if ("".equals(nombre) || 
			"".equals(cont) || 
			"".equals(email) || 
			"".equals(nacion.toString())) {

			session.setAttribute(Utils.mensaje, 
					"Debes rellenar todos los campos.");
			
		} else if (entityManager.createNamedQuery("noRepes")
								.setParameter("loginParam", nombre)
								.setParameter("emailParam", email)
								.getResultList().isEmpty()) {

			User u = new User();
			u.setLogin(nombre);
			u.setPassword(passwordEncoder.encode(cont));
			u.setRoles("USER");
			u.setDinero(1000);
			u.setEmail(email);
			u.setNacion(nacion);
			u.setListo(false);

			entityManager.persist(u);
			session.setAttribute(Utils.mensaje, "La cuenta se creo correctamente :)");
			log.info(nombre + " creo una cuenta");

		}else {
			session.setAttribute(Utils.mensaje, "Nombre y/o email ya cogidos :(");
		}
		return "login";
	}

	/*
	 * #######################################################################
	 * ###                        PERFIL USUARIO                           ###
	 * #######################################################################
	 */

	/**
	 * Operaciones: 1º Modifcar => login, password, email, nacion, imagen 
	 * 				2º Anadir amigo 
	 * 				3º Ver perfil(tanto el suyo como el de amigos 
	 * 				4º Eliminar cuenta 
	 * 				5º Eliminar amigo 
	 * 				6º Eliminar item 
	 * 				7º Logout
	 */

	/**
	 * Operacion modificadora: Cambia los datos personales del usuario. Solo
	 * modifica aquellos campos que sean rellenados. Se pide la contrasena actual
	 * del usuario por mayor seguridad
	 * 
	 * @param nombre:
	 *            Nuevo nombre del usuario
	 * @param contNueva:
	 *            Nueva contrasena del usuario
	 * @param contActual:
	 *            Nueva contrasena actual del usuario
	 * @param email:
	 *            Nuevo email del usuario
	 * @param nacion:
	 *            Nuevo pais del usuario
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/modificarPerfil", method = RequestMethod.POST)
	@Transactional
	public String modificarPerfil(String nombre,String contNueva,
								  @RequestParam String contActual,
								  String email, Nacionalidades nacion,
								  HttpSession session) {
			
		if("".equals(contActual)) {
			session.setAttribute(Utils.mensaje,"Es obligatorio que rellenes el campo 'Contrasena actual'");
			return "configuracion";
		}
		
		if(!"".equals(nombre) && Utils.usuarioExiste(entityManager, nombre,session,log) != null) {
			session.setAttribute(Utils.mensaje,
					"Nombre ya cogido.");
			return "configuracion";
		}
		
		if(!"".equals(email) && !entityManager.createQuery("from User where email = :email",
		   User.class).setParameter("email", email).getResultList().isEmpty()) {
			
			session.setAttribute(Utils.mensaje,"Email ya cogido.");
			return "configuracion";
		}
		
		User u = Utils.userFromSession(entityManager, session);
		
		if (passwordEncoder.matches(contActual, u.getPassword())) {

			int modificacion = 0;
			
			if(revisaModificacion(nombre, u.getLogin())) { u.setLogin(nombre); modificacion++; }
			
			if(revisaModificacion(email, u.getEmail())) { u.setLogin(nombre); modificacion++; }
			
			if(revisaModificacion(contNueva, u.getPassword())) { u.setPassword(passwordEncoder.encode(contNueva)); modificacion++; }
			
			if(revisaModificacion(nacion.toString(), u.getNacion().toString())) { u.setNacion(nacion); modificacion++; }


			if (modificacion > 0) {
				session.setAttribute(Utils.mensaje,
						(modificacion == 1 ? "Modificacion realizada" : "Modificaciones realizadas")
								+ " correctamente.");

				log.info(u.getLogin() + " modificó parametros de su perfil.");
				entityManager.flush();
				session.setAttribute(Utils.user, u);

				return "configuracion";
			}

			session.setAttribute(Utils.mensaje,
					"Se ha producido un error, debes rellenar al menos uno de los campos y debe/n ser diferentes a los antiguos.");

			return "configuracion";
		}

		session.setAttribute(Utils.mensaje,
				"Contrasena incorrecta");
		session.setAttribute(Utils.imagen, "/user/fotoPerfil/");
		return "configuracion";

	}

	/**
	 * Operacion modificadora: Anade un nuevo amigo a la lista de amigos del
	 * usuario. Revisa si el amigo existe en la BD y si no lo tenia anteriormente.
	 * Esta operacion no anade en la lista del amigo el usuario en cuestion.
	 * 
	 * @param nombreA:
	 *            Nombre del amigo
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/anadirAmigo", method = RequestMethod.POST)
	@Transactional
	public String anadirAmigo(@RequestParam String nombreA, HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);

		if (nombreA.equals(u.getLogin())) {
			session.setAttribute(Utils.mensaje, "No te puedes anadir a ti mismo como amigo.");
			return "perfil";
		}

		User u2 = Utils.usuarioExiste(entityManager, nombreA,session,log);

		if (u2 != null) {
			
			if(!usuarioTieneAmigo(u2, false, u)) {

				u.getAmigos().add(u2);
				session.setAttribute(Utils.mensaje, nombreA + " anadido a amigos.");
				session.setAttribute(Utils.user, u);
			
			} else {
				session.setAttribute(Utils.mensaje, nombreA + " ya esta en tu lista de amigos.");
			}
		}
		
		session.setAttribute(Utils.imagen, "/user/fotoPerfil/");
		return "perfil";
	}

	/**
	 * Operacion observadora: Muestra el perfil de un usuario dado su nombre.
	 * 
	 * @param nombre:
	 *            Nombre del usuario
	 * @return Te redirige al perfil o al perfil del amigo, cargando en el sesion la
	 *         respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/perfilAmigo", method = RequestMethod.GET)
	public String perfilAmigo(@RequestParam long id, HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);			
		User u2 = (User) entityManager.find(User.class, id);
			
		if (usuarioTieneAmigo(u2, false, u)) {

			session.setAttribute(Utils.perfil, u2);
			session.setAttribute(Utils.imagen, "/user/fotoPerfil/");
			return "perfilAmigo";
			
		} else {
			session.setAttribute(Utils.mensaje,
					"Perfil privado, debes anadirlo a amigos para poder verlo.");
		}
			
		
		return "perfil";
	}

	/**
	 * Operacion eliminadora: Elimina la cuenta de el usuario logueado.
	 * 
	 * @return Te redirige al login tras borrar tu cuenta.
	 */
	@RequestMapping(value = "/eliminarCuenta", method = RequestMethod.POST)
	@Transactional
	public String eliminarCuenta(HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		
		Utils.borraUsuario(u, entityManager);
		File f = localData.getFile("user", String.valueOf(u.getId()));
		f.delete();
		log.info(u.getLogin() + " elimino su cuenta.");
		Utils.eliminaVariablesSession(session);

		return "redirect:/login";
	}

	/**
	 * Operacion eliminadora: Elimina un amigo de la lista de amigos del usuario
	 * 
	 * @param id:
	 *        Id del amigo.
	 * @return Te redirige al perfil
	 */
	@RequestMapping(value = "/eliminarAmigo", method = RequestMethod.POST)
	@Transactional
	public String eliminarAmigo(@RequestParam long id, HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		User u2 = (User) entityManager.find(User.class, id);

		if (usuarioTieneAmigo(u2, true, u)) {

			entityManager.flush();
			session.setAttribute(Utils.mensaje, u2.getLogin() + " eliminado.");
			session.setAttribute(Utils.user, u);
		} else {
			session.setAttribute(Utils.mensaje, u2.getLogin() + " no esta en tu lista de amigos.");
		}

		session.setAttribute(Utils.imagen, "/user/fotoPerfil/");
		return "perfil";
	}

	/**
	 * Operacion eliminadora: Elimina un item de la lista de propiedades del
	 * usuario
	 * 
	 * @param id:
	 *         Id del item
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/borrarItem", method = RequestMethod.POST)
	@Transactional
	public String borrarItem(@RequestParam long id, HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		Item i = (Item) entityManager.find(Item.class, id);

		if (usuarioTieneItem(i, true, u)) {

			entityManager.flush();
			session.setAttribute(Utils.user, u);

		} else {
			session.setAttribute(Utils.mensaje, "No tienes ese item");
		}

		session.setAttribute(Utils.imagen, "/user/fotoPerfil/");

		return "perfil";
	}

	/**
	 * Operacion modificadora: Elimina todas las varaibales de sesion y saca elimina
	 * los datos de usuario cargados
	 * 
	 * @return Te redirige al login
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {

		Utils.eliminaVariablesSession(session);
		return "login";
	}

	/*
	 * #######################################################################
	 * ###                          SALOON                                 ###
	 * #######################################################################
	 */

	/**
	 * Operaciones: 1º Crear partida 
	 * 				2º Unirse a una partida 
	 *				3º Ver y buscar partidas(por juego,por amigos o por nombre) 
	 * 				4º Salir de la partida 5º Salir del juego
	 */

	/**
	 * Operacion creadora: Crea una nueva partida
	 * 
	 * @param juego:
	 *            Tipo de juego al que se va a jugar en la partida.
	 * @param maxJugadores:
	 *            Maximo numero de jugadores.
	 * @param cont:
	 *            Contrasena de la partida.
	 * @param nombrePar:
	 *            Nombre de la partida.
	 * @return Info de la transacción y redireccion al saloon.
	 */
	@RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
	@Transactional
	public String crearPartida(String cont, HttpSession session, 
							   @RequestParam Juegos juego,
							   @RequestParam int maxJugadores, 
							   @RequestParam String nombrePar) {

		User u = Utils.userFromSession(entityManager, session);

		if (u.getPartida() == null) {

			if (maxJugadores < 2 || maxJugadores > 7) {
				session.setAttribute(Utils.mensaje,
						"Las partidas deben ser de como minimo 2 jugadores y como maximo 7.");
				return "saloon";
			}

			if (entityManager.createNamedQuery("getPartidaPorNombre").setParameter("nombreParam", nombrePar)
					.getResultList().isEmpty()) {
				Partida p = new Partida();
				p.setJugadores(new ArrayList<>());
				p.setNombre(nombrePar);
				p.setMaxJugadores(maxJugadores);
				p.setJuego(juego);
				if ("".equals(cont)) {
					p.setPass("no");
				} else {
					p.setPass(passwordEncoder.encode(cont));
				}
				p.setAbierta(true);

				u.setListo(false);
				u.setPartida(p);
				p.getJugadores().add(u);
				entityManager.persist(p);
				entityManager.flush();
				session.setAttribute(Utils.user, u);
				session.setAttribute(Utils.mensaje, "Estas dentro de la partida");

			} else {
				session.setAttribute(Utils.mensaje, "Ese nombre de partida ya esta cogido :(");
			}

		} else {
			session.setAttribute(Utils.mensaje, "Ya estas dentro de una partida.");
		}

		return "saloon";
	}

	/**
	 * Operacion modificadora: Un usuario se une a una partida ya existente.
	 * 
	 * @param id_p:
	 *            Id de la partida.
	 * @param pass:
	 *            Contraseña de la partida.
	 * @return Info de si ha habido algun problema al unirse a la partida o
	 *         redireccion a la partida.
	 */
	@RequestMapping(value = "/unirsePartida", method = RequestMethod.POST)
	@Transactional
	public String unirsePartida(@RequestParam long id_p, String pass, HttpSession session) {

		Partida p = entityManager.find(Partida.class, id_p);
		User u = Utils.userFromSession(entityManager, session);

		if (p != null) {

			if (u.getPartida() == null) {

				if (p.getMaxJugadores() > p.getJugadores().size() && p.isAbierta()) {

					if (pass == null && "no".equals(p.getPass()) || passwordEncoder.matches(pass, p.getPass())) {

						String darDin = "";
						if (u.getDinero() <= 0) {
							u.setDinero(100);
							darDin = " No tenias dinero, te hemos dado 100 monedas para que juegues :).";
						}
						u.setPartida(p);
						p.getJugadores().add(u);
						entityManager.flush();
						session.setAttribute(Utils.user, u);
						session.setAttribute(Utils.mensaje, "Estas dentro de la partida." + darDin);
					} else {
						session.setAttribute(Utils.mensaje, "Contrasena incorrecta.");
					}

				} else {
					session.setAttribute(Utils.mensaje, "No hay hueco en la partida.");
				}
			} else {
				session.setAttribute(Utils.mensaje, "Ya estas dentro de una partida.");
			}
		} else {
			session.setAttribute(Utils.mensaje, "Esa partida ya no existe.");
		}
		session.setAttribute(Utils.saloon, null);
		return "saloon";
	}

	/**
	 * Operacion obervadora: Busca y muestra las partidas en funcion de el nombre
	 * del tipo de juego o del nombre de la paretida o de los amigos.
	 * 
	 * @param juego:
	 *            Tipo de juego al que se va a jugar en la partida.
	 * @param nombrePar:
	 *            Nombre de la partida.
	 * @return Info de si se ha creado correctamente la partida o no.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verSaloon", method = RequestMethod.GET)
	public String verPartidas(HttpSession session, Juegos juego, String nombrePar, Model model,
			HttpServletRequest request) {

		session.setAttribute(Utils.mensaje, null);
		session.setAttribute(Utils.saloon, null);
		List<Partida> partidas = new ArrayList<Partida>();
		User u = Utils.userFromSession(entityManager, session);

		if (!"".equals(nombrePar)) {

			partidas = (List<Partida>) entityManager.createNamedQuery("getPartidaPorNombre")
					.setParameter("nombreParam", nombrePar).getResultList();

			if (!partidas.isEmpty()) {
				session.setAttribute(Utils.saloon, partidas);
			} else {
				session.setAttribute(Utils.mensaje, "Esa partida no existe.");
			}

			Utils.socket(model, request, "user/verSaloon", Utils.chatSocket);
			return "saloon";

		} else if (juego != Juegos.Amigos) {
			partidas = (List<Partida>) entityManager.createNamedQuery("getPartidaPorJuego")
					.setParameter("juegoParam", juego).getResultList();
			session.setAttribute(Utils.saloon, partidas);
			Utils.socket(model, request, "user/verSaloon", Utils.chatSocket);
			return "saloon";
		}

		for (User u2 : u.getAmigos()) {
			partidas.add(u2.getPartida());
		}

		session.setAttribute(Utils.saloon, partidas);
		Utils.socket(model, request, "user/verSaloon", Utils.chatSocket);
		return "saloon";
	}

	/**
	 * Operacion modicadora: Se coprueba si todos la partida esta lista
	 * para comenzar.
	 * 
	 * @return Info de la transacción y redirección a saloon si
	 * no esta lista o la partida en caso contrario.
	 */
	@RequestMapping(value = "/empezarPartida", method = RequestMethod.POST)
	@Transactional
	public String empezarPartida(HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		Partida p = entityManager.find(Partida.class, u.getPartida().getId());

		if (!u.isListo()) {
			u.setListo(true);
		}

		int iJugadores = 0;
		while (iJugadores <  p.getJugadores().size() && 
			   p.getJugadores().get(iJugadores).isListo()) {
			iJugadores++;
		}

		if (iJugadores == p.getMaxJugadores()) {

			p.setAbierta(false);
			u.setPartida(p);
			entityManager.flush();

		} else {
			String jugadores = "";

			for (User u2 : p.getJugadores()) {
				jugadores += u2.getLogin();
				jugadores += (u2.isListo() == true ? " esta listo." : " no esta listo.");
			}

			session.setAttribute(Utils.mensaje, 
					"Faltan jugadores para estar completos (" + 
					(p.getMaxJugadores()-iJugadores) + "/" + 
					p.getMaxJugadores() + ")." + jugadores);

			session.setAttribute(Utils.user, u);
			return "saloon";
		}

		session.setAttribute(Utils.user, u);
		return "redirect:/partidaBlackJack";
	}

	/**
	 * Operacion modicadora: Usuario sale de la partida antes de que comienze la partida.
	 * Si la partida tiene menos de dos jugadores, esta se cerrara y sacara al otro usuario.
	 * 
	 * @return Redirige a sallon
	 */
	@RequestMapping(value = "/salirDeLaPartida", method = RequestMethod.POST)
	@Transactional
	public String salirDeLaPartida(HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		Partida p = entityManager.find(Partida.class, u.getPartida().getId());

		elminaJugador(u);
		u.setPartida(null);

		if (p.getJugadores().size() <= 0) {
			entityManager.remove(p);
		}
		session.setAttribute(Utils.user, u);
		session.setAttribute(Utils.mensaje, "Saliste de la partida");

		return "redirect:/saloon";
	}

	/**
	 * Operacion modicadora: Usuario sale de la partida ya empezada.
	 * 
	 * @param dineroFinal:
	 *            Dinero que tiene el usuario después de 
	 *            haber jugado la partida.
	 * @return Void.
	 */
	@RequestMapping(value = "/salirDelJuego", method = RequestMethod.POST)
	@Transactional
	public String salirDelJuego(HttpSession session, @RequestParam String dineroFinal) {

		int dinero = Integer.valueOf(dineroFinal);
		User u = Utils.userFromSession(entityManager, session);

		Partida p = (Partida) entityManager.createNamedQuery("getPartidaPorNombre")
				.setParameter("nombreParam", u.getPartida().getNombre()).getSingleResult();
		u.setPartida(p);
		int ganado = dinero - u.getDinero();

		if (ganado != 0) {

			if (ganado > 0) {
				u.setPganadas(u.getPganadas() + 1);
				u.setDganado(ganado);
			} else {
				u.setPperdidas(u.getPperdidas() + 1);
				u.setDperdido(Math.abs(ganado));
			}
		}

		log.info(u.getLogin() + " salió de la partida." + u.getPartida().getNombre());
		p.getJugadores().remove(u);

		if (u.getPartida().getJugadores().size() == 0) {
			entityManager.remove(p);
		}

		u.setPartida(null);
		u.setPjugadas(u.getPjugadas() + 1);
		u.setDinero(dinero);

		session.setAttribute(Utils.user, u);
		session.setAttribute(Utils.saloon, null);

		return "redirect:/saloon";
	}

	
	/*
	 * #######################################################################
	 * ###                           TIENDA                                ###
	 * #######################################################################
	 */
	
	/**
	 * Operaciones:
	 * 
	 * 1º Comprar item
	 */
	
	/**
	 * Operacion modicadora: Usuario compra item
	 * 
	 * @param id_it:
	 *            Id del item que quiere comprar
	 * @return Mensaje de error o redireccion en caso contrario
	 */	
	@RequestMapping(value = "/comprarItem", method = RequestMethod.POST)
	@Transactional
	public String comprarItem(@RequestParam long id_it, HttpSession session) {

		try {
			Item i = entityManager.find(Item.class, id_it);
			User u = Utils.userFromSession(entityManager, session);
			
			if (!usuarioTieneItem(i, false, u)) {

				if (u.getDinero() >= i.getPrecio()) {

					u.setDinero(u.getDinero() - i.getPrecio());
					u.getPropiedades().add(i);
					i.getPropietarios().add(u);
					session.setAttribute(Utils.user, u);
					session.setAttribute(Utils.mensaje, "Compra realizada :)");

				} else {
					session.setAttribute(Utils.mensaje, "No tienes suficiente dinero.");
				}
			} else {
				session.setAttribute(Utils.mensaje, "Ya tienes ese item.");
			}

		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(Utils.mensaje, "El item no existe");
		}
		Utils.tienda(session, entityManager);
		return "tienda";
	}

	
	/*
	 * #######################################################################
	 * ###                           FORO                                  ###
	 * #######################################################################
	 */
	
	/**
	 * Operaciones:
	 * 
	 * 1º Comentar en foro 2º Borrar comentario
	 */
	
	/**
	 * Operacion creadora: Usuario comenta en el foro en un tema en concreto
	 * 
	 * @param comentario:
	 *            Mensaje del comentario
	 * @param tema:
	 *            Tema del que comentara el usuario
	 * @return Redirrecion al foro.
	 */
	@RequestMapping(value = "/comentar", method = RequestMethod.POST)
	@Transactional
	public String Comentar(@RequestParam String comentario, @RequestParam Temas tema,
						   HttpSession session) {

		ComentarioForo c = new ComentarioForo();
		User u = Utils.userFromSession(entityManager, session);

		c.setComentario(comentario);
		c.setTema(tema);
		c.setUsuario(u);
		c.setFecha(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		entityManager.persist(c);
		u.getComentarios().add(c);
		
		session.setAttribute(Utils.user, u);
		Utils.foro(session, entityManager, tema);
		session.setAttribute(Utils.tema, tema);
		
		return "foro";
	}

	/**
	 * Operacion eliminadora: Usuario borra un comentario.
	 * 
	 * @param id_c:
	 *            Id del comentario
	 * @return Te redirige al foro.
	 */
	@RequestMapping(value = "/borrarComentario", method = RequestMethod.POST)
	@Transactional
	public String borrarComentario(@RequestParam long id_c, HttpSession session) {

		ComentarioForo c = entityManager.find(ComentarioForo.class, id_c);
		User u = Utils.userFromSession(entityManager, session);
		
		if(u.getLogin().equals(c.getUsuario().getLogin())) {
			u.getComentarios().remove(c);
		}else {
			u = c.getUsuario();
			u.getComentarios().remove(c);
		}
		entityManager.remove(c);
		Utils.foro(session, entityManager, c.getTema());
		session.setAttribute(Utils.tema, c.getTema());

		return "foro";
	}

	/**
	 * Operacion observadora: Muestra el foro en funcion del tema.
	 * 
	 * @param tema:
	 *            Tema de conversacion del foro
	 * @return Te redirige al foro.
	 */
	@RequestMapping(value = "/foro", method = RequestMethod.GET)
	public String foro(Temas tema, HttpSession session) {

		session.setAttribute(Utils.mensaje, null);
		Utils.foro(session, entityManager, tema);	
		session.setAttribute(Utils.tema, tema);
		return "foro";

	}
	
	/*
	 * #######################################################################
	 * ### 						   RANKING 								   ###
	 * #######################################################################
	 */
	
	/**
	 * Operaciones: 1º Ver ranking por amigos.
	 * 				2º Por pais.
	 * 				3º Por global.
	 */
	
	/**
	 * Operacion observadora: Muestra el ranking en funcion de la busqueda
	 * seleccionada.
	 * 
	 * @param busqueda:
	 *            Opción de busqueda.
	 * @return Te redirige al ranking.
	 */
	@RequestMapping(value = "/verRanking", method = RequestMethod.GET)
	public String verRanking(String busqueda, HttpSession session) {

		switch (busqueda) {
		case "amigos":
			verRankingAmigos(session);
			break;
		case "global":
			verRankingGlobal(session);
			break;
		case "pais":
			verRankingPais(session);
			break;
		}

		session.setAttribute(Utils.imagen, "/user/fotoPerfil/");
		return "ranking";
	}
	
	/*
	 * #######################################################################
	 * ### 					        IMAGENES                               ###
	 * #######################################################################
	 */
	
	/**
	 * Operacion observadora: Descarga una foto de la bd
	 * 	
	 * @param id:
	 *            Identificador del usuario al que le pertenece la fotp
	 * @return la imagen o error.
	 */
	@RequestMapping(value = "/fotoPerfil/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public void fotoUsuario(@PathVariable("id") String id, HttpServletResponse response) {

		File f = localData.getFile("user", id);
		try (InputStream in = f.exists() ? new BufferedInputStream(new FileInputStream(f))
				: new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("unknown-user.jpg"))) {
			FileCopyUtils.copy(in, response.getOutputStream());
		} catch (IOException ioe) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			log.info("Se ha producido un error inesperado relacionado con la foto de perfil");
		}
	}

	/**
	 * Opracion observadora: Carga una imagen de usuario via Ajax en la bd
	 * 
	 * @param id:
	 *            Id del usuario
	 * @param photo:
	 *            Foto a cargar
	 * @return Te redirige al perfil cargando mensaje de log en la sesion
	 */
	@RequestMapping(value = "/fotoPerfil/{id}", method = RequestMethod.POST)
	public String cargarArchivo(@RequestParam("photo") MultipartFile photo, @PathVariable("id") String id,
			HttpSession session) {

		if (photo.isEmpty()) {
			session.setAttribute(Utils.mensaje, "No has elegido ninguna foto a subir.");
		} else {
			File f = localData.getFile("user", id);
			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f))) {
				stream.write(photo.getBytes());
				session.setAttribute(Utils.mensaje, "Foto subida correctamente :)");
			} catch (Exception e) {
				session.setAttribute(Utils.mensaje, "Error al subir la foto :(");
			}
		}

		return "perfil";
	}
	
	/*
	 * #######################################################################
	 * ### 					       AUXILIARES                              ###
	 * #######################################################################
	 */

	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void verRankingPais(HttpSession session) {

		User u = Utils.userFromSession(entityManager, session);
		List<User> usuarios = (List<User>) entityManager.createNamedQuery("getUsuarioNacion")
				.setParameter("nacionParam", u.getNacion()).getResultList();

		session.setAttribute("ranking", ordena(usuarios));
	}
	
	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * 
	 * @return void
	 */
	private void verRankingAmigos(HttpSession session) {
		
		User u = Utils.userFromSession(entityManager, session);

		session.setAttribute("ranking", ordena(u.getAmigos()));
	}
	
	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * 
	 * @return void
	 */
	private void verRankingGlobal(HttpSession session) {

		@SuppressWarnings("unchecked")
		List<User> usuarios = (List<User>) entityManager.createNamedQuery("getUsuarios").getResultList();
		session.setAttribute("ranking", ordena(usuarios));
	}
	
	/**
	 * Operacion auxiliar: Ordena una lista de usuarios mayor a menor en funcion de
	 * su dinero
	 * 
	 * @param usuarios:
	 *            Lista desordenada
	 * @return lista de usuarios ordenada
	 */
	private List<User> ordena(List<User> usuarios) {

		Collections.sort(usuarios, new Comparator<User>() {
			public int compare(User u1, User u2) {

				if (u1.getDinero() > u2.getDinero())
					return u1.getDinero();

				return u2.getDinero();
			}
		});
		return usuarios;
	}
	
	/**
	 * Operacion auxiliar: Saca un usuario de la partida en la que está.
	 * 
	 * @param u:
	 *          Usuario a sacar de la partida.
	 * @return Void.
	 */
	private void elminaJugador(User u) {

		int itJugadores = 0;
		while (itJugadores < u.getPartida().getJugadores().size() && 
			   !u.getPartida().getJugadores().get(itJugadores).getLogin().equals(u.getLogin())) {
		}
		
		u.getPartida().getJugadores().remove(itJugadores);
	}
	
	/**
	 * Operación auxiliar: Busca si un usuario tiene un amigo en concreto. Si
	 * el parametro elimina esta a true, además lo elimina.
	 * 
	 * @param u2:
	 *            Usuario a buscar.
	 * @param elimina:
	 *            Modo eliminacion.
	 *            
	 * @return true si lo encontro, false en caso contrario.
	 */
	private boolean usuarioTieneAmigo(User u2, boolean elimina, User u) {

		int indiceA = 0;
		while (indiceA < u.getAmigos().size() && 
			   !u.getAmigos().get(indiceA).getLogin().equals((u2.getLogin()))) {
			
			indiceA++;
		}

		if(indiceA != u.getAmigos().size()) {
			
			if(elimina) {
				u.getAmigos().remove(indiceA);
			}
			
			return true;
		}
		
		return false;

	}
	
	/**
	 * Operación auxiliar: Busca si un usuario tiene un item en concreto. 
	 * Si el parametro elimina está a true, además lo elimina.
	 * 
	 * @param i:
	 *            Item a buscar.
	 * @param elimina:
	 *            Modo eliminacion.          
	 * @param u:
	 *            Usuario en cuestión.         
	 * @return true si lo encontro, false en caso contrario.
	 */
	private boolean usuarioTieneItem(Item i, boolean elimina, User u) {

		int iPropiedades = 0;

		while (iPropiedades < u.getPropiedades().size() && 
			   !u.getPropiedades().get(iPropiedades).getNombre()
			   .equals(i.getNombre())) {
			iPropiedades++;
		}

		if (iPropiedades != u.getPropiedades().size()) {

			int iPropietarios = 0;
			while (iPropietarios < i.getPropietarios().size() && 
				   !i.getPropietarios().get(iPropiedades).getLogin().equals(u.getLogin())) {
				iPropietarios++;
			}

			if (elimina) {
				u.getPropiedades().remove(iPropiedades);
				i.getPropietarios().remove(iPropietarios);
			}

			return true;
		}

		return false;

	}
		
	/**
	 * Operación auxiliar: Revisa si el nuevo valor que introdujo el usuario es
	 * igual al nuevo. La idea de este método es evitar transacciones inecesarias 
	 * con la bd.
	 * 
	 * @param nuevoValor:
	 *            Valor insertado por el usuario.
	 * @param antiguoValor:
	 *            Valor que tenia antes del intento de modificacion.
	 * @return true si son distintos, false en caso contrario.
	 */	
	private boolean revisaModificacion(String nuevoValor, String antiguoValor) {
		if(!"".equals(nuevoValor) && !nuevoValor.equals(antiguoValor)) return true;
		return false;
	}
}