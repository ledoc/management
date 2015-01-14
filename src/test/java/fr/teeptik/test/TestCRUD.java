package fr.teeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.service.MesureService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class TestCRUD {
	
	private MesureService mesureService;
	
	
	@Test
	public void testCRUDMesure() throws Exception {

	    // Gets two random numbers
		Random random = new Random();
	    Integer random1 = random.nextInt(10);
	    Integer updateRandom = random1 + 1;

	    // Item is the domain object
	    Mesure mesure = new Mesure(1, "type" + random1, new Date(), new Enregistreur(), "valeur" + random1);

	    // The method findAll brings back all the objects from the DB
	    List<Mesure> firstFindAll = mesureService.findAll();

	    // Item gets mock values and is persisted. Id is returned
	    mesureService.create(mesure);
	    Integer id = mesure.getId();

	    // Find the created object with the given Id and makes sure it has the right values
	    mesure = mesureService.findById(id);
	    assertNotNull("L'objet doit exister", mesure);

	    // Updates the object with new random values
	    mesure = new Mesure(1, "type" + updateRandom, new Date(), new Enregistreur(), "valeur" + updateRandom);
	    mesureService.update(mesure);

	    // Find the updated object and makes sure it has the new values
	    mesure = mesureService.findById(id);
	    assertNotNull("Object should exist", mesure);

	    // Gets all the objects from the database...
	    List<Mesure> secondFindAll = mesureService.findAll();

	    // ...and makes sure there is one more object
	    if (firstFindAll.size() + 1 != secondFindAll.size()) fail("La collection doit être augmenter de 1");

	    // The object is now deleted
	    mesureService.remove(mesure);

	    // Find the object and make sure it has been removed
	    mesure = mesureService.findById(id);
	    assertNull("L'objet ne doit pas exister", mesure);

	    // Gets all the objects from the database...
	    List<Mesure> thirdFindAll = mesureService.findAll();

	    // ...and makes sure we have the original size
	    if (firstFindAll.size() != thirdFindAll.size()) fail("La collection doit avoir la même taille qu'à l'origine");
	}
}
