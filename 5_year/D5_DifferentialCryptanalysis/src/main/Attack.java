package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Attack {
	
	static char input_diff = (char)0b1110000000000000;
	static Diff hiprob_diff = new Diff((char)/*0b0100_0000_0100_0100*/0x8808, 0.000366289604980805);
	static Diff hiprob_diff2 = new Diff((char)0b0100_0000_0100_0100, 0.000926289604980805);
	static String workdir = "C:\\Users\\ASUS\\Desktop\\D5_CP1";
	static String filepath_OT_basic = workdir + "\\m.bin";
	static String filepath_OT_different = workdir + "\\m_different.bin";
	static String filepath_CT_basic = workdir + "\\c.bin";
	static String filepath_CT_different = workdir + "\\c_different.bin";
	
	public static void attack() throws IOException {
		InputStream is = new FileInputStream(filepath_CT_basic);
		byte[] b = new byte[Character.MAX_VALUE*2];
		is.read(b);
		
		is = new FileInputStream(filepath_CT_different);
		byte[] b_diff = new byte[b.length];
		is.read(b_diff);
		
		Map<Character, Character> inp_outp = new HashMap<>();
		for(int i = 0; i < b.length; i+=2) {
			char c1 =  (char)(((int)b[i] & 0xFF) ^ (b[i+1] << 8)); //(int)b[i] & 0xFF works, just b[i] doesnt work: converts 98 to ffffffff98 or something
			char c2 =  (char)(((int)b_diff[i] & 0xFF) ^ (b_diff[i+1] << 8));
			inp_outp.put(c1, c2);
		}
		
		double_attack(inp_outp);
	}
	
	public static void attack(Map<Character, Character> inp_outp) {
		SortedMap<Integer, List<Character>> sm = new TreeMap<>(Comparator.reverseOrder());
		for (char c = 0; c < Character.MAX_VALUE; c++) {
			int stat = Attack.differentialCountStatistic(inp_outp, hiprob_diff.beta, c);
			if (sm.get(stat) == null) {
				LinkedList<Character> new_l = new LinkedList<>();
				new_l.add(c);
				sm.put(stat, new_l);
			} else {
				sm.get(stat).add(c);
			}
		}
		System.out.println(sm.firstKey());
		List<Character> e = sm.get(sm.firstKey());
		for (char c : e)
			System.out.println(Integer.toBinaryString(c));
	}
	
	public static void double_attack(Map<Character, Character> inp_outp) {
		SortedMap<Integer, List<Character>> sm1 = new TreeMap<>(Comparator.reverseOrder());
		SortedMap<Integer, List<Character>> sm2 = new TreeMap<>(Comparator.reverseOrder());
		for (char c = 0; c < Character.MAX_VALUE; c++) {
			int stat1 = Attack.differentialCountStatistic(inp_outp, hiprob_diff.beta, c);
			int stat2 = Attack.differentialCountStatistic(inp_outp, hiprob_diff2.beta, c);
			
			if (sm1.get(stat1) == null) {
				LinkedList<Character> new_l = new LinkedList<>();
				new_l.add(c);
				sm1.put(stat1, new_l);
			} else {
				sm1.get(stat1).add(c);
			}
			
			if (sm2.get(stat2) == null) {
				LinkedList<Character> new_l = new LinkedList<>();
				new_l.add(c);
				sm2.put(stat2, new_l);
			} else {
				sm2.get(stat2).add(c);
			}
		}
		//System.out.println(sm1.firstKey());
		//List<Character> e = sm1.get(sm1.firstKey());
		//for (char c : e)
		//	System.out.println(Integer.toBinaryString(c));
		
		//System.out.println();
		
		//System.out.println(sm2.firstKey());
		//List<Character> e2 = sm2.get(sm2.firstKey());
		//for (char c : e2)
		//	System.out.println(Integer.toBinaryString(c));
		
		for(Character c : sm1.get(sm1.firstKey())) {
			if(sm2.get(sm2.firstKey()).contains(c)) System.out.println(Integer.toHexString(c));
		}
	}
	
	public static int differentialCountStatistic(Map<Character, Character> enc_diff_pairs, char diff, char last_key_candidate) {
		int statistic = 0;
		for(Map.Entry<Character, Character> pair : enc_diff_pairs.entrySet()) {
			int before_last_key1 = pair.getValue() ^ last_key_candidate;
			int before_last_key2 = pair.getKey() ^ last_key_candidate;
			char five_rounds_xor_sixth_key1 = (char) Heys.decr_round((char)before_last_key1, (char)0);
			char five_rounds_xor_sixth_key2 = (char) Heys.decr_round((char)before_last_key2, (char)0);
			if(five_rounds_xor_sixth_key1 == (five_rounds_xor_sixth_key2 ^ diff)) {
				statistic++;
			}
		}
		return statistic;
	}
	
	public static void general_attack() throws IOException {
		generateBasicFiles();
		//encryptFilesWithExternalTool();
		//readEncrypted();
		//collectStatistics();
		//decide();
	}

	public static void generateBasicFiles() throws IOException {
		generateBasicFiles(filepath_OT_basic, filepath_OT_different, hiprob_diff.beta);
	}

	public static void generateBasicFiles(String filepath_basic, String filepath_different, char differential)
			throws IOException {
		byte diff_lower = (byte)differential;
		byte diff_higher = (byte)(differential >> 8);
		//System.out.println(Integer.toHexString(differential) + "\t" + Integer.toHexString(diff_lower) + " " + Integer.toHexString(diff_higher));
		File f_b = new File(filepath_basic);
		File f_d = new File(filepath_different);
		OutputStream os_basic = new FileOutputStream(f_b);
		OutputStream os_diff = new FileOutputStream(f_d);
		for(int i = 0; i < Character.MAX_VALUE/2; i++) {
			os_basic.write(i);
			os_diff.write(i ^ diff_lower);
			os_basic.write(i/(Byte.MAX_VALUE - Byte.MIN_VALUE + 1));
			os_diff.write((i/(Byte.MAX_VALUE - Byte.MIN_VALUE + 1)) ^ diff_higher);
		}
		os_basic.close();
		os_diff.close();
	}
}
