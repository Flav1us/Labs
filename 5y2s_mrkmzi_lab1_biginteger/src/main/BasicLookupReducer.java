package main;

import java.util.Arrays;

public class BasicLookupReducer {
	//private static final int prec_size = 20;
	Myr mod;
	Myr[] lookup_table;
	int k;
	Myr b;
	public BasicLookupReducer(Myr mod) {
		if(mod.equals(Myr.ZERO)) {
			throw new ArithmeticException("modulus cant be zero");
		}
		this.b = Myr.ONE.shift(1); // L = 16
		this.mod = mod;
		this.k = getK(mod); // definition of k: mod < b^k
		//System.out.println("k: " + this.k);
		this.lookup_table = precalculation(this.mod, /*prec_size*/ 2*k);
	}
	
	public Myr reduce(Myr arg) {
		//for(int i = 0; i < arg.marr.length; i++) System.out.println(Integer.toHexString(arg.marr[i]));
		int[] marr = Arrays.copyOf(arg.marr, k); //k-1?
		//System.out.println("marr");
		//for(int i = 0; i < marr.length; i++) System.out.println(Integer.toHexString(marr[i]));
		Myr res = new Myr(marr);
		//System.out.println("res: " + res.toString());
		for(int i = k; i < arg.marr.length; i++) {
			//System.out.println("res: " + res.toString() + " += " + new Myr(Arrays.copyOfRange(arg.marr, i, i+1)).toString() + " * " + lookup_table[i-k].toString());
			try {
				res = res.add(lookup_table[i-k].multiply(new Myr(Arrays.copyOfRange(arg.marr, i, i+1))));
			} catch (ArrayIndexOutOfBoundsException e) {
				if (arg.toBinString().length() >= mod.toBinString().length() * 2) {
					throw new IllegalArgumentException(
							"Lookup table can't handle such a big argument, sempai. Argument should be < mod^2.\n"
									+ "arg bit length: " + arg.toBinString().length() + ",\n" + "mod bit length: "
									+ mod.toBinString().length() + ".");
				}
				else throw e;
			}
		}
		return res.mod(mod);
	}
	
	private Myr[] precalculation(Myr m, int size) {
		// lookup_table[j] = b^(k+j) mod n;
		Myr[] lookup_table = new Myr[size];
		Myr t = b.shift(k-1); //k or k-1? already shifter by one (Myr.ONE.shift(k)))
		for(int j = 0; j < lookup_table.length; j++) {
			lookup_table[j] = t.mod(m); //TODO optimize
			t = lookup_table[j].shift(1);
		}
		//System.out.println("lookup table:");
		//for(int i = 0; i < lookup_table.length; i++) {
		//	System.out.println(i + ": " + lookup_table[i]);
		//}
		return lookup_table;
	}
	
	public int getK(Myr mod) { //mod < b^k
		Myr t = this.b;
		for(int k = 1; k < 1000 ; k++) {
			if(mod.compareTo(t) < 0) return k;
			t = t.shift(1);
		}
		throw new RuntimeException("number impossibly large");
	}/*public static int getK(Myr mod) {
		int k = mod.toBinString().length();
		return k;
	}*/
}
