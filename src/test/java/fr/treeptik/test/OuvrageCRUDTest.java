package fr.treeptik.test;

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
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.OuvrageService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class, ApplicationConfiguration.class,
		DispatcherServletConfiguration.class })
public class OuvrageCRUDTest {

	private Logger logger = Logger.getLogger(OuvrageCRUDTest.class);
	@Inject
	private OuvrageService ouvrageService;

	 @Test
	public void testCRUDNappeSouterraine() throws Exception {
		logger.info("--testCRUDNappeSouterraine --");
		
		 this.createNappe();
	}


	private Ouvrage createNappe() {
		Ouvrage nappe = new Ouvrage();
		nappe.setCodeOuvrage("Pz12");
		nappe.setRattachement(false);
		nappe.setCoteSolNGF(nappe.getCoteRepereNGF() - nappe.getMesureRepereNGFSol());
		nappe.setMesureProfondeur(15.67F);
		nappe.setMesureRepereNGFSol(0.68F);
		nappe.setCoteRepereNGF(75.15F);

		nappe.setNumeroBSS("0000 x 00000 0");

		try {
			ouvrageService.create(nappe);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nappe;
	}

}
