package fr.treeptik.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mesure {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// niveaux d'eau, pluviometrie, niveau batterie
	private String type;
	private Date date;
	@ManyToOne
	private Enregistreur enregistreur;
	private String valeur;
	
	
	
	public Mesure() {
		super();
	}
	public Mesure(Integer id, String type, Date date,
			Enregistreur enregistreur, String valeur) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		this.enregistreur = enregistreur;
		this.valeur = valeur;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Enregistreur getEnregistreur() {
		return enregistreur;
	}
	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	
	
}
