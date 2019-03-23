package main;

public class Gal {
	/*
	 * ¬ариант 9
	 * ѕоле m=283
	 * p = x^283 + x^26 + x^9 + x + 1
	 */
	boolean marr[];
	static int m = 283;
	Gal (String input) throws IllegalArgumentException {
		if (input.length() > m) throw new IllegalArgumentException("Input length > m");
		while(input.length() < m) input = "0".concat(input);
		marr = new boolean[m];
		for (int i=0; i<m; i++) {
			if (input.charAt(i) != '0' && input.charAt(i) != '1') {
				throw new IllegalArgumentException("Illegal character in input string");
			}
			else {
				marr[i] = input.charAt(i)=='0' ? false : true;
			}
		}
	}
	
	Gal (int[] input) throws IllegalArgumentException {
		marr = new boolean[m];
		for(int i=0; i < input.length; i++ ) {
			marr[Gal.m - input[i] - 1] = true;
		}
	}
	
	public String toString() {
		String res = "";
		for(int i=0; i<this.marr.length; i++) {
			res = res.concat(this.marr[i]==false ? "0" : "1");
		}
		while(res.charAt(0)=='0' && res.length()>1) res = res.substring(1);
		return res;
	}
	
	public String toPolyString() {
		String res = "";
		String t = "";
		for(int i=0; i<this.marr.length; i++) {
			if (this.marr[this.marr.length-i-1] == true) {
				t = "x^" + i + " + ";
				res = t.concat(res);
			}
		}
		if (res.length() > 0) res = res.substring(0, res.length()-3);
		return res;
	}
	
	static boolean madd(boolean a, boolean b) {
		/*if (a==true && b==true) return false;
		if (a==false && b == false) return false;
		else return true;*/
		return a ^ b;
	}
	
	static Gal add(Gal a, Gal b) { 
		/*
		//может складывать любые массивы булеанов (согласно правилам madd)
		if (a.marr.length < b.marr.length) { Gal t = a; a = b; b = t; }
		//System.out.println("a = " + a.toString() + ", b = " + b.toString());
		//System.out.println(a.marr.length +" "+ b.marr.length);
		Gal res = new Gal("");
		if (a.marr.length > Gal.m) res.marr = new boolean[a.marr.length];
		for(int i=0; i<a.marr.length-b.marr.length; i++) {
			res.marr[i] = a.marr[i];
		}
		for(int i=a.marr.length-b.marr.length; i<a.marr.length; i++) {
			//System.out.println(i);
			res.marr[i] = Gal.madd(a.marr[i], b.marr[i-a.marr.length+b.marr.length]);
		}
		return res;
		*/
		Gal res = new Gal("");
		for(int i=0; i<Gal.m; i++) {
			//System.out.println(i);
			res.marr[i] = a.marr[i] ^ b.marr[i];
		}
		return res;
	}
	
	Gal shift(int k) {
		String str = "";
		Gal res;
		if (k>=0) {
			for(int i=0; i<k; i++) str = str.concat("0");
			try {
				res = new Gal(this.toString().concat(str));
			} catch (IllegalArgumentException e) {
				boolean[] t = new boolean[Gal.m + k];
				//System.out.println("Gal.m + k = " + (Gal.m+k)+ " this.marr.length = " + this.marr.length);
				for(int i=0; i<this.marr.length; i++) { t[i] = this.marr[i]; /*System.out.println("i= " + i + ", t[i] = " + t[i]);*/ }
				for(int i=this.marr.length; i<t.length; i++) t[i] = false;
				res = Gal.mod(t);
			}
		}
		else {
			System.out.println("shift argument < 0 !");
			res = new Gal(this.toString().substring(0, Gal.m-k));
		}
		return res;
	}
	
	static Gal mod(boolean[] a) {
		//p.marr[0]= true; p.marr[256] = true; p.marr[273] = true; p.marr[281]=true; p.marr[282] = true;
		Gal res = new Gal("");
		int[] t = {0, 257, 274, 282, 283}; //281, 282?
		if (a.length >= Gal.m) {
			//System.out.println(a.length-Gal.m);
			for(int i=0; i<a.length-Gal.m; i++) {
				//System.out.println("hey" + i);
				if (a[i] == true) {
					for(int j: t) {
						a[i+j] = a[i+j] ^ true;
						//System.out.println((i+j) + " hey kappa " + (a[i+j]));
					}
				}
			}
		}
		//for(int i=0; i<a.length; i++) System.out.println("a: + " + i + " " + a[i]);
		for(int i=0; i<a.length-Gal.m; i++) if(a[i] == true) System.out.println("ACHTUNG in mod func");
		for(int i=0; i<Gal.m; i++) {
			//System.out.println(i + " " + (a.length - i - 1));
			res.marr[Gal.m - i - 1] = a[a.length - i - 1];
			//System.out.println("a: " + a[Gal.m - i - 1]);
		}
		return res;
	}
	
	static Gal mul(Gal a, Gal b){
		/*Gal p = new Gal("");
		p.marr[0]= true; p.marr[256] = true; p.marr[273] = true; p.marr[281]=true; p.marr[282] = true; */
		Gal res = new Gal("");
		for(int i=Gal.m-1; i>=0; i--) {
			//System.out.println("kappa");
			//System.out.println(b.marr[i]);
			if (b.marr[i] == true) {
				//System.out.println(a.shift(Gal.m-i-1).toString());
				res = Gal.add(res, a.shift(Gal.m-i-1));
				//System.out.println("res = " + res.toString());
			}	
		}
		/*for(int i=0; i<res.marr.length - Gal.m; i++) {
			if(res.marr[i]==true) {
				res = Gal.add(res, p.shift(Gal.m-i));
			}
		}*/
		return res;
	}
	
	public Gal pow2() {
		//System.out.println(this.toPolyString());
		//for(int i=200; i<Gal.m; i++) System.out.println(this.marr[i]);
		boolean[] a = new boolean[Gal.m * 2];
		for(int i=0; i<a.length; i++) a[i] = false;
		for(int i=Gal.m-1; i>=0; i--) {
			//if (this.marr[i] == true) System.out.println(i + " a[" + 2*i + "] = " + this.marr[i]);
			a[2*i+1] = this.marr[i];
		}
		//for(int i=0; i<Gal.m; i++) System.out.println(a[i]);
		Gal res = Gal.mod(a);
		return res;
	}
	
	public Gal pow(/*int*/Myr k) throws IllegalArgumentException {
		/*if(k==0) return new Gal("1");
		//if(k<0) throw new IllegalArgumentException("возведение в отрицательную степень не предусмотрено");
		boolean[] a = new boolean[Gal.m * k];
		//for(int i=0; i<a.length; i++) a[i] = false;
		for(int i=Gal.m-1; i>=0; i--) {
			a[k*i+(k-1)] = this.marr[i];
		}
		Gal res = Gal.mod(a);
		return res;*/
		
		/*Myr result = new Myr("");
		int ressize = A.size * B.size;
		result.marr = new int[ressize];
		String b = B.toBinString();
		result.marr[0] = 1;
		for(int i=b.length()-1; i>=0; i--) {
			if(b.charAt(i) == '1') {
				result = Myr.LongMul(result, A);
			}
			A = Myr.LongMul(A, A);
		}
		return result;*/
		String b = /*Integer.toBinaryString*/k.toBinString();
		Gal A = new Gal("");
		A.marr = this.marr.clone();
		Gal res = new Gal("1");
		for(int i = b.length()-1; i>=0; i--) {
			if(b.charAt(i) == '1') {
				res = Gal.mul(res, A);
			}
			A = Gal.mul(A, A);
		}
		return res;	
	}
	
	/*public Gal inverse() {
		//String t = "";
		//Myr M = new Myr("");
		Gal res = new Gal("1");
		for(int i=0; i<Gal.m-2; i++) {
			//t = Integer.toHexString((int)Math.pow(2,i));
			//M = new Myr(t);
			res = Gal.mul(res, this.pow((int)Math.pow(2,i)));
			//System.out.println(res.toString());
			System.out.print(".");
		}
		System.out.println();
		//res = res.pow((int)Math.pow(2,Gal.m) - 2); return res;
		return res.pow(2);
	}*/
	
	public Gal inverse2() {
		Gal a = new Gal("1");
		/*String t = "";
		Myr M = new Myr("");*/
		Gal res = new Gal("1");
		for(int i=0; i<Gal.m; i++) {
			a.marr[i] = this.marr[i];
		}
		for(int i=1; i<Gal.m; i++) {
			/*t = Integer.toHexString((int)Math.pow(2,i));
			M = new Myr(t);*/
			res = Gal.mul(res, a);
			a = a.pow2();
			//System.out.println(res.toString());
			//System.out.print(".");
		}
		System.out.println("");
		//res = res.pow((int)Math.pow(2,Gal.m) - 2); return res;
		return res.pow2();
	}
	
	public boolean equals(Gal b) {
		for(int i=0; i<Gal.m; i++) {
			if(this.marr[i] != b.marr[i]) return false;
		}
		return true;
	}
	
	static void testDone(String testname, boolean result) {
		String t = result==true?"passed":"failed";
		System.out.println("Gal " + testname + " testing: " + t);
	}
	
	static void testMul() {
		Gal t = new Gal("111001001000110111000001010110010001101110000011000001010110");
		// a * 0 = 0
		if (new Gal("0").equals(Gal.mul(t, new Gal("0"))) == false) {
			System.out.println("1");
			testDone("Multiplication", false);
			return;
		}
		// a * 1 = a
		if (t.equals(Gal.mul(t, new Gal("1"))) == false) {
			System.out.println("2");
			testDone("Multiplication", false);
			return;
		}
		// a << 5 = a * 100000
		if(t.shift(5).equals(Gal.mul(t, new Gal("100000"))) == false) {
			System.out.println("3");
			testDone("Multiplication", false);
			return;
		}
		// a * a = a^2
		if(Gal.mul(t, t).equals(t.pow(new Myr("2"))) == false) {
			System.out.println("4");
			testDone("Multiplication", false);
			return;
		}
		// (a + b)^2 = a^2 + b^2
		Gal k = new Gal("1111000000101011111111111111110010110000101001111");
		if(Gal.add(t, k).pow(new Myr("2")).equals(Gal.add(t.pow(new Myr("2")), k.pow(new Myr("2")))) == false) {
			System.out.println("5");
			testDone("Multiplication", false);
			return;
		}
		testDone("Multiplication", true);
	}
	
	static void testPow() {
		Gal t = new Gal("010110010001101110000011000001010110111001001000110111000001");
		//System.out.println(Myr.LongPow(new Myr("2"), new Myr(Integer.toHexString(Gal.m))).toDecString());
		//a^0 = 1
		Gal eq1, eq2;
		eq1 = new Gal("1");
		eq2 = t.pow(new Myr("0"));
		if (eq1.equals(eq2) == false) {
			System.out.println("1");
			testDone("Rising to a power", false);
			return;
		}
		//a^(2^283) = a (выполн€етс€ долго)
		eq1 = t;
		eq2 = t.pow( Myr.LongPow(new Myr("2"), new Myr(Integer.toHexString(Gal.m))) ); // a^(n-1) = 1;
		if (eq1.equals(eq2) == false) {
			System.out.println("2");
			testDone("Rising to a power", false);
			return;
		}
		//a^2 = a^2
		eq1 = t.pow2();
		eq2 = t.pow(new Myr("2"));
		if (eq1.equals(eq2) == false) {
			System.out.println("3");
			testDone("Rising to a power", false);
			return;
		}
		testDone("Rising to a power", true);
	}
	
	static void testInverse() {
		Gal t = new Gal("101100001110111111111101101110100000100100110");
		Gal eq1, eq2;
		// a^(-1) * a = 1
		eq1 = new Gal("1");
		eq2 = Gal.mul(t, t.inverse2());
		if( !eq1.equals(eq2)/* == false*/) {
			System.out.println("1");
			InverseTestFailed();
			return;
		}
		testDone("Inverse", true);
	}
	
	static void InverseTestFailed() {
		testDone("Inverse", false);
	}
	
}








