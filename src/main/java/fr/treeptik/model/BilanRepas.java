package fr.treeptik.model;

import java.util.Date;

public class BilanRepas {

	private Date date;
	private Double sommeCalories;
	private Double sommeProteines;
	private Double sommeGlucides;
	private Double sommeLipides;

	
	
	public BilanRepas() {
		super();
	}

	public BilanRepas(Date date, Double sommeCalories,
			Double sommeProteines, Double sommeGlucides, Double sommeLipides) {
		super();
		this.date = date;
		this.sommeCalories = sommeCalories;
		this.sommeProteines = sommeProteines;
		this.sommeGlucides = sommeGlucides;
		this.sommeLipides = sommeLipides;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getSommeCalories() {
		return sommeCalories;
	}

	public void setSommeCalories(Double sommeCalories) {
		this.sommeCalories = sommeCalories;
	}

	public Double getSommeProteines() {
		return sommeProteines;
	}

	public void setSommeProteines(Double sommeProteines) {
		this.sommeProteines = sommeProteines;
	}

	public Double getSommeGlucides() {
		return sommeGlucides;
	}

	public void setSommeGlucides(Double sommeGlucides) {
		this.sommeGlucides = sommeGlucides;
	}

	public Double getSommeLipides() {
		return sommeLipides;
	}

	public void setSommeLipides(Double sommeLipides) {
		this.sommeLipides = sommeLipides;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((sommeCalories == null) ? 0 : sommeCalories.hashCode());
		result = prime * result
				+ ((sommeGlucides == null) ? 0 : sommeGlucides.hashCode());
		result = prime * result
				+ ((sommeLipides == null) ? 0 : sommeLipides.hashCode());
		result = prime * result
				+ ((sommeProteines == null) ? 0 : sommeProteines.hashCode());
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
		BilanRepas other = (BilanRepas) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (sommeCalories == null) {
			if (other.sommeCalories != null)
				return false;
		} else if (!sommeCalories.equals(other.sommeCalories))
			return false;
		if (sommeGlucides == null) {
			if (other.sommeGlucides != null)
				return false;
		} else if (!sommeGlucides.equals(other.sommeGlucides))
			return false;
		if (sommeLipides == null) {
			if (other.sommeLipides != null)
				return false;
		} else if (!sommeLipides.equals(other.sommeLipides))
			return false;
		if (sommeProteines == null) {
			if (other.sommeProteines != null)
				return false;
		} else if (!sommeProteines.equals(other.sommeProteines))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BilanRepas [date=" + date + ", sommeCalories=" + sommeCalories
				+ ", sommeProteines=" + sommeProteines + ", sommeGlucides="
				+ sommeGlucides + ", sommeLipides=" + sommeLipides + "]";
	}

}
