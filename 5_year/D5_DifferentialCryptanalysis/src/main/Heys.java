package main;

public class Heys {
	
	//var 8
	public static int[] S = {	1,		2,		3,		0xE,
								6,		0xD,	0xB,	8,
								0xF, 	0xA, 	0xC, 	5,
								7, 		9, 		0, 		4};

	
	public static int[] S_reverse = reverse(S);
	/*private static int[] S = new int[16]; //test
	static {
		for(int i = 0; i < S.length; i++) S[i] = i;
	}*/
	
	public Heys() {
		// TODO Auto-generated constructor stub
	}
	
	private static int[] reverse(int[] s2) {
		int[] s_reverse = new int[s2.length];
		for(int i = 0; i <s_reverse.length; i++) {
			s_reverse[s2[i]] = i;
		}
		return s_reverse;
	}

	public static char encrypt(char m, char[] key) {
		if(key.length != 7) System.out.println("key length might be wrong");
		char res = m;
		for(int i = 0; i < 6; i++ ) {
			res = round(res, key[i]);
		}
		res ^= key[6];
		return res;
	}
	
	//char works as unsigned 16-bit number
	public static char round (char block, char round_key) {
		char res = 0;
		block ^= round_key;
		char[] t = new char[4];
		for(int i = 0; i < 4; i++) {
			t[i] = (char) S[block >>> 4*i & 0xF];
			t[i] = (char)  (((t[i] & 0b0001) << 3) ^ // 0bABCD -> 0bA000_B000_C000_D000
							((t[i] & 0b0010) << 6) ^
							((t[i] & 0b0100) << 9) ^
							((t[i] & 0b1000) << 12));
			t[i] = (char) (t[i] >> 3-i);
			res ^= t[i];
		}
		return res;
	}
	
	
	
	public static char decr_round (char block, char round_key) {
		char permutation = 0;
		char[] t = new char[4];
		for(int i = 0; i < 4; i++) {
			t[i] = (char) (block >>> 4*i & 0xF);
			t[i] = (char)  (((t[i] & 0b0001) << 3) ^ // 0bABCD -> 0bA000_B000_C000_D000
							((t[i] & 0b0010) << 6) ^
							((t[i] & 0b0100) << 9) ^
							((t[i] & 0b1000) << 12));
			t[i] = (char) (t[i] >> 3-i);
			permutation ^= t[i];
		}
		//permuted; next S-blocks:
		char res = 0;
		for(int i = 0; i < 4; i++) {
			res ^= (char) (S_reverse[permutation >>> 4*i & 0xF] << 4*i);
		}
		//now round key add
		res ^= round_key;
		return res;
	}

}
