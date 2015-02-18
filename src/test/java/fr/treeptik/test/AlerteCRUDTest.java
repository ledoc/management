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
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.TendanceAlerte;
import fr.treeptik.service.AlerteDescriptionService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class AlerteCRUDTest {

	private Logger logger = Logger.getLogger(AlerteCRUDTest.class);
	@Inject
	private AlerteDescriptionService alerteDescriptionService;

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

		AlerteDescription alerteDescription = new AlerteDescription();

		alerteDescription.setActivation(activation);
		alerteDescription.setEmailDEnvoi(emailOrSMSDEnvoi);
		alerteDescription.setCodeAlerte(code);
		alerteDescription.setIntitule(intitule);
//		alerte.setLienAPIDW(lienAPIDW);
//		alerte.setModeEnvoi(modeEnvoi);
		alerteDescription.setTendance(tendance);
//		alerte.setTypeAlerte(type);
		alerteDescription.setSeuilAlerte(valeurCritique);

		alerteDescriptionService.create(alerteDescription);
		Integer id = alerteDescription.getId();

		alerteDescription = alerteDescriptionService.findById(id);
		assertNotNull("L'objet doit exister", alerteDescription);

		List<AlerteDescription> firstFindAll = alerteDescriptionService.findAll();

		AlerteDescription alerte2 = new AlerteDescription();
		alerte2.setActivation(activation2);
		alerte2.setEmailDEnvoi(emailOrSMSDEnvoi2);
		alerte2.setCodeAlerte(code2);
		alerte2.setIntitule(intitule2);
//		alerte2.setLienAPIDW(lienAPIDW2);
//		alerte2.setModeEnvoi(modeEnvoi2);
		alerte2.setTendance(tendance2);
//		alerte2.setTypeAlerte(type2);
		alerte2.setSeuilAlerte(valeurCritique2);

		alerteDescriptionService.create(alerte2);

		alerte2.setCodeAlerte("ARMAGEDON");
		;
		alerteDescriptionService.update(alerte2);

		Integer id2 = alerte2.getId();
		alerte2 = alerteDescriptionService.findById(id2);
		assertNotNull("L'objet doit exister", alerte2);

		List<AlerteDescription> secondFindAll = alerteDescriptionService.findAll();
		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}

//		alerteService.remove(alerte);
		alerteDescription = alerteDescriptionService.findById(id);
		assertNull("L'objet ne doit pas exister", alerteDescription);

		List<AlerteDescription> thirdFindAll = alerteDescriptionService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		alerteDescriptionService.remove(alerte2.getId());
	}
}
