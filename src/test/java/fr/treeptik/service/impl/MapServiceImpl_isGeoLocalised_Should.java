package fr.treeptik.service.impl;

import org.junit.Test;

import fr.treeptik.generic.interfaces.HasGeoCoords;
import fr.treeptik.service.MapService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MapServiceImpl_isGeoLocalised_Should {

	private MapService mapService = new MapServiceImpl();

	@Test
	public void returnedFalse_IfLatitudeIsNull() {
		HasGeoCoords coord = new HasGeoCoords() {
			@Override
			public Float getLatitude() {
				return null;
			}
			@Override
			public Float getLongitude() {
				return (float) -1.0;
			}
		};
		assertThat(mapService.isGeoLocalised(coord), is(false));
	}
	
	@Test
	public void returnedFalse_IfLongitudeIsNull() {
		HasGeoCoords coord = new HasGeoCoords() {
			@Override
			public Float getLatitude() {
				return (float) -1.0;
			}
			@Override
			public Float getLongitude() {
				return null;
			}
		};
		assertThat(mapService.isGeoLocalised(coord), is(false));
	}
	
	@Test
	public void returnedTrue_IfSetCoords() {
		HasGeoCoords coord = new HasGeoCoords() {
			@Override
			public Float getLatitude() {
				return (float) -1.0;
			}
			@Override
			public Float getLongitude() {
				return (float) -2.0;
			}
		};
		assertThat(mapService.isGeoLocalised(coord), is(true));
	}

}
