package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeSite;
import fr.treeptik.service.SiteService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class SiteCRUDTest {

	private Logger logger = Logger.getLogger(SiteCRUDTest.class);
	@Inject
	private SiteService siteService;

	@Test
	public void testCRUDSite() throws Exception {
		logger.info("--testCRUDMesure --");

		String coordonneesGeographique = "48.7736N/2.0276E";
		String nom = "secteur de meyreuil";
		String code = "T-13-01";
		String departement = "13";
		TypeSite typeSite = TypeSite.SECTEUR;
		String stationMeteo = "code Météo France + coordonnées géographiques";
		List<Ouvrage> ouvrages1 = new ArrayList<Ouvrage>();
		
		String coordonneesGeographique2 = "38.7736N/6.0276E";
		String nom2 = "secteur de velaux";
		String code2 = "T-13-02";
		String departement2 = "13";
		TypeSite typeSite2 = TypeSite.SECTEUR;
		String stationMeteo2 = "code Météo France2 + coordonnées géographiques";
		List<Ouvrage> ouvrages2 = new ArrayList<Ouvrage>();

		Site site = new Site();
		site.setCoordonneesGeographique(coordonneesGeographique);
//		site.setEtablissement(etablissement);
		site.setCodeSite(code);
		site.setDepartement(departement);
		site.setNom(nom);
		site.setOuvrages(ouvrages1);
		site.setStationMeteo(stationMeteo);
		site.setTypeSite(typeSite);

		siteService.create(site);
		Integer id = site.getId();

		site = siteService.findById(id);
		assertNotNull("L'objet doit exister", site);

		List<Site> firstFindAll = siteService.findAll();

		Site site2 = new Site();
		site2.setCoordonneesGeographique(coordonneesGeographique2);
//		site2.setEtablissement(etablissement2);
		site2.setCodeSite(code2);
		site2.setDepartement(departement2);
		site2.setNom(nom2);
		site2.setOuvrages(ouvrages2);
		site2.setStationMeteo(stationMeteo2);
		site2.setTypeSite(typeSite2);
		
		siteService.create(site2);

		site2.setNom(nom + 2);
		siteService.update(site2);

		Integer id2 = site2.getId();
		site2 = siteService.findById(id2);
		assertNotNull("L'objet doit exister", site2);

		List<Site> secondFindAll = siteService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()){
			fail("La collection doit être augmentée de 1");			
		}

//		siteService.remove(site);

		site = siteService.findById(id);
		assertNull("L'objet ne doit pas exister", site);

		List<Site> thirdFindAll = siteService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()){
			fail("La collection doit avoir la même taille qu'à l'origine");
			
		}
		siteService.remove(site2.getId());
	}
}
