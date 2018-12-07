package lab1;

import java.util.Arrays;

import org.apache.commons.math3.distribution.WeibullDistribution;

import util.SampleGenerator;

public class L1Task3 {
	public static void task3() {
		firstMethod();
		System.out.println("=================");
		secondMethod();
		
		//ksi = SampleGenerator.weibull(1, 3, (int)n_ksi(ksi, alph));
		///double MG = Arrays.stream(ksi).map(ksi_i -> (1-G(ksi_i))).sum() / ksi.length;
		///System.out.println(MF + "\t" + MG);
	}
	
	public static void firstMethod() {
		double[] eta = SampleGenerator.weibull(1, 3, 100000);
			for(double alph = 0.1; alph >= 0.001; alph/=10) {
				double alph1= alph;
				int n_ = (int) n_eta(eta, alph);
				System.out.println("n* = " + n_);
				//ksi = SampleGenerator.weibull(alph, 2, (int)n_ksi(ksi, alph));
				//System.out.println((int)n_ksi(ksi, alph));
				//double MF = Arrays.stream(eta).map(eta_i -> F(eta_i, alph1)).sum() / eta.length; 
				double MF = 0;
				for(int i = 0; i<n_; i++) {
					MF += F(eta[i], alph1);
				}
				//double MG= Arrays.stream(ksi).map(ksi_i -> 1-G(ksi_i)).sum() / ksi.length; 
				System.out.println(MF/n_/* + "\t" + MG*/);
			
		}
	}
	
	public static void secondMethod() {
		int max_size = 3000000;
			for(double alph = 0.1; alph >= 0.001; alph/=10) {
				double[] ksi = SampleGenerator.weibull(alph, 2, max_size);
				double alph1= alph;
				//double MG= Arrays.stream(ksi).map(ksi_i -> 1-G(ksi_i)).sum() / ksi.length; 
				double MG = 0;
				int n_ = (int) n_ksi(ksi, alph);
				System.out.println("n* = " + n_);
				if(n_ > max_size) System.out.println("n_ =" + n_ + " > " + max_size);
				for(int i = 0; i<n_; i++) {
					MG += 1-G(ksi[i]);
				}
				System.out.println(/*MF + "\t" + */MG/n_);
			}
		
	}

	public static double F(double u, double alph) {
		return 1 - Math.pow(Math.E, -Math.pow(alph*u, 2));
	}
	public static double G(double u) {
		return 1 - Math.pow(Math.E, -Math.pow(u, 3));
	}
	
	/*public static double integral(double ksi[], double eta[]) {
		int x_max = Math.min(ksi.length, eta.length);
		int ctr = 0;
		for(int i = 0; i < x_max; i++) {
			if(ksi[i] < eta[i]) ctr ++;
		}
		//System.out.println(ctr + "\t" + x_max);
		return new Double(ctr)/x_max;
	}*/
	
	public static double quantil(double alpha, int power) {
		return new WeibullDistribution(power, 1.0/alpha).inverseCumulativeProbability(1 - 0.05);
	}
	
	public static long n_eta(double[] eta, double alpha) {
		int n = eta.length;
		double S1 = Arrays.stream(eta).map(i -> F(i, alpha)).sum();
		double S2 = Arrays.stream(eta).map(x -> F(x, alpha)*F(x, alpha)).sum();
		double an = S1/n;
		//double sign = Arrays.stream(eta).map(x -> (x-an)*(x-an)).sum() / (n-1);
		double sign = (S2 - S1*S1/n)/(n-1);
		return Math.round(Math.pow(quantil(1, 3), 2)*sign/(an*an*0.0001))+1;
	}
	
	private static long n_ksi(double[] ksi, double alph) {
		int n = ksi.length;
		double S1 = Arrays.stream(ksi).map(i -> 1-G(i)).sum();
		double S2 = Arrays.stream(ksi).map(i -> (1-G(i))*(1-G(i))).sum();
		double an = S1/n;
		double sign = (S2 - S1*S1/n)/(n-1);
		return Math.round(Math.pow(1.96, 2)*sign/(an*an*0.0001)) + 1;
	}
}
