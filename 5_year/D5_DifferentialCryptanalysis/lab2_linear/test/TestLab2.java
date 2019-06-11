package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.junit.Ignore;
import org.junit.Test;

import branch_and_bound.Node;
import main.Heys;
import main.LinearPotential;
import main.M2;

public class TestLab2 {
	@Test @Ignore
	public void testBandB() throws ClassNotFoundException, IOException {
		int needed = 700;
		Map<Character, List<Node>> all = new HashMap<Character, List<Node>>();
		int total_lp = 0;
		start:
			for (int j = 0; j < 4; j++) {
				System.out.println("iter " + j);
				for (int i = 1 << j * 4; i <= 0b1111 << j * 4; i += (1 << j * 4)) {
					List<Node> res = LinearPotential.branch_and_bound((char) i);
					all.put((char)i, res);
					total_lp += res.size();
					if(total_lp >= needed) break start; 
				}
			}
		System.out.println("total LP count: " + total_lp);
		for(Entry<Character, List<Node>> e : all.entrySet()) {
			System.out.println("alpha: " + Integer.toHexString(e.getKey()));
			for(Node n : e.getValue()) {
				System.out.println(Integer.toHexString(n.value) + "\t" + n.input_prob);
			}
		}
	}
	@Test @Ignore
	public void testScalarMult() {
		char a = 0b10101101;
		for(char b = 0; b < 1<<4; b++) 
			System.out.println(LinearPotential.scalar_mult(a, b) ? 1 : 0);
	}
	
	@Test @Ignore
	public void testRoundLP() {
		char alpha = 0xF;
		for(char i = 1; i < 1<<4; i++) {
			System.out.print(LinearPotential.roundLP(alpha, i) + "\t");
		}
	}
	
	
	@Test @Ignore
	public void testS() {
		char c = 0xffff;
		System.out.println(Integer.toHexString(Heys.S(c)));
	}
	
	@Test //@Ignore
	public void testAttack() {
		int ct_num = 100;
		SortedMap<Integer, List<Character>> a = M2.attack(ct_num);
		for(Entry<Integer, List<Character>> e : a.entrySet()) {
			System.out.println("stat: " + e.getKey());
			for(char key : e.getValue()) if(key == 0xf53b) System.out.println(Integer.toHexString(key));
		}
	}
	
	@Test @Ignore
	public void testPermutation() {
		char a = 0b0000_0000_1000_0000;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(Heys.permutation(a)));
	}
	
	@Test @Ignore
	public void testLPDecreasesEachRound() {
		char x = (char)(Math.random()*Character.MAX_VALUE);
		char key = (char)(Math.random()*Character.MAX_VALUE);
		char y1 = Heys.round(x, key);
		char y2 = Heys.round(y1, key);
		
		char alpha = (char)(Math.random()*Character.MAX_VALUE);
		char beta = (char)(Math.random()*Character.MAX_VALUE);
		
		System.out.println(Integer.toHexString(alpha) + "\t" + Integer.toHexString(beta));
		
		double lp = LinearPotential.roundLP(alpha, beta);
	}
	
	@Test @Ignore
	public void testSblockLP() {
		double[][] sblock_lp = LinearPotential.precalc_sblock_lp;
		for(int i = 0; i < sblock_lp.length; i++) {
			for(int j = 0; j < sblock_lp[0].length; j++) {
				System.out.print((sblock_lp[i][j]) + "\t");
			}
			System.out.println();
		}
	}
	
	@Test @Ignore
	public void testMassiveRoundLPs() {
		char[] betas = new char[(1<<4) - 1];
		for(char i = 0; i < betas.length; i++) betas[i] = (char) (i+1);
		double[] res = LinearPotential.massiveRoundLP((char)0b1111, betas);
		for(int i = 0; i < res.length; i++) System.out.println(res[i]);
	}
	
	@Test @Ignore
	public void testShifts() {
		for (int j = 0; j < 4; j++) {
			System.out.println("iter " + j);
			for (int i = 1 << j * 4; i <= 0b1111 << j * 4; i += (1 << j * 4)) {
				System.out.println(Integer.toBinaryString(i));
			}
		}
	}
	

}
