package fr.treeptik.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Ouvrage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	// Nom de la nappe / cours d'eau / réservoir / bassin
	protected String nom;
	// Si l'ouvrage est "lié" à un autre
	protected Boolean asservissement;
	// Ouvrage auquel il est asservi
	@OneToOne
	protected Ouvrage ouvrageMaitre;
	// Côte repère NGF : à indiquer
	/**
	 * Nivellement général de la France
	 */
	protected float coteRepereNGF;
	// Mesure 3 (Niveau Manuel) : à indiquer dernier NM + date + accès
	// historique NM
	@OneToOne
	protected Mesure niveauManuel;
	// Mesure Enregistreur : dernière mesure relevé avec date et heure
	@OneToOne
	protected Mesure mesureEnregistreur;
	protected String photo;
	protected String code;
	protected String codeSite;
	// D-type
	// suivi de nappe souterraine, suivi d’eaux de surface (Vanne / Pompe :
	// version 2) ;
	@Enumerated(EnumType.STRING)
	protected TypeOuvrage typeOuvrage;
	@OneToOne
	protected Enregistreur enregistreur;

	protected String croquisDynamique;
	protected String commentaire;
	@OneToMany(mappedBy = "ouvrage")
	protected List<Mesure> mesures;
	@OneToMany
	protected List<Alerte> alertes;

	@OneToMany
	protected List<Document> documents;

	/**
	 * Spécifique À EAU DE SURFACE TODO : comprendre :( Côte Sol « berge » : à
	 * indiquer
	 */
	private float coteSol;

	/**
	 * Spécifique à NAPPE SOUTERRAINE
	 * 
	 */
	private String numeroBSS;

	// Mesure 1 (Repère NGF / Sol) : à indiquer
	private float mesureRepereNGFSol;
	// Mesure 2 (Profondeur) : à indiquer
	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	private float mesureProfondeur;

	// Côte Sol NGF : à calculer = côte repère NGF – Mesure 1
	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol
	 */
	private float coteSolNGF;

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
	public float getCoteRepereNGF() {
		return coteRepereNGF;
	}

	/**
	 * Nivellement général de la France
	 */
	public void setCoteRepereNGF(float coteRepereNGF) {
		this.coteRepereNGF = coteRepereNGF;
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
	public void setNiveauManuel(Mesure niveauManuel) {
		this.niveauManuel = niveauManuel;
	}

	public Mesure getMesureEnregistreur() {
		return mesureEnregistreur;
	}

	public void setMesureEnregistreur(Mesure mesureEnregistreur) {
		this.mesureEnregistreur = mesureEnregistreur;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeSite() {
		return codeSite;
	}

	public void setCodeSite(String codeSite) {
		this.codeSite = codeSite;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
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
	public float getCoteSol() {
		return coteSol;
	}

	/**
	 * Mesure spécifique aux eaux de surface
	 * 
	 * @return
	 */
	public void setCoteSol(float coteSol) {
		this.coteSol = coteSol;
	}

	public String getNumeroBSS() {
		return numeroBSS;
	}

	public void setNumeroBSS(String numeroBSS) {
		this.numeroBSS = numeroBSS;
	}

	public float getMesureRepereNGFSol() {
		return mesureRepereNGFSol;
	}

	public void setMesureRepereNGFSol(float mesureRepereNGFSol) {
		this.mesureRepereNGFSol = mesureRepereNGFSol;
	}

	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	public float getMesureProfondeur() {
		return mesureProfondeur;
	}

	/**
	 * longueur entre NGF et le fond de la nappe
	 */
	public void setMesureProfondeur(float mesureProfondeur) {
		this.mesureProfondeur = mesureProfondeur;
	}

	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol (mesure1)
	 */
	public float getCoteSolNGF() {
		return coteSolNGF;
	}

	/**
	 * this.coteRepereNGF-this.mesureRepereNGFSol (mesure1)
	 */
	public void setCoteSolNGF(float coteSolNGF) {
		this.coteSolNGF = coteSolNGF;
	}

	@Override
	public String toString() {
		return "Ouvrage [id=" + id + ", nom=" + nom + ", asservissement=" + asservissement + ", ouvrageMaitre="
				+ ouvrageMaitre + ", coteRepereNGF=" + coteRepereNGF + ", niveauManuel=" + niveauManuel
				+ ", mesureEnregistreur=" + mesureEnregistreur + ", photo=" + photo + ", code=" + code + ", codeSite="
				+ codeSite + ", typeOuvrage=" + typeOuvrage  + ", enregistreur=" + enregistreur
				+ ", croquisDynamique=" + croquisDynamique + ", commentaire=" + commentaire + ", coteSol=" + coteSol
				+ ", numeroBSS=" + numeroBSS + ", mesureRepereNGFSol=" + mesureRepereNGFSol + ", mesureProfondeur="
				+ mesureProfondeur + ", coteSolNGF=" + coteSolNGF + "]";
	}

}
