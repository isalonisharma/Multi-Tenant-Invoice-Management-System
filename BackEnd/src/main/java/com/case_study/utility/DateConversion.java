package com.case_study.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Class Name: DateConversion
 * 
 * Use: To Convert the LocalDateTime into different formats
 * 
 * @author saloni.sharma
 */
public class DateConversion {
	/**
	 * Function Name: LocalDateTimeToStrin
	 * 
	 * Feature: Date Conversion as per Organization Date Format
	 * 
	 * Description: It convert LocalDateTime to String as per different formats.
	 * 
	 * LONG format: April 3, 2019 MEDIUM format: Apr 3, 2019 SHORT format: 4/3/19
	 * FULL format: Wednesday, April 3, 2019
	 * 
	 * @param localDateTime
	 * @param dateFormat
	 * 
	 * @return formattedDate
	 */
	public static String convertLocalDateTime(LocalDateTime localDateTime, String dateFormat) {
		String formattedDate = null;
		switch (dateFormat) {
		case "SHORT":
			formattedDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
			break;
		case "MEDIUM":
			formattedDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
			break;
		case "LONG":
			formattedDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
			break;
		case "FULL":
			formattedDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
			break;
		default:
			formattedDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
		}
		return formattedDate;
	}
}