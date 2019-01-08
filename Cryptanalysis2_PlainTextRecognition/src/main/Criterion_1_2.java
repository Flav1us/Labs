package main;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Criterion_1_2 {
	
	Map<String, Double> A_prh_frq;
	
	public Criterion_1_2(File src_learn) throws IOException {
		Map<String, Integer> bifreq = Frequencies.getBigramQuantities(src_learn, Main.alphabet, true);
		A_prh_frq = Frequencies.A_prh_frq(bifreq);
	}
	
	public boolean isPlainText(File src) throws IOException {
		boolean isPlainText = true;
		Map<String, Double> bigram_frequencies = Frequencies.getBigramFrequencies(src, Main.alphabet, true);
		Optional<Map.Entry<String, Double>> t = bigram_frequencies.entrySet().stream()
			.filter(i -> A_prh_frq.containsKey(i.getKey()))
			.filter(i -> A_prh_frq.get(i.getKey()) < i.getValue())
			.findFirst();
		if(t.isPresent()) {
			isPlainText = false;
		}
		return isPlainText;
	}
}
