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

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
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

}
