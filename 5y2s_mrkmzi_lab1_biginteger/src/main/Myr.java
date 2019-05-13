package main;

//import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class Myr {
	public static final Myr ONE = new Myr("1");
	public static final Myr ZERO = new Myr("0");
	int size;
	public int[] marr;
	
	public Myr(String input) {
		setArr(input);
	}
	
	public Myr(int[] marr) {
		this.marr = marr;
		this.size = marr.length;
	}
	
	public Myr(String input, int radix) {
		switch(radix) {
			case 2:
				if (input == "") input = "0";
				while(input.length()>0 && input.charAt(0)=='0' ) {
					if (input.length()==1) break;
					input=input.substring(1);
				}
				while (input.length() % 16 != 0) input = "0".concat(input);
				this.size = input.length()/16;
				this.marr = new int[this.size];
				for(int i=0; i<this.size; i++) {
					this.marr[i] = Integer.parseInt(input.substring(input.length()-16*(i+1), input.length()-16*i), 2);
				}
				break;
			default:
				throw new IllegalArgumentException("radix " + radix + " not supporteD");
		}

	}

	public void setArr(String input) {
		if (input == "") input = "0";
		while(input.length()>0 && input.charAt(0)=='0' ) {
			if (input.length()==1) break;
			input=input.substring(1);
		}
		while (input.length() % 4 != 0) input = "0".concat(input);
		this.size = input.length()/4;
		this.marr = new int[this.size];
		for(int i=0; i<this.size; i++) {
			this.marr[i] = Integer.parseInt(input.substring(input.length()-4*(i+1), input.length()-4*i), 16);
		}
	}
	
	/*public static int size(String input, int module) { 
		while (input.charAt(0)=='0') {
			if (input.length()==1) return 0;
			input=input.substring(1);
		}
		return (int)Math.ceil((double)input.length()/module);
	}*/
	
	public String toString() {
		String res = "";
		String temp = "";
		for(int i=0; i<this.marr.length; i++) {
			temp = Integer.toHexString(this.marr[this.marr.length-i-1]);
			while (temp.length() < 4) temp = "0".concat(temp);
			res=res.concat(temp);
		}
		while (res.length() > 0 && res.charAt(0) == '0') {
			res = res.substring(1);
		}
		if(res.length() == 0) return "0";
		return res;
	}
	
	public String toDecString() {
		String res = "";
		String temp = "";
		for(int i=0; i<this.size; i++) {
			temp = Integer.toString(this.marr[this.size-i-1]);
			while (temp.length() < 4) temp = "0".concat(temp);
			res=res.concat(temp);
		}
		while(res.length()>0 && res.charAt(0)=='0' ) {
			res=res.substring(1);
		}
		return res;
	}
	
	public String toBinString() {
		String res = "";
		String temp = "";
		for(int i=0; i<this.size; i++) {
			temp = Integer.toBinaryString(this.marr[this.size-i-1]);
			while (temp.length() < 16) temp = "0".concat(temp);
			res=res.concat(temp);
		}
		while(res.length()>1 && res.charAt(0)=='0' ) {
			res=res.substring(1);
		}
		return res.length()==0 ? "0" : res;
	}	
	
	public static Myr LongAdd(Myr A, Myr B) {
		int ressize = A.size > B.size ? A.size + 1 : B.size + 1;
		Myr result = new Myr("");
		result.marr = new int[ressize];
		result.size = ressize;
		int carry = 0;
		int temp = 0;
		for(int i=0; i< ressize; i++) {
			temp = (A.size > i ? A.marr[i] : 0) + (B.size > i ? B.marr[i] : 0) + carry;
			result.marr[i] = temp & 65535;
			carry = temp >> 16;
		}	
		return result;	
	}
	
	public static Myr LongSub(Myr A, Myr B) throws IllegalArgumentException {
		if (Myr.comp(A, B) == -1) throw new IllegalArgumentException("Wrong arguments: frist argument must be geather then the second");
		Myr result = new Myr("");
		result.marr = new int[A.size];
		result.size = A.size;
		int borrow = 0;
		int temp = 0;
		for (int i=0; i<A.size; i++) {
			temp = (A.size > i ? A.marr[i] : 0) - (B.size > i ? B.marr[i] : 0) - borrow;
			if (temp >=0) {
				result.marr[i] = temp;
				borrow=0;
			}
			else {
				temp = 0x10000 +temp; 	//12-5 = (9+1) -5 +2 = 9-5+1 +2
				result.marr[i] = temp;
				borrow = 1;
			}
		}
		return result;
		
	}
	
	public static Myr smul(Myr A, int B) throws IllegalArgumentException {
		if (B > 0xFFFF) throw new IllegalArgumentException("Illegal second argument: length should be <= 4");
		int ressize = A.size + Integer.toHexString(B).length();
		Myr result = new Myr("");
		result.marr = new int[ressize];
		result.size = ressize;
		int temp = 0;
		int carry = 0;
		for(int i=0; i<A.size+1; i++) {
			temp = i<A.size?A.marr[i]:0;
			result.marr[i]=(temp*B + carry) & 65535; // 65535 == 1111 1111 1111 1111b
			carry = (temp*B + carry) >>> 16;
		}
		return result;	 
	}
	
	public Myr shift(int power) throws java.lang.ArrayIndexOutOfBoundsException {
		//if(power<0) power=0;
		Myr result = new Myr("");
		result.marr = new int[this.size + power];
		result.size = this.size + power;
		for(int i=0; i<this.size; i++) {
			result.marr[i+power]=this.marr[i];
		}
		for(int i=0; i<power; i++) {
			result.marr[i]= 0;
		}
		return result;
	}
	
	public Myr shiftBits(int numbits) {
		if(numbits >= 0) return shiftBitsOptimized(numbits);
		
		StringBuilder bin_repr = new StringBuilder(this.toBinString());
		if(numbits >= 0) bin_repr.append(StringUtils.repeat('0', numbits));
		else if(numbits > -bin_repr.length()) bin_repr.delete(bin_repr.length()+numbits, bin_repr.length());
		else {
			/*System.out.println("numBits: " + numbits + " , bin length: " + bin_repr.length());
			throw new IllegalArgumentException("cannot shift right anymore");*/
			return Myr.ZERO;
		}
		return new Myr(bin_repr.toString(), 2);
	}
	
	public Myr shiftBitsOptimized(int numbits) {
		if(numbits >= 0) {
			Myr res = this.shift(numbits/16);
			int shift = numbits % 16;
			if (shift == 0) return res;
			if(Integer.numberOfLeadingZeros(res.marr[res.marr.length-1])-16 >= shift) {
				//System.out.println("if");
				for(int i = res.marr.length-1; i>0; i--) {
					res.marr[i] = ((res.marr[i] << shift)&0xFFFF) ^ res.marr[i-1] >> (16 - shift); // TODO debug
				}
				res.marr[0] = (res.marr[0] << shift)&0xFFFF;
			} else {
				//System.out.println("else");
				int[] old_marr = res.marr;
				res.marr = new int[res.marr.length + 1];
				res.size++;
				res.marr[res.marr.length-1] = old_marr[old_marr.length-1] >> (16 - shift);
				for(int i = res.marr.length-2; i > 0; i--) {
					//System.out.println("for");
					
					res.marr[i] = ((old_marr[i] << shift)&0xFFFF) ^  (old_marr[i-1] >> (16-shift));
				}
				res.marr[0] = (old_marr[0] << shift)&0xFFFF;
			}
			return res;
		}
		else return shiftBits(numbits); //TODO right shift
	}
	
	
	/*multiplies by 16
	 * public Myr shiftByOne() {
		return Myr.smul(new Myr(this.toString()),0x10);
	}*/
		
	public static Myr LongMul(Myr A, Myr B) {
		Myr temp = new Myr("");
		int ressize = A.size + B.size;
		Myr result = new Myr("");
		result.marr = new int[ressize];
		result.size = ressize;
		for(int i=0; i<B.size; i++) {
			temp = Myr.smul(A, B.marr[i]); 
			temp = temp.shift(i);
			//result = new Myr(Myr.LongAdd(result, temp).toString());
			result = Myr.LongAdd(result, temp);
		}
		return new Myr(result.toString()); //to remove leading zeroes
	}

	public static int comp(Myr A, Myr B) {
		//if (A.size>B.size) return 1;
		//if (A.size<B.size) return -1;
		//System.out.println(A.toString()+ " A "+A.size);
		//System.out.println(B.toString()+ " B "+B.size);
		
		if (A.size >= B.size) {
			for(int i=A.size-1; i>=0; i--) {
				if(A.marr[i] > (B.size<=i ? 0 : B.marr[i])) return 1;
				if(A.marr[i] < (B.size<=i ? 0 : B.marr[i])) return -1;
			}
		}
		if (B.size >= A.size) {
			for(int i=B.size-1; i>=0; i--) {
				if(B.marr[i] > (A.size<=i ? 0 : A.marr[i])) return -1;
				if(B.marr[i] < (A.size<=i ? 0 : A.marr[i])) return 1;
			}
		}
		return 0;
	}
	
	public int compareTo(Myr B) {
		return comp(this, B);
	}
	
	//bugged
	public static Divret LongDiv(Myr A, Myr B) {
		Divret result = new Divret();
		int l1;
		int l2 = B.toBinString().length();
		Myr temp = new Myr("1");
		Myr C = new Myr("");
		Myr R = new Myr(A.toString());
		while(Myr.comp(R, B) >= 0) {
			l1 = R.toBinString().length();
			//System.out.println("R = " + R + ", R.size = " + l1 + "; B = " + B + ", B.size = " + l2);
			C = B.shiftBits(l1-l2);
			//System.out.println(R.toString());
			//System.out.println(C.toString());
			while (Myr.comp(R, C) < 0) {
				l1--;
				C = B.shiftBits(l1-l2);
				//System.out.println(C.toString());
			}
			R = Myr.LongSub(R, C);
			result.Q = new Myr(Myr.LongAdd(result.Q, temp.shiftBits(l1-l2)).toString());
			//System.out.println(result.Q.toString());
		}
		result.remainder = new Myr(R.toString());
		return result;
	}
	
	public static Myr LongPow(Myr A, Myr B) {
		Myr result = new Myr("");
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
		return result;
	}
	
	public static Myr gcdEvkl(Myr A, Myr B) { //O(loga)
		long start_timer = System.currentTimeMillis();
		if(Myr.comp(B, new Myr("0"))==0 || Myr.comp(A, new Myr("0"))==0) {
			System.out.println("evkl run time: " + (System.currentTimeMillis()-start_timer) + "ms. !Trivial case!");
			return new Myr("0");
		}
		Myr T = new Myr("");
		if(Myr.comp(A, B) < 0) {
			T = A; A = B; B = T;
		}
		do {
			//if (Myr.comp(B, A) == 1) System.out.println("ACHTUNG");
			T = B;
			B = Myr.LongDiv(A, B).remainder;
			A = T;
			//System.out.println(B.toString());
			if (Myr.comp(B, new Myr("0"))==0) {
				//System.out.println("evkl run time: " + (System.currentTimeMillis()-start_timer) + "ms.");
				return A;
			}
		} while (true);
		
	}
	
	
	static Myr gcdStein(Myr A, Myr B) { //2loga
		long start_timer = System.currentTimeMillis();
		Myr T = new Myr("");  //����������� � 3� while
		Myr d = new Myr("1");
		while (A.marr[0] % 2 == 0 && B.marr[0] % 2==0) {
			//System.out.println("1st while");
			A = A.div2();
			B = B.div2();
			d = Myr.LongMul(d, new Myr("2")); 
		}
		while(A.marr[0] % 2 == 0) {
			//System.out.println("2nd while");
			A = A.div2();
		}
		while(Myr.comp(B, new Myr("0")) != 0) {
			//System.out.println(B.toString());
			//System.out.println("3rd while");
			while(B.marr[0] % 2==0) {
				B = B.div2();
			}
			T = A;
			A = Myr.comp(A, B) < 0 ? A : B;
			B = Myr.comp(T, B) < 0 ? Myr.LongSub(B, T) : Myr.LongSub(T, B);	
		}
		T = Myr.LongMul(d, A);
		System.out.println("stein run time: " + (System.currentTimeMillis()-start_timer) + "ms.");
		return T;
	}

	Myr div2() {
		Myr T = this;
		for(int i=0; i < T.marr.length - 1; i++) {
			T.marr[i] = T.marr[i] >> 1;
			T.marr[i] += T.marr[i+1]%2 << 15;
		}
		T.marr[T.marr.length-1] = T.marr[T.marr.length-1] >> 1;
		return T;
	}

/*	static Myr lcmPrime(Myr A, Myr B) {
		if(comp(A,B) < 0) {
			Myr T = B;
			B = A;
			A = T;
		}
		Myr lcm = new Myr("1");
		ArrayList<Myr> factorA = new ArrayList();
		ArrayList<Myr> factorB = new ArrayList();
		for(Myr i = new Myr("2"); Myr.comp(i, A) < 0; i = Myr.LongAdd(i, new Myr("1"))) {
			while(Myr.comp(Myr.LongDiv(A, i).remainder, new Myr("0")) ==0) {
				factorA.add(i);
			}
			while(Myr.comp(Myr.LongDiv(B, i).remainder, new Myr("0")) ==0) {
				factorB.add(i);
			}
		}
		
	}
*/
	static Myr lcm(Myr A, Myr B) {
		return Myr.LongDiv(Myr.LongMul(A, B), Myr.gcdEvkl(A,B)).Q; // lcm(a,b) = a*b/gcd(a,b)
	}
	
	public static Myr LongAdd(Myr A, Myr B, Myr mod) {
		if (Myr.comp(A, mod) > 0) A = Myr.LongDiv(A, mod).remainder;
		if (Myr.comp(B, mod) > 0) B = Myr.LongDiv(B, mod).remainder;
		int ressize = A.size > B.size ? A.size + 1 : B.size + 1;
		Myr result = new Myr("");
		result.marr = new int[ressize];
		result.size = ressize;
		int carry = 0;
		int temp = 0;
		for(int i=0; i< ressize; i++) {
			temp = (A.size > i ? A.marr[i] : 0) + (B.size > i ? B.marr[i] : 0) + carry;
			//System.out.println(Integer.toHexString((A.size > i ? A.marr[i] : 0)) + " + " + Integer.toHexString((B.size > i ? B.marr[i] : 0)) + " + " + carry + " = " + Integer.toHexString(temp));
			result.marr[i] = temp & 65535;
			carry = temp >> 16;
		}	
		return new Myr(Myr.LongDiv(result, mod).remainder.toString());	
	}
	
	public static Myr LongSub(Myr A, Myr B, Myr mod) throws IllegalArgumentException {
		if (Myr.comp(A, B) == -1) throw new IllegalArgumentException("Wrong arguments: frist argument must be geather then the second");
		Myr result = new Myr("");
		result.marr = new int[A.size];
		result.size = A.size;
		int borrow = 0;
		int temp = 0;
		for (int i=0; i<A.size; i++) {
			temp = (A.size > i ? A.marr[i] : 0) - (B.size > i ? B.marr[i] : 0) - borrow;
			if (temp >=0) {
				result.marr[i] = temp;
				borrow=0;
			}
			else {
				temp = 0x10000 +temp; 	//12-5 = (9+1) -5 +2 = 9-5+1 +2
				result.marr[i] = temp;
				borrow = 1;
			}
		}
		return Myr.LongDiv(result, mod).remainder;
		
	}
	
	public Myr KillLD(int k) { // KillLastDigits - ��������������� ������� ��� Barrett. ���������� ������� �� ������� �������� ����� �� (2^16)^k
		Myr T = new Myr(this.toString());
		for(int i=0; i<T.size; i++) {
			if (i<T.size-k) T.marr[i] = T.marr[i+k];
			else T.marr[i] = 0;
		}
		return T;
	}
	
	/*static Myr FindMu(String a, String b) {
		while(b.length()%4!=0) {
			b="0".concat(b);
			System.out.println("findmu b");
		}
		while(a.length()<b.length()/2) {
			a="0".concat(a);
			System.out.println("findmu a");
		}
		int k = b.length();
		Myr X = Myr.LongPow(new Myr("10"), new Myr(Integer.toHexString(2*k)));
		return Myr.LongDiv(X, new Myr(a)).Q;
	}*/
	
	
	static Myr Barrett(Myr A, Myr N, Myr m) { // ����� - r = A mod N, m - ��������������� ��������������� �������� [((2^16)^2*N.size)/N]
		int k = N.size	/*(int)Math.ceil(((double)A.size)/2.0)*/;
		/*System.out.println("k = " + k);
		System.out.println("Mu = " + m);
		System.out.println("A = " + A.toString());*/
		Myr q = A.KillLD(k-1);
		//System.out.println("q= " + q.toString());
		q = Myr.LongMul(q, m);
		//System.out.println("q*m= "+q.toString());
		q = q.KillLD(k+1);
		/*System.out.println("q= "+q.toString());
		System.out.println("A= " + A.toString() + " q*N= " + Myr.LongMul(q, N) );*/
		Myr r = Myr.LongSub(A, Myr.LongMul(q, N));
		//System.out.println("r:"+r.toString());
		while (Myr.comp(r, N) >= 0) {
			//System.out.println("sub");
			//System.out.println(r.toString() + "   " + N.toString());
			r = Myr.LongSub(r, N);
		}
		return new Myr(r.toString());
	}

	public static Myr LongPowBarrett(Myr A, Myr B, Myr N) {
		String b = B.toBinString();
		Myr C = new Myr("1");
		Myr shift = new Myr("1").shift(2*B.size);
		//System.out.println("shift");
		//Myr Mu = Myr.LongDiv(shift , N).Q;
		//Myr Mu = new Myr(new BigInteger(shift.toString(), 16).divide(new BigInteger(N.toString(), 16)).toString(16));
		Myr Mu = Myr.LongDiv(shift , N).Q;
		//System.out.println("div");
		
		for(int i=0; i<b.length(); i++) {
			if(b.charAt(b.length()-i-1) == '1') {
				C = Myr.Barrett(Myr.LongMul(A, C), N, Mu);
			}
			A = Myr.Barrett(Myr.LongMul(A, A), N, Mu);
		}
		return new Myr(C.toString());
	}
	//4bee3936c723a16d22e846395eb3b7851a77b5111a9c29732fea5ce214aa4b4
	//137D3DCDC00BE0F9BC13B8CDF5378A88E22A521DD6B809700F25B2028C23E

	public Myr multiply(Myr B) {
		return LongMul(this, B);
	}

	public Myr subtract(Myr B) {
		return LongSub(this, B);
	}

	public Myr divide(Myr B) {
		return LongDiv(this, B).Q;
	}

	public Myr mod(Myr modulus) {
		return LongDiv(this, modulus).remainder;
	}
	
	/*public Myr extended_eucl(Myr a, Myr b) { //not woking
		Myr q, r, x1 = Myr.ZERO, x2 = Myr.ONE, y1 = Myr.ONE, y2 = Myr.ZERO;
		Myr x, y, d;
		while(b.compareTo(Myr.ZERO) > 0) {
			System.out.println("ext iter");
			q = a.divide(b);
			r = a.subtract(q.multiply(b));
			x = x2.subtract(q.multiply(x1));
			y = y2.subtract(q.multiply(y1));
			a = b;
			b = r;
			x2 = x1; x1 = x; y2 = y1; y1 = y;
			if(b.compareTo(a.divide(b).multiply(b)) < 0) break;
		}
		x = x2; y = y2; d = a;
		return x;
	}*/

	public Myr modInverse(Myr m) {
		/*Myr a = new Myr(this.marr);
		Myr m0 = new Myr(m.marr);
		Myr y = Myr.ZERO, x = Myr.ONE;
		
		while(a.compareTo(Myr.ONE) > 0) {
			System.out.println("iter");
			Myr q = a.divide(m);
			Myr t = m;
			
			m = a.mod(m);
			a = t;
			t = y;
			
			try {
				y = x.subtract(q.multiply(y));
			} catch(IllegalArgumentException e) {
				return x;
			}
			x = t;
		}
		return x;*/
		return LongPowBarrett(this, m.subtract(new Myr("2")), m);
	}

	public Myr and(Myr mask) {
		int[] res_marr = new int[Math.min(this.marr.length, mask.marr.length)];
		for(int i = 0; i < res_marr.length ; i++) {
			res_marr[i] = this.marr[i] & mask.marr[i];
		}
		return new Myr(res_marr); 
	}

	public Myr add(Myr B) {
		return Myr.LongAdd(this, B);
	}

	public boolean testBit(int i) {
		String binr = this.toBinString();
		return this.toBinString().charAt(binr.length() - i - 1) == '0' ? false : true;
	}
	
	public boolean equals(Myr B) {
		if(this.marr.length == B.marr.length) {
			//System.out.println("len ok");
			for(int i = 0; i < this.marr.length; i++) {
				if(this.marr[i] != B.marr[i]) return false;
			}
		}
		return this.toString().equals(B.toString());
	}
	
	public Myr pow(Myr B) {
		return LongPow(this, B);
	}


}


