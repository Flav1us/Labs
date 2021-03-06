package advancedBarrett;

import main.Myr;

public class AdvBarrettReducer {
	Myr mod;
	Myr mu;
	int shift;
	public AdvBarrettReducer(Myr mod) {
		if(mod.equals(Myr.ZERO)) {
			throw new ArithmeticException("modulus cant be zero");
		}
		//System.out.println("BR constructor:");
		this.mod = mod;
		this.shift = mod.toBinString().length()*2; //TODO redifine
		this.mu = getMu(mod);
		//System.out.println("(hex)factor: " + this.mu.toString());
		//System.out.println("(dec)shift " + shift);
		//System.out.println("init barrett reducer done");
	}
	
	private Myr getMu(Myr mod) {
		/*Myr b = Myr.ONE.shift(1);
		int k = mod.toBinString().length();
		return b.pow(new Myr(Integer.toHexString(2*k)).divide(mod));*/
		return Myr.ONE.shiftBits(shift).divide(mod); // b = 2
	}

	public Myr reduce(Myr x) {
		if(x.compareTo(mod) < 0) return x;
		Myr q = Myr.LongMul(x, this.mu);
		q = q.shiftBits(-shift); //optimize: killLD, but need to redifine shift
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