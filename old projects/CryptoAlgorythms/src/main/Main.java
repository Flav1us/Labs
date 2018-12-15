package main;

import berlekamp_massey.BKMController;
import brilhardt_morrison.BMController;
import silver_pohlig_hellman.SPHController;

public class Main {

	public static void main(String[] args) {
		//BMfactor(10123);
		//SPHdecide(5, 60, 97);
		BKMdecide(new byte[] {1,0,1,1,1,0,0,0});
	}
	
	public static void BMfactor(int n) {
		new BMController(n).simpleCheck();
	}
	
	public static void SPHdecide(int a, int b, int p) {
		new SPHController(a, b, p).process();
	}
	
	public static void BKMdecide(byte[] arr) {
		System.out.println(BKMController.BerlekampMassey(arr));
	}

}
