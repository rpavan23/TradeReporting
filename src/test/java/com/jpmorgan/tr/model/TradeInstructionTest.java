package com.jpmorgan.tr.model;

import static com.jpmorgan.tr.constants.BuyOrSellIndicator.B;
import static com.jpmorgan.tr.constants.Currency.AED;
import static com.jpmorgan.tr.constants.Currency.USD;
import static java.lang.Long.valueOf;
import static java.time.LocalDate.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.jpmorgan.tr.model.TradeInstruction;

public class TradeInstructionTest {

	
	/**
	 * Test case to verify tradeAmountInUSD
	 */
	@Test
	public void GIVEN_trade_WHEN_trade_amount_fetch_THEN_tradeAmount_should_match(){
		TradeInstruction tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), AED, of(2018, 7, 19), 
				of(2018, 7, 19), valueOf(50), new BigDecimal("150.5"));
		assertThat(new BigDecimal("3762.50"), is(tradeInstruction.getTradeAmountInUSD()));
		
		// when currency is USD, it should not consider agreedFX rate as it is not applicable.
		tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), USD, of(2018, 7, 19), 
				of(2018, 7, 19), valueOf(50), new BigDecimal("150.5"));
		assertThat(new BigDecimal("7525.0"),  is(tradeInstruction.getTradeAmountInUSD()));
	}
	
	
	/**
	 * Test case to verify settlement date for AED and other currencies
	 * API getFinalSettlementDate() checks currency and settlement date, and then calculates
	 * final settlement date
	 */
	@Test
	public void GIVEN_trade_WHEN_settlement_date_fetch_THEN_date_should_be_weekday(){
		TradeInstruction tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), AED, of(2018, 7, 19), 
				of(2018, 7, 19), valueOf(50), new BigDecimal("150.5"));
		assertThat(of(2018, 7, 19), is(tradeInstruction.getFinalSettlementDate()));
		
		tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), AED, of(2018, 7, 19), 
				of(2018, 7, 20), valueOf(50), new BigDecimal("150.5"));
		assertThat(of(2018, 7, 22), is(tradeInstruction.getFinalSettlementDate()));
		
		tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), USD, of(2018, 7, 19), 
				of(2018, 7, 20), valueOf(50), new BigDecimal("150.5"));
		assertThat(of(2018, 7, 20), is(tradeInstruction.getFinalSettlementDate()));
		
		tradeInstruction = new TradeInstruction("Entity1", B, new BigDecimal("0.5"), USD, of(2018, 7, 19), 
				of(2018, 7, 21), valueOf(50), new BigDecimal("150.5"));
		assertThat(of(2018, 7, 23), is(tradeInstruction.getFinalSettlementDate()));
	}
}
