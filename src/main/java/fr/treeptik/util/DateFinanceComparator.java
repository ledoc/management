package fr.treeptik.util;

import java.util.Comparator;

import fr.treeptik.model.Finance;

public class DateFinanceComparator implements Comparator<Finance> {

	@Override
	public int compare(Finance finance1, Finance finance2) {
		return finance1.getDate().compareTo(finance2.getDate());
	}

	
}