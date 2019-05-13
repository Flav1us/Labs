package main;

public class BarrettReducer {
	Myr mod;
	Myr mu;
	int shift;
	public BarrettReducer(Myr mod) {
		if(mod.equals(Myr.ZERO)) {
			throw new ArithmeticException("modulus cant be zero");
		}
		//System.out.println("BR constructor:");
		this.mod = mod;
		this.shift = mod.toBinString().length()*2;
		this.mu = getMu(mod);
		//System.out.println("(hex)factor: " + this.mu.toString());
		//System.out.println("(dec)shift " + shift);
		//System.out.println("init barrett reducer done");
	}
	
	public Myr getMu(Myr mod) {
		//b = 2^16, mu = b^2k / p, k = log_b (p) + 1
		int k = mod.marr.length;
		return Myr.ONE.shift(2*k).divide(mod); // b = 2
	}

	public Myr reduce(Myr x) {
		if(x.compareTo(mod) < 0) return x;
		int k = mod.marr.length;
		//Myr q = Myr.LongMul(x, this.mu);
		Myr q = x.KillLD(k-1); //optimize: killLD, but need to redifine shift
		q = q.multiply(mu);
		q = q.KillLD(k+1);
		q = q.multiply(this.mod);
		Myr r = Myr.LongSub(x, q);
		if (Myr.comp(r, this.mod) >= 0) {
			//System.out.println(r.toString() + "\n" + this.mod.toString());
			//System.out.println("sub");
			r = Myr.LongSub(r, this.mod);
		}
		if(Myr.comp(r, this.mod) >= 0) throw new IllegalStateException();
		return r;//new Myr(r.toString());
	}
	
	
}
