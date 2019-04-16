package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import main.BasicLookupReducer;
import main.Myr;

public class LookupTableTest {
	
	//@Ignore
	@Test
	public void testReduce() {
		int num_iter = 100;
		for(int i= 0; i < num_iter; i++) {
			int mod_len = (int)(Math.random()*2047)+1;
			BigInteger reducable = new BigInteger(mod_len, new Random()).multiply(new BigInteger(mod_len*2, new Random()));
			BigInteger mod = new BigInteger((int)(Math.random()*mod_len), new Random());
			assert mod.bitLength() < reducable.bitLength();
			BigInteger reduced = reducable.mod(mod);
			
			Myr reducable_m = new Myr(reducable.toString(16));
			Myr mod_m = new Myr(mod.toString(16));
			Myr reduced_m = new BasicLookupReducer(mod_m).reduce(reducable_m);
			
			//System.out.println(reduced_m.toString() + "\n" + reduced.toString(16));
			assertTrue(reduced_m.toString().equals(reduced.toString(16)));
		}
	}
	
	@Ignore
	@Test
	public void basic_test() {
		BigInteger reducable = new BigInteger("65c792fe988c3c3aabf5c2fb97e4e00d79c32e77", 16);
		BigInteger mod = new BigInteger("63e86804", 16);
		assert mod.bitLength() < reducable.bitLength();
		BigInteger reduced = reducable.mod(mod);
		System.out.println(reducable.toString(16) + "\n" + mod.toString(16) + "\n" + reduced.toString(16));
		
		Myr reducable_m = new Myr(reducable.toString(16));
		Myr mod_m = new Myr(mod.toString(16));
		Myr reduced_m = new BasicLookupReducer(mod_m).reduce(reducable_m);
		
		System.out.println("reduced mod: " + reduced_m.toString());
		
		//System.out.println(new BigInteger("100000000", 16).mod(mod).toString(16));
		
		System.out.println("lookup");
		for(int i = 2; i < 10; i++) {
			System.out.println(new BigInteger("10000").pow(i).mod(mod).toString(16));
		}
		
		assertTrue(reduced_m.toString().equals(reduced.toString(16)));
	}
	
	@Ignore
	@Test
	public void testReduceSimple() {
		BigInteger reducable = new BigInteger("10000000000000000", 16);
		BigInteger mod = new BigInteger("100000000", 16);
		assertTrue(reducable.mod(mod).toString().equals("0"));
		
		Myr r_m = new Myr(reducable.toString(16));
		Myr m_m = new Myr(mod.toString(16));
		Myr reduced = new BasicLookupReducer(m_m).reduce(r_m);
		assertTrue(reduced.toString().equals("0"));
	}
	
	@Ignore
	@Test
	public void testGetK() {
		for(int i = 0; i < 10; i++) {
			BigInteger bmod = BigInteger.probablePrime((int)(Math.random()*2000), new Random());
			//BigInteger bint = new BigInteger(2048, new Random());
			Myr mod = new Myr(bmod.toString(16));
			//Myr a = new Myr(bint.toString(16));
			BasicLookupReducer blr = new BasicLookupReducer(mod);
			int k = blr.getK(mod);
			//System.out.println(k);
			assertTrue(bmod.compareTo(new BigInteger("2").pow(16*k)) < 0);
			assertTrue(bmod.compareTo(new BigInteger("2").pow(16*(k-1))) >= 0);
			//assertTrue(bmod.compareTo(new BigInteger("2").pow(k-1)) >= 0);
			
			//assertTrue(mod.compareTo(new Myr("2").pow(new Myr(Integer.toHexString(k)))) < 0);
			//assertTrue(mod.compareTo(new Myr("2").pow(new Myr(Integer.toHexString(k-1)))) >= 0);
		}
	}
}
