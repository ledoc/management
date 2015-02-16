package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
import fr.treeptik.model.Administrateur;
import fr.treeptik.service.AdministrateurService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class AdministrateurCRUDTest {

	private Logger logger = Logger.getLogger(AdministrateurCRUDTest.class);
	@Inject
	private AdministrateurService administrateurService;

	@Test
	public void testCRUDAdministrateur() throws Exception {
		logger.info("--testCRUDMesure --");

		String nom = "solices";
		String identifiant = "solices1";
		String prenom = "master";
		String telFixe = "0123456789";
		String telPortable = "0623456789";
		String mail1 = "h.fontbonne@teeptik.fr";
		String mail2 = "mymailperso@hotmail.com";
		String motDePasse = "aaa";

		String nom2 = "solices";
		String identifiant2 = "solices2";
		String prenom2 = "master";
		String telFixe2 = "2123456789";
		String telPortable2 = "2623456789";
		String mail12 = "h.fontbonne2@teeptik.fr";
		String mail22 = "mymailperso2@hotmail.com";
		String motDePasse2 = "aaa";

		Administrateur administrateur = new Administrateur();
		administrateur.setIdentifiant(identifiant);
		administrateur.setMail1(mail1);
		administrateur.setMail2(mail2);
		administrateur.setMotDePasse(motDePasse);
		administrateur.setNom(nom);
		administrateur.setPrenom(prenom);
		administrateur.setTelFixe(telFixe);
		administrateur.setTelPortable(telPortable);

		administrateurService.create(administrateur);
		Integer id = administrateur.getId();

		administrateur = administrateurService.findById(id);
		assertNotNull("L'objet doit exister", administrateur);

		List<Administrateur> firstFindAll = administrateurService.findAll();

		Administrateur administrateur2 = new Administrateur();

		administrateur2.setIdentifiant(identifiant2);
		administrateur2.setMail1(mail12);
		administrateur2.setMail2(mail22);
		administrateur2.setMotDePasse(motDePasse2);
		administrateur2.setNom(nom2);
		administrateur2.setPrenom(prenom2);
		administrateur2.setTelFixe(telFixe2);
		administrateur2.setTelPortable(telPortable2);

		administrateurService.create(administrateur2);

		administrateur2.setNom("Anonymous");
		
		administrateurService.update(administrateur2);

		Integer id2 = administrateur2.getId();
		administrateur2 = administrateurService.findById(id2);
		assertNotNull("L'objet doit exister", administrateur2);

		List<Administrateur> secondFindAll = administrateurService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}

//		administrateurService.remove(administrateur);

		administrateur = administrateurService.findById(id);
		assertNull("L'objet ne doit pas exister", administrateur);

		List<Administrateur> thirdFindAll = administrateurService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		administrateurService.remove(administrateur2.getId());
	}
}
