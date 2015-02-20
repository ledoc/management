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

}
