package main;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Criterion_3_0 {
	public static double kH = 0.15;
	double basic_specific_entropy;
	
	public Criterion_3_0(File src_learn) throws IOException {
		Map<String, Double> basic_frq = Frequencies.getBigramFrequencies(src_learn, Main.alphabet, true);
		basic_specific_entropy = Frequencies.specificEntropy(basic_frq);
	}
	
	public boolean isPlainText(File src) throws IOException {
		Map<String, Double> frq = Frequencies.getBigramFrequencies(src, Main.alphabet, true);
		double specific_entropy = Frequencies.specificEntropy(frq);
		System.out.println(specific_entropy + " " + basic_specific_entropy);
		return (basic_specific_entropy - kH < specific_entropy) && (specific_entropy < basic_specific_entropy + kH);
	}
}
