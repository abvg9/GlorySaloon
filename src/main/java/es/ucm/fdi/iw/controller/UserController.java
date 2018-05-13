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
import es.ucm.fdi.iw.common.enums.Movimientos;
import es.ucm.fdi.iw.common.enums.Nacionalidades;
import es.ucm.fdi.iw.common.enums.Temas;
import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.logica.Jugador;
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
    public void addAttributes(Model model,HttpSession session) {	  	
        model.addAttribute("s", "/static");
        session.setAttribute(CargaAtributos.user, CargaAtributos.u);
    }
        	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                           LOGIN                                   ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
    
    /** Operaciones:
     *  1º Crear Cuenta
     */
     	   
	/**
	 * Operacion creadora: Crea un nuevo usuario
	 * Se pide la contraseña actual del usuario por mayor seguridad
	 * @param nombre: Nombre del usuario 
	 * @param cont: Contraseña del usuario
	 * @param email: Email del usuario
	 * @param nacion: Pais del usuario
	 * @return Si todo a salido bien, te redirige a login, si no, carga en sesion un mensaje de error y te redirige a crearCuenta.
	 */
    @RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(
			@RequestParam(required=true) String nombre, 
			@RequestParam(required=true) String cont,
			@RequestParam(required=true) String email,
			@RequestParam(required=true) Nacionalidades nacion,
										 HttpSession session) {

		if("".equals(nombre) || "".equals(cont) ||
		   "".equals(email) || "".equals(nacion.toString())) {

			session.setAttribute(CargaAtributos.mensaje,"Debes rellenar todos los campos.");
			return "crearCuenta";
			
		}else if(entityManager.createNamedQuery("noRepes").setParameter("loginParam", nombre)
		         .setParameter("emailParam", email)
		         .getResultList().isEmpty()) { 
			
			byte a = 0;
			User u = new User();
			u.setLogin(nombre);
			u.setPassword(passwordEncoder.encode(cont));
			u.setRoles("USER");
			u.setEnabled(a);
			u.setDinero(0);
			u.setEmail(email);
			u.setNacion(nacion);	
			u.setPganadas(0);
			u.setPperdidas(0);
			u.setPjugadas(0);
			u.setDperdido(0);
			u.setDganado(0);
			u.setAmigos(new ArrayList<User>());
			u.setComentarios(new ArrayList<ComentarioForo>());
			u.setPropiedades(new ArrayList<Item>());
			u.setPartida(new Partida());
			u.setListo(false);
				
			entityManager.persist(u);
			session.setAttribute(CargaAtributos.mensaje,"La cuenta se creo correctamente :)");
			log.info(nombre + " creo una cuenta");
			return "login";
						 
		}
		session.setAttribute(CargaAtributos.mensaje,"Nombre y/o email ya cogidos :(");
		return "crearCuenta";
	}
		  
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                       PERFIL USUARIO                              ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
    
    /** Operaciones:
     *  1º Modifcar => login, password, email, nacion, imagen
     *  2º Añadir amigo
     *  3º Ver perfil(tanto el suyo como el de amigos
     *  4º Eliminar cuenta
     *  5º Eliminar amigo
     *  6º Eliminar item
     *  7º Logout
     */
      
	/**
	 * Operacion modificadora: Cambia los datos personales del usuario. Solo modifica aquellos campos que sean rellenados.
	 * Se pide la contraseña actual del usuario por mayor seguridad
	 * @param nombre: Nuevo nombre del usuario 
	 * @param contNueva: Nueva contraseña del usuario
	 * @param contActual: Nueva contraseña actual del usuario
	 * @param email: Nuevo email del usuario
	 * @param nacion: Nuevo pais del usuario
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/modificarPerfil", method = RequestMethod.POST)
	@Transactional
	public String modificarPerfil(String nombre,String contNueva,@RequestParam(required=true) String contActual,
								  String email,Nacionalidades nacion,HttpSession session){
				
		if(passwordEncoder.matches(contActual, CargaAtributos.u.getPassword())) {
			
			int modificacion = 0;

			if(!"".equals(nombre) && !CargaAtributos.u.getLogin().equals(nombre))
			{CargaAtributos.u.setLogin(nombre); modificacion++;}
			
			if(!"".equals(email) && !CargaAtributos.u.getEmail().equals(email))
			{CargaAtributos.u.setEmail(email); modificacion++;}
					
			if(!"".equals(contNueva) && !passwordEncoder.matches(contNueva, contActual))
			{CargaAtributos.u.setPassword(passwordEncoder.encode(contNueva)); modificacion++;}
			
			if(!"".equals(nacion.toString()) && !CargaAtributos.u.getNacion().equals(nacion))
			{CargaAtributos.u.setNacion(nacion); modificacion++;}

			if(modificacion > 0) {
				
				if(modificacion == 1) {session.setAttribute(CargaAtributos.mensaje,"Modificacion realizada correctamente.");}
				else {session.setAttribute(CargaAtributos.mensaje,"Modificaciones realizadas correctamente.");}
				log.info(CargaAtributos.u.getLogin() + " modifico parametros de su perfil.");
				entityManager.merge(CargaAtributos.u);
							
				return "perfil";
			}
			
			session.setAttribute(CargaAtributos.mensaje,"Se ha producido un error, debes rellenar al menos uno de los campos y debe/n ser diferentes a los antiguos.");

			return "perfil";
		}
		
		session.setAttribute(CargaAtributos.mensaje,"Contraseña incorrecta. Es obligatorio que rellenes el campo 'Contraseña actual'");
		session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
		return "perfil";
			
	}

	/**
	 * Operacion modificadora: Añade un nuevo amigo a la lista de amigos del usuario. 
	 * Revisa si el amigo existe en la BD y si no lo tenia anteriormente.
	 * Esta operacion no añade en la lista del amigo el usuario en cuestion.
	 * @param nombreA: Nombre del amigo
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/añadirAmigo", method = RequestMethod.POST)
	@Transactional
	public String añadirAmigo(@RequestParam(required=true) String nombreA, HttpSession session) {
		
		if(nombreA.equals(CargaAtributos.u.getLogin())) {
			session.setAttribute(CargaAtributos.mensaje,"No te puedes añadir a ti mismo como amigo.");
			return "perfil";
		}
		
		try {
			User u2 = (User)entityManager.createNamedQuery("getUsuario")
					   .setParameter("loginParam", nombreA)
			           .getSingleResult();
								
			if(usuarioTieneAmigo(u2,false)){
				
				CargaAtributos.u.getAmigos().add(u2);
				entityManager.merge(CargaAtributos.u);
				session.setAttribute(CargaAtributos.mensaje,nombreA+" añadido a amigos.");
				session.setAttribute(CargaAtributos.user,CargaAtributos.u);
				
			}else {
				session.setAttribute(CargaAtributos.mensaje,nombreA+" ya esta en tu lista de amigos.");
			}

		}catch(NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje,"No existe ningun usuario llamado " +nombreA+".");
		}
		
		session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");	
		return "perfil";
	}
	
	/**
	 * Operacion observadora: Ver el perfil de un usuario dado su nombre.
	 * @param nombre: Nombre del usuario
	 * @return Te redirige al perfil o al perfil del amigo, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/pefilAmigo", method = RequestMethod.GET)
	public String pefilAmigo(@RequestParam(required=true) String nombre,HttpSession session) {
		
		try {
			User u2 = (User)entityManager.createNamedQuery("getUsuario")
			           .setParameter("loginParam", nombre)
			           .getSingleResult();
			
			if(usuarioTieneAmigo(u2,false)) {
				session.setAttribute(CargaAtributos.mensaje,"Perfil privado, debes añadirlo a amigos para poder verlo.");
				return "perfil";
			}

			if(!nombre.equals(CargaAtributos.u.getLogin())){
				
				User perfil = (User)entityManager.createNamedQuery("getUsuario")
				               .setParameter("loginParam", nombre)
				               .getSingleResult();
				session.setAttribute("perfil", perfil);
				session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
				return "perfilAmigo";
			}
			
		}catch(NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje,nombre + " no existe.");
		}
			
		return "perfil";
	}
	
	/**
	 * Operacion modificadora: Elimina la cuenta de usuario logueado de la BD
	 * @param nombre: Nombre del usuario del que se elimina su cuenta
	 * @return Te redirige al login tras borrar tu cuenta
	 */
	@RequestMapping(value = "/eliminarCuenta", method = RequestMethod.POST)
	@Transactional
	public String eliminarCuenta(HttpSession session, String nombre) {
		
		User u = null;
		boolean iguales = false;
		
		if(nombre != null){
			
			if(nombre.equals(CargaAtributos.u.getLogin())) {
				u = CargaAtributos.u;
				iguales = true;	
			}else {
				
				if("".equals(nombre)){
					session.setAttribute(CargaAtributos.mensaje,"Debes rellenar el campo.");
					return "perfil";
				}
				
				try {
					u = (User)entityManager.createNamedQuery("getUsuario")
					     .setParameter("loginParam", nombre)
					     .getSingleResult();
				
				}catch(NoResultException e) {
					log.error(e);
					session.setAttribute(CargaAtributos.mensaje,nombre + " no existe.");
					return "perfil";
				}
			}
			
		}else {
			u = CargaAtributos.u;
			iguales = true;	
		}

		
		entityManager.remove(entityManager.merge(u));
		//borrar imagen usuario
		File f = localData.getFile("user", String.valueOf(u.getId()));
		f.delete();
		log.info(u.getLogin() + " elimino su cuenta.");
		
		if(iguales) {
			session.removeAttribute(CargaAtributos.user);
			session.removeAttribute(CargaAtributos.mensaje);
			session.removeAttribute(CargaAtributos.tema);
			session.removeAttribute(CargaAtributos.imagen);
			//session.invalidate();
			CargaAtributos.u = null;
		}
	
		if(iguales) return "login";
		
		return "perfil";
	}
	
	/**
	 * Operacion modificadora: Elimina un amigo de la lista de amigos del usuario
	 * @param nombreAmigo: Nombre del amigo del usuario
	 * @return Te redirige al perfil
	 */
	@RequestMapping(value = "/eliminarAmigo", method = RequestMethod.POST)
	@Transactional
	public String eliminarAmigo(@RequestParam(required=true) String nombreAmigo,HttpSession session) {
		
		try {
			User u2 = (User)entityManager.createNamedQuery("getUsuario")
			           .setParameter("loginParam", nombreAmigo)
			           .getSingleResult();
			
			if(!usuarioTieneAmigo(u2,true)) {
				session.setAttribute(CargaAtributos.mensaje,nombreAmigo + " eliminado.");
				session.setAttribute(CargaAtributos.user, CargaAtributos.u);
				entityManager.merge(CargaAtributos.u);
				
			}else {
				session.setAttribute(CargaAtributos.mensaje,nombreAmigo + " no esta en tu lista de amigos.");
			}
		}catch(NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje,nombreAmigo + " no existe.");
		}
		session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");	
		return "perfil";
	}
	
	/**
	 * Operacion modificadora: Elimina un item de la lista de propiedades del usuario
	 * @param id: Id del item
	 * @return Te redirige al perfil, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/borrarItem", method = RequestMethod.POST)
	public String borrarItem(@RequestParam(required=true) long id,HttpSession session) {
		
		try {
			Item i = (Item)entityManager.find(Item.class,id);
			
			if(usuarioTieneItem(i,true)){
		
				entityManager.merge(CargaAtributos.u);
				entityManager.merge(i);
				
			}else {
				session.setAttribute(CargaAtributos.mensaje,"No tienes ese item");
			}
			
		}catch(NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje,"El item no existe");
		}
		
		session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
		
		return "perfil";
	}
	
	/**
	 * Operacion modificadora: Elimina todas las varaibales de sesion y saca elimina los datos de usuario cargados
	 * @return Te redirige al login
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {

		session.removeAttribute(CargaAtributos.user);
		session.removeAttribute(CargaAtributos.tema);
		session.removeAttribute(CargaAtributos.imagen);
		session.removeAttribute(CargaAtributos.mensaje);
		session.removeAttribute(CargaAtributos.foro);
		session.removeAttribute(CargaAtributos.tienda);

		//session.invalidate();
		CargaAtributos.u=null;
		return "login";
	}
	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                            PARTIDAS                               ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
    /** Operaciones:
     *  ---------------------------
     *  --- Fuera de la partida ---
     *  ---------------------------
     *  
     *  1º Crear partida
     *  2º Unirse a una partida
     *  3º Ver y buscar partidas(por juego, por amigos o por nombre)
     *  
     *  -----------------------------------------------
     *  --- Dentro de la partida(sin haber empezao) ---
     *  -----------------------------------------------
     *  
     *  4º Salirse de la partida
     *  5º Pasar turno(realizando movimiento)
     *  6º Empezar partida
     *  
     *  -----------------------------------------------
     *  --- Dentro de la partida(habiendo empezado) ---
     *  -----------------------------------------------
     *  7º Apostar
     *  8º Pasar turno
     *  9º Pedir carta
     *  (salir->4º)
     *  
     */
	
	/**
	 * Operacion creadora: Crea una nueva partida
	 * @param juego: Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores: Maximo numero de jugadores
	 * @param cont: Contraseña de la partida
	 * @param nombrePar: Nombre de la partida
	 * @return Info de si se ha creado correctamente la partida o redirige a la partida
	 */
	@RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
	@Transactional
	public  String crearPartida(String cont,
								 HttpSession session,
                @RequestParam(required=true) Juegos juego,
			    @RequestParam(required=true) int maxJugadores, 
			    @RequestParam(required=true) String nombrePar) {
				
		if(CargaAtributos.u.getPartida() == null) {
			
			if(maxJugadores < 2 || maxJugadores > 7) {
				session.setAttribute(CargaAtributos.mensaje, "Las partidas deben ser de como minimo 2 jugadores y como maximo 7.");
				return "saloon";
			}

			if(entityManager.createNamedQuery("getPartidaPorNombre")
			                .setParameter("nombreParam", nombrePar)
			                .getResultList().isEmpty()) {
				Partida p = new Partida();
				p.setAbierta(true);
				p.setApostado(0);
				p.setJuego(juego);
				p.setMaxJugadores(maxJugadores);
				p.setNombre(nombrePar);
				p.setJugadores(new ArrayList<User>(maxJugadores));
				p.setValoresManos(new ArrayList<ArrayList<Integer>>(maxJugadores));
				p.setPalosManos(new ArrayList<ArrayList<Integer>>(maxJugadores));
				if("".equals(cont)) {
					p.setPass("no");
				}else {
					p.setPass(passwordEncoder.encode(cont));
				}
				CargaAtributos.u.setListo(false);
				
				p.getJugadores().add(CargaAtributos.u);
				CargaAtributos.u.setPartida(p);
				entityManager.persist(p);
				entityManager.persist(entityManager.merge(CargaAtributos.u));
				session.setAttribute(CargaAtributos.mensaje, "Estas dentro de la partida");
				return "saloon";
				
			}
			
			session.setAttribute(CargaAtributos.mensaje, "Ese nombre de partida ya esta cogido :(");
			return "saloon";
		}
		
		session.setAttribute(CargaAtributos.mensaje, "Ya estas dentro de una partida.");
		return "saloon";
	}
	
	/**
	 * Operacion modificadora: Un usuario se une a una partida ya existente.
	 * @param juego: Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores: Maximo numero de jugadores
	 * @param nombrePar: Nombre de la partida
	 * @return Info de si ha habido algun problema al unirse a la partida o redireccion a la partida.
	 */
	@RequestMapping(value = "/unirsePartida", method = RequestMethod.POST)
	@Transactional
	public String unirsePartida(
			     @RequestParam(required=true) long id_p,
										      String pass,
										      HttpSession session) {
		
		Partida p = entityManager.find(Partida.class, id_p);
		
		if(CargaAtributos.u.getPartida() != null) {
			
			if(p.getMaxJugadores() > p.getJugadores().size()+1 && p.isAbierta()) {
				
				if("no".equals(p.getPass()) || passwordEncoder.matches(pass, p.getPass())) {
					CargaAtributos.u.setPartida(p);
					p.getJugadores().add(CargaAtributos.u);
					entityManager.persist(p);
					entityManager.persist(CargaAtributos.u);
					session.setAttribute(CargaAtributos.mensaje, "Estas dentro de la partida");
				}else {
					session.setAttribute(CargaAtributos.mensaje, "Contraseña incorrecta.");
					return "saloon";
				}
					
			}else {
				session.setAttribute(CargaAtributos.mensaje, "No hay hueco en la partida.");
				return "saloon";
			}	
		}else {
			session.setAttribute(CargaAtributos.mensaje, "Ya estas dentro de una partida.");
			return "saloon";
		}
		
		return "saloon";
	}

	/**
	 * Operacion obervadora: Busca y muestra las partidas en funcion de el nombre del tipo de juego o del nombre de la paretida o de los amigos.
	 * @param juego: Tipo de juego al que se va a jugar en la partida
	 * @param nombrePar: Nombre de la partida
	 * @return Info de si se ha creado correctamente la partida o no.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verSaloon", method = RequestMethod.GET)
	public String verPartidas(HttpSession session,Juegos juego,String nombrePar) {
		
		session.setAttribute(CargaAtributos.mensaje, null);
		List<Partida> partidas = new ArrayList<Partida>();	
		
		if(!"".equals(nombrePar)) {
			
			partidas = (List<Partida>)entityManager.createNamedQuery("getPartidaPorNombre")
			           .setParameter("nombreParam", nombrePar)
			           .getResultList();	           
			session.setAttribute(CargaAtributos.saloon,partidas);
			return "saloon";
		}else if(juego != Juegos.Amigos){
			partidas = (List<Partida>)entityManager.createNamedQuery("getPartidaPorJuego")
			           .setParameter("juegoParam", juego)
			           .getResultList();	           
			session.setAttribute(CargaAtributos.saloon,partidas);
			return "saloon";
		}

		for(int i = 0; i < CargaAtributos.u.getAmigos().size(); i++) {
			partidas.add(CargaAtributos.u.getAmigos().get(0).getPartida());
		}            
		session.setAttribute(CargaAtributos.saloon,partidas);
		return "saloon";
	}
	
	/**
	 * Operacion modicadora: Pasa de turno.
	 * @param id_partida: Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/empezarPartida", method = RequestMethod.POST)
	@Transactional
	public String empezarPartida(long id_p,HttpSession session) {
		//si todos le han dado a empezar[partida.usuario[i].isListo()]
		Partida p = entityManager.find(Partida.class, id_p);
		int i = 0;
		int a = 0;
		
		if(!CargaAtributos.u.isListo()) {
			CargaAtributos.u.setListo(true);
			entityManager.persist(entityManager.merge(CargaAtributos.u));
		}

		while(i < p.getJugadores().size() && p.getJugadores().get(i).isListo()){
			if(p.getJugadores().get(i).isListo())
				a++;
			i++;
		}
		
		if(i == p.getMaxJugadores()) {
						
			CargaAtributos.u.setListo(false);
			p.setAbierta(false);
			entityManager.persist(entityManager.merge(CargaAtributos.u));
			entityManager.persist(p);
			
			List<Jugador> jugadores = new ArrayList<Jugador>(p.getMaxJugadores());
			
			for(int j = 0; j < jugadores.size();j++) {
				jugadores.get(j).setTurno(j);
				jugadores.get(j).setApostado(0.0);
				jugadores.get(j).setNombre(p.getJugadores().get(j).getLogin());
				jugadores.get(j).setDinero(p.getJugadores().get(j).getDinero());
				jugadores.get(j).setMano(new ArrayList<Carta>());
			}
			
			session.setAttribute(CargaAtributos.jugadores,jugadores);
			session.setAttribute(CargaAtributos.partida, p);
			session.setAttribute(CargaAtributos.juego, p.getJuego());
			
		}else {
			session.setAttribute(CargaAtributos.mensaje, "Faltan jugadores por confirmar ("+a+"/"+p.getMaxJugadores()+")."
					+ "Dales unos segundos para que esten listos.");
			return "saloon";
		}

		
		return "partida";
	}

	/**
	 * Operacion modicadora: Pasa de turno.
	 * @param id_partida: Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/apostar", method = RequestMethod.POST)
	@Transactional
	public String apostar(Double apostado,HttpSession session) {
		
		if(apostado <= CargaAtributos.u.getDinero() && apostado > 0) {
			
			//variables para jsp
			session.setAttribute(CargaAtributos.infoPartida, CargaAtributos.u.getLogin() + " apostó "+apostado+".");
			
			//variables para el juego
			session.setAttribute(CargaAtributos.cantidadApostada, apostado);
			session.setAttribute(CargaAtributos.movimiento, Movimientos.apostar);
			session.setAttribute(CargaAtributos.totalApostado, apostado+ (Integer)session.getAttribute(CargaAtributos.totalApostado));
			
			//modificamos variables locales
			CargaAtributos.u.getPartida().setApostado(CargaAtributos.u.getPartida().getApostado() + apostado);
			CargaAtributos.u.getPartida().setInfoPartida((String)session.getAttribute(CargaAtributos.infoPartida));
			CargaAtributos.u.setDinero(CargaAtributos.u.getDinero()-apostado);
			
			//modificamos variables bd
			entityManager.merge(CargaAtributos.u.getPartida());
			entityManager.merge(CargaAtributos.u);
			
		}else {
			session.setAttribute(CargaAtributos.mensaje, "No puedes apostar esa cantidad");
		}
		
		return "partida";
	}
	
	/**
	 * Operacion modicadora: Pasa de turno.
	 * @param id_partida: Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/pasarTurno", method = RequestMethod.POST)
	@Transactional
	public String pasarTurno(HttpSession session) {

		//variables para jsp
		session.setAttribute(CargaAtributos.infoPartida, CargaAtributos.u.getLogin() + " se plantó.");
		
		//variables para el juego
		session.setAttribute(CargaAtributos.movimiento, Movimientos.plantarse);
		
		//modificamos variables locales
		
		//modificamos variables bd
		CargaAtributos.u.getPartida().setInfoPartida((String)session.getAttribute(CargaAtributos.infoPartida));
		entityManager.merge(CargaAtributos.u.getPartida());
		
		return "partida";
	}
	
	/**
	 * Operacion modicadora: Pasa de turno.
	 * @param id_partida: Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/pedirCarta", method = RequestMethod.POST)
	@Transactional
	public String pedirCarta(HttpSession session) {

		//variables para jsp
		session.setAttribute(CargaAtributos.infoPartida, CargaAtributos.u.getLogin() + " pidio carta.");
		
		//variables para el juego
		session.setAttribute(CargaAtributos.movimiento, Movimientos.pedirCartas);
		
		//modificamos variables locales
		
		//modificamos variables bd
		CargaAtributos.u.getPartida().setInfoPartida((String)session.getAttribute(CargaAtributos.infoPartida));
		entityManager.merge(CargaAtributos.u.getPartida());
		
		return "partida";
	}
	
	/**
	 * Operacion modicadora: Saca a un usuario de la partida en la que esta actualmente
	 * Si la partida tiene menos de dos jugadores, esta se cerrara y sacara al otro usuario de dentro.
	 * @return Redirige a sallon
	 */
	@RequestMapping(value = "/salirDeLaPartida", method = RequestMethod.POST)
	@Transactional
	public String salirDeLaPartida(HttpSession session) {
			
		//modificar estadisticas usuario(si ya ha jugado)		
		if(CargaAtributos.u.getPartida().getJugadores().size() < 2) {	
			
			User u2 = CargaAtributos.u.getPartida().getJugadores().get(0);
			u2.setPartida(null);
			CargaAtributos.u.getPartida().getJugadores().remove(0);
			entityManager.merge(u2);
			entityManager.remove(entityManager.merge(CargaAtributos.u.getPartida()));
			session.setAttribute(CargaAtributos.mensaje,"La partida acabo.");
		}

		CargaAtributos.u.getPartida().getJugadores().remove(CargaAtributos.u);
		CargaAtributos.u.setPartida(null);
		entityManager.merge(CargaAtributos.u);
		return "saloon";
	}
	
	/**
	 * Operacion modicadora: Pasa de turno.
	 * @param id_partida: Id de la partida
	 * @return Void.
	 */
	@RequestMapping(value = "/actualizaManos", method = RequestMethod.POST)
	@Transactional
	public String actualizaManos(ArrayList<ArrayList<Carta>> manos) {
		
		for(int i = 0; i < manos.size();i++) {
			for(int j = 0; j < manos.get(i).size();j++) {			
				CargaAtributos.u.getPartida().getValoresManos().get(i).set(j, manos.get(i).get(j).getValor().ordinal());
				CargaAtributos.u.getPartida().getPalosManos().get(i).set(j, manos.get(i).get(j).getPalo().ordinal());
			}
		}			
		entityManager.merge(CargaAtributos.u.getPartida());
		return "partida";
	}
	
	@RequestMapping(value = "/actualizaMovimiento", method = RequestMethod.POST)
	@Transactional
	public String actualizaMovimiento(ArrayList<ArrayList<Carta>> manos) {
		
		for(int i = 0; i < manos.size();i++) {
			for(int j = 0; j < manos.get(i).size();j++) {			
				CargaAtributos.u.getPartida().getValoresManos().get(i).set(j, manos.get(i).get(j).getValor().ordinal());
				CargaAtributos.u.getPartida().getPalosManos().get(i).set(j, manos.get(i).get(j).getPalo().ordinal());
			}
		}			
		entityManager.merge(CargaAtributos.u.getPartida());
		return "partida";
	}
	
	@RequestMapping(value = "/actualizaInfoPartida", method = RequestMethod.GET)
	@Transactional
	public String actualizaInfoPartida(HttpSession session) {
		
		Partida p = (Partida)entityManager.createNamedQuery("getPartidaPorNombre")
								 .setParameter("nombreParam", CargaAtributos.u.getPartida().getNombre()).getSingleResult();
		
		session.setAttribute(CargaAtributos.infoPartida,p.getInfoPartida());
		session.setAttribute(CargaAtributos.turno,p.getTurno());
		return "partida";
	}
	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                            TIENDA                                 ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
    /** Operaciones:
     *  
     *  1º Comprar item
     */
		
	/**
	 * Operacion modicadora: Usuario compra item
	 * @param id_it: Id del item que quiere comprar
	 * @return Mensaje de error o redireccion en caso contrario
	 */
	@RequestMapping(value = "/comprarItem", method = RequestMethod.POST)
	@Transactional
	public String comprarItem(@RequestParam(required=true) long id_it,HttpSession session) {
		
		try {
			Item i = entityManager.find(Item.class, id_it);
			
			if(usuarioTieneItem(i,false)) {
				
				if(CargaAtributos.u.getDinero() >= i.getPrecio()) {
					
					CargaAtributos.u.setDinero(CargaAtributos.u.getDinero() - i.getPrecio());
					CargaAtributos.u.getPropiedades().add(i);
					i.getPropietarios().add(CargaAtributos.u);
					entityManager.merge(CargaAtributos.u);
					entityManager.merge(i);
					session.setAttribute(CargaAtributos.mensaje, "Compra realizada :)");
						
				}else {
					session.setAttribute(CargaAtributos.mensaje, "No tienes suficiente dinero.");
				}	
			}else {
				session.setAttribute(CargaAtributos.mensaje, "Ya tienes ese item.");
			}
		
		}catch(NoResultException e) {
			log.error(e);
			session.setAttribute(CargaAtributos.mensaje,"El item no existe");
		}
		CargaAtributos.cargaTienda(session, entityManager);
		return "tienda";
	}

    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                              FORO                                 ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
    /** Operaciones:
     *  
     *  1º Comentar en foro
     *  2º Borrar comentario
     */
	
	/**
	 * Operacion modicadora: Usuario comenta en el foro en un tema en concreto
	 * @param comentario: Mensaje del comentario
	 * @param tema: Tema del que comentara el usuario
	 * @return Redirrecion al foro.
	 */
	@RequestMapping(value = "/comentar", method = RequestMethod.POST)
	@Transactional
	public String Comentar(@RequestParam(required=true) String comentario,@RequestParam(required=true) Temas tema, 
						   HttpSession session) {
		
		ComentarioForo c = new ComentarioForo();

		c.setComentario(comentario);
		c.setTema(tema);
		c.setUsuario(CargaAtributos.u);
		c.setFecha(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		entityManager.persist(entityManager.merge(c));
		CargaAtributos.cargaForo(session, entityManager,tema);
		session.setAttribute(CargaAtributos.tema,tema);
		return "foro";
	}
	
	/**
	 * Operacion eliminadora: Usuario borra un comentario que hizo.
	 * @param id_c: Id del comentario
	 * @return Te redirige al foro.
	 */
	@RequestMapping(value = "/borrarComentario", method = RequestMethod.POST)
	@Transactional
	public String borrarComentario(@RequestParam(required=true) long id_c, HttpSession session) {
		
		ComentarioForo c = entityManager.find(ComentarioForo.class, id_c);
		entityManager.remove(entityManager.merge(c));
		CargaAtributos.cargaForo(session, entityManager,c.getTema());
		session.setAttribute(CargaAtributos.tema,c.getTema());
		return "foro";
	}
	
	/**
	 * Operacion observadora: Muestra el foro en funcion del tema.
	 * @param tema: Tema de conversacion del foro
	 * @return Te redirige al foro.
	 */
	@RequestMapping(value = "/foro", method = RequestMethod.GET)
	public String foro(Temas tema, HttpSession session) {
		session.setAttribute(CargaAtributos.mensaje, null);
		CargaAtributos.cargaForo(session, entityManager, tema);
		session.setAttribute(CargaAtributos.tema,tema);
		return "foro";
	}
	
	/*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                             RANKING                               ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
    /** Operaciones:
     *  
     *  1º Ver ranking por amigos,
     *  2º Por pais o
     *  3º Por global
     */
	
	/**
	 * Operacion observadora: Muestra el ranking en funcion de la busqueda seleccionada
	 * @param busqueda: Opcion de busqueda
	 * @return Te redirige al ranking
	 */
	@RequestMapping(value = "/verRanking", method = RequestMethod.GET)
	public String verRanking(String busqueda, HttpSession session) {
		
		switch(busqueda){
			case "amigos" : verRankingAmigos(session); break;
			case "global": verRankingGlobal(session); break;
			case "pais": verRankingPais(session); break;
		}
		
		session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
		return "ranking";
	}
	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                          AUXILIARES                               ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
		
	/**
	 * Operacion observadora: Descarga una foto de la bd
	 * @param id: Identificador del usuario al que le pertenece la fotp
	 * @return la imagen o error.
	 */
	@RequestMapping(value="/fotoPerfil/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public void fotoUsuario(@PathVariable("id") String id,HttpServletResponse response) {
		
	    File f = localData.getFile("user", id);
	    try (InputStream in = f.exists() ? 
		    	new BufferedInputStream(new FileInputStream(f)) :
		    	new BufferedInputStream(this.getClass().getClassLoader()
		    			.getResourceAsStream("unknown-user.jpg"))) {
	    	FileCopyUtils.copy(in, response.getOutputStream());
	    } catch (IOException ioe) {
	    	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	log.info("Se ha producido un error inesperado relacionado con la foto de perfil");
	    }
	}
	
	/**
	 * Opracion observadora: Carga una imagen de usuario via Ajax en la bd
	 * @param id: Id del usuario 
	 * @param photo: Foto a cargar
	 * @return Te redirige al perfil cargando mensaje de log en la sesion
	 */	
	@RequestMapping(value="/fotoPerfil/{id}", method=RequestMethod.POST)
    public String cargarArchivo(@RequestParam("photo") MultipartFile photo,@PathVariable("id") String id,
    		   					HttpSession session){

        if (photo.isEmpty()) { 
        	session.setAttribute(CargaAtributos.mensaje,"No has elegido ninguna foto a subir.");
        } else {
	        File f = localData.getFile("user", id);
	        try (BufferedOutputStream stream =
	                new BufferedOutputStream(
	                    new FileOutputStream(f))) {
	            stream.write(photo.getBytes());
	            session.setAttribute(CargaAtributos.mensaje,"Foto subida correctamente :)");
	        } catch (Exception e) {
	        	session.setAttribute(CargaAtributos.mensaje,"Error al subir la foto :(");
	        }
        }
        		
		return "perfil";
	}
	
	/**
	 * Operacion auxiliar: Busca si un usuario tiene un amigo en concreto. Si elimina esta a true, ademas lo elimina
	 * @param u2: Usuario a buscar 
	 * @param elimina: Modo eliminacion
	 * @return true si lo encontro, false en caso contrario.
	 */	
	private boolean usuarioTieneAmigo(User u2,boolean elimina) {

		int i = 0;
		while(CargaAtributos.u.getAmigos().size() > i && 
			  !CargaAtributos.u.getAmigos().get(i).getLogin().equals(u2.getLogin())) {i++;}
		
		if(elimina) CargaAtributos.u.getAmigos().remove(i);
		
		return i == CargaAtributos.u.getAmigos().size();
			
	}
	
	/**
	 * Operacion auxiliar: Busca si un usuario tiene un item en concreto. Si elimina esta a true, ademas lo elimina
	 * @param i: Item a buscar 
	 * @param elimina: Modo eliminacion
	 * @return true si lo encontro, false en caso contrario.
	 */	
	private boolean usuarioTieneItem(Item i,boolean elimina) {

		int inI = 0;
		int inU = 0;
		
		while(CargaAtributos.u.getPropiedades().size() > inI && 
			  !CargaAtributos.u.getPropiedades().get(inI).getNombre().equals(i.getNombre())) {inI++;}
		
		if(inI != CargaAtributos.u.getPropiedades().size()) {
			while(i.getPropietarios().size() > inI && 
				  i.getPropietarios().get(inU).getLogin().equals(CargaAtributos.u.getPropiedades().get(inI).getNombre()))
			{inU++;}
		}
		
		if(elimina) {
			CargaAtributos.u.getPropiedades().remove(inI);
			i.getPropietarios().remove(inU);
		}
		
		return inI == CargaAtributos.u.getPropiedades().size();
			
	}
	
	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void verRankingPais(HttpSession session) {
			
		List<User> usuarios = (List<User>)entityManager.createNamedQuery("getUsuarioNacion")
                       .setParameter("nacionParam", CargaAtributos.u.getNacion())
                       .getResultList();
		
		session.setAttribute("ranking",ordena(usuarios));
	}

	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * @return void
	 */
	private void verRankingAmigos(HttpSession session) {
		session.setAttribute("ranking",ordena(CargaAtributos.u.getAmigos()));
	}

	/**
	 * Operacion auxiliar: Carga en la sesion una lista de usuarios
	 * @return void
	 */
	private void verRankingGlobal(HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<User> usuarios = (List<User>)entityManager.createNamedQuery("getUsuarios")
        					   .getResultList();	
		session.setAttribute("ranking",ordena(usuarios));
	}
	
	/**
	 * Operacion auxiliar: Ordena una lista de usuarios mayor a menor en funcion de su dinero
	 * @param usuarios: Lista desordenada
	 * @return lista de usuarios ordenada
	 */	
	private List<User> ordena(List<User> usuarios){
		Collections.sort(usuarios, new Comparator<User>() {
			public int compare(User u1, User u2) {
				
				if(u1.getDinero() > u2.getDinero())
					return (int) u1.getDinero();
				
				return (int) u2.getDinero();
			}
		});
		return usuarios;
	}

}
