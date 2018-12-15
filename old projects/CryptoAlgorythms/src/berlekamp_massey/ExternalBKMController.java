package berlekamp_massey;

import java.util.Arrays;

public class ExternalBKMController {

	 public static int BerlekampMassey(byte[] s)                
	    {
	        int L, n, m, d;
	        int N=s.length;
	        byte[] c=new byte[N];
	        byte[] b=new byte[N];
	        byte[] t=new byte[N];

	        //Initialization
	        b[0]=c[0]=1;
	        n=L=0;
	        m=-1;
	                
	        //Algorithm core
	        while (n<N) {
	        	System.out.println("while N < n: " + n + " < " + N);
	        	System.out.println("d=s["+n+"]: d = " + s[n]);
	            d=s[n];
	            for (int i=1; i<=L/*n*/; i++) {
	            	//System.out.print(d+"^=c["+i+"]&s["+(n-i)+"]: " + "d"+" ^= "+c[i]+" & " + s[n-i]);
	            	d^=c[i]&s[n-i];            //(d+=c[i]*s[N-i] mod 2)
	            	//System.out.println(" = " + d);
	            }
	            if (d==1) {
	            	System.out.println("T(D)<-C(D)");
	                System.arraycopy(c, 0, t, 0, N);    //T(D)<-C(D)
	                for (int i=0; (i+n-m)<N; i++) {
	                	//System.out.println("c[i+n-m]^=b[i] " + "c["+(i+n-m)+"]^=b["+i+"] = " + c[i+n-m]+"^=" + b[i] + " = " + (c[i+n-m]^b[i]));
	                	c[i+n-m]^=b[i];
	                }
	                System.out.println("c = "+Arrays.toString(c));
	                if (L<=(n>>1)) {
	                	System.out.println("L = " + L + " <= " + "(n>>1) = " + (n>>1));
	                	System.out.println("L := n+1-L = " + (n+1-L));
	                    L=n+1-L;
	                    m=n;
	                    System.out.println("B(D)<-T(D)");
	                    System.arraycopy(t, 0, b, 0, N);    //B(D)<-T(D)
	                    System.out.println("b: " + Arrays.toString(b));
	                }
	            }
	            n++;
	        }
	        return L;
	    }
}

