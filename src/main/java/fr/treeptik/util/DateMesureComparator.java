package fr.treeptik.util;

import java.util.Comparator;

import fr.treeptik.model.Mesure;

public class DateMesureComparator implements Comparator<Mesure> {

	@Override
	public int compare(Mesure m1, Mesure m2) {
		return m1.getDate().compareTo(m2.getDate());
	}

}
