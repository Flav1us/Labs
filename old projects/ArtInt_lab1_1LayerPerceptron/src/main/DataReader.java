package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class DataReader {
	double[][] input = new double[100][2];
	int[] result = new int[100];
	static String defaultPath = "C://main/Programs/JavaWorkspace/ArtInt_lab1_1LayerPerceptron/data/data09.csv";
	
	DataReader() throws IOException {
		getData();
	}
	
	public void getData() throws IOException {
		BufferedReader br = Files.newBufferedReader(Paths.get(defaultPath));
		String s;
		String[] sa;
		int i=0;
		while((s = br.readLine()) != null) {
			sa = s.split(";");
			//System.out.println(sa[0] + "\t" +sa[1] + "\t" +sa[2]);
			input[i][0] = Double.parseDouble(sa[0]);
			input[i][1] = Double.parseDouble(sa[1]);
			result[i] = Integer.parseInt(sa[2]);
			i++;
		}
		br.close();
	}
	
	public static void showLearningData() throws IOException {
		DataReader d = new DataReader();
		int ctr = 0;
		for(int i=0; i<d.input.length; i++) {
			if(i%4!=0) {
				System.out.println(++ctr + "\t" + d.input[i][0] + "\t" + d.input[i][1] + "\t" + d.result[i]);
			}
		}
	}
}
