package com.jpmorgan.tr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.jpmorgan.tr.model.TradeInstructionTest;
import com.jpmorgan.tr.service.ReportingServiceTest;
import com.jpmorgan.tr.util.DateUtilTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   DateUtilTest.class,
   ReportingServiceTest.class,
   TradeInstructionTest.class
})
public class TestSuite {
}
