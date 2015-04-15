package fr.treeptik.util;

import fr.treeptik.shared.dto.graph.PointGraphDTO;

import java.util.Comparator;


public class DatePointComparator implements Comparator<PointGraphDTO> {

	@Override
	public int compare(PointGraphDTO point1, PointGraphDTO point2) {
		return point1.getDate().compareTo(point2.getDate());
	}

	
}
