package signature;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import hash.PerByteMD;
import util.ByteUtils;
import util.Util;

public class ElGamal {
	private static final BigInteger 
 		p = new BigInteger("AF5228967057FE1CB84B92511BE89A47",16),
		q = new BigInteger("57A9144B382BFF0E5C25C9288DF44D23",16),
		a = new BigInteger("9E93A4096E5416CED0242228014B67B5",16),
		x = new BigInteger("A69B1F284D33B191FDB59099A0703E0C",16); //secret key
		//x = new BigInteger(128, new Random());
	
	public static void sign(String file_path) throws IOException {
		BigInteger[] sign = sign(PerByteMD.hash(file_path));
		System.out.println("k = " + sign[0].toString(16));
		System.out.println("S = " + sign[1].toString(16));
	}
	
	private static BigInteger[] sign(long msg_hash) {
		System.out.println(" ");
		System.out.println("-- signing --");
		assert x.compareTo(p) <= 0;
		msg_hash = ByteUtils.bytesToLong(Util.invert(ByteUtils.longToBytes(msg_hash)));
		byte[] Hp = H_pad(msg_hash);
		Hp = Util.invert(Hp);
		System.out.println("padded hash: " + new BigInteger(1, Hp).toString(16));
		//ByteBuffer.wrap(Hp).flip();
		for(int i = 0; i < Hp.length; i++) System.out.print(Integer.toHexString(Byte.toUnsignedInt(Hp[i])) + " "); System.out.println();

		BigInteger 	H = new BigInteger(1, Hp),
					//H = new BigInteger("FFFFFFFFFFFF0057AB1629FD782025", 16),
					//U = new BigInteger(128, new Random()),
					U = new BigInteger("36C35C862ED90C1327D086CB0CA7F939", 16),
					Z = H.multiply(a.modPow(U, p)).mod(p), //mod p?? с ним k = 0
					k = U.add(Z).multiply(x.add(BigInteger.ONE).modInverse(q)).mod(q),
							//k = U.subtract(x.multiply(H).multiply(Z)).divide(new BigInteger("2")).mod(q),
					g = U.multiply(x).subtract(Z).multiply(x.add(BigInteger.ONE).modInverse(q)).mod(q),
					S = a.modPow(g, p),
					y = a.modPow(x, p);
		//System.out.println("?? " + U.add(Z).toString(16));
		//System.out.println("from test: " + new BigInteger("4D957C65F0BA854E88294E4C874AA946", 16).multiply(x.add(BigInteger.ONE)).mod(q).toString(16) + "\t" + U.add(Z).mod(q).toString(16));
		//System.out.println("x: " + x.toString(16) + "\tx+1: " +x.add(BigInteger.ONE).toString(16));
		System.out.println("X: " + x.toString(16));
		System.out.println("y: " + y.toString(16));
		System.out.println("U: " + U.toString(16));
		System.out.println("H: " + H.toString(16));
		System.out.println("Z: " + Z.toString(16));
		System.out.println("g: " + g.toString(16));
		System.out.println("K: " + k.toString(16));
		//System.out.println("should be zero: " + g.add(Z).mod(q));
		//System.out.println(U.multiply(x).subtract(Z).mod(q).toString(16)); // -g
		System.out.println("S: " + S.toString(16));
		//System.out.println(a.modPow(x.multiply(U.add(Z)).divide(x.add(BigInteger.ONE)), p).toString(16));
		/*if(!y.modPow(k, p).equals(S.multiply(a.modPow(H.multiply(S).multiply(a.modPow(k, p)), p)))) {
			throw new RuntimeException("verification failed: " + y.modPow(k, p) + "\t" + S.multiply(a.modPow(H.multiply(S).multiply(a.modPow(k, p)), p)));
		}*/
		//System.out.println("verification " + y.modPow(k, p).toString(16) + "\t" + S.multiply(a.modPow(H.multiply(S.multiply(a.modPow(k, p))), p)).toString(16));
		System.out.println("verification:");
		System.out.println(y.modPow(k, p).toString(16));
		System.out.println(S.multiply(a.modPow(H.multiply(S).multiply(a.modPow(k, p)).mod(p), p)).mod(p).toString(16));
		//System.out.println(S.multiply(a.modPow(k, p)).mod(p).toString(16) + "\t" + a.modPow(U, p).toString(16) + "\t" + a.modPow(g, p).multiply(a.modPow(k, p)).mod(p).toString(16) + "\t");
		//System.out.println("??? отличаются на 1: " + U.toString(16) + "\t" + U.add(Z).divide(x.add(BigInteger.ONE)).add((U.multiply(x)).subtract(Z).divide(x.add(BigInteger.ONE))).toString(16));
		//assert a.modPow(U.multiply(x).subtract(Z).multiply(x.add(BigInteger.ONE).modInverse(q)), p).equals(S);
		//System.out.println("!!! " + S.multiply(a.modPow(H.multiply(a.modPow(U, p)), p)).mod(p).toString(16) + "\t" + a.modPow(x.multiply(U.add(H.multiply(a.modPow(U, p))).divide(x.add(BigInteger.ONE))), p).toString(16));
		//System.out.println("U:" + U.toString(16) + "\t" + U.mod(q).toString(16));
		BigInteger[] res = new BigInteger[2];
		res[0] = k;
		res[1] = S;
		return res;
	}

	private static byte[] H_pad(long msg_hash) {
		byte[] H = new byte[16];
		byte[] mh = ByteUtils.longToBytes(msg_hash);
		for(int i = 0; i < mh.length; i++) {
			H[i] = mh[mh.length - 1 - i];
		}
		for(int i = mh.length+1; i < H.length-1; i++) {
			H[i] = ~(byte)0;
			//H[i] = Byte.MIN_VALUE; //? 0b100000000000...0 ?
		}
		return H;
	}
	
}
