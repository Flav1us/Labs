package main;

public class main {

	public static void main(String[] args) {
		Polynomial P = new Polynomial();
		double a = 1, b = 4;
		System.out.println("["+ a + ", " + b + "]");
		System.out.println("result: " + P.halves(a, b));
		System.out.println("result: " + P.chord(a, b));
		System.out.println("result: " + P.newton(a, b));
		System.out.println("\n-------\n");
		a = 1; b = 2.5;
		System.out.println("["+ a + ", " + b + "]");
		System.out.println("result: " + P.halves(a, b));
		System.out.println("result: " + P.chord(a, b));
		System.out.println("result: " + P.newton(a, b));
		a = 1; b = 2.2;
		System.out.println("\n-------\n");
		System.out.println("["+ a + ", " + b + "]");
		System.out.println("result: " + P.halves(a, b));
		System.out.println("result: " + P.chord(a, b));
		System.out.println("result: " + P.newton(a, b));
	}

}
