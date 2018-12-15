package brilhardt_morrison;

public class SimpleFactorisator {
	public static int[] base = {-1,  2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
	
	public static int[] factor(int arg) {
		int[] res = new int[base.length];
		if(arg < 0) res[0] = 1;
		for(int i=1; i<base.length; i++) {
			if(arg % base[i] == 0) {
				res[i] = powerOfDivisor(arg, base[i]);
				arg /= (int)Math.pow(base[i], res[i]);
			}
		}		
		if(arg == 1 || arg == -1)  return res;
		else throw new IllegalArgumentException("cant decompose over basis up to " + base[base.length-1]);
	}
	
	public static int powerOfDivisor(int dividend, int divisor) {
		double n = dividend;
		double p = divisor;
		int ctr = 0;
		while(true) {
			if(n % p == 0) {
				ctr++;
				n = n/p;
			}
			else {
				break;
			}
		}
		return ctr;
	}
	
	public static void print(int[] a) {
		for(int i=0; i<a.length; i++) System.out.print(a[i] + "\t");
		System.out.println();
	}

	public static void printFactor(PTable pt) {
		System.out.println("\n\t");
		for(int i=0; i<base.length; i++) System.out.print(base[i] + "\t");
		System.out.println();
		for(int i = 1; i < pt.cStep; i++) {
			try {

				print(factor(pt.table[2][i]));
			}
			catch (IllegalArgumentException e) {
				continue;
			}
		}
	}
}
