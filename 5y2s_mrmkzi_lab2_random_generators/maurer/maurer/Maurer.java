package maurer;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Maurer {
	public static final int maxBitsForTrialDivisions = 18, m = maxBitsForTrialDivisions;
	public static final int certainty = 5;
	public static final double c = 0.1;
	public static boolean debug_mode = false;
	private static final BigInteger TWO = new BigInteger("2");
	//public static final int m = 20;
	static long selected_time = 0;
	int recursion_depth = 0;
	static List<BigInteger> primes = new LinkedList<BigInteger>();
	
	static {
		if(debug_mode) System.out.println("Ingoring precalculation");
		/*long t0 = System.currentTimeMillis();
		for(BigInteger bi = new BigInteger("2"); bi.compareTo(BigInteger.ONE.shiftLeft(maxBitsForTrialDivisions)) < 0; bi = bi.add(BigInteger.ONE)) {
			if(bi.isProbablePrime(certainty)) {
				primes.add(bi);
				//System.out.println(bi.toString(16));
			}
		}
		System.out.println("precalc primes complete in " + (System.currentTimeMillis() - t0) + " ms");*/
	}

	public BigInteger recursiveMauer(int k) { // k is number of bits
		recursion_depth ++;
		//TODO maurer pseudocode
		if(debug_mode) System.out.println("start");
		if(k <= maxBitsForTrialDivisions) {
			BigInteger n;
			do {
				n = new BigInteger(k, new Random());
				if(debug_mode)System.out.println("while in the beginning");
			} while (!n.isProbablePrime(certainty));
			if(!n.isProbablePrime(certainty)) System.out.println("test failed for trial divisions");
			return n;
		}
		double r;
		if (k > 2*m) do {
			r = Math.pow(2, Math.random() - 1);
			if(debug_mode) System.out.println("while in the middle: r = " + r);
		} while ((k - r*k) <= m); // <= ? //r? http://www.excel-team.ru/do_while_until.php
		else r = 0.5;
		//System.out.println("1: R = " + R);
		
		
		BigInteger q = recursiveMauer((int) Math.floor(r * k)/* + 1 */); // TODO check bounds
		
		//System.out.println("q = " + q.toString(16));
		BigInteger I = BigInteger.ONE.shiftLeft(k-1).divide(q.multiply(TWO));
		//System.out.println("I = " + I.toString(16));
		
		BigInteger n = null; 
		boolean flag = false;
		while(flag == false) {
			BigInteger R;
			do {
				
				if (debug_mode)
					System.out.println("while in maurer");
				
				do {
					//R = randomIntegerBelow(q.multiply(TWO), debug_mode);
					R = randomIntegerBelow(I, debug_mode).add(I);
				} while(!R.multiply(TWO).gcd(q).equals(BigInteger.ONE));
				
				if (debug_mode)
					System.out.println("R = " + R.toString(16));
				n = TWO.multiply(R).multiply(q).add(BigInteger.ONE); // 2*T*q+1
				// System.out.println("n = " + n.toString(16));
				
			} while (!n.isProbablePrime(certainty));
			//if(n.isProbablePrime(certainty)) {
				if(debug_mode)System.out.println("inner while");
				BigInteger a = randomIntegerBelow(n.subtract(new BigInteger("4")).add(TWO), debug_mode); //random in [2; n-2];
				assert(a.compareTo(TWO) >= 0 && a.compareTo(n.subtract(TWO)) <= 0);
				BigInteger b = a.modPow(n.subtract(BigInteger.ONE), n);
				if(b.equals(BigInteger.ONE)) {
					b = a.modPow(TWO.multiply(R), n);
					BigInteger d = n.gcd(b.subtract(BigInteger.ONE));
					if(d.equals(BigInteger.ONE)) flag = true;
				}
			//}
		}
		if(n == null) System.out.println("n is null");
		if(debug_mode) {System.out.println("end");
		System.out.println("selected time = " + selected_time + " ms");
		System.out.println("recursion_depth " + recursion_depth);}
		return n;
	}

	public static boolean isPrime_TrialDivisions(BigInteger n) { // too slow!!
		
		if(n.equals(BigInteger.ZERO)) return false;
		if(n.equals(BigInteger.ONE)) return false;
		//if(n.bitLength() > maxBitsForTrialDivisions) throw new IllegalArgumentException("max bits count exceeded for trial division");
		ListIterator<BigInteger> primes_iterator = primes.listIterator(0);
		
		for(BigInteger b = primes_iterator.next(); b.compareTo(n.sqrt()) <= 0 && primes_iterator.hasNext(); b = primes_iterator.next()) {
			if(n.mod(b).equals(BigInteger.ZERO)) return false;
		}
		
		return true;
	}
	
	public static BigInteger randomIntegerBelow(BigInteger N, boolean debug_mode) { //TODO test
		Random r = new Random();
		if(debug_mode)System.out.println("rib: N = " + N.toString(2));
		BigInteger res = null;
		do {
			int bitlen = N.bitLength()-1;
			/* -> 200000000000000000000asdasdasd -like numbers. why?
			do bitlen = (int)(Math.random()*(N.bitLength())); // 
			while(bitlen < 2);*/
			long t0 = System.currentTimeMillis();
			//res = BigInteger.probablePrime(bitlen, r);
			res = new BigInteger(bitlen, r);//BigInteger.probablePrime(bitlen, r);
			selected_time += System.currentTimeMillis() - t0;
			if(debug_mode)System.out.println("'while' in randIntBelow, res = " + res.toString(16));
		} while (res.compareTo(N) >= 0); //|| res.compareTo(BigInteger.ZERO) <= 0);
		return res;
	}


}
