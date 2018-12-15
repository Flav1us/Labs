package main;
//y = e^x - точное решение
//y' = (1-x^2)y + (x^2)*(e^x) Ц уравнение (вариант 8)
public class Runge {
	static int points = 20; //кол-во точек
	static double[] x = new double[points];
	static double[] y = new double[points];
	static double y0 = 1; //y(x0) 
	static double h = 0.1; //шаг

	public static void main(String[] args) {
		//System.out.println(f(0.05,0));
		//System.out.println(1.0 + ((0.16666666666)*(1214872.0 + 0.03583320593381796 + 0.03633933685088108 + 0.02539544730999734)));
		for(int i=0; i<points; i++) x[i] = i*h;
		y = /*Adams.*/getRoots(0, y0, h, points);
		System.out.println(y.length);
		x = new double[points];
		for(int i=0; i<y.length; i++) {
			x[i] = i*h;
			//System.out.format("%.1f\t%.4f\t%.4f\n",x[i], y[i], Math.pow(Math.E, x[i]));
			System.out.println(x[i]+"\t"+y[i]+"\t"+Math.pow(Math.E, x[i]));
		}
		y = Adams.getRoots(0, y0, h, points);
		System.out.println(y.length);
		x = new double[points];
		for(int i=0; i<y.length; i++) {
			x[i] = i*h;
			//System.out.format("%.1f\t%.4f\t%.4f\n",x[i], y[i], Math.pow(Math.E, x[i]));
			System.out.println(x[i]+"\t"+y[i]+"\t"+Math.pow(Math.E, x[i]));
		}
		//getRoots();
	}
	
	static double[] getRoots(double x0, double y0, double h, int numOfPoints) {
		x = new double[numOfPoints];
		for(int i=0; i<x.length; i++) x[i] = i*h;
		y = new double[numOfPoints];
		y[0] = y0;
		for(int i=1; i<y.length; i++) {
			double[] k = k(i-1); // ! 
			y[i] = y[i-1] + (1.0/6)*(k[0] + 2*k[1] + 2*k[2] + k[3]);
		}
		
		return y;
	}
	
	static void getRoots() {
		y[0] = y0;
		for(int i=0; i<x.length; i++) x[i] = i*h;
		for(int i=1; i<y.length; i++) {
			double[] k = k(i-1); // ! Єбаные в рот баги, как же вы заебали, бл€ть. ’орошего тебе дн€. !
			y[i] = y[i-1] + (1.0/6)*(k[0] + 2*k[1] + 2*k[2] + k[3]);
		}
		//точка, приблизительный результат, точный результат:
		for(int i=0; i<x.length; i++) System.out.format("%.1f\t%.4f\t%.4f\n",x[i], y[i], Math.pow(Math.E, x[i]));
	}
	
	static double[] k(int i) {
		double[] k = new double[4]; //метод –унге- утта 4-го пор€дка
		k[0] = h*f(x[i], y[i]);
		k[1] = h*f(x[i] + 0.5*h, y[i] + 0.5*k[0]);
		k[2] = h*f(x[i] + 0.5*h, y[i] + 0.5*k[1]);
		k[3] = h*f(x[i] +     h, y[i] +     k[2]);	
		return k;
	}
	
	static double f(double x, double y) {
		return (1-x*x)*y+x*x*Math.pow(Math.E, x); //см. уравнение в начале
	}
}
