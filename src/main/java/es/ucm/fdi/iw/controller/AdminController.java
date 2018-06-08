package es.ucm.fdi.iw.controller;

import java.io.File;
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
import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.common.enums.Nacionalidades;
import es.ucm.fdi.iw.common.utils.Utils;
import es.ucm.fdi.iw.model.Item;
import es.ucm.fdi.iw.model.User;

@Controller
@RequestMapping("admin")
public class AdminController {

	private static Logger log = Logger.getLogger(AdminController.class);

	@Autowired
	private LocalData localData;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EntityManager entityManager;

	/**
	 * Operacion creadora: Crea un nuevo usuario Igual que la del ususario salvo que
	 * en esta se puede elegir si la nueva cuenta es admin o usuario
	 * 
	 * @param nombre:
	 *            Nombre del usuario
	 * @param cont:
	 *            Contraseña del usuario
	 * @param email:
	 *            Email del usuario
	 * @param nacion:
	 *            Pais del usuario
	 * @param isAdmin:
	 *            Si el nuevo usuario es admin
	 * @return Te redirige a admin y carga en la sesion el mensaje.
	 */
	@RequestMapping(value = "/crearCuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(@RequestParam String nombre,@RequestParam String cont, 
			@RequestParam String email, @RequestParam Nacionalidades nacion, 
			HttpSession session, @RequestParam String isAdmin) {

		User us = Utils.userFromSession(entityManager, session);

		if ("".equals(nombre) || "".equals(cont) || "".equals(email) || "".equals(nacion.toString())) {

			session.setAttribute(Utils.mensaje, "Debes rellenar todos los campos.");
			return "adminConf";

		} else if (entityManager.createNamedQuery("noRepes").setParameter("loginParam", nombre)
				.setParameter("emailParam", email).getResultList().isEmpty()) {

			User u = new User();
			u.setLogin(nombre);
			u.setPassword(passwordEncoder.encode(cont));
			u.setDinero(1000);
			u.setEmail(email);
			u.setNacion(nacion);
			u.setRoles("on".equals(isAdmin) ? "ADMIN,USER" : "USER");

			entityManager.persist(u);
			session.setAttribute(Utils.mensaje, "La cuenta se creo correctamente :)");
			log.info("Admin " + us.getLogin() + "creo una cuenta al usuario " + nombre);

		}
		session.setAttribute(Utils.mensaje, "Nombre y/o email ya cogidos :(");
		return "redirect:/perfil";
	}

	/**
	 * Operacion modificadora: Elimina un item de la bd
	 * 
	 * @param id:
	 *            Id del item
	 * @return Te redirige a la tienda, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/borrarItem", method = RequestMethod.POST)
	@Transactional
	public String borrarItem(@RequestParam long id) {

		Item i = (Item) entityManager.find(Item.class, id);
		if (i != null) {

			if (i.getPropietarios().isEmpty()) {
				log.info("Item anadido a la BD");
				entityManager.remove(i);
			} else {
				log.error("No puedes borrar items si algun usuario los posee");
			}

		}

		return "redirect:/tienda";
	}

	/**
	 * Operacion modificadora: Anadde un item de la bd
	 * 
	 * @param precio:
	 *            Precio del item
	 * @param nombre:
	 *            Nombre del item
	 * @return Te redirige a la tienda, cargando en el sesion la respusta(correcta o
	 *         incorrecta ante la peticion)
	 */
	@RequestMapping(value = "/anadirItem", method = RequestMethod.POST)
	@Transactional
	public String anadirItem(@RequestParam int precio, @RequestParam String nombre, @RequestParam String descripcion) {

		if (entityManager.createNamedQuery("getItem").setParameter("nombreParam", nombre).getResultList().isEmpty()) {

			Item i = new Item();
			i.setNombre(nombre);
			i.setPrecio(precio);
			i.setDescripcion(descripcion);
			entityManager.persist(i);
			log.info("Item anadido a la BD");

		}

		return "redirect:/tienda";
	}

	/**
	 * Operacion eliminadora: Elimina la cuenta de un usuario.
	 * 
	 * @param nombre:
	 *            Nombre del usuario al que se le elimina su cuenta
	 * @return Te redirige al login tras borrar tu cuenta o al perfil en caso de que
	 *         borres la cuenta de otro usuario que no seas tu (Esto último solo lo
	 *         pueden los administradores).
	 */
	@RequestMapping(value = "/eliminarCuenta", method = RequestMethod.POST)
	@Transactional
	public String eliminarCuenta(HttpSession session, @RequestParam String nombre) {

		User uEliminado = null;
		boolean iguales = false;
		User uEliminador = Utils.userFromSession(entityManager, session);

		if (nombre != null && !nombre.equals(uEliminador.getLogin())) {

			uEliminado = Utils.usuarioExiste(entityManager, nombre, session, log);
			if (uEliminado == null) {
				return "perfil";
			}

		} else {

			uEliminado = uEliminador;
			iguales = true;
		}

		Utils.borraUsuario(uEliminado, entityManager);
		File f = localData.getFile("user", String.valueOf(uEliminado.getId()));
		f.delete();
		log.info(uEliminado.getLogin() + " elimino su cuenta.");

		if (iguales) {
			Utils.eliminaVariablesSession(session);
		}

		if (iguales) {
			return "login";
		}

		return "redirect:/perfil";
	}
}
