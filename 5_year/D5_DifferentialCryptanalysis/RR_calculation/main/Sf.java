package main;

public class Sf {
	int c;
	int x1, x2, xa1, xa2;
	
	public Sf() {
		this.x1 = 0;
		this.x2 = 0;
		this.xa1 = 0;
		this.xa2 = 0;
		this.c = 0;
	}
	
	public int iterate(int x, int a) {
		//System.out.format("x = %d, a = %d, x1 = %d, x2 = %d, xa1 = %d, xa2 = %d, c = %d\n", x, a, x1, x2, xa1, xa2, c);
		
		int y = ((xa2 + (x^a)) ^ (x + x2)) + c;
		//System.out.format("(%d + (%d^%d)) ^ (%d + %d)) + %d = %d\n", xa2, x, a, x, x2, c, y);
		xa2 = xa1;
		xa1 = x^a;
		
		x2 = x1;
		x1 = x;
		
		c = y/2;
		//if( c> 2) System.out.println("C: " + c);
		return y%2;
	}
}
