package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Criterion_1_1 {
	private File dst;
	Map<String, Integer> plt_bifreq;
	List<String> A_prh; //Main.src
	
	public Criterion_1_1(File src_learn) throws IOException {
		/*dst = new File("resources\\test_formated.txt");
		Format.format(src_learn, dst, Main.alphabet, Main.replace);*/
		plt_bifreq = Frequencies.getBigramQuantities(src_learn, Main.alphabet, true);
		A_prh = Frequencies.getProhibitedBigrams(plt_bifreq);
	}

	public boolean isPlainText(File src, int k_p) throws IOException {
		//dst = new File("resources\\test_formated.txt");
		//Format.format(src, dst, Main.alphabet, Main.replace);
		boolean isPlainText = true;
		Map<String, Integer> bifreq = Frequencies.getBigramQuantities(src, Main.alphabet, true); //src
		long prohibitedBigramClasses = bifreq.entrySet().stream()
			.filter(x -> x.getValue() != 0)
			.filter(x -> A_prh.contains(x.getKey()))
			.count();
		if(prohibitedBigramClasses < 3) {
			bifreq.entrySet().stream()
			.filter(x -> x.getValue() != 0)
			.filter(x -> A_prh.contains(x.getKey()))
			.forEach(System.out::println);
		}
		System.out.println("prohibitedBigramClasses: " + prohibitedBigramClasses);
		if(prohibitedBigramClasses >= k_p) {
			isPlainText = false;
		}
		return isPlainText;
	}
}
