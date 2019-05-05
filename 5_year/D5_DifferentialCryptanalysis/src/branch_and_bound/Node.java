package branch_and_bound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Node {
	char value;
	double input_prob;//what if several parents? its good
	List<Node> parents = new ArrayList<>();
	
	//next round members with probabilities to get into them
	//Map<Node, Double> children = new TreeMap<>();
	
	public Node() {}
	
	public Node(char value, Node parent, double input_prob) {
		this.value = value;
		this.parents.add(parent);
		this.input_prob = input_prob;
	}

	/*public void addChild(Node child, double prob) {
		children.put(child, prob);
	}*/
	
}