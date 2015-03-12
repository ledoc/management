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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
public class Mesure implements Serializable, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TypeMesureOrTrame typeMesureOrTrame;
//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date date;
	private String unite;
	@JsonIgnore
	@ManyToOne
	private Capteur capteur;
	private Float signalBrut;
	private Float valeur;

	public Mesure() {
		super();
	}

	public Mesure(Integer id, TypeMesureOrTrame typeMesureOrTrame, Date date,
			Ouvrage ouvrage, Float valeur) {
		super();
		this.id = id;
		this.typeMesureOrTrame = typeMesureOrTrame;
		this.date = date;
		this.valeur = valeur;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeMesureOrTrame getTypeMesureOrTrame() {
		return typeMesureOrTrame;
	}

	public void setTypeMesureOrTrame(TypeMesureOrTrame typeMesureOrTrame) {
		this.typeMesureOrTrame = typeMesureOrTrame;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Capteur getCapteur() {
		return capteur;
	}

	public void setCapteur(Capteur capteur) {
		this.capteur = capteur;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public Float getSignalBrut() {
		return signalBrut;
	}

	public void setSignalBrut(Float signalBrut) {
		this.signalBrut = signalBrut;
	}

	public Float getValeur() {
		return valeur;
	}

	public void setValeur(Float valeur) {
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((typeMesureOrTrame == null) ? 0 : typeMesureOrTrame
						.hashCode());
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
		if (typeMesureOrTrame != other.typeMesureOrTrame)
			return false;
		if (Float.floatToIntBits(valeur) != Float.floatToIntBits(other.valeur))
			return false;
		return true;
	}

	public Mesure cloneMe() {
		try {
			return (Mesure) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "Mesure [id=" + id + ", typeMesureOrTrame=" + typeMesureOrTrame
				+ "capteur : " + capteur + "]";
	}

}
