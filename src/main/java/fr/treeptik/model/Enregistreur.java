package fr.treeptik.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Enregistreur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// identifiant applicatif
	private String mid;
	private String nom;
	private String commentaire;

	// Analogique ou numérique : différence pour calcul
	@Enumerated(EnumType.STRING)
	private TypeEnregistreur typeEnregistreur;

	// figer la chronique en cas de changement d'un quelconque composant.
	// fiche passera en écriture libre
	// Un bouton de liaison NM apparaîtra afin da faire correspondre la nouvelle
	// données reçu avec un Niveau Manuel
	private Boolean maintenance;
	private Float altitude;
	private Float coeffTemperature;
	private Float salinite;

	// modem : nom, modèle, numéro série…
	private String modem;
	// la transmission : mode (SMS, GPRS ou Satellite), opérateur (ORANGE, SFR,
	// BOUYGUES, IRIDIUM…) ;
	private String transmission;
	// la SIM : numéro série, numéro d’appel ;
	private String sim;
	// type, marque/modele...
	private String batterie;
	// % du niveau de la batterie
	private Integer niveauBatterie;

	// marque/ modele , numéro de serie..
	private String panneauSolaire;
	// nom, pleine echelle, longueur câble...
	private String sonde;
	// croquis dynamique de l'ensemble
	private String croquis;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "enregistreur")
	private List<TrameDW> trameDWs;

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "enregistreur")
	private List<Capteur> capteurs;

	/**
	 * TODO pas mettre de valeur par defaut
	 */
	@JsonIgnore
	private Boolean valid = true;

	@JsonIgnore
	private Integer period;
	@JsonIgnore
	private Integer localizableStatus;
	@JsonIgnore
	private String clientName;
	@JsonIgnore
	private Integer until;
	@JsonIgnore
	private String pid;
	@JsonIgnore
	private String comment;
	@JsonIgnore
	private String type;
	@JsonIgnore
	private String userName;
	@JsonIgnore
	@ManyToOne
	private Ouvrage ouvrage;

	// private String server;	



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public TypeEnregistreur getTypeEnregistreur() {
		return typeEnregistreur;
	}

	public void setTypeEnregistreur(TypeEnregistreur typeEnregistreur) {
		this.typeEnregistreur = typeEnregistreur;
	}

	public Float getAltitude() {
		return altitude;
	}

	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	public Float getCoeffTemperature() {
		return coeffTemperature;
	}

	public void setCoeffTemperature(Float coeffTemperature) {
		this.coeffTemperature = coeffTemperature;
	}

	public Ouvrage getOuvrage() {
		return ouvrage;
	}

	public void setOuvrage(Ouvrage ouvrage) {
		this.ouvrage = ouvrage;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getUntil() {
		return until;
	}

	public void setUntil(int until) {
		this.until = until;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLocalizableStatus() {
		return localizableStatus;
	}

	public void setLocalizableStatus(int localizableStatus) {
		this.localizableStatus = localizableStatus;
	}

	public Boolean getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(Boolean maintenance) {
		this.maintenance = maintenance;
	}

	public String getModem() {
		return modem;
	}

	public void setModem(String modem) {
		this.modem = modem;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getBatterie() {
		return batterie;
	}

	public void setBatterie(String batterie) {
		this.batterie = batterie;
	}

	public Integer getNiveauBatterie() {
		return niveauBatterie;
	}

	public void setNiveauBatterie(Integer niveauBatterie) {
		this.niveauBatterie = niveauBatterie;
	}

	public String getPanneauSolaire() {
		return panneauSolaire;
	}

	public void setPanneauSolaire(String panneauSolaire) {
		this.panneauSolaire = panneauSolaire;
	}

	public String getSonde() {
		return sonde;
	}

	public void setSonde(String sonde) {
		this.sonde = sonde;
	}

	public String getCroquis() {
		return croquis;
	}

	public void setCroquis(String croquis) {
		this.croquis = croquis;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public void setLocalizableStatus(Integer localizableStatus) {
		this.localizableStatus = localizableStatus;
	}

	public void setUntil(Integer until) {
		this.until = until;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Float getSalinite() {
		return salinite;
	}

	public void setSalinite(Float salinite) {
		this.salinite = salinite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<TrameDW> getTrameDWs() {
		return trameDWs;
	}

	public void setTrameDWs(List<TrameDW> trameDWs) {
		this.trameDWs = trameDWs;
	}

	public List<Capteur> getCapteurs() {
		return capteurs;
	}

	public void setCapteurs(List<Capteur> capteurs) {
		this.capteurs = capteurs;
	}

	@Override
	public String toString() {
		return "Enregistreur [id=" + id + ", mid=" + mid + ", maintenance="
				+ maintenance + ", altitude=" + altitude
				+ ", coeffTemperature=" + coeffTemperature + ", modem=" + modem
				+ ", transmission=" + transmission + ", sim=" + sim
				+ ", batterie=" + batterie + ", niveauBatterie="
				+ niveauBatterie + ", panneauSolaire=" + panneauSolaire
				+ ", sonde=" + sonde + ", croquis=" + croquis + ", valid="
				+ valid + ", period=" + period + ", localizableStatus="
				+ localizableStatus + ", clientName=" + clientName + ", until="
				+ until + ", pid=" + pid + ", comment=" + comment + ", type="
				+ type + ", userName=" + userName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((mid == null) ? 0 : mid.toUpperCase().hashCode());
		result = prime * result
				+ ((sim == null) ? 0 : sim.toUpperCase().hashCode());
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
		Enregistreur other = (Enregistreur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equalsIgnoreCase(other.mid))
			return false;
		if (sim == null) {
			if (other.sim != null)
				return false;
		} else if (!sim.equalsIgnoreCase(other.sim))
			return false;
		return true;
	}

}
