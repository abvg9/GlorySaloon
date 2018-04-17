package es.ucm.fdi.iw.model;

import java.awt.image.BufferedImage;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Item {
	
	private long id;
	BufferedImage imagen;
	private double precio;
	private String nombre;
	private String descripcion;
	
	public BufferedImage getImagen() {
		return imagen;
	}

	public void getImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}

	@Id
	@GeneratedValue
	@Column(unique=true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	
}
