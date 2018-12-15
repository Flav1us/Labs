package main;

public class Interpole {
	
	//private String filename = "src\\main\\java\\main\\graphic.xlsx";
	
	public static void main(String[] args) {
		// y = 2*(sin(x/2))^2
		int np = 10; //number of points
		double[][] values = new double[np][2];
		double pi = Math.PI;
		
		values[0][0] = -pi;
		for(int i=1; i<np; i++) {
			values[i][0] = values[i-1][0] + 2*pi/(np-1);
		}
		for(int i=0; i<np; i++) {
			values[i][1] = 2*Math.pow(Math.sin(values[i][0] / 2), 2);
			//System.out.println(values[i][0] + "\t" + values[i][1]);
		}
		double[] lagr = lagrangePoly(values);
		printPoly(lagr);
		checkLagr(lagr, values);
		System.out.println();
		/*System.out.println(2*pi/(6*(np-1)) + "\t" + 4*pi/(3*(np-1)));
		for(int i=1; i<np; i++) {
			System.out.println((1/(2*pi/(np-1))) * ( values[i+1][1] - 2*values[i][1] + values[i-1][1] ));
		}
	    x1	=	-	1.0766090200458	
		x2	=	-	0.10615293188746	
		x3	=	0.50097350334598	
		x4	=	0.98237471657659	
		x5	=	0.98237471657659	
		x6	=	0.50097350334598	
		x7	=	-	0.10615293188746	
		x8	=	-	1.0766090200458	*/
		double[] m = {0, -1.0766090200458, -0.10615293188746, 0.50097350334598, 0.98237471657659, 0.98237471657659, 0.50097350334598, -0.10615293188746, -1.0766090200458, 0};
		double[][] splines = calcSplines(m, values);
		for(int i=0; i<splines.length; i++) {
			System.out.print("["+values[i][0]+"; ["+values[i+1][0]+"]:\t");
			printPoly(splines[i]);
		}
		System.out.println();
		checkSplines(splines, values);
	}
	
	static void checkSplines(double[][] splines, double[][] values){
		for(int i = 0; i<splines.length; i++) {
			System.out.println(p(values[i][0], splines[i])-values[i][1]);
		}
	}
	
	static void checkLagr(double[] lagr, double[][] values) {
		//System.out.println(" l " + lagr.length);
		double[] newVal = new double[values.length];
		
		for(int i=0; i<values.length; i++) {
			newVal[i] = 2*Math.pow(Math.sin((values[i][0]+0.2) / 2), 2);
			//System.out.println(values[i][0] + "\t" + values[i][1]);
		}
		for(int i=0; i<newVal.length; i++) {
			System.out.println(p(values[i][0], lagr) - values[i][1]);
		}
	}
	
	static double p(double x, double[] f) {
		double res = 0;
		for(int i=0; i<f.length; i++) {
			res += f[i]*Math.pow(x,i);
		}
		return res;
	}
	
	public static double[][] calcSplines(double[] m, double[][] values) {
		double[][] res = new double[values.length - 1][4];
		double h = 2*Math.PI/(values.length-1);
		double[] t;
		double[] x = {0, 1};
		for(int i=1; i<values.length; i++) {
			t = mulPolyAndConst(	pow(subConstAndPoly(x, values[i][0]),3)		, m[i-1]/(6*h));
			res[i-1] = addPolynoms(res[i-1], t);
			t = mulPolyAndConst(	pow(subPolyAndConst(x, values[i-1][0]),3)		, m[i]/(6*h));
			res[i-1] = addPolynoms(res[i-1], t);
			t = mulPolyAndConst( subConstAndPoly(x, values[i][0]), (values[i-1][1] - m[i-1]*h*h/6)/h);
			res[i-1] = addPolynoms(res[i-1], t);
			t = mulPolyAndConst( subPolyAndConst(x, values[i-1][0]), (values[i][1] - m[i]*h*h/6)/h);
			res[i-1] = addPolynoms(res[i-1], t);
		}
		return res;
	}
	
	public static double[] pow (double[] a, int p) {
		if (p==0) {
			double[] t = new double[1];
			t[0] = 1;
			return t;
		}
		double[] res = a.clone();
		for(int i=0; i<p-1; i++) res = mulPolynoms(a, res);
		return res;
	}
	
	public static double[] lagrangePoly(double[][] points) {
		int n = points.length;
		for(int i = 0; i<n; i++) {
			if(points[i].length != 2) {
				System.out.println("lagrangePoly: wrong input - need like points[][2]");
				return null;
			}
		}
		double[] poly = new double[n - 1];
		//double[] t = new double[n];
		for(int i=0; i<n; i++) {
			double[] t = new double[n-1];
			double[] x = new double[2];
			double con;
			t[0] = points[i][1];
			for(int j=0; (j<n) ; j++) {
				if(i==j) continue;
				x[0] = -points[j][0];
				x[1] = 1;
				//System.out.print("x = "); printPoly(x);
				t = mulPolynoms(t, x);
				con = points[i][0] - points[j][0];
				//System.out.println("con = " + con);
				t = mulPolyAndConst(t, 1/con);
				//System.out.print(i + " ");
				//printPoly(t);
			}
			//System.out.println();
			poly = addPolynoms(poly, t);
		}
		
		return poly;
	}
	
	public static double[] addPolynoms(double[] a, double[] b) {
		double[] c = new double[a.length > b.length ? a.length : b.length];
		for(int i=0; i<c.length; i++) {
			if(i<a.length && i<b.length) {
				c[i] = a[i] + b[i];
			}
			else if(i<a.length && i>= b.length) {
				c[i] = a[i];
			}
			else c[i] = b[i];
		}
		return c;
	}
	
	public static double[] mulPolynoms(double[] a, double[] b) {
		double[] c = new double[(a.length-1) + (b.length-1) + 1];
		//System.out.println(c.length);
		for(int i=0; i<c.length; i++) c[i] = 0;
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<b.length; j++) {
				c[i+j] += a[i] * b[j];
			}
		}
		return c;
	}
	
	public static double[] subPolyAndConst(double[] poly, double con) {
		double[] c = new double[poly.length];
		for(int i=0; i<c.length; i++) {
			c[i] = poly[i];
		}
		c[0] = poly[0]-con;
		return c;
	}
	
	public static double[] subConstAndPoly(double[] poly, double con) {
		double[] c = new double[poly.length];
		for(int i=0; i<c.length; i++) {
			c[i] = -poly[i];
		}
		c[0] = c[0] + con;
		return c;
	}
	
	public static double[] mulPolyAndConst(double[] poly, double con) {
		double[] c = new double[poly.length];
		for(int i=0; i<c.length; i++) {
			c[i] = con*poly[i];
		}
		return c;
	}
	
	public static double[] diffPoly(double[] a) {
		double[] da = new double[a.length - 1]; 
		for(int i=0; i<da.length; i++) {
			da[i] = (i+1)*a[i+1];
		}
		return da;
	}
	
	public static void printPoly(double[] a) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<a.length; i++) {
			if(a[i] != 0) {
				s.append(a[i] + "*x^" + i + " + ");
			}
		}
		/*if(s.charAt(s.length()-2) == '+') {
			System.out.println(s.substring(0, s.length()-2));
			return;
		}*/
		System.out.println(s.toString());
	}

}
