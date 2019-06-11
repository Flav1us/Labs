package test;

import static main.LinearPotential.scalar_mult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

import branch_and_bound.Node;
import main.Heys;
import main.LinearPotential;
import main.M2;
import main.SerializableLPTable;

public class Modules {
	
	
	static char[] key = {0xfeef, 0x4321, 0xabcd, 0xdcba, 0xefef, 0xfefe, 0x1111};
	static SerializableLPTable lpt = null;
	
	@Test //@Ignore
	public void testWithKnownKey() {
		int needed_ct = 1000;
		char[] m = new char[needed_ct];
		char[] c = new char[needed_ct];
		for(char i = 0; i < needed_ct; i++) {
			m[i] = i;
			c[i] = Heys.encrypt(i, key);
		}
		
		Map<Character, List<Node>> lp = new HashMap<>();
		List<Node> b1 = new LinkedList<>();
		b1.add(new Node((char)64, null, 0.0000516));
		b1.add(new Node((char)68, null, 0.000115));
		b1.add(new Node((char)1028, null, 6.017653504386544e-05));
		b1.add(new Node((char)1092, null, 0.00011238869046792388));
		b1.add(new Node((char)16384, null, 5.447553121484816e-05));
		lp.put((char) 0b1111, b1);
		
		SortedMap<Integer, List<Character>> a = attack(c, lp);
		for(Entry<Integer, List<Character>> e : a.entrySet()) {
			System.out.println("stat: " + e.getKey());
			for(char key1 : e.getValue()) if(key1 == key[0]) System.out.println(Integer.toHexString(key1));
		}
	}
	
	public static SortedMap<Integer, List<Character>> attack(char[] ct, Map<Character, List<Node>> highLP) {
		SortedMap<Integer, List<Character>> key_candidates_stat = new TreeMap<>(Comparator.reverseOrder());
		for (char key_candidate = 0x1; key_candidate < Character.MAX_VALUE; key_candidate++) {
			int stat = 0;
			for (Entry<Character, List<Node>> e : highLP.entrySet()) {
				char alpha = e.getKey();
				for (Node beta_node : e.getValue()) {
					char beta = beta_node.value;
					for (char x = 0; x < ct.length; x++) {
						char x1 = Heys.round(x, key_candidate);
						char y = ct[x];
						assert(y == Heys.encrypt(x, key));
						if (scalar_mult(alpha, x1) ^ scalar_mult(y, beta))
							stat--;
						else
							stat++;
					}
				}
			}
			System.out.println(hex(key_candidate) + " " + stat);
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
	
	private static String hex(int stat) {
		return Integer.toHexString(stat);
	}

	public static Map<Character, List<Node>> getNeededLP(){
		Map<Character, List<Node>> all = new HashMap<Character, List<Node>>();
		int total_lp = 0;
		for (int j = 0; j < 4; j++) {
			System.out.println("iter " + j);
			for (int i = 1 << j * 4; i <= 0b1111 << j * 4; i += (1 << j * 4)) {
				List<Node> res = branch_and_bound((char) i);
				all.put((char)i, res);
				total_lp += res.size();
			}
		}
		//assert total_lp >= needed;
		System.out.println("total LP count: " + total_lp);
		for(Entry<Character, List<Node>> e : all.entrySet()) {
			System.out.println("alpha: " + Integer.toHexString(e.getKey()));
			for(Node n : e.getValue()) {
				System.out.println(Integer.toHexString(n.value) + "\t" + n.input_prob);
			}
		}
		return all;
	}
	
	public static List<Node> branch_and_bound(char input){
		double minprob = 0.11;
		double minprob_last = 0.00008;
		ArrayList<List<Node>> content;
		
		try {
		if(lpt == null)lpt = new SerializableLPTable();

		int numIter = 5;
		content = new ArrayList<>();
		
		//root
		List<Node> root = new LinkedList<Node>();
		root.add(new Node(input, null, 1.0));
		content.add(root);
		
		for (int i = 0; i < numIter; i++) {
			Node[] interim = new Node[Character.MAX_VALUE];
			for (Node n : content.get(i)) {
				//System.out.println("iter " + i);
				for (char c = 0; c < Character.MAX_VALUE; c++) {
					// double p = BranchAndBound.roundDiffProb(n.value, c, decrease_multiplier);
					Double p0 = lpt.get(n.value).get(c);
					double p = p0 == null ? 0 : p0;
					if (interim[c] == null) {
						interim[c] = new Node(c, n, p*n.input_prob); //p*n.input_prob
					} else {
						interim[c].input_prob += p*n.input_prob; //p*n.input_prob
						//interim[c].parents.add(n);
					}
				}
			}
			List<Node> OKProb = new LinkedList<Node>();
			for (Node n : interim) {
				if(i < numIter-1) {
				if (n != null && n.input_prob > Math.pow(minprob, i+1)) {
					OKProb.add(n);
				}} 
				else {
					if (n != null && n.input_prob > minprob_last) {
						OKProb.add(n);
					}
				}
			}
			System.out.println("OKProb " + i + " length: " + OKProb.size());
			if(i == numIter-1) {
				System.out.println("alpha " + Integer.toHexString(input));
				for(Node t : OKProb) {
				System.out.println(Integer.toHexString(t.value) + "\t" + t.input_prob);
			}}
			content.add(OKProb);
			// идея - хранить пары дифф с ненулевой вер
		}
		if(content.size() != numIter+1) System.out.println("content size == " + content.size() +  " != " +  numIter+1);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return content.get(content.size()-1);
	}
}
