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

@SuppressWarnings("serial")
@Entity
public class Mesure implements Serializable, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// niveaux d'eau, pluviometrie, niveau batterie
	@Enumerated(EnumType.STRING)
	private TypeMesureOrTrame typeMesure;
	private Date date;
	@ManyToOne
	private Ouvrage ouvrage;

	@ManyToOne
	private Enregistreur enregistreur;

	private Float valeur;

	public Mesure() {
		super();
	}

	public Mesure(Integer id, TypeMesureOrTrame typeMesureOrTrame, Date date,
			Ouvrage ouvrage, Float valeur) {
		super();
		this.id = id;
		this.typeMesure = typeMesureOrTrame;
		this.date = date;
		this.ouvrage = ouvrage;
		this.valeur = valeur;
	}
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeMesureOrTrame getTypeMesure() {
		return typeMesure;
	}

	public void setTypeMesure(TypeMesureOrTrame typeMesure) {
		this.typeMesure = typeMesure;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Ouvrage getOuvrage() {
		return ouvrage;
	}

	public void setOuvrage(Ouvrage ouvrage) {
		this.ouvrage = ouvrage;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}

	public Float getValeur() {
		return valeur;
	}

	public void setValeur(Float valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "Mesure [id=" + id + ", typeMesure=" + typeMesure + ", date="
				+ date + ", valeur=" + valeur + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((typeMesure == null) ? 0 : typeMesure.hashCode());
		result = prime * result + Float.floatToIntBits(valeur);
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
		Mesure other = (Mesure) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (typeMesure != other.typeMesure)
			return false;
		if (Float.floatToIntBits(valeur) != Float.floatToIntBits(other.valeur))
			return false;
		return true;
	}
	
	public Mesure cloneMe(){
		try {
			return (Mesure) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
