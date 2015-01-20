package fr.treeptik.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fr.treeptik.model.User;

@Controller
public class Test {

	private static final Logger logger = Logger.getLogger(Test.class);
	private String clientName;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public static void main(String[] args) {
		float intensite = 200;
		float profMax = 6;
		float dernier_calcul_Niveau_Eau = 8;
		float derniere_HauteurEau = 4;

		// float hauteurEau =
		// MesureService.conversionSignalElectrique_HauteurEau(intensite,
		// profMax);
		// System.out.println("hauteurEau = " + hauteurEau );
		// float niveauEau =
		// MesureService.conversionHauteurEau_CoteAltimetrique(hauteurEau,
		// dernier_calcul_Niveau_Eau, derniere_HauteurEau);
		// System.out.println("niveauEau = " + niveauEau);

	}


	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String upload(Model model) {
		clientName = new String();
		model.addAttribute("user", new User());
		return "client/upload";
	}

	@RequestMapping(value = "/client/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@ModelAttribute User user,
			@RequestParam("nom") String nom, MultipartFile file) {
		
		String fileName = null;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				fileName = file.getOriginalFilename();
				String rootPath = System.getProperty("user.home");
				File dir = new File(rootPath + File.separator + nom);
				if (!dir.exists())
					dir.mkdirs();

				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Emplacement du fichier sur le serveur : "
						+ serverFile.getAbsolutePath());

				return "Upload réussie du fichier=" + fileName;
			} catch (Exception e) {
				return "échec de l'upload de  " + fileName + " => "
						+ e.getMessage();
			}
		} else {
			return "échec de l'upload " + fileName
					+ " car le ficher est vide.";
		}
	}

}
