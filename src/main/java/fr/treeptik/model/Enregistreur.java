package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Enregistreur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//identifiant applicatif
	private String mid;
	// figer la chronique en cas de changement d'un quelconque composant.
	// fiche passera en écriture libre
	// Un bouton de liaison NM apparaîtra afin da faire correspondre la nouvelle
	// données reçu avec un Niveau Manuel
	private Boolean maintenance;

	// modem : nom, modèle, numéro série…
	private String modem;
	// la transmission : mode (SMS, GPRS ou Satellite), opérateur (ORANGE, SFR,
	// BOUYGUES, IRIDIUM…) ;
	private String trasmission;
	// la SIM : numéro série, numéro d’appel ;
	private String sim;
	// type, marque/modele...
	private String batterie;
	//  % du niveau de la batterie
	private Integer niveauBatterie;
	
	// marque/ modele , numéro de serie..
	private String panneauSolaire;
	// nom, pleine echelle, longueur câble...
	private String sonde;
	// croquis dynamique de l'ensemble
	private String croquis;
	//
	@OneToMany
	private List<Alerte> alertesActives;
	@OneToMany(mappedBy="enregistreur")
	private List<Mesure> mesures;
	@OneToMany(mappedBy="enregistreur")
	private List<TrameDW> trameDWs;
	

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

	public String getTrasmission() {
		return trasmission;
	}

	public void setTrasmission(String trasmission) {
		this.trasmission = trasmission;
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

	public List<Alerte> getAlertesActives() {
		return alertesActives;
	}

	public void setAlertesActives(List<Alerte> alertesActives) {
		this.alertesActives = alertesActives;
	}

	public List<Mesure> getMesures() {
		return mesures;
	}

	public void setMesures(List<Mesure> mesures) {
		this.mesures = mesures;
	}

	public List<TrameDW> getTrameDWs() {
		return trameDWs;
	}

	public void setTrameDWs(List<TrameDW> trameDWs) {
		this.trameDWs = trameDWs;
	}

	
	
}
