package fr.treeptik.util;

import java.util.Comparator;

import fr.treeptik.model.BilanRepas;

public class DateBilanRepasComparator implements Comparator<BilanRepas> {

	@Override
	public int compare(BilanRepas bilan1, BilanRepas bilan2) {
		return bilan1.getDate().compareTo(bilan2.getDate());
	}

}