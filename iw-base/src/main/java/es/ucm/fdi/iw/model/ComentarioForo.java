package es.ucm.fdi.iw.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import es.uc.fdi.iw.common.enums.Temas;

@Entity
public class ComentarioForo {
	
	private long id;
	private User usuario;
	private Date fecha;
	private String comentario;
	private Temas tema;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}	
	
	// un comentario tiene un dueño
	@ManyToOne(targetEntity=User.class)
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getComentarios() {
		return comentario;
	}

	public void setComentarios(String comentarios) {
		this.comentario = comentarios;
	}

	public Temas getTema() {
		return tema;
	}

	public void setTema(Temas tema) {
		this.tema = tema;
	}
}
