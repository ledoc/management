package fr.treeptik.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Ouvrage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	// Nom de la nappe / cours d'eau / réservoir / bassin
	protected String nom;
	protected String codeOuvrage;
	// D-type
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	@Enumerated(EnumType.STRING)
	protected TypeOuvrage typeOuvrage;
	@ManyToOne
	private Site site;
	/**
	 * Nivellement général de la France
	 */
	// Côte repère NGF : à indiquer
	protected Float coteRepereNGF;

	protected String commentaire;
	// Si l'ouvrage est "lié" à un autre
	protected Boolean asservissement;
	// Ouvrage auquel il est asservi
	
	@OneToOne
	protected Ouvrage ouvrageMaitre;

	protected String photo;
	protected String croquisDynamique;

	@OneToMany
	protected List<Enregistreur> enregistreurs;
	@OneToMany
	protected List<Mesure> mesures;
	@OneToMany
	protected List<Alerte> alertes;
	@OneToMany
	protected List<Document> documents;

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
		return "Ouvrage [id=" + id + ", nom=" + nom + ", asservissement="
				+ asservissement + ", ouvrageMaitre=" + ouvrageMaitre
				+ ", coteRepereNGF=" + coteRepereNGF + ", photo=" + photo
				+ ", codeOuvrage=" + codeOuvrage + ", typeOuvrage="
				+ typeOuvrage + ", croquisDynamique=" + croquisDynamique
				+ ", commentaire=" + commentaire + ", coteSol=" + coteSol
				+ ", numeroBSS=" + numeroBSS + ", mesureRepereNGFSol="
				+ mesureRepereNGFSol + ", mesureProfondeur=" + mesureProfondeur
				+ ", coteSolNGF=" + coteSolNGF + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeOuvrage == null) ? 0 : codeOuvrage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result
				+ ((numeroBSS == null) ? 0 : numeroBSS.hashCode());
		result = prime * result
				+ ((typeOuvrage == null) ? 0 : typeOuvrage.hashCode());
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
		} else if (!codeOuvrage.equals(other.codeOuvrage))
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
		if (numeroBSS == null) {
			if (other.numeroBSS != null)
				return false;
		} else if (!numeroBSS.equals(other.numeroBSS))
			return false;
		if (typeOuvrage != other.typeOuvrage)
			return false;
		return true;
	}

}
