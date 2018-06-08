package es.ucm.fdi.iw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import es.ucm.fdi.iw.common.enums.Nacionalidades;

@Entity
@NamedQueries({ 
@NamedQuery(name = "getUsuario", 
	query = "select u from User u where u.login = :loginParam"),
@NamedQuery(name = "getUsuarios", 
	query = "select u from User u"),
@NamedQuery(name = "noRepes", 
	query = "select u from User u where "
			+ "u.login = :loginParam or "
			+ "u.email = :emailParam"),
@NamedQuery(name = "getUsuarioNacion", 
	query = "select u from User u where u.nacion = :nacionParam") }
)
public class User {

	private long id;
	private String login;
	private String password;
	private String roles;
	private int dinero;
	private String email;
	private Nacionalidades nacion;
	private List<User> amigos;
	private List<ComentarioForo> comentarios;
	private int Pganadas;
	private int Pperdidas;
	private int Pjugadas;
	private int Dperdido;
	private int Dganado;
	private Partida partida;
	private List<Item> propiedades;
	private boolean listo;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(unique = true)
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

	@Min(0)
	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	@Column(unique = true)
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

	@ManyToMany(targetEntity = User.class, fetch=FetchType.EAGER)
	public List<User> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<User> miHashSet) {
		this.amigos = miHashSet;
	}

	@OneToMany(targetEntity = ComentarioForo.class, fetch=FetchType.EAGER)
	public List<ComentarioForo> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentarioForo> comentarios) {
		this.comentarios = comentarios;
	}

	@ManyToOne(targetEntity = Partida.class, cascade=CascadeType.ALL)
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
	@ManyToMany(targetEntity = Item.class,fetch = FetchType.EAGER)	
	public List<Item> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<Item> propiedades) {
		this.propiedades = propiedades;
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

	@Min(0)
	public int getDperdido() {
		return Dperdido;
	}

	public void setDperdido(int dperdido) {
		Dperdido = dperdido;
	}

	@Min(0)
	public int getDganado() {
		return Dganado;
	}

	public void setDganado(int dganado) {
		Dganado = dganado;
	}

	@Min(0)
	public int getPjugadas() {
		return Pjugadas;
	}

	public void setPjugadas(int pjugadas) {
		Pjugadas = pjugadas;
	}

	public boolean isListo() {
		return listo;
	}

	public void setListo(boolean listo) {
		this.listo = listo;
	}

}
