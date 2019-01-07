package main;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Criterion_3_0 {
	public static double kH = 0.1;
	
	public static boolean isPlainText(File src, Map<String, Double> basic_frq) throws IOException {
		File dst = new File("resources\\test_formated.txt");
		Format.format(src, dst, Main.alphabet, Main.replace);
		Map<String, Double> frq = Frequencies.getBigramFrequencies(dst, Main.alphabet, true);
		double specific_entropy = Frequencies.specificEntropy(frq);
		double basic_specific_entropy = Frequencies.specificEntropy(basic_frq);
		return (basic_specific_entropy - kH < specific_entropy) && (specific_entropy < basic_specific_entropy + kH);
	}
}
