package main;

/*********************************
   Компьютерный практикум №3
   ФІ-43, А. Малышко, В. Лещенко
 *******************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RandSeqGen {
 //генератор псевдослучайных последовательностей на основе линейных регистров сдвига (генератор Джиффи)
	//вариант 8
	static final double ta = 2.326; //квантиль нормального распределения уровня 99% (для a=0.01)
	static final double tb = 6.0093; //квантиль нормального распределения уровня (1 - 2^-30)*100% = 99.9999999068% (https://planetcalc.ru/4986/)
	//N* = (4.028+2*tb)^2 ~ 257.49 ~ 258
	static int N1 = 258, N2 = 265; //для n= 30, 31;
	//static double C1 = 80.67, C2 = 82.54;
	static double C1 = /*71*/60/*65 - result*/, C2 = 77;
	static int n1 = 30, n2 = 31, n3 = 32;
	
	static int testLength = 100;
	static int[] 
			l1Poly = {0, 24, 26, 29, 30},
			l2Poly = {0, 28, 31},
			l3Poly = {0, 25, 27, 29, 30, 31, 32};
	static int l1len = 30;
	static int l2len = 31;
	static int l3len = 32;
	static String szTest = "1010111111110000001100010111000100010101100100100011011011111101101000100001001110001010110010101001";
	static String sz = "10101010100010000001001011001101001011010110011011011010001000111101101000000010000110110011110100011100111101111111111001000001110100000100111111101010010010001111101011010110001001111111011100110111101000010010110101010101001100010100111001101111100000100011101011010101000001111110101011101010010101000100000101000000001111100011101110100100111000010010011101001011101001000011110010010011001101100100000110101010100010011110101000000000010010101100010111000101110100000101001000101111010110110000111011011011001111000010011010001011111010101111001110011111001111011100111000011000111100011110110111110100000110011001000100101011111011101110111011001111011110010110010111011000000111100110111010011000010001101011110011111010111100101110100000110101001101101110010011000111110000100110001100010101001011000100101001100101110101111100111111001100100101010010100010000011110010100010001101011011001111110101001001001011010001011000001000000001011110110100000000100110000010110011000000011001111001110011100111101000011101100101101110011001110111110100101101011000100010010010110110010000100110100111000101110010010100101011011011110000001101100010000101011000000001010110000000010101011100110000100101011101100000001111101101000000001100011011001010101000111110111100001101111101111100101100111000000111010100100100010110111011000110000001101011011011011001010010011010100101111010111000110100101010011111100101110101011001111010101101100001001001101110000110100100000001000001110101010010000011111111001000101101011101101011100110010101011100111111101000111111001101001001111101110100111000110001111001100100100001011101001011001100100111010010000010010100011101100010001000110000101011100101100100111010001110001001010110010011111111101010010101101101011001100001000011001000100110110010111000011101011111000100100111101110111010111010000100110010100001010000111011010011010011100111001101100111111110100010001101101010000011001010001110110000111101001110001110010010100001111000000010101110111111011010001101001101010000000011010010101000011001";
	//for dummies:
	//static String szdum = "10101010100010000001001011001101001011010110011011011010001000111101101000000010000110110011110100011100111101111111111001000001110100000100111111101010010010001111101011010110001001111111011100110111101000010010110101010101001100010100111001101111100000100011101011010101000001111110101011101010010101000100000101000000001111100011101110100100111000010010011101001011101001000011110010010011001101100100000110101010100010011110101000000000010010101100010111000101110100000101001000101111010110110000111011011011001111000010011010001011111010101111001110011111001111011100111000011000111100011110110111110100000110011001000100101011111011101110111011001111011110010110010111011000000111100110111010011000010001101011110011111010111100101110100000110101001101101110010011000111110000100110001100010101001011000100101001100101110101111100111111001100100101010010100010000011110010100010001101011011001111110101001001001011010001011000001000000001011110110100000000100110000010110011000000011001111001110011100111101000011101100101101110011001110111110100101101011000100010010010110110010000100110100111000101110010010100101011011011110000001101100010000101011000000001010110000000010101011100110000100101011101100000001111101101000000001100011011001010101000111110111100001101111101111100101100111000000111010100100100010110111011000110000001101011011011011001010010011010100101111010111000110100101010011111100101110101011001111010101101100001001001101110000110100100000001000001110101010010000011111111001000101101011101101011100110010101011100111111101000111111001101001001111101110100111000110001111001100100100001011101001011001100100111010010000010010100011101100010001000110000101011100101100100111010001110001001010110010011111111101010010101101101011001100001000011001000100110110010111000011101011111000100100111101110111010111010000100110010100001010000111011010011010011100111001101100111111110100010001101101010000011001010001110110000111101001110001110010010100001111000000010101110111111011010001101001101010000000011010010101000011001";
	static String szdum = "10101010100010000001001011001101001011010110011011011010001000111101101000000010000110110011110100011100111101111111111001000001110100000100111111101010010010001111101011010110001001111111011100110111101000010010110101010101001100010100111001101111100000100011101011010101000001111110101011101010010101000100000101000000001111100011101110100100111000010010011101001011101001000011110010010011001101100100000110101010100010011110101000000000010010101100010111000101110100000101001000101111010110110000111011011011001111000010011010001011111010101111001110011111001111011100111000011000111100011110110111110100000110011001000100101011111011101110111011001111011110010110010111011000000111100110111010011000010001101011110011111010111100101110100000110101001101101110010011000111110000100110001100010101001011000100101001100101110101111100111111001100100101010010100010000011110010100010001101011011001111110101001001001011010001011000001000000001011110110100000000100110000010110011000000011001111001110011100111101000011101100101101110011001110111110100101101011000100010010010110110010000100110100111000101110010010100101011011011110000001101100010000101011000000001010110000000010101011100110000100101011101100000001111101101000000001100011011001010101000111110111100001101111101111100101100111000000111010100100100010110111011000110000001101011011011011001010010011010100101111010111000110100101010011111100101110101011001111010101101100001001001101110000110100100000001000001110101010010000011111111001000101101011101101011100110010101011100111111101000111111001101001001111101110100111000110001111001100100100001011101001011001100100111010010000010010100011101100010001000110000101011100101100100111010001110001001010110010011111111101010010101101101011001100001000011001000100110110010111000011101011111000100100111101110111010111010000100110010100001010000111011010011010011100111001101100111111110100010001101101010000011001010001110110000111101001110001110010010100001111000000010101110111111011010001101001101010000000011010010101000011001";
	static int[] 
			l1dumPoly = {0, 22, 25},
			l2dumPoly = {0, 20, 24, 25, 26},
			l3dumPoly = {0, 22, 25, 26, 27};
	static int l1dumlen = 25;
	static int l2dumlen = 26;
	static int l3dumlen = 27;
	static int dN1 = 221, dN2 = 229; //для n= 25, 26;
	static double dC1 = 70.075, dC2 = /*72.2825*/87.01;
	static boolean[] z;
	static String[] result = { //результат для 8 упрощённого варианта
		"1110111000001010000100111",
		"00110010111100010001001010",
		"100110110111110101100110011"
	};
	static String[] key = { //для тестов и дебага
			"1000110101111010001100011",//1010010111111000001100011
			"10101110101001010111000101",
     	   //1010111111110000001100010111000	z
		   //001000111101111101000000100 raw[0]
		   //000000010101010101000000000 raw[1]
			"110011010111010101010001001"
			 
	};
	
	
	public static void main(String[] args) {
		long start_timer = System.currentTimeMillis();
		/*try {
			L3Breaker.filter(L1Breaker.filter(), L2Breaker.filter());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//testBreak();
		//testL3Breaker();
		//testTime();
		//L3Breaker.testCheck();
		
		boolean[][] b = filter();
		System.out.println("result:");
		System.out.println(boolArrToString(b[0]));
		System.out.println(boolArrToString(b[1]));
		System.out.println(boolArrToString(b[2]));
		
		//checkResult(result);
		

		System.out.println("time taken: " + (System.currentTimeMillis() - start_timer)/60000 + " min");
	}
	
	static void init(boolean dum) { //dum == true -> упрошённый вариант
		if(!dum) {
			//sz = sz.substring(0, N1); //нам не нужно больше символов для заданой точности
			z = sToBA(sz);
		}
		else {
			//szdum = szdum.substring(0, dN1);
			z = sToBA(szdum);
		}
	}
	
	static boolean[][] filter() { 
		ArrayList<boolean[]> passedL1 = L1Breaker.filter();
		for(boolean[] t : passedL1) System.out.println(boolArrToString(t));
		ArrayList<boolean[]> passedL2 = L2Breaker.filter();
		
		//ArrayList<boolean[]> passedL1 = new ArrayList<>();
		//ArrayList<boolean[]> passedL2 = new ArrayList<>();
		/*passedL1.add(sToBA("1110101000001010000101110"));
		passedL1.add(sToBA("1110111000001010000100111"));*/
		//passedL2.add(sToBA("01101000100111110011011011"));
		//passedL2.add(sToBA("10101001101010010001000010"));
		
		/*System.out.println("sizes:\t" + passedL1.size() + "\t" +passedL2.size());
		//boolean[] t;
		ArrayList<boolean[]> res = new ArrayList<>();
		LR l1 = new LR(new boolean[l1len], l1Poly), l2 = new LR(new boolean[l2len], l2Poly);
		for(int l1iter=0; l1iter<passedL1.size(); l1iter++) {
			l1.init = passedL1.get(l1iter);
			boolean[] x = l1.generSeq(z.length);
			LR l3 = new LR(new boolean[l3len], l3Poly);
			for(int l2iter=0; l2iter<passedL2.size(); l2iter++) {
				//printBoolArr(jiffie(passedL1.get(i), passedL2.get(j), z, z.length));
				l2.init = passedL2.get(l2iter);
				boolean[] y = l2.generSeq(z.length);
				boolean[] e = new boolean[(int)Math.pow(2, l3len)];
				for(int i=1; i<Math.pow(2, (int)Math.pow(2, l3len)); i++) {
					for(int j=l3len-1; j>=0; j--) { //от конца к началу, если 0 то меняем на 1 и заканчиваем, если 1 - меняем на 0 и продолжаем
						if(e[j]==false) {
							//change = j;
							e[j] = true;
							break;
						}
						e[j] = false;
					}
					l3.init = e;
					//t = l3.generSeq(z.length);
					
				}
			}
		}*/
		boolean[][] res = null;
		try {
			res = L3Breaker.filter(passedL1, passedL2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LR l1 = new LR(res[0], l1dumPoly);
		LR l2 = new LR(res[1], l2dumPoly);
		LR l3 = new LR(res[2], l3dumPoly);
		System.out.println(boolArrToString(jiffie(l1, l2, l3, N1)));
		System.out.println(sz);
		return res;
	}
	
	static List<boolean[][]> filterDum() {
		List<boolean[][]> res = new ArrayList<>();
		LR l1 = new LR(new boolean[l1dumlen], l1dumPoly);
		LR l2 = new LR(new boolean[l2dumlen], l2dumPoly);
		//ArrayList<boolean[]> l1fil = filter(l1, dN1, dC1);
		ArrayList<boolean[]> l2fil = filter(l2, dN2, dC2);
		return res;
	}
	
	static void checkResult(String[] result) {
		z = sToBA(sz);
		LR l1 = new LR(sToBA(result[0]), l1dumPoly);
		LR l2 = new LR(sToBA(result[1]), l2dumPoly);
		LR l3 = new LR(sToBA(result[2]), l3dumPoly);
		boolean[] t = jiffie(l1, l2, l3, z.length);
		System.out.println(sz);
		System.out.println(boolArrToString(t));
		
	}
	
	static ArrayList<boolean[]> filter(LR l, int N, double C) {
		int n = l.reglen;
		long start_timer = System.currentTimeMillis();
		ArrayList<boolean[]> passed = new ArrayList<>();
		int R = 0;
		boolean[] x = new boolean[n];
		boolean[] y = new boolean[N];
		//LR l1 =/*new LR(new boolean[n1], l1Poly)*/ ;
		//int change;
		for(int i=1; i<Math.pow(2, n); i++) {
			for(int j=n-1; j>=0; j--) { //от конца к началу, если 0 то меняем на 1 и заканчиваем, если 1 - меняем на 0 и продолжаем
				if(x[j]==false) {
					//change = j;
					x[j] = true;
					break;
				}
				x[j] = false;
			}
			l.init = x;
			y = l.generSeq(N);
			for(int j=0; j<y.length; j++) {
				//if(y[j]==z[j]) R++;
				if(y[j] ^ z[j]) R++;
			}
			//if(R>=C) {
			if(R<C) {
				passed.add(x);
				System.out.println("possible answer found:\t" + R + " < " + C + "\t" + Integer.toBinaryString(i));
			}
			//printBoolArr(y);
			//System.out.println(R + " " + C);
			R = 0;
			if(i%(int)(Math.pow(2, n)/100) == 0) System.out.println((System.currentTimeMillis() - start_timer)/1000 + " s");;
		}
		return passed;
	}
	
	
	
	public static boolean[] stringToBoolArr(String s) throws IllegalArgumentException {
		//System.out.println("stoba: " + s);
		boolean[] b = new boolean[s.length()];
		for(int i=0; i<b.length; i++) {
			if(s.charAt(i) == '0') b[i] = false;
			else if(s.charAt(i) == '1') b[i] = true;
			else throw new IllegalArgumentException("String should contain '0' or '1'. Found '" + s.charAt(i)+ "'.");
		}
		return b;
	}

	static boolean[] jiffie() {
		//return jiffie(new LR(sToBA(key[0]), l1Poly), new LR(sToBA(key[1]), l2Poly), new LR(sToBA(key[2]), l3Poly), testLength);
		return jiffie(new LR(sToBA(key[0]), l1dumPoly), new LR(sToBA(key[1]), l2dumPoly), new LR(sToBA(key[2]), l3dumPoly), testLength);
	}
	
	static boolean[] jiffie(LR l1, LR l2, LR l3,/* boolean[][] key,*/ int seqlen) { //key - массив из трех начальных заполнений регистров
		//результат работы L1:
		boolean[] F = new boolean[seqlen];
		boolean[] x = l1.generSeq(seqlen);
		boolean[] y = l2.generSeq(seqlen);
		boolean[] s = l3.generSeq(seqlen);
		for(int i=0; i<F.length; i++) {
			//System.out.println(mulBool(x[i], s[i]) + " " + mulBool(s[i] ^ true, y[i]));
			//System.out.println(mulBool(x[i], y[i]) ^ mulBool(s[i] ^ true, y[i]));
			F[i] = mulBool(x[i], s[i]) ^ mulBool(s[i] ^ true, y[i]);
		}
		return F;
	}
	
	static void testJiffie() {
		key[0] = "101010111010100110101011110101";
		key[1] = "0000000000000000000000000000000";
		key[2] = "00000000000000000000000000000000";
		//key[2] = "11111111111111111111111111111111";
		//boolean[] F = jiffie();
		//for(int i=0; i<F.length; i++) System.out.println(F[i]);
		printBoolArr(jiffie());
		
		key[0] = "100011010111101000110001101010";
		key[1] = "1010111010100101011100010101011";
		key[2] = "11001101011101010101000100100110";
	}
	
	static void testBreak() {
		System.out.println(key[0].length() + "\t" + key[1].length() + "\t" + key[2].length());
		System.out.println(Integer.parseInt(key[0], 2));
		System.out.println(Integer.parseInt(key[1], 2));
		boolean[] testZ = jiffie(new LR(sToBA(key[0]), l1dumPoly), new LR(sToBA(key[1]), l2dumPoly), new LR(sToBA(key[2]), l3dumPoly), testLength);
		szTest = boolArrToString(testZ);
		L1Breaker.C = 24;
		L2Breaker.C = 25;
		L1Breaker.N = testLength;
		L2Breaker.N = testLength;
		L1Breaker.sz = szTest;
		L2Breaker.sz = szTest;
		L3Breaker.sz = szTest;
		
		try {
			L3Breaker.filter(L1Breaker.filter(), L2Breaker.filter());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void testTime() {
		long stime = System.currentTimeMillis();
		LR l = new LR(sToBA(key[0]), l1dumPoly);
		int llen = key[0].length();
		LR lr3 = l;
		boolean[] z = null;
		for(int i=0; i</*(int)Math.pow(2, key[0].length())*/10; i++) z=l.generSeq(220); //23416 ms
		System.out.println(System.currentTimeMillis() - stime);
		boolean[] x = new boolean[key[0].length()];
		for(int j=llen-1; j>=0; j--) { //от конца к началу, если 0 то меняем на 1 и заканчиваем, если 1 - меняем на 0 и продолжаем
			if(x[j]==false) {
				x[j] = true;
				break;
			}
			x[j] = false;
		}
		//тело перебора: проверяем правильность, сравнивая сгенерированную Джиффи последовательность с эталонной z:
		lr3.init = x;
		//System.out.println(x.length + "\t" + lr3.reglen);
		int zlen = z.length;
		boolean[] l3 = lr3.generSeq(zlen);
		
		for(int k=0; k<zlen; k++) {
			//[i]?
			//boolean t = (((l3[k]&x[k]) ^ ((!l3[k])&!x[k])) != l3[k] );
		}
		System.out.println(System.currentTimeMillis() - stime);
	}
	
	static void testL3Breaker() {
		int testLength = 100;
		String[] key = {
				"1000110101111010001100011",
				"10101110101001010111000101",
				"110011010111010101010001001"
		};
		int[] 
		l1poly = {0, 22, 25},
		l2poly = {0, 20, 24, 25, 26},
		l3poly = {0, 22, 25, 26, 27};
		boolean[] testZ = jiffie(new LR(sToBA(key[0]), l1poly), new LR(sToBA(key[1]), l2poly), new LR(sToBA(key[2]), l3poly), testLength);
		ArrayList<boolean[]> l1res = new ArrayList<>(), l2res = new ArrayList<>();
		l1res.add(sToBA(key[0]));
		l2res.add(sToBA(key[1]));
		try {
			L3Breaker.filter(l1res, l2res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean mulBool(boolean a, boolean b) {
		return ((a==true)&&(b==true)) ? true : false;
	}
	
	static boolean[] sToBA(String a) throws IllegalArgumentException {
		/*boolean[] res = new boolean[a.length()];
		for(int i=0; i<a.length(); i++) {
			if (a.charAt(i)=='1') res[i] = true;
			else if(a.charAt(i) == '0') res[i] = false;
			else throw new IllegalArgumentException("Illegal symbol in arr: " + a.charAt(i));
		}
		return res;*/
		return stringToBoolArr(a);
	}
	
	public static String boolArrToString(boolean[] b) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<b.length; i++) {
			//System.out.print(b[i]==false?'0':'1');
			s.append(b[i] == false ? '0' : '1');
		}
		//System.out.println();
		return s.toString();
	}
	
	static void printBoolArr(boolean[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i]?1:0);
			//if((i+1)%100 == 0) System.out.println();
		}
		System.out.println();
	}
	
	public static void saveBoolArr(boolean[][] arr, String filename) throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(arr);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("looks like file not found:\n" + e.getMessage());
		}
	}
	
	
	public static boolean[][] retrieveBoolArr(String filename) {
		boolean[][] arr = null;
		try {
	         FileInputStream fileIn = new FileInputStream(filename);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         arr = (boolean[][])in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Map class not found");
	         c.printStackTrace();
	         return null;
	      }
		return arr;
	}
	
	public static String lpad(String s, int n) {
		StringBuilder s1 = new StringBuilder();
		for(int i=0; i<n-s.length(); i++) {
			s1.append('0');
		}
		s1.append(s);
	    return s1.toString();  
	}
}
