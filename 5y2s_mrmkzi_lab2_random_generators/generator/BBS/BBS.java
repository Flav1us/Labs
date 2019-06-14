package BBS;

import java.math.BigInteger;
import java.util.Random;

import general.BitRG;

public class BBS extends BitRG {
	BigInteger r = new BigInteger(128, new Random()); //128 == p.bitLength();
	static final BigInteger p = new BigInteger("D5BBB96D30086EC484EBA3D7F9CAEB07", 16);
	static final BigInteger q = new BigInteger("425D2B9BFDB25B9CF6C416CC6E37B59C1F", 16);
	static final BigInteger n = q.multiply(p);

	@Override
	public boolean iterate() {
		r = r.modPow(BigInteger.TWO, n);
		return r.testBit(0);
	}

	

}
