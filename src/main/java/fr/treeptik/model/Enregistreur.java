package fr.treeptik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

@Entity
public class Enregistreur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// identifiant applicatif
	private String mid;

	// Analogique ou numérique : différence pour calcul
	@Enumerated(EnumType.STRING)
	private TypeEnregistreur typeEnregistreur;

	// Ce qui va être mesuré
	private TypeMesureOrTrame typeMesureOrTrame;

	// figer la chronique en cas de changement d'un quelconque composant.
	// fiche passera en écriture libre
	// Un bouton de liaison NM apparaîtra afin da faire correspondre la nouvelle
	// données reçu avec un Niveau Manuel
	private Boolean maintenance;
	private Float altitude;
	private Float coeffTemperature;
	private Float echelleCapteur;

	// Mesure 3 (Niveau Manuel) : à indiquer dernier NM + date + accès
	// historique NM
	@Transient
	private Mesure niveauManuel;
	// Mesure Enregistreur : dernière mesure relevé avec date et heure
	@Transient
	private Mesure derniereMesure;
	// nécessaire pour calcul du niveau d'eau
	@Transient
	private TrameDW derniereTrameDW;

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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "enregistreur")
	private List<AlerteDescription> alerteDescriptions;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "enregistreur")
	private List<AlerteEmise> alerteEmises;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "enregistreur")
	private List<TrameDW> trameDWs;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "enregistreur")
	private List<Mesure> mesures;

	/**
	 * TODO pas mettre de valeur par defaut
	 */
	private Boolean valid = true;
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
	@ManyToOne
	private Ouvrage ouvrage;

	// private String server;

	public Enregistreur() {
		super();
		this.niveauManuel = new Mesure();
		this.derniereMesure = new Mesure();
		this.derniereTrameDW = new TrameDW();
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

	@PostUpdate
	@PostLoad
	@PostPersist
	public void setDynamicMesures() {
		this.initNiveauManuel();
		this.initDerniereMesure();
		this.initDerniereTrameDW();
	}

	/**
	 * d'après ce que j'ai compris, niveau mesuré en premier et par la suite
	 * manuellement du niveau d'eau par rapport au NGF (CoteRepereNGF)
	 * 
	 * @param niveauManuel
	 */
	public Mesure getNiveauManuel() {
		return niveauManuel;
	}

	/**
	 * d'après ce que j'ai compris, niveau mesuré en premier et par la suite
	 * manuellement du niveau d'eau par rapport au NGF (CoteRepereNGF)
	 * 
	 * @param niveauManuel
	 */
	public void initNiveauManuel() {

		if (this.mesures != null) {
			List<Mesure> allMesureManuel = new ArrayList<Mesure>();

			for (Mesure mesure : this.mesures) {

				System.out.println(mesure);
				if (mesure.getTypeMesureOrTrame().equals(
						TypeMesureOrTrame.NIVEAUMANUEL)) {
					allMesureManuel.add(mesure);
				}
			}

			if (allMesureManuel.size() != 0) {
				if (allMesureManuel.size() > 1) {
					Comparator<Mesure> comparatorMesure = (m1, m2) -> m1
							.getDate().compareTo(m2.getDate());

					this.niveauManuel = Collections.max(allMesureManuel,
							comparatorMesure).cloneMe();
				} else {

					this.niveauManuel = allMesureManuel.get(0).cloneMe();

				}

			} else {
				this.niveauManuel = new Mesure();
			}

			// boolean typeManuelPresence = this.mesures.stream().anyMatch(
			// m -> m.getTypeMesureOrTrame().equals(
			// TypeMesureOrTrame.NIVEAUMANUEL));
			//
			// if (typeManuelPresence) {
			// List<Mesure> allMesureManuel = this.mesures
			// .stream()
			// .filter(m -> m.getTypeMesureOrTrame().equals(
			// TypeMesureOrTrame.NIVEAUMANUEL))
			// .collect(Collectors.toList());
			//
			// if (allMesureManuel.size() < 1) {
			// Comparator<Mesure> c = (m1, m2) -> m1.getDate().compareTo(
			// m2.getDate());
			//
			// Optional<Mesure> max = allMesureManuel.stream().max(c);
			//
			// this.niveauManuel = max.isPresent() ? max.get().cloneMe()
			// : new Mesure();
			// } else {
			// this.niveauManuel = allMesureManuel.get(0).cloneMe();
			// }
			// }

		}
	}

	/**
	 * récupérer la dernière mesure enregistrée
	 * 
	 * @return
	 */
	public Mesure getDerniereMesure() {
		return derniereMesure;
	}

	/**
	 * affecter la dernière mesure enregistrée
	 * 
	 * @return
	 */
	public void initDerniereMesure() {
		if (this.mesures != null) {

			boolean typeNotManuelPresence = this.mesures.stream().anyMatch(
					m -> !m.getTypeMesureOrTrame().equals(
							TypeMesureOrTrame.NIVEAUMANUEL));

			if (typeNotManuelPresence) {

				List<Mesure> allMesureNotManuel = this.mesures
						.stream()
						.filter(m -> !m.getTypeMesureOrTrame().equals(
								TypeMesureOrTrame.NIVEAUMANUEL))
						.collect(Collectors.toList());

				if (allMesureNotManuel.size() < 1) {
					Comparator<Mesure> c = (m1, m2) -> m1.getDate().compareTo(
							m2.getDate());

					Optional<Mesure> max = allMesureNotManuel.stream().max(c);

					this.derniereMesure = max.isPresent() ? max.get()
							: new Mesure();
				} else {
					this.derniereMesure = allMesureNotManuel.get(0);
				}
			}
		}
	}

	public TrameDW getDerniereTrameDW() {
		return derniereTrameDW;
	}

	public void initDerniereTrameDW() {

		if (this.trameDWs != null) {

			if (this.trameDWs.size() < 1) {

				Comparator<TrameDW> c = (t1, t2) -> t1.getDate().compareTo(
						t2.getDate());

				Optional<TrameDW> optional = this.trameDWs.stream().max(c);

				this.derniereTrameDW = optional.isPresent() ? optional.get()
						: new TrameDW();
			}

			else {
				this.derniereTrameDW = this.trameDWs.get(0);
			}
		}

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

	public Float getEchelleCapteur() {
		return echelleCapteur;
	}

	public void setEchelleCapteur(Float echelleCapteur) {
		this.echelleCapteur = echelleCapteur;
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

	public List<Mesure> getMesures() {
		return mesures;
	}

	public void setMesures(List<Mesure> mesures) {
		this.mesures = mesures;
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
		return "Enregistreur [id=" + id + ", mid=" + mid + ", maintenance="
				+ maintenance + ", altitude=" + altitude
				+ ", coeffTemperature=" + coeffTemperature + ", niveauManuel="
				+ niveauManuel + ", derniereMesure=" + derniereMesure
				+ ", modem=" + modem + ", transmission=" + transmission
				+ ", sim=" + sim + ", batterie=" + batterie
				+ ", niveauBatterie=" + niveauBatterie + ", panneauSolaire="
				+ panneauSolaire + ", sonde=" + sonde + ", croquis=" + croquis
				+ ", valid=" + valid + ", period=" + period
				+ ", localizableStatus=" + localizableStatus + ", clientName="
				+ clientName + ", until=" + until + ", pid=" + pid
				+ ", comment=" + comment + ", type=" + type + ", userName="
				+ userName + ", ouvrage=" + ouvrage.getCodeOuvrage() + "]";
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

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public void setNiveauManuel(Mesure niveauManuel) {
		this.niveauManuel = niveauManuel;
	}

	public void setDerniereMesure(Mesure derniereMesure) {
		this.derniereMesure = derniereMesure;
	}

	public void setDerniereTrameDW(TrameDW derniereTrameDW) {
		this.derniereTrameDW = derniereTrameDW;
	}

	public List<AlerteDescription> getAlerteDescriptions() {
		return alerteDescriptions;
	}

	public void setAlerteDescriptions(List<AlerteDescription> alerteDescriptions) {
		this.alerteDescriptions = alerteDescriptions;
	}

	public List<AlerteEmise> getAlerteEmises() {
		return alerteEmises;
	}

	public void setAlerteEmises(List<AlerteEmise> alerteEmises) {
		this.alerteEmises = alerteEmises;
	}

	public TypeMesureOrTrame getTypeMesureOrTrame() {
		return typeMesureOrTrame;
	}

	public void setTypeMesureOrTrame(TypeMesureOrTrame typeMesureOrTrame) {
		this.typeMesureOrTrame = typeMesureOrTrame;
	}
}
