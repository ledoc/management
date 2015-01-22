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
import fr.treeptik.model.Client;
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

	private Site site;
	private Site site2;

	@Before
	public void createTwoSites() {
		String coordonneesGeographique = "48.7736N/2.0276E";
		String nom = "secteur de meyreuil";
		Client client = null;
		String code = "T-13-01";
		String departement = "13";
		TypeSite type = TypeSite.SECTEUR;
		String stationMeteo = "code Météo France + coordonnées géographiques";

		String coordonneesGeographique2 = "38.7736N/6.0276E";
		String nom2 = "secteur de velaux";
		Client client2 = null;
		String code2 = "T-13-02";
		String departement2 = "13";
		TypeSite type2 = TypeSite.SECTEUR;
		String stationMeteo2 = "code Météo France2 + coordonnées géographiques";

		site = new Site();
		site.setCoordonneesGeographique(coordonneesGeographique);
		site.setClient(client);
		site.setCode(code);
		site.setDepartement(departement);
		site.setNom(nom);
		site.setStationMeteo(stationMeteo);
		site.setType(type);

		site2 = new Site();
		site2.setCoordonneesGeographique(coordonneesGeographique2);
		site2.setClient(client2);
		site2.setCode(code2);
		site2.setDepartement(departement2);
		site2.setNom(nom2);
		site2.setStationMeteo(stationMeteo2);
		site2.setType(type2);

		try {
			siteService.create(site);
			siteService.create(site2);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCRUDEtablissement() throws Exception {
		logger.info("--testCRUDMesure --");

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

		site = siteService.findById(site.getId());

		etablissement.getSites().add(site);

		etablissementService.create(etablissement);
		Integer id = etablissement.getId();

		etablissement = etablissementService.findById(id);
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
		etablissement.setSiteWeb(siteWeb2);

		site2 = siteService.findById(site2.getId());

		etablissement2.getSites().add(site);
		etablissement2.getSites().add(site2);

		etablissementService.create(etablissement2);

		etablissement2.setNom(nom + 2);
		etablissementService.update(etablissement2);

		etablissement = etablissementService.findById(id);
		assertNotNull("L'objet doit exister", etablissement2);

		List<Etablissement> secondFindAll = etablissementService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size())
			fail("La collection doit être augmentée de 1");


		etablissement = etablissementService.findByIdWithJoinFechSites(id);
		
		etablissement.getSites().add(site);
		
		etablissementService.update(etablissement);

		etablissementService.remove(etablissement);
		
		assertNull("L'objet ne doit pas exister", etablissement);

		List<Etablissement> thirdFindAll = etablissementService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");
		}
		etablissementService.remove(etablissement2.getId());

	}
}
