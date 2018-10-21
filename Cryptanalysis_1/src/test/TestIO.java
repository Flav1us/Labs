package test;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import main.Main;
import util.Util;


public class TestIO {
	
	@Test
	public void testPrintCSV() {
		Util.printArr(Util.getDoubleCSV(Main.path_prob));
		Util.printArr(Util.getIntegerCSV(Main.path_table));
	}

	@Test
	public void test() {
		
	}


}
