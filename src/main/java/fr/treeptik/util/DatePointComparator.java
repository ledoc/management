package fr.treeptik.util;

import java.util.Comparator;

import fr.treeptik.model.PointGraphDTO;

public class DatePointComparator implements Comparator<PointGraphDTO> {

	@Override
	public int compare(PointGraphDTO point1, PointGraphDTO point2) {
		return point1.getDate().compareTo(point2.getDate());
	}

	
}