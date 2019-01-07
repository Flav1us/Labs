package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Criterion_1_3 {
	
	public static boolean isPlainText(File src, Map<String, Double> A_prh_frq) throws IOException {
		File dst = new File("resources\\test_formated.txt");
		Format.format(src, dst, Main.alphabet, Main.replace);
		Map<String, Double> bi_frq = Frequencies.getBigramFrequencies(dst, Main.alphabet, true);
		/*double Fp = 0;
		for(Map.Entry<String, Double> e : bi_frq.entrySet()) {
			if(A_prh_frq.containsKey(e.getKey())) {
				Fp += e.getValue();
			}
		}*/
		double Fp = bi_frq.entrySet().stream().filter(i -> A_prh_frq.containsKey(i.getKey())).mapToDouble(i -> i.getValue()).sum();
		double Kp = A_prh_frq.entrySet().stream().mapToDouble(i -> i.getValue()).sum();
		return Fp <= Kp;
	}
}
