package com.jpmorgan.tr.model;

import static com.jpmorgan.tr.constants.Currency.USD;
import static com.jpmorgan.tr.util.DateUtil.getNextWorkingDay;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.jpmorgan.tr.constants.BuyOrSellIndicator;
import com.jpmorgan.tr.constants.Currency;
/**
 * Trade Instruction model object
 * @author pavan
 */
public class TradeInstruction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String entity;
    private BuyOrSellIndicator buyOrSellIndicator;
    private BigDecimal agreedFx;
    private Currency currency;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    // moves to next working day when settlement date falls in weekend
    private LocalDate finalSettlementDate;
    private Long units;
	private BigDecimal pricePerUnit;
   
    public TradeInstruction(final String entity,
		   final BuyOrSellIndicator buyOrSellIndicator,
		   final BigDecimal agreedFx,
		   final Currency currency,
		   final LocalDate instructionDate,
		   final LocalDate settlementDate,
		   final Long units,
		   final BigDecimal pricePerUnit){
	   this.entity = entity;
	   this.buyOrSellIndicator = buyOrSellIndicator;
	   this.agreedFx = agreedFx;
	   this.currency = currency;
	   this.instructionDate = instructionDate;
	   this.settlementDate = settlementDate;
	   this.units = units;
	   this.pricePerUnit = pricePerUnit;
	   this.finalSettlementDate = getNextWorkingDay(currency, settlementDate);
    }
   
    public String getEntity() {
	    return entity;
    }

    public BuyOrSellIndicator getBuyOrSellIndicator() {
		return buyOrSellIndicator;
	}
	
	public BigDecimal getAgreedFx() {
		return agreedFx;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public LocalDate getInstructionDate() {
		return instructionDate;
	}
	
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	
	public Long getUnits() {
		return units;
	}
	
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public LocalDate getFinalSettlementDate() {
		return finalSettlementDate;
	}

	/**
	 * Return trade amount in USD
	 */
	public BigDecimal getTradeAmountInUSD() {
		return pricePerUnit.multiply(valueOf(units)).multiply( this.currency == USD ? ONE : agreedFx);
	}
	
}
