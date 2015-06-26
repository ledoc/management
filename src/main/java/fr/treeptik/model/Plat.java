package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Plat {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer quantite;
	@ManyToOne
	private Aliment aliment;
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
	
	
	
	
	
}
