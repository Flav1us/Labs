package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import main.MontRed_reference;

public class RefMontTest {

	@Test
	public void test() {
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

}
