package main;

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

public class SerializableLPTable implements Serializable {

	private static final long serialVersionUID = 424124342010L;
	private List<Map<Character,Double>> probabilities = null;
	//list with size character.maxvalue, 
	private static final String filepath = "C:\\Users\\ASUS\\Desktop\\D5_CP2\\serializable_LP_table.bin";
	
	public SerializableLPTable() throws ClassNotFoundException, IOException {
		probabilities = load();
	}


	public Map<Character, Double> get(char inp_corr) throws IOException {
		if(probabilities.get(inp_corr) == null) {
			probabilities.set(inp_corr, new TreeMap<>());
			for(char c = 0; c < Character.MAX_VALUE; c++) {
				//System.out.println(".");
				 double p = LinearPotential.roundLP(inp_corr, c);
				 if(p > 0) {
					 probabilities.get(inp_corr).put(c, p);
				 }
			}
			//this.save();
		}
		return probabilities.get(inp_corr);
	}

	public void save() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
		oos.writeObject(probabilities);
		oos.close();
		System.out.println("table saved");
	}
	
	@SuppressWarnings("unchecked")
	private static List<Map<Character, Double>> load() throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));) {
			System.out.println("loading saved list");
			return (List<Map<Character, Double>>) ois.readObject();			
		} catch (FileNotFoundException e) {
			System.out.println("file not found, creating new table");
			List<Map<Character, Double>> lln = new ArrayList<Map<Character, Double>>();
			for(char c = 0; c < Character.MAX_VALUE; c++) {
				lln.add(null); //null for correlations that were never calculated before; empty list for all 0 probabilities.
			}
			assert lln.size() == Character.MAX_VALUE;
			return lln;
		}
	}
	
}
