package com.jpmorgan.tr.csv;

import static com.jpmorgan.tr.validator.TradeInstructionValidator.validateAndGetTrade;
import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

import com.jpmorgan.tr.model.TradeInstruction;

public class CSVReader {
	
	public List<TradeInstruction> getTradeInstructions(final String csvFileName){
        try{
        	return Files.lines(get(getSystemResource(csvFileName).toURI()))
            		.skip(1).map(mapToTrade).collect(toList());
        }
        catch(IOException|URISyntaxException e){
        	throw new RuntimeException(e);
        } 
	}
	
	private static Function<String, TradeInstruction> mapToTrade = (line) -> validateAndGetTrade(line);
	
}
