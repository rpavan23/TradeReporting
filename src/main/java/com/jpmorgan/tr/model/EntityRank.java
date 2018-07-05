package com.jpmorgan.tr.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Entity model with total trade amount in USD and rank.
 * @author pavan
 */
public class EntityRank implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String entity;
	private Integer rank;
	private BigDecimal totalTradeAmountInUSD;
	
	
	public EntityRank(final String entity, final BigDecimal totalTradeAmountInUSD, final Integer rank){
		this.rank = rank;
		this.totalTradeAmountInUSD = totalTradeAmountInUSD;
		this.entity = entity;
	}

	public String getEntity() {
		return entity;
	}

	public Integer getRank() {
		return rank;
	}

	public BigDecimal getTotalTradeAmountInUSD() {
		return totalTradeAmountInUSD;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EntityRank){
			EntityRank entityRank = (EntityRank) obj;
			return entity.equals(entityRank.getEntity()) && totalTradeAmountInUSD.equals(entityRank.getTotalTradeAmountInUSD())
					&& rank.equals( entityRank.getRank() );
		}
		return false;
	}
}
