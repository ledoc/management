package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enregistreur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

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
	//  % du niveau de batterie
	private Integer niveauBatterie;
	
	// marque/ modele , numero de serie..
	private String panneauSolaire;
	// nom, pleine echelle, longueur câble...
	private String sonde;
	// croquis dynamique de l'ensemble
	private String croquis;
	//
	private List<Alerte> alertesActives;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
