package test;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import main.Heys;

public class Test1 {

	
	@Test
	@Ignore
	public void test_round() {
		char m = 0b1100101100101001; //with test S should do 1000_0010_0010_0000
		char k = 0b0100101101001001;
		//System.out.println(Integer.toBinaryString(Heys.round(m, k)));
		//is correct??
		assertTrue("1010100010101111".equals(Integer.toBinaryString(Heys.round(m, k))));
		
	}
	
	@Test
	@Ignore
	public void test_reverse() {
		for(int i = 0; i < Heys.S.length; i++) {
			assertTrue(Heys.S[Heys.S_reverse[i]] == i);
		}
	}
	
	@Test
	public void test_decrypt_block() {
		Random r = new Random();
		for(int i = 0; i <20; i++ ) {
			char block = (char)r.nextInt();
			char key = (char)r.nextInt();
			assertTrue(Heys.decr_round(Heys.round(block, key), key) == block);
		}
	}

}
