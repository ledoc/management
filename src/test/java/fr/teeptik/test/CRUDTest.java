package fr.teeptik.test;

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
import fr.treeptik.model.Mesure;
import fr.treeptik.service.MesureService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class,
		ApplicationConfiguration.class, DispatcherServletConfiguration.class })
public class CRUDTest {

	private Logger logger = Logger.getLogger(CRUDTest.class);
	@Inject
	private MesureService mesureService;

	 @Test
	public void testCRUDMesure() throws Exception {
		logger.info("--testCRUDMesure --");

		Random random = new Random();
		Integer random1 = random.nextInt(10);
		Integer random2 = random1 + 1;
		Integer random3 = random1 + 2;

		Mesure mesure1 = new Mesure();
		mesure1.setType("type" + random1);
		mesure1.setDate(new Date());
		mesure1.setValeur("valeur" + random1);

		mesureService.create(mesure1);
		Integer id = mesure1.getId();

		mesure1 = mesureService.findById(id);
		assertNotNull("L'objet doit exister", mesure1);

		List<Mesure> firstFindAll = mesureService.findAll();

		Mesure mesure2 = new Mesure();

		mesure2.setType("type" + random2);
		mesure2.setDate(new Date());
		mesure2.setValeur("valeur" + random2);

		mesureService.create(mesure2);

		mesure2.setType("type" + random3);
		mesure2.setDate(new Date());
		mesure2.setValeur("valeur" + random3);

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
