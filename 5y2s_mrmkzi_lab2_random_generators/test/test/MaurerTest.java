package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;

import maurer.Maurer;

public class MaurerTest {

	@Test //@Ignore
	public void testMauer() {
		//Mauer.debug_mode = true;
		Maurer m = new Maurer();
		for(int i = 0; i < 1; i++) {
			BigInteger res = m.recursiveMauer(2000);
			assertTrue(res.isProbablePrime(Maurer.certainty));
			System.out.println(res.toString(16));
		}
	}
	
	@Test @Ignore
	public void manual() {
		BigInteger i = new BigInteger("1", 10);
		System.out.println(i.isProbablePrime(100) + "\t" + Maurer.isPrime_TrialDivisions(i));
		System.out.println();
	}
	
	
	@Test @Ignore
	public void testTrialDivision() {
		int num_iter1 = 1000;
		for(int i = 0; i < num_iter1; i++) {
			int size = (int)(Math.random() * Maurer.maxBitsForTrialDivisions);
			BigInteger k = new BigInteger(size, new Random());
			//System.out.println(k.toString());
			assertTrue(k.isProbablePrime(100) == Maurer.isPrime_TrialDivisions(k));
		}
	}
	
	@Test @Ignore
	public void testRandIntBelow() {
		BigInteger n = new BigInteger("4feeeeeeefefaccaaddcadcadccac121121", 16);
		System.out.println(Maurer.randomIntegerBelow(n, true));
	}
}
