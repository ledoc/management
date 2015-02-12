package fr.treeptik.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
public class Ouvrage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Nom de la nappe / cours d'eau / réservoir / bassin
	private String nom;
	private String codeOuvrage;
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	@ManyToOne
	private TypeOuvrage typeOuvrage;
	@ManyToOne
	@JsonIgnore
	private Site site;
	private String coordonneesGeographique;
	private Float latitude;
	private Float longitude;
	/**
	 * Nivellement général de la France
	 */
	// Côte repère NGF : à indiquer
	private Float coteRepereNGF;

	private String commentaire;
	// Si l'ouvrage est "lié" à un autre
	private Boolean asservissement;
	// Ouvrage auquel il est asservi

	@ManyToOne
	private Ouvrage ouvrageMaitre;
	@JsonIgnore
	@OneToMany(mappedBy = "ouvrageMaitre")
	private List<Ouvrage> ouvrageFils;

	private String photo;
	private String croquisDynamique;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ouvrage")
	private List<Enregistreur> enregistreurs;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "ouvrage")
	private List<Mesure> mesures;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ouvrage")
	private List<Alerte> alertes;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "ouvrage")
	private List<Document> documents;

	/**
	 * Spécifique À EAU DE SURFACE TODO : comprendre :( Côte Sol « berge » : à
	 * indiquer
	 */
	private Float coteSol;

	/**
	 * Spécifique à NAPPE SOUTERRAINE
	 * 
	 */
	private String numeroBSS;

	// Mesure 1 (Repère NGF / Sol) : à indiquer
	private Float mesureRepereNGFSol;
	// Mesure 2 (Profondeur) : à indiquer
	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	private Float mesureProfondeur;

	// Côte Sol NGF : à calculer = côte repère NGF – Mesure 1
	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol
	 */
	private Float coteSolNGF;

	public Ouvrage() {
		super();
	}

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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Boolean getAsservissement() {
		return asservissement;
	}

	public String getCoordonneesGeographique() {
		return coordonneesGeographique;
	}

	public void setCoordonneesGeographique(String coordonneesGeographique) {
		this.coordonneesGeographique = coordonneesGeographique;
		this.setLatitude();
		this.setLongitude();
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude() {
		this.latitude = Float.valueOf(this.coordonneesGeographique.substring(0,
				this.coordonneesGeographique.lastIndexOf("/")));
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude() {

		this.longitude = Float.valueOf(this.coordonneesGeographique.substring(
				this.coordonneesGeographique.lastIndexOf("/") + 1,
				this.coordonneesGeographique.length()));
	}

	public void setAsservissement(Boolean asservissement) {
		this.asservissement = asservissement;
	}

	public Ouvrage getOuvrageMaitre() {
		return ouvrageMaitre;
	}

	public void setOuvrageMaitre(Ouvrage ouvrageMaitre) {
		this.ouvrageMaitre = ouvrageMaitre;
	}

	/**
	 * Nivellement général de la France
	 */
	public Float getCoteRepereNGF() {
		return coteRepereNGF;
	}

	/**
	 * Nivellement général de la France
	 */
	public void setCoteRepereNGF(Float coteRepereNGF) {
		this.coteRepereNGF = coteRepereNGF;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCodeOuvrage() {
		return codeOuvrage;
	}

	public void setCodeOuvrage(String codeOuvrage) {
		this.codeOuvrage = codeOuvrage;
	}

	public List<Enregistreur> getEnregistreurs() {
		return enregistreurs;
	}

	public void setEnregistreurs(List<Enregistreur> enregistreurs) {
		this.enregistreurs = enregistreurs;
	}

	public List<Mesure> getMesures() {
		return mesures;
	}

	public void setMesures(List<Mesure> mesures) {
		this.mesures = mesures;
	}

	public List<Alerte> getAlertes() {
		return alertes;
	}

	public void setAlertes(List<Alerte> alertes) {
		this.alertes = alertes;
	}

	public String getCroquisDynamique() {
		return croquisDynamique;
	}

	public void setCroquisDynamique(String croquisDynamique) {
		this.croquisDynamique = croquisDynamique;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public TypeOuvrage getTypeOuvrage() {
		return typeOuvrage;
	}

	public void setTypeOuvrage(TypeOuvrage typeOuvrage) {
		this.typeOuvrage = typeOuvrage;
	}

	/**
	 * Mesure spécifique aux eaux de surface
	 * 
	 * @return
	 */
	public Float getCoteSol() {
		return coteSol;
	}

	/**
	 * Mesure spécifique aux eaux de surface
	 * 
	 * @return
	 */
	public void setCoteSol(Float coteSol) {
		this.coteSol = coteSol;
	}

	public String getNumeroBSS() {
		return numeroBSS;
	}

	public void setNumeroBSS(String numeroBSS) {
		this.numeroBSS = numeroBSS;
	}

	public Float getMesureRepereNGFSol() {
		return mesureRepereNGFSol;
	}

	public void setMesureRepereNGFSol(Float mesureRepereNGFSol) {
		this.mesureRepereNGFSol = mesureRepereNGFSol;
	}

	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	public Float getMesureProfondeur() {
		return mesureProfondeur;
	}

	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	public void setMesureProfondeur(Float mesureProfondeur) {
		this.mesureProfondeur = mesureProfondeur;
	}

	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol (mesure1)
	 */
	public Float getCoteSolNGF() {
		return coteSolNGF;
	}

	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol (mesure1)
	 */
	public void setCoteSolNGF(Float coteSolNGF) {
		this.coteSolNGF = coteSolNGF;
	}


	@Override
	public String toString() {
		return "Ouvrage [id=" + id + ", nom=" + nom + ", codeOuvrage="
				+ codeOuvrage + ", typeOuvrage=" + typeOuvrage + ", site="
				+ site + ", coordonneesGeographique=" + coordonneesGeographique
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", coteRepereNGF=" + coteRepereNGF + ", commentaire="
				+ commentaire + ", asservissement=" + asservissement
				+ ", photo=" + photo + ", croquisDynamique=" + croquisDynamique
				+ ", coteSol=" + coteSol + ", numeroBSS=" + numeroBSS
				+ ", mesureRepereNGFSol=" + mesureRepereNGFSol
				+ ", mesureProfondeur=" + mesureProfondeur + ", coteSolNGF="
				+ coteSolNGF + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codeOuvrage == null) ? 0 : codeOuvrage.toUpperCase()
						.hashCode());
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
		Ouvrage other = (Ouvrage) obj;
		if (codeOuvrage == null) {
			if (other.codeOuvrage != null)
				return false;
		} else if (!codeOuvrage.equalsIgnoreCase(other.codeOuvrage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Ouvrage> getOuvrageFils() {
		return ouvrageFils;
	}

	public void setOuvrageFils(List<Ouvrage> ouvrageFils) {
		this.ouvrageFils = ouvrageFils;
	}

}
