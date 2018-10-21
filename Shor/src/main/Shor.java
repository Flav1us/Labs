package main;

import java.util.Random;

import org.mathIT.numbers.Numbers;
import org.mathIT.quantum.Circuit;
import org.mathIT.quantum.Register;
import org.mathIT.util.FunctionParser;

public class Shor {

	private int n;
	
	public Shor(int n) {
		this.n = n;
	}

	public int run() throws Exception {
		Random rand = new Random();
		for(int i = 0; i < 200; i++) {
			int a = 2 + rand.nextInt(n - 3);
			//System.out.println("a = " + a);
			int order = (int) extractOrder(getGoodY(a), n);
			//int order = estimate(a);
			//System.out.println("Order: " + order);
			if (order % 2 == 0) {
				int x = Numbers.modPow(a, order/2, n) - 1;
				int d = (int) Numbers.gcd(x, n);
				if (d >= 2) return d;
			}
		}
		throw new Exception("too many iterations");
	}

	private double getGoodY(int a) throws Exception {
		int qbits = (int) (2 * (1 + Math.log(n - 1)/Math.log(2)));
		Circuit c = new Circuit();
		c.initialize(qbits, qbits/2, 0);
		for (int i = 1; i <= qbits; i++) {
			c.addHadamard(i, false);
		}

		c.addFunction(new FunctionParser(String.format("%s ^ x mod %s", a, n)));
		
		c.addMeasurement(intRange(c.getYRegister().size), true);
		c.addInvQFT(false);
		c.addMeasurement(intRange(c.getXRegister().size), false);
		
		while(true) {		
			c.initializeRegisters();
			c.executeAll();
			int stateNum = read(c.getXRegister());
			if (stateNum != 0) return Double.valueOf(stateNum) / Numbers.pow(2, qbits);
			//if (j != 0) return extract(j, (int)Numbers.pow(2, qbits), 2);
		}
	}

	private int read(Register reg) {
		double[] re = reg.getReal();
		double[] im = reg.getImaginary();
		for (int stateNum = 0; stateNum < re.length; stateNum++) {
			double p = re[stateNum] * re[stateNum] + im[stateNum] * im[stateNum];
			if (new Double(p).equals(new Double(1))) {
				return stateNum;
			}
		}
		throw new RuntimeException("Indeterminate state");
	}
	
	public static long extractOrder(double value, int bound) {
		//Модели параллельного программирования	И.Е. Федотов 6.4.4
		//https://c4b475d7-a-62cb3a1a-s-sites.googlegroups.com/site/programming086lectures/qc/chapter4.pdf?attachauth=ANoY7cqauQ2V4zrZxpa6EvL9YYxbVNkzRBIPjXJUSF4exlRf6qKrh4VkMzmh-uqJz7KHntpI1hBmw9zYsk0MB9x8tjeeSfPNdYAFCz5qFVB0KGsgtNmnhWiPArO3XqqgVsiiKR8uxp6bFPXcOTi1CVLmWj2pAFvvM-Lg4JpWWHxRw7hq3UOl2ugKX6XB90XRN9uwLayjCxqTUUzoO-jorf5r-kmIqkfMxbDTEdFPEx53qvil01dLJx4%3D&attredirects=0
		int limit = 2;
		long y = 0;
		long newY = continuedFractionForLimit(value, limit);
		while (y != newY && newY < bound) {
			y = newY;
			limit++;
			newY = continuedFractionForLimit(value, limit);
		}
		return y;
	}

	private static long continuedFractionForLimit(double value, int limit) {
		long[] continuedFraction = Numbers.continuedFraction(value, limit);
		/*for(int i = 0; i<continuedFraction.length; i++) {
			System.out.print(continuedFraction[i] + " ");
		} System.out.println();*/
		long x = 1;
		long y = 0;
		for (int i = continuedFraction.length - 1; i >= 0; i--) {
			long newY = x;
			long newX = continuedFraction[i] * x + y;
			//System.out.println(continuedFraction[i] + " * " +  x + " + " + y);
			x = newX;
			y = newY;
		}
		return y;
	}
	
	public int[] intRange(int max) {
		int[] res = new int[max];
		for (int i = 0; i < max; i++) {
			res[i] = i + 1;
		}
		return res;
	}

}
