package es.ucm.fdi.iw.model;

import java.awt.image.BufferedImage;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import es.uc.fdi.iw.common.enums.Nacionalidades;

@Entity
public class User {
	private long id;
	private String login; //nombre usuario
	private String password;
	private String roles; // split by , to separate roles
	private byte enabled;
	private double dinero;
	private String email;
	private Nacionalidades nacion;
	BufferedImage imagen;
	private List<User> amigos;
	private List<ComentarioForo> comentFor;
	private int pGanadas; //partidas ganadas
	private int pPerdidas;
	private int pJugadas;
	private double Dperdido; //dinero perdido
	private double Dganado;
	private Partida partida;
	private List<Item> items;
	
	@Id
	@GeneratedValue
	@Column(unique=true)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}	

	//Nombre.
	@Column(unique=true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public byte getEnabled() {
		return enabled;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}

	public double getDinero() {
		return dinero;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}

	@Column(unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Nacionalidades getNacion() {
		return nacion;
	}

	public void setNacion(Nacionalidades nacion) {
		this.nacion = nacion;
	}
	
	public BufferedImage getImagen() {
		return imagen;
	}

	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}

	@ManyToMany(targetEntity=User.class)
	public List<User> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<User> amigos) {
		this.amigos = amigos;
	}
	
	public int getpGanadas() {
		return pGanadas;
	}
	
	public void setpGanadas(int pGanadas) {
		this.pGanadas = pGanadas;
	}
	
	public int getpPerdidas() {
		return pPerdidas;
	}
	
	public void setpPerdidas(int pPerdidas) {
		this.pPerdidas = pPerdidas;
	}
	
	public int getpJugadas() {
		return pJugadas;
	}
	
	public void setpJugadas(int pJugadas) {
		this.pJugadas = pJugadas;
	}
	public double getDperdido() {
		return Dperdido;
	}
	
	public void setDperdido(double dperdido) {
		Dperdido = dperdido;
	}
	
	public double getDganado() {
		return Dganado;
	}
	
	public void setDganado(double dganado) {
		Dganado = dganado;
	}
	
	// un usuario tiene **muchos** comentarios posteados en el foro
	@OneToMany(targetEntity=ComentarioForo.class)
	@JoinColumn(name="user")
	public List<ComentarioForo> getComentFor() {
		return comentFor;
	}

	public void setComentFor(List<ComentarioForo> comentFor) {
		this.comentFor = comentFor;
	}

	// un usuario esta en una partida
	@ManyToOne(targetEntity= Partida.class)
	@JoinColumn(name="id")
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
