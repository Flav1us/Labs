package main;

public class LinearSystem {
	//variant 9
	static final double[][] sysIn = { {3.81,0.25,1.28,1.75}, {2.25,1.32,5.58,0.49}, {5.31,7.28,0.98,1.04}, {10.39,2.45,3.35,2.28} };
	static double[][] sys = { {3.81,0.25,1.28,1.75}, {2.25,1.32,5.58,0.49}, {5.31,7.28,0.98,1.04}, {10.39,2.45,3.35,2.28} };
	static double[] b = {4.21, 8.97, 2.38, 12.98};
	static final double[] bIn = {4.21, 8.97, 2.38, 12.98};
	/*static final double[][] sysIn = { {1,1,1,1}, {0,1,1,1}, {0,0,1,1}, {0,0,0,1} };
	static double[][] sys = sysIn.clone();
	static double[] b = {1,5,3,4};
	static final double[] bIn = b.clone();*/
	static int[] swaps = {0, 1, 2, 3};
	
	static void decide() {
		
		for(int i=0; i<4; i++) {
			//System.out.println("\niteration" + i + ":");
			LinearSystem.iteration(i);
		}
		
		/*b[3] /= sys[3][3];
		sys[3][3] = sys[3][3]/sys[3][3];*/
		
		LinearSystem.printCurrentMatrix();
		/*LinearSystem.backIteration();
		LinearSystem.printCurrentMatrix();*/
		
		double[] x = new double[4];
		x[swaps[3]] = b[3];
		System.out.println("x" + swaps[3] + " = " + x[swaps[3]]);
		x[swaps[2]] = b[2]-(sys[2][3]*x[swaps[3]]);
		System.out.println("x" + swaps[2] + " = " + x[swaps[2]]);
		x[swaps[1]] = b[1]-(sys[1][3]*x[swaps[3]])-(sys[1][2]*x[swaps[2]]);
		System.out.println("x" + swaps[1] + " = " + x[swaps[1]]);
		x[swaps[0]] = b[0]-(sys[0][3]*x[swaps[3]])-(sys[0][2]*x[swaps[2]])-(sys[0][1]*x[swaps[1]]);
		System.out.println("x" + swaps[0] + " = " + x[swaps[0]]); 
		
		System.out.println();
		for(int i=0; i<4; i++) {
			System.out.println(" "+i+"| " + (x[0]*sysIn[i][0] + x[1]*sysIn[i][1] + x[2]*sysIn[i][2] + x[3]*sysIn[i][3] - bIn[i]));
		}
	}
	
	static void iteration(int n) {
		System.out.println("Iteration" + (n));
		double max = sys[n][n], temp;
		int mrow=n, mcol=n;
		for(int i=n; i<4; i++) {
			for(int j=n; j<4; j++) {
				if(Math.abs(sys[i][j]) > max) {
					mrow = i; mcol = j;
					max = sys[i][j];
				}
			}
		}
		swapCols(n, mcol);
		swapRows(mrow, n);
		LinearSystem.printCurrentMatrix();
		System.out.println("x status (swaps):");
		for(int i=0; i<4; i++) System.out.print(swaps[i] + " ");
		System.out.println("\n");
		for(int i=n; i<4; i++) {
			temp = sys[i][n];
			for(int j=n; j<4; j++) {
				sys[i][j] = sys[i][j]/temp;
				if(i>n) {
					sys[i][j] -= sys[n][j];
				}
			}
			b[i] = b[i]/temp;
			if(i>n) b[i] -= b[n];
		}
		LinearSystem.printCurrentMatrix();
	}
	
	static void backIteration() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<4; j++) {
				if(sys[i+1][j] != 0) sys[i][j] -= sys[i][j]/sys[i+1][j];
			}
		}
		
	}
	
	 static void printCurrentMatrix() {
		for(int i=0; i<4; i++) {
			System.out.print("|\t");
			for(int j=0; j<4; j++) {
				System.out.format("%2.4f   \t",sys[i][j]);
				
			}
			//System.out.print("|");
			//System.out.println("\t" + b[/*swaps[*/i/*]*/] + "\t|");
			System.out.format("|\t%2.4f\t  |\n", b[i]);
		}
		System.out.println();
	}
	 
	static void swapRows(int src, int dst) {
		double[] tarr = new double[4];
		double t;
		for(int i=0; i<4; i++) {
			tarr[i] = sys[src][i];
		}
		for(int i=0; i<4; i++) {
			sys[src][i] = sys[dst][i];
		}
		for(int i=0; i<4; i++) {
			sys[dst][i] = tarr[i];
		}
		t = b[dst];
		b[dst] = b[src];
		b[src] = t;
	}
	
	static void swapCols(int src, int dst) {
		double[] tarr = new double[4];
		int t;
		for(int i=0; i<4; i++) {
			tarr[i] = sys[i][src];
		}
		for(int i=0; i<4; i++) {
			sys[i][src] = sys[i][dst];
		}
		for(int i=0; i<4; i++) {
			sys[i][dst] = tarr[i];
		}
		t = swaps[dst];
		swaps[dst] = swaps[src];
		swaps[src] = t;
	}
	
}
