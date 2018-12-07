package util;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;

public class SampleGenerator {

	public static double[] standartGauss(int size) {
		boolean isSizeOdd = false;
		if(size % 2 == 1) {
			size++;
			isSizeOdd = true;
		}
		double[] res = new double[size];
		double[] uniform = uniform(0, 1, size);
		for(int i=0; i<size; i+=2) {
			res[i] = Math.sqrt(-2 * Math.log(uniform[i])) * Math.sin(2 * Math.PI * uniform[i + 1]);
			res[i+1] = Math.sqrt(-2 * Math.log(uniform[i])) * Math.cos(2 * Math.PI * uniform[i + 1]);
		}
		if(isSizeOdd) return Arrays.copyOf(res, size-1);
		return res;
	}
	
	public static double[] uniform(double min, double max, int size) {
		Random r = new Random();
		double[] res = new double[size];
		for(int i=0; i<res.length; i++) {
			res[i] = r.nextDouble() * (max - min) + min;
		}
		return res;
	}
	
	public static double[] weibull(double alpha, int power, int size) {
		/*double[] uni = new UniformRealDistribution(0, 1).sample(size);
		System.out.println("\t" + Math.pow(Math.log(0.06174181236390486), ));
		System.out.println("kappa\t" + uni[1] + "\t" + Math.pow(Math.log(uni[1]), 1.0/power));
		return Arrays.stream(uni).map(x -> -(Math.pow(Math.log(x), 1.0/power) / alpha)).toArray();*/
		///double k = new WeibullDistribution(power, 1.0/alpha).inverseCumulativeProbability(0.05);
		return new WeibullDistribution(power, 1.0/alpha).sample(size);
	}

}
