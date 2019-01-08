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
	
	
	public static Map<String, Integer> getBigramQuantities(File src, char[] alphabet, boolean overlay) throws IOException {
		 Map<String, Integer> quantities = new HashMap<String, Integer>();
		 char c1, c2;
		 String bi;
		 for(int i=0; i<alphabet.length; i++) {
			 for(int j=0; j<alphabet.length; j++) {
				 //заполняем хэшмап всевозможными биграммами
				 quantities.put(Character.toString(alphabet[i]).concat(Character.toString(alphabet[j])), 0);
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
			 /*try {*/
			 if(overlay == true) quantities.put(bi, quantities.get(bi) + 1); //если без наложения, суём всё
			 
			 else if (ctr % 2 == 0) { //если "с", суём кажую вторую
				 quantities.put(bi, quantities.get(bi) + 1);
			 }
			 /*} catch (NullPointerException e) {
				 System.out.println("unknown bigram: \"" + bi + "\"");
			 }*/
			 ctr++;
			 c1 = c2;
		 }
		 br.close();
		 return quantities;
	}
	
	public static Map<String, Double> getBigramFrequencies(File src, char[] alphabet, boolean overlay) throws IOException {
		Map<String, Integer> biq = Frequencies.getBigramQuantities(src, alphabet, overlay);
		long sum = biq.values().stream().mapToInt(i -> i.intValue()).sum();
		Map<String, Double> bifr = new HashMap<>();
		for(Map.Entry<String, Integer> e : biq.entrySet()) {
			bifr.put(e.getKey(), (double)e.getValue()/sum);
		}
		return bifr;
	}
	
	public static List<String> getProhibitedBigrams(Map<String, Integer> bifreq) {
		List<String> A_prh = bifreq.entrySet().stream()
				.filter(bi_entry -> bi_entry.getValue() == 0)
				.map(x -> x.getKey())
				.collect(Collectors.toList());
		//A_prh.stream().forEach(System.out::println);
		return A_prh;
	}
	
	public static Map<String, Double> A_prh_frq(Map<String, Integer> bifreq) {
		List<String> A_prh = getProhibitedBigrams(bifreq);
		Map<String, Double> frq = new HashMap<>();
		long sum = bifreq.values().stream().mapToInt(i -> i.intValue()).sum();
		for(Map.Entry<String, Integer> e : bifreq.entrySet()) {
			if(A_prh.contains(e.getKey())) {
				frq.put(e.getKey(), (double)e.getValue()/sum);
			}
		}
		return frq;
	}
	
	public static double specificEntropy(Map<String, Double> frequencies) {
		int len = frequencies.keySet().stream().findFirst().get().length();
		for(String s : frequencies.keySet()) assert(s.length() == len);
		//frequencies.values().stream().filter(i -> !i.equals(0.0)).mapToDouble(i -> -i*(Math.log10(i)/Math.log10(2))).forEach(System.out::println);
		double specific_entropy =  frequencies.values().stream().filter(i -> i > 0.0000000001).mapToDouble(i -> -i*(Math.log10(i)/Math.log10(2))).sum() / len;
		return specific_entropy;
	}
	
}
