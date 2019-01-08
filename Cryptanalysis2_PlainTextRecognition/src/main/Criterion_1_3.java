package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Criterion_1_3 {
	 Map<String, Double> A_prh_frq;
	
	public Criterion_1_3(File src_learn) throws IOException {
		Map<String, Integer> plt_bifreq = Frequencies.getBigramQuantities(src_learn, Main.alphabet, true);
		//List<String> A_prh = Frequencies.getProhibitedBigrams(plt_bifreq);
		A_prh_frq = Frequencies.A_prh_frq(plt_bifreq);
	}
	
	public boolean isPlainText(File src) throws IOException {
		Map<String, Double> bi_frq = Frequencies.getBigramFrequencies(src, Main.alphabet, true);
		/*double Fp = 0;
		for(Map.Entry<String, Double> e : bi_frq.entrySet()) {
			if(A_prh_frq.containsKey(e.getKey())) {
				Fp += e.getValue();
			}
		}*/
		double Fp = bi_frq.entrySet().stream().filter(i -> A_prh_frq.containsKey(i.getKey())).mapToDouble(i -> i.getValue()).sum();
		double Kp = A_prh_frq.entrySet().stream().mapToDouble(i -> i.getValue()).sum();
		//System.out.println(Fp + " ?<=? " + Kp);
		return Fp <= Kp;
	}
}
