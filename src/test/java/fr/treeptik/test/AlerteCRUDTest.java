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
import fr.treeptik.model.Alerte;
import fr.treeptik.model.TendanceAlerte;
import fr.treeptik.service.AlerteService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class AlerteCRUDTest {

	private Logger logger = Logger.getLogger(AlerteCRUDTest.class);
	@Inject
	private AlerteService alerteService;

	@Test
	public void testCRUDAlerte() throws Exception {
		logger.info("--testCRUDMesure --");

		String code = "Tsunami";
		Boolean activation = true;
		String emailOrSMSDEnvoi = "h.fontbonne@treeptik.fr";
		String intitule = "Données";
		String lienAPIDW = "/solices/donnéesDW";
		String modeEnvoi = "email";
		TendanceAlerte tendance = TendanceAlerte.EGAL;
		String type = "Niveau haut";
		float valeurCritique = 4.6F;

		String code2 = "Godzilla";
		Boolean activation2 = true;
		String emailOrSMSDEnvoi2 = "0123456789";
		String intitule2 = "Données";
		String lienAPIDW2 = "/solices/donnéesDW2";
		String modeEnvoi2 = "SMS";
		TendanceAlerte tendance2 = TendanceAlerte.SUPERIEUR;
		String type2 = "inondation";
		float valeurCritique2 = 7.6F;

		Alerte alerte = new Alerte();

		alerte.setActivation(activation);
		alerte.setEmailDEnvoi(emailOrSMSDEnvoi);
		alerte.setCodeAlerte(code);
		alerte.setIntitule(intitule);
		alerte.setLienAPIDW(lienAPIDW);
		alerte.setModeEnvoi(modeEnvoi);
		alerte.setTendance(tendance);
		alerte.setTypeAlerte(type);
		alerte.setValeurCritique(valeurCritique);

		alerteService.create(alerte);
		Integer id = alerte.getId();

		alerte = alerteService.findById(id);
		assertNotNull("L'objet doit exister", alerte);

		List<Alerte> firstFindAll = alerteService.findAll();

		Alerte alerte2 = new Alerte();
		alerte2.setActivation(activation2);
		alerte2.setEmailDEnvoi(emailOrSMSDEnvoi2);
		alerte2.setCodeAlerte(code2);
		alerte2.setIntitule(intitule2);
		alerte2.setLienAPIDW(lienAPIDW2);
		alerte2.setModeEnvoi(modeEnvoi2);
		alerte2.setTendance(tendance2);
		alerte2.setTypeAlerte(type2);
		alerte2.setValeurCritique(valeurCritique2);

		alerteService.create(alerte2);

		alerte2.setCodeAlerte("ARMAGEDON");
		;
		alerteService.update(alerte2);

		Integer id2 = alerte2.getId();
		alerte2 = alerteService.findById(id2);
		assertNotNull("L'objet doit exister", alerte2);

		List<Alerte> secondFindAll = alerteService.findAll();
		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}

//		alerteService.remove(alerte);
		alerte = alerteService.findById(id);
		assertNull("L'objet ne doit pas exister", alerte);

		List<Alerte> thirdFindAll = alerteService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		alerteService.remove(alerte2.getId());
	}
}
