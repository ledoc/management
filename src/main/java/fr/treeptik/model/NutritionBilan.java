package fr.treeptik.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NutritionBilan {
	@Id
	@GeneratedValue
	private Long id;
	private Float proteine;
	private Float lipide;
	private Float glucide;
	private Float bcaa;
	private Float calories;

	
	
	public NutritionBilan() {
		super();
	}

	public NutritionBilan(Float proteine, Float lipide, Float glucide,
			Float bcaa, Float calories) {
		super();
		this.proteine = proteine;
		this.lipide = lipide;
		this.glucide = glucide;
		this.bcaa = bcaa;
		this.calories = calories;
	}

	public Float getProteine() {
		return proteine;
	}

	public void setProteine(Float proteine) {
		this.proteine = proteine;
	}

	public Float getLipide() {
		return lipide;
	}

	public void setLipide(Float lipide) {
		this.lipide = lipide;
	}

	public Float getGlucide() {
		return glucide;
	}

	public void setGlucide(Float glucide) {
		this.glucide = glucide;
	}

	public Float getBcaa() {
		return bcaa;
	}

	public void setBcaa(Float bcaa) {
		this.bcaa = bcaa;
	}

	public Float getCalories() {
		return calories;
	}

	public void setCalories(Float calories) {
		this.calories = calories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bcaa == null) ? 0 : bcaa.hashCode());
		result = prime * result
				+ ((calories == null) ? 0 : calories.hashCode());
		result = prime * result + ((glucide == null) ? 0 : glucide.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lipide == null) ? 0 : lipide.hashCode());
		result = prime * result
				+ ((proteine == null) ? 0 : proteine.hashCode());
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
		NutritionBilan other = (NutritionBilan) obj;
		if (bcaa == null) {
			if (other.bcaa != null)
				return false;
		} else if (!bcaa.equals(other.bcaa))
			return false;
		if (calories == null) {
			if (other.calories != null)
				return false;
		} else if (!calories.equals(other.calories))
			return false;
		if (glucide == null) {
			if (other.glucide != null)
				return false;
		} else if (!glucide.equals(other.glucide))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lipide == null) {
			if (other.lipide != null)
				return false;
		} else if (!lipide.equals(other.lipide))
			return false;
		if (proteine == null) {
			if (other.proteine != null)
				return false;
		} else if (!proteine.equals(other.proteine))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NutritionBilan [id=" + id + ", proteine=" + proteine
				+ ", lipide=" + lipide + ", glucide=" + glucide + ", bcaa="
				+ bcaa + ", calories=" + calories + "]";
	}

}
