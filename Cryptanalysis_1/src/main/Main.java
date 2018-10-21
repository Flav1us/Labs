package main;

import java.text.DecimalFormat;

import util.Util;

public class Main {
	public static String path_prob = "C:\\main\\prog\\EclipseWorkspace\\2018\\Cryptanalysis_1\\var\\prob_06.csv";
	public static String path_table = "C:\\main\\prog\\EclipseWorkspace\\2018\\Cryptanalysis_1\\var\\table_06.csv";

	static Double[][] prob;
	static Integer[][] table;
	static int clen;
	static DecimalFormat df = new DecimalFormat("#.####");;
	
	static {
		prob = Util.getDoubleCSV(path_prob);
		table = Util.getIntegerCSV(path_table);
		assert (prob[0].length == table.length) && (table.length == table[0].length); // |M| == |K| == |C|
		clen = prob[0].length; // |C|
		//DecimalFormat df = new DecimalFormat("#.#####"); NULLPO: не работает??? почему?
	}

	public static void main(String[] args) {
		double[][] pmc = P_MifC();
		//System.out.println(pmc.length);
		System.out.println("\n" + pmc[14][1]);
	}
	
	public static double[] P_C() { // P(C)
		double[] p_c = new double[clen];
		for(int i = 0; i<table.length; i++) {
			for(int j = 0; j<table[i].length; j++) {
				//table:	cols - plaintext, rows - keys
				//prob:		0 - plaintext, 1 - keys	(distribution)
				p_c[table[i][j]] += prob[0][i]*prob[1][j];
			}
		}
		
		System.out.println("P(C)");
		for(int i = 0; i < p_c.length;i++) {
			System.out.print(df.format(p_c[i]) + "\t");
		}
		System.out.println("\n");
		//System.out.println(whole);
		Util.checkProbLess1(p_c);
		return p_c;
	}
	
	public static double[][] P_MC() { //P(M, C)
		double[][] p_mc = new double[table.length][table[0].length];
		for(int i = 0; i<table.length; i++) {
			for(int j = 0; j<table[0].length; j++) {
				//cols(j) - plaintext, rows(i) - ciphertexts
				p_mc[i][table[i][j]] += prob[0][i]*prob[1][j];  
			}
		}
		System.out.println("P(M, C)");
		for(int i = 0; i<p_mc.length; i++) {
			for(int j = 0; j<  p_mc[0].length; j++) {
				System.out.print(p_mc[i][j] + "\t");
			} System.out.println();
		}System.out.println();
		Util.checkProbLess1(p_mc);
		return p_mc;
	}
	
	public static double[][] P_MifC(){
		double[][] res = new double[table.length][table[0].length];
		double[][] pmc = P_MC();
		double[] pc = P_C();
		System.out.println("P(M|C)");
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				res[i][j] = pmc[i][j]/pc[i];
				System.out.print(df.format(res[i][j]) + "\t");
			} System.out.println();
		}
		Util.checkProbLess1(res);
		return res;
	}

}
