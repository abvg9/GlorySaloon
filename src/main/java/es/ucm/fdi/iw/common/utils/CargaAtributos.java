package es.ucm.fdi.iw.common.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
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

	public static User u;
	
	//Antes de la partida
	public static final String saloon = "saloon";
	public static final String jugadores = "jugadores";
	public static final String juego = "juego";
	
	//Durante la partida(experimental)
	public static final String infoPartida = "inforPartida";
	public static final String partida = "partida";
	public static final String cantidadApostada = "cantidadApostada";
	public static final String movimiento = "movimiento";
	public static final String totalApostado = "totalApostado";
		
	public static final String manos = "manos";
	public static final String turno = "turno";
	public static final String ganadores = "ganadores";

	
	@SuppressWarnings("unchecked")
	public static void cargaForo(HttpSession session,EntityManager entityManager, Temas tema) {
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
	public static void cargaTienda(HttpSession session,EntityManager entityManager) {

		List<Item> tnd = (List<Item>)entityManager.createNamedQuery("getTienda")
			                 .getResultList();
		session.setAttribute(tienda, tnd);
	}
	
	@SuppressWarnings("unchecked")
	public static void cargaJugadores(HttpSession session,EntityManager entityManager) {

		List<Item> tnd = (List<Item>)entityManager.createNamedQuery("getTienda")
			                 .getResultList();
		session.setAttribute(tienda, tnd);
	}
	

}
