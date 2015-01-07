package fr.treeptik.service;

public class MesureService {

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA) ;
	public static float conversionSignalElectrique_HauteurEau(float intensite,
			float profMax) {
		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float hauteurEau;
		hauteurEau = (profMax/16) * (intensite - 4);

		return hauteurEau;
	}

	// Ns0 = Nm0
	// Nm0 : mesure manuelle initiale
	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	public static float conversionHauteurEau_CoteAltimetrique(float hauteurEau,
			float dernier_calcul_Niveau_Eau, float derniere_HauteurEau) {
		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float niveauEau;
		niveauEau = dernier_calcul_Niveau_Eau + (hauteurEau - derniere_HauteurEau);

		return niveauEau;
	}
}
