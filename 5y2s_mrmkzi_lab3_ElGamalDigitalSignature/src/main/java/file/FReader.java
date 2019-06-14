package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import util.Util;

public class FReader {
	/*public static byte[] read(String path) throws IOException {
		//padding implemented here
		//bytes in LCM
		Path p = Paths.get(path);
		byte[] file = Files.readAllBytes(p);
		BigInteger t = new BigInteger(file); //prbly cuts leading zeroes in binary form?
		StringBuilder msg = new StringBuilder(t.toString(2));
		msg.append('1');
		while(msg.length() % 64 > 0) msg.append('0');
		return Util.invert(new BigInteger(msg.toString(), 2).toByteArray());
	}*/
	
	/*public static byte[] read(String path) throws IOException {
		Path p = Paths.get(path);
		byte[] file = Files.readAllBytes(p);
		System.out.println("input file length: " + file.length);
		System.out.println("input file: " + Util.bytesToHex(file));
		byte[] paddedFile = new byte[file.length + 1];
		if((paddedFile.length + 1) % 8 > 0) {
			paddedFile = new byte[file.length + 1 + (8 - (file.length+1)%8)];
		}
		for(int i = 0; i<file.length; i++) paddedFile[i] = file[i];
		paddedFile[file.length] = (byte)0b10000000;
		for(int i = file.length + 1; i < paddedFile.length; i++)  paddedFile[i] = (byte)0;
		System.out.println("padded file: " + Util.bytesToHex(paddedFile));
		if(paddedFile.length % 8 != 0) throw new RuntimeException("error: length in padding: " + paddedFile.length);
		return paddedFile;
	}*/
	
	public static byte[] read(String path) throws IOException {
		return new byte[]{0, 0, 0, 0, 1, 0, 0, 0};
	}
}
