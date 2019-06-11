package branch_and_bound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PrecalculationTable {
	//private static int decrease_multiplier = 1; //for speed during testing
	
	public static List<List<Node>> content = new ArrayList<>();
	
	private static final double minprob = 0.15;
	
	/*
	 *                          *
	 * 			0	0.4	0.1	0z	0	0	0.3	0	0.2	0
	 * 			*	*	*	*	*	*	*	*	*	*
	 * 				*	*				*		*
	 */
	
	private static SerializableDiffTable dt = null;
	 
	public static void init() throws ClassNotFoundException, IOException {
		char input = (char)0b1111;
		init(input);
	}
	
	public static void init(char input) throws ClassNotFoundException, IOException {
		int numIter = 5;
		content = new ArrayList<>();
		if (dt == null) dt = new SerializableDiffTable();
		
		//root
		List<Node> root = new LinkedList<Node>();
		root.add(new Node(input, null, 1));
		//done: 0b1, 0b1111
		content.add(root);
		
		for (int i = 0; i < numIter; i++) {
			Node[] interim = new Node[Character.MAX_VALUE];
			for (Node n : content.get(i)) {
				//System.out.println("iter " + i);
				for (char c = 0; c < Character.MAX_VALUE; c++) {
					// double p = BranchAndBound.roundDiffProb(n.value, c, decrease_multiplier);
					Double p0 = dt.get(n.value).get(c);
					double p = p0 == null ? 0 : p0;
					if (interim[c] == null) {
						interim[c] = new Node(c, n, p*n.input_prob); //p*n.input_prob
					} else {
						interim[c].input_prob += p*n.input_prob; //p*n.input_prob
						interim[c].parents.add(n);
					}
				}
			}
			List<Node> notZeroProb = new LinkedList<Node>();
			for (Node n : interim) {
				if (n != null && n.input_prob > Math.pow(minprob, i+1)) {
					notZeroProb.add(n); }}
			System.out.println("notZeroProb " + i + " length: " + notZeroProb.size());
			content.add(notZeroProb);
			for(Node t : content.get(i+1)) {
				System.out.println(Integer.toHexString(t.value) + "\t" + t.input_prob);
			}
			// идея - хранить пары дифф с ненулевой вер
		}
		if(content.size() != numIter+1) System.out.println("content size == " + content.size() +  " != " +  numIter+1);
	}
	
	public static char getMaxProbDiff() {
		List<Node> l = content.get(content.size()-1);
		double max_prob = 0;
		Character max_pr_diff = null;
		for(Node n : l) {
			if(n.input_prob > max_prob) {
				max_prob = n.input_prob;
				max_pr_diff = n.value;
			}
		}
		if(max_pr_diff == null) throw new IllegalStateException("no valid candidates for differential. increase minprob");
		return max_pr_diff;
	}
	
	public static void sortNodeList(List<Node> l) {
		Collections.sort(l, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return Double.compare(o2.input_prob, o1.input_prob);
			}
		});
	}
}
