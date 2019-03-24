package main;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class BranchAndBound {
	
	static final char fixed_input_x = (char) 123456; 
	
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
	
	public static double roundDiffProb(char inp_diff, char out_diff) {
		char x = fixed_input_x;
		//average by keys:
		char diff_count = 0;
		int decrease_multiplier = 63;
		for(char key = Character.MIN_VALUE; key < Character.MAX_VALUE; key+=63) {
			//System.out.println((int)key);
			if(Heys.round((char)(x ^ inp_diff), key) == (out_diff ^ (Heys.round(x, key)))) {
				diff_count++;
				//System.out.println("found");
			}
		}
		double res = (double)diff_count*decrease_multiplier /(double)(Character.MAX_VALUE - Character.MIN_VALUE);
		//System.out.println(res);
		return res ;
	}

}
