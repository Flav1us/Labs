package bm;

import java.math.BigInteger;
import java.util.Random;

import general.RandomGenerator;

public class BM extends RandomGenerator {
	static final BigInteger p = new BigInteger("CEA42B987C44FA642D80AD9F51F10457690DEF10C83D0BC1BCEE12FC3B6093E3", 16);
	static final BigInteger a = new BigInteger("5B88C41246790891C095E2878880342E88C79974303BD0400B090FE38A688356", 16);
	//p = 2q+1
	static final BigInteger q = new BigInteger("675215CC3E227D3216C056CFA8F8822BB486F788641E85E0DE77097E1DB049F1", 16);
	BigInteger T = new BigInteger(256, new Random()); //entropy source, may be > 256

	public BM() {}
	
	@Override
	public BigInteger genRandInt(int bit_length) {
		boolean[] res = new boolean[bit_length];
		for(int i=0; i<bit_length; i++) {
			//if((i+1)%(bit_length/100) == 0) System.out.print('.');
			res[i] = iterate();
		}
		//System.out.println();
		return new BigInteger(boolArrToString(res), 2); //toBytes
	}
	
	@Override
	public byte[] genRandBytes(int byte_length) {
		boolean t;
		byte[] seq = new byte[byte_length];
		for(int i=0; i<byte_length; i++) {
			//if(i%(byte_length/100) == 0) System.out.print('.');
			for(int j=0; j<8; j++) {
				t = iterate();
				if(t == true) {
					seq[i] = (byte) (seq[i] | (byte)(1<<(7-j)));
				}
			} 
		}//System.out.println();
		return seq;
	}
	
	private boolean iterate() {
		boolean res = (T.compareTo(q) < 0) ? true : false;
		T = a.modPow(T, p);
		return res;
	}

}
