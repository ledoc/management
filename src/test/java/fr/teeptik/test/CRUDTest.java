package fr.teeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.ForEachSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.model.deveryware.Mobile;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.MobileService;
import fr.treeptik.service.OuvrageService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class CRUDTest {

	private Logger logger = Logger.getLogger(CRUDTest.class);
	@Inject
	private MesureService mesureService;
	@Inject
	private MobileService mobileService;
	@Inject
	private OuvrageService ouvrageService;

	private static final String MID = "gps://ORANGE/+33781916177";

	
	 @Test
	public void testGetMesuresOfOuvrage() throws Exception {

		logger.info("--testGetMesuresOfOuvrage --");
		Ouvrage ouvrage = ouvrageService.findByIdWithJoinFechMesures(1);
		System.out.println(ouvrage);
		for (Mesure mesure : ouvrage.getMesures()) {
			System.out.println(mesure);
		}
	}
	
	
	// @Test
	public void testGetMobile() throws Exception {

		logger.info("--testGetMobile --");
		Mobile mobile = mobileService.findByMid(MID);
		System.out.println(mobile);
		Mobile mobile2 = mobileService.findByMidWithJoinFechTrameDWs(MID);
		List<TrameDW> trameDWs = mobile2.getTrameDWs();
		System.out.println(trameDWs);
	}

//	@Test
	public void testCRUDNappeSouterraine() throws Exception {
		logger.info("--testCRUDNappeSouterraine --");
//
//		this.createNappe();
//
		Ouvrage nappe = this.updateNappe();

		Mesure mesure = this.createMesure(mobileService.findByMidWithJoinFechTrameDWs(MID), nappe);

		this.addMesure(mesure, nappe);

	}

	private void addMesure(Mesure mesure, Ouvrage nappe) {
		try {
			nappe = ouvrageService.findByIdWithJoinFechMesures(nappe.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		nappe.getMesures().add(mesure);
		try {
			ouvrageService.update(nappe);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(nappe.getMesures());
	}

	// @Test
	public void testCRUDMesure() throws Exception {
		logger.info("--testCRUDMesure --");

		Random random = new Random();
		float random1 = random.nextFloat();
		float random2 = random1 + 1;
		float random3 = random1 + 2;

		Mesure mesure1 = new Mesure();
		mesure1.setType(TypeMesure.NIVEAUDEAU);
		mesure1.setDate(new Date());
		mesure1.setValeur(random1);

		mesureService.create(mesure1);
		Integer id = mesure1.getId();

		mesure1 = mesureService.findById(id);
		assertNotNull("L'objet doit exister", mesure1);

		List<Mesure> firstFindAll = mesureService.findAll();

		Mesure mesure2 = new Mesure();

		mesure2.setType(TypeMesure.NIVEAUDEAU);
		mesure2.setDate(new Date());
		mesure2.setValeur(random2);

		mesureService.create(mesure2);

		mesure2.setType(TypeMesure.NIVEAUDEAU);
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

	private Mesure createMesureManuel() {
		Mesure mesureNiveauManuel = new Mesure(null, TypeMesure.NIVEAUDEAU, new Date(), null, 3.47F);
		try {
			mesureService.create(mesureNiveauManuel);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return mesureNiveauManuel;

	}

	private Ouvrage updateNappe() {
		Ouvrage nappe = null;
		try {
			nappe = ouvrageService.findById(1);
			// Mobile mobile = mobileService.findByMidWithJoinFechTrameDWs(MID);
			Mobile mobile = mobileService.findByMid(MID);
			System.out.println(mobile);

			nappe.setMobile(mobile);

			ouvrageService.update(nappe);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nappe;
	}

	private Ouvrage createNappe() {
		Ouvrage nappe = new Ouvrage();
		nappe.setCode("Pz12");
		nappe.setAsservissement(false);
		nappe.setCodeSite("1001");
		nappe.setNom("Nappe de Brie");
		nappe.setCoteSolNGF(nappe.getCoteRepereNGF() - nappe.getMesureRepereNGFSol());
		nappe.setMesureProfondeur(15.67F);
		nappe.setMesureRepereNGFSol(0.68F);
		nappe.setCoteRepereNGF(75.15F);
		nappe.setMesures(new ArrayList<Mesure>());

		// nappe.setAlertes(null);
		// nappe.setCroquisDynamique(null);
		// nappe.setDocuments(documents);
		// nappe.setEnregistreur(enregistreur);
		// Mesure lastMesure = new Mesure();
		// nappe.setMesureEnregistreur(lastMesure);
		// nappe.setPhoto(null);
		// nappe.setOuvrageMaitre(ouvrageMaitre);

		nappe.setNiveauManuel(createMesureManuel());
		nappe.setNumeroBSS("0000 x 00000 0");
		nappe.setTypeOuvrage(TypeOuvrage.NAPPE_SOUTERRAINE);

		try {
			ouvrageService.create(nappe);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nappe;
	}

	private Mesure createMesure(Mobile mobile, Ouvrage nappe) {
		List<TrameDW> trameDWs = mobile.getTrameDWs();
		TrameDW trameDW = trameDWs.get(trameDWs.size()-1);

		float derniere_HauteurEau = 1F;

		Mesure mesure = null;
		try {
			mesure = new Mesure(null, TypeMesure.NIVEAUDEAU, new Date(), ouvrageService.findById(nappe.getId()),
					mesureService.conversionHauteurEau_CoteAltimetrique(
							mesureService.conversionSignalElectrique_HauteurEau(trameDW.getSignalBrut(),
									nappe.getMesureProfondeur()), nappe.getNiveauManuel().getValeur(),
							derniere_HauteurEau));
			mesureService.create(mesure);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return mesure;
	}
}
