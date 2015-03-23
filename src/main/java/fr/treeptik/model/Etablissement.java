package fr.treeptik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import fr.treeptik.generic.interfaces.HasGeoCoords;

@SuppressWarnings("serial")
@Entity
public class Etablissement implements Serializable, HasGeoCoords {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nom;
	// applicatif : par défaut le nom ou l'entité
	private String codeEtablissement;
	private String coordonneesGeographique;
	private String coordonneesLambert;
	private Float latitude;
	private Float longitude;
	private String formeJuridique;
	private String siret;
	private String telephone;
	private String mail;
	private String siteWeb;
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="etablissement")
	private List<Site> sites;
	@ManyToMany(mappedBy="etablissements")
	private List<Client> clients;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeEtablissement() {
		return codeEtablissement;
	}

	public void setCodeEtablissement(String codeEtablissement) {
		this.codeEtablissement = codeEtablissement;
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

	public String getFormeJuridique() {
		return formeJuridique;
	}

	public void setFormeJuridique(String formeJuridique) {
		this.formeJuridique = formeJuridique;
		this.setLatitude();
		this.setLongitude();
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<Site> getSites() {
		if (this.sites == null) {
			this.sites = new ArrayList<Site>();
		}
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	@Override
	public String toString() {
		return "Etablissement [id=" + id + ", nom=" + nom
				+ ", codeEtablissement=" + codeEtablissement
				+ ", coordonneesGeographique=" + coordonneesGeographique
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", formeJuridique=" + formeJuridique + ", siret=" + siret
				+ ", telephone=" + telephone + ", mail=" + mail + ", siteWeb="
				+ siteWeb + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.toUpperCase().hashCode());
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
		Etablissement other = (Etablissement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equalsIgnoreCase(other.nom))
			return false;
		return true;
	}


}
