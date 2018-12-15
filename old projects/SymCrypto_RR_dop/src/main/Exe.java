package main;
//import java.util.Map;

import polyBasis.*;
public class Exe {
	
	//1000000000000011 = x^15+x+1
	//f(x) = x^N, g(x) = x^M - (15,15) - булевые функции
	
	public static void main(String[] args) {
		long start_timer = System.currentTimeMillis();
		//int n = 15;
		// TODO Auto-generated method stub
		/*Gal gal = new Gal("10").pow(new Myr("2"));
		System.out.println(gal.toString());*/
		
		showBFandBGCoordFuncProperties();
		//CoordinateFunction.testSpreading();
		//testBF();
		//CoordinateFunction.testSpreading();
		//showBFandBGCoordFuncProperties();
		System.out.println("time taken: " + (System.currentTimeMillis() - start_timer) + " ms");
	}
	
	static void showBFandBGCoordFuncProperties() {
		int n = 15;
		/*Gal g = new Gal("10");
		System.out.println(g.pow(new Myr(Integer.toBinaryString(2176))).toPolyString());*/
		BooleanFunction bf = new BooleanFunction("bf");
		bf.init('f', true);
		//bf.printTableOfAccordance();
		
		System.out.println("bf average spreading:");
		for(int i=0; i<bf.avgSpreading.length; i++) {
			System.out.println(bf.avgSpreading[i] + "\t");
		} System.out.println();
		
		System.out.println("disbalance:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bf.coordinateFunctions[i].disbalance);
		System.out.println("deg:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bf.coordinateFunctions[i].deg);
		System.out.println("NL:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bf.coordinateFunctions[i].findNL());
		System.out.println("CorrImmun:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bf.coordinateFunctions[i].findCorrImmun());
		System.out.println("spreading:");
		for(int i=0; i<n; i++) {
			for(int j = 0;j<n; j++) {
				System.out.print(bf.coordinateFunctions[i].spreading[j] + "\t");
			}System.out.println();
		}
		bf.printCFAvgSprDev(); //сам печатает подпись
		System.out.println("Average spreading deviation:");
		bf.printAvgSprDev();
		System.out.println("SAEavg\t"+bf.hasSAEInAvg()+"\tSAEzero\t" + bf.hasSAEofZero());
		System.out.println("max diff probab.: " + bf.maxDiffProb(true));
		
		BooleanFunction bg = new BooleanFunction("bg");
		bg.init('g', true);
		//bf.printTableOfAccordance();
		
		System.out.println("bg average spreading:");
		for(int i=0; i<bg.avgSpreading.length; i++) {
			System.out.println(bg.avgSpreading[i] + "\t");
		} System.out.println();
		
		System.out.println("disbalance:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bg.coordinateFunctions[i].disbalance);
		System.out.println("deg:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bg.coordinateFunctions[i].deg);
		System.out.println("NL:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bg.coordinateFunctions[i].findNL());
		System.out.println("CorrImmun:");
		for(int i=0; i<n; i++)System.out.println(i+":\t" + bg.coordinateFunctions[i].findCorrImmun());
		System.out.println("spreading:");
		for(int i=0; i<n; i++) {
			for(int j = 0;j<n; j++) {
				System.out.print(bg.coordinateFunctions[i].spreading[j] + "\t");
			}System.out.println();
		}
		bg.printCFAvgSprDev(); //сам печатает подпись
		System.out.println("Average spreading deviation:");
		bg.printAvgSprDev();
		System.out.println("max diff probab.: " + bg.maxDiffProb(true));
		System.out.println("SAEavg\t"+bg.hasSAEInAvg()+"\tSAEzero\t" + bg.hasSAEofZero());
		
		
	}
	
	public void printBfSpreading() {
		BooleanFunction bf = new BooleanFunction("bf");
		bf.init('f', true);
		for(CoordinateFunction cf : bf.coordinateFunctions) {
			for(int spr : cf.spreading) {
				System.out.println(spr);
			}
			System.out.println();
		}
	}
	
	public static void testBF() {
		//в классе Gal в методе mul нужно изменить массив, задающий порождающий полином поля F2^n.
		int n = 3;
		BooleanFunction bf = new BooleanFunction("bf");
		bf.n = 3;
		bf.initTest();
		bf.printTableOfAccordance();
		System.out.println("");
		for(int i=0; i<n; i++) {
			bf.coordinateFunctions[i].printWalshTable();
			System.out.println("corr immun: " + bf.coordinateFunctions[i].corrImmun);
			System.out.println("deg: " + bf.coordinateFunctions[i].deg);
			System.out.println("disb: " + bf.coordinateFunctions[i].disbalance);
			System.out.println("NL: " + bf.coordinateFunctions[i].NL);
			System.out.println("power: " + bf.coordinateFunctions[i].findPower());
			System.out.println("Spreading:\n");
			int[] sp = bf.coordinateFunctions[i].allSpreading();
			for(int j=0; j<sp.length; j++) {
				System.out.println("spr " + j + ":\t" + sp[j]);
			}
		}
		
		System.out.println("average spreading dev");
		bf.printAvgSprDev();
		
		bf.n= 15;
		
	}

}
