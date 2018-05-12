package es.ucm.fdi.iw.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import es.ucm.fdi.iw.common.utils.CargaAtributos;

@Controller	
public class RootController {
	
	@Autowired
	private EntityManager entityManager;
	
    @ModelAttribute
    public void addAttributes(Model model,HttpSession session) {	  	
        model.addAttribute("s", "/static");
        session.setAttribute(CargaAtributos.user, CargaAtributos.u);
        session.setAttribute(CargaAtributos.imagen,"/user/fotoPerfil/");
    }

	@GetMapping({"/", "/index"})
	public String root() {
		return "login";
	}
	
	@GetMapping("/perfil")
	public String perfil(HttpSession session) {
		session.removeAttribute(CargaAtributos.mensaje);
		return "perfil";
	}
	
	@GetMapping("/tienda")
	public String tienda(HttpSession session) {  
		session.removeAttribute(CargaAtributos.mensaje);
		CargaAtributos.cargaTienda(session, entityManager);
		return "tienda";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
		
	@GetMapping("/crearCuenta")
	public String crearCuenta(HttpSession session){
		session.removeAttribute(CargaAtributos.mensaje);
		return "crearCuenta";
	}
			
	@GetMapping("/ranking")
	public String ranking(HttpSession session) {
		session.removeAttribute(CargaAtributos.mensaje);
		return "ranking";
	}
		
	@GetMapping("/reglas")
	public String reglas(HttpSession session) {
		session.removeAttribute(CargaAtributos.mensaje);
		return "reglas";
	}
	
	@GetMapping("/saloon")
	public String saloon(HttpSession session) {
		session.removeAttribute(CargaAtributos.mensaje);
		return "saloon";
	}
	
	@GetMapping("/partida")
	public String partida(HttpSession session) {
		session.removeAttribute(CargaAtributos.mensaje);
		return "partida";
	}
		
}
