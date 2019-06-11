package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import branch_and_bound.Node;

public class LinearPotential {

	//static char fixed_input_x = 0x1234;
	private static List<List<Node>> content;
	private static SerializableLPTable lpt;
	private static double minprob = 0.000075;
	private static double minprob_last = 0.00006;
	
	public static double[][] precalc_sblock_lp = new double[16][16];
	
	static {
		//long t0 = System.currentTimeMillis();
		//System.out.println("precalculation");
		for(int a = 0; a < precalc_sblock_lp.length; a++) {
			for(int b = 0; b < precalc_sblock_lp[0].length; b++) {
				precalc_sblock_lp[a][b] = 0.0;
				for(int x = 0; x < 16; x++) {
					precalc_sblock_lp[a][b] += scalar_mult((char)a, (char)x) ^ scalar_mult((char)b, (char)Heys.S[x]) ? -1 : 1;
				}
				precalc_sblock_lp[a][b] = Math.pow(precalc_sblock_lp[a][b]/16, 2);
				//System.out.println(a + " -> " + b + ":\t" + precalc_sblock_lp[a][b]);
			}
		}
		//System.out.println("precalc complete in " + (System.currentTimeMillis() - t0) + " ms");
	}
	 
	
	public static List<Node> branch_and_bound(char input){
		try {
		int numIter = 5;
		content = new ArrayList<>();
		if (lpt == null) lpt = new SerializableLPTable();
		
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
			lpt.save();
			List<Node> OKProb = new LinkedList<Node>();
			for (Node n : interim) {
				if(i < numIter-1) {
				if (n != null && n.input_prob > minprob) {//Math.pow(minprob, i+1)) {
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
	
	public static Map<Character, List<Node>> getNeededLP(int needed){
		Map<Character, List<Node>> all = new HashMap<Character, List<Node>>();
		int total_lp = 0;
		start:
		for (int j = 0; j < 4; j++) {
			System.out.println("iter " + j);
			for (int i = 1 << (j * 4); i <= 0b1111 << (j * 4); i += (1 << (j * 4))) {
				List<Node> res = LinearPotential.branch_and_bound((char) i);
				all.put((char)i, res);
				total_lp += res.size();
				if(total_lp >= needed) break start; 
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

	public static double roundLP(char alpha, char beta) {
		beta = Heys.permutation(beta);
		double lp = 1.0;
		for(int i = 0; i < 4; i++) {
			lp *= precalc_sblock_lp[(alpha >> (4*i)) & 0xF][(beta >> (4*i)) & 0xF];
		}
		return lp;
	}
	
	public static double[] massiveRoundLP(char alpha, char[] betas) {
		//TODO optimize
		double[] lps = new double[betas.length];
		Arrays.fill(lps, 1.0);
			for (int i = 0; i < lps.length; i++) {
				for (int j = 0; j < 4; j++) {
				//System.out.println(i + " " + j + " " + precalc_sblock_lp[(alpha >> (4 * j)) & 0xF][(Heys.permutation(betas[i]) >> (4 * j)) & 0xF]);
				System.out.println(Integer.toString((alpha >> (4 * j)) & 0xF) + "\t" + Integer.toString((betas[i] >> (4 * j)) & 0xF));
				lps[i] *= precalc_sblock_lp[(alpha >> (4 * j)) & 0xF][(betas[i] >> (4 * j)) & 0xF];
			}
		}
		return lps;
	}
	
	
	public static boolean scalar_mult(char a, char b) {
		return Integer.bitCount(a&b) % 2 == 1; //a[0]&b[0] xor a[1]&b[1] xor ...
	}
}
