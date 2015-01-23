package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeSite;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.SiteService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class EtablissementCRUDTest {

	private Logger logger = Logger.getLogger(EtablissementCRUDTest.class);
	@Inject
	private SiteService siteService;
	@Inject
	private EtablissementService etablissementService;

	@Before
	public void createFourSites() {
		String coordonneesGeographique = "48.7736N/2.0276E";
		String nom = "secteur de meyreuil";
		String code = "T-13-01";
		String departement = "13";
		TypeSite type = TypeSite.SECTEUR;
		String stationMeteo = "code Météo France + coordonnées géographiques";

		String coordonneesGeographique2 = "38.7736N/6.0276E";
		String nom2 = "secteur de velaux";
		String code2 = "T-13-02";
		String departement2 = "13";
		TypeSite type2 = TypeSite.SECTEUR;
		String stationMeteo2 = "code Météo France2 + coordonnées géographiques";

		Site site = new Site();
		site.setCoordonneesGeographique(coordonneesGeographique);
		site.setCode(code);
		site.setDepartement(departement);
		site.setNom(nom);
		site.setStationMeteo(stationMeteo);
		site.setType(type);
		
		Site site2 = new Site();
		site2.setCoordonneesGeographique(coordonneesGeographique2);
		site2.setCode(code2);
		site2.setDepartement(departement2);
		site2.setNom(nom2);
		site2.setStationMeteo(stationMeteo2);
		site2.setType(type2);
		
		String nom3 = "secteur de Gardanne";
		String code3 = "T-13-03";
		
		Site site3 = new Site();
		site3.setCoordonneesGeographique(coordonneesGeographique2);
		site3.setCode(code3);
		site3.setNom(nom3);
		site3.setDepartement(departement2);
		site3.setStationMeteo(stationMeteo2);
		site3.setType(type2);
		
		String nom4 = "secteur de Aix";
		String code4 = "T-13-04";
		
		Site site4 = new Site();
		site3.setCoordonneesGeographique(coordonneesGeographique2);
		site3.setCode(code4);
		site3.setNom(nom4);
		site3.setDepartement(departement2);
		site3.setStationMeteo(stationMeteo2);
		site3.setType(type2);

		try {
			siteService.create(site);
			siteService.create(site2);
			siteService.create(site3);
			siteService.create(site4);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCRUDEtablissement() throws Exception {
		logger.info("--testCRUDEtablissement --");

		String coordonneesGeographique = "48.7736N/2.0276E";
		String formeJuridique = "Association";
		String nom = "la fontaine de jouvence";
		String codeEtablissement = "AssocJouvence";
		String siret = "67988-UFEIH";
		String telephone = "0123456789";
		String mail = "h.fontbonne@treeptik.fr";
		String mail2 = "a.fontbonne@treeptik.fr";
		String telephone2 = "1023456789";
		String siteWeb = "www.site1";
		String siteWeb2 = "www.site2";

		Etablissement etablissement = new Etablissement();
		etablissement.setCoordonneesGeographique(coordonneesGeographique);
		etablissement.setFormeJuridique(formeJuridique);
		etablissement.setMail(mail);
		etablissement.setNom(nom);
		etablissement.setCodeEtablissement(codeEtablissement);
		etablissement.setSiret(siret);
		etablissement.setTelephone(telephone);
		etablissement.setSiteWeb(siteWeb);

		Site site = siteService.findById(1);
		etablissement.getSites().add(site);
		etablissementService.create(etablissement);

		Integer id = etablissement.getId();
		etablissement = etablissementService.findByIdWithJoinFetchSites(id);

		assertNotNull("L'objet doit exister", etablissement);

		List<Etablissement> firstFindAll = etablissementService.findAll();

		Etablissement etablissement2 = new Etablissement();
		etablissement2.setCoordonneesGeographique(coordonneesGeographique + 1);
		etablissement2.setFormeJuridique(formeJuridique + 1);
		etablissement2.setMail(mail2);
		etablissement2.setNom(nom + 1);
		etablissement2.setCodeEtablissement(codeEtablissement + 1);
		etablissement2.setSiret(siret + 1);
		etablissement2.setTelephone(telephone2);
		etablissement2.setSiteWeb(siteWeb2);

		Site site2 = siteService.findById(2);
		Site site3 = siteService.findById(3);
		etablissement2.getSites().add(site2);
		etablissement2.getSites().add(site3);
		
		etablissementService.create(etablissement2);

		etablissement2.setNom(nom + 2);
		etablissementService.update(etablissement2);
		
		Integer id2 = etablissement2.getId();
		etablissement2 = etablissementService.findById(id2);
	
		System.out.println(etablissement2.getNom());
		assertNotNull("L'objet doit exister", etablissement2);

		List<Etablissement> secondFindAll = etablissementService.findAll();
		if (firstFindAll.size() + 1 != secondFindAll.size())
			fail("La collection doit être augmentée de 1");

		etablissement = etablissementService.findByIdWithJoinFetchSites(id);
		
		Site site4 = siteService.findById(4);
		etablissement.getSites().add(site4);
		etablissementService.update(etablissement);

		etablissement = etablissementService.findById(id);
		etablissementService.remove(etablissement);

		etablissement = etablissementService.findById(id);
		assertNull("L'objet ne doit pas exister", etablissement);

		 List<Etablissement> thirdFindAll = etablissementService.findAll();
		
		 if (firstFindAll.size() != thirdFindAll.size()) {
		 fail("La collection doit avoir la même taille qu'à l'origine");
		 }
		 etablissementService.remove(etablissement2.getId());

	}
}
