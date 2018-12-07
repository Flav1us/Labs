package lab2;

import java.util.Arrays;
import org.apache.commons.math3.distribution.NormalDistribution;

public class L2Task1 {
	int n;
	public double[] input;
	public int[] freq;
	int r = 40;
	double g = 0.05;
	public double zg = 54.572; //r = 40, g = 0.05
	double[] edges = new double[r+1];
	
	public L2Task1() {
		//regenerate(n);
	}


	public boolean regenerate(int n) {
		this.n= n;
		//input = util.SampleGenerator.standartGauss(n);
		NormalDistribution nb =new NormalDistribution(); 
		input = nb.sample(n);
		frequencies();
		double delta = delta(nb);
		System.out.println(delta + "\t" + zg + "\t" + (delta < zg));
		if(delta < zg) {
			///System.out.println(delta + "\tpassed");
			return true;
		}
		else {
			//System.out.println(delta + "\tfailed");
			return false;
		}
	}
	

	public void frequencies() {
		freq = new int[r];
		edges[0] = Arrays.stream(input).min().getAsDouble();
		edges[edges.length-1] = Arrays.stream(input).max().getAsDouble();
		double boxsize = (edges[edges.length - 1] - edges[0]) / r;
		for(int i = 1; i < edges.length - 1; i++) {
			edges[i] = edges[0] + i * boxsize;
		}
		//Arrays.stream(edges).forEach(System.out::println);
		for(int i=0; i<n; i++) {
			checkIn(input[i]);
		}
	}


	private void checkIn(double d) {
		for(int i=1; i<edges.length; i++) {
			if(d <= edges[i]) {
				freq[i-1]++;
				break;
			}
		}
	}
	
	public double delta(NormalDistribution nb) {
		//NormalDistribution nb = new NormalDistribution();
		double res = 0;
		double[] p = new double[r];
		for(int i = 0; i<edges.length-1; i++) {
			p[i] = nb.cumulativeProbability(edges[i], edges[i+1]);
		}
		for(int i = 0; i< r; i++) {
			if(n*p[i] < 5 && i < r-1) {
				freq[i+1] += freq[i];
				p[i+1] += p[i];
				i++;
			}
			res += Math.pow(freq[i] - n*p[i], 2)/(n*p[i]);
		}
		
		return res;
	}
	
}