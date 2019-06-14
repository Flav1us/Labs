package bm;

import java.math.BigInteger;

import general.ByteRG;

public class BM_byte extends BM {
	private static final BigInteger c128 = new BigInteger("128");
	
	byte iterate() {
		// k(p-1)/256 < Ti <= (k+1)(p-1)/256
		// kq/128 < Ti <= (k+1)q/128
		// k < Ti*128/q <= k+1
		// k = 128*Ti/q - 1
		byte k = T.multiply(c128).divide(q).subtract(BigInteger.ONE).byteValue();
		T = a.modPow(T, p);
		return k;
	}
	
	@Override
	public BigInteger genRandInt(int bit_length) {
		int byte_length = bit_length/8 + 1;
		byte[] res = new byte[byte_length];
		for(int i=0; i<byte_length; i++) {
			res[i] = iterate();
		}
		res[0] = (byte) (res[0]&ByteRG.getMask(bit_length%8));
		return new BigInteger(res);
	}
	
	@Override
	public byte[] genRandBytes(int byte_length) {
		byte[] res = new byte[byte_length];
		for(int i=0; i<byte_length; i++) {
			if(i%byte_length/100==0) System.out.print('.');
			res[i] = iterate();
		} System.out.println();
		return res;
	}
	
}
