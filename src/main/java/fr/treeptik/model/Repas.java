package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Repas implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	@OneToOne
	private NutritionBilan nutritionBilan;
	@ManyToMany
	private List<Plat> listPlats;


	public Repas() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public NutritionBilan getNutritionBilan() {
		return nutritionBilan;
	}

	public void setNutritionBilan(NutritionBilan nutritionBilan) {
		this.nutritionBilan = nutritionBilan;
	}

	public List<Plat> getListPlats() {
		return listPlats;
	}

	public void setListPlats(List<Plat> listPlats) {
		this.listPlats = listPlats;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((listPlats == null) ? 0 : listPlats.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result
				+ ((nutritionBilan == null) ? 0 : nutritionBilan.hashCode());
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
		Repas other = (Repas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listPlats == null) {
			if (other.listPlats != null)
				return false;
		} else if (!listPlats.equals(other.listPlats))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (nutritionBilan == null) {
			if (other.nutritionBilan != null)
				return false;
		} else if (!nutritionBilan.equals(other.nutritionBilan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Repas ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (nom != null ? "nom=" + nom + ", " : "")
				+ (nutritionBilan != null ? "nutritionBilan=" + nutritionBilan
						+ ", " : "")
				+ (listPlats != null ? "listPlats=" + listPlats + ", " : "")
				+ "]";
	}

}
