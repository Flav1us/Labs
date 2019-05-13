package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import external_impl.BigBarrettReducer;
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

	//@Ignore
	@Test
	public void test_barr() {
		for(int j = 0; j < 10; j++) {
		//System.out.println("iter j");
		BigInteger modb = new BigInteger(1024, new Random());
		Myr mod = new Myr(modb.toString(16));
		BarrettReducer br = new BarrettReducer(mod);
		for(int i = 0; i < 100; i++) {
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
	
	@Ignore
	@Test
	public void compTest() {
		BigInteger b_mod = new BigInteger("5");
		BigInteger b_x = new BigInteger("17");
		Myr mod = new Myr(b_mod.toString(16));
		Myr x = new Myr(b_x.toString(16));
		BigBarrettReducer b_br = new BigBarrettReducer(b_mod);
		BarrettReducer br = new BarrettReducer(mod);
		
		System.out.println(b_br.reduce(b_x).toString(16));
		System.out.println(br.reduce(x).toString());
	}
	
	@Ignore
	@Test
	public void testMu() {
		Myr mod = new Myr("abcd");
		int k = mod.marr.length;
		System.out.println("len " + mod.marr.length);
		BarrettReducer br = new BarrettReducer(mod);
		Myr mu = br.getMu(mod);
		System.out.println(mu.toString());
		Myr z = new Myr("abcdef111111111111");
		System.out.println(z.shift(-k+1).toString());
	}
}
