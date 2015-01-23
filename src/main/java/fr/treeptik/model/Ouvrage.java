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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asservissement == null) ? 0 : asservissement.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codeSite == null) ? 0 : codeSite.hashCode());
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + Float.floatToIntBits(coteRepereNGF);
		result = prime * result + Float.floatToIntBits(coteSol);
		result = prime * result + Float.floatToIntBits(coteSolNGF);
		result = prime * result + ((croquisDynamique == null) ? 0 : croquisDynamique.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mesureEnregistreur == null) ? 0 : mesureEnregistreur.hashCode());
		result = prime * result + Float.floatToIntBits(mesureProfondeur);
		result = prime * result + Float.floatToIntBits(mesureRepereNGFSol);
		result = prime * result + ((niveauManuel == null) ? 0 : niveauManuel.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((numeroBSS == null) ? 0 : numeroBSS.hashCode());
		result = prime * result + ((ouvrageMaitre == null) ? 0 : ouvrageMaitre.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((typeOuvrage == null) ? 0 : typeOuvrage.hashCode());
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
		if (asservissement == null) {
			if (other.asservissement != null)
				return false;
		} else if (!asservissement.equals(other.asservissement))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeSite == null) {
			if (other.codeSite != null)
				return false;
		} else if (!codeSite.equals(other.codeSite))
			return false;
		if (commentaire == null) {
			if (other.commentaire != null)
				return false;
		} else if (!commentaire.equals(other.commentaire))
			return false;
		if (Float.floatToIntBits(coteRepereNGF) != Float.floatToIntBits(other.coteRepereNGF))
			return false;
		if (Float.floatToIntBits(coteSol) != Float.floatToIntBits(other.coteSol))
			return false;
		if (Float.floatToIntBits(coteSolNGF) != Float.floatToIntBits(other.coteSolNGF))
			return false;
		if (croquisDynamique == null) {
			if (other.croquisDynamique != null)
				return false;
		} else if (!croquisDynamique.equals(other.croquisDynamique))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mesureEnregistreur == null) {
			if (other.mesureEnregistreur != null)
				return false;
		} else if (!mesureEnregistreur.equals(other.mesureEnregistreur))
			return false;
		if (Float.floatToIntBits(mesureProfondeur) != Float.floatToIntBits(other.mesureProfondeur))
			return false;
		if (Float.floatToIntBits(mesureRepereNGFSol) != Float.floatToIntBits(other.mesureRepereNGFSol))
			return false;
		if (niveauManuel == null) {
			if (other.niveauManuel != null)
				return false;
		} else if (!niveauManuel.equals(other.niveauManuel))
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
		if (ouvrageMaitre == null) {
			if (other.ouvrageMaitre != null)
				return false;
		} else if (!ouvrageMaitre.equals(other.ouvrageMaitre))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (typeOuvrage != other.typeOuvrage)
			return false;
		return true;
	}

}
