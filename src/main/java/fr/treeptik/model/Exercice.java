package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Exercice {

	@Id
	@GeneratedValue
	private Long id;
	private String nom;
	private String muscle;

	public Exercice() {
		super();
	}

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

	public String getMuscle() {
		return muscle;
	}

	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}

}
