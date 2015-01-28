package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import fr.treeptik.service.ClientService;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.SiteService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class ClientCRUDTest {

	private Logger logger = Logger.getLogger(ClientCRUDTest.class);
	@Inject
	private ClientService clientService;
	@Inject
	private EtablissementService etablissementService;
	@Inject
	private SiteService siteService;
	private Etablissement etablissement = new Etablissement();
	private Etablissement etablissement2 = new Etablissement();
	private Etablissement etablissement3 = new Etablissement();
	private Etablissement etablissement4 = new Etablissement();

	@Before
	public void createfourEtablissement() throws ServiceException {
		etablissement = new Etablissement();
		etablissement2 = new Etablissement();
		etablissement3 = new Etablissement();
		etablissement4 = new Etablissement();

		String coordonneesGeographique = "48.7736N/2.0276E";
		String formeJuridique = "Association";
		String nom = "la fontaine de jouvence";
		String codeEtablissement = "AssocJouvence";
		String mail = "h.fontbonne@treeptik.fr";

		String nomSite = "secteur de meyreuil";
		String code = "T-13-01";
		String departement = "13";
		TypeSite typeSite = TypeSite.SECTEUR;
		String stationMeteo = "code Météo France + coordonnées géographiques";
		Site site = new Site();
		site.setCoordonneesGeographique(coordonneesGeographique);
		site.setCode(code);
		site.setDepartement(departement);
		site.setNom(nomSite);
		site.setStationMeteo(stationMeteo);
		site.setTypeSite(typeSite);

		site = siteService.create(site);

		List<Etablissement> etablissementList = new ArrayList<Etablissement>();
		etablissementList.add(etablissement);
		etablissementList.add(etablissement2);
		etablissementList.add(etablissement3);
		etablissementList.add(etablissement4);
		int i = 0;
		for (Etablissement etablissement : etablissementList) {
			i++;
			etablissement.setCoordonneesGeographique(coordonneesGeographique + i);
			etablissement.setFormeJuridique(formeJuridique + i);
			etablissement.setMail(mail + i);
			etablissement.setNom(nom + i);
			etablissement.setCodeEtablissement(codeEtablissement + i);
			if (i == 0) {
				siteService.findById(site.getId());
				etablissement.getSites().add(site);
			}
			etablissementService.create(etablissement);
		}
	}

	@Test
	public void testCRUDClient() throws Exception {
		logger.info("--testCRUDMesure --");

		String nom = "bleu";
		String identifiant = "bleu1";
		String prenom = "grand";
		String posteOccupe = "sousfifre";
		String telFixe = "0123456789";
		String telPortable = "0623456789";
		String mail1 = "h.fontbonne@teeptik.fr";
		String mail2 = "mymailperso@hotmail.com";
		String motDePasse = "aaa";

		String nom2 = "mayol";
		String identifiant2 = "mayol1";
		String prenom2 = "jacques";
		String posteOccupe2 = "GrandManitou";
		String telFixe2 = "2123456789";
		String telPortable2 = "2623456789";
		String mail12 = "h.fontbonne2@teeptik.fr";
		String mail22 = "mymailperso2@hotmail.com";
		String motDePasse2 = "aaa";

		Client client = new Client();
		client.setIdentifiant(identifiant);
		client.setMail1(mail1);
		client.setMail2(mail2);
		client.setMotDePasse(motDePasse);
		client.setNom(nom);
		client.setPrenom(prenom);
		client.setPosteOccupe(posteOccupe);
		client.setTelFixe(telFixe);
		client.setTelPortable(telPortable);
		etablissement = etablissementService.findById(etablissement.getId());
		client.getEtablissements().add(etablissement);

		clientService.create(client);
		Integer id = client.getId();
		client = clientService.findById(id);
		assertNotNull("L'objet doit exister", client);

		client = clientService.findByIdWithJoinFetchEtablissements(client.getId());
		etablissement3 = etablissementService.findById(etablissement3.getId());
		client.getEtablissements().add(etablissement3);
		System.out.println(client.getEtablissements());
		client = clientService.update(client);
		
		List<Client> firstFindAll = clientService.findAll();

		Client client2 = new Client();
		client2.setIdentifiant(identifiant2);
		client2.setMail1(mail12);
		client2.setMail2(mail22);
		client2.setMotDePasse(motDePasse2);
		client2.setNom(nom2);
		client2.setPrenom(prenom2);
		client.setPosteOccupe(posteOccupe2);
		client2.setTelFixe(telFixe2);
		client2.setTelPortable(telPortable2);
		clientService.create(client2);

		
		
		client2.setNom("Anonymous");
		etablissement2 = etablissementService.findById(etablissement2.getId());
		client2.getEtablissements().add(etablissement2);
		System.out.println(client2.getEtablissements());
		client2 = clientService.update(client2);



		Integer id2 = client2.getId();
		client2 = clientService.findByIdWithJoinFetchEtablissements(id2);
		assertNotNull("L'objet doit exister", client2);
		etablissement2 = etablissementService.findById(etablissement2.getId());
		Boolean remove = client2.getEtablissements().remove(etablissement2);
		System.out.println("remove : " + remove );
		clientService.update(client2);
		client2 = clientService.findByIdWithJoinFetchEtablissements(id2);
		System.out.println(client2.getEtablissements());
		List<Client> secondFindAll = clientService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}
		client = clientService.findById(id);
		clientService.remove(client);

		client = clientService.findById(id);
		assertNull("L'objet ne doit pas exister", client);

		List<Client> thirdFindAll = clientService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		clientService.remove(client2.getId());
	}
}
