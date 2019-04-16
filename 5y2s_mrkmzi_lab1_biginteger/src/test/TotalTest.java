package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import main.BasicLookupReducer;
import main.Myr;

public class TotalTest {
	
	//extreme cases
	BigInteger[][] ec; // ec[0] % ec[1] = ec[2];
	{
		ec = new BigInteger[3][3];
		ec[0][0] = new BigInteger("0");
		ec[0][1] = new BigInteger("500000000000000000000", 16);
		ec[0][2] = new BigInteger("0");
		
		ec[1][0] = new BigInteger("2").pow(64);
		ec[1][1] = new BigInteger("2").pow(16);
		ec[1][2] = new BigInteger("0");
		
		ec[2][0] = new BigInteger("123123123", 16);
		ec[2][1] = new BigInteger("1");
		ec[2][2] = new BigInteger("0");
	}

	//@Ignore
	@Test
	public void testExtremes() {
		for(int i = 0; i < ec.length; i++) {
			assertTrue(ec[i][0].mod(ec[i][1]).equals(ec[i][2]));
			BasicLookupReducer blr = new BasicLookupReducer(new Myr(ec[i][1].toString(16)));
			assertTrue(blr.reduce(new Myr(ec[i][0].toString(16))).equals(new Myr(ec[i][2].toString(16))));
		}
	}
	
	@Test(expected=ArithmeticException.class)
	public void testExceptions() {
		BasicLookupReducer blr = new BasicLookupReducer(new Myr("0"));
	}
	
	@Ignore
	@Test
	public void testLookupTableReducer() {
		int num_iter_mods = 10, num_iter_reduces = 5000;
		System.out.format("lookup table reducer test\nfor %d moduless, for each mod %d reduced numbers.\n", num_iter_mods, num_iter_reduces);
		long t0 = System.currentTimeMillis();
		for(int i = 0; i < num_iter_mods; i++) {			
			BigInteger mod_bi = new BigInteger((int)(Math.random()*2048), new Random());
			Myr mod = new Myr(mod_bi.toString(16));
			BasicLookupReducer lktr = new BasicLookupReducer(mod);
			for(int j = 0; j < num_iter_reduces; j++) {
				BigInteger z_bi = new BigInteger((int)(Math.random()*1024), new Random());
				Myr z = new Myr(z_bi.toString(16));
				if(z_bi.compareTo(mod_bi.pow(2)) > 0) { j--; System.out.print("-"); continue;}
				assertTrue(z_bi.mod(mod_bi).toString(16).equals(lktr.reduce(z).toString()));
			}
		}
		System.out.println("finished. time taken: " + (double)(System.currentTimeMillis()-t0)/1000 + " sec");
		
		
	}
}
