package com.jpmorgan.tr.util;

import static com.jpmorgan.tr.constants.Currency.SAR;
import static com.jpmorgan.tr.constants.Currency.USD;
import static com.jpmorgan.tr.util.DateUtil.getNextWorkingDay;
import static com.jpmorgan.tr.util.DateUtil.isWeekEnd;
import static java.time.LocalDate.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateUtilTest {
	
	@Test
	public void testIsWeekEnd(){
		assertTrue(isWeekEnd(SAR, of(2018, 7, 20)));   // Friday
		assertFalse(isWeekEnd(SAR, of(2018, 7, 22)));  // Sunday
		
		assertFalse(isWeekEnd(USD, of(2018, 7, 20)));  // Friday
		assertTrue(isWeekEnd(USD, of(2018, 7, 21)));   // Saturday
	}
	
	@Test
	public void testGetNextWorkingDay(){
		assertThat(of(2018, 7, 22).getDayOfMonth(), is(getNextWorkingDay(SAR, of(2018, 7, 20)).getDayOfMonth()));
		assertThat(of(2018, 7, 20).getDayOfMonth(), is(getNextWorkingDay(USD, of(2018, 7, 20)).getDayOfMonth()));
	}
	
}
