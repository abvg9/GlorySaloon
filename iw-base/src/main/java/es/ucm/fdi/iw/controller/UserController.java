package es.ucm.fdi.iw.controller;

import java.awt.image.BufferedImage;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import es.uc.fdi.iw.common.enums.Nacionalidades;
import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.Item;
import es.ucm.fdi.iw.model.User;

@Controller	
@RequestMapping("user")
public class UserController {
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// Punto de entrada a la base de datos.
	@Autowired
	private EntityManager entityManager;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("s", "../static");
    }
    
	@RequestMapping(value = "/crear_Cuenta", method = RequestMethod.POST)
	@Transactional
	public String crearCuenta(
			@RequestParam(required=true) String login, 
			@RequestParam(required=true) String password,
			@RequestParam(required=true) String email,
			@RequestParam(required=true) Nacionalidades nacion,
			@RequestParam(required=false) BufferedImage imagen,
			@RequestParam(required=false) String isAdmin, Model m) {
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
		entityManager.persist(u);
		
		return "user";
	}
	
	@RequestMapping(value = "/entrar", method = RequestMethod.POST)
	@Transactional
	public String Entrar(
			@RequestParam(required=true) String login, 
			@RequestParam(required=true) String password,
			@RequestParam(required=false) String isAdmin,Model m) {
			
		User u = new User();
		u.setLogin(login);
		u.setPassword(passwordEncoder.encode(password));
		u.setRoles("on".equals(isAdmin) ? "ADMIN,USER" : "USER");
		/* Hay que hacer un createNamedQuery en user y usarlo aqui, mira apuntes!*/
		m.addAttribute("users", entityManager
				.createNamedQuery
				("select u from User u where password ='"+password+"'"+
				"AND login ='"+login+"'").getResultList());
		
		return "user";
	}
	
	@RequestMapping(value = "/compra", method = RequestMethod.POST)
	@Transactional
	public String ComprarItem(
			@RequestParam(required=true) String login,
			@RequestParam(required=true) String idIt, Model m) {
			
			User u = entityManager.getReference(User.class, login);
			Item i = entityManager.getReference(Item.class, idIt);
			
			if(i.getPrecio() <= u.getDinero()) {
				if(!u.getItems().contains(i)) {
					u.setDinero(u.getDinero()-i.getPrecio());
					u.getItems().add(i);
					entityManager.persist(u);	
					entityManager.flush();
				}else {
					// Ya tenia ese objeto
				}
			}else {
				//No tiene dinero
			}

		
		entityManager.persist(u);
		
		return "user";
	}

}
