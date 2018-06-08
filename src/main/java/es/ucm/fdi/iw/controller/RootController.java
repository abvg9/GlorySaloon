package es.ucm.fdi.iw.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import es.ucm.fdi.iw.common.utils.Utils;

@Controller	
public class RootController {
	
	@Autowired
	private EntityManager entityManager;
	
    @ModelAttribute
    public void addAttributes(Model model,HttpSession session) {	  	
        model.addAttribute("s", "/static");
        session.setAttribute(Utils.imagen,"/user/fotoPerfil/");
    }

	@GetMapping({"/", "/index"})
	public String root(HttpSession session) {
		return "home";
	}
	
	@GetMapping("/perfil")
	public String perfil(HttpSession session) {
		session.setAttribute(Utils.mensaje, null);
		return "perfil";
	}
	
	@GetMapping("/configuracion")
	public String configuracion(HttpSession session) {
		session.setAttribute(Utils.mensaje, null);
		return "configuracion";
	}
	
	@GetMapping("/adminConf")
	public String adminConf(HttpSession session) {
		session.setAttribute(Utils.mensaje, null);
		return "adminConf";
	}
	
	@GetMapping("/tienda")
	public String tienda(HttpSession session) {  
		session.setAttribute(Utils.mensaje, null);
		Utils.tienda(session, entityManager);
		return "tienda";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
		
	@GetMapping("/crearCuenta")
	public String crearCuenta(HttpSession session){
		session.setAttribute(Utils.mensaje, null);
		return "home";
	}
			
	@GetMapping("/ranking")
	public String ranking(HttpSession session) {
		session.setAttribute(Utils.mensaje, null);
		return "ranking";
	}
		
	@GetMapping("/reglas")
	public String reglas(HttpSession session) {
		session.setAttribute(Utils.mensaje, null);
		return "reglas";
	}
	
	@GetMapping("/saloon")
	public String saloon(HttpSession session,Model model, HttpServletRequest request) {
		session.setAttribute(Utils.mensaje, null);
		Utils.socket(model, request, "saloon", Utils.chatSocket);
		return "saloon";
	}
	
	@GetMapping("/partidaBlackJack")
	public String partida(HttpSession session, HttpServletRequest request,Model model) {
		session.setAttribute(Utils.mensaje, null);
		Utils.socket(model, request, "partidaBlackJack", Utils.partidaSocket);
		return "partidaBlackJack";
	}	
}
