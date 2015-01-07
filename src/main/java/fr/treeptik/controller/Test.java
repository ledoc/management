package fr.treeptik.controller;

import fr.treeptik.service.MesureService;

public class Test {

	
	
	public static void main(String[] args) {
		float intensite = 200;
		float profMax = 6;
		float dernier_calcul_Niveau_Eau = 8;
		float derniere_HauteurEau = 4;

		float hauteurEau = MesureService.conversionSignalElectrique_HauteurEau(intensite, profMax);
		System.out.println("hauteurEau = "  + hauteurEau );
		float niveauEau = MesureService.conversionHauteurEau_CoteAltimetrique(hauteurEau, dernier_calcul_Niveau_Eau, derniere_HauteurEau);
		System.out.println("niveauEau = " + niveauEau);
		
	}
	
	
}
