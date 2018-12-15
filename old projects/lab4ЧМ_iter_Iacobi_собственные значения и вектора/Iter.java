
public class Iter {
	static Matrix compT(Matrix A, int i, int j) {
		if(A.n!=A.m) throw new IllegalArgumentException("A in sot a square Matrix: A.n = "+A.n+" != A.m = " + A.m);
		double[][] values = new double[A.n][A.m];
		for(int k=0; k<A.n; k++) {
			values[k][k] = 1.0;
		}
		double mu = (A.values[j][j] - A.values[i][i])/(2*A.values[i][j]);
		double t = sign(mu)/(Math.abs(mu) + Math.pow(Math.pow(mu, 2)+1, 0.5)), t2=Math.pow(t, 2);
		double c = 1/(Math.pow(t2+1, 0.5));
		double s = t*c;
		values[i][i] = c;
		values[j][j] = c;
		values[i][j] = s;
		values[j][i] = -s;
		Matrix T = new Matrix(values);
		return T;
	}
	
	static double sign(double t) {
		return t/Math.abs(t);
	}
	
	static int[] maxNdElem(Matrix A) {
		int[] pos = new int[2];
		double max = 0;
		for(int i=0; i<A.n; i++) {
			for(int j=0; j<A.m; j++) {
				if(i!=j) {
					if(Math.abs(A.values[i][j]) > max) {
						max = Math.abs(A.values[i][j]);
						pos[0] = i; pos[1] = j;
					}
				}
			}
		}
		return pos;
	}
	
	static void iterate(Matrix A) {
		int[] pos;
		Matrix T;
		for(int i=0; i<A.n*(A.n-1); i++) {
			pos = Iter.maxNdElem(A);
			T = Iter.compT(A, pos[0],pos[1]);
			System.out.println("T:");
			T.print();
			System.out.println("T':");
			T.T().print();
			A = Matrix.mul(Matrix.mul(T.T(),A), T);
			System.out.println("A:");
			A.print();
			System.out.println("Snd = " + A.Snd());
			System.out.println("\n");
		}
	}
}
