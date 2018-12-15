package main;

import java.io.IOException;

//http://alife.narod.ru/lectures/neural/Neu_ch04.htm
public class Perceptron {
	private static double tempo = 0.1;
	double[] W = {1.0, 1.0};
	private final double edge = 10;
	private final int sample_proportion = 4; //отношение мощности обучающей выборки к мощности проверочной.
	
	public int solve(double[] input) {
		checkInput(input);
		return (input[0]*W[0] + input[1]*W[1]) >= edge ? 1 : 0;
	}
	
	public int adapt(double[] input, int result) { //адаптировался -> 1, иначе 0
		checkInput(input, result);
		int realRes = solve(input);
		for(int i=0; i<2; i++) {
			//if(result-realRes!=0)System.out.println("W["+i+"] += " + input[i] + " * (" + result + "-"+realRes+")*"+tempo);
			W[i] += input[i]*(result-realRes)*tempo;
		}
		if(result-realRes != 0) System.out.println(W[0]+"*x" + " + " + W[1]+"*y");
		return ((result-realRes) == 0 ? 0 : 1);
	}
	
	public int mutate(DataReader d, double tempo){// цикл адаптаций, возвращает количество не угаданных
		this.tempo = tempo;
		int ctr = 0;
		for(int i = 0; i<d.input.length; i++) {
			if(i%sample_proportion != 0) ctr += adapt(d.input[i], d.result[i]);
		}
		return ctr;
	}
	
	public int check(DataReader d) {
		int ctr = 0;
		//System.out.println("solved\texpected");
		for(int i = 0; i<d.input.length; i++) {
			if (i%sample_proportion == 0) {
				//System.out.println(this.solve(d.input[i]) + "\t" + d.result[i]);
				if(this.solve(d.input[i]) != d.result[i]) ctr++;
			}
		}
		return ctr;
	}
	
// ---------- ---------- ---------- ---------- ---------- ---------- ---------- ----------
	
	public static void showProgress(/*int cycles,*/ double tempo) throws IOException {
		Perceptron p = new Perceptron();
		DataReader d = new DataReader();
		System.out.println("mutation errors count:\n№:\terrors:");
		/*for(int j=0; j<cycles; j++) {
			System.out.println(j+1 + "\t" + p.mutate(d, tempo));
		}*/
		int k, ctr=0;
		do {
			k=p.mutate(d,tempo);
			System.out.println(++ctr + "\t" + k);
		} while (k!=0);
		System.out.println("check:\t" + p.check(d));
		System.out.print("result:\t");
		p.print();
	}
	
	public static void showProgress() throws IOException {
		//Perceptron.showProgress(15, 1);
		Perceptron.showProgress(tempo);
		//Perceptron.showProgress(80, 0.01);
	}
	

// ---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- 
	public void print() {
		System.out.println(this.W[0] + "\t" + this.W[1] + "\ttempo: " + this.tempo);
	}
	
	public static void checkInput(double[] input, int result) {
		if (input.length != 2) throw new IllegalArgumentException("input.length = " + input.length + " != 2");
		if (result != 1 && result != 0) throw new IllegalArgumentException("result = " + result + " != 0|1");
	}
	
	public static void checkInput(double[] input) {
		if (input.length != 2) throw new IllegalArgumentException("input.length = " + input.length + " != 2");
	}
	
	public static void checkInput(int result) {
		if (result != 1 && result != 0) throw new IllegalArgumentException("result = " + result + " != 0|1");
	}

}
