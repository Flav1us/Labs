package main;

import static main.LinearPotential.scalar_mult;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import branch_and_bound.Node;

public class M2 {
	static final String dir = "C:\\Users\\ASUS\\Desktop\\D5_CP2\\";
	
	static String filepath_CT = dir + "c.bin";

	private static int neededLPcount = 400;
	
	public static SortedMap<Integer, List<Character>> attack(int ct_num) {
		try {
			
		InputStream is = new FileInputStream(filepath_CT);
		byte[] b = new byte[ct_num * 2];
		is.read(b);
		
		
		char[] inp_outp = new char[ct_num];
		for(int i = 0; i < b.length; i+=2) {
			char c1 =  (char)(((int)b[i] & 0xFF) ^ (b[i+1] << 8)); //(int)b[i] & 0xFF works, just b[i] doesnt work: converts 98 to ffffffff98 or something
			inp_outp[i/2] = c1;
		}
		
		return attack(inp_outp, LinearPotential.getNeededLP(neededLPcount ));
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static SortedMap<Integer, List<Character>> attack(char[] ct, Map<Character, List<Node>> highLP) {
		SortedMap<Integer, List<Character>> key_candidates_stat = new TreeMap<>(Comparator.reverseOrder());
		for (char key_candidate = 0; key_candidate < Character.MAX_VALUE; key_candidate++) {
			int stat = 0;
			for (Entry<Character, List<Node>> e : highLP.entrySet()) {
				char alpha = e.getKey();
				for (Node beta_node : e.getValue()) {
					char beta = beta_node.value;
					for (char x = 0; x < ct.length; x++) {
						char x1 = Heys.round(x, key_candidate);
						char y = ct[x];
						if (scalar_mult(alpha, x1) ^ scalar_mult(y, beta))
							stat--;
						else
							stat++;
					}
				}
			}
			if (stat < 0)
				stat = -stat;
			if (key_candidates_stat.get(stat) == null) {
				LinkedList<Character> new_l = new LinkedList<>();
				new_l.add(key_candidate);
				key_candidates_stat.put(stat, new_l);
			} else {
				key_candidates_stat.get(stat).add(key_candidate);
			}
		}
		return key_candidates_stat;
	}
	
	public static String hex(char y) {
		return Integer.toHexString(y);
	}
}
