package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class AlerteEmise implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String codeAlerte;
	private String intitule;
	@Enumerated(EnumType.STRING)
	private TypeAlerte typeAlerte;
	@Enumerated(EnumType.STRING)
	private TendanceAlerte tendance;
	private Float seuilPreAlerte;
	private Float seuilAlerte;
	@OneToOne
	private Mesure mesureLevantAlerte;
	@Enumerated(EnumType.STRING)
	private NiveauAlerte niveauAlerte;
	private Date date;
	private Boolean acquittement;
	private Integer compteurCheckAcquittement;

	@ManyToOne
	private Capteur capteur;

	public String getCodeAlerte() {
		return codeAlerte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCodeAlerte(String code) {
		this.codeAlerte = code;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public Mesure getMesureLevantAlerte() {
		return mesureLevantAlerte;
	}

	public void setMesureLevantAlerte(Mesure mesureLevantAlerte) {
		this.mesureLevantAlerte = mesureLevantAlerte;
	}

	public NiveauAlerte getNiveauAlerte() {
		return niveauAlerte;
	}

	public void setNiveauAlerte(NiveauAlerte niveauAlerte) {
		this.niveauAlerte = niveauAlerte;
	}

	public TypeAlerte getTypeAlerte() {
		return typeAlerte;
	}

	public void setTypeAlerte(TypeAlerte typeAlerte) {
		this.typeAlerte = typeAlerte;
	}

	public TendanceAlerte getTendance() {
		return tendance;
	}

	public void setTendance(TendanceAlerte tendance) {
		this.tendance = tendance;
	}

	public Float getSeuilAlerte() {
		return seuilAlerte;
	}

	public void setSeuilAlerte(Float seuilAlerte) {
		this.seuilAlerte = seuilAlerte;
	}

	public Float getSeuilPreAlerte() {
		return seuilPreAlerte;
	}

	public void setSeuilPreAlerte(Float seuilPreAlerte) {
		this.seuilPreAlerte = seuilPreAlerte;
	}

	public Capteur getCapteur() {
		return capteur;
	}

	public void setCapteur(Capteur capteur) {
		this.capteur = capteur;
	}

	public Boolean getAcquittement() {
		return acquittement;
	}

	public void setAcquittement(Boolean acquittement) {
		this.acquittement = acquittement;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCompteurCheckAcquittement() {
		return compteurCheckAcquittement;
	}

	public void setCompteurCheckAcquittement(Integer compteurCheckAcquittement) {
		this.compteurCheckAcquittement = compteurCheckAcquittement;
	}

	@Override
	public String toString() {
		return "AlerteEmise [id=" + id + ", codeAlerte=" + codeAlerte
				+ ", intitule=" + intitule + ", typeAlerte=" + typeAlerte
				+ ", tendance=" + tendance + ", seuilPreAlerte="
				+ seuilPreAlerte + ", seuilAlerte=" + seuilAlerte
				+ ", niveauAlerte=" + niveauAlerte + ", date=" + date
				+ ", acquittement=" + acquittement
				+ ", compteurCheckAcquittement=" + compteurCheckAcquittement
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeAlerte == null) ? 0 : codeAlerte.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((intitule == null) ? 0 : intitule.hashCode());
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
		AlerteEmise other = (AlerteEmise) obj;
		if (codeAlerte == null) {
			if (other.codeAlerte != null)
				return false;
		} else if (!codeAlerte.equals(other.codeAlerte))
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
		return true;
	}

}
