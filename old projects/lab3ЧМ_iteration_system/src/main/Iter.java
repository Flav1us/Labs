package main;

public class Iter {
	//система с диагональным перевесом
	static double[][] sys = { {12.39,2.45,3.35,2.28}, {5.31,8.28,0.98,1.04}, {2.25,1.32,5.58,0.49}, {1.75,0.25,1.28,5.81} };
	//static double[][] sys = { {5,1,1,1}, {1,5,1,1}, {1,1,5,1}, {1,1,1,5} };
	static final int n = sys.length;
	static double[] b = {4.21, 8.97, 2.38, 12.98 };
	static double[] d = new double[n];
	static double[][] c = new double[n][n]/* = { {9.39,2.45,3.35,2.28}, {5.31,7.28,0.98,1.04}, {2.25,1.32,4.58,0.49}, {1.75,0.25,1.28,3.81} }*/;
	static final double e = 1E-15;

	static void initC() {
		//double[][] c = new double[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				c[i][j] = -(sys[i][j]/sys[i][i]);
				if(i==j) c[i][j] = 0;
				//System.out.println(c[i][j]);
			}
			d[i] = b[i]/sys[i][i];
		}
	}
	
	static double[] iter() {
		initC();
		double[] x = new double[n];
		double[] x1 = {100,200,123,321};
		double q=0, qmax=0, max=0;
		for(int i=0; i<n; i++) {
			q=0;
			for(int j=0; j<n; j++) {
				q+=c[i][j];
			}
			if(Math.abs(q)>1) {
				System.out.println("ERROR IN C: q>1");
				return null;
			}
			if(qmax<q) qmax=q;
		}
		q=qmax;
		int iter = 0;
		do {
			iter++;
			max=0;
			for(int i=0; i<n; i++) {x[i]=0;}
			for(int i=0; i<n; i++) {
				for(int j = 0; j<n; j++) {
					x[i] += x1[j]*c[i][j];
				}
				x[i]+=d[i];
			}
			System.out.format("x1=%,5f\tx2=%,5f\tx3=%,5f\tx4=%,5f\t%n", x[0], x[1], x[2], x[3]);
			for(int i=0; i<x.length; i++) {
				if(Math.abs(x1[i]-x[i]) > max) max = Math.abs(x1[i]-x[i]);
			}
			x1=x.clone();
			//System.out.println("\nmax: " + (1/(1-q))*max+"\n");
		} while((1/(1-q))*max >= e);
		
		for(int i=0; i<n; i++) {
			double t = 0;
			for(int j=0; j<n; j++) t+=x[j]*sys[i][j];
			System.out.println(t-b[i]);
		}
		System.out.println("Iterations: " + iter);
		return x;
	}
}
