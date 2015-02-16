package fr.treeptik.test;

import java.util.ArrayList;
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
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.OuvrageService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class OuvrageCRUDTest {

	private Logger logger = Logger.getLogger(OuvrageCRUDTest.class);
	@Inject
	private MesureService mesureService;
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private OuvrageService ouvrageService;

	private static final String MID = "gps://ORANGE/+33781916177";

	 @Test
	public void testCRUDNappeSouterraine() throws Exception {
		logger.info("--testCRUDNappeSouterraine --");
		
		 this.createNappe();
		
		Ouvrage nappe = this.updateNappe();

		Mesure mesure = this.createMesure(enregistreurService.findByMidWithJoinFetchTrameDWs(MID), nappe);

		this.addMesure(mesure, nappe);

	}

	private void addMesure(Mesure mesure, Ouvrage nappe) {
		try {
			nappe = ouvrageService.findByIdWithJoinFetchMesures(nappe.getId());
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

	private Ouvrage createNappe() {
		Ouvrage nappe = new Ouvrage();
		nappe.setCodeOuvrage("Pz12");
		nappe.setRattachement(false);
//		nappe.setSite("1001");
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

//		nappe.setNiveauManuel(createMesureManuel());
		nappe.setNumeroBSS("0000 x 00000 0");
//		nappe.setTypeOuvrage(TypeOuvrage.NAPPE_SOUTERRAINE);

		try {
			ouvrageService.create(nappe);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nappe;
	}

	private Mesure createMesure(Enregistreur enregistreur, Ouvrage nappe) throws ServiceException {
		if(enregistreur.getTrameDWs() == null){
			throw new ServiceException("Pas de trames DW à analyser");
		}
		List<TrameDW> trameDWs = enregistreur.getTrameDWs();
		TrameDW trameDW = trameDWs.get(trameDWs.size() - 1);

		float derniere_HauteurEau = 1F;

		Mesure mesure = null;
		try {
//			mesure = new Mesure(null, TypeMesure.NIVEAUDEAU, new Date(), ouvrageService.findById(nappe.getId()),
//					mesureService.conversionHauteurEau_CoteAltimetrique(
//							mesureService.conversionSignalElectrique_HauteurEau(trameDW.getSignalBrut(),
//									nappe.getMesureProfondeur()), nappe.getNiveauManuel().getValeur(),
//							derniere_HauteurEau));
			mesure = mesureService.create(mesure);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return mesure;
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
			// Enregistreur enregistreur = enregistreurService.findByMidWithJoinFechTrameDWs(MID);
			Enregistreur enregistreur = enregistreurService.findByMid(MID);
			System.out.println(enregistreur);

			nappe.getEnregistreurs().add(enregistreur);

			ouvrageService.update(nappe);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nappe;
	}

}
