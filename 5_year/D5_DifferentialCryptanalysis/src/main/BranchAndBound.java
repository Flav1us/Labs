package main;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class BranchAndBound {
	
	static final char fixed_input_x = (char) 123456; 
	static Map<Character, MyEntry> precalc = new ConcurrentHashMap<>();
	
	static {
		precalc.put((char)1, new MyEntry((char)10001, 0.25));
		precalc.put((char)10001, new MyEntry((char)110011, 0.0625));
	}
	
	
	private static class MyEntry {
		public MyEntry() {};
		public MyEntry(char b, double p) {this.beta = b; this.prob = p;}
		public char beta;
		public double prob;
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
	
	public static void singleLine(){
		double p_min = 0.1;
		char alpha0 = 0b0000_0000_0000_0001;
		char alpha1 = 0;
		for(char beta = Character.MIN_VALUE; beta < Character.MAX_VALUE; beta++) {
			//System.out.println(Integer.valueOf(beta));
			double p = roundDiffProb(alpha0, beta);
			if(p >= p_min) {
				System.out.println(Integer.toBinaryString(beta) + "\t" + p);
				alpha1 = beta;
				break;
			}
		}
		
		maxRoundDP(alpha1);
	}
	
	public static double maxRoundDP(char alpha) {
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
		return p_max;
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
