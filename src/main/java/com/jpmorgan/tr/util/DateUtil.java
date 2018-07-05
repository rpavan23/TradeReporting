package com.jpmorgan.tr.util;

import java.time.LocalDate;

import com.jpmorgan.tr.constants.Currency;
/**
 * Date utility
 * @author pavan
 */
public class DateUtil {
	
    /**
     * Checks whether given date is weekend or not for given currency
     * @param currency
     * @param date
     * @return true if @date is weekend for the given currency.
     */
	public static Boolean isWeekEnd(final Currency currency, final LocalDate date){
	     return currency.getWeekEndDays().contains(date.getDayOfWeek());
	}
	
	/**
	 * Gives next working day for the given currency and date
	 * @param currency
	 * @param date
	 * @return LocalDate, next working day 
	 */
	public static LocalDate getNextWorkingDay(final Currency currency, final LocalDate date){
		LocalDate nextDate = date;
		while (isWeekEnd( currency, nextDate )){
			nextDate = nextDate.plusDays(1);
		}
		return nextDate;
	}
	
}
