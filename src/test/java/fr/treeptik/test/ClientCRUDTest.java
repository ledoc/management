package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.model.Client;
import fr.treeptik.model.Etablissement;
import fr.treeptik.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class ClientCRUDTest {

	private Logger logger = Logger.getLogger(ClientCRUDTest.class);
	@Inject
	private ClientService clientService;

	// @Test
	public void testCRUDClient() throws Exception {
		logger.info("--testCRUDMesure --");

		String nom = "bleu";
		String identifiant = "bleu1";
		String prenom = "grand";
		String posteOccupe = "sousfifres";
		String telFixe = "0123456789";
		String telPortable = "0623456789";
		String mail1 = "h.fontbonne@teeptik.fr";
		String mail2 = "mymailperso@hotmail.com";
		String motDePasse = "aaa";
		List<Etablissement> etablissements = null; 
		
		String nom2 = "mayol";
		String identifiant2 = "mayol1";
		String prenom2 = "jacques";
		String posteOccupe2 = "GrandManitou";
		String telFixe2 = "2123456789";
		String telPortable2 = "2623456789";
		String mail12 = "h.fontbonne2@teeptik.fr";
		String mail22 = "mymailperso2@hotmail.com";
		String motDePasse2 = "aaa";
		List<Etablissement> etablissements2 = null; 
		
		

		

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
		client.setEtablissements(etablissements);
		
		clientService.create(client);
		Integer id = client.getId();

		client = clientService.findById(id);
		assertNotNull("L'objet doit exister", client);

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
		client2.setEtablissements(etablissements2);

		clientService.create(client2);

		client2.setNom("Anonymous");;
		clientService.update(client2);

		client2 = clientService.findById(id);
		assertNotNull("L'objet doit exister", client2);

		List<Client> secondFindAll = clientService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()){
			fail("La collection doit être augmentée de 1");			
		}

		clientService.remove(client);

		client = clientService.findById(id);
		assertNull("L'objet ne doit pas exister", client);

		List<Client> thirdFindAll = clientService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()){
			fail("La collection doit avoir la même taille qu'à l'origine");
			
		}
		clientService.remove(client2.getId());
	}
}
