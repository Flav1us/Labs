package brilhardt_morrison;

public class BruteCheck {
	
	public static void areSameP2(PTable pt) {
		basic:
		for(int i = 1; i < pt.cStep; i++) {
			//System.out.println("kek");
			for(int j = 1; j < pt.cStep; j++) {
				//System.out.println("kek2");
				if(i==j) continue;
				if(pt.table[2][i] == pt.table[2][j]) {
					//System.out.println("kek3");
					int t = pt.contr.minmod(pt.table[1][i] * pt.table[1][j]);
					int[] s_arr;
					try {
						s_arr = SimpleFactorisator.factor(pt.table[2][i]);
					}
					catch (IllegalArgumentException e) {
						continue;
					}
					int s = 1;
					//System.out.println("kek4");
					for(int k = 0; k < s_arr.length; k++) {
						s *= Math.pow(SimpleFactorisator.base[k], s_arr[k]);
					}
					//System.out.println("kek5");
					s = pt.contr.minmod(s);
					if(Math.abs(t) == Math.abs(s)) continue;
					else {
						System.out.println(t + "\t" + s);
						System.out.println("("+i+", "+j+")" + "\t" + eukl(t-s, pt.contr.n) + "\t" + eukl(t+s, pt.contr.n));
						break basic;
					}
				}
			}
		}
		System.out.println("done");
	}
	
	public static int find(PTable pt) {
		for(int i=0; i<pt.cStep; i++) {
			for(int j = 0; j < pt.cStep; j++) {
				if (i==j) continue;
				if(isSumCoefsEven(pt, i, j)) {
					int[] res = constructResult(i ,j);
				} //TODO закончить полный перебор
			}
		}
		return 0;
	}
	
	private static int[] constructResult(int ... args) {
		int[] res = null;
		//TODO закончить
		return res;
	}
	
	public static int eukl(int a, int b) {
		if(a<0 || b<0) return eukl((int)Math.abs(a), (int)Math.abs(b));
		if(a < b) return eukl(b, a);
		while (a != 0 && b != 0) {
			if (a > b) {
				a %= b;
			} else {
				b %= a;
			}
		}
		return a+b;
 	}

	public static boolean isSumCoefsEven(PTable pt, int ... args) {
		//try {
			int[][] factor = new int[args.length][];
			for(int i = 0; i < args.length; i++) {
				factor[i] = SimpleFactorisator.factor(pt.table[2][args[i]]);
			}
			
 			for(int j = 0; j < SimpleFactorisator.base.length; j++) {
 				int sum = 0;
 				for(int k = 0; k < factor.length; k++) {
 					sum += factor[k][j];
 				}
				if((sum % 2) != 0) return false;
			}
			return true;
		/*}
		catch (IllegalArgumentException e) {
			return false;
		}*/
	}
}
