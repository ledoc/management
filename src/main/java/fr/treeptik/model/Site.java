package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// site ou secteur
	private String type;
	private String nom;

	// A défaut, S (site) ou T (secteur), département et numéro d’ordre de
	// création ex: S-77-01.
	private String code;
	private String departement;
	private String coordonneesGeographique;
	// Station météo : code Météo France et coordonnées géographiques ;
	private String stationMeteo;
	@ManyToOne
	private Client client;
	@OneToMany
	private List<Ouvrage> ouvrages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getCoordonneesGeographique() {
		return coordonneesGeographique;
	}

	public void setCoordonneesGeographique(String coordonneesGeographique) {
		this.coordonneesGeographique = coordonneesGeographique;
	}

	public String getStationMeteo() {
		return stationMeteo;
	}

	public void setStationMeteo(String stationMeteo) {
		this.stationMeteo = stationMeteo;
	}
	
	

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Ouvrage> getOuvrages() {
		return ouvrages;
	}

	public void setOuvrages(List<Ouvrage> ouvrages) {
		this.ouvrages = ouvrages;
	}

}
