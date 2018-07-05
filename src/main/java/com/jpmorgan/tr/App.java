package com.jpmorgan.tr;

import static com.jpmorgan.tr.constants.BuyOrSellIndicator.B;
import static com.jpmorgan.tr.constants.BuyOrSellIndicator.S;
import static java.lang.System.out;
import static java.math.BigDecimal.ROUND_UP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.jpmorgan.tr.csv.CSVReader;
import com.jpmorgan.tr.model.EntityRank;
import com.jpmorgan.tr.model.TradeInstruction;
import com.jpmorgan.tr.service.IReportingService;
import com.jpmorgan.tr.service.impl.ReportingService;
/**
 * Application that loads trade instructions from csv and then generates reports 
 * 1. Amount in USD settled incoming everyday
 * 2. Amount in USD settled outgoing everyday
 * 3. Ranking of entities based on incoming and outgoing amount.
 * @author pavan
 */
public class App { 
	
	public static final String CSV_FILE_NAME = "TestData.csv";
	
	public static void main(String[] args) {
		
		App app = new App();
		CSVReader csvTradeInstructionReader = new CSVReader();
		IReportingService reportingService = new ReportingService();

		// load trade instructions
		List<TradeInstruction> tradeInstructions = csvTradeInstructionReader.getTradeInstructions(CSV_FILE_NAME);
		
		// Amount in USD settled outgoing everyday
		out.println("Amount in USD settled outgoing(Buy) everyday");
		out.printf("%-20s  %s\n","Date", "TotalAmountInUSD");
		app.printAmountInUSD( reportingService.getTradeAmountInUSD(tradeInstructions, B) );
		
		// Amount in USD settled incoming everyday
		out.println("\nAmount in USD settled incoming(Sell) everyday");
		out.printf("%-20s  %s\n","Date", "TotalAmountInUSD");
		app.printAmountInUSD( reportingService.getTradeAmountInUSD(tradeInstructions, S) );
		
		// Ranking of entities based on outgoing amount in USD
		out.println("\nRanking of entities based on outgoing(Buy) amount");
		out.printf("%-10s  %-20s  %s  %-25s\n","Date", "Entity", "Rank", "TotalAmountInUSD" );
		app.printEntityRanks(reportingService.getRankingsByDateAndEntity(tradeInstructions, B) );
		
		// Ranking of entities based on incoming amount in USD		
		out.println("\nRanking of entities based on incoming(Sell) amount");
		out.printf("%-10s  %-20s  %s  %-25s\n","Date", "Entity", "Rank", "TotalAmountInUSD" );
		app.printEntityRanks( reportingService.getRankingsByDateAndEntity(tradeInstructions, S) );
	}

	private void printEntityRanks(final Map<LocalDate, List<EntityRank>> rankByDateAndEntity) {
		rankByDateAndEntity.entrySet().forEach(entry -> entry.getValue().stream().forEach( x -> 
							out.printf("%-10s  %-20s  %s  %18.4f   \n",entry.getKey(), x.getEntity(), x.getRank(), 
							x.getTotalTradeAmountInUSD().setScale(4, ROUND_UP))));				
	}

	private void printAmountInUSD(final Map<LocalDate, BigDecimal> tradeAmountInUSD) {
		tradeAmountInUSD.entrySet().forEach( x -> out.printf("%-20s  %15.4f\n",x.getKey(), x.getValue().setScale(4, ROUND_UP)));
	}
}
