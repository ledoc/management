package fr.treeptik.service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.generic.interfaces.HasGeoCoords;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;

public interface MapService {

	Marker transformOuvrageInMarker(Ouvrage ouvrage) throws ServiceException;

	Marker transformEtablissementInMarker(Etablissement etablissement)
			throws ServiceException;
	
	Marker transformSiteInMarker(Site site) throws ServiceException;

	boolean isGeoLocalised(HasGeoCoords coord);
}
