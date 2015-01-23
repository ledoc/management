package fr.treeptik.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Enregistreur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	// identifiant applicatif
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
	// % du niveau de la batterie
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
//	@OneToMany(mappedBy = "enregistreur")
//	private List<TrameDW> trameDWs;
	
	private boolean valid;
	/**
	 * TODO : trouver la classe de cette bete là
	 */

	// private Class[] seamless;
	private Integer period;
	private Integer localizableStatus;
	private String clientName;
	private Integer until;
	private String pid;
	private String comment;
	private String type;
	private String userName;
	@OneToMany(mappedBy = "enregistreur")
	private List<TrameDW> trameDWs;

	// private String server;

	public Enregistreur() {
		super();
	}

	public Enregistreur(HashMap<String, Object> xmlrpcHashMap) {
		super();
		this.valid = (boolean) xmlrpcHashMap.get("valid");
		// this.seamless = xmlrpcHashMap.get("seamless");
		this.period = (int) xmlrpcHashMap.get("period");
		this.localizableStatus = (int) xmlrpcHashMap.get("localizableStatus");
		this.clientName = (String) xmlrpcHashMap.get("clientName");
		this.mid = (String) xmlrpcHashMap.get("mid");
		this.until = (int) xmlrpcHashMap.get("until");
		this.pid = (String) xmlrpcHashMap.get("pid");
		this.comment = (String) xmlrpcHashMap.get("comment");
		this.type = (String) xmlrpcHashMap.get("type");
		this.userName = (String) xmlrpcHashMap.get("userName");
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public List<TrameDW> getTrameDWs() {
		return trameDWs;
	}

	public void setTrameDWs(List<TrameDW> trameDWs) {
		this.trameDWs = trameDWs;
	}

	
	@Override
	public String toString() {
		return "Enregistreur [id=" + id + ", valid=" + valid + ", period=" + period + ", localizableStatus="
				+ localizableStatus + ", clientName=" + clientName + ", mid=" + mid + ", until=" + until + ", pid="
				+ pid + ", comment=" + comment + ", type=" + type + ", userName=" + userName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		result = prime * result + ((sim == null) ? 0 : sim.hashCode());
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
		} else if (!mid.equals(other.mid))
			return false;
		if (sim == null) {
			if (other.sim != null)
				return false;
		} else if (!sim.equals(other.sim))
			return false;
		return true;
	}

	// public Object getSeamless() {
	// return seamless;
	// }
	//
	// public void setSeamless(Object seamless) {
	// this.seamless = seamless;
	// }
	
	

}
