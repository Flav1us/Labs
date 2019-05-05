package main;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import branch_and_bound.BranchAndBound;
import branch_and_bound.PrecalculationTable;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {
		long t0 = System.currentTimeMillis();
		
		//Util.fileGen("Q".getBytes(StandardCharsets.UTF_16LE), "m");
		//BranchAndBound.precalculation(0.001);
		//System.out.println(BranchAndBound.roundDiffProb('a', 'b'));
		
		//BranchAndBound.singleLine();
		
		//char[] key = {0x3864, 0xffff, 0x1234, 0x11ff, 0xaabb, 0x4321, 0x2345};
		//System.out.println(Integer.toHexString((int) Heys.encrypt((char)0xeeff, key)));
		
		//System.out.println(BranchAndBound.roundDiffProb((char)0b0000_0000_0000_0001, (char)10001) > 0.000001);
		
		PrecalculationTable.init();
		
		
		
		System.out.println("time taken: " + (System.currentTimeMillis() - t0) + " ms");
	}

	/*private static void expectedNeededTexts() {
		double res = 1.0;
		for(Map.Entry<Character, Diff> e : BranchAndBound.precalc.entrySet()) {
			res *= e.getValue().probability;
		}
		System.out.println(1.0/res);
	}*/


}
