package main;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Criterion_1_2 {
	public static boolean isPlainText(File src, Map<String, Double> critical_frequencies) throws IOException {
		File dst = new File("resources\\test_formated.txt");
		Format.format(src, dst, Main.alphabet, Main.replace);
		boolean isPlainText = true;
		Map<String, Double> bigram_frequencies = Frequencies.getBigramFrequencies(dst, Main.alphabet, true);
		Optional<Map.Entry<String, Double>> t = bigram_frequencies.entrySet().stream()
			.filter(i -> critical_frequencies.containsKey(i.getKey()))
			.filter(i -> critical_frequencies.get(i.getKey()) < i.getValue())
			.findFirst();
		if(t.isPresent()) {
			isPlainText = false;
		}
		return isPlainText;
	}
}
