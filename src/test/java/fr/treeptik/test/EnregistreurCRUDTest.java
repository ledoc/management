package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
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
import fr.treeptik.model.Alerte;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.TrameDW;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.util.XMLRPCUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class EnregistreurCRUDTest {

	private Logger logger = Logger.getLogger(EnregistreurCRUDTest.class);
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private XMLRPCUtils xmlRPCUtils;

	private static final String MID = "gps://ORANGE/+33781916177";

//	@Test
	public void createEnregistreurFromXMLRPC() throws Exception {
		String sessionKey = xmlRPCUtils.openSession();
		Object[] listEnregistreursXMLRPC = xmlRPCUtils.enregistreurList(sessionKey);

		Enregistreur enregistreur = null;
		for (Object enregistreurXmlRpc : listEnregistreursXMLRPC) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> enregistreurHashMap = (HashMap<String, Object>) enregistreurXmlRpc;

			enregistreur = new Enregistreur(enregistreurHashMap);
			logger.info(enregistreur);
			enregistreurService.create(enregistreur);
		}
	}

	// @Test
	public void testGetEnregistreur() throws Exception {

		logger.info("--testGetEnregistreur --");
		Enregistreur enregistreur = enregistreurService.findByMid(MID);
		System.out.println(enregistreur);
		Enregistreur enregistreur2 = enregistreurService.findByMidWithJoinFechTrameDWs(MID);
		List<TrameDW> trameDWs = enregistreur2.getTrameDWs();
		System.out.println(trameDWs);
	}

	@Test
	public void testCRUDMesure() throws Exception {
		logger.info("--testCRUDMesure --");

		String mid = "gps://ORANGE/+33781916177";
		Boolean maintenance = false;
		String modem = "freebox";
		String trasmission = "GPRS";
		String sim = "1234567";
		String batterie = "midas";
		Integer niveauBatterie = 95;
		String panneauSolaire = "solar";
		String sonde = "amadeus";
		String croquis = "mycroquis.jpeg";
		List<Alerte> alertesActives = null;

		String mid2 = "gps://BOUYGUES/+33781916177";
		Boolean maintenance2 = false;
		String modem2 = "IRIDIUM";
		String trasmission2 = "Satellite";
		String sim2 = "685345";
		String batterie2 = "goodyear";
		Integer niveauBatterie2 = 40;
		String panneauSolaire2 = "eclypsia";
		String sonde2 = "ferik";
		String croquis2 = "eskis.jpeg";
		List<Alerte> alertesActives2 = null;

		Enregistreur enregistreur = enregistreurService.findByMid(MID);

		enregistreur.setAlertesActives(alertesActives);
		enregistreur.setBatterie(batterie);
		enregistreur.setCroquis(croquis);
		enregistreur.setMaintenance(maintenance);
		enregistreur.setMid(mid);
		enregistreur.setModem(modem);
		enregistreur.setNiveauBatterie(niveauBatterie);
		enregistreur.setPanneauSolaire(panneauSolaire);
		enregistreur.setSim(sim);
		enregistreur.setSonde(sonde);
		enregistreur.setTrasmission(trasmission);

		enregistreurService.update(enregistreur);
		Integer id = enregistreur.getId();

		enregistreur = enregistreurService.findById(id);
		assertNotNull("L'objet doit exister", enregistreur);

		List<Enregistreur> firstFindAll = enregistreurService.findAll();

		Enregistreur enregistreur2 = enregistreurService.findByMidWithJoinFechTrameDWs(MID);

		enregistreur2.setAlertesActives(alertesActives2);
		enregistreur2.setBatterie(batterie2);
		enregistreur2.setCroquis(croquis2);
		enregistreur2.setMaintenance(maintenance2);
		enregistreur2.setMid(mid2);
		enregistreur2.setModem(modem2);
		enregistreur2.setNiveauBatterie(niveauBatterie2);
		enregistreur2.setPanneauSolaire(panneauSolaire2);
		enregistreur2.setSim(sim2);
		enregistreur2.setSonde(sonde2);
		enregistreur2.setTrasmission(trasmission2);

		enregistreurService.update(enregistreur2);
		
		
		String mid3 = "gps://SFR/+33781916177";
		Boolean maintenance3 = false;
		String modem3 = "SFR";
		String trasmission3 = "SMS";
		
		Enregistreur enregistreur3 = new Enregistreur();
		
		enregistreur3.setMid(mid3);
		enregistreur3.setMaintenance(maintenance3);
		enregistreur3.setModem(modem3);
		enregistreur3.setTrasmission(trasmission3);
		
		enregistreurService.create(enregistreur3);
		
		
		enregistreur2 = enregistreurService.findById(id);
		assertNotNull("L'objet doit exister", enregistreur2);

		List<Enregistreur> secondFindAll = enregistreurService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}

		enregistreurService.remove(enregistreur);

		enregistreur = enregistreurService.findById(id);
		assertNull("L'objet ne doit pas exister", enregistreur);

		List<Enregistreur> thirdFindAll = enregistreurService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		
		enregistreurService.remove(enregistreur3.getId());
	}
}
