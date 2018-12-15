package main;

public class Adams {
	public static double[] getRoots(double x0, double y0, double h, int numOfPoints) throws IllegalArgumentException {
		double[] y = new double[numOfPoints];
		//алгоритм четвёртого порядка точности
		double[] res0 = Runge.getRoots(x0, y0, h, 4);
		if(res0.length!=4) throw new IllegalArgumentException("res0.length != 4");
		for(int i=0; i<res0.length; i++) y[i] = res0[i];
		//for(int i=0; i<4; i++) System.out.print(y[i] + "\t");
		//System.out.println();
		for(int i=4; i<numOfPoints; i++) {
			y[i] = y[i-1] + h*(55.0*y[i-1] - 59.0*y[i-2] + 37.0*y[i-3] - 9.0*y[i-4])/24;
		}
		return y;
	}
}
