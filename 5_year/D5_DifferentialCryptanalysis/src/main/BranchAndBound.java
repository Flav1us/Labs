package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class BranchAndBound {
	
	static final char fixed_input_x = (char) 123456; 
	static Map<Character, Diff> precalc = new ConcurrentHashMap<>();
	
	static {
		precalc.put((char)0b1, new Diff((char)0b10001, 0.25));
		precalc.put((char)0b10001, new Diff((char)0b110011, 0.0625));
		precalc.put((char)0b110011, new Diff((char)0b1100110000, 0.0625));
		precalc.put((char)0b1100110000, new Diff((char)0b11001100000, 0.0625));
		precalc.put((char)0b11001100000, new Diff((char)0b110000001100000, 0.0625));
		precalc.put((char)0b110000001100000, new Diff((char)0b1010000010100000, 0.0625));
	}
	
	
	/*private static class MyEntry {
		//public MyEntry() {};
		public MyEntry(char b, double p) {this.beta = b; this.prob = p;}
		public char beta;
		public double prob;
	}*/
	
	private static class Node {
		char value;
		double input_prob;//what if several parents? its good
		List<Node> parents = new ArrayList<>();
		
		//next round members with probabilities to get into them
		Map<Node, Double> children = new TreeMap<>();
		
		public Node(char value, Node parent) {
			this.value = value;
		}

		public void addChild(Node child, double prob) {
			children.put(child, prob);
		}
	}
	
	//ignore this func yet
	public static void precalculation(double p_min) {
		// 1/2^16 = 0,0000152587890625
		Map<Character, Map<Character, Double>> precalc = new ConcurrentHashMap<>();
		
		double p;
		for(char a = Character.MIN_VALUE; a <= Character.MAX_VALUE; a++) {
			System.out.println('.');
			long time = System.currentTimeMillis(); 
			for(char b = Character.MIN_VALUE; b < Character.MAX_VALUE; b++) {
				if((b+1)%(Character.MAX_VALUE/10) == 0) System.out.println(b);
				p = roundDiffProb(a, b);
				if(p >= p_min) {
					System.out.println("found > pmin");
					if(precalc.containsKey(a)) {
						precalc.get(a).put(b, p);
					} else {
						TreeMap<Character, Double> m = new TreeMap<>();
						m.put(b, p);
						precalc.put(a, m);
					}
				}
			}
			System.out.println("time: " + (System.currentTimeMillis() - time));
		}
		
	}
	
	public static void BandB(char start_diff, double minprob, int rounds) {
		//List<Map<Character, Map<Character, Double>>> diff = new ArrayList<Map<Character, Map<Character, Double>>>();
		Map<Character, Double>[] omega = new HashMap[7]; //
		//Node root = new Node(start_diff, null);
		omega[0].put(start_diff, 1.0);
		for(int i = 1; i <= 5; i++) {//5 rounds differentials
			for(Map.Entry<Character, Double> parent : omega[i-1].entrySet()) {
				for(char beta = Character.MIN_VALUE; beta < Character.MAX_VALUE; beta++) {
					double prob = roundDiffProb(parent.getKey(), beta);
					//if(prob >= minprob) {
						if(omega[i].containsKey(beta)) {
							omega[i].put(beta, omega[i].get(beta) + parent.getValue()*prob);
						}
						else {
							omega[i].put(beta, parent.getValue()*prob);
						}
					//}
				}
			}
			//here bound
			for(Map.Entry<Character, Double> child : omega[i].entrySet()) {
				if(child.getValue() < minprob) {
					omega[i].remove(child);
				}
			}
			
		}
		
	}
	
	public static void singleLine(){
		
		maxRoundDP((char)0b110000001100000);
	}
	
	public static Diff maxRoundDP(char alpha) {
		double p_max = 0.0;
		char appr_beta = 0;
		for(char beta = Character.MIN_VALUE; beta < Character.MAX_VALUE; beta++) {
			//System.out.println(Integer.valueOf(beta));
			double p = roundDiffProb(alpha, beta);
			if(p >= p_max) {
				//System.out.println(Integer.toBinaryString(beta) + "\t" + p);
				p_max = p;
				appr_beta = beta;
			}
		}
		System.out.println(Integer.toBinaryString(alpha) + " -> " + Integer.toBinaryString(appr_beta) + "\t" + p_max);
		return new Diff(appr_beta, p_max);
	}
	
	public static double roundDiffProb(char inp_diff, char out_diff) {
		char x = fixed_input_x;
		//average by keys:
		char diff_count = 0;
		int decrease_multiplier = 1;
		for(char key = Character.MIN_VALUE; key < Character.MAX_VALUE-decrease_multiplier-1; key+=decrease_multiplier) {
			//System.out.println((int)key);
			if(Heys.round((char)(x ^ inp_diff), key) == (out_diff ^ (Heys.round(x, key)))) {
				diff_count++;
				//System.out.println("found");
			}
		}
		double res = (double)diff_count*decrease_multiplier/(double)(Character.MAX_VALUE -decrease_multiplier-1 - Character.MIN_VALUE);
		//System.out.println(res);
		return res ;
	}

}
