package branch_and_bound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrecalculationTable {
	private static int decrease_multiplier = 1; //for speed during testing
	
	public static List<List<Node>> content = new ArrayList<>();
	
	private static final double minprob = 0.12;
	
	/*
	 *                          *
	 * 			0	0.4	0.1	0z	0	0	0.3	0	0.2	0
	 * 			*	*	*	*	*	*	*	*	*	*
	 * 				*	*				*		*
	 */
	 
	public static void init() throws ClassNotFoundException, IOException {
		
		SerializableDiffTable dt = new SerializableDiffTable();
		
		//root
		List<Node> root = new LinkedList<Node>();
		root.add(new Node((char)0b1, null, 1));
		content.add(root);
		
		//first level
		/*Node[] interim = new Node[Character.MAX_VALUE];
		for(char c = 0; c < interim.length; c++) {
			if((c+1) % (interim.length/10) == 0) System.out.print(".");
			Double prob = dt.get(root.get(0).value).get(c);
			interim[c] = new Node(c, root.get(0), prob == null ? 0 : prob);//new Node(c, root.get(0), BranchAndBound.roundDiffProb(root.get(0).value, c, decrease_multiplier));
		} 
		System.out.println();//"\ninit done");
		
		List<Node> notZeroProb = new LinkedList<Node>();
		for(char c = 0; c < interim.length; c++) {
			if(interim[c].input_prob > minprob) {
				notZeroProb.add(interim[c]);
				//System.out.println(Integer.toString((int)c, 2) + "\t" + interim[c].input_prob);
			}
		}
		content.add(notZeroProb);
		System.out.println("notZeroProb1 length: " + notZeroProb.size());*/
		
		for (int i = 0; i < 4; i++) {
			Node[] interim = new Node[Character.MAX_VALUE];
			for (Node n : content.get(i)) {
				System.out.println("iter " + i);
				for (char c = 0; c < Character.MAX_VALUE; c++) {
					// double p = BranchAndBound.roundDiffProb(n.value, c, decrease_multiplier);
					Double p0 = dt.get(n.value).get(c);
					double p = p0 == null ? 0 : p0;
					if (interim[c] == null) {
						interim[c] = new Node(c, n, p);
					} else {
						interim[c].input_prob += p;
						interim[c].parents.add(n);
					}
				}
			}
			List<Node> notZeroProb = new LinkedList<Node>();
			for (Node n : interim) {
				if (n.input_prob > minprob) {
					notZeroProb.add(n); }}
			System.out.println("notZeroProb " + i + " length: " + notZeroProb.size());
			content.add(notZeroProb);
			// идея - хранить пары дифф с ненулевой вер
		}
		
		dt.save();
	}
}
