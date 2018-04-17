package es.ucm.fdi.iw.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import es.uc.fdi.iw.common.enums.Juegos;

public class Reglas {
	
	private Juegos juego;
	private String reglas;
	private long id;
	private String descripcion;
		
	@Id
	@GeneratedValue
	@Column(unique=true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
		
	public String getReglas() {
		return reglas;
	}
	
	public void setReglas(String reglas) {
		this.reglas = reglas;
	}
	
	@Column(unique=true)
	public Juegos getJuego() {
		return juego;
	}

	public void setJuego(Juegos juego) {
		this.juego = juego;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
