package com.jpmorgan.tr.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.jpmorgan.tr.constants.BuyOrSellIndicator;
import com.jpmorgan.tr.model.EntityRank;
import com.jpmorgan.tr.model.TradeInstruction;

public interface IReportingService {

	public Map<LocalDate, BigDecimal> getTradeAmountInUSD(final List<TradeInstruction> tradeInstructions, 
			  final BuyOrSellIndicator buyOrSellIndicator);
	
	public Map<LocalDate, List<EntityRank>> getRankingsByDateAndEntity(final List<TradeInstruction> tradeInstructions, 
			   final BuyOrSellIndicator buyOrSellIndicator);
}
