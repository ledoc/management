package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Plat {

	@Id
	@GeneratedValue
	private Long id;
	private Integer quantite;
	@ManyToOne
	private Aliment aliment;
	@OneToOne
	private NutritionBilan nutritionBilan;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQuantite() {
		return quantite;
	}
	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}
	public Aliment getAliment() {
		return aliment;
	}
	public void setAliment(Aliment aliment) {
		this.aliment = aliment;
	}
	public NutritionBilan getNutritionBilan() {
		return nutritionBilan;
	}
	public void setNutritionBilan(NutritionBilan nutritionBilan) {
		this.nutritionBilan = nutritionBilan;
	}
	
}
