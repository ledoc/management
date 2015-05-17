package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Seance implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer numeroDeSequence;
	@OneToMany
	private Set<Exercice> exercices;
	private Integer nombreSerie;
	private Integer nombreRepetition;
	private String commentaire;
	@Temporal(TemporalType.DATE)
	private Date date;

	public Seance() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumeroDeSequence() {
		return numeroDeSequence;
	}

	public void setNumeroDeSequence(Integer numeroDeSequence) {
		this.numeroDeSequence = numeroDeSequence;
	}

	public Set<Exercice> getExercices() {
		return exercices;
	}

	public void setExercices(Set<Exercice> exercices) {
		this.exercices = exercices;
	}

	public Integer getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(Integer nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public Integer getNombreRepetition() {
		return nombreRepetition;
	}

	public void setNombreRepetition(Integer nombreRepetition) {
		this.nombreRepetition = nombreRepetition;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
