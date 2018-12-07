package lab1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import util.SampleGenerator;
import util.Util;

public class L1Task12 { //критерий хи-квадрат пирсона 


	
	public static void task1() {
		for(int n = 100; n <= 100000; n*=10) {
			//System.out.println("n = " + n);
			task1(n);
		}
	}
	
	public static void task2() {
		for(int n = 100; n <= 100000; n*=10) {
			//System.out.println("n = " + n);
			task2(n);
		}
	}
	
	/*public static void task3() {
		task3(1000000);
	}*/
	
	public static void task1(int n) {
		//n = 100, 1000, 10000, 100000
		System.out.println("1.1 =======================================================");
		double z_g = 1.96;
		double sig = 1;
		double[] sample = SampleGenerator.standartGauss(n);
		double a_n_ = Util.avg(sample);
		double min = a_n_ - z_g * sig / Math.sqrt(n); 
		double max = a_n_ + z_g * sig / Math.sqrt(n);
		System.out.println(min + "\t" + max);
		System.out.println("1.2 =================================");
		//a_n_ не изменяется
		sig = sig(sample, n);
		z_g = 1.66; //https://planetcalc.ru/5019/, p = 0.95, степеней свободы -- 99; 1.64 для 999 и 9999
		min = a_n_ - z_g * sig / Math.sqrt(n-1); 
		max = a_n_ + z_g * sig / Math.sqrt(n-1);
		System.out.println(min + "\t" + max);
		System.out.println("1.3 ==================================");
		//https://statanaliz.info/metody/opisanie-dannyx/122-doveritelnyj-interval-matematicheskogo-ozhidaniya
		z_g = 1.96;
		//a_n_ и sig не изменяются
		min = a_n_ - z_g * sig / Math.sqrt(n); 
		max = a_n_ + z_g * sig / Math.sqrt(n);
		System.out.println(min + "\t" + max);
	}
	
	
	public static void task2(int n) {
		System.out.println("2 =======================================================");
		double[] sample = SampleGenerator.standartGauss(n);
		
		Double[] z_g_100 = {74.22192747, 129.5611972};
		Double[] z_g_1000 = {914.2571538, 1089.530913};
		Double[] z_g_10000 = {9724.718377, 10279.07018};
		Double[] z_g_100000 = {99125.3733, 100878.4153};
		
		Map<Integer, Double[]> quantils = new HashMap<>();
		quantils.put(100, z_g_100);
		quantils.put(1000, z_g_1000);
		quantils.put(10000, z_g_10000);
		quantils.put(100000, z_g_100000);
		
		double z_g1 = quantils.get(n)[0];
		double z_g2 = quantils.get(n)[1];
		
		double sig = sig(sample, n);
		double min = n * sig / z_g2; 
		double max = n * sig / z_g1;
		System.out.println(min + "\t" + max);
	}
	
	
	
	public static double sig(double[] sample, int n) {
		double a_n_ = Util.avg(sample);
		return Arrays.stream(sample).map(xi -> Math.pow(xi - a_n_, 2)).sum() / n;
	}
	
	/*public static double F(double u, double alph) {
		return 1 - Math.pow(Math.E, -Math.pow(alph*u, 2));
	}
	
	public static double integral(double ksi[], double eta[]) {
		int x_max = Math.min(ksi.length, eta.length);
		int ctr = 0;
		for(int i = 0; i < x_max; i++) {
			if(ksi[i] < eta[i]) ctr ++;
		}
		return new Double(ctr)/x_max;
	}*/
	
	/*public double[] getBounds(double[] a1, double[] a2) {
		int x_max = Math.min(a1.length, a2.length);
		double y_max = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < x_max; i++) {
			
		}
		double[] res = new double[2];
		res[0] = x_max;
		res[1] = y_max;
		//TODO min and max bound for x and y?
		return res;
	}*/
	

}
