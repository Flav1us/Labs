package test;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

import lab1.L1Task3;
import lab2.L2Task1;
import lab2.L2Task2;
import lab2.L2Task3;
import util.SampleGenerator;

public class Test {
	@Ignore
	@org.junit.Test
	public void testGauss() {
		int size = 5;
		double[] arr = SampleGenerator.standartGauss(size);
		assertTrue(arr.length == size);
		for(double a : arr) System.out.println(a);
		System.out.println();
	}
	
	@Ignore
	@org.junit.Test
	public void testUniform() {
		double[] arr = SampleGenerator.uniform(-100, 10, 10);
		for(double a : arr) System.out.println(a);
		System.out.println();
	}
	
	@Ignore
	@org.junit.Test
	public void testL21() {
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
	
@Ignore
	@org.junit.Test
	public void testL22() {
		L2Task2 l = new L2Task2();
		int scss = 0;
		for(int n = 100; n <= 10000; n*=10) {
			System.out.println("n = "+n);
			for(int i = 0; i < 100; i++) {
				if(l.regenerate(n)) scss ++;
			}
			System.out.println("succeed: " + scss + " / 100");
			scss = 0;
		}
	}
	

	@org.junit.Test
	public void testL23() {
		L2Task3 l = new L2Task3();
		l.recalculate();
	}
	
@Ignore
	@org.junit.Test
	public void testL13() {
		/*for(int n = 100; n <= 1000000; n*=10) {
			System.out.println("n = " + n);
			for(double alph = 0.1; alph >= 0.001; alph/=10) {				
				System.out.println("alph = " + alph);
			}
		}*/
		L1Task3.task3();
	}
	
}
