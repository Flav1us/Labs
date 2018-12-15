package main;

public class Polynomial {
	double[] f = {1,1,-2,-9,-3,-2};
	double delta = 0.0001; // погрешность. истинное значение ~ 2.18251
	int iterations = 0;
	
	double f(double x) {
		return Math.pow(x,5) + Math.pow(x,4) - 2*Math.pow(x,3) - 9*Math.pow(x,2) - 3*x - 2;
	}
	
	double df(double x) {
		return 5*Math.pow(x,4) + 4*Math.pow(x,3) - 6*Math.pow(x,2) - 18*x - 3;
	}
	
	boolean checkRoot(double x1, double x2) {
		if(f(x1)*f(x2) < 0) return true;
		else return false;
	}
	
	double halves(double x1, double x2) {
		double c=(x1+x2)/2;
		while(x2-x1 > delta) {
			iterations++;
			//System.out.println(x1 + " " + x2);
			c=(x1+x2)/2;
			if(checkRoot(x1, c) == true) x2=c;
			else x1=c;
		}
		System.out.println("1: " + iterations + " iterations");
		iterations = 0;
		return c;
	}
	
	double chord(double x1, double x2) {
		double c=(x1+x2)/2;
		while(x2-x1 > delta && Math.abs(f(c)) > delta) {
			//System.out.println(x1 + " " + x2);
			c=(x1*f(x2)-x2*f(x1)) / (f(x2)-f(x1));
			iterations++;
			if(checkRoot(x1, c) == true) x2=c;
			else x1=c;
		}
		System.out.println("2: " + iterations + " iterations");
		iterations = 0;
		return c;
	}
	
	double newton(double x1, double x2) {
		double c2, c1=(x1+x2)/2;
		do {
			//System.out.println("c1 = " + c1);
			c2 = c1;
			c1=c1-f(c1)/df(c1);
			iterations++;
		} while(Math.abs(c2-c1) > delta && Math.abs(f(c1)) > delta);
		System.out.println("3: " + iterations + " iterations");
		iterations = 0;
		return c1;
	}
}
