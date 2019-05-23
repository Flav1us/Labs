package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import main.BarrettReducer;
import main.Myr;


public class BarrettReducerTest {
	@Ignore
	@Test
	public void testshiftBits() {
		for(int i = 0; i < 1000; i++) {
			BigInteger xb = new BigInteger(50, new Random());
			Myr b = new Myr(xb.toString(16));
			int shift = (int)(Math.random() * 50);
			String xb_shifted = xb.shiftRight(shift).toString(2);
			String b_shifted = b.shiftBits(-shift).toBinString();
			System.out.println(xb_shifted + "\n" + b_shifted +"\n");
			assertTrue(xb_shifted.equals(b_shifted));
		}
	}

	@Ignore
	@Test
	public void test_barr() {
		for(int j = 0; j < 1000; j++) {
		//System.out.println("iter j");
		BigInteger modb = new BigInteger(1024, new Random());
		Myr mod = new Myr(modb.toString(16));
		BarrettReducer br = new BarrettReducer(mod);
		for(int i = 0; i < 1000; i++) {
			BigInteger xb;
			do { //ref impl works for x < mod^2.
				//System.out.println("while");
				xb = new BigInteger(2000, new Random());
			} while(xb.compareTo(modb.pow(2)) >= 0);
			Myr x = new Myr(xb.toString(16));
			
			//System.out.println(bbr.reduce(x).toString(16));
			//System.out.println(x.mod(mod).toString(16));
			//System.out.println();
			assertTrue(br.reduce(x).toString().equals(xb.mod(modb).toString(16)));
		}
		}
	}
	
}
