package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alerte {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String code;
	private Boolean activation;
	private String intitule;
	private String type;
	// la tendance : + - > < =
	private String tendance;
	private String valeurCritique;
	private String modeEnvoi;
	private String EmailOrSMSDEnvoi;
	// Un lien vers l’API DW si il s’agit d’une alerte Données ou connexion, Mouvements ;
	private String lienAPIDW;

	
	/**
	 * TODO : prise de connaissance - voir pour quelle version
	 */
	// private Boolean acquittement ;

	public String getCode() {
		return code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getActivation() {
		return activation;
	}

	public void setActivation(Boolean activation) {
		this.activation = activation;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTendance() {
		return tendance;
	}

	public void setTendance(String tendance) {
		this.tendance = tendance;
	}

	public String getValeurCritique() {
		return valeurCritique;
	}

	public void setValeurCritique(String valeurCritique) {
		this.valeurCritique = valeurCritique;
	}

	public String getModeEnvoi() {
		return modeEnvoi;
	}

	public void setModeEnvoi(String modeEnvoi) {
		this.modeEnvoi = modeEnvoi;
	}

	public String getEmailOrSMSDEnvoi() {
		return EmailOrSMSDEnvoi;
	}

	public void setEmailOrSMSDEnvoi(String emailOrSMSDEnvoi) {
		EmailOrSMSDEnvoi = emailOrSMSDEnvoi;
	}

	public String getLienAPIDW() {
		return lienAPIDW;
	}

	public void setLienAPIDW(String lienAPIDW) {
		this.lienAPIDW = lienAPIDW;
	}

	
	
}
