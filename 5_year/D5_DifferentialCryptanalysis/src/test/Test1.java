package test;

import org.junit.jupiter.api.Test;

import main.Heys;

public class Test1 {

	public Test1() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void test_round() {
		char m = 0b1100101100101001; //with test S should do 1000_0010_0010_0000
		char k = 0b0100101101001001;
		System.out.println(Integer.toBinaryString(Heys.round(m, k)));
		
	}

}
