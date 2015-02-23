package fr.treeptik.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class AlerteDescription implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String codeAlerte;
	private Boolean activation;
	private String intitule;
	@Enumerated(EnumType.STRING)
	private TypeAlerte typeAlerte;
	@Enumerated(EnumType.STRING)
	private TendanceAlerte tendance;
	private Float seuilPreAlerte;
	private Float seuilAlerte;
	private Boolean aSurveiller;
	private Integer compteurRetourNormal;

	@ManyToOne
	private Enregistreur enregistreur;

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

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}

	public Boolean getaSurveiller() {
		return aSurveiller;
	}

	public void setaSurveiller(Boolean aSurveiller) {
		this.aSurveiller = aSurveiller;
	}

	public Integer getCompteurRetourNormal() {
		return compteurRetourNormal;
	}

	public void setCompteurRetourNormal(Integer compteurRetourNormal) {
		this.compteurRetourNormal = compteurRetourNormal;
	}

	@Override
	public String toString() {
		return "AlerteDescription [id=" + id + ", codeAlerte=" + codeAlerte
				+ ", activation=" + activation + ", intitule=" + intitule
				+ ", typeAlerte=" + typeAlerte + ", tendance=" + tendance
				+ ", seuilPreAlerte=" + seuilPreAlerte + ", seuilAlerte="
				+ seuilAlerte + ", aSurveiller=" + aSurveiller
				+ ", compteurRetourNormal=" + compteurRetourNormal + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeAlerte == null) ? 0 : codeAlerte.toLowerCase().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((intitule == null) ? 0 : intitule.toLowerCase().hashCode());
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
		AlerteDescription other = (AlerteDescription) obj;
		if (codeAlerte == null) {
			if (other.codeAlerte.toLowerCase() != null)
				return false;
		} else if (!codeAlerte.equalsIgnoreCase(other.codeAlerte))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intitule == null) {
			if (other.intitule.toLowerCase() != null)
				return false;
		} else if (!intitule.equalsIgnoreCase(other.intitule))
			return false;
		return true;
	}

	
	
	
}
