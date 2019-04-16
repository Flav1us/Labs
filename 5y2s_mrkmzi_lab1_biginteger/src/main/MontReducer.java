package main;

/* 
 * Montgomery reduction algorithm (Java)
 * 
 * Copyright (c) 2018 Project Nayuki
 * All rights reserved. Contact Nayuki for licensing.
 * https://www.nayuki.io/page/montgomery-reduction-algorithm
 */


import java.util.Objects;


public final class MontReducer {
	
	// Input parameter
	public Myr modulus;  // Must be an odd number at least 3
	
	// Computed numbers
	public Myr reducer;       // Is a power of 2
	public int reducerBits;          // Equal to log2(reducer)
	public Myr reciprocal;    // Equal to reducer^-1 mod modulus
	public Myr mask;          // Because x mod reducer = x & (reducer - 1)
	public Myr factor;        // Equal to (reducer * reducer^-1 - 1) / n
	public Myr convertedOne;  // Equal to convertIn(Myr.ONE)
	
	
	
	// The modulus must be an odd number at least 3
	public MontReducer(Myr modulus) {
		// Modulus
		Objects.requireNonNull(modulus);
		if (modulus.marr[0] % 2 == 0 /*|| modulus.compareTo(Myr.ONE) <= 0*/)
			throw new IllegalArgumentException("Modulus must be an odd number at least 3");
		this.modulus = modulus;
		
		// Reducer
		//may take long
		reducerBits = (modulus.toBinString().length() / 8 + 1) * 8;  // This is a multiple of 8
		reducer = Myr.ONE.shiftBits(reducerBits);  // This is a power of 256
		mask = Myr.LongSub(reducer, Myr.ONE);
		//System.out.println(Myr.gcdEvkl(reducer, modulus).toString());
		assert reducer.compareTo(modulus) > 0 && Myr.gcdEvkl(reducer, modulus).equals(Myr.ONE);
		
		// Other computed numbers
		reciprocal = reducer.modInverse(modulus);
		factor = reducer.multiply(reciprocal).subtract(Myr.ONE).divide(modulus);
		convertedOne = reducer.mod(modulus);
	}
	
	
	
	// The range of x is unlimited
	public Myr convertIn(Myr x) {
		return x.shiftBits(reducerBits).mod(modulus);
	}
	
	
	// The range of x is unlimited
	public Myr convertOut(Myr x) {
		return x.multiply(reciprocal).mod(modulus);
	}
	
	
	// Inputs and output are in Montgomery form and in the range [0, modulus)
	public Myr multiply(Myr x, Myr y) {
		assert x.compareTo(modulus) < 0;
		assert y.compareTo(modulus) < 0;
		Myr product = x.multiply(y);
		Myr temp = product.and(mask).multiply(factor).and(mask);
		Myr reduced = product.add(temp.multiply(modulus)).shiftBits(-reducerBits);
		Myr result = reduced.compareTo(modulus) < 0 ? reduced : reduced.subtract(modulus);
		assert result.compareTo(modulus) < 0;
		return result;
	}
	
	
	// Input x (base) and output (power) are in Montgomery form and in the range [0, modulus); input y (exponent) is in standard form
	public Myr pow(Myr x, Myr y) {
		assert  x.compareTo(modulus) < 0;
		
		Myr z = convertedOne;
		for (int i = 0, len = y.toBinString().length(); i < len; i++) {
			if (y.testBit(i))
				z = multiply(z, x);
			x = multiply(x, x);
		}
		return z;
	}
	
}