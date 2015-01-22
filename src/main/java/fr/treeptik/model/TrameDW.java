package fr.treeptik.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TrameDW {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Enregistreur enregistreur;
	// @ManyToOne
	// private Enregistreur enregistreur;
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

	// public Enregistreur getEnregistreur() {
	// return enregistreur;
	// }
	//
	// public void setEnregistreur(Enregistreur enregistreur) {
	// this.enregistreur = enregistreur;
	// }

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
