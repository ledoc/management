package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;
	private Float valeur;
	private String midEnregistreur;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getValeur() {
		return valeur;
	}

	public void setValeur(Float valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "Point [date=" + date + ", valeur=" + valeur + "]";
	}

	public String getMidEnregistreur() {
		return midEnregistreur;
	}

	public void setMidEnregistreur(String midEnregistreur) {
		this.midEnregistreur = midEnregistreur;
	}

	
	
}
