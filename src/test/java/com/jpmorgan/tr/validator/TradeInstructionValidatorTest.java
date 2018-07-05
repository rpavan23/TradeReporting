package com.jpmorgan.tr.validator;

import static com.jpmorgan.tr.validator.TradeInstructionValidator.validateAndGetTrade;
import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Paths.get;
import static org.apache.log4j.LogManager.getLogger;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.junit.Test;
/**
 *  
 * @author pavan
 */
public class TradeInstructionValidatorTest {

	private static final Logger log = getLogger(TradeInstructionValidatorTest.class);
	
	/**
	 * Test for invalid trades
	 */
	@Test
	public void testInvalidTrades(){
		Stream<String> tradeLines = null;
		try {
			tradeLines = Files.lines(get(getSystemResource("invalid-test-trades.csv").toURI())).skip(1);
		} catch (IOException | URISyntaxException e) {
		    fail("File not found");
		}
		tradeLines.forEach( tradeLine -> {
			try {
				validateAndGetTrade(tradeLine);
				fail("Trade shouldn't be valid");
			}catch ( RuntimeException exception ){
				log.error(exception);
			}
		});
	}
	
	/**
	 * Test for valid trades
	 */
	@Test
	public void testValidTrades(){
		Stream<String> tradeLines = null;
		try {
			tradeLines = Files.lines(get(getSystemResource("sample-test-trades.csv").toURI())).skip(1);
		} catch (IOException | URISyntaxException e) {
		    fail("File not found");
		}
		tradeLines.forEach( tradeLine -> {
			try {
				validateAndGetTrade(tradeLine);				
			}catch ( RuntimeException exception ){
				log.error(exception);
				fail("Trade should be valid");
			}
		});
	}
}
