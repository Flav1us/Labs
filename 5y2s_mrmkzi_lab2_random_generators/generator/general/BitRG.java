package general;

import java.math.BigInteger;

public abstract class BitRG extends RandomGenerator{
	
	@Override
	public BigInteger genRandInt(int bit_length) {
		boolean[] res = new boolean[bit_length];
		for(int i=0; i<bit_length; i++) {
			res[i] = iterate();
		}
		return new BigInteger(boolArrToString(res), 2);
	}
	
	@Override
	public byte[] genRandBytes(int byte_length) {
		boolean t;
		byte[] seq = new byte[byte_length];
		for(int i=0; i<byte_length; i++) {
			for(int j=0; j<8; j++) {
				t = iterate();
				if(t == true) {
					seq[i] = (byte) (seq[i] | (byte)(1<<(7-j))); //первый сгенерированный бит должне быть первым
				}
			}
		}
		return seq;
	}
	
	public abstract boolean iterate();
}
