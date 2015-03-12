package fr.treeptik.model;

import java.io.Serializable;
import java.util.Comparator;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Capteur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private Enregistreur enregistreur;

	// Ce qui va être mesuré
	@Enumerated(EnumType.STRING)
	private TypeMesureOrTrame typeMesureOrTrame;

	private Float echelleMaxCapteur;
	private Float echelleMinCapteur;

	@JsonIgnore
	@Transient
	private Mesure niveauManuel;
	// dernière mesure relevé avec date et heure
	@JsonIgnore
	@Transient
	private Mesure derniereMesure;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "capteur")
	private List<AlerteDescription> alerteDescriptions;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "capteur")
	private List<AlerteEmise> alerteEmises;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "capteur")
	private List<Mesure> mesures;

	public Capteur() {
		super();
		this.niveauManuel = new Mesure();
		this.derniereMesure = new Mesure();
	}

	@PostUpdate
	@PostLoad
	@PostPersist
	public void setDynamicMesures() {
		this.initNiveauManuel();
		this.initDerniereMesure();

	}

	/**
	 * affecter la niveau manuel le plus récent
	 */
	public void initNiveauManuel() {

		if (this.mesures != null) {

			boolean typeManuelPresence = this.mesures.stream().anyMatch(m ->

			m.getTypeMesureOrTrame().equals(TypeMesureOrTrame.NIVEAUMANUEL));

			if (typeManuelPresence) {
				List<Mesure> allMesureManuel = this.mesures
						.stream()
						.filter(m -> m.getTypeMesureOrTrame().equals(
								TypeMesureOrTrame.NIVEAUMANUEL))
						.collect(Collectors.toList());

				if (allMesureManuel.size() > 1) {
					Comparator<Mesure> c = (m1, m2) -> m1.getDate().compareTo(
							m2.getDate());

					Optional<Mesure> max = allMesureManuel.stream().max(c);

					this.niveauManuel = max.isPresent() ? max.get().cloneMe()
							: new Mesure();
				} else {
					this.niveauManuel = allMesureManuel.get(0).cloneMe();
				}
			}
		} else {
			this.niveauManuel = new Mesure();
		}

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

				if (allMesureNotManuel.size() > 1) {
					Comparator<Mesure> c = (m1, m2) -> m1.getDate().compareTo(
							m2.getDate());

					Optional<Mesure> max = allMesureNotManuel.stream().max(c);

					this.derniereMesure = max.isPresent() ? max.get()
							: new Mesure();
				} else {
					this.derniereMesure = allMesureNotManuel.get(0);
				}
			}
		} else {
			this.derniereMesure = new Mesure();
		}
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

	public TypeMesureOrTrame getTypeMesureOrTrame() {
		return typeMesureOrTrame;
	}

	public void setTypeMesureOrTrame(TypeMesureOrTrame typeMesureOrTrame) {
		this.typeMesureOrTrame = typeMesureOrTrame;
	}

	public Float getEchelleMaxCapteur() {
		return echelleMaxCapteur;
	}

	public void setEchelleMaxCapteur(Float echelleMaxCapteur) {
		this.echelleMaxCapteur = echelleMaxCapteur;
	}

	public Float getEchelleMinCapteur() {
		return echelleMinCapteur;
	}

	public void setEchelleMinCapteur(Float echelleMinCapteur) {
		this.echelleMinCapteur = echelleMinCapteur;
	}

	public Mesure getNiveauManuel() {
		return niveauManuel;
	}

	public void setNiveauManuel(Mesure niveauManuel) {
		this.niveauManuel = niveauManuel;
	}

	public Mesure getDerniereMesure() {
		return derniereMesure;
	}

	public void setDerniereMesure(Mesure derniereMesure) {
		this.derniereMesure = derniereMesure;
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

	public List<Mesure> getMesures() {
		return mesures;
	}

	public void setMesures(List<Mesure> mesures) {
		this.mesures = mesures;
	}

	@Override
	public String toString() {
		return "Capteur [id=" + id + ", typeMesureOrTrame=" + typeMesureOrTrame
				+ ", echelleMaxCapteur=" + echelleMaxCapteur
				+ ", echelleMinCapteur=" + echelleMinCapteur + "]";
	}

}
