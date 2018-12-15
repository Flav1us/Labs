package main;

import java.util.ArrayList;
import java.util.List;

public class CoordinateFunction {
	
	public boolean[] values;
	public int disbalance;
	public int n;
	// в cf[0] - 16384 нулей, остальные по модулю - 256.
	public int[] walshTable;
	public int NL;
	public int corrImmun;
	public int deg;
	//в avgSpreadingDeviation() берется spreading отсюда, если он не null
	public int[] spreading = null;
	public double[] avgSpreadingDeviation;
	public boolean hasSAE;
	
	public CoordinateFunction(boolean[] values, int n) {
		this.values = values;
		this.n = n;
		this.init();
	}
	
	public void init() {
		/*for(int i=0; i<this.values.length; i++) {
			System.out.println(i + "\t" + this.values[i]);
		}*/
		this.disbalance = this.disbalance();
		this.walshTable = this.findWalshTable();
		this.NL = this.findNL();
		this.corrImmun = this.findCorrImmun();
		this.deg = this.findPower();
		this.avgSpreadingDeviation = this.avgSpreadingDeviation();
		this.spreading = this.allSpreading();
		this.hasSAE = this.hasSAE();
	}
	
	public int disbalance() {
		int numOfOnes=0, numOfZeroes=0;
		for(boolean i : values) {
			if (i==false) numOfZeroes++;
			else numOfOnes++;
		}
		return Math.abs(numOfOnes-numOfZeroes);
	}
	
	/*public static int[] fastFurieTransform(int[] u, int n) {
		int t;
		for(int i=0; i<n; i++) {
			System.out.println("i=" + i);
			//for(int j=0; j<(int)Math.pow(2, i); j++) {
			for(int j=0; j<n; j+=(int)Math.pow(2, i)) {
				System.out.println("j="+j);
				for(int k=0; k<(int)Math.pow(2, n-i-1); k++) {
					t = u[k+j];
					u[k+j]= t + u[k+j+(int)Math.pow(2, n-i-1)];
					System.out.println("k+j = " + (k+j)+ ", (int)Math.pow(2, n-i-1) = " + (int)Math.pow(2, n-i-1) );
					u[k+j+(int)Math.pow(2, n-i-1)]= t - u[k+j+(int)Math.pow(2, n-i-1)];
				}
			for(int k=0; k<u.length; k++) System.out.print(u[k] + " ");
				//for(int k=(int)Math.pow(2, n-i-1)+1; k<(int)Math.pow(2, n-i); k++) {
					//u[k+j]= t - u[k+j+(int)Math.pow(2, n-i-1)];
				//}
			 System.out.println();
			}		
		}
		return u;
	}*/

	public static int[] fastFurieTransform(int[] u, int n) {
		/*for(int i=0; i<3; i++) {
		System.out.println();
		for(int j=0; j<8; j+=(int)Math.pow(2, 3-i)) {
			System.out.println();
			for(int k=j; k<j+(int)Math.pow(2, 3-i-1); k++) {
				System.out.println(k + " ");
			}
		}*/
	
		int t;
		for(int i = 0; i<n; i++) {
			//System.out.println("\ni="+i);
			for(int j=0; j<u.length; j+=(int)Math.pow(2, n-i)) {
				for(int k=j; k<j+(int)Math.pow(2, n-i-1); k++) {
					//System.out.println(k);
					t = u[k];
					u[k] = u[k] + u[k+(int)Math.pow(2, n-i-1)];
					u[k+(int)Math.pow(2, n-i-1)] = t - u[k+(int)Math.pow(2, n-i-1)];
				}
			}
			/*for(int k=0; k<u.length; k++) System.out.print(u[k] + " ");
			System.out.println();*/
		}
		return u;
	}
	
	public void printWalshTable() {
		System.out.println();
		for(int i=0; i<Math.pow(2, n); i++) {
			System.out.println(BooleanFunction.lpad(Integer.toBinaryString(i), n) + " " + this.walshTable[i]);
		}
		System.out.println();
	}
	
	public static void test() {
		System.out.println("COR\n\n");
		CoordinateFunction.testCor();
		System.out.println("\n\nFFT\n\n");
		CoordinateFunction.testFFT();
		System.out.println("\n\nFMT\n\n");
		CoordinateFunction.testFMT();
		System.out.println("\n\nNL\n\n");
		CoordinateFunction.testNL();
		System.out.println("\n\nPOWER\n\n");
		CoordinateFunction.testPower();
		System.out.println("\n\nSPREADING\n\n");
		CoordinateFunction.testSpreading();
	}
	
	static void testFFT() {
		int[] t = {1,2,3,4,2,3,4,5,1,1,1,1,1,1,1,1};
		t = fastFurieTransform(t, 3);
		for(int i=0; i<t.length; i++) {
			System.out.print(t[i] + " ");
		}
		System.out.println();
		//output: <24 -4 -8 0 -4 0 0 0 8 0 0 0 0 0 0 0> 
	}
	
	public static boolean[] fastMebiusTransform(boolean[] u, int n) {
		//boolean t;
		for(int i = 0; i<n; i++) {
			//System.out.println("\ni="+i);
			for(int j=0; j<u.length; j+=(int)Math.pow(2, n-i)) {
				for(int k=j; k<j+(int)Math.pow(2, n-i-1); k++) {
					//System.out.println(k);
					//t = u[k];
					//u[k] = u[k] + u[k+(int)Math.pow(2, n-i-1)];
					u[k+(int)Math.pow(2, n-i-1)] = u[k] ^ u[k+(int)Math.pow(2, n-i-1)];
				}
			}//for(int k=0; k<u.length; k++) System.out.print(u[k] + " ");
		}
		return u;
	}
	
	static void testFMT() {
		boolean[] u = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true};
		u = fastMebiusTransform(u, 5);
		System.out.println();
		for(boolean t : u) System.out.print(t==false ? 0+" " : 1+" ");
		System.out.println();
	}
	
	static void testPower() {
		boolean[] values = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true};
		CoordinateFunction cf = new CoordinateFunction(values, 5);
		System.out.println(cf.findPower() + " =? 5");
	}
	
	public int findPower() {
		int max=0, pow=0;
		String s;
		boolean[] t = fastMebiusTransform(this.values.clone(), this.n);
		for(int i=0; i<t.length; i++) {
			if(t[i] == true) {
				s = Integer.toBinaryString(i);
				for(char c : s.toCharArray()) {
					if(c == '1') max++;
				}
				if(max > pow) pow = max;
				max = 0;
			}
		}
		return pow;
	}
	
	int[] findWalshTable() {
		int[] t = new int[this.values.length];
		for(int i=0; i<t.length; i++) {
			t[i] = this.values[i] ? -1 : 1;
		}
		t = fastFurieTransform(t, n);
		return t;
	}
	
	public int findNL() {
		int[] t = this.walshTable.clone();
		int max = 0;
		for(int i : t) {
			if(Math.abs(i) > max) max = Math.abs(i);
		}
		return ((int)Math.pow(2, n-1) - max/2);
	}
	
	static void testNL() {
		boolean[] values = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true};
		CoordinateFunction cf = new CoordinateFunction(values, 5);
		System.out.println(cf.findNL() + " ?= 5");
	}
	
	static void testCor() {
		boolean[] values = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true};
		CoordinateFunction cf = new CoordinateFunction(values, 5);
		System.out.println(cf.findCorrImmun());
	}
	
	public int findCorrImmun() {
		int res = 0;
		List<Integer> index = new ArrayList<>();
		for(int i=0; i<n; i++) {
			int nOfOnes = 0;
			for(int j=0; j<this.walshTable.length; j++) {
				for(char c : Integer.toBinaryString(j).toCharArray()) {
					if(c == '1') nOfOnes++;
				}
				if(nOfOnes == i) index.add(j);
				nOfOnes = 0;
			}
			for(Integer j : index) {
				if(this.walshTable[j] != 0) return res;
			}
			res = i;
			index.clear();
		}
		return res;
	}
	
	//Strict Avalance Effect
	public boolean hasSAE() {
		int c = (int)Math.pow(2, n-1);
		for(int d : this.spreading) {
			if (d != c) return false;
		}
		return true;
	}
	
	public int spreading(int varNum) {
		int k = 0;
		int twoPowN = (int)Math.pow(2, n);
		String t1, t2;
		//String s;
		char[] chArr;
		for(int i=0; i<twoPowN; i++) {
			t1 = BooleanFunction.lpad(Integer.toBinaryString(i), n);
			chArr = t1.toCharArray();
			chArr[varNum] = t1.charAt(varNum)=='1' ? '0' : '1';
			t2 = String.valueOf(chArr);
			//System.out.println(t1 + " " + t2 + " " + this.values[Integer.parseInt(t1, 2)] + " " + this.values[Integer.parseInt(t2, 2)]);
			//System.out.println(t1 + "\t" + t2 + "\t" + Integer.parseInt(t2, 2) + "\t\t" + this.values[Integer.parseInt(t1, 2)] + "\t" + this.values[Integer.parseInt(t2, 2)]);
			k+=(this.values[Integer.parseInt(t1, 2)] ^ this.values[Integer.parseInt(t2, 2)])==false ? 0 : 1;
		}
		//System.out.println();
		return k;
	}
	
	/*	public int spreading(int varNum) {
		int k = 0;
		int twoPowN = (int)Math.pow(2, n);
		String t1, t2;
		//String s;
		char[] chArr;
		for(int i=0; i<twoPowN; i++) {
			t1 = BooleanFunction.lpad(Integer.toBinaryString(i), n);
			chArr = t1.toCharArray();
			chArr[varNum] = t1.charAt(varNum)=='1' ? '0' : '1';
			t2 = String.valueOf(chArr);
			//System.out.println(t1 + " " + t2 + " " + this.values[Integer.parseInt(t1, 2)] + " " + this.values[Integer.parseInt(t2, 2)]);
			k+=(this.values[Integer.parseInt(t1, 2)] ^ this.values[Integer.parseInt(t2, 2)])==false ? 0 : 1;
		}
		return k;
	}*/
	
	public int[] allSpreading() {
		int[] res = new int[n];
		for(int i=0; i<res.length; i++) {
			//System.out.println("kappa");
			res[i] = this.spreading(i);
			//System.out.println(res[i]);
		}
		return res;
	}
	
	public double[] avgSpreadingDeviation() {
		int[] spr;
		if(this.spreading != null) spr = this.spreading;
		else spr = this.allSpreading();
		/*for(int i=0; i<allSpreading().length; i++) {
			System.out.println(allSpreading()[i]);
		}*/
		double[] res = new double[n];
		//double t = 0;
		for(int i=0; i<res.length; i++) {
			res[i] = (Math.abs(spr[i] - Math.pow(2, n-1))/(Math.pow(2, n-1)));
			//t+=res[i];
		}
		return res;
	}
	
	
	
	static void testSpreading() {
		boolean[] values = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true};
		CoordinateFunction cf = new CoordinateFunction(values, 5);
		/*for(int i=0; i<cf.values.length; i++) {
			System.out.println(i + "\t" + cf.values[i]);
		}*/
		//System.out.println();
		for(int i=0; i<cf.n; i++) {
			System.out.print(cf.spreading(i) + " ");
			System.out.println();
		}
		System.out.println();
	}
	
	static <T extends Iterable<T>> void printArr (T arr) {
		for(T i : arr) {
			System.out.println(i.toString());
		}
	}
	
	//Равенство парсеваля: сумма квадратов коэф. Уолша должна быть равна 2^2n.
	public void testParsevalEquality() {
		int res = 0;
		for(int w : this.walshTable) {
			res += w*w;
		}
		System.out.println(res + " should be " + (int)Math.pow(2, 2*n));
	}
	
	/*public int power() {
		boolean[] u = this.values.clone();
		int twn = (int)Math.pow(2, n);
		int t;
		int pow;
		for(int i=0; i<n; i++) {
			for(int j=0; j<(int)Math.pow(2, i); j++) {
				for(int k=0; k<(int)Math.pow(2, n-j-1); k++) {
					
				}
			}
		}
		return 0;
	}*/
}
