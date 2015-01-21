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

import fr.treeptik.model.deveryware.Mobile;

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
	protected String coteRepereNGF;
	// Mesure 3 (Niveau Manuel) : à indiquer dernier NM + date + accès
	// historique NM
	protected String niveauManuel;
	// Mesure Enregistreur : dernière mesure relevé avec date et heure
	protected String mesureEnregistreur;
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
	private Mobile mobile;

	protected String croquisDynamique;
	protected String commentaire;
	@OneToMany
	protected List<Alerte> alertes;

	@OneToMany
	protected List<Document> documents;

	/**
	 * Spécifique À EAU DE SURFACE TODO : comprendre :( Côte Sol « berge » : à
	 * indiquer
	 */
	private String coteSol;

	/**
	 * Spécifique à NAPPE SOUTERRAINE
	 * 
	 * @return
	 */
	private String numeroBSS;

	// Mesure 1 (Repère NGF / Sol) : à indiquer
	private String mesureRepereNGFSol;
	// Mesure 2 (Profondeur) : à indiquer
	private String mesureProfondeur;

	// Côte Sol NGF : à calculer = côte repère NGF – Mesure 1
	private String coteSolNGF;

	public Ouvrage() {
		super();
	}

	/**
	 * 
	 * Constructeur spécifique aux EAUX DE SURFACE
	 * 
	 * @param id
	 * @param nom
	 * @param asservissement
	 * @param ouvrageMaitre
	 * @param coteRepereNGF
	 * @param niveauManuel
	 * @param mesureEnregistreur
	 * @param photo
	 * @param code
	 * @param codeSite
	 * @param typeOuvrage
	 * @param enregistreur
	 * @param croquisDynamique
	 * @param commentaire
	 * @param alertes
	 * @param documents
	 * @param rapports
	 * @param coteSol
	 */
	public Ouvrage(Integer id, String nom, Boolean asservissement, Ouvrage ouvrageMaitre, String coteRepereNGF,
			String niveauManuel, String mesureEnregistreur, String photo, String code, String codeSite,
			TypeOuvrage typeOuvrage, Mobile mobile, Enregistreur enregistreur, String croquisDynamique,
			String commentaire, List<Alerte> alertes, List<Document> documents, String coteSol) {
		super();
		this.id = id;
		this.nom = nom;
		this.asservissement = asservissement;
		this.ouvrageMaitre = ouvrageMaitre;
		this.coteRepereNGF = coteRepereNGF;
		this.niveauManuel = niveauManuel;
		this.mesureEnregistreur = mesureEnregistreur;
		this.photo = photo;
		this.code = code;
		this.codeSite = codeSite;
		this.typeOuvrage = typeOuvrage;
		this.mobile = mobile;
		this.enregistreur = enregistreur;
		this.croquisDynamique = croquisDynamique;
		this.commentaire = commentaire;
		this.alertes = alertes;
		this.documents = documents;
		this.coteSol = coteSol;
	}

	/**
	 * Constructeur spécifique aux NAPPES SOUTERRAINES
	 * 
	 * @param id
	 * @param nom
	 * @param asservissement
	 * @param ouvrageMaitre
	 * @param coteRepereNGF
	 * @param niveauManuel
	 * @param mesureEnregistreur
	 * @param photo
	 * @param code
	 * @param codeSite
	 * @param typeOuvrage
	 * @param enregistreur
	 * @param croquisDynamique
	 * @param commentaire
	 * @param alertes
	 * @param documents
	 * @param rapports
	 * @param numeroBSS
	 * @param mesureRepereNGFSol
	 * @param mesureProfondeur
	 * @param coteSolNGF
	 */
	public Ouvrage(Integer id, String nom, Boolean asservissement, Ouvrage ouvrageMaitre, String coteRepereNGF,
			String niveauManuel, String mesureEnregistreur, String photo, String code, String codeSite,
			TypeOuvrage typeOuvrage, Mobile mobile, Enregistreur enregistreur, String croquisDynamique,
			String commentaire, List<Alerte> alertes, List<Document> documents, String numeroBSS,
			String mesureRepereNGFSol, String mesureProfondeur, String coteSolNGF) {
		super();
		this.id = id;
		this.nom = nom;
		this.asservissement = asservissement;
		this.ouvrageMaitre = ouvrageMaitre;
		this.coteRepereNGF = coteRepereNGF;
		this.niveauManuel = niveauManuel;
		this.mesureEnregistreur = mesureEnregistreur;
		this.photo = photo;
		this.code = code;
		this.codeSite = codeSite;
		this.typeOuvrage = typeOuvrage;
		this.mobile = mobile;
		this.enregistreur = enregistreur;
		this.croquisDynamique = croquisDynamique;
		this.commentaire = commentaire;
		this.alertes = alertes;
		this.documents = documents;
		this.numeroBSS = numeroBSS;
		this.mesureRepereNGFSol = mesureRepereNGFSol;
		this.mesureProfondeur = mesureProfondeur;
		this.coteSolNGF = coteSolNGF;
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

	public String getCoteRepereNGF() {
		return coteRepereNGF;
	}

	public void setCoteRepereNGF(String coteRepereNGF) {
		this.coteRepereNGF = coteRepereNGF;
	}

	public String getNiveauManuel() {
		return niveauManuel;
	}

	public void setNiveauManuel(String niveauManuel) {
		this.niveauManuel = niveauManuel;
	}

	public String getMesureEnregistreur() {
		return mesureEnregistreur;
	}

	public void setMesureEnregistreur(String mesureEnregistreur) {
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

	public Mobile getMobile() {
		return mobile;
	}

	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
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

	public String getCoteSol() {
		return coteSol;
	}

	public void setCoteSol(String coteSol) {
		this.coteSol = coteSol;
	}

	public String getNumeroBSS() {
		return numeroBSS;
	}

	public void setNumeroBSS(String numeroBSS) {
		this.numeroBSS = numeroBSS;
	}

	public String getMesureRepereNGFSol() {
		return mesureRepereNGFSol;
	}

	public void setMesureRepereNGFSol(String mesureRepereNGFSol) {
		this.mesureRepereNGFSol = mesureRepereNGFSol;
	}

	public String getMesureProfondeur() {
		return mesureProfondeur;
	}

	public void setMesureProfondeur(String mesureProfondeur) {
		this.mesureProfondeur = mesureProfondeur;
	}

	public String getCoteSolNGF() {
		return coteSolNGF;
	}

	public void setCoteSolNGF(String coteSolNGF) {
		this.coteSolNGF = coteSolNGF;
	}

}
