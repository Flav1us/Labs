package brilhardt_morrison;

public class PTable {
	BMController contr;
	public final int TAB_LENGTH = 10000;
	public int[][] table = new int[3][TAB_LENGTH];
	public int cStep = 2;
	
	public PTable(int init, BMController parentController) {
		this.contr = parentController;
		table[1][0] = 1;
		table[2][0] = 1;
		table[0][1] = init;
		System.out.println("a[1] = " + table[0][1]);
		table[1][1] = init;
		System.out.println("p[1] = " + table[1][1]);
		table[2][1] = contr.minmod(init*init);
		System.out.println("p2[1] = " + table[2][1]);
		System.out.println("=====================");
	}

	public void addArg(int iter) {
		System.out.println(cStep);
		table[0][cStep] = iter;
		System.out.println("a["+cStep+"] = " + table[0][cStep]);
		table[1][cStep] = contr.minmod(table[0][cStep] * table[1][cStep-1] + table[1][cStep-2]);
		System.out.println("p["+cStep+"] = " + table[1][cStep]);
		table[2][cStep] = contr.minmod(table[1][cStep] * table[1][cStep]);
		System.out.println("p2["+cStep+"] = " + table[2][cStep]);
		System.out.println("=====================");
		cStep++;
	}
	
	public void print() {
		System.out.print("\n¹:\t");
		for(int i=0; i<cStep; i++) {
			System.out.print(i + "\t");
		}
		
		System.out.print("\na:\t");
		for(int i=0; i<cStep; i++) {
			System.out.print(table[0][i] + "\t");
		}
		
		System.out.print("\np:\t");
		for(int i=0; i<cStep; i++) {
			System.out.print(table[1][i] + "\t");
		}
		
		System.out.print("\np2:\t");
		for(int i=0; i<cStep; i++) {
			System.out.print(table[2][i] + "\t");
		}
	}
	
}
