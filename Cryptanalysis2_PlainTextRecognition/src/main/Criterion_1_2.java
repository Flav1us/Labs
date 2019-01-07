package main;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class Criterion_1_2 {
	public static boolean isPlainText(File src, Map<String, Integer> bigram_frequencies, Map<String, Double> critical_frequencies) {
		boolean isPlainText = true;
		long sum = bigram_frequencies.values().stream().mapToInt(i -> i.intValue()).sum();
		Optional<Map.Entry<String, Integer>> t = bigram_frequencies.entrySet().stream()
			.filter(i -> critical_frequencies.containsKey(i.getKey()))
			.filter(i -> critical_frequencies.get(i.getKey()) < (double)i.getValue()/sum)
			.findFirst();
		if(t.isPresent()) {
			isPlainText = false;
		}
		return isPlainText;
	}
}
