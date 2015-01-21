package fr.treeptik.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private TypeMesure type;
	private Date date;
	@ManyToOne
	private Ouvrage ouvrage;
	private float valeur;

	public Mesure() {
		super();
	}

	public Mesure(Integer id, TypeMesure type, Date date, Ouvrage ouvrage, float valeur) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		this.ouvrage = ouvrage;
		this.valeur = valeur;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeMesure getType() {
		return type;
	}

	public void setType(TypeMesure type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Ouvrage getOuvrage() {
		return ouvrage;
	}

	public void setOuvrage(Ouvrage ouvrage) {
		this.ouvrage = ouvrage;
	}

	public float getValeur() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "Mesure [id=" + id + ", type=" + type + ", date=" + date + ", valeur=" + valeur + "]";
	}

}
