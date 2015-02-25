package fr.treeptik.util;

import java.util.Comparator;

import fr.treeptik.model.Point;

public class DatePointComparator implements Comparator<Point> {

	@Override
	public int compare(Point point1, Point point2) {
		return point1.getDate().compareTo(point2.getDate());
	}

	
}
