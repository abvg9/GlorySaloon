package es.ucm.fdi.iw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import es.uc.fdi.iw.common.enums.Juegos;

public class Partida {
	
	private long id;
	private List<User> jugadores;
	private int MaxJugadores;
	private Juegos juego;
	private String contraseña;

	// una partida tiene **muchos** jugadores
	@OneToMany(targetEntity=User.class)
	public List<User> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<User> jugadores) {
		this.jugadores = jugadores;
	}

	@Id
	@GeneratedValue
	@Column(unique=true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMaxJugadores() {
		return MaxJugadores;
	}

	public void setMaxJugadores(int maxJugadores) {
		MaxJugadores = maxJugadores;
	}

	public Juegos getJuego() {
		return juego;
	}

	public void setJuego(Juegos juego) {
		this.juego = juego;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
}
