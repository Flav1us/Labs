package test;

import java.math.BigInteger;

import org.junit.Test;

import util.Util;

public class MiscTest {
	@Test
	public void testBigInteger() {
		//byte[] bytes = {(byte) 0x1F, (byte) 0x00, (byte) 0x01};
		byte[] bytes = {(byte) -3};
		System.out.println("as hex: " + Integer.toHexString(Byte.toUnsignedInt(bytes[0])));
		BigInteger b = new BigInteger(1, bytes);
		System.out.println("b: " + b.toString(16));
		byte[] by = b.toByteArray();
		System.out.println("to byte arr: ");
		for(int i = 0; i<by.length; i++) System.out.print(by[i] + "\t");
		System.out.println();
		System.out.println("bytes to hex: " + Util.bytesToHex(b.toByteArray()));
		System.out.println(new BigInteger(Util.invert(b.toByteArray())));
		System.out.println((byte)(0b100000011));
		System.out.println(Integer.toBinaryString(Util.shift13BitsLeft(0xEFFFFFE0)));
		long M = 0xFF1FFFFFEE1EEEEEL;
		int L = (int) M;
		int R = (int) (M >> 32);
		System.out.println(Integer.toHexString(L) + " " + Integer.toHexString(R));
	}
	
}
