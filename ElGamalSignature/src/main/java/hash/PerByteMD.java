package hash;

import java.io.IOException;
import java.io.RandomAccessFile;

import misty.Misty;
import util.ByteUtils;

public class PerByteMD {
	public static long hash(String path) throws IOException {
		System.out.println("-- hashing --");
		RandomAccessFile fr = new RandomAccessFile(path, "r");
		System.out.println("file length (bytes) : " + fr.length());
		long pad_len = (1 + (8 - (fr.length()+1)%8))%8;
		if(fr.length() == 0) pad_len = 8; // КОСТЫЛЬ на пустой файл!
		System.out.println("padding length = " + pad_len);
		long res_len = fr.length() + pad_len;
		assert res_len % 8 == 0;
		long H = 0;
		long M;
		/*if(pad_len == 8) { //для обратного
			//M = fr.seek(fr.length());
			M = Long.MIN_VALUE;
			H = G(M, H);
		}
		else {
			fr.seek(fr.length() - (8 - pad_len));
			byte[] t = new byte[8];
			fr.read(t);
			t[(int) (8-pad_len)] = Byte.MIN_VALUE;
			assert fr.getFilePointer() == fr.length();
			//fr.seek(fr.length() - (8 - pad_len) - 8);
		}
		
		//byte[] bt = new byte[8];
		for(fr.seek(fr.getFilePointer() - (8-pad_len) - 8); fr.getFilePointer() >= 0; fr.seek(fr.getFilePointer() - 16)) {
			//for(int i = 0; i < 8;i ++) bt[i] = fr.readByte();
			if(fr.getFilePointer() == 8) break;
			System.out.println("pointer: " + fr.getFilePointer());
			M = fr.readLong();
			H = G(M, H);
		}*/
		
		for(int i = 1; fr.getFilePointer() < fr.length() + pad_len - 8; i++) {
			System.out.println(" ");
			System.out.println("\ndone: " + fr.getFilePointer() + " / " + fr.length() + "\t(" + ((double)fr.getFilePointer()*100/fr.length()) + "%)");
			M = fr.readLong();
			System.out.println("M" +i + ": " + Long.toUnsignedString(M,  16));
			H = G(M, H);
			System.out.println("H" +i + ": " + Long.toUnsignedString(H,  16));
		}
		byte[] bt = new byte[8];
		for(int i = 0; i < 8-pad_len;i ++) bt[i] = fr.readByte();
		bt[(int) (8-pad_len)] = Byte.MIN_VALUE;
		M = ByteUtils.bytesToLong(bt);
		H = G(M, H);
		System.out.println("H : " + Long.toUnsignedString(H,  16));
		fr.close();
		return H; // test not inverted
		
	}
	

	public static long G(long M, long H) { //var 9
		return Misty.encipher(M, M^H) ^ M;
	}
}
