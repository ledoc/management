package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Round {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	@ManyToOne
	private Exercice exercice;
	private Integer numeroDeSequence;
	private String poids;
	private Integer nombreSerie;
	private String nombreRepetition;
	private String commentaire;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	public Exercice getExercice() {
		return exercice;
	}
	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}
	public Integer getNumeroDeSequence() {
		return numeroDeSequence;
	}
	public void setNumeroDeSequence(Integer numeroDeSequence) {
		this.numeroDeSequence = numeroDeSequence;
	}
	public String getPoids() {
		return poids;
	}
	public void setPoids(String poids) {
		this.poids = poids;
	}
	public Integer getNombreSerie() {
		return nombreSerie;
	}
	public void setNombreSerie(Integer nombreSerie) {
		this.nombreSerie = nombreSerie;
	}
	public String getNombreRepetition() {
		return nombreRepetition;
	}
	public void setNombreRepetition(String nombreRepetition) {
		this.nombreRepetition = nombreRepetition;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	
}
