package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
	private List<User> jugadores;
	private int MaxJugadores;
	private Juegos juego;
	private String pass;
	private boolean abierta;
	
	//experimental
	private String infoPartida;
	private double TotalApostado;
	private ArrayList<ArrayList<Integer>> palosManos;
	private ArrayList<ArrayList<Integer>> valoresManos;
	private int turno;

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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@DecimalMin("0.00")
	public double getApostado() {
		return TotalApostado;
	}

	public void setApostado(double TotalApostado) {
		this.TotalApostado = TotalApostado;
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

	public boolean isAbierta() {
		return abierta;
	}

	public void setAbierta(boolean abierta) {
		this.abierta = abierta;
	}

	public ArrayList<ArrayList<Integer>> getPalosManos() {
		return palosManos;
	}

	public void setPalosManos(ArrayList<ArrayList<Integer>> palosManos) {
		this.palosManos = palosManos;
	}

	public ArrayList<ArrayList<Integer>> getValoresManos() {
		return valoresManos;
	}

	public void setValoresManos(ArrayList<ArrayList<Integer>> valoresManos) {
		this.valoresManos = valoresManos;
	}

	public String getInfoPartida() {
		return infoPartida;
	}

	public void setInfoPartida(String infoPartida) {
		this.infoPartida = infoPartida;
	}

}
