package test;

import static org.junit.Assert.assertTrue;
import static util.ByteUtils.bytesToLong;
import static util.ByteUtils.longToBytes;

import org.junit.Test;

import util.ByteUtils;

public class Basic {
	@Test
	public void testLong() {
		long l = Long.parseUnsignedLong("8FFFFFFFFFF11111", 16);
		byte[] lb = longToBytes(l);
		for(int i = 0; i <lb.length; i++) System.out.print(lb[i] + " ");System.out.println();
		assertTrue(bytesToLong(longToBytes(l)) == l);

		l = Long.parseUnsignedLong("11", 16);
		assertTrue(bytesToLong(longToBytes(l)) == l);
		
		int i = 321482794;
		//System.out.println(ByteUtils.bytesToInt(ByteUtils.intToBytes(i)));
		assertTrue(ByteUtils.bytesToInt(ByteUtils.intToBytes(i)) == i);
		
		byte b = Byte.MIN_VALUE;
		System.out.println((Byte.toString(b)));
		
		/*ByteBuffer buf = ByteBuffer.allocate(4);
		byte[] bytes = {(byte)0xEE, (byte)0x01, (byte)0x01, (byte)0xFF};
		buf = ByteBuffer.wrap(bytes);
		System.out.println(buf.getInt());
		buf.flip();
		System.out.println(buf.getInt());*/
		System.out.println(Long.toUnsignedString(Long.MIN_VALUE, 2));
		System.out.println(Long.toUnsignedString(Long.MIN_VALUE >>> 32, 2));
	}
}
