package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Aliment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	private Float proteine;
	private Float lipide;
	private Float glucide;
	private Float bcaa;
	private Integer calories;
	@ManyToOne
	private Repas repas;

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

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Float getProteine() {
		return proteine;
	}

	public void setProteine(Float proteine) {
		this.proteine = proteine;
	}

	public Float getLipide() {
		return lipide;
	}

	public void setLipide(Float lipide) {
		this.lipide = lipide;
	}

	public Float getGlucide() {
		return glucide;
	}

	public void setGlucide(Float glucide) {
		this.glucide = glucide;
	}

	public Float getBcaa() {
		return bcaa;
	}

	public void setBcaa(Float bcaa) {
		this.bcaa = bcaa;
	}

	public Repas getRepas() {
		return repas;
	}

	public void setRepas(Repas repas) {
		this.repas = repas;
	}

}
