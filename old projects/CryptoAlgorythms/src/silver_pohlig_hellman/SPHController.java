package silver_pohlig_hellman;

import java.math.BigInteger;

import brilhardt_morrison.SimpleFactorisator;

public class SPHController {
	
	int a, b, p; //a^x = b (mod p)
	int inv_a;
	
	public SPHController(int a, int b, int p) {
		this.a = a;
		this.b = b;
		this.p = p;
		inv_a = inv(a);
	}

	public void process() {
		int[] base = {-1, 2, 3, 5, 7, 11, 13};
		SimpleFactorisator.base = base;
		int[] factor = SimpleFactorisator.factor(p-1);
		
		int notZero = 0;
		for(int i = 0; i < factor.length; i++) if(factor[i] > 0) notZero++;
		
		int[][] r = new int[notZero][]; //предвычисление
		
		int ctr = 0;
		for(int i=0; i<factor.length; i++) {
			if(factor[i] > 0) {
				r[ctr++] = new int[base[i]]; 
			}
		}
		
		ctr = 0;
		int[] trimedFactor = new int[r.length];
		for(int i=0; i<factor.length; i++) {
			if(factor[i] > 0) trimedFactor[ctr++] = factor[i];
		}
		SimpleFactorisator.print(trimedFactor);
			
		for(int i = 0; i < r.length; i++) {
			for(int j=0; j<r[i].length; j++) {
				System.out.format("r[%d][%d] = %d^(%d*%d/%d) = ", i, j, a, j, p-1, r[i].length);
				r[i][j] = mpow(a, j*(p-1)/r[i].length); //r[i].length совпадает с і-м элементом разложения
				System.out.println(r[i][j]);
				//System.out.println(a + "^" + j+"*"+(p-1)+"/"+r[i].length);
				//System.out.print(r[i][j] + " ");
			} //System.out.println();
		}
		/*
		int y = p;
		int divisor = r[0].length;
		int x0src = mpow(b, (p-1)/divisor);
		int x0 = getIndex(r[0], x0src);
		y = y*mpow(b, -x0);
		System.out.println(x0src);
		divisor *= divisor;
		int x1src = mpow(b, (p-1)/divisor);
		System.out.println(x1src);
		*/
		
		//System.out.println(getIndex(r[1], 35));
		
/* The following code will make you cry
 * 
                              _
     _._ _..._ .-',     _.._(`))
    '-. `     '  /-._.-'    ',/
       )         \            '.
      / _    _    |             \
     |  a    a    /              |
     \   .-.                     ;  
      '-('' ).-'       ,'       ;
         '-;           |      .'
            \           \    /
            | 7  .__  _.-\   \
            | |  |  ``/  /`  /
           /,_|  |   /,_/   /
              /,_/      '`-'
 
 */
		
		int y = b;
		int divisor;
		int[][] x = new int[trimedFactor.length][];
		for(int i=0; i<trimedFactor.length; i++) {
			System.out.println("===============\nIteration " + i);
			x[i] = new int[trimedFactor[i]];
			divisor = r[i].length;
			for(int j=0; j<trimedFactor[i]; j++) {
				System.out.println("getI:\t"+((p-1)/divisor));
				x[i][j] = getIndex(r[i], mpow(y, (p-1)/divisor));
				System.out.println("x: " + x[i][j]);
				System.out.print("y = " + y + " * " + inv_a + "^(" + x[i][j] + "*" + mpow(r[i].length, j) + ") = "); 
				y = (y * mpow(inv_a, x[i][j]*mpow(r[i].length, j))) % p;
				System.out.println(y);
				divisor *= r[i].length;
			}
			y = b;
		}
		
		int[] kto_b = new int[trimedFactor.length];
		int[] kto_mod = new int[trimedFactor.length];
		for(int i=0; i<kto_b.length; i++) {
			for(int j=0; j<x[i].length; j++) {
				kto_b[i] += x[i][j]*mpow(r[i].length, j);
			}
		}
		
		for(int i=0; i<kto_b.length; i++) {
			kto_mod[i] = mpow(r[i].length, trimedFactor[i]);
		}
		
		SimpleFactorisator.print(kto_b);
		SimpleFactorisator.print(kto_mod);
		
		System.out.println(ChineseRemainderTheorem.chineseRemainder(kto_mod, kto_b));
	}
	
	
	
	public int inv(int a) {
		for(int b = 0; b < p; b++) {
			if(b * a % p == 1) return b;
		}
		throw new RuntimeException("no inverse found");
	}

	private int getIndex(int[] is, int x0src) {
		for(int i = 0; i < is.length; i++) {
			//System.out.println(is[i] + "\t" + x0src);
			if(is[i] == x0src) return i;
		}
		throw new RuntimeException("index not found!");
	}

	public int mpow(int a, int b) {
		return BigInteger.valueOf(a).modPow(BigInteger.valueOf(b), BigInteger.valueOf(p)).intValue();
	}

}
