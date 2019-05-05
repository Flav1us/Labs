package branch_and_bound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SerializableDiffTable implements Serializable {
	
	/**
	 * lazy serializable precalculation table
	 * at the very beginning - empty
	 * when required to return a value - calculates it, saves and returns
	 * next time no need to calculate again
	 * 
	 * bound to a variant, this one is for variant 8
	 */

	private static final long serialVersionUID = 123456782010L;
	private static final String filepath = "C:\\Users\\ASUS\\Desktop\\D5_CP1\\serializable_diff_table.bin";
	public List<Map<Character,Double>> probabilities;
	
	public SerializableDiffTable() throws ClassNotFoundException, IOException {
		this.probabilities = this.load();
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<Character,Double>> load() throws ClassNotFoundException, IOException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));) {
			System.out.println("loading saved list");
			return (List<Map<Character, Double>>) ois.readObject();			
		} catch (FileNotFoundException e) {
			System.out.println("file not found, creating new table");
			List<Map<Character, Double>> lln = new ArrayList<Map<Character, Double>>();
			for(char c = 0; c < Character.MAX_VALUE; c++) {
				lln.add(null); //null for differentials that were never calculated before; empty list for all 0 probabilities.
			}
			assert lln.size() == Character.MAX_VALUE;
			return lln;
		}
	}

	public Map<Character, Double> get(char inp_diff) throws FileNotFoundException, IOException {
		if(probabilities.get(inp_diff) == null) {
			probabilities.set(inp_diff, new TreeMap<>());
			for(char c = 0; c < Character.MAX_VALUE; c++) {
				 double p = BranchAndBound.roundDiffProb(inp_diff, c);
				 if(p > 0) {
					 probabilities.get(inp_diff).put(c, p);
				 }
			}
			this.save();
		}
		return probabilities.get(inp_diff);
	}
	
	public void save() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
		oos.writeObject(probabilities);
		oos.close();
		System.out.println("table saved");
	}
	


}
