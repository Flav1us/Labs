package main;
//input diff: 1110000000000000,  max_prob: 1000000010001000	3.662947680437373E-4
//result: ba03
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import branch_and_bound.BranchAndBound;
import branch_and_bound.Node;
import branch_and_bound.PrecalculationTable;

public class Main {

	static String workdir = "C:\\Users\\ASUS\\Desktop\\D5_CP2";
	public Main() {
		// TODO Auto-generated construcjjjjjjjjjjnnnnnnnnnnnnnnnnnnnnnnnnnmtor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {
		long t0 = System.currentTimeMillis();
		
		//char[] key = {0x3864, 0xffff, 0x1234, 0x11ff, 0xaabb, 0x4321, 0x2345};
		//System.out.println(Integer.toHexString((int) Heys.encrypt((char)0x0000, key)));
		//calculateDifferentials();
		//String workdir = "C:\\Users\\ASUS\\Desktop\\D5_CP1";
		Attack.generateBasicFiles(workdir + "\\m.bin", workdir + "\\m_different.bin", (char)0b1110000000000000, 1);
		
		Attack.attack(); //ba03
		
		//PrecalculationTable.init();
		
		System.out.println("time taken: " + (System.currentTimeMillis() - t0) + " ms");
	}

	private static void calculateDifferentials() {
		for (int j = 0; j < 4; j++) {
			System.out.println("iter " + j);
			for (int i = 1 << j * 4; i <= 0b1111 << j * 4; i += (1 << j * 4)) {
				System.out.println("input: " + Integer.toBinaryString((int) i));
				try {
					PrecalculationTable.init((char) i);
					// List<Node> l =
					// PrecalculationTable.content.get(PrecalculationTable.content.size() - 1);
					// for (Node n : l)
					// System.out.println(Integer.toString((int) n.value, 2) + "\t" + n.input_prob);

					List<Node> last = PrecalculationTable.content.get(PrecalculationTable.content.size() - 1);
					PrecalculationTable.sortNodeList(last);
					if (last.size() > 0) {
						if (((last.get(0).value >> 8) & 0xf) > 0) {
							System.out.println("\nmax_prob: " + Integer.toString((int) last.get(0).value, 16) + "\t"
									+ last.get(0).input_prob);
						}
					} else
						System.out.println("no candidates");
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	public static void intersection() throws IOException {
	 
	}
	
	

	/*private static void expectedNeededTexts() {
		double res = 1.0;
		for(Map.Entry<Character, Diff> e : BranchAndBound.precalc.entrySet()) {
			res *= e.getValue().probability;
		}
		System.out.println(1.0/res);
	}*/


}
