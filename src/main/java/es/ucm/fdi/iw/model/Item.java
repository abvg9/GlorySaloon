package es.ucm.fdi.iw.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;

@Entity
@NamedQueries({
@NamedQuery(name="getTienda",query="select t from Item t "),
@NamedQuery(name="getItem",query="select i from Item i where i.nombre = :nombreParam")
})
public class Item {
	
	private long id;
	private int precio;
	private String nombre;
	private String descripcion;
	private List<User> propietarios;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Min(0)
	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Column(unique=true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToMany(targetEntity=User.class)
	public List<User> getPropietarios() {
		return propietarios;
	}

	public void setPropietarios(List<User> propietarios) {
		this.propietarios = propietarios;
	}
	
}
