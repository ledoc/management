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
public class TrameDW implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Enregistreur enregistreur;
	@Enumerated(EnumType.STRING)
	private TypeMesureOrTrame typeTrameDW;
	private Date date;
	private Float signalBrut;
	// 1ere conversion
	private Float valeur;

	public TrameDW() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enregistreur getEnregistreur() {
		return enregistreur;
	}

	public void setEnregistreur(Enregistreur enregistreur) {
		this.enregistreur = enregistreur;
	}

	public TypeMesureOrTrame getTypeTrameDW() {
		return typeTrameDW;
	}

	public void setTypeTrameDW(TypeMesureOrTrame typeTrameDW) {
		this.typeTrameDW = typeTrameDW;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public void setValeur(Float hauteurEau) {
		this.valeur = hauteurEau;
	}

	@Override
	public String toString() {
		return "TrameDW [id=" + id + ", date=" + date + ", signalBrut="
				+ signalBrut + ", hauteurEau=" + valeur + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((enregistreur == null) ? 0 : enregistreur.hashCode());
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(signalBrut);
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
		TrameDW other = (TrameDW) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (enregistreur == null) {
			if (other.enregistreur != null)
				return false;
		} else if (!enregistreur.equals(other.enregistreur))
			return false;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(signalBrut) != Float
				.floatToIntBits(other.signalBrut))
			return false;
		return true;
	}
}
