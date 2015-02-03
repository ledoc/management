package fr.treeptik.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class DateUnixConverter {

	public static Integer stringToInt(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		Date date = dateFormat.parse(dateString);
		Long timestamp = date.getTime();
		int unixDate = (int) (timestamp / 1000);
		return unixDate;

	}

	public static Integer dateToInt(Date date) throws ParseException {
		Long timestamp = date.getTime();
		int unixDate = (int) (timestamp / 1000);
		return unixDate;

	}

	public static Date intToDate(Integer intUnix) throws ParseException {

		Date date = new Date((long) intUnix * (long) 1000);
		return date;

	}

	public static String intToString(Integer intUnix) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		Long dateLong = (long) intUnix;
		String dateString = dateFormat.format(new Date(dateLong * 1000));
		return dateString;
	}

	/**
	 * Méthode pour renvoyer une Date TimeTemporalType.TIME en donnant comme
	 * argument la date format Unix renvoyée par le XMLRPC
	 * 
	 * @param intUnix
	 * @return
	 * @throws ParseException
	 */
	public static Date intToTime(Integer intUnix) throws ParseException {
		Date date = intToDate(intUnix);
		LocalTime localTime = new LocalTime(date);
		Date dateTime = localTime.toDateTimeToday().toDate();
		return dateTime;
	}

}
