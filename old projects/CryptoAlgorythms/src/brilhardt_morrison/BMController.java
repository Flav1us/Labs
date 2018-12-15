package brilhardt_morrison;

public class BMController {
	
	int n;
	public PTable pt;

	public BMController(int factorizable) {
		this.n = factorizable;
		IterableParameters itp = new IterableParameters(n);
		pt = new PTable((int)itp.a, this);
		for(int i=0; i<10; i++) pt.addArg((int)itp.iter());
	}
	
	public void process() {
		SimpleFactorisator.printFactor(pt);
	}


	
	public void simpleCheck() {
		BruteCheck.areSameP2(pt);
	}
	
	public int minmod(int t) {
		if(t<0) return minmod(n+t%n);
		if (t%n > n/2){
			return t%n-n;
		}
		else {
			return t%n;
		}
	}
}
