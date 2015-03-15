package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;
	private Float valeur;
	private TypeCaptAlerteMesure typeCaptAlerteMesure;
	private String mid;
	private String unite;

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

	public TypeCaptAlerteMesure getTypeCaptAlerteMesure() {
		return typeCaptAlerteMesure;
	}

	public void setTypeCaptAlerteMesure(TypeCaptAlerteMesure typeCaptAlerteMesure) {
		this.typeCaptAlerteMesure = typeCaptAlerteMesure;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}
	
}
