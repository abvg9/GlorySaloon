package es.ucm.fdi.iw.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import es.ucm.fdi.iw.common.utils.CargaAtributos;

@Controller	
//@RequestMapping("root")
public class RootController {
	
	@Autowired
	private EntityManager entityManager;
	
    @ModelAttribute
    public void addAttributes(Model model,HttpSession session) {	  	
        model.addAttribute("s", "/static");
        session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
    }

	@GetMapping({"/", "/index"})
	public String root() {
		return "home";
	}
	
	@GetMapping("/perfil")
	public String perfil(HttpSession session) {
		session.setAttribute(CargaAtributos.mensaje, null);
		return "perfil";
	}
	
	@GetMapping("/tienda")
	public String tienda(HttpSession session) {  
		session.setAttribute(CargaAtributos.mensaje, null);
		CargaAtributos.tienda(session, entityManager);
		return "tienda";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
		
	@GetMapping("/crearCuenta")
	public String crearCuenta(HttpSession session){
		session.setAttribute(CargaAtributos.mensaje, null);
		return "crearCuenta";
	}
			
	@GetMapping("/ranking")
	public String ranking(HttpSession session) {
		session.setAttribute(CargaAtributos.mensaje, null);
		return "ranking";
	}
		
	@GetMapping("/reglas")
	public String reglas(HttpSession session) {
		session.setAttribute(CargaAtributos.mensaje, null);
		return "reglas";
	}
	
	@GetMapping("/saloon")
	public String saloon(HttpSession session,Model model, HttpServletRequest request) {
		session.setAttribute(CargaAtributos.mensaje, null);
		CargaAtributos.socket(model, request, "saloon", "chatsocket");
		return "saloon";
	}
	
	@GetMapping("/partida/{id}")
	public String partida(HttpSession session, HttpServletRequest request,Model model) {
		session.setAttribute(CargaAtributos.mensaje, null);
		CargaAtributos.socket(model, request, "partida", "partidasocket");
		return "partida";
	}	
	
}
