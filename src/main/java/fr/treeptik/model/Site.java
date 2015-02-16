package fr.treeptik.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Site implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// site ou secteur
	@Enumerated(EnumType.STRING)
	private TypeSite typeSite;
	private String nom;

	// A défaut, S (site) ou T (secteur), département et numéro d’ordre de
	// création ex: S-77-01.
	private String codeSite;
	private String departement;
	private String coordonneesGeographique;
	private String coordonneesLambert;
	private Float latitude;
	private Float longitude;
	// Station météo : code Météo France et coordonnées géographiques ;
	private String stationMeteo;
	@ManyToOne
	private Etablissement etablissement;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="site")
	private List<Ouvrage> ouvrages;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeSite getTypeSite() {

		return typeSite;
	}

	public void setTypeSite(TypeSite typeSite) {
		this.typeSite = typeSite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeSite() {
		return codeSite;
	}

	public void setCodeSite(String code) {
		this.codeSite = code;
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
		this.setLatitude();
		this.setLongitude();
	}
	
	
	
	public String getCoordonneesLambert() {
		return coordonneesLambert;
	}

	public void setCoordonneesLambert(String coordonneesLambert) {
		this.coordonneesLambert = coordonneesLambert;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude() {
		this.latitude = Float.valueOf(this.coordonneesGeographique.substring(0, this.coordonneesGeographique.lastIndexOf("/")));
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude() {
		
		this.longitude = Float.valueOf(this.coordonneesGeographique.substring(this.coordonneesGeographique.lastIndexOf("/") + 1, this.coordonneesGeographique.length()));
	}


	public String getStationMeteo() {
		return stationMeteo;
	}

	public void setStationMeteo(String stationMeteo) {
		this.stationMeteo = stationMeteo;
	}

	public List<Ouvrage> getOuvrages() {
		return ouvrages;
	}
	
	public Etablissement getEtablissement() {
		return etablissement;
	}
	
	public void setEtablissement(Etablissement etablissement) {
		this.etablissement = etablissement;
	}

	public void setOuvrages(List<Ouvrage> ouvrages) {
		this.ouvrages = ouvrages;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", typeSite=" + typeSite + ", nom=" + nom
				+ ", codeSite=" + codeSite + ", departement=" + departement
				+ ", coordonneesGeographique=" + coordonneesGeographique
				+ ", stationMeteo=" + stationMeteo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeSite == null) ? 0 : codeSite.toUpperCase().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Site other = (Site) obj;
		if (codeSite == null) {
			if (other.codeSite != null)
				return false;
		} else if (!codeSite.equalsIgnoreCase(other.codeSite))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
