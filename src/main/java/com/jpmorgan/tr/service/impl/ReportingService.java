package com.jpmorgan.tr.service.impl;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.log4j.LogManager.getLogger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.jpmorgan.tr.constants.BuyOrSellIndicator;
import com.jpmorgan.tr.model.EntityRank;
import com.jpmorgan.tr.model.TradeInstruction;
import com.jpmorgan.tr.service.IReportingService;

/*
 * Service class to calculate tradeAmountInUSD for give trades.
 * And to generate ranks for trade entities
 */
public class ReportingService implements IReportingService{

	private static final Logger log = getLogger(ReportingService.class);
	
	/**
	 * API to get total amount in USD for given trade instructions and buyOrSellIndicator
	 * @param tradeInstructions
	 * @param buyOrSellIndicator
	 * @return map with key as date and value as BigDecimal(totalAmountInUSD)
	 */
	public Map<LocalDate, BigDecimal> getTradeAmountInUSD(final List<TradeInstruction> tradeInstructions, 
														  final BuyOrSellIndicator buyOrSellIndicator) {
		
		log.debug("Calculating tradeAmountInUSD for trades " + buyOrSellIndicator );
		
		Map<LocalDate, List<TradeInstruction>> tradesByDate = tradeInstructions.stream()
				.filter(x -> x.getBuyOrSellIndicator() == buyOrSellIndicator)
				.collect(groupingBy(TradeInstruction::getFinalSettlementDate));
		
		Map<LocalDate, BigDecimal> tradeAmountInUSDByDate = tradesByDate.entrySet().stream()
		.collect(toMap(entry -> entry.getKey(), entry -> entry.getValue().stream().map(x->x.getTradeAmountInUSD())
															.reduce(ZERO, BigDecimal::add)));
		
		return tradeAmountInUSDByDate;
	}
	
	/**
	 * API to get ranks for each entity for given trade instructions and buyOrSellIndicator
	 * @param tradeInstructions
	 * @param buyOrSellIndicator
	 * @return map with key as date and value as list of entities with rank
	 */
	public Map<LocalDate, List<EntityRank>> getRankingsByDateAndEntity(final List<TradeInstruction> tradeInstructions, 
																	   final BuyOrSellIndicator buyOrSellIndicator){
		
		log.debug("Assigning rank for trade entities with trades " + buyOrSellIndicator );
		
		// filter trades by BuyOrSell Indicator and then group by trade-date
		Map<LocalDate, List<TradeInstruction>> tradesByDate = tradeInstructions.stream()
				.filter(x -> x.getBuyOrSellIndicator() == buyOrSellIndicator)
				.collect(groupingBy(TradeInstruction::getFinalSettlementDate));
		
		Map<LocalDate, List<EntityRank>> ranksToEntityByDate = 
				tradesByDate.entrySet().stream().collect( toMap(entry -> entry.getKey(), 
				entry -> {
					List<EntityRank> entityRankList = new ArrayList<>();
					// group trades by entity
					Map<String, List<TradeInstruction>> entityMap = entry.getValue().stream().collect(groupingBy(TradeInstruction::getEntity));
					
					// calculate trade amount
					Map<String, BigDecimal> entityMapWithTradeAmount = entityMap.entrySet().stream()
							.collect(toMap(entity -> entity.getKey(), 
										   entity -> entity.getValue().stream().map(x->x.getTradeAmountInUSD()).reduce(ZERO, BigDecimal::add)));
					 
					// group entities by trade amount to assign ranks(rank will be same for entities with same trade amount)
					Map<BigDecimal,List<String>> mapKeyAsAmountAndEntitiesAsValue = new HashMap<>();
					entityMapWithTradeAmount.entrySet().stream().forEach( x ->{
						List<String> entities = mapKeyAsAmountAndEntitiesAsValue.get(x.getValue());
						if ( entities == null ){
							entities = new ArrayList<>();
							mapKeyAsAmountAndEntitiesAsValue.put(x.getValue(), entities);
						}
						entities.add(x.getKey());
					});
					
					// assign ranks to entities
					List<BigDecimal> sortAmount = mapKeyAsAmountAndEntitiesAsValue.keySet().stream().sorted((o1, o2) -> o2.compareTo(o1)).collect(toList());
					AtomicInteger atomicInteger = new AtomicInteger(1);
					sortAmount.stream().forEach(tradeAmount -> {
						int rank = atomicInteger.getAndIncrement();
						List<String> entities = mapKeyAsAmountAndEntitiesAsValue.get(tradeAmount);
						entities.stream().forEach( entity -> entityRankList.add( new EntityRank(entity, tradeAmount, rank) ));
					});
					
					return entityRankList;
				}) );
		return ranksToEntityByDate;
	}

}
