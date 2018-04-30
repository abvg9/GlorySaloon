package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import es.uc.fdi.iw.common.enums.Nacionalidades;

@Entity
@NamedQueries({
@NamedQuery(name="noRepes",
query="select u from User u where u.login = :loginParam or u.email = :emailParam"),
@NamedQuery(name="verAmigos",
query="select u.amigos from User u where u.login = :loginParam")
})
public class User {
	
	private long id;
	private String login;
	private String password;
	private String roles;
	private byte enabled;
	private double dinero;
	private String email;
	private Nacionalidades nacion;
	private List<User> amigos;
    private List<ComentarioForo> comentarios;
	private int Pganadas;
	private int Pperdidas;
	private int Pjugadas;
	private double Dperdido;
	private double Dganado;
	private Partida partida;	
	private List<Item> propiedades;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}	

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

	@JoinTable(name = "user_amigos",
    joinColumns = {@JoinColumn(name = "amigo_A", referencedColumnName = "id", nullable=false)},
    inverseJoinColumns = {@JoinColumn(name = "amigo_B", referencedColumnName = "id", nullable =false)})
	@ManyToMany(targetEntity=User.class)
	public List<User> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<User> amigos) {
		this.amigos = amigos;
	}
	
	@OneToMany(targetEntity=ComentarioForo.class)
	@JoinColumn(name="usuario_id")
	public List<ComentarioForo> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<ComentarioForo> comentarios) {
		this.comentarios = comentarios;
	}

	@ManyToOne(targetEntity=Partida.class)
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	@ManyToMany(targetEntity=Item.class)
	public List<Item> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<Item> propiedades) {
		this.propiedades = propiedades;
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
