package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class RepasDate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date date;
	@ManyToOne
	private  Repas repas;
	
	@Transient
	private Long copyRepasId;

	
	
	public RepasDate() {
		super();
	}

	
	public RepasDate(Repas repas) {
		super();
		this.repas = repas;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Repas getRepas() {
		return repas;
	}

	public void setRepas(Repas repas) {
		this.repas = repas;
	}

	public Long getCopyRepasId() {
		return copyRepasId;
	}

	public void setCopyRepasId(Long copyRepasId) {
		this.copyRepasId = copyRepasId;
	}

	
	
	@Override
	public String toString() {
		return "RepasDate [" + (id != null ? "id=" + id + ", " : "")
				+ (date != null ? "date=" + date + ", " : "")
				+ (repas != null ? "repas=" + repas + ", " : "")
				+ (copyRepasId != null ? "copyRepasId=" + copyRepasId : "")
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyRepasId == null) ? 0 : copyRepasId.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((repas == null) ? 0 : repas.hashCode());
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
		RepasDate other = (RepasDate) obj;
		if (copyRepasId == null) {
			if (other.copyRepasId != null)
				return false;
		} else if (!copyRepasId.equals(other.copyRepasId))
			return false;
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
		if (repas == null) {
			if (other.repas != null)
				return false;
		} else if (!repas.equals(other.repas))
			return false;
		return true;
	}
	
	
	
	
}
