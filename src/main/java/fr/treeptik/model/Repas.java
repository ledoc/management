package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Repas implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private TypeRepas typeRepas;
	@Temporal(TemporalType.DATE)
	private Date date;
	private Map<Aliment, Integer> quantiteParAliment;

	public Repas() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeRepas getTypeRepas() {
		return typeRepas;
	}

	public void setTypeRepas(TypeRepas typeRepas) {
		this.typeRepas = typeRepas;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<Aliment, Integer> getQuantiteParAliment() {
		return quantiteParAliment;
	}

	public void setQuantiteParAliment(Map<Aliment, Integer> quantiteParAliment) {
		this.quantiteParAliment = quantiteParAliment;
	}

}
