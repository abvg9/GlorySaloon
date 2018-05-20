package es.ucm.fdi.iw.common.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import es.ucm.fdi.iw.common.enums.Temas;
import es.ucm.fdi.iw.model.ComentarioForo;
import es.ucm.fdi.iw.model.Item;
import es.ucm.fdi.iw.model.User;

public final class CargaAtributos {
	
	public static final String mensaje = "mensaje";
	public static final String foro = "foro";
	public static final String tienda = "tienda";
	public static final String imagen = "imagen";
	public static final String user = "user";
	public static final String tema = "tema";
	public static final String saloon = "saloon";
	public static final String chatSocket = "chatsocket";
	public static final String partidaSocket = "partidaSocket";
	private static final String endpoint = "endpoint";
	
	
	@SuppressWarnings("unchecked")
	public static void foro(HttpSession session,EntityManager entityManager, Temas tema) {
		List<ComentarioForo> comentarios;
		comentarios = (List<ComentarioForo>)entityManager.createNamedQuery("getForo")
			           .setParameter("temaParam", tema)
			           .getResultList();
		Collections.sort(comentarios, new Comparator<ComentarioForo>() {
			public int compare(ComentarioForo c1, ComentarioForo c2) {
				return c1.getFecha().compareTo(c2.getFecha());
			}
		});
		session.setAttribute(foro,comentarios);
	}
	
	@SuppressWarnings("unchecked")
	public static void tienda(HttpSession session,EntityManager entityManager) {

		List<Item> tnd = (List<Item>)entityManager.createNamedQuery("getTienda")
			                 .getResultList();
		session.setAttribute(tienda, tnd);
	}
	
	@SuppressWarnings("unchecked")
	public static void jugadores(HttpSession session,EntityManager entityManager) {

		List<Item> tnd = (List<Item>)entityManager.createNamedQuery("getTienda")
			                 .getResultList();
		session.setAttribute(tienda, tnd);
	}
	
	public static void socket(Model model,HttpServletRequest request,String replace, String by) {

		model.addAttribute(endpoint, request.getRequestURL().toString()
				.replaceFirst("[^:]*", "ws")
				.replace(replace, by));
	}
	
	public static boolean cargaUsuario(String username, String password, EntityManager entityManager, HttpSession session) {
		
		if(username != null && password != null) {
			
			if(!"".equals(username) && !"".equals(password)){
				try {
					User u = (User)entityManager.createNamedQuery("getUsuario").setParameter("loginParam", username).getSingleResult();
					session.setAttribute(CargaAtributos.user, u);
					return true;
				} catch (Exception e) {
					session.setAttribute(CargaAtributos.mensaje,"Nombre y/o contraseña incorrectos.");
		    	}
			}
			
		}
		return false;
	}

}
