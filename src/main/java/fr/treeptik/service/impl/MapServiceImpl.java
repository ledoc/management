package fr.treeptik.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.generic.interfaces.HasGeoCoords;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.service.MapService;

@Service
public class MapServiceImpl implements MapService {

	private Logger logger = Logger.getLogger(MapServiceImpl.class);

	@Override
	public Marker transformOuvrageInMarker(Ouvrage ouvrage)
			throws ServiceException {
		logger.info("--transformOuvrageInMarker MapServiceImpl-- ouvrage : "
				+ ouvrage);

		Marker marker = new Marker();
		marker.setType("ouvrage");
		marker.setItemId(ouvrage.getId());
		marker.setLat(ouvrage.getLatitude());
		marker.setLng(ouvrage.getLongitude());
		marker.setTitle(ouvrage.getCodeOuvrage());
		marker.setIconPath("/img/iconMap/blue-pin-th.png");
		marker.setItemName(ouvrage.getTypeOuvrage().getDescription());
		Map<String, String> infoWindow = new HashMap<String, String>();
		marker.setInfoWindow(infoWindow);
		return marker;
	}

	@Override
	public Marker transformEtablissementInMarker(Etablissement etablissement)
			throws ServiceException {
		logger.info("--transformEtablissementInMarker MapServiceImpl-- etablissement : "
				+ etablissement);

		Marker marker = new Marker();
		marker.setType("etablissement");
		marker.setItemId(etablissement.getId());
		marker.setLat(etablissement.getLatitude());
		marker.setLng(etablissement.getLongitude());
		marker.setTitle(etablissement.getCodeEtablissement());
		marker.setIconPath("/img/iconMap/red-pin-th.png");
		marker.setItemName(etablissement.getNom());
		Map<String, String> infoWindow = new HashMap<String, String>();
		marker.setInfoWindow(infoWindow);

		return marker;
	}

	@Override
	public Marker transformSiteInMarker(Site site) throws ServiceException {
		logger.info("--transformSiteInMarker MapServiceImpl-- site : " + site);

		Marker marker = new Marker();
		marker.setType("site");
		marker.setItemId(site.getId());
		marker.setLat(site.getLatitude());
		marker.setLng(site.getLongitude());
		marker.setTitle(site.getCodeSite());
		marker.setIconPath("/img/iconMap/yellow-pin-th.png");
		marker.setItemName(site.getNom());
		Map<String, String> infoWindow = new HashMap<String, String>();
		marker.setInfoWindow(infoWindow);
		return marker;
	}

	@Override
	public boolean isGeoLocalised(HasGeoCoords coord) {
		return coord.getLongitude() != null && coord.getLatitude() != null;
	}
}
