package misty;

import static util.ByteUtils.bytesToInt;
import static util.ByteUtils.intToBytes;
import static util.ByteUtils.invert;

import util.ByteUtils;
import util.Util;

public class Misty {
	 private static final int[] sbox = 
		 {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
		  0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
		  0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
		  0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
		  0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
		  0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
		  0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
		  0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
		  0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
		  0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
		  0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
		  0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
		  0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
		  0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
		  0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
		  0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16};
	 
	 public static long encipher(long message, long key) {
		 return E(key, message);
	 }
	 
	 public static long E(long K, long M) {
		 System.out.println("Misty: M: " + Long.toUnsignedString(M, 16));
		 System.out.println("Misty: K: " + Long.toUnsignedString(K, 16));
		 int R = (int) M; // в методе наоборот
		 int L = (int) (M >> 32); 

		 R = bytesToInt(invert(intToBytes(R)));
		 L = bytesToInt(invert(intToBytes(L)));

		 int[] K_ = new int[4];
		 K_[1] = (int) K;
		 K_[0] = (int) (K >> 32);
		 
		 K_[0] = bytesToInt(invert(intToBytes(K_[0])));
		 K_[1] = bytesToInt(invert(intToBytes(K_[1])));
		 
		 /*byte[] K1b = Arrays.copyOf(ByteUtils.longToBytes(K), 4);
		 ByteBuffer.wrap(K1b).flip();
		 K_[0] = ByteBuffer.wrap(K1b).getInt();

		 
		 byte[] K0b = Arrays.copyOfRange(ByteUtils.longToBytes(K), 4, 8);
		 K_[1] =  ByteBuffer.wrap(K0b).getInt();*/
		 
		 K_[2] = ~K_[1]; 
		 K_[3] = ~K_[0];
		 
		 System.out.println("Keys: K0 = " + Integer.toHexString(K_[0]) + ", K1 = " + Integer.toHexString(K_[1]) + ", K2 = " + Integer.toHexString(K_[2]) + ", K3 = " + Integer.toHexString(K_[3]));
		
		 
		 System.out.print("L0: " + Integer.toHexString(L) + "\t");
		 System.out.println("R0: " + Integer.toHexString(R));
		 
		 int L_prev;
		 for(int i = 0; i < 4; i++) {
			 //System.out.println("S(K)\nS(K_[i]^R\t" + tBS(S(K_[i]^R)) + "\n<<<13\t" + tBS(Util.shift13BitsLeft(S(K_[i]^R))) + "\nL\t" + tBS(L) + "\nxor\t" + tBS((Util.shift13BitsLeft(S(K_[i]^R)) ^ L)));
			//// System.out.println("real\t" + tBS(Integer.parseUnsignedInt("79B4EC1D", 16)) + "\t" + "79B4EC1D");
			 //System.out.println("my:\t" + tBS((Util.shift13BitsLeft(S(K_[i]^R)) ^ L)) + Integer.toHexString(Util.shift13BitsLeft(S(K_[i]^R)) ^ L));
			 L_prev = L; //если не сохранять, R запомнит не то
			 L = F(K_[i], R) ^ L_prev;
			 R = L_prev;
			 System.out.println("L" + (i+1) + ": " + Integer.toHexString(L));
			 //System.out.println("L" + (i+1) + ": " + Integer.toBinaryString(L));
			 
			 //System.out.println("R" + (i+1) + ": " + Integer.toHexString(R));
		 }
		 
		 //System.out.println(Long.toHexString((long)R) + " " +  Long.toHexString((long)L<<32));
		 long res = Integer.toUnsignedLong(R) + (Integer.toUnsignedLong(L)<<32);
		 
		 res = ByteUtils.bytesToLong(Util.invert(ByteUtils.longToBytes(res)));
		 System.out.println("encrypt output: " + Long.toHexString(res));
		 return res;
		 //22.05 05:55	можно проверить: H = E_K(A) xor B, это же шифрование
	 }
	 
	 private static int F(int K, int R) {
		// System.out.println("F :\tS(" + Integer.toBinaryString(K) + " ^ " + Integer.toBinaryString(R) + ") = " + "S(" + Integer.toBinaryString(K^R) + ")");
		// System.out.println("F :\tS(K_[i] = " + tBS(K) + " ^ R = " + tBS(R) + ") = " + "S(" + tBS(K^R) + ")\t" + tBS(S(K^R)) + "\t" +  Integer.toHexString(Util.shift13BitsLeft(S(K ^ R))));
		 
		return Util.shift13BitsLeft(S(K ^ R));
	 }

	 //TODO: private (pub for test)
	public static int S(int X) {
		//System.out.println("X = " + Integer.toHexString(X));
		byte[] T = intToBytes(X);
		byte[] Y = new byte[4];
		for (int i = 0; i < Y.length; i++) {
			//System.out.println(T[i]);
			Y[i] = (byte) sbox[Byte.toUnsignedInt(T[i])];
		}
		int S = bytesToInt(Y);
		return S;
	}
	
	public static String tBS(int arg) { //int to binary string, length = 32
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toBinaryString(arg));
		while(sb.length() < 32) sb.insert(0, '0');
		assert sb.length() == 32;
		return sb.toString();
	}
	
}

