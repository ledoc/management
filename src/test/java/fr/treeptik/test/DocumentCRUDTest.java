package fr.treeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
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
import fr.treeptik.model.Document;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.DocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class DocumentCRUDTest {

	private Logger logger = Logger.getLogger(DocumentCRUDTest.class);
	@Inject
	private DocumentService documentService;

	@Test
	public void testCRUDDocument() throws Exception {
		logger.info("--testCRUDMesure --");

		Date date = new Date();
		String nom = "document Top Secret";
		Integer taille = 100;
		Ouvrage ouvrage = null;

		Date date2 = new Date();
		String nom2 = "document Top Taupe Secret";
		Integer taille2 = 10000;
		Ouvrage ouvrage2 = null;

		Document document = new Document();

		document.setDate(date);
		document.setNom(nom);
		document.setTaille(taille);
		document.setOuvrage(ouvrage);

		documentService.create(document);
		Integer id = document.getId();

		document = documentService.findById(id);
		assertNotNull("L'objet doit exister", document);

		List<Document> firstFindAll = documentService.findAll();

		Document document2 = new Document();
		document2.setDate(date2);
		document2.setNom(nom2);
		document2.setTaille(taille2);
		document2.setOuvrage(ouvrage2);

		documentService.create(document2);

		document2.setNom("Anonymous");
		;
		documentService.update(document2);

		document2 = documentService.findById(id);
		assertNotNull("L'objet doit exister", document2);

		List<Document> secondFindAll = documentService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size()) {
			fail("La collection doit être augmentée de 1");
		}

		documentService.remove(document);

		document = documentService.findById(id);
		assertNull("L'objet ne doit pas exister", document);

		List<Document> thirdFindAll = documentService.findAll();

		if (firstFindAll.size() != thirdFindAll.size()) {
			fail("La collection doit avoir la même taille qu'à l'origine");

		}
		documentService.remove(document2.getId());
	}
}
