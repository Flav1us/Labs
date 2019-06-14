package general;

import java.math.BigInteger;
import general.ByteRG;

public abstract class ByteRG extends RandomGenerator{
	
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
			res[i] = iterate();
		}
		return res;
	}

	public static byte getMask(int length) {
		return (byte) (Math.pow(2, length) - 1);
	}


	public abstract byte iterate();
}
