package fr.treeptik.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.treeptik.model.Aliment;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.model.Plat;
import fr.treeptik.model.Repas;

@Component
public class CSVUtils {

	public List<String> doAlimentHeaderCSV() {
		List<String> headers = Arrays.asList("Nom", "Quantité référence",
				"Calories", "Protéines", "Glucides", "Lipides");
		return headers;
	}

	public List<String> doPlatHeaderCSV() {
		List<String> headers = Arrays.asList("Nom", "Aliment", "Quantité",
				"Protéines", "Glucides", "Lipides");
		return headers;
	}

	public List<String> doRepasHeaderCSV() {
		List<String> headers = Arrays.asList("Nom", "Date",
				"Protéines", "Glucides", "Lipides");
		return headers;
	}

	public String doAlimentCSV(List<Aliment> listAliment, StringBuilder builder) {

		for (Aliment aliment : listAliment) {
			NutritionBilan nutritionBilan = aliment.getNutritionBilan();
			builder.append(aliment.getNom() + ";");
			Float quantite = aliment.getQuantite();
			builder.append(quantite + ";");
			toNutrtionBilanCSV(builder, nutritionBilan, quantite);
		}
		String csv = builder.toString();
		return csv;
	}

	public String doPlatCSV(List<Plat> listPlat, StringBuilder builder) {

		for (Plat plat : listPlat) {
			NutritionBilan nutritionBilan = plat.getNutritionBilan();
			builder.append(plat.getNom() + ";");
			Aliment aliment = plat.getAliment();
			builder.append(aliment.getNom() + ";");
			Float quantite = (float) plat.getQuantite();
			builder.append(quantite + ";");
			toNutrtionBilanCSV(builder, nutritionBilan, null);

		}
		String csv = builder.toString();
		return csv;
	}

	public String doRepasCSV(List<Repas> listRepas, StringBuilder builder) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Repas repas : listRepas) {
			NutritionBilan nutritionBilan = repas.getNutritionBilan();
			builder.append(repas.getNom() + ";");
			builder.append(sdf.format(repas.getDate()) + ";");

			toNutrtionBilanCSV(builder, nutritionBilan, null);
			List<Plat> listPlat = repas.getListPlats();
			for (Plat plat : listPlat) {
				
				builder.append(" " + ";");
				builder.append(" " + ";");
				builder.append(plat.getNom() + ";");
				Aliment aliment = plat.getAliment();
				builder.append(aliment.getNom() + ";");
				Float quantite = (float) plat.getQuantite();
				
				builder.append(quantite + ";\n");

			}

		}
		String csv = builder.toString();
		return csv;
	}

	private void toNutrtionBilanCSV(StringBuilder builder,
			NutritionBilan nutritionBilan, Float quantite) {
		if (quantite != null) {

			builder.append(quantite * nutritionBilan.getCalories() + ";");
			builder.append(quantite * nutritionBilan.getProteine() + ";");
			builder.append(quantite * nutritionBilan.getGlucide() + ";");
			builder.append(quantite * nutritionBilan.getLipide() + ";\n");
		} else {
			builder.append(nutritionBilan.getCalories() + ";");
			builder.append(nutritionBilan.getProteine() + ";");
			builder.append(nutritionBilan.getGlucide() + ";");
			builder.append(nutritionBilan.getLipide() + ";\n");
		}
	}
}
