package test;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

import main.Attack;
import main.Heys;

public class testAttack {
	static char[] key = {0x3864, 0xffff, 0x1234, 0x11ff, 0xaabb, 0x4321, 0x2345};
	
	@Ignore
	@Test
	public void testStatistic() {
		Map<Character, Character> data = new TreeMap<>();
		char inp_diff = 0b1110000000000000;
		char out_diff = 0b1000000010001000;
		for(char c = Character.MIN_VALUE ; c < Character.MAX_VALUE; c++) {
			data.put(Heys.encrypt(c, key), Heys.encrypt((char)(c ^ inp_diff), key));
		}
		
		SortedMap<Integer, List<Character>> sm = new TreeMap<>(Comparator.reverseOrder());
		
		for(char c = 0; c < Character.MAX_VALUE; c++) {
			int stat = Attack.differentialCountStatistic(data, out_diff, c);
			if(sm.get(stat) == null) {
				LinkedList<Character> new_l = new LinkedList<>();
				new_l.add(c);
				sm.put(stat, new_l);
			}
			else {
				sm.get(stat).add(c);
			}
			
		}
		
		System.out.println(sm.firstKey());
		List<Character> e = sm.get(sm.firstKey());
		for(char c : e) System.out.println(Integer.toHexString(c));
	}
	
	//@Ignore
	@Test
	public void testExternalEncipher() throws IOException {
		String dir = "C:\\Users\\ASUS\\Desktop\\D5_CP1\\test";
		InputStream is = new FileInputStream(dir + "\\c_test.bin");
		byte[] b = new byte[Character.MAX_VALUE*2];
		int count = is.read(b);
		assertTrue(count == b.length);
		
		char[] my_c = new char[Character.MAX_VALUE];
		for(char c = 0; c < my_c.length; c++) {
			my_c[c] = Heys.encrypt(c, key);
		}
		
		for(int i = 0; i < b.length; i+=2) {
			char c =  (char)(((int)b[i] & 0xFF) ^ (b[i+1] << 8));;
			 
			//System.out.print(Integer.toHexString(b[i]) + "\t" + Integer.toHexString(b[i+1]) + "\t");
			System.out.println(Integer.toHexString(c) + "\t" + Integer.toHexString(my_c[i/2]));
			assertTrue(c == my_c[i/2]);
		}
		
		is.close();
		is = new FileInputStream(dir + "\\c_test_different.bin");
		byte[] b_diff = new byte[Character.MAX_VALUE*2];
		count = is.read(b_diff);
		assertTrue(count == b_diff.length);
		
		
		is.close();
	}
	
	
/*	@Ignore
	@Test
	public void testHighprobDiff() {
		char input_diff = 0b1110000000000000;
		char diff = 0b1000000010001000;
		int stat = 0;
		for(char c = 0; c < Character.MAX_VALUE; c++) {
			char a = fiveRoundsEnc(c);
			char b = fiveRoundsEnc((char)(c ^ input_diff));
		}
	}

	private char fiveRoundsEnc(char t) {
		for(int i = 0; i < 5; i++) {
			t = Heys.round(t, key[i]);
		}
		return t;
	}
	
	*/
}
