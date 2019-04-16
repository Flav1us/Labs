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
		long t0 = System.currentTimeMillis();
		int n_tests = 10;
		for(int i = 0; i < n_tests; i++) {
			System.out.println(i);
			BigInteger modt = BigInteger.probablePrime(768, new Random());
			BigInteger at = new BigInteger(700, new Random());
			BigInteger at_inv = at.modInverse(modt);
			Myr a = new Myr(at.toString(16));
			Myr mod = new Myr(modt.toString(16));
			Myr a_inv = a.modInverse(mod);
			//System.out.println("len " + a_inv.toString().length());
			//System.out.println(at_inv.toString(16) + "\n" + a_inv.toString());
			assertTrue(at_inv.toString(16).equals(a_inv.toString()));
			assertTrue(a.multiply(a_inv).mod(mod).toString().equals(Myr.ONE.toString()));
		}
		System.out.println(System.currentTimeMillis() - t0 + " ms");
	}
	
	@Ignore
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
	
	@Ignore
	@Test
	public void testToBinString() {
		int test_cases = 100;
		int bit_size = 90;
		for(int i = 0; i < test_cases ; i++) {
			BigInteger bi = new BigInteger(bit_size, new Random());
			Myr m = new Myr(bi.toString(16));
			Myr m1 = new Myr(bi.toString(2), 2);
			System.out.println(m1.toBinString() + "\n" + bi.toString(2));
			assertTrue(m1.toBinString().equals(bi.toString(2)));
		}
	}
	
	@Ignore
	@Test
	public void testShiftBits() {
		int test_cases = 100;
		int bit_size = 90;
		for(int i = 0; i < test_cases ; i++) {
			BigInteger bi = new BigInteger(bit_size, new Random());
			Myr m = new Myr(bi.toString(16));
			int numbits = new Random().nextInt() % (bit_size-5);
			String res1 = m.shiftBits(numbits).toBinString();
			String res2 = bi.shiftLeft(numbits).toString(2);
			System.out.println(res1 + "\n" + res2);
			assertTrue(res1.equals(res2));
		}	
	}
	
	@Test
	public void testTestBit() {
		int test_cases = 100;
		for(int i = 0; i < test_cases  ; i++) {
			BigInteger bi = new BigInteger(1024, new Random());
			Myr m = new Myr(bi.toString(16));
			Random r = new Random();
			for(int j = 0; j < 10; j++) {
				int t = r.nextInt(bi.bitCount());
				assertTrue(bi.testBit(j) == m.testBit(j));				
			}
			/*System.out.println(bi.toString(2));
			for(int j = 0; j < bi.toString(2).length(); j++) {
				System.out.print(bi.testBit(bi.toString(2).length() - j - 1) ? '1' : '0');
			} System.out.println();*/
		}
	}

}
