package es.ucm.fdi.iw.model;

import java.util.List;
<<<<<<< HEAD
import javax.persistence.Column;
=======

>>>>>>> 84f0d72a8272ea9592a3403962fa629c43a6aa21
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;

@Entity
public class Item {
	
	private long id;
	private double precio;
	private String nombre;
	private String descripcion;
	private List<User> propietarios;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@DecimalMin("0.00")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

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
