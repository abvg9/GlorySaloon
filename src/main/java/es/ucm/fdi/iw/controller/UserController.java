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
import java.util.HashSet;
import java.util.Iterator;
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
import es.ucm.fdi.iw.common.utils.CargaAtributos;
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
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### LOGIN ### ### ###
	 * #########################################################################
	 * #######################################################################
	 */

	/**
	 * Operaciones: 1º Crear Cuenta
	 */

	/**
	 * Operacion creadora: Crea un nuevo usuario Se pide la contrasena actual del
	 * usuario por mayor seguridad
	 * 
	 * @param nombre:
	 *            Nombre del usuario
	 * @param cont:
	 *            Contrasena del usuario
	 * @param email:
	 *            Email del usuario
	 * @param nacion:
	 *            Pais del usuario
	 * @return Si todo a salido bien, te redirige a login, si no, carga en sesion un
	 *         mensaje de error y te redirige a crearCuenta.
	 */
	@RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(@RequestParam(required = true) String nombre, @RequestParam(required = true) String cont,
			@RequestParam(required = true) String email, @RequestParam(required = true) Nacionalidades nacion,
			HttpSession session) {

		if ("".equals(nombre) || "".equals(cont) || "".equals(email) || "".equals(nacion.toString())) {

			session.setAttribute(CargaAtributos.mensaje, "Debes rellenar todos los campos.");
			return "crearCuenta";

		} else if (entityManager.createNamedQuery("noRepes").setParameter("loginParam", nombre)
				.setParameter("emailParam", email).getResultList().isEmpty()) {

			byte a = 0;
			User u = new User();
			u.setLogin(nombre);
			u.setPassword(passwordEncoder.encode(cont));
			u.setRoles("USER");
			u.setEnabled(a);
			u.setDinero(1000);
			u.setEmail(email);
			u.setNacion(nacion);
			u.setPganadas(0);
			u.setPperdidas(0);
			u.setPjugadas(0);
			u.setDperdido(0);
			u.setDganado(0);
			u.setAmigos(new HashSet<User>());
			u.setComentarios(new HashSet<ComentarioForo>());
			u.setPropiedades(new HashSet<Item>());
			u.setPartida(null);
			u.setListo(false);

			entityManager.persist(u);
			session.setAttribute(CargaAtributos.mensaje, "La cuenta se creo correctamente :)");
			log.info(nombre + " creo una cuenta");
			return "login";

		}
		session.setAttribute(CargaAtributos.mensaje, "Nombre y/o email ya cogidos :(");
		return "crearCuenta";
	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### PERFIL USUARIO ### ### ###
	 * #########################################################################
	 * #######################################################################
	 */

	/**
	 * Operaciones: 1º Modifcar => login, password, email, nacion, imagen 2º Anadir
	 * amigo 3º Ver perfil(tanto el suyo como el de amigos 4º Eliminar cuenta 5º
	 * Eliminar amigo 6º Eliminar item 7º Logout
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
	public String modificarPerfil(String nombre, String contNueva, @RequestParam(required = true) String contActual,
			String email, Nacionalidades nacion, HttpSession session) {

		User u = CargaAtributos.userFromSession(entityManager, session);
		if (passwordEncoder.matches(contActual, u.getPassword())) {

			int modificacion = 0;

			// FIXME: si lo haces 4 veces, escribete una f. auxiliar
			if (!"".equals(nombre) && !u.getLogin().equals(nombre)) {
				u.setLogin(nombre);
				modificacion++;
			}

			if (!"".equals(email) && !u.getEmail().equals(email)) {
				u.setEmail(email);
				modificacion++;
			}

			if (!"".equals(contNueva) && contNueva != contActual) {
				u.setPassword(passwordEncoder.encode(contNueva));
				modificacion++;
			}

			if (!"".equals(nacion.toString()) && !u.getNacion().equals(nacion)) {
				u.setNacion(nacion);
				modificacion++;
			}

			if (modificacion > 0) {
				session.setAttribute(CargaAtributos.mensaje,
						(modificacion == 1 ? "Modificacion realizada" : "Modificaciones realizadas")
								+ "correctamente.");

				log.info(u.getLogin() + " modificó parametros de su perfil.");
				entityManager.flush();
				session.setAttribute(CargaAtributos.user, u);

				return "perfil";
			}

			session.setAttribute(CargaAtributos.mensaje,
					"Se ha producido un error, debes rellenar al menos uno de los campos y debe/n ser diferentes a los antiguos.");

			return "perfil";
		}

		session.setAttribute(CargaAtributos.mensaje,
				"Contrasena incorrecta. Es obligatorio que rellenes el campo 'Contrasena actual'");
		session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");
		return "perfil";

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

		User u = CargaAtributos.userFromSession(entityManager, session);

		if (nombreA.equals(u.getLogin())) {
			session.setAttribute(CargaAtributos.mensaje, "No te puedes anadir a ti mismo como amigo.");
			return "perfil";
		}

		try {
			User u2 = (User) entityManager.createNamedQuery("getUsuario").setParameter("loginParam", nombreA)
					.getSingleResult();

			if (!usuarioTieneAmigo(u2, false, u)) {

				u.getAmigos().add(u2);
				session.setAttribute(CargaAtributos.mensaje, nombreA + " anadido a amigos.");
				session.setAttribute(CargaAtributos.user, u);

			} else {
				session.setAttribute(CargaAtributos.mensaje, nombreA + " ya esta en tu lista de amigos.");
			}

		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje, "No existe ningun usuario llamado " + nombreA + ".");
		}

		session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");
		return "perfil";
	}

	/**
	 * Operacion observadora: Ver el perfil de un usuario dado su nombre.
	 * 
	 * @param nombre:
	 *            Nombre del usuario
	 * @return Te redirige al perfil o al perfil del amigo, cargando en el sesion la
	 *         respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/pefilAmigo", method = RequestMethod.GET)
	public String perfilAmigo(@RequestParam(required = true) String nombre, HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
		try {
			User u2 = (User) entityManager.createNamedQuery("getUsuario")
					.setParameter("loginParam", nombre)
					.getSingleResult();

			if (usuarioTieneAmigo(u2, false, u)) {

				User perfil = (User) entityManager.createNamedQuery("getUsuario")
						.setParameter("loginParam", nombre)
						.getSingleResult();
				session.setAttribute(CargaAtributos.perfil, perfil);
				session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");
				return "perfilAmigo";
			} else {

				session.setAttribute(CargaAtributos.mensaje,
						"Perfil privado, debes anadirlo a amigos para poder verlo.");
				return "perfil";
			}

		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje, nombre + " no existe.");
		}

		return "perfil";
	}

	/**
	 * Operacion modificadora: Elimina la cuenta de usuario logueado de la BD
	 * 
	 * @param nombre:
	 *            Nombre del usuario del que se elimina su cuenta
	 * @return Te redirige al login tras borrar tu cuenta
	 */
	@RequestMapping(value = "/eliminarCuenta", method = RequestMethod.POST)
	@Transactional
	public String eliminarCuenta(HttpSession session, String nombre) {

		User u = null;
		boolean iguales = false;
		User us = CargaAtributos.userFromSession(entityManager, session);


		// un admin esta intentado borrar una cuenta que no es la suya
		if (nombre != null && !nombre.equals(us.getLogin())) {

			try {
				u = (User) entityManager.createNamedQuery("getUsuario").setParameter("loginParam", nombre)
						.getSingleResult();

			} catch (NoResultException e) {
				log.error(e);
				session.setAttribute(CargaAtributos.mensaje, nombre + " no existe.");
				return "perfil";
			}

		} else {
			// un usuario(sea admin o usuario) esta intentado borrar su cuenta
			u = us;
			iguales = true;
		}
		u.setAmigos(null);
		u.setComentarios(null);

		for (Item it : u.getPropiedades()) {
			usuarioTieneItem(it, true, u);
		}

		u.setPropiedades(null);

		entityManager.remove(entityManager.merge(u));
		// borrar imagen usuario
		File f = localData.getFile("user", String.valueOf(u.getId()));
		f.delete();
		log.info(u.getLogin() + " elimino su cuenta.");

		if (iguales) {
			eliminaVariablesSession(session);
		}

		if (iguales)
			return "login";

		return "perfil";
	}

	/**
	 * Operacion modificadora: Elimina un amigo de la lista de amigos del usuario
	 * 
	 * @param nombreAmigo:
	 *            Nombre del amigo del usuario
	 * @return Te redirige al perfil
	 */
	@RequestMapping(value = "/eliminarAmigo", method = RequestMethod.POST)
	@Transactional
	public String eliminarAmigo(@RequestParam(required = true) String nombreAmigo, HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
		try {
			User u2 = (User) entityManager.createNamedQuery("getUsuario").setParameter("loginParam", nombreAmigo)
					.getSingleResult();

			if (usuarioTieneAmigo(u2, true, u)) {

				session.setAttribute(CargaAtributos.mensaje, nombreAmigo + " eliminado.");
				session.setAttribute(CargaAtributos.user, u);
			} else {
				session.setAttribute(CargaAtributos.mensaje, nombreAmigo + " no esta en tu lista de amigos.");
			}
		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje, nombreAmigo + " no existe.");
		}
		session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");
		return "perfil";
	}

	/**
	 * Operacion modificadora: Elimina un item de la lista de propiedades del
	 * usuario
	 * 
	 * @param id:
	 *            Id del item
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/borrarItem", method = RequestMethod.POST)
	@Transactional
	public String borrarItem(@RequestParam(required = true) long id, HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
		try {
			Item i = (Item) entityManager.find(Item.class, id);

			if (usuarioTieneItem(i, true, u)) {

				entityManager.merge(u);
				entityManager.merge(i);
				entityManager.flush();
				session.setAttribute(CargaAtributos.user, u);

			} else {
				session.setAttribute(CargaAtributos.mensaje, "No tienes ese item");
			}

		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje, "El item no existe");
		}

		session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");

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

		eliminaVariablesSession(session);
		return "login";
	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### SALOON ### ### ###
	 * #########################################################################
	 * #######################################################################
	 */

	/**
	 * Operaciones:
	 * 
	 * 1º Crear partida 2º Unirse a una partida 3º Ver y buscar partidas(por juego,
	 * por amigos o por nombre) 4º Salir de la partida 5º Salir del juego
	 */

	/**
	 * Operacion creadora: Crea una nueva partida
	 * 
	 * @param juego:
	 *            Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores:
	 *            Maximo numero de jugadores
	 * @param cont:
	 *            Contrasena de la partida
	 * @param nombrePar:
	 *            Nombre de la partida
	 * @return Info de si se ha creado correctamente la partida o redirige a la
	 *         partida
	 */
	@RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
	@Transactional
	public String crearPartida(String cont, HttpSession session, @RequestParam(required = true) Juegos juego,
			@RequestParam(required = true) int maxJugadores, @RequestParam(required = true) String nombrePar) {

		User u = (User) session.getAttribute(CargaAtributos.user);

		if (u.getPartida() == null) {

			if (maxJugadores < 2 || maxJugadores > 7) {
				session.setAttribute(CargaAtributos.mensaje,
						"Las partidas deben ser de como minimo 2 jugadores y como maximo 7.");
				return "saloon";
			}

			if (entityManager.createNamedQuery("getPartidaPorNombre").setParameter("nombreParam", nombrePar)
					.getResultList().isEmpty()) {
				Partida p = new Partida();
				p.setNombre(nombrePar);
				p.setJugadores(new HashSet<User>());
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
				entityManager.merge(u);
				session.setAttribute(CargaAtributos.user, u);
				session.setAttribute(CargaAtributos.mensaje, "Estas dentro de la partida");

			} else {
				session.setAttribute(CargaAtributos.mensaje, "Ese nombre de partida ya esta cogido :(");
			}

		} else {
			session.setAttribute(CargaAtributos.mensaje, "Ya estas dentro de una partida.");
		}

		return "saloon";
	}

	/**
	 * Operacion modificadora: Un usuario se une a una partida ya existente.
	 * 
	 * @param juego:
	 *            Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores:
	 *            Maximo numero de jugadores
	 * @param nombrePar:
	 *            Nombre de la partida
	 * @return Info de si ha habido algun problema al unirse a la partida o
	 *         redireccion a la partida.
	 */
	@RequestMapping(value = "/unirsePartida", method = RequestMethod.POST)
	@Transactional
	public String unirsePartida(@RequestParam(required = true) long id_p, String pass, HttpSession session) {

		Partida p = entityManager.find(Partida.class, id_p);
		User u = (User) session.getAttribute(CargaAtributos.user);

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
						entityManager.merge(u);
						entityManager.persist(p);
						session.setAttribute(CargaAtributos.user, u);
						session.setAttribute(CargaAtributos.mensaje, "Estas dentro de la partida." + darDin);
					} else {
						session.setAttribute(CargaAtributos.mensaje, "Contrasena incorrecta.");
					}

				} else {
					session.setAttribute(CargaAtributos.mensaje, "No hay hueco en la partida.");
				}
			} else {
				session.setAttribute(CargaAtributos.mensaje, "Ya estas dentro de una partida.");
			}
		} else {
			session.setAttribute(CargaAtributos.mensaje, "Esa partida ya no existe.");
		}
		return "saloon";
	}

	/**
	 * Operacion obervadora: Busca y muestra las partidas en funcion de el nombre
	 * del tipo de juego o del nombre de la paretida o de los amigos.
	 * 
	 * @param juego:
	 *            Tipo de juego al que se va a jugar en la partida
	 * @param nombrePar:
	 *            Nombre de la partida
	 * @return Info de si se ha creado correctamente la partida o no.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verSaloon", method = RequestMethod.GET)
	public String verPartidas(HttpSession session, Juegos juego, String nombrePar, Model model,
			HttpServletRequest request) {

		session.setAttribute(CargaAtributos.mensaje, null);
		List<Partida> partidas = new ArrayList<Partida>();
		User u = (User) session.getAttribute(CargaAtributos.user);

		if (!"".equals(nombrePar)) {

			partidas = (List<Partida>) entityManager.createNamedQuery("getPartidaPorNombre")
					.setParameter("nombreParam", nombrePar).getResultList();

			if (!partidas.isEmpty()) {
				session.setAttribute(CargaAtributos.saloon, partidas);
			} else {
				session.setAttribute(CargaAtributos.mensaje, "Esa partida no existe.");
			}

			CargaAtributos.socket(model, request, "user/verSaloon", CargaAtributos.chatSocket);
			return "saloon";

		} else if (juego != Juegos.Amigos) {
			partidas = (List<Partida>) entityManager.createNamedQuery("getPartidaPorJuego")
					.setParameter("juegoParam", juego).getResultList();
			session.setAttribute(CargaAtributos.saloon, partidas);
			CargaAtributos.socket(model, request, "user/verSaloon", CargaAtributos.chatSocket);
			return "saloon";
		}

		for (User u2 : u.getAmigos()) {
			partidas.add(u2.getPartida());
		}

		session.setAttribute(CargaAtributos.saloon, partidas);
		CargaAtributos.socket(model, request, "user/verSaloon", CargaAtributos.chatSocket);
		return "saloon";
	}

	/**
	 * Operacion modicadora: Pasa de turno.
	 * 
	 * @param id_partida:
	 *            Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/empezarPartida", method = RequestMethod.POST)
	@Transactional
	public String empezarPartida(HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
		Partida p = entityManager.find(Partida.class, u.getPartida().getId());
		int a = 0;

		if (!u.isListo()) {
			u.setListo(true);
			entityManager.merge(u);
		}

		Iterator<User> it = p.getJugadores().iterator();
		while (it.hasNext() && it.next().isListo()) {
			a++;
		}

		if (!it.hasNext()) {

			p.setAbierta(false);
			u.setPartida(p);
			entityManager.persist(p);
			entityManager.merge(u);

		} else {
			String jugadores = "";

			for (User u2 : p.getJugadores()) {
				jugadores += u2.getLogin();
				if (u2.isListo()) {
					jugadores += " esta listo.";
				} else {
					jugadores += " no esta listo.";
				}
			}

			session.setAttribute(CargaAtributos.mensaje, "Faltan jugadores por confirmar (" + a + "/"
					+ p.getMaxJugadores() + ")." + "Dales unos segundos para que esten listos. " + jugadores);

			session.setAttribute(CargaAtributos.user, u);
			return "saloon";
		}

		session.setAttribute(CargaAtributos.user, u);
		return "redirect:/partidaBlackJack";
	}

	/**
	 * Operacion modicadora: Saca a un usuario de la partida en la que esta
	 * actualmente(esta eperando, aun no ha jugado). Si la partida tiene menos de
	 * dos jugadores, esta se cerrara y sacara al otro usuario.
	 * 
	 * @return Redirige a sallon
	 */
	@RequestMapping(value = "/salirDeLaPartida", method = RequestMethod.POST)
	@Transactional
	public String salirDeLaPartida(HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
		Partida p = entityManager.find(Partida.class, u.getPartida().getId());

		elminaJugador(u);
		u.setPartida(null);
		entityManager.merge(u);

		if (p.getJugadores().size() > 0) {
			entityManager.merge(p);
		} else {
			entityManager.remove(p);
		}
		session.setAttribute(CargaAtributos.user, u);
		session.setAttribute(CargaAtributos.mensaje, "Saliste de la partida");

		return "redirect:/saloon";
	}

	@RequestMapping(value = "/salirDelJuego", method = RequestMethod.POST)
	@Transactional
	public String salirDelJuego(HttpSession session, @RequestParam(required = true) String dineroFinal) {

		int dinero = Integer.valueOf(dineroFinal);
		User u = (User) session.getAttribute(CargaAtributos.user);

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
			entityManager.remove(u.getPartida());
		} else {
			entityManager.merge(u.getPartida());
		}

		u.setPartida(null);
		u.setPjugadas(u.getPjugadas() + 1);
		u.setDinero(dinero);

		entityManager.merge(u);

		session.setAttribute(CargaAtributos.user, u);

		return "redirect:/saloon";
	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### TIENDA ### ### ###
	 * #########################################################################
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
	public String comprarItem(@RequestParam(required = true) long id_it, HttpSession session) {

		try {
			Item i = entityManager.find(Item.class, id_it);
			User u = (User) session.getAttribute(CargaAtributos.user);

			if (!usuarioTieneItem(i, false, u)) {

				if (u.getDinero() >= i.getPrecio()) {

					u.setDinero(u.getDinero() - i.getPrecio());
					u.getPropiedades().add(i);
					i.getPropietarios().add(u);
					entityManager.merge(i);
					entityManager.merge(u);
					session.setAttribute(CargaAtributos.user, u);
					session.setAttribute(CargaAtributos.mensaje, "Compra realizada :)");

				} else {
					session.setAttribute(CargaAtributos.mensaje, "No tienes suficiente dinero.");
				}
			} else {
				session.setAttribute(CargaAtributos.mensaje, "Ya tienes ese item.");
			}

		} catch (NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje, "El item no existe");
		}
		CargaAtributos.tienda(session, entityManager);
		return "tienda";
	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### FORO ### ### ###
	 * #########################################################################
	 * #######################################################################
	 */

	/**
	 * Operaciones:
	 * 
	 * 1º Comentar en foro 2º Borrar comentario
	 */

	/**
	 * Operacion modicadora: Usuario comenta en el foro en un tema en concreto
	 * 
	 * @param comentario:
	 *            Mensaje del comentario
	 * @param tema:
	 *            Tema del que comentara el usuario
	 * @return Redirrecion al foro.
	 */
	@RequestMapping(value = "/comentar", method = RequestMethod.POST)
	@Transactional
	public String Comentar(@RequestParam(required = true) String comentario, @RequestParam(required = true) Temas tema,
			HttpSession session) {

		ComentarioForo c = new ComentarioForo();
		User u = (User) session.getAttribute(CargaAtributos.user);

		c.setComentario(comentario);
		c.setTema(tema);
		c.setUsuario(u);
		c.setFecha(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		entityManager.merge(c);

		session.setAttribute(CargaAtributos.user, u);
		CargaAtributos.foro(session, entityManager, tema);
		session.setAttribute(CargaAtributos.tema, tema);
		return "foro";
	}

	/**
	 * Operacion eliminadora: Usuario borra un comentario que hizo.
	 * 
	 * @param id_c:
	 *            Id del comentario
	 * @return Te redirige al foro.
	 */
	@RequestMapping(value = "/borrarComentario", method = RequestMethod.POST)
	@Transactional
	public String borrarComentario(@RequestParam(required = true) long id_c, HttpSession session) {

		ComentarioForo c = entityManager.find(ComentarioForo.class, id_c);
		entityManager.remove(c);
		CargaAtributos.foro(session, entityManager, c.getTema());
		session.setAttribute(CargaAtributos.tema, c.getTema());

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

		session.setAttribute(CargaAtributos.mensaje, null);
		CargaAtributos.foro(session, entityManager, tema);
		session.setAttribute(CargaAtributos.tema, tema);
		return "foro";

	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### RANKING ### ### ###
	 * #########################################################################
	 * #######################################################################
	 */

	/**
	 * Operaciones:
	 * 
	 * 1º Ver ranking por amigos, 2º Por pais o 3º Por global
	 */

	/**
	 * Operacion observadora: Muestra el ranking en funcion de la busqueda
	 * seleccionada
	 * 
	 * @param busqueda:
	 *            Opcion de busqueda
	 * @return Te redirige al ranking
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

		session.setAttribute(CargaAtributos.imagen, "/user/fotoPerfil/");
		return "ranking";
	}

	/*
	 * #######################################################################
	 * ######################################################################### ###
	 * ### ### AUXILIARES ### ### ###
	 * #########################################################################
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
			session.setAttribute(CargaAtributos.mensaje, "No has elegido ninguna foto a subir.");
		} else {
			File f = localData.getFile("user", id);
			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f))) {
				stream.write(photo.getBytes());
				session.setAttribute(CargaAtributos.mensaje, "Foto subida correctamente :)");
			} catch (Exception e) {
				session.setAttribute(CargaAtributos.mensaje, "Error al subir la foto :(");
			}
		}

		return "perfil";
	}

	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void verRankingPais(HttpSession session) {

		User u = (User) session.getAttribute(CargaAtributos.user);
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
		User u = (User) session.getAttribute(CargaAtributos.user);
		List<User> amigos = new ArrayList<User>(u.getAmigos().size());

		for (User u2 : u.getAmigos()) {
			amigos.add(u2);
		}

		session.setAttribute("ranking", ordena(amigos));
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

	private void eliminaVariablesSession(HttpSession session) {
		session.removeAttribute(CargaAtributos.user);
		session.removeAttribute(CargaAtributos.tema);
		session.removeAttribute(CargaAtributos.imagen);
		session.removeAttribute(CargaAtributos.mensaje);
		session.removeAttribute(CargaAtributos.foro);
		session.removeAttribute(CargaAtributos.tienda);
	}

	private void elminaJugador(User u) {

		Iterator<User> it = u.getPartida().getJugadores().iterator();
		while (it.hasNext() && !it.next().getLogin().equals(u.getLogin())) {
		}
		it.remove();
	}

	/**
	 * Operacion auxiliar: Busca si un usuario tiene un amigo en concreto. Si
	 * elimina esta a true, ademas lo elimina
	 * 
	 * @param u2:
	 *            Usuario a buscar
	 * @param elimina:
	 *            Modo eliminacion
	 * @return true si lo encontro, false en caso contrario.
	 */
	private boolean usuarioTieneAmigo(User u2, boolean elimina, User u) {

		Iterator<User> it = u.getAmigos().iterator();
		while (it.hasNext() && !it.next().getLogin().equals(u2.getLogin())) {
		}

		if (elimina) {
			it.remove();
		}

		return it.hasNext();

	}

	/**
	 * Operacion auxiliar: Busca si un usuario tiene un item en concreto. Si elimina
	 * esta a true, ademas lo elimina
	 * 
	 * @param i:
	 *            Item a buscar
	 * @param elimina:
	 *            Modo eliminacion
	 * @return true si lo encontro, false en caso contrario.
	 */
	private boolean usuarioTieneItem(Item i, boolean elimina, User u) {

		Iterator<Item> itPropiedades = u.getPropiedades().iterator();

		while (itPropiedades.hasNext() && !itPropiedades.next().getNombre().equals(i.getNombre())) {
		}

		if (itPropiedades.hasNext()) {

			Iterator<User> itPropietarios = i.getPropietarios().iterator();
			while (itPropietarios.hasNext() && !itPropietarios.next().getLogin().equals(u.getLogin())) {
			}

			if (elimina) {
				itPropiedades.remove();
				itPropietarios.remove();
			}

			return true;
		}

		return false;

	}
}