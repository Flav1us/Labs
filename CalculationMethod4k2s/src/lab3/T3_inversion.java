package lab3;

import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

public class T3_inversion {
	public static void regenerate(boolean shouldBeAccepted) {
		int n = 500; // 50k ~ 5 sec
		double z_g = 1.645;
		double[] X = new UniformRealDistribution().sample(n); 
		
		//should be rejected
		if (!shouldBeAccepted) {
			double[] Y = new NormalDistribution().sample(n);
			for(int i = n/2; i < X.length; i++) X[i] = Y[i];
		}
		
		//double[] X = new double[500];
		//for(int i = 0; i < X.length; i++) X[i] = i;
		//double[] X_ = X.clone();
		//double[] X = {2.0, 1.0, 3.0}, X_ = X.clone(); //debug
		
		
		double[] X_ = X.clone();
		int[] indexes = new int[X.length];
		Arrays.sort(X_);
		for(int i = 0; i<X_.length; i++) {
			Double x = new Double(X_[i]);
			for(int j = 0; j < X.length; j++) {
				if(x.equals(X[j])) {
					indexes[i] = j;
					break;
				}
			}
		}
		
		int S_n = 0;
		for(int i = 0; i < indexes.length; i++) {
			for(int j = i; j >= 0; j--) {
				if(indexes[j] > indexes[i]) S_n++;
			}
			//System.out.println(S_n);
		}
		System.out.println("S_n = " + S_n);
		double stat = Math.abs(S_n - (double)n*(n-1)/4)*Math.sqrt(72.0/(n * (n-1) * (2*n+5)));
		System.out.println("stat: " + stat);
		if( stat < z_g) {
			System.out.println("accepted");
		}
		else System.out.println("rejected");
		System.out.println();
		//Arrays.stream(indexes).forEach(x -> System.out.print(x + " ")); System.out.println();
		
	}
}
