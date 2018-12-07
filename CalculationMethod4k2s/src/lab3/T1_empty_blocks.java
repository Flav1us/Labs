 package lab3;

import java.util.Arrays;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class T1_empty_blocks {
	
	public static boolean regenerate(int n_, int m_, double alph, double beta) {
		System.out.format("n = %s\talpha = %s\nm = %s\tbeta = %s\n", n_, alph, m_, beta);
		double[] X, Y;
		double z_g = 1.645;
		X = new ExponentialDistribution(alph).sample(n_);
		Y = new ExponentialDistribution(beta).sample(m_);
		/* debug
		double[] X = {1.0, 2.0, 3.0};
		double[] Y = {-10, -10, -10, 1.5, 1.5, 2.5, 3.5, 10, 10, 10};
		*/
		Arrays.sort(X);
		int[] blocks = new int[X.length + 1];
		int curr_block = 0;
		for(int i = 0; i < Y.length; i++) {
			while(Y[i] > X[curr_block] && curr_block < X.length - 1) curr_block ++;
			if(Y[i] > X[X.length - 1]) curr_block ++; //костыль
			blocks[curr_block] ++;
			curr_block = 0;
		}
		//System.out.println("points in each block:");
		//for(int i = 0; i < blocks.length; i++) System.out.print(blocks[i] + " ");System.out.println();
		
		int empty_ctr = 0;
		for(int i = 0; i<blocks.length; i++) {
			if(blocks[i] == 0) empty_ctr++;
		}
		System.out.println("empty blocks: " + empty_ctr + " of " + blocks.length + " total");
		
		double m = m_, n = n_; //для того, чтобы не писать каждый раз double в дроби
		double ro = m/n;
		double Mu_kr = n/(1+ro) + ro*Math.sqrt(n)*z_g/Math.pow(1+ro, 1.5);
		System.out.println("Mu_0 = " + empty_ctr + ", Mu_kr = " + Mu_kr);
		if(empty_ctr > Mu_kr) {
			System.out.println("rejected\n");
			return false;
		}
		else {
			System.out.println("accepted\n");
			return true;
		}
	}
}
