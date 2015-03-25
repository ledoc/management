package fr.treeptik.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Objet utiliser pour construire les pointsd de la google map
 * @author herve
 *
 */
public class Marker {

	private float lat;
	private float lng;
	private String title;
	private String itemName;
	private Integer itemId;
	private String type;
	private String iconPath;
	private Map<String, String>  infoWindow;

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String infoWindow) {
		this.itemName = infoWindow;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Map<String, String> getInfoWindow() {
		if(infoWindow == null) {
			infoWindow = new HashMap<String, String>();
		}
		return infoWindow;
	}

	public void setInfoWindow(Map<String, String> infoWindow) {
		this.infoWindow = infoWindow;
	}

}
