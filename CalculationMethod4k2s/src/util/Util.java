package util;

public class Util {
	public static double avg(double[] arg) {
		double sum = 0;
		for(double a : arg) sum += a;
		return sum/arg.length;
	}
}
