package main;

public class BarrettReducer {
	Myr mod;
	Myr mu;
	int k;
	public BarrettReducer(Myr mod) {
		if(mod.equals(Myr.ZERO)) {
			throw new ArithmeticException("modulus cant be zero");
		}
		//System.out.println("BR constructor:");
		this.mod = mod;
		this.k = mod.toBinString().length()/16 + 1;
		if(mod.toBinString().length() % 16 == 0) k--;
		this.mu = getMu(mod);
		//System.out.println("(hex)factor: " + this.mu.toString());
		//System.out.println("(dec)shift " + shift);
		//System.out.println("init barrett reducer done");
	}
	
	private Myr getMu(Myr mod) {
		/*Myr b = Myr.ONE.shift(1);
		int k = mod.toBinString().length();
		return b.pow(new Myr(Integer.toHexString(2*k)).divide(mod));*/
		return Myr.ONE.shift(2*k).divide(mod); // b = 2
	}

	public Myr reduce(Myr z) {
		//long t0 = System.currentTimeMillis();
		if(z.compareTo(mod) < 0) return z;
		Myr q = z.shift(-k+1); //optimize: killLD, but need to redifine shift
		q = q.multiply(this.mu);
		q = q.shift(-k-1);
		//q = q.multiply(this.mod);
		Myr r = Myr.longSub(z, q.multiply(mod));
		while (Myr.comp(r, this.mod) >= 0) {
			//System.out.println(r.size + "\t" +  this.mod.size);
			//System.out.println(r.toString() + "\n" + this.mod.toString());
			//System.out.println("sub");
			r = Myr.longSub(r, this.mod);
		}
		/*if(Myr.comp(r, this.mod) >= 0) {
			System.out.println("r / mod = " + r.divide(mod));
			throw new IllegalStateException("r > mod:\n"+r.toString() + " > " + mod.toString());
		}*/
		//System.out.println("reduce time: " + (System.currentTimeMillis() - t0));
		return r;//new Myr(r.toString());
	}
	
	
}
