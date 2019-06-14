package testMISTY;

import org.junit.Ignore;
import org.junit.Test;
import static util.ByteUtils.bytesToInt;
import static util.ByteUtils.intToBytes;
import static util.ByteUtils.invert;
import static util.ByteUtils.bytesToLong;
import static util.ByteUtils.longToBytes;
import hash.PerByteMD;
import misty.Misty;

public class MistyTest {

	@Test
	public void test() {
		System.out.println(Long.toHexString(PerByteMD.G(Long.parseUnsignedLong("6B61707061800000", 16), 0)));
	}

	@Ignore
	@Test
	public void testBytes() {
		byte bt = (byte)-128;
		System.out.println(Integer.toHexString(Byte.toUnsignedInt(Byte.MIN_VALUE)<<8));
		int it1 = -128;
		System.out.println(it1<<1);
		System.out.println("---------");
		int i1 = 0x61800000;
		byte[] t = intToBytes(i1);
		for(int i = 0; i < t.length; i++) System.out.println(t[i]);
		byte[] it = invert(t);
		System.out.println();
		for(int i = 0; i < t.length; i++) System.out.println(it[i]);
		System.out.println();
		System.out.println(Integer.toHexString(bytesToInt(it)));
		System.out.println(Integer.toUnsignedString((int)it[it.length-1] + ((int)it[it.length-2])*256, 16));
		System.out.println(Integer.toHexString(it[it.length-2]*256));
		System.out.println(Integer.toHexString(-128*64));
		System.out.println();
		System.out.println("===============================");
	}
}
