package fr.treeptik.dto;

import java.io.Serializable;
import java.util.Date;

public class PointGraphDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Date date;
    private Double valeur;
    private String categorie;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	@Override
    public String toString() {
        return "Point [date=" + date + ", valeur=" + valeur + "]";
    }
}