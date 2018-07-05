package com.jpmorgan.tr.validator;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.jpmorgan.tr.constants.BuyOrSellIndicator;
import com.jpmorgan.tr.constants.Currency;
import com.jpmorgan.tr.model.TradeInstruction;

public class TradeInstructionValidator {
	
	private static final Integer ENTITY = 0;
	private static final Integer BUY_SELL = 1;
	private static final Integer AGREED_FX = 2;
	private static final Integer CURRENCY = 3;
	private static final Integer INSTRUCTION_DATE = 4;
	private static final Integer SETTLEMENT_DATE = 5;
	private static final Integer UNITS = 6;
	private static final Integer PRICE_PER_UNIT = 7;
	
	private static final DateTimeFormatter DATE_FORMATTER = ofPattern("dd MMM yyyy");

	public static TradeInstruction validateAndGetTrade(final String tradeLine) {
		try {
			String[] parameter = tradeLine.split(",");
			String entity = parameter[ENTITY];
	        BuyOrSellIndicator buyOrSellIndicator = BuyOrSellIndicator.valueOf(parameter[BUY_SELL]);
	        BigDecimal agreedFX = new BigDecimal(parameter[AGREED_FX]);
	        Currency currency = Currency.valueOf(parameter[CURRENCY]);
	        LocalDate instructionDate = parse(parameter[INSTRUCTION_DATE], DATE_FORMATTER );
	        LocalDate settlementDate = parse(parameter[SETTLEMENT_DATE], DATE_FORMATTER );
	        Long units = Long.valueOf(parameter[UNITS]);
	        BigDecimal pricePerUnit = new BigDecimal(parameter[PRICE_PER_UNIT]);
	        
	        return new TradeInstruction(entity,buyOrSellIndicator,agreedFX,currency,instructionDate,
	        		settlementDate,units,pricePerUnit);
		}catch(DateTimeParseException|IllegalArgumentException e ){
			throw new RuntimeException("Error while parsing: "+tradeLine , e);
		}
    }
}
