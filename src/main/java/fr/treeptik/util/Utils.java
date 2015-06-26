package fr.treeptik.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

	
	public static double round(double unrounded, int precision)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, RoundingMode.CEILING);
	    return rounded.doubleValue();
	}
}
