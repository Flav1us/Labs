package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Frequencies {
	
	public static Map<Character, Integer> getSingleCharFrequencies(File src, char[] alphabet) throws IOException {
		Map<Character, Integer> frequencies = new HashMap<Character, Integer>();
		char c;
		 for(int i=0; i<alphabet.length; i++) {
			 frequencies.put(alphabet[i], 0);
		 }
		 BufferedReader br = new BufferedReader (new FileReader(src));
		 //c1=(char)br.read();
		 //frequencies.put(c1, frequencies.get(c1)+1);
		 int t;
		 while((t=br.read()) != -1) {
			 c = (char)t;
			 //System.out.println(c);
			 frequencies.put(c, frequencies.get(c)+1);
		 }
		 br.close();
		return frequencies;
	}
	
	
	public static Map<String, Integer> getBigramFrequencies(File src, char[] alphabet, boolean overlay) throws IOException {
		 Map<String, Integer> frequencies = new HashMap<String, Integer>();
		 char c1, c2;
		 String bi;
		 for(int i=0; i<alphabet.length; i++) {
			 for(int j=0; j<alphabet.length; j++) {
				 //заполняем хэшмап всевозможными биграммами
				 frequencies.put(Character.toString(alphabet[i]).concat(Character.toString(alphabet[j])), 0);
			 }
		 }
		 BufferedReader br = new BufferedReader (new FileReader(src));
		 int t;
		 int ctr = 0;
		 c1=(char)br.read();
		 while((t=br.read()) != -1) {
			 c2 = (char)t;
			 bi=Character.toString(c1).concat(Character.toString(c2));
			 //System.out.println(bi);
			 if(overlay == true) frequencies.put(bi, frequencies.get(bi) + 1); //если без наложения, суём всё
			 else if (ctr % 2 == 0) { //если "с", суём кажую вторую
				 frequencies.put(bi, frequencies.get(bi) + 1);
			 }
			 ctr++;
			 c1 = c2;
		 }
		 br.close();
		 return frequencies;
	}
	
	public static List<String> getProhibitedBigrams(Map<String, Integer> bifreq) {
		List<String> A_prh = bifreq.entrySet().stream()
				.filter(bi_entry -> bi_entry.getValue() == 0)
				.map(x -> x.getKey())
				.collect(Collectors.toList());
		return A_prh;
	}
	
}
