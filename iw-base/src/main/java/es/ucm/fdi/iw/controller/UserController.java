package es.ucm.fdi.iw.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import es.uc.fdi.iw.common.enums.Nacionalidades;
import es.uc.fdi.iw.common.enums.Temas;
import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.ComentarioForo;
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
			entityManager.persist(u);
			entityManager.flush();
		}else {
			
			//el campo login o email ya estan cogidos (return "redirect:/camposMalMetidos";)
		}
		
		return "redirect:/admin";
	}
		
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verAmigos", method = RequestMethod.GET)
	@ResponseBody
	public List<User> verAmigo_s(
			@RequestParam(required=true) String login) {
				
		return (List<User>)entityManager.createNamedQuery("noRepes")
			    .setParameter("loginParam", login)
			    .getResultList();
	}

}
