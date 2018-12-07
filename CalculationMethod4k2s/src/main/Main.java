package main;
import lab1.L1Task12;
import lab2.L2Task1;
import lab3.T1_empty_blocks;
import lab3.T3_inversion;

public class Main {

	public static void main(String[] args) {
		l31();
		System.out.println("======================================");
		l32();
	}
	
	private static void l32() {
		T3_inversion.regenerate(true);
		T3_inversion.regenerate(false);
	}

	private static void l31() {
		T1_empty_blocks.regenerate(5000, 10000, 1, 1);
		T1_empty_blocks.regenerate(5000, 10000, 1, 1.5);
		
		boolean accepted = true;
		int n = 50, m = 100;
		double alpha = 1, beta = 1.2;
		while(accepted) {
			accepted = T1_empty_blocks.regenerate(n, m, alpha, beta);
			n *= 10; m *= 10;
		}
	}

	public static void l1() {
		L1Task12.task1();
		L1Task12.task2();
		//L1Task12.task3();
	}
	
	public static void l21() {
		L2Task1 l = new L2Task1();
		int scss = 0;
		for(int n = 100; n <= 100000; n*=10) {
			System.out.println("n = "+n);
			for(int i = 0; i < 100; i++) {
				if(l.regenerate(n)) scss ++;
			}
			System.out.println("succeed: " + scss + " / 100");
			scss = 0;
		}
	}
	
	public static void l2() {
		l21();
		
	}

}
