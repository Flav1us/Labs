package external_impl;
//project Nayuki

import java.math.BigInteger;
import java.util.Random;


public final class BigBarrettReducer {
	
	/* Self-test program */
	
	public static void main(String[] args) {
		System.out.print("Self-check...");
		for (int i = 0; i < 1000; i++) {
			BigInteger mod = randomModulus();
			BigInteger modSqr = mod.pow(2);
			int modSqrLen = modSqr.bitLength();
			BigBarrettReducer br = new BigBarrettReducer(mod);
			for (int j = 0; j < 100; j++) {
				BigInteger x;
				do x = new BigInteger(modSqrLen, rand);
				while (x.compareTo(modSqr) >= 0);
				if (!br.reduce(x).equals(x.mod(mod)))
					throw new AssertionError();
			}
		}
		System.out.println(" Passed");
	}
	
	
	private static BigInteger randomModulus() {
		int bits = rand.nextInt(1000) + 2;
		while (true) {
			BigInteger result = new BigInteger(bits, rand);
			if (result.bitLength() == bits && result.bitCount() > 1)
				return result;
		}
	}
	
	
	private static Random rand = new Random();
	
	
	
	/* Reusable library */
	
	private BigInteger modulus;
	private BigInteger factor;
	private int shift;
	
	
	public BigBarrettReducer(BigInteger mod) {
		if (mod.signum() <= 0)
			throw new IllegalArgumentException("Modulus must be positive");
		if (mod.bitCount() == 1)
			throw new IllegalArgumentException("Modulus must not be a power of 2");
		//System.out.println("BBR constructor");
		modulus = mod;
		//System.out.println("(bin)modulus: " + mod.toString(2));
		shift = modulus.bitLength() * 2;
		//System.out.println("(dec)shift: "+shift);
		factor = BigInteger.ONE.shiftLeft(shift).divide(mod);
		//System.out.println("(hex)factor: " + factor.toString(16));
	}
	
	
	// For x in [0, mod^2), this returns x % mod.
	public BigInteger reduce(BigInteger x) {
		assert x.signum() >= 0 && x.compareTo(modulus.pow(2)) < 0;
		//System.out.println("x.multiply(factor).shiftRight(shift) = " + x.multiply(factor).shiftRight(shift).toString(16));
		BigInteger t = x.subtract(x.multiply(factor).shiftRight(shift).multiply(modulus));
		return t.compareTo(modulus) < 0 ? t : t.subtract(modulus);
	}
	
}