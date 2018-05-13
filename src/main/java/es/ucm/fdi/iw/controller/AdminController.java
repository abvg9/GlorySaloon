package es.ucm.fdi.iw.controller;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import es.ucm.fdi.iw.common.enums.Nacionalidades;
import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.model.ComentarioForo;
import es.ucm.fdi.iw.model.Item;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;

@Controller	
@RequestMapping("admin")
public class AdminController {
	
	private static Logger log = Logger.getLogger(AdminController.class);
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EntityManager entityManager;
  
	/**
	 * Operacion creadora: Crea un nuevo usuario
	 * Igual que la del ususario salvo que en esta se puede elegir si la nueva cuenta es admin o usuario
	 * @param nombre: Nombre del usuario 
	 * @param cont: Contraseña del usuario
	 * @param email: Email del usuario
	 * @param nacion: Pais del usuario
	 * @param isAdmin: Si el nuevo usuario es admin
	 * @return Te redirige a admin y carga en la sesion el mensaje.
	 */
    @RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(@RequestParam(required=true) String nombre,@RequestParam(required=true) String cont,
							  @RequestParam(required=true) String email,@RequestParam(required=true) Nacionalidades nacion,
							  HttpSession session,String isAdmin) {
		
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
			u.setRoles("on".equals(isAdmin) ? "ADMIN,USER" : "USER");
			u.setPperdidas(0);
			u.setPjugadas(0);
			u.setDperdido(0);
			u.setDganado(0);
			u.setAmigos(new ArrayList<User>());
			u.setComentarios(new ArrayList<ComentarioForo>());
			u.setPropiedades(new ArrayList<Item>());
			u.setPartida(new Partida());
				
			entityManager.persist(u);
			session.setAttribute(CargaAtributos.mensaje,"La cuenta se creo correctamente :)");
			log.info("Admin " +CargaAtributos.u.getLogin()+
			"creo una cuenta al usuario "+nombre);
			
						 
		}
		session.setAttribute(CargaAtributos.mensaje,"Nombre y/o email ya cogidos :(");
		return "perfil";
	}
    
	/**
	 * Operacion modificadora: Elimina un item de la bd
	 * @param id: Id del item
	 * @return Te redirige a la tienda, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/borrarItem", method = RequestMethod.POST)
	@Transactional
	public String borrarItem(@RequestParam(required=true) long id) {
		
		Item i = (Item)entityManager.find(Item.class,id);
		if( i != null) {
			
			if(i.getPropietarios().isEmpty()) {
				log.info("Item añadido a la BD");
				entityManager.remove(i);
			}else {
				log.error("No puedes borrar items si algun usuario los posee");
			}
		
		}
			
		return "redirect:/tienda";
	}
	
	/**
	 * Operacion modificadora: Añadde un item de la bd
	 * @param precio: Precio del item
	 * @param nombre: Nombre del item
	 * @return Te redirige a la tienda, cargando en el sesion la respusta(correcta o incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/añadirItem", method = RequestMethod.POST)
	@Transactional
	public String añadirItem(@RequestParam(required=true) double precio, @RequestParam(required=true) String nombre,
			 				 @RequestParam(required=true) String descripcion) {
				
	    if(entityManager.createNamedQuery("getItem")
		   .setParameter("nombreParam", nombre)
		   .getResultList().isEmpty()) {
			
	    	Item i = new Item();
	    	i.setNombre(nombre);
	    	i.setPrecio(precio);
	    	i.setDescripcion(descripcion);
	    	i.setPropietarios(new ArrayList<User>());	
			entityManager.persist(i);
			log.info("Item añadido a la BD");
			
		}
				
		return "redirect:/tienda";
	}

}
