package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import main.BarrettReducer;
import main.LookupReducer;
import main.MontReducer;
import main.Myr;

public class MontTest {
	
	//@Ignore
	@Test
	public void bintTest() {
		int bitlen = 120;
		int num_iter = 100;
		for(int i= 0; i < num_iter; i++) {
			int mod_len;
			BigInteger reducable;
			BigInteger mod;
			do {
				mod_len = (int)(Math.random()*bitlen)+2;
				reducable = BigInteger.probablePrime(mod_len*2 - 1, new Random());//new BigInteger(mod_len*2 - 1, new Random());
				mod = new BigInteger(mod_len, new Random());
				System.out.println("while");
			} while (mod.bitLength() >= reducable.bitLength() || mod.compareTo(BigInteger.ZERO) == 0 || !mod.testBit(0) || mod.equals(new BigInteger("1")));
			
			Myr reducable_m = new Myr(reducable.toString(16));
			Myr mod_m = new Myr(mod.toString(16));
			//System.out.println(reducable_m.toString() + "\t" + mod_m.toString());
			
			System.out.println("reducing " + reducable.toString(16) + " mod " + mod.toString(16));
			MontReducer mr = new MontReducer(mod_m);
			Myr reduced_m = mr.convertOut(mr.pow(mr.convertIn(reducable_m), new Myr("1")));
			
			
			//System.out.println(reduced_m.toString());
			//System.out.println("b\t" + new BarrettReducer(mod_m).reduce(reducable_m));
			assertTrue(reduced_m.equals(new BarrettReducer(mod_m).reduce(reducable_m)));
		}
	}
	
	
	
	@Ignore
	@Test
	public void manual() {
		BigInteger modt = /*BigInteger.probablePrime*/new BigInteger(60, new Random());
		BigInteger at = new BigInteger(120, new Random());
		BigInteger bt = new BigInteger("1");
		
		Myr mod = new Myr(modt.toString(16));
		Myr a = new Myr(at.toString(16));
		Myr b = new Myr(bt.toString(16));

		//Myr c = a.multiply(b).mod(mod);

		MontReducer red = new MontReducer(mod);


		String c1 = at.modPow(bt, modt).toString(16);
		Myr d = red.convertOut(red.pow(red.convertIn(a), b));
		//System.out.println(c + "\n" + d);
		
		System.out.println(c1 + "\n" + d.toString());
		assertTrue(c1.equals(d.toString()));
	}
	
	@Ignore
	@Test
	public void compTest() {
		int test_cases = 1;
		for (int i = 0; i < test_cases; i++) {
			int mod_len = (int)(Math.random()*150)+1;
			BigInteger modt = BigInteger.probablePrime(mod_len, new Random());
			BigInteger at = new BigInteger(mod_len-1, new Random());
			BigInteger bt = new BigInteger(mod_len, new Random());
			
			Myr mod = new Myr(modt.toString(16));
			Myr a = new Myr(at.toString(16));
			Myr b = new Myr(bt.toString(16));

			Myr c = a.multiply(b).mod(mod);

			MontRed_reference mref = new MontRed_reference(modt);
			MontReducer red = new MontReducer(mod);
			
			Myr d = red.convertOut(red.multiply(red.convertIn(a), red.convertIn(b)));

			assertTrue(c.equals(d));

			String c1 = mref.convertOut(mref.pow(mref.convertIn(at), bt)).toString(16);
			d = red.convertOut(red.pow(red.convertIn(a), b));
			//System.out.println(c + "\n" + d);
			
			System.out.println(c1 + "\n" + d.toString());
			//System.out.println(Myr.LongPowBarrett(a, b, mod));
			assertTrue(c1.equals(d.toString()));
		}
	}
	
	@Ignore
	@Test
	public void testConvert() {
		int n_iter = 10;
		for (int i = 0; i < n_iter ; i++) {
			BigInteger modt = BigInteger.probablePrime(1024, new Random());
			BigInteger at = new BigInteger(1024, new Random());

			Myr mod = new Myr(modt.toString(16));
			Myr a = new Myr(at.toString(16));

			MontRed_reference mref = new MontRed_reference(modt);
			MontReducer red = new MontReducer(mod);

			BigInteger converted_ref = mref.convertIn(at);
			Myr converted = red.convertIn(a);

			assertTrue(converted.toString().equals(converted_ref.toString(16)));

			at = mref.convertOut(converted_ref);
			a = red.convertOut(converted);

			assertTrue(a.toString().equals(at.toString(16)));
		}
	}
	
	@Ignore
	@Test
	public void testMultiplyAndPow() {
		int n_iter = 10;
		for (int i = 0; i < n_iter ; i++) {
			BigInteger modt = BigInteger.probablePrime(1024, new Random());
			BigInteger at = new BigInteger(1000, new Random());
			BigInteger bt = new BigInteger(1000, new Random());

			Myr mod = new Myr(modt.toString(16));
			Myr a = new Myr(at.toString(16));
			Myr b = new Myr(bt.toString(16));

			MontRed_reference mref = new MontRed_reference(modt);
			MontReducer red = new MontReducer(mod);

			BigInteger mul_ref = mref.multiply(at, bt);
			Myr mul = red.multiply(a, b);

			assertTrue(mul.toString().equals(mul_ref.toString(16)));
			
			
			BigInteger pow_ref = mref.pow(at, bt);
			Myr pow = red.pow(a, b);
			//System.out.println(pow.toString() + "\n" + (pow_ref.toString(16)));
			assertTrue(pow.toString().equals(pow_ref.toString(16)));
		}
	}
	
	@Ignore
	@Test
	public void testFields() {
		BigInteger modt = BigInteger.probablePrime(1024, new Random());
		BigInteger at = new BigInteger(1000, new Random());
		
		Myr mod = new Myr(modt.toString(16));
		Myr a = new Myr(at.toString(16));
		
		MontRed_reference mref = new MontRed_reference(modt);
		MontReducer red = new MontReducer(mod);
		
		assertTrue(mref.convertedOne.toString(16).equals(red.convertedOne.toString()));
		assertTrue(mref.modulus.toString(16).equals(red.modulus.toString()));
		assertTrue(mref.reducer.toString(16).equals(red.reducer.toString()));
		assertTrue(mref.reciprocal.toString(16).equals(red.reciprocal.toString()));
		assertTrue(mref.mask.toString(16).equals(red.mask.toString()));
		assertTrue(mref.factor.toString(16).equals(red.factor.toString()));
		assertTrue(mref.reducerBits==(red.reducerBits));
		
	}
	
	

}
