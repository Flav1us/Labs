package general;

import java.math.BigInteger;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public abstract class RandomGenerator {
	
	
	public abstract BigInteger genRandInt(int bit_length);
	public abstract byte[] genRandBytes(int byte_length);
	
	
	public static String boolArrToString(boolean[] b) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<b.length; i++) {
			//System.out.print(b[i]==false?'0':'1');
			s.append(b[i] == false ? '0' : '1');
		}
		//System.out.println();
		//StringUtils.leftPad(b.toString(2), size, '0');
		return s.toString();
	}
	
	public boolean[] getRandBoolArr(int size) {
		BigInteger b = new BigInteger(size, new Random());
		String st = StringUtils.leftPad(b.toString(2), size, '0');
		if(st.length() != size) System.out.println("error: st.len = " + st.length() + " != " + size);
		boolean[] res = new boolean[size];
		for(int i=0; i<res.length; i++) res[i] = st.charAt(i)=='1' ? true : false;
		return res;
	}
	
	public static boolean[] stringToBoolArr(String s) throws IllegalArgumentException {
		//System.out.println("stoba: " + s);
		boolean[] b = new boolean[s.length()];
		for(int i=0; i<b.length; i++) {
			if(s.charAt(i) == '0') b[i] = false;
			else if(s.charAt(i) == '1') b[i] = true;
			else throw new IllegalArgumentException("String should contain '0' or '1'. Found '" + s.charAt(i)+ "'.");
		}
		return b;
	}
}
