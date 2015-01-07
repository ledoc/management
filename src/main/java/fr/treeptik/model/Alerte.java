package fr.treeptik.model;

public class Alerte {

	private String code; 
	private String intitule;
	private String type;
	// la tendance : + - > < =
	private String tendance; 	
	private String valeurCritique; 
	private String modeEnvoi;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTendance() {
		return tendance;
	}
	public void setTendance(String tendance) {
		this.tendance = tendance;
	}
	public String getValeurCritique() {
		return valeurCritique;
	}
	public void setValeurCritique(String valeurCritique) {
		this.valeurCritique = valeurCritique;
	}
	public String getModeEnvoi() {
		return modeEnvoi;
	}
	public void setModeEnvoi(String modeEnvoi) {
		this.modeEnvoi = modeEnvoi;
	} 

	

}
