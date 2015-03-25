package fr.treeptik.model.assembler;

import org.apache.log4j.Logger;

import fr.treeptik.generic.interfaces.HasGeoCoords;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;

public class MarkerAssembler {

	private static final String MARKER_TYPE_OUVRAGE = "ouvrage";
	private static final String MARKER_TYPE_ETABLISSEMENT = "etablissement";
	private static final String MARKER_TYPE_SITE = "site";

	private static final String ICON_MAP_OUVRAGE = "/img/iconMap/blue-pin-th.png";
	private static final String ICON_MAP_ETABLISSEMENT = "/img/iconMap/red-pin-th.png";
	private static final String ICON_MAP_SITE = "/img/iconMap/yellow-pin-th.png";

	private Logger logger = Logger.getLogger(MarkerAssembler.class);

	public Marker fromOuvrage(Ouvrage ouvrage) {
		logger.info("assemble from Ouvrage to Marker : " + ouvrage);
		Marker marker = new Marker();
		marker.setType(MARKER_TYPE_OUVRAGE);
		marker.setIconPath(ICON_MAP_OUVRAGE);
		writeFromHasGeoCoords(marker, ouvrage);

		marker.setItemId(ouvrage.getId());
		marker.setTitle(ouvrage.getCodeOuvrage());
		marker.setItemName(ouvrage.getTypeOuvrage().getDescription());
		return marker;
	}

	public Marker fromEtablissement(Etablissement etablissement) {
		logger.info("assemble from Etablissement to Marker : " + etablissement);
		Marker marker = new Marker();
		marker.setType(MARKER_TYPE_ETABLISSEMENT);
		marker.setIconPath(ICON_MAP_ETABLISSEMENT);
		writeFromHasGeoCoords(marker, etablissement);

		marker.setItemId(etablissement.getId());
		marker.setTitle(etablissement.getCodeEtablissement());
		marker.setItemName(etablissement.getNom());
		return marker;
	}

	public Marker fromSite(Site site) {
		logger.info("assemble from Site to Marker : " + site);
		Marker marker = new Marker();
		marker.setType(MARKER_TYPE_SITE);
		marker.setIconPath(ICON_MAP_SITE);
		writeFromHasGeoCoords(marker, site);

		marker.setItemId(site.getId());
		marker.setTitle(site.getCodeSite());
		marker.setItemName(site.getNom());
		return marker;
	}

	private void writeFromHasGeoCoords(Marker marker, HasGeoCoords coords) {
		marker.setLat(coords.getLatitude());
		marker.setLng(coords.getLongitude());
	}

}
