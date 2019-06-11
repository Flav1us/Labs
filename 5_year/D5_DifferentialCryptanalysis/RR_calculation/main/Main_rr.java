package main;

public class Main_rr {

	public static void main(String[] args) {
		A();
	}

	private static void test1() {
		Sf f = new Sf();
		int x = 0b01;
		int a = 0b11;
		int n   = 2 ;
		System.out.println(Integer.toBinaryString(df(x, a, n)));
		for(int i = 0; i < n; i++)
			System.out.println(f.iterate((x >> (i))&1, (a >> (i))&1));
	}

	private static void testC() {
		Sf f = new Sf();
		int n = 1000;
		for(int x = 0; x < n; x++) {
			for(int a = 0; a < n; a++) {
				for(int i = 0; i < n; i++) {
					f.iterate((x >> (i))&1, (a >> (i))&1);
				}
			}
		}
	}
	
	private static void test() {
		int x = 6, a = 3, n = 100;
		System.out.println(Integer.toBinaryString(df(x, a, n)));
	}
	
	public static int df(int x, int a, int n) {
		return ((5*(x^a)) ^ (5*x))% (1<<n);
	}
	
	public static void A() {
		for(int x = 0; x <= 1; x++) {
			for(int a = 0; a <= 1; a++) {
				for(int u = 0; u < 5; u++) {
					int z = (((5*(x^a)) ^ (5*x)) + u )% 2;
					int v = (((5*(x^a)) ^ (5*x)) + u ) / 2;
					System.out.format("z = %d, x = %d, a = %d, u = %d, v = %d\n", z, x, a, u, v);
				}
			}
		}
	}

}
