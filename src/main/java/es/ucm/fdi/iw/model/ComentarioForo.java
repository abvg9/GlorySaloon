package es.ucm.fdi.iw.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import es.ucm.fdi.iw.common.enums.Temas;

@Entity
@NamedQueries({
@NamedQuery(name="getForo",
	query="select f from ComentarioForo f where f.tema = :temaParam")
})
public class ComentarioForo {
	
	private long id;
	private User usuario;
	private String fecha;
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

	@ManyToOne(targetEntity=User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String date) {
		this.fecha = date;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Temas getTema() {
		return tema;
	}

	public void setTema(Temas tema) {
		this.tema = tema;
	}
}
