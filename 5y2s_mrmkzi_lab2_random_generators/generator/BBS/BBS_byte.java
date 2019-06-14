package BBS;

import java.math.BigInteger;
import java.util.Random;

import general.ByteRG;

public class BBS_byte extends ByteRG {
	BigInteger r = new BigInteger(128, new Random()); //128 == p.bitLength();
	static final BigInteger p = BBS.p;
	static final BigInteger q = BBS.q;
	static final BigInteger n = q.multiply(p);
	BigInteger c256 = new BigInteger("256");
	
	@Override
	public byte iterate() {
		r = r.modPow(BigInteger.TWO, n);
		byte[] arr = r.mod(c256).toByteArray(); //�� ���������� ������� (�.�. ����) ������ ��������� ������� ����
		return arr.length==1?arr[0]:arr[1];
	}

}
