package fr.treeptik.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.MapService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;

public class MapController_ListMarkers_Should {

	private static final String USER_LOGIN = "user1";

	private static final int SITE_ID_1 = 1;
	private static final int SITE_ID_2 = 2;
	private static final int SITE_ID_3 = 3;

	private static final int OUVRAGE_ID_1 = 11;
	private static final int OUVRAGE_ID_2 = 12;
	private static final int OUVRAGE_ID_3 = 13;

	private static final int ETABLISSEMENT_ID_1 = 21;
	private static final int ETABLISSEMENT_ID_2 = 22;
	private static final int ETABLISSEMENT_ID_3 = 23;

	@Mock
	MapService mapService;

	@Mock
	SiteService siteService;

	@Mock
	EtablissementService etablissementService;

	@Mock
	OuvrageService ouvrageService;

	@Mock
	HttpServletRequest request;

	@Mock
	SecurityContext context;
	@Mock
	Authentication authentication;

	@InjectMocks
	MapController mapController = new MapController();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		when(context.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn(USER_LOGIN);
		SecurityContextHolder.setContext(context);

		when(request.isUserInRole("ADMIN")).thenReturn(false);
	}

	@Test
	public void serviceGetAllItems_convertSitesInMarkers_IfSiteGeoLocalised()
			throws ControllerException, ServiceException {
		configSites();
		List<Marker> list = mapController.getAllItems(request);
		assertThat(list.size(), is(2));
		assertThat(list.get(0).getItemId(), is(SITE_ID_1));
		assertThat(list.get(1).getItemId(), is(SITE_ID_3));
	}

	@Test
	public void serviceGetAllItems_convertOuvragesInMarkers_IfOuvrageGeoLocalised()
			throws ControllerException, ServiceException {
		configOuvrages();
		List<Marker> list = mapController.getAllItems(request);
		assertThat(list.size(), is(2));
		assertThat(list.get(0).getItemId(), is(OUVRAGE_ID_1));
		assertThat(list.get(1).getItemId(), is(OUVRAGE_ID_3));
	}

	@Test
	public void serviceGetAllItems_convertEtablissementsInMarkers_IfEtablissementGeoLocalised()
			throws ControllerException, ServiceException {
		configEtablissements();
		List<Marker> list = mapController.getAllItems(request);
		assertThat(list.size(), is(2));
		assertThat(list.get(0).getItemId(), is(ETABLISSEMENT_ID_1));
		assertThat(list.get(1).getItemId(), is(ETABLISSEMENT_ID_3));
	}

	@Test
	public void serviceGetAllItems_convertAllInMarkers()
			throws ControllerException, ServiceException {
		configOuvrages();
		configSites();
		configEtablissements();
		List<Marker> list = mapController.getAllItems(request);
		assertThat(list.size(), is(6));
		assertThat(list.get(0).getItemId(), is(SITE_ID_1));
		assertThat(list.get(1).getItemId(), is(SITE_ID_3));
		assertThat(list.get(2).getItemId(), is(OUVRAGE_ID_1));
		assertThat(list.get(3).getItemId(), is(OUVRAGE_ID_3));
		assertThat(list.get(4).getItemId(), is(ETABLISSEMENT_ID_1));
		assertThat(list.get(5).getItemId(), is(ETABLISSEMENT_ID_3));
	}

	private void configOuvrages() throws ServiceException {
		List<Ouvrage> ouvrages = new ArrayList<Ouvrage>();
		Ouvrage ouvrage1 = ouvrageWithMinValue();
		ouvrage1.setId(OUVRAGE_ID_1);
		ouvrages.add(ouvrage1);
		Ouvrage ouvrage2 = ouvrageWithMinValue();
		ouvrage2.setId(OUVRAGE_ID_2);
		ouvrages.add(ouvrage2);
		Ouvrage ouvrage3 = ouvrageWithMinValue();
		ouvrage3.setId(OUVRAGE_ID_3);
		ouvrages.add(ouvrage3);
		when(ouvrageService.findByClientLogin(USER_LOGIN)).thenReturn(ouvrages);

		when(mapService.isGeoLocalised(ouvrage1)).thenReturn(true);
		when(mapService.isGeoLocalised(ouvrage2)).thenReturn(false);
		when(mapService.isGeoLocalised(ouvrage3)).thenReturn(true);
	}

	private Ouvrage ouvrageWithMinValue() {
		Ouvrage ouvrage = new Ouvrage();
		ouvrage.setCoordonneesGeographique("1.0/2.0");
		ouvrage.setLatitude();
		ouvrage.setLongitude();
		ouvrage.setTypeOuvrage(new TypeOuvrage());
		return ouvrage;
	}

	private void configSites() throws ServiceException {
		List<Site> sites = new ArrayList<Site>();
		Site site1 = siteWithMinValue();
		site1.setId(SITE_ID_1);
		sites.add(site1);
		Site site2 = siteWithMinValue();
		site2.setId(SITE_ID_2);
		sites.add(site2);
		Site site3 = siteWithMinValue();
		site3.setId(SITE_ID_3);
		sites.add(site3);
		when(siteService.findByClientLogin(USER_LOGIN)).thenReturn(sites);

		when(mapService.isGeoLocalised(site1)).thenReturn(true);
		when(mapService.isGeoLocalised(site2)).thenReturn(false);
		when(mapService.isGeoLocalised(site3)).thenReturn(true);
	}
	
	private Site siteWithMinValue() {
		Site site = new Site();
		site.setCoordonneesGeographique("1.0/2.0");
		site.setLatitude();
		site.setLongitude();
		return site;
	}


	private void configEtablissements() throws ServiceException {
		List<Etablissement> etablissements = new ArrayList<Etablissement>();
		Etablissement etablissement1 = etablissementWithMinValue();
		etablissement1.setId(ETABLISSEMENT_ID_1);
		etablissements.add(etablissement1);
		Etablissement etablissement2 = etablissementWithMinValue();
		etablissement2.setId(ETABLISSEMENT_ID_2);
		etablissements.add(etablissement2);
		Etablissement etablissement3 = etablissementWithMinValue();
		etablissement3.setId(ETABLISSEMENT_ID_3);
		etablissements.add(etablissement3);
		when(etablissementService.findByClientLogin(USER_LOGIN)).thenReturn(
				etablissements);

		when(mapService.isGeoLocalised(etablissement1)).thenReturn(true);
		when(mapService.isGeoLocalised(etablissement2)).thenReturn(false);
		when(mapService.isGeoLocalised(etablissement3)).thenReturn(true);
	}
	
	private Etablissement etablissementWithMinValue() {
		Etablissement etablissement = new Etablissement();
		etablissement.setCoordonneesGeographique("1.0/2.0");
		etablissement.setLatitude();
		etablissement.setLongitude();
		return etablissement;
	}

}
