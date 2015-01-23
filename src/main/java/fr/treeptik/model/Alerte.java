package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private TendanceAlerte tendance;
	private float valeurCritique;
	//SMS Email ou les deux
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

	public TendanceAlerte getTendance() {
		return tendance;
	}

	public void setTendance(TendanceAlerte tendance) {
		this.tendance = tendance;
	}

	public float getValeurCritique() {
		return valeurCritique;
	}

	public void setValeurCritique(float valeurCritique) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EmailOrSMSDEnvoi == null) ? 0 : EmailOrSMSDEnvoi.hashCode());
		result = prime * result + ((activation == null) ? 0 : activation.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((intitule == null) ? 0 : intitule.hashCode());
		result = prime * result + ((lienAPIDW == null) ? 0 : lienAPIDW.hashCode());
		result = prime * result + ((modeEnvoi == null) ? 0 : modeEnvoi.hashCode());
		result = prime * result + ((tendance == null) ? 0 : tendance.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + Float.floatToIntBits(valeurCritique);
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
		Alerte other = (Alerte) obj;
		if (EmailOrSMSDEnvoi == null) {
			if (other.EmailOrSMSDEnvoi != null)
				return false;
		} else if (!EmailOrSMSDEnvoi.equals(other.EmailOrSMSDEnvoi))
			return false;
		if (activation == null) {
			if (other.activation != null)
				return false;
		} else if (!activation.equals(other.activation))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intitule == null) {
			if (other.intitule != null)
				return false;
		} else if (!intitule.equals(other.intitule))
			return false;
		if (lienAPIDW == null) {
			if (other.lienAPIDW != null)
				return false;
		} else if (!lienAPIDW.equals(other.lienAPIDW))
			return false;
		if (modeEnvoi == null) {
			if (other.modeEnvoi != null)
				return false;
		} else if (!modeEnvoi.equals(other.modeEnvoi))
			return false;
		if (tendance != other.tendance)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (Float.floatToIntBits(valeurCritique) != Float.floatToIntBits(other.valeurCritique))
			return false;
		return true;
	}

	
	
}
