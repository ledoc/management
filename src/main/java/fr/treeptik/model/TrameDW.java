package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TrameDW implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Enregistreur enregistreur;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Temporal(TemporalType.TIME)
	private Date heure;
	// milliAmpère
	private float signalBrut;
	// 1ere conversion
	private String hauteurEau;

	// public TrameDW(HashMap<String, Object> hashMapHistoryXmlRpc){
	//
	// }

	// niveau d’eau en mètre au moment de l’installation du capteur et de la
	// mesure manuelle de référence Nm0
	// private String niveauStatique;
	// 2ème conversion
	// private String coteAltimetrique;
	// @OneToMany
	// private List<Alerte> alertes;

	public TrameDW() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getHeure() {
		return heure;
	}

	public void setHeure(Date heure) {
		this.heure = heure;
	}

	public float getSignalBrut() {
		return signalBrut;
	}

	public void setSignalBrut(float signalBrut) {
		this.signalBrut = signalBrut;
	}

	public String getHauteurEau() {
		return hauteurEau;
	}

	public void setHauteurEau(String hauteurEau) {
		this.hauteurEau = hauteurEau;
	}

	@Override
	public String toString() {
		return "TrameDW [id=" + id + ", date=" + date + ", heure=" + heure + ", signalBrut=" + signalBrut
				+ ", hauteurEau=" + hauteurEau + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((enregistreur == null) ? 0 : enregistreur.hashCode());
		result = prime * result + ((hauteurEau == null) ? 0 : hauteurEau.hashCode());
		result = prime * result + ((heure == null) ? 0 : heure.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(signalBrut);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrameDW other = (TrameDW) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (enregistreur == null) {
			if (other.enregistreur != null)
				return false;
		} else if (!enregistreur.equals(other.enregistreur))
			return false;
		if (hauteurEau == null) {
			if (other.hauteurEau != null)
				return false;
		} else if (!hauteurEau.equals(other.hauteurEau))
			return false;
		if (heure == null) {
			if (other.heure != null)
				return false;
		} else if (!heure.equals(other.heure))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(signalBrut) != Float.floatToIntBits(other.signalBrut))
			return false;
		return true;
	}

	// public String getNiveauStatique() {
	// return niveauStatique;
	// }
	//
	// public void setNiveauStatique(String niveauStatique) {
	// this.niveauStatique = niveauStatique;
	// }
	//
	// public String getCoteAltimetrique() {
	// return coteAltimetrique;
	// }
	//
	// public void setCoteAltimetrique(String coteAltimetrique) {
	// this.coteAltimetrique = coteAltimetrique;
	// }

	// public List<Alerte> getAlertes() {
	// return alertes;
	// }
	//
	// public void setAlertes(List<Alerte> alertes) {
	// this.alertes = alertes;
	// }

}
