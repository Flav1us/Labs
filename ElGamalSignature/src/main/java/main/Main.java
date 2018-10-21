package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import signature.ElGamal;

public class Main {
	public static String empty = "C:\\Users\\ASUS\\empty.bin";
	public static String long_ = "C:\\Users\\ASUS\\toHashLong.bin";
	public static String big = "F:\\Games\\!install\\Witcher 3\\[R.G. Mechanics] The Witcher 3 Wild Hunt - GOTY\\data1.bin";
	
	public static void main(String[] args) throws IOException {
		String path = null;
		try {
			//path = args[0];
			path = big;
		}
		catch (Exception e) {
			System.out.println("first argument should be file path, for example \"C:\\\\Users\\\\username\\\\fileToSign.bin\"");
			System.exit(0);
		}
		//ElGamal.sign(path);
		//System.out.println("final hash:\t" + Long.toUnsignedString(PerByteMD.hash(empty), 16));
		//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
		System.out.println("signing " + path);
		ElGamal.sign(path);
	}

}
