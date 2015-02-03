package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class MesureCRUDTest {

	private Logger logger = Logger.getLogger(MesureCRUDTest.class);
	@Inject
	private MesureService mesureService;
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private OuvrageService ouvrageService;

	private static final String MID = "gps://ORANGE/+33781916177";

	@Test
	public void testGetMesuresOfOuvrage() throws Exception {

		logger.info("--testGetMesuresOfOuvrage --");
		Ouvrage ouvrage = ouvrageService.findByIdWithJoinFetchMesures(1);
		System.out.println(ouvrage);
		for (Mesure mesure : ouvrage.getMesures()) {
			System.out.println(mesure);
		}
	}

	// @Test
	public void testGetEnregistreur() throws Exception {

		logger.info("--testGetEnregistreur --");
		Enregistreur enregistreur = enregistreurService.findByMid(MID);
		System.out.println(enregistreur);
		Enregistreur enregistreur2 = enregistreurService.findByMidWithJoinFetchTrameDWs(MID);
		List<TrameDW> trameDWs = enregistreur2.getTrameDWs();
		System.out.println(trameDWs);
	}

	// @Test
	public void testCRUDMesure() throws Exception {
		logger.info("--testCRUDMesure --");

		Random random = new Random();
		float random1 = random.nextFloat();
		float random2 = random1 + 1;
		float random3 = random1 + 2;

		Mesure mesure1 = new Mesure();
		mesure1.setTypeMesure(TypeMesure.NIVEAUDEAU);
		mesure1.setDate(new Date());
		mesure1.setValeur(random1);

		mesureService.create(mesure1);
		Integer id = mesure1.getId();

		mesure1 = mesureService.findById(id);
		assertNotNull("L'objet doit exister", mesure1);

		List<Mesure> firstFindAll = mesureService.findAll();

		Mesure mesure2 = new Mesure();

		mesure2.setTypeMesure(TypeMesure.NIVEAUDEAU);
		mesure2.setDate(new Date());
		mesure2.setValeur(random2);

		mesureService.create(mesure2);

		mesure2.setTypeMesure(TypeMesure.NIVEAUDEAU);
		mesure2.setDate(new Date());
		mesure2.setValeur(random3);
		mesureService.update(mesure2);

		mesure2 = mesureService.findById(id);
		assertNotNull("L'objet doit exister", mesure2);

		List<Mesure> secondFindAll = mesureService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size())
			fail("La collection doit être augmentée de 1");

		mesureService.remove(mesure2);

		mesure2 = mesureService.findById(id);
		assertNull("L'objet ne doit pas exister", mesure2);

		List<Mesure> thirdFindAll = mesureService.findAll();

		if (firstFindAll.size() != thirdFindAll.size())
			fail("La collection doit avoir la même taille qu'à l'origine");
	}

}
