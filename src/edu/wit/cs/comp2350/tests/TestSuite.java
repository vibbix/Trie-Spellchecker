package edu.wit.cs.comp2350.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LAB5TestCase.class,
        BinTreeTestCase.class,
        HashTableTestCase.class,
        LinearTestCase.class,
})

public class TestSuite {
	
	static String stringOutput(String[] lines, Object[] values) {
		return String.format(String.join("", lines), values);
	}
	
}
