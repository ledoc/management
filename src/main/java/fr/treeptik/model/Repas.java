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
	private TypeRepas typeRepas;
	@Temporal(TemporalType.DATE)
	private Date date;
	@OneToOne
	private NutritionBilan nutritionBilan;
	@ManyToMany
	private List<Plat> listPlats;
	@Transient
	private Long copyRepasId;
	
	
	
	
	public Repas(Repas copyRepas) {
		super();
		
		this.nom = copyRepas.getNom();
		this.typeRepas = copyRepas.getTypeRepas();
		this.nutritionBilan = copyRepas.getNutritionBilan();
		this.listPlats = copyRepas.getListPlats();
		
	}

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

	public Long getCopyRepasId() {
		return copyRepasId;
	}

	public void setCopyRepasId(Long copyRepasId) {
		this.copyRepasId = copyRepasId;
	}

	@Override
	public String toString() {
		return "Repas ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (nom != null ? "nom=" + nom + ", " : "")
				+ (typeRepas != null ? "typeRepas=" + typeRepas + ", " : "")
				+ (date != null ? "date=" + date + ", " : "")
				+ (nutritionBilan != null ? "nutritionBilan=" + nutritionBilan
						+ ", " : "")
				+ (listPlats != null ? "listPlats=" + listPlats + ", " : "")
				+ (copyRepasId != null ? "copyRepasId=" + copyRepasId : "")
				+ "]";
	}


}
