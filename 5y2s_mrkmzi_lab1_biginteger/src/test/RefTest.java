package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import external_impl.BigBarrettReducer;
import main.MontRed_reference;

public class RefTest {

	@Ignore
	@Test
	public void test_mont() {
		int test_cases = 10;
		for (int i = 0; i < test_cases; i++) {
			BigInteger mod = BigInteger.probablePrime(1024, new Random());
			BigInteger a = new BigInteger(1023, new Random());
			BigInteger b = new BigInteger(1023, new Random());

			BigInteger c = a.multiply(b).mod(mod);

			MontRed_reference ref = new MontRed_reference(mod);
			BigInteger d = ref.convertOut(ref.multiply(ref.convertIn(a), ref.convertIn(b)));

			assertTrue(c.equals(d));

			c = a.modPow(b, mod);
			d = ref.convertOut(ref.pow(ref.convertIn(a), b));
			//System.out.println(c + "\n" + d);
			
			assertTrue(c.equals(d));
		}
	}
	
	@Test
	public void test_barr() {
		for(int j = 0; j < 10; j++) {
		BigInteger mod = new BigInteger(1024, new Random());
		BigBarrettReducer bbr = new BigBarrettReducer(mod);
		for(int i = 0; i < 100; i++) {
			BigInteger x;
			do { //ref impl works for x < mod^2.
				x = new BigInteger(2048, new Random());
			} while(x.compareTo(mod.pow(2)) >= 0);
			
			//System.out.println(bbr.reduce(x).toString(16));
			//System.out.println(x.mod(mod).toString(16));
			//System.out.println();
			assertTrue(bbr.reduce(x).toString(16).equals(x.mod(mod).toString(16)));
		}
		}
	}

}
