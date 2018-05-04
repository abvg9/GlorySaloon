package es.ucm.fdi.iw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import es.uc.fdi.iw.common.enums.Juegos;
import es.uc.fdi.iw.common.enums.Nacionalidades;
import es.uc.fdi.iw.common.enums.Temas;
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
    public void addAttributes(Model model) {
        model.addAttribute("s", "../static");
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
     *  2º Loguearse?
     */
      
	@RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(
			@RequestParam(required=true) String login, 
			@RequestParam(required=true) String password,
			@RequestParam(required=true) String email,
			@RequestParam(required=true) Nacionalidades nacion,
			@RequestParam(required=false) String isAdmin, Model m) {
			
		/*El usuario no tiene un nombre o un email igual al de otro usuario*/
		if(entityManager.createNamedQuery("noRepes")
			    .setParameter("loginParam", login)
			    .setParameter("emailParam", email)
			    .getResultList().isEmpty()) {
			User u = new User();
			u.setLogin(login);
			u.setPassword(passwordEncoder.encode(password));
			u.setRoles("on".equals(isAdmin) ? "ADMIN,USER" : "USER");
			u.setEmail(email);
			u.setPperdidas(0);
			u.setDganado(0);
			u.setDinero(0);
			u.setDperdido(0);
			u.setNacion(nacion);
			u.setPjugadas(0);

			try {
				entityManager.persist(u);
				entityManager.flush();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			
			//el campo login o email ya estan cogidos (return "redirect:/camposMalMetidos";)
		}
		
		return "redirect:/admin";
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
     */
      
	/**
	 * Operacion modificadora: Cambia los datos personales del usuario. Solo modifica aquellos campos que sean rellenados.
	 * Se pide la contraseña actual del usuario por mayor seguridad.
	 * @param nombre: Nuevo nombre del usuario 
	 * @param contNueva: Nueva contraseña del usuario
	 * @param contActual: Nueva contraseña actual del usuario
	 * @param email: Nuevo email del usuario
	 * @param nacion: Nuevo pais del usuario
	 * @param foto: Nueva foto de perfil del usuario
	 * @param id: Id del usuario
	 * @return Respuesta textual que indica el éxito, código de estado
	 */
	@RequestMapping(value = "/modificarPerfil", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody String modificarPerfil(String nombre,
								               String contNueva,
		          @RequestParam(required=true) String contActual,
								               String email, 
								               Nacionalidades nacion,
								               HttpServletResponse response,
								               MultipartFile foto,
						   @PathVariable("id") String id){
				
		User u = entityManager.find(User.class, Integer.parseInt(id));
	
		if(passwordEncoder.matches(contActual, u.getPassword())) {
			
			int modificacion = 0;

			if(!nombre.isEmpty()) {u.setLogin(nombre); modificacion++;}
			
			if(!email.isEmpty()) {u.setEmail(email); modificacion++;}
			
			if(!contNueva.isEmpty()) {u.setPassword(passwordEncoder.encode(contActual));; modificacion++;}
			
			if(!nacion.toString().isEmpty()) {u.setNacion(nacion); modificacion++;}
			
			if(!foto.isEmpty()) {
				
				if(!subirFoto(foto,id)) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return "Fallo al modificar tu foto de perfil.";
				}
				modificacion++;
			}
			
			entityManager.persist(u);
			entityManager.flush();
			
			if(modificacion == 0) {response.setStatus(HttpServletResponse.SC_ACCEPTED);return "No has realizado ninguna modificacion.";}
			
			if(modificacion == 1) {response.setStatus(HttpServletResponse.SC_ACCEPTED);return "Modificacion realizada correctamente.";}
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return "Modificaciones realizadas correctamente.";

		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return "Contraseña incorrecta.";
			
	}

	/**
	 * Operacion modificadora: Añade un nuevo amigo a la lista de amigos del usuario. Revisa si el amigo existe en la BD y si no lo tenia anteriormente.
	 * Esta operacion no añade en la lista del amigo el usuario en cuestion.
	 * @param nombreA: Nombre del amigo
	 * @param id: Id del usuario
	 * @return Respuesta textual que indica el éxito, código de estado
	 */
	@RequestMapping(value = "/añadirAmigo", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody String añadirAmigo(
		                                    HttpServletResponse response,
               @RequestParam(required=true) String nombreA, 
                        @PathVariable("id") String id) {
		
		
		User u2 = (User)entityManager.createNamedQuery("getUsuario")
			    .setParameter("loginParam", nombreA)
			    .getSingleResult();
		
		if(u2.equals(null)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "No existe ningun usuario llamado '"+nombreA+"'.";
		}
		
		User u1 = entityManager.find(User.class, Integer.parseInt(id));
		
		if(!u1.getAmigos().contains(u2)) {
			
			u1.getAmigos().add(u2);
			entityManager.persist(u1);
			entityManager.flush();
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return "Amigo añadido.";
		}
		
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return "Ya figura en tu lista de amigos.";
	}
	
	/**
	 * Operacion observadora: Ver el perfil de un usuario dado su nombre. Este metodo para ver el propio perfil y el de sus amigos.
	 * @param nombre: Nombre del usuario
	 * @return Void. Añade al modelo el usuario para poder acceder a este desde codigo no java.
	 */
	@RequestMapping(value = "/pefil", method = RequestMethod.GET)
	public void pefil(
		   @RequestParam(required=true) String nombre, Model m) {
		
		User u = (User)entityManager.createNamedQuery("getUsuario")
			           .setParameter("loginParam", nombre)
			           .getSingleResult();

		m.addAttribute("usuario", u);
	}
	
	/**
	 * Operacion modificadora: Dado un nombre de usuario, lo elimina de la BD.
	 * @param nombre: Nombre del usuario
	 * @return Devulve mensaje de informacion sobre la operacion.
	 */
	@RequestMapping(value = "/eliminarCuenta", method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody String eliminarCuenta(
			                                   HttpServletResponse response,
		          @RequestParam(required=true) String nombre) {
		
		User u = (User)entityManager.createNamedQuery("getUsuario")
			     .setParameter("loginParam", nombre)
			     .getSingleResult();
		
		if(!u.equals(null)) {
			entityManager.remove(u);
			entityManager.flush();	
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return "Cuenta eliminada.";
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return "Usuario desconocido.";
	}
	
	/**
	 * Operacion modificadora: Eliminar amigo de la lista de amigos del usuario.
	 * @param nombreAmigo: Nombre del amigo del usuario
	 * @param nombre: Nombre del usuario
	 * @return Void.
	 */
	@RequestMapping(value = "/eliminarAmigo", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody String eliminarAmigo(
			                                  HttpServletResponse response,
			     @RequestParam(required=true) String nombreAmigo,
		         @RequestParam(required=true) String nombre) {
		
		User u1 = (User)entityManager.createNamedQuery("getUsuario")
			           .setParameter("loginParam", nombre)
			           .getSingleResult();
		User u2 = (User)entityManager.createNamedQuery("getUsuario")
		           .setParameter("loginParam", nombreAmigo)
		           .getSingleResult();
		
		u1.getAmigos().remove(u2);
		entityManager.persist(u1);
		entityManager.flush();
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return "Amigo eliminado.";
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
     *  4º Actualizar estadisticas,dinero,etc ...
     *  
     *  ----------------------------
     *  --- Dentro de la partida ---
     *  ----------------------------
     *  
     *  5º Realizar movimiento en partida
     *  6º Salirse de la partida
     *  7º Pasar turno
     *  
     *  
     */
	
	/**
	 * Operacion creadora: Crea una nueva partida.
	 * @param juego: Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores: Maximo numero de jugadores
	 * @param nombrePar: Nombre de la partida
	 * @param nombreUs: Nombre del usuario
	 * @return Info de si se ha creado correctamente la partida o no.
	 */
	@RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody String crearPartida(
			    @RequestParam(required=true) Juegos juego,
			    @RequestParam(required=true) int maxJugadores, 
			                                 String contraseña,
			    @RequestParam(required=true) String nombrePar,
			                                 HttpServletResponse response,
			    @RequestParam(required=true) String nombreUs) {
		
		User u = (User)entityManager.createNamedQuery("getUsuario")
		          .setParameter("loginParam", nombreUs)
		          .getSingleResult();
		
		if(!u.getPartida().equals(null)) {
			
			Partida p = new Partida();
			p.setApostado(0);
			p.setJuego(juego);
			p.setMaxJugadores(maxJugadores);
			p.setNombre(nombrePar);
			
			if(contraseña.isEmpty()) {
				p.setPass("no");
			}else {
				p.setPass(contraseña);
			}
			
			p.getJugadores().add(u);
			u.setPartida(p);
	
			u.setPartida(p);
			p.getJugadores().add(u);
			entityManager.persist(p);
			entityManager.persist(u);
			entityManager.flush();
			
			return "redirect:/partida";
		}
		
		
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return "Ya estas dentro de una partida.";
	}
	
	/**
	 * Operacion creadora: Crea una nueva partida.
	 * @param juego: Tipo de juego al que se va a jugar en la partida
	 * @param maxJugadores: Maximo numero de jugadores
	 * @param nombrePar: Nombre de la partida
	 * @param nombreUs: Nombre del usuario
	 * @return Info de si se ha creado correctamente la partida o no.
	 */
	@RequestMapping(value = "/unirsePartida", method = RequestMethod.POST)
	@Transactional
	public String unirsePartida(
			@RequestParam(required=true) long id_p, 
			@RequestParam(required=true) long id_us,
			@RequestParam(required=true) String pass) {
		
		User u = entityManager.find(User.class, id_us);
		Partida p = entityManager.find(Partida.class, id_p);
		
		if(!p.getJugadores().contains(u)) {
			
			if(p.getMaxJugadores() > p.getJugadores().size()+1) {
				
				if(p.getPass().equalsIgnoreCase("no") || p.getPass().equals(pass)) {
					u.setPartida(p);
					p.getJugadores().add(u);
					entityManager.persist(p);
					entityManager.persist(u);
					entityManager.flush();
				}else {
					// No ha metido la contraseña bien (return "redirect:/ERROR";)
				}
					
			}else {
				// No hay hueco en la partida (return "redirect:/ERROR";)
			}	
		}else {
			// el jugador ya esta dentro de la partida (return "redirect:/ERROR";)
		}
		
		return "redirect:/sallon";
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
     *  2º Vender item
     *  
     */
	
	@RequestMapping(value = "/comparItem", method = RequestMethod.POST)
	@Transactional
	public String comparItem(
			@RequestParam(required=true) long id_us, 
			@RequestParam(required=true) long id_it, Model m) {
		
		User u = entityManager.find(User.class, id_us);
		Item i = entityManager.find(Item.class, id_it);
		
		if(u.getPropiedades().contains(i)) {
			
			if(u.getDinero() >= i.getPrecio()) {
				
				u.setDinero(u.getDinero() - i.getPrecio());
				u.getPropiedades().add(i);
				i.getPropietarios().add(u);
				entityManager.persist(i);
				entityManager.persist(u);
				entityManager.flush();
					
			}else {
				// No tiene dinero (return "redirect:/ERROR";)
			}	
		}else {
			// El jugador ya tiene esa propiedad (return "redirect:/ERROR";)
		}
		
		return "redirect:/foro";
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
     *  2º Ver foro por tema
     *  
     */
	
	@RequestMapping(value = "/comentar", method = RequestMethod.POST)
	@Transactional
	public String Comentar(
			@RequestParam(required=true) User login, 
			@RequestParam(required=true) String comentario,
			@RequestParam(required=true) Temas tema, Model m) {
		
		ComentarioForo c = new ComentarioForo();
		c.setComentario(comentario);
		c.setTema(tema);
		c.setUsuario(login);
		c.setFecha(new Date());
		entityManager.persist(c);
		entityManager.flush();
		

		return "redirect:/foro";
	}
	
	@RequestMapping(value = "/borrarComentar", method = RequestMethod.DELETE)
	@Transactional
	public String BorrarComentario(
			@RequestParam(required=true) long id_c, Model m) {
		
		ComentarioForo c = entityManager.find(ComentarioForo.class, id_c);
		entityManager.remove(c);
		entityManager.flush();

		return "redirect:/foro";
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
     *  1º Ver ranking por amigos, pais o global
     *  
     */
	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                             REGLAS                                ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
    /** Operaciones:
     *  
     *  1º Ver reglas por juego
     *  
     */
	
    /*#######################################################################
    #########################################################################
    ###                                                                   ###
    ###                          AUXILIARES                               ###
    ###                                                                   ###
    #########################################################################
    #######################################################################*/
	
	/**
	 * Sube una foto de un usuario, via Ajax.
	 * @param foto: Foto del usuario
	 * @param id: Id del usuario 
	 * @return True si se ha logrado subir la foto correctamente, false en caso contrario.
	 */
	private boolean subirFoto(MultipartFile foto,String id) {
		
        File f = localData.getFile("user", id);
        try (BufferedOutputStream stream =
                new BufferedOutputStream(
                    new FileOutputStream(f))) {
            stream.write(foto.getBytes());
        } catch (Exception e) {
	    	return false;
        }
        
        return true;
	}
}
