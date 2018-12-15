package brilhardt_morrison;

public class IterableParameters {
	int D;
	double sqrD;
	
	double U;
	double V;
	double alph;
	double a;
	
	public IterableParameters(int n) {
		D = n;
		sqrD = Math.sqrt(D);
		
		V = 1;
		a = Math.floor(Math.sqrt(n));
		//System.out.println("a = " + a);
		U = 0 + a*V;
		//System.out.println("U = " + U);
	}
	
	public double iter() {
		System.out.println("V = (" + D + " - " + U + "^2)/" + V + " = " + (D-U*U)/V);
		V = (D-U*U)/V;
		System.out.println("alph = (" + sqrD + " + " + U + ")/" + V + " = " + (sqrD+U)/V);
		alph = (sqrD+U)/V;
		a = Math.floor(alph);
		System.out.println("a = " + a);
		System.out.println("U = " + a + "*" + V + " - " + U + " = " + (a*V-U)) ;
		U = a*V - U;
		System.out.println("---------");
		
		return a;
	}
}
