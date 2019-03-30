package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import main.Myr;

public class MyrTest {
	
	BigInteger at = new BigInteger(768, new Random());
	BigInteger bt = new BigInteger(768, new Random());
	BigInteger mt = new BigInteger(768, new Random());
	Myr a = new Myr(at.toString(16));
	Myr b = new Myr(bt.toString(16));
	Myr m = new Myr(mt.toString(16));

	@Ignore
	@Test
	public void testInverse() {
		//extended Euclid alg
		BigInteger at_inv = at.modInverse(mt);
		Myr a_inv = a.modInverse(m);
		
		assertTrue(at_inv.toString(16).equals(a_inv.toString()));
	}
	
	//@Ignore
	@Test
	public void testLongPowBarrett() {
		long t0 = System.currentTimeMillis();
		String res1 = at.modPow(bt, mt).toString(16);
		long t1 = System.currentTimeMillis();
		System.out.println("BigInteger time: " + (t1-t0) + " ms.");
		String res2 = Myr.LongPowBarrett(a, b, m).toString();
		long t2 = System.currentTimeMillis();
		System.out.println("Myr time: " + (t2-t1) + " ms.");
		assertTrue(res1.equals(res2));
	}
	
	@Ignore
	@Test
	public void testMultiply() {
		long t0 = System.currentTimeMillis();
		String res1 = at.multiply(bt).toString(16);
		long t1 = System.currentTimeMillis();
		System.out.println("BigInteger time: " + (t1-t0) + " ms.");
		String res2 = a.multiply(b).toString();
		long t2 = System.currentTimeMillis();
		System.out.println("Myr time: " + (t2-t1) + " ms.");
		assertTrue(res1.equals(res2));
	}
	
	@Ignore
	@Test
	public void testEfficency() {
		System.out.println("efficency test");
		
		long t0 = System.currentTimeMillis();
		int n_tests = 1, bit_len = 768;
		for(int i = 0; i < n_tests; i++) {
			//BigInteger a = new BigInteger(bit_len, new Random());
			//BigInteger b = new BigInteger(bit_len, new Random());
			//Myr.LongAdd(new Myr(a.toString(16)), new Myr(b.toString(16)));
			Myr.LongPowBarrett(a, b, m);
			//at.divide(bt);
			
		}
		System.out.println(System.currentTimeMillis() - t0 + " ms");
		//BigInteger c = new BigInteger(bit_len, new Random());
		//System.out.println(c.toString(16));
	}

}
