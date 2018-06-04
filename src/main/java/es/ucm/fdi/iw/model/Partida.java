package es.ucm.fdi.iw.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import es.ucm.fdi.iw.common.enums.Juegos;

@Entity
@NamedQueries({
@NamedQuery(name="getPartidaPorNombre",
query="select p from Partida p where p.nombre = :nombreParam"),
@NamedQuery(name="getPartidaPorJuego",
query="select p from Partida p where p.juego = :juegoParam")
})
public class Partida {
	
	private long id;
	private String nombre;
	private Set<User> jugadores;
	private int maxJugadores;
	private Juegos juego;
	private String pass;
	private boolean abierta;

	@OneToMany(targetEntity=User.class)
	public Set<User> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Set<User> jugadores) {
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
		return maxJugadores;
	}

	public void setMaxJugadores(int maxJugadores) {
		this.maxJugadores = maxJugadores;
	}

	public Juegos getJuego() {
		return juego;
	}

	public void setJuego(Juegos juego) {
		this.juego = juego;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isAbierta() {
		return abierta;
	}

	public void setAbierta(boolean abierta) {
		this.abierta = abierta;
	}

}
