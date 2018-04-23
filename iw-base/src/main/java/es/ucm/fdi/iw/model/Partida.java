package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import es.uc.fdi.iw.common.enums.Juegos;

@Entity
public class Partida {
	
	private long id;
	private List<User> jugadores;
	private int MaxJugadores;
	private Juegos juego;
	private String pass;

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
		return pass;
	}

	public void setContraseña(String contraseña) {
		this.pass = contraseña;
	}
	
}
