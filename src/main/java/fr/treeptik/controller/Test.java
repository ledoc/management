package fr.treeptik.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.impl.MesureServiceImpl;

@Controller
public class Test {

	// private static final Logger logger = Logger.getLogger(Test.class);
	// private String clientName;
	//
	// public String getClientName() {
	// return clientName;
	// }
	//
	// public void setClientName(String clientName) {
	// this.clientName = clientName;
	// }
	//
	private static MesureService mesureService = new MesureServiceImpl();

	public static void main(String[] args) {

		testCoteAltimetrique();

	}

	public static void testCoteAltimetrique() {

		TrameDW trameDW = new TrameDW();

		trameDW.setSignalBrut(6.025F);
		trameDW.setTypeTrameDW(TypeMesureOrTrame.NIVEAUDEAU);

		try {
			mesureService.conversionSignalElectrique_CoteAltimetrique(trameDW);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	//
	// float intensite = 200;
	// float profMax = 6;
	// float dernier_calcul_Niveau_Eau = 8;
	// float derniere_HauteurEau = 4;
	//
	// // float hauteurEau =
	// // MesureService.conversionSignalElectrique_HauteurEau(intensite,
	// // profMax);
	// // float niveauEau =
	// // MesureService.conversionHauteurEau_CoteAltimetrique(hauteurEau,
	// // dernier_calcul_Niveau_Eau, derniere_HauteurEau);
	//
	// }

	//
	// @RequestMapping(method = RequestMethod.GET, value = "/")
	// public String upload(Model model) {
	// clientName = new String();
	// model.addAttribute("client", new Client());
	// return "client/upload";
	// }
	//
	// @RequestMapping(value = "/client/uploadFile", method =
	// RequestMethod.POST)
	// public @ResponseBody String uploadFileHandler(@ModelAttribute Client
	// client, @RequestParam("nom") String nom,
	// MultipartFile file) {
	//
	// String fileName = null;
	// if (!file.isEmpty()) {
	// try {
	// byte[] bytes = file.getBytes();
	//
	// fileName = file.getOriginalFilename();
	// String rootPath = System.getProperty("user.home");
	// File dir = new File(rootPath + File.separator + nom);
	// if (!dir.exists())
	// dir.mkdirs();
	//
	// File serverFile = new File(dir.getAbsolutePath() + File.separator +
	// fileName);
	// BufferedOutputStream stream = new BufferedOutputStream(new
	// FileOutputStream(serverFile));
	// stream.write(bytes);
	// stream.close();
	//
	// logger.info("Emplacement du fichier sur le serveur : " +
	// serverFile.getAbsolutePath());
	//
	// return "Upload réussie du fichier=" + fileName;
	// } catch (Exception e) {
	// return "échec de l'upload de  " + fileName + " => " + e.getMessage();
	// }
	// } else {
	// return "échec de l'upload " + fileName + " car le ficher est vide.";
	// }
	// }

}
