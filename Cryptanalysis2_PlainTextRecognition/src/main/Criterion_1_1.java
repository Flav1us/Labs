package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Criterion_1_1 {
	public static boolean isPlainText(File src, int k_p) throws IOException {
		File dst = new File("resources\\test_formated.txt");
		Format.format(src, dst, Main.alphabet, Main.replace);
		boolean isPlainText = true;
		Map<String, Integer> plt_bifreq = Frequencies.getBigramQuantities(Main.formated, Main.alphabet, true);
		List<String> A_prh = Frequencies.getProhibitedBigrams(plt_bifreq); //Main.src
		Map<String, Integer> bifreq = Frequencies.getBigramQuantities(dst, Main.alphabet, true); //src
		long prohibitedBigramClasses = bifreq.entrySet().stream()
			.filter(x -> x.getValue() != 0)
			.filter(x -> A_prh.contains(x.getKey()))
			.count();
		if(prohibitedBigramClasses >= k_p) {
			//System.out.println("prohibitedBigramClasses: " + prohibitedBigramClasses);
			isPlainText = false;
		}
		return isPlainText;
	}
}