package main;

public class Main {

	public static void main(String[] args) {
		double sq32 = Math.sqrt(3.0/2);
		System.out.println(df3(0,0,0));
	}

	public static double f(double x1, double x2) {
		return (x1*x1 + 3*x2*x2 + Math.cos(x1 + x2));
	}
	
	public static double df1(double x1, double x2, double x3) {
		return 4*x1/s(x1, x2, x3) - 1;
	}
	
	public static double df2(double x1, double x2, double x3) {
		return 8*x2/s(x1, x2, x3);
	}
	
	public static double df3(double x1, double x2, double x3) {
		return 4*x3/s(x1, x2, x3) - 1;
	}
	
	public static double s(double x1, double x2, double x3) {
		return Math.sqrt(3+x1*x1+2*x2*x2+x3*x3);
	}
	
	
}
