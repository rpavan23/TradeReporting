package com.jpmorgan.tr.service;

import static com.jpmorgan.tr.constants.BuyOrSellIndicator.B;
import static com.jpmorgan.tr.constants.BuyOrSellIndicator.S;
import static com.jpmorgan.tr.constants.Currency.CAD;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.tr.csv.CSVReader;
import com.jpmorgan.tr.model.EntityRank;
import com.jpmorgan.tr.model.TradeInstruction;
import com.jpmorgan.tr.service.impl.ReportingService;

public class ReportingServiceTest {
	
	private List<TradeInstruction> tradeInstructions;
	private ReportingService reportingService;

	@Before
	public void setup(){
		reportingService = new ReportingService();
		CSVReader csvReader = new CSVReader();
		tradeInstructions = csvReader.getTradeInstructions("sample-test-trades.csv");
	}
	
	/**
	 * GIVEN test trades
	 * WHEN  report is generated for settled incoming(sell) total trade amount in USD
	 * THEN should calculate total trade amount in USD for all sell trades
	 */
	@Test
	public void GIVEN_trades_WHEN_report_invoked_for_sell_trades_THEN_should_give_total_trade_amount(){
		Map<LocalDate, BigDecimal> tradeAmountInUSD = reportingService.getTradeAmountInUSD(tradeInstructions, S);
		assertNotNull(tradeAmountInUSD);
		assertThat(new BigDecimal("3560.000"), is(tradeAmountInUSD.get(of(2018, 7, 8))));
		assertThat(new BigDecimal("6493.800"), is(tradeAmountInUSD.get(of(2018, 7, 2))));
	}
	
	
	/**
	 * GIVEN test trades
	 * WHEN  report is generated for settled outgoing(buy) total trade amount in USD
	 * THEN should calculate total trade amount in USD for all sell trades
	 */
	@Test
	public void GIVEN_trades_WHEN_report_invoked_for_buy_trades_THEN_should_give_total_trade_amount(){
		Map<LocalDate, BigDecimal> tradeAmountInUSD = reportingService.getTradeAmountInUSD(tradeInstructions, B);
		assertNotNull(tradeAmountInUSD);
		assertThat(new BigDecimal("4672.500"), is(tradeAmountInUSD.get(of(2018, 7, 8))));
		assertThat(new BigDecimal("7629.820"), is(tradeAmountInUSD.get(of(2018, 7, 2))));
	}
	
	
	/**
	 * GIVEN test trades
	 * WHEN  report is generated for Ranking of entities based on incoming(sell)
	 * THEN should calculate ranks
	 */
	@Test
	public void GIVEN_trades_WHEN_report_invoked_for_sell_rank_THEN_should_give_ranks(){
		Map<LocalDate, List<EntityRank>> rankingsByDateAndEntity = reportingService.getRankingsByDateAndEntity(tradeInstructions, S);
							
		assertNotNull(rankingsByDateAndEntity);
		assertThat( asList(new EntityRank("E6", new BigDecimal("3560.000"), 1)), 
				is(rankingsByDateAndEntity.get(of(2018, 7, 8))));
		assertThat( asList(new EntityRank("E5", new BigDecimal("4000"), 1),
				new EntityRank("E4", new BigDecimal("1961.250"), 2),
				new EntityRank("E2", new BigDecimal("243.750"), 3),
				new EntityRank("E3", new BigDecimal("218.400"), 4),
				new EntityRank("E1", new BigDecimal("70.400"), 5)), 
				is(rankingsByDateAndEntity.get(of(2018, 7, 2))));
	}
	
	
	/**
	 * GIVEN test trades
	 * WHEN  report is generated for Ranking of entities based on outgoing(buy)
	 * THEN should calculate ranks
	 */
	@Test
	public void GIVEN_trades_WHEN_report_invoked_for_buy_rank_THEN_should_give_ranks(){
		Map<LocalDate, List<EntityRank>> rankingsByDateAndEntity = reportingService.getRankingsByDateAndEntity(tradeInstructions, B);
							
		assertNotNull(rankingsByDateAndEntity);
		assertThat( asList(new EntityRank("E6", new BigDecimal("4672.500"), 1)), 
				is(rankingsByDateAndEntity.get(of(2018, 7, 8))));
		assertThat( asList(new EntityRank("E5", new BigDecimal("5250"), 1),
				new EntityRank("E4", new BigDecimal("1202.900"), 2),
				new EntityRank("E2", new BigDecimal("715.000"), 3),
				new EntityRank("E1", new BigDecimal("256.000"), 4),
				new EntityRank("E3", new BigDecimal("205.920"), 5)), 
				is(rankingsByDateAndEntity.get(of(2018, 7, 2))));
	}
	
	
	/**
	 * GIVEN test trades
	 * WHEN  report is generated for Ranking of entities based on outgoing(buy)
	 * THEN should calculate same rank for entities which has same total trade amount in USD.
	 */
	@Test
	public void GIVEN_trades_WHEN_report_invoked_for_buy_rank_THEN_should_give_same_ranks(){
		List<TradeInstruction> tradeSet = new ArrayList<>();
		tradeSet.add( new TradeInstruction("E1", B, new BigDecimal("0.890"), CAD, of(2018, 7, 4), of(2018, 7, 4), 10l, new BigDecimal("25")));
		tradeSet.add( new TradeInstruction("E2", B, new BigDecimal("0.890"), CAD, of(2018, 7, 4), of(2018, 7, 4), 10l, new BigDecimal("25")));

		Map<LocalDate, List<EntityRank>> rankingsByDateAndEntity = reportingService.getRankingsByDateAndEntity( tradeSet, B);
							
		assertNotNull(rankingsByDateAndEntity);
		assertThat( asList(new EntityRank("E1", new BigDecimal("222.500"), 1),
				           new EntityRank("E2", new BigDecimal("222.500"), 1)),
				    is(rankingsByDateAndEntity.get(of(2018, 7, 4))));
	}
	
}
