package fr.treeptik.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mesure {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// niveaux d'eau, pluviometrie, niveau batterie
	@Enumerated(EnumType.STRING)
	private TypeMesure type;
	private Date date;
	@ManyToOne
	private Ouvrage ouvrage;
	private float valeur;

	public Mesure() {
		super();
	}

	public Mesure(Integer id, TypeMesure type, Date date, Ouvrage ouvrage, float valeur) {
		super();
		this.id = id;
		this.type = type;
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

	public TypeMesure getType() {
		return type;
	}

	public void setType(TypeMesure type) {
		this.type = type;
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

	public float getValeur() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "Mesure [id=" + id + ", type=" + type + ", date=" + date + ", valeur=" + valeur + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type != other.type)
			return false;
		if (Float.floatToIntBits(valeur) != Float.floatToIntBits(other.valeur))
			return false;
		return true;
	}

}
