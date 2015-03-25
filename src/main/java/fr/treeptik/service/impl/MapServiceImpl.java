package fr.treeptik.service.impl;

import org.springframework.stereotype.Service;

import fr.treeptik.generic.interfaces.HasGeoCoords;
import fr.treeptik.service.MapService;

@Service
public class MapServiceImpl implements MapService {

	@Override
	public boolean isGeoLocalised(HasGeoCoords coord) {
		return coord.getLongitude() != null && coord.getLatitude() != null;
	}
}
