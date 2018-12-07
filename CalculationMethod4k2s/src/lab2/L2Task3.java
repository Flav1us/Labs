package lab2;

import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

public class L2Task3 { // Критерий Смирнова
	public int n = 110;
	public int m = 240;
	private double[] X;
	private double[] Y;
	private double zg = 1.36;
			
	public double F(double x, double[] sample) {
		double res = 0.0;
		for(int i = 0; i < sample.length; i++) {
			if(x > sample[i]) res++;
		}
		//System.out.println(res/sample.length);
		return res/sample.length;
	}
	
	public double Dnm() {
		double[] Y_ = Y.clone(); 
		Arrays.sort(Y_);
		double Dnm_pl = Double.NEGATIVE_INFINITY;
		double Dnm_min = Double.NEGATIVE_INFINITY;
		double temp;
		for(int k = 1; k <= m; k++) {
			temp = (double)k/m - F(Y_[k-1], X);
			//System.out.println((double)k/m + " - " +  F(Y_[k-1], X));
				
			if(temp > Dnm_pl) Dnm_pl = temp;
		}
		for(int k = 1; k <= m; k++) {
			temp = F(Y_[k-1], X) - (double)(k-1)/m;
			if(temp > Dnm_min) Dnm_min = temp;
		}
		//System.out.println(Dnm_pl+"\t" +Dnm_min);
		return Math.max(Dnm_pl, Dnm_min);
	}
	
	public boolean criteria() {
		System.out.println(zg*Math.sqrt(1d/n + 1d/m));
		return Dnm() < zg*Math.sqrt(1d/n + 1d/m);
	}
	
	public void recalculate() {
		X = new NormalDistribution().sample(n);
		Y = new NormalDistribution(0, 1).sample(m);
		//Y = new UniformRealDistribution(-10, 10).sample(m);
		//Arrays.sort(X);
		//for(int i = 0; i<Y.length; i++) System.out.println(F(Y[i], Y));
		double Dnm = Dnm();
		System.out.println(Dnm);
		System.out.println(criteria());
	}
}
