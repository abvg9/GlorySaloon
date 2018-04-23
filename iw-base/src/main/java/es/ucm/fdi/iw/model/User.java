package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private List<User> amigos;
    private List<ComentarioForo> comentarios;
	private int Pganadas; //partidas ganadas
	private int Pperdidas;
	private int Pjugadas;
	private double Dperdido; //dinero perdido
	private double Dganado;
	private Partida partida;	
	private List<Item> items;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}	

	//Nombre.
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

	@ManyToMany(targetEntity=User.class,mappedBy="login")
	public List<User> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<User> amigos) {
		this.amigos = amigos;
	}
	
	// un usuario tiene **muchos** comentarios posteados en el foro
	@OneToMany(targetEntity=ComentarioForo.class)
	public List<ComentarioForo> getComentFor() {
		return comentarios;
	}

	public void setComentFor(List<ComentarioForo> comentFor) {
		this.comentarios = comentFor;
	}

	// un usuario esta en una partida
	@ManyToOne(targetEntity=Partida.class)
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	@ManyToMany(targetEntity=Item.class, mappedBy="propietarios")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getPganadas() {
		return Pganadas;
	}

	public void setPganadas(int pganadas) {
		Pganadas = pganadas;
	}

	public int getPperdidas() {
		return Pperdidas;
	}

	public void setPperdidas(int pperdidas) {
		Pperdidas = pperdidas;
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

	public int getPjugadas() {
		return Pjugadas;
	}

	public void setPjugadas(int pjugadas) {
		Pjugadas = pjugadas;
	}
		
}
