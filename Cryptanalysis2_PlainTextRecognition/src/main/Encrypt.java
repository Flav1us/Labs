package main;

import java.util.Random;

public class Encrypt {
	static char[] alphabet = Main.alphabet;
	
	static String[] bigrams = new String[alphabet.length*alphabet.length];
	
	static {
		for(int i =0; i<bigrams.length; i++) {
			bigrams[i] = String.valueOf(alphabet[i/alphabet.length]).concat(String.valueOf(alphabet[i%alphabet.length]));
		//System.out.print(bigrams[i]);
		}
	}

	/*public static char[] affine(char[] cbuf, int a, int b) {
		char[] enc = new char[cbuf.length];
		for(int i = 0; i < enc.length; i++) {
			//System.out.println((pos(cbuf[i]) * a + b) % alphabet.length);
			enc[i] = alphabet[(pos(cbuf[i]) * a + b) % alphabet.length];
			//System.out.print(enc[i]);
		}
		return enc;
	}*/
	
	public static char[] affine(char[] cbuf) {
		Random rand = new Random();
		int a = rand.nextInt(bigrams.length);
		int b = rand.nextInt(bigrams.length);
		assert (cbuf.length%2==0);
		char[] res = new char[cbuf.length];
		for(int i = 0; i<cbuf.length; i+=2) {
			String bi = String.valueOf(cbuf[i]).concat(String.valueOf(cbuf[i+1]));
			int pos = pos(bi);
			res[i] =  bigrams[mod_bi(pos*a+b)].charAt(0);
			res[i+1] =  bigrams[mod_bi(pos*a+b)].charAt(1);
		}
		return res;
	}
	
	public static char[] random(char[] cbuf) {
		char[] res = new char[cbuf.length];
		Random r = new Random();
		for(int i =0; i<res.length; i++) {
			res[i] = alphabet[mod32(r.nextInt())];
		}
		return res;
	}
	
	public static char[] vigenere(char[] cbuff) {
		Random rand = new Random();
		int r = 10;
		int[] key = new int[r];
		for(int i =0; i<key.length;i++) {
			key[i]=mod32(rand.nextInt());
		}
		char[] enc = new char[cbuff.length];
		for(int i=0; i<enc.length;i++) {
			enc[i]=alphabet[mod32(pos(cbuff[i]) + key[i%r])];
		}
		return enc;
	}
	
	public static char[] double_random(char[] cbuff) {
		Random rand = new Random();
		String[] bigrams = new String[alphabet.length*alphabet.length];
		for(int i =0; i<bigrams.length; i++) {
			bigrams[i] = String.valueOf(alphabet[i/alphabet.length]).concat(String.valueOf(alphabet[i%alphabet.length]));
			//System.out.print(bigrams[i]);
		}
		char[] res = new char[cbuff.length];
		int r1, r2;
		for (int i = 0; i < res.length; i++) {
			r1 = rand.nextInt(alphabet.length*alphabet.length);
			r2 = rand.nextInt(alphabet.length*alphabet.length);
			res[i] = alphabet[mod32(r1+r2)];
		}
		return res;
	}
	
	static int mod32(int arg) {
		assert(alphabet.length==32);
		while(arg<0) arg+=32;
		return arg%32;
	}
	
	static int mod_bi(int arg) {
		assert(alphabet.length==32);
		while(arg<0) arg+=32*32;
		return arg%(32*32);
	}

	static int pos(char c) {
		for(int i=0; i<alphabet.length; i++) { //находим буквы в алфавите
			if(c == alphabet[i]) return i;
		}
		System.err.println("ERROR: CHARACTER NOT FOUND in alphabet (decodeCaesar)");
		return -1; //should never come here
	}
	
	static int pos(String bi) {
		for(int i=0; i<bigrams.length; i++) { //находим буквы в алфавите
			if(bi.equals(bigrams[i])) return i;
		}
		System.err.println("bigram not found in pos");
		return -1; //should never come here
	}
}
