package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
<<<<<<< HEAD
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
=======
>>>>>>> 84f0d72a8272ea9592a3403962fa629c43a6aa21

import es.uc.fdi.iw.common.enums.Nacionalidades;

@Entity
<<<<<<< HEAD
@NamedQueries({
@NamedQuery(name="getUsuario",
query="select u from User u where u.login = :loginParam")
})
=======
>>>>>>> 84f0d72a8272ea9592a3403962fa629c43a6aa21
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
<<<<<<< HEAD
	private List<Item> propiedades;
		
=======
	private List<Item> items;
	
>>>>>>> 84f0d72a8272ea9592a3403962fa629c43a6aa21
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

	@DecimalMin("0.00")
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

	@Min(0)
	public int getPganadas() {
		return Pganadas;
	}

	public void setPganadas(int pganadas) {
		Pganadas = pganadas;
	}

	@Min(0)
	public int getPperdidas() {
		return Pperdidas;
	}

	public void setPperdidas(int pperdidas) {
		Pperdidas = pperdidas;
	}

	@DecimalMin("0.00")
	public double getDperdido() {
		return Dperdido;
	}

	public void setDperdido(double dperdido) {
		Dperdido = dperdido;
	}

	@DecimalMin("0.00")
	public double getDganado() {
		return Dganado;
	}

	public void setDganado(double dganado) {
		Dganado = dganado;
	}

	@Min(0)
	public int getPjugadas() {
		return Pjugadas;
	}

	public void setPjugadas(int pjugadas) {
		Pjugadas = pjugadas;
	}
		
}
