package hash;

import java.util.Arrays;

import misty.Misty;
import util.ByteUtils;

public class MD { //Merkle-Damgard
	
	public static long hash(byte[] message) {
		if(message.length % 8 != 0) throw new RuntimeException("error in hash: incorrect message length");
		long H = 0;
		int t = message.length / 8;
		long M;
		System.out.println("hashing");
		for(int i = 0; i < t; i+=8) {
			M = ByteUtils.bytesToLong(Arrays.copyOfRange(message, i, i+8));
			H = G(M, H);
			System.out.println("M" + i + " = " + Long.toHexString(M));
			System.out.println("H" + i + " = " + Long.toHexString(H));
		}
		System.out.println("hash: " + Long.toHexString(H));
		return H;
	}
	
	private static long G(long M, long H) { //var 9
		return Misty.encipher(M, M^H) ^ M;
	}
}
