package fr.treeptik.service;

import fr.treeptik.generic.interfaces.HasGeoCoords;

public interface MapService {

	boolean isGeoLocalised(HasGeoCoords coord);
}
