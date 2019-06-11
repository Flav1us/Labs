package main;

public class Sf_yasv {

	int c1, c2;
	int x1, x2, xa1, xa2;
	
	public Sf_yasv() {
		this.c1 = 0;
		this.c2 = 0;
	}
	
	public int iterate(int x, int a) {
		System.out.format("x = %d, a = %d, c1 = %d, c2 = %d\n", x, a, c1, c2);
		
		int z1 = (5*x + c1) % 2 ;
		c1 = (5*x + c1) >> 1;
		
		int z2 = (5*(x^a) + c2) % 2;
		c2 = (5*(x^a) + c2) >> 1;
		
		int y = z1 ^ z2;
		//System.out.format("(%d + (%d^%d)) ^ (%d + %d)) + %d = %d\n", xa2, x, a, x, x2, c, y);
		return y;
	}
}
