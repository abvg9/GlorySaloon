package es.ucm.fdi.iw.controller;

import java.security.Principal;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller	
public class RootController {

	private static Logger log = Logger.getLogger(RootController.class);
	  
	@Autowired
	private EntityManager entityManager;
	
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("s", "/static");
    }

	@GetMapping({"/", "/index"})
	public String root(Model model, Principal principal) {
		log.info(principal.getName() + " de tipo " + principal.getClass());
		return "home";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
	
	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}
	
	@GetMapping("/compras")
	public String compras() {
		return "compras";
	}
	
	@GetMapping("/foro")
	public String foro() {
		return "foro";
	}
	
	@GetMapping("/ranking")
	public String ranking() {
		return "ranking";
	}
	
	@GetMapping("/reglas")
	public String reglas() {
		return "reglas";
	}
	
	@GetMapping("/saloon")
	public String saloon() {
		return "saloon";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
}
