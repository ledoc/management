package fr.treeptik.model.deveryware;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.treeptik.model.TrameDW;

@Entity
public class Mobile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private boolean valid;

	/**
	 * TODO : trouver la classe de cette bete là
	 */

	// private Class[] seamless;
	private int period;
	private int localizableStatus;
	private String clientName;
	private String mid;
	private int until;
	private String pid;
	private String comment;
	private String type;
	private String userName;
	@OneToMany(mappedBy = "mobile")
	private List<TrameDW> trameDWs;

	// private String server;

	public Mobile() {
		super();
	}

	public Mobile(HashMap<String, Object> xmlrpcHashMap) {
		super();
		this.valid = (boolean) xmlrpcHashMap.get("valid");
		// this.seamless = xmlrpcHashMap.get("seamless");
		this.period = (int) xmlrpcHashMap.get("period");
		this.localizableStatus = (int) xmlrpcHashMap.get("localizableStatus");
		this.clientName = (String) xmlrpcHashMap.get("clientName");
		this.mid = (String) xmlrpcHashMap.get("mid");
		this.until = (int) xmlrpcHashMap.get("until");
		this.pid = (String) xmlrpcHashMap.get("pid");
		this.comment = (String) xmlrpcHashMap.get("comment");
		this.type = (String) xmlrpcHashMap.get("type");
		this.userName = (String) xmlrpcHashMap.get("userName");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getUntil() {
		return until;
	}

	public void setUntil(int until) {
		this.until = until;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLocalizableStatus() {
		return localizableStatus;
	}

	public void setLocalizableStatus(int localizableStatus) {
		this.localizableStatus = localizableStatus;
	}

	public List<TrameDW> getTrameDWs() {
		return trameDWs;
	}

	public void setTrameDWs(List<TrameDW> trameDWs) {
		this.trameDWs = trameDWs;
	}

	@Override
	public String toString() {
		return "Mobile [id=" + id + ", valid=" + valid + ", period=" + period + ", localizableStatus="
				+ localizableStatus + ", clientName=" + clientName + ", mid=" + mid + ", until=" + until + ", pid="
				+ pid + ", comment=" + comment + ", type=" + type + ", userName=" + userName + "]";
	}

	// public Object getSeamless() {
	// return seamless;
	// }
	//
	// public void setSeamless(Object seamless) {
	// this.seamless = seamless;
	// }

}
