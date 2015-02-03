package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// site ou secteur
	@Enumerated(EnumType.STRING)
	private TypeSite typeSite;
	private String nom;

	// A défaut, S (site) ou T (secteur), département et numéro d’ordre de
	// création ex: S-77-01.
	private String code;
	private String departement;
	private String coordonneesGeographique;
	// Station météo : code Météo France et coordonnées géographiques ;
	private String stationMeteo;
	@OneToMany(mappedBy="site")
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

	public List<Ouvrage> getOuvrages() {
		return ouvrages;
	}

	public void setOuvrages(List<Ouvrage> ouvrages) {
		this.ouvrages = ouvrages;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", typeSite=" + typeSite + ", nom=" + nom + ", code=" + code + ", departement=" + departement
				+ ", coordonneesGeographique=" + coordonneesGeographique + ", stationMeteo=" + stationMeteo
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((coordonneesGeographique == null) ? 0 : coordonneesGeographique.hashCode());
		result = prime * result + ((departement == null) ? 0 : departement.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((stationMeteo == null) ? 0 : stationMeteo.hashCode());
		result = prime * result + ((typeSite == null) ? 0 : typeSite.hashCode());
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (coordonneesGeographique == null) {
			if (other.coordonneesGeographique != null)
				return false;
		} else if (!coordonneesGeographique.equals(other.coordonneesGeographique))
			return false;
		if (departement == null) {
			if (other.departement != null)
				return false;
		} else if (!departement.equals(other.departement))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (stationMeteo == null) {
			if (other.stationMeteo != null)
				return false;
		} else if (!stationMeteo.equals(other.stationMeteo))
			return false;
		if (typeSite != other.typeSite)
			return false;
		return true;
	}

	
}
