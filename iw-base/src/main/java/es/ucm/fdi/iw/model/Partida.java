package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
<<<<<<< HEAD
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
=======

>>>>>>> 84f0d72a8272ea9592a3403962fa629c43a6aa21
import es.uc.fdi.iw.common.enums.Juegos;

@Entity
public class Partida {
	
	private long id;
	private String nombre;
	private List<User> jugadores;
	private int MaxJugadores;
	private Juegos juego;
	private String pass;
	private double apostado;
	private int turno;

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

	@DecimalMin("0.00")
	public double getApostado() {
		return apostado;
	}

	public void setApostado(double apostado) {
		this.apostado = apostado;
	}
	
	@Min(0)
	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	


}
