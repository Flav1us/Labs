package test;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

public class testRAF {
	@Test
	public void test() throws IOException {
		RandomAccessFile fr = new RandomAccessFile("C:\\Users\\ASUS\\toHashLong.bin", "r");
		System.out.println("file length (bytes) : " + fr.length());
		for(int i = 0; i < fr.length(); i++) System.out.print(fr.readByte() + " ");
		System.out.println("\n");
		fr.seek(fr.length() - 1);
		long pad_len = 1 + (8 - (fr.length()+1)%8);
		System.out.println("pad len: " + pad_len);
		long res_len = fr.length() + pad_len;
		assert res_len % 8 == 0;
		
		fr.seek(fr.length() - (8-pad_len));
		byte[] t = new byte[8];
		fr.read(t);
		t[(int) (8-pad_len)] = Byte.MIN_VALUE;
		assert fr.getFilePointer() == fr.length();
		for(int i = 0; i < t.length; i++) System.out.print(t[i] + " "); System.out.println();
		System.out.println(fr.getFilePointer());
		
		//fr.seek(fr.getFilePointer() - pad_len);
		for(fr.seek(fr.getFilePointer() - (8-pad_len) - 8); fr.getFilePointer() >= 0; fr.seek(fr.getFilePointer() - 16)) {
			System.out.println(fr.getFilePointer());
			for(int i = 0; i < 8;i ++) System.out.print(fr.readByte() + " ");
			System.out.println();
		}
	}
}
