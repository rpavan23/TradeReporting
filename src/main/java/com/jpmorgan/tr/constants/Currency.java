package com.jpmorgan.tr.constants;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Arrays.asList;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Allowed currencies for the trade instruction.
 * It also allows to configure weekend days for each currency.
 * @author pavan
 *
 */
public enum Currency {
	
   AED(FRIDAY,SATURDAY),   SAR(FRIDAY,SATURDAY),
   USD(SATURDAY,SUNDAY),   HKD(SATURDAY,SUNDAY),
   EUR(SATURDAY,SUNDAY),   JPY(SATURDAY,SUNDAY), 
   GBP(SATURDAY,SUNDAY),   AUD(SATURDAY,SUNDAY),
   CAD(SATURDAY,SUNDAY),   CHF(SATURDAY,SUNDAY), 
   CNY(SATURDAY,SUNDAY),   SEK(SATURDAY,SUNDAY), 
   MXN(SATURDAY,SUNDAY),   NZD(SATURDAY,SUNDAY), 
   SGP(SATURDAY,SUNDAY),   INR(SATURDAY,SUNDAY);
   
   private List<DayOfWeek> weekEndDays;

   private Currency(DayOfWeek... weekEndDays) {
	   this.weekEndDays = asList(weekEndDays);
   }
   
   public List<DayOfWeek> getWeekEndDays(){
	   return weekEndDays;
   }
   
}
