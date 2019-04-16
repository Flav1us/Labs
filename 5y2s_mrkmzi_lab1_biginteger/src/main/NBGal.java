package main;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NBGal {
	static /*final*/ int m = 131;
	static RealMatrix lambda = null;
	static int[] powersOf2mod263 = null;
	boolean[] marr;
	//������ ������������� input ������ ������������ �� �������� � ��������. ���������� ��� �������� � � ������� marr.
	NBGal(String input) throws IllegalArgumentException {
		marr = new boolean[m];
		if(input == "") input = "0";
		for(int i=0; i<input.length(); i++) {
			if(input.charAt(i)!='0' && input.charAt(i)!='1') {
				throw new IllegalArgumentException("Initialazing string contains illegal character: " + input.charAt(i) + " on step " + i + ".");
			}
			marr[i] = input.charAt(i)=='0' ? false : true;
		}
		if (powersOf2mod263 == null) powersOf2mod263 = findPowersOf2mod263();
		if( lambda == null ) lambda = findMultiplicativeMatrix();
	}
	
	static void setM(int new_m) {
		m = new_m;
		powersOf2mod263 = findPowersOf2mod263();
		lambda = findMultiplicativeMatrix();
	}
	
	static NBGal one() {
		String t = "";
		for(int i=0; i<m; i++) {
			t = t.concat("1");
		}
		return new NBGal(t);
	}
	
	public String toString() {
		String res = "";
		for(int i=0; i<this.marr.length; i++) {
			//System.out.println(res + "kappa");
			res = res.concat(this.marr[i] == false ? "0" : "1");
		}
		//while(res.charAt(0) == '0' && res.length() > 1) {res = res.substring(1);}
		return res;
	}
	
	static NBGal add(NBGal arg1, NBGal arg2) {
		NBGal res = new NBGal("");
		for(int i=0; i<m; i++) {
			res.marr[i] = arg1.marr[i] ^ arg2.marr[i];
		}
		return res;
	}
	
	static void testAdd() {
		boolean failDetector = false;
		NBGal t = new NBGal("1001100010010110010001");
		if(t.toString().equals(NBGal.add(t, new NBGal("0")).toString()) == false) {
			System.out.println("NBGal addition test failed on step 1.");
			failDetector = true;
		}
		if("0".equals(NBGal.add(t, t).toString()) == false) {
			System.out.println("NBGal addition test failed on step 2.");
			failDetector = true;
		}
		if(failDetector != true) {
			System.out.println("NBGal addition test passed.");
		}
	}
	
	public NBGal riseToPow2() {
		String res_inp = this.toString();
		String lastChar =  Character.toString(res_inp.charAt(res_inp.length() - 1));
		res_inp = res_inp.substring(0, res_inp.length()-1);
		res_inp = lastChar.concat(res_inp);
		return new NBGal(res_inp);
	}
	
	static void testRiseToPow2() {
		boolean failDetector = false;
		NBGal t = new NBGal("110101111101110100110101101");
		if(new NBGal("0").riseToPow2().toString().equals(new NBGal("0").toString()) == false) {
			System.out.println("NBGal rise to power 2 test failed on step 1.");
			failDetector = true;
		}
		NBGal t2 = t;
		for(int i=0; i<m; i++) {
			t2 = t2.riseToPow2();
		}
		if(t.toString().equals(t2.toString()) == false) {
			System.out.println("NBGal rise to power 2 test failed on step 2.");
			failDetector = true;
		}
		if (failDetector != true) {
			System.out.println("NBGal rise to power 2 test passed.");
		}
	}
	
	public boolean findTrace(NBGal arg) {
		boolean res = false;
		for(int i=0; i<m; i++) {
			res = res ^ this.marr[i];
		}
		return res;
	}

	static RealMatrix findMultiplicativeMatrix() {
		if (NBGal.powersOf2mod263 == null) NBGal.powersOf2mod263 = findPowersOf2mod263();
		//System.out.println("Calculating lambda...\n");
		//������� ������� 131�131 � ���������� ���� double
		RealMatrix lambda = MatrixUtils.createRealMatrix(m, m);
		for(int row=0; row<m; row++) {
			lambda.setRow(row, findKthRow(row));
		}
		return lambda;
	}

	private static double[] findKthRow(int row) {
		//double two_pow_row = Math.pow(2d, row);
		//double two_pow_column = 1;
		double resulting_row[] = new double[m];
		for(int column=0; column<m; column++) {
			//System.out.print(".");
			if ( // +- 2^i +- 2^j = 1 mod 2m+1
				/*argModPIs1(  two_pow_row + two_pow_column) ||
				argModPIs1(  two_pow_row - two_pow_column) ||
				argModPIs1(- two_pow_row + two_pow_column) ||
				argModPIs1(- two_pow_row - two_pow_column)*/
				argModPIs1(  powersOf2mod263[row] + powersOf2mod263[column]) ||
				argModPIs1(  powersOf2mod263[row] - powersOf2mod263[column]) ||
				argModPIs1(- powersOf2mod263[row] + powersOf2mod263[column]) ||
				argModPIs1(- powersOf2mod263[row] - powersOf2mod263[column])
			)
			{ resulting_row[column] = 1d; }
			else
			{ resulting_row[column] = 0d; }
			//two_pow_column *= 2d;
			//System.out.println("2^col = " + two_pow_column);
		}
		return resulting_row;
	}
	

	static boolean argModPIs1(double arg) {
		if(arg < 0) {
			arg = arg%(2*m+1);
			arg+=2*m+1;
		}
		if(arg % (2*m+1) == 1d) return true;
		else return false;
	}
	
	 static int[] findPowersOf2mod263() {
		//System.out.println("generated");
		int[] res = new int[NBGal.m];
		int k = 1;
		for(int i=0; i<NBGal.m; i++) {
			res[i] = k;
			k = k*2 % (2*m+1);
		}
		return res;
	}
	
	static void testMultiplicativeMatrix() {
		int oldm = m;
		boolean failDetector = false;
		
		m = 3; // ������� ������� �� ������� � ���������.
		NBGal.powersOf2mod263 = findPowersOf2mod263();
		NBGal.lambda = NBGal.findMultiplicativeMatrix();
		double[][] matrixData = {{0,1,0},{1,0,1},{0,1,1}};
		RealMatrix lambda_proved = MatrixUtils.createRealMatrix(matrixData);
		RealMatrix lambda = findMultiplicativeMatrix();
		for(int row = 0; row < m; row++) {
			for(int col = 0; col < m; col++) {
				if(lambda.getEntry(row, col) != lambda_proved.getEntry(row, col)) {
					System.out.println("Multiplicative matrix test failed on step 1.");
					failDetector = true;
				}
			}
		}
		if(failDetector == true) { // ������� ������� � �������. ���� ���� - ��� ������.
			printMatrix(lambda);
			System.out.println();
			printMatrix(lambda_proved);
		}
		
		m = oldm;
		NBGal.powersOf2mod263 = findPowersOf2mod263();
		NBGal.lambda = NBGal.findMultiplicativeMatrix();
		//NBGal t = new NBGal("1");
		int k = 0;
		for(int i=0; i<NBGal.m; i++) {
			for(int j = 0; j<NBGal.m; j++) {
				if(NBGal.lambda.getEntry(i, j) == 1d) k += 1; 
			}
		}
		if(k != 2*m-1) {
			System.out.println("Multiplicative matrix test failed on step 2: Number of '1' elements = " + k + " != 2*m-1 = 263.");
			failDetector = true;
		}
		if(failDetector == false) System.out.println("NBGal multiplicative matrix test passed.");
	}
	
	static NBGal multiply(NBGal arg1, NBGal arg2) throws Exception {
		NBGal res = new NBGal("");
		double[] vec1 = new double[m], vec2 = new double[m];
		for(int i=0; i<m; i++) {
			vec1[i] = arg1.marr[i] == true ? 1d : 0d;
			vec2[i] = arg2.marr[i] == true ? 1d : 0d;
		}
		for(int i=0; i<m; i++) {
			res.marr[i] = vectorMult(vec1, vec2);
			vec1 = vectorCyckleShift(vec1);
			vec2 = vectorCyckleShift(vec2);
		}
		return res;
	}
	
	private static double[] vectorCyckleShift(double[] vec) {
		double t = vec[0];
		for(int i=0; i<m-1; i++) {
			vec[i] = vec[i+1];
		}
		vec[m-1] = t;
		return vec;
	}
	
	public static boolean vectorMult(double[] arg1, double arg2[]) throws Exception {
		RealMatrix u = MatrixUtils.createRowRealMatrix(arg1);
		RealMatrix v = MatrixUtils.createRowRealMatrix(arg2).transpose();
		RealMatrix res_matrix = u.multiply(lambda).multiply(v);
		/*System.out.println("u");
		printMatrix(u);
		System.out.println("\nlambda:");
		printMatrix(lambda);
		System.out.println("\nv");
		printMatrix(v);
		System.out.println("\nu*lambda");
		printMatrix(u.multiply(lambda));
		System.out.println("\nu*lambda*v");
		printMatrix(u.multiply(lambda).multiply(v));*/
		if(res_matrix.getColumnDimension() != 1 || res_matrix.getRowDimension() != 1 ) {
			throw new Exception("Mistake in vectorMult: invalid result FORM: matrix " + res_matrix.getRowDimension() + "x" + res_matrix.getColumnDimension());
		}
		res_matrix.setEntry(0, 0, (res_matrix.getEntry(0,0) % 2d)); //����� �� ������ 2
		switch((int)res_matrix.getEntry(0, 0)) {
			case 1:
				return true;
			case 0:
				return false;
			default:
				throw new Exception("Mistake in vectorMult: invalid result VALUE: " + res_matrix.getEntry(0, 0));
		}
	}
	
	public static void printMatrix(RealMatrix matrix) {
		for( int i = 0; i< matrix.getRowDimension(); i++) { 
			for(int j = 0; j< matrix.getColumnDimension(); j++) {
				System.out.print(matrix.getEntry(i, j) + "  ");
			}
			System.out.println();
		}
	}
	
	public static void testMultiplication() throws IllegalArgumentException, Exception {
		int oldm = m;
		boolean failDetector = false;
		//������ �� �������� 3.10.2016
		m = 3;
		NBGal arg1 = new NBGal("110"), arg2 = new NBGal("011");
		//System.out.println(multiply(arg1, arg2).toString());
		if(multiply(arg1, arg2).toString().equals("001") == false) {
			System.out.println("Multiplication test failed on step 1.");
			failDetector = true;
		}
		if(failDetector != true) System.out.println("Multiplication test passed.");
		m = oldm;
		
	}
	
	public NBGal inverse() throws Exception {
		NBGal res = this, a = this;
		int k = 1;
		//System.out.println("k = 1");
		//NBGal res_pow_k = res;
		String t = Integer.toBinaryString(m-1);
		//while (t.charAt(0) == '0') t = t.substring(1);
		//System.out.println("t = " + t);
		for(int i=1; i<t.length(); i++) {
			//System.out.println("i = " + i);
			//System.out.println("b = b*b^2^" + k);
			res = multiply(res, res.pow2Powk(k));
			//System.out.println(res.toString());
			k *= 2;
			//System.out.println("k = " + k);
			if(t.charAt(i) == '1') {
				//System.out.println("res = a*b^2");	
				res = multiply(res.riseToPow2(), a);
				//System.out.println(res.toString());
				k += 1;
				//System.out.println("k = " + k);
			}
		}
		res = res.riseToPow2();
		/*if(multiply(res, a).toString() != new NBGal("1").toString()) {
			System.out.println("res = " + res);
			throw new Exception("Mistake in inverse result: " + multiply(res, a).toString() + " != 1");
		}*/
		return res;
	}
	
	public NBGal pow2Powk(int k) {
		NBGal res = this;
		for(int i=0; i<k; i++) res = res.riseToPow2();
		return res;
	}
	
	public static void testInverse() throws IllegalArgumentException, Exception {
		boolean failDetector = false;
		setM(131);
		NBGal b = new NBGal("1011010011101101010011111111111100001011111011110110101000011101011100110101001101110101");
		/*System.out.println("b^2 = " + b.riseToPow2().toString());
		System.out.println("b^2 * b = " + multiply(b.pow2Powk(1), b));
		System.out.println("a^3 = " + b.pow(3).toString());
		System.out.println(multiply(b.riseToPow2(), b));
		System.out.println(b.riseToPow2().toString() + " " + b.pow(2).toString());*/

		NBGal b_inv = b.inverse();
		if(!multiply(b, b_inv).toString().equals(NBGal.one().toString())) {
			//System.out.println("b = " + b);
			//System.out.println("b^-1 = " + b_inv);
			//System.out.println(multiply(b, b_inv).toString());
			//System.out.println(one().toString());
			System.out.println("Test Inverse failed: mistake in inverse result: " + multiply(b, b_inv).toString() + " != 1...1");
			failDetector = true;
		}
		if(failDetector == false) System.out.println("Inverse test passed.");
		setM(131);
	}
	
	public NBGal pow(int k) throws Exception {
		if(k > Math.pow(2, (double)m) - 1) k = k % (int)Math.pow(2, (double)m) - 1;
		NBGal res = this;
		for(int i=0; i<k; i++) res = multiply(res, this);
		return res;
	}
	
}
