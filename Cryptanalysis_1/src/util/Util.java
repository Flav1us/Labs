package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Util {
	public static String[][] getCSV(String path) {
		List<String[]> resList = new LinkedList<>();
		String line = "";
		String csvSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			  while ((line = br.readLine()) != null) {
				  resList.add(line.split(csvSplitBy));
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String[][])resList.toArray(new String[0][0]);
	}
	
	public static Double[][] getDoubleCSV(String path) {
		String[][] csv = getCSV(path);
		Double[][] res = new Double[csv.length][csv[0].length];
		for(int i = 0; i < csv.length; i++) {
			Double[] d_line = new Double[csv[0].length];
			for(int j = 0; j < csv[0].length; j++) {
				d_line[j] = Double.parseDouble(csv[i][j]);
			}
			res[i] = d_line;
		}
		 return res;
	}
	
	public static Integer[][] getIntegerCSV(String path) {
		String[][] csv = getCSV(path);
		Integer[][] res = new Integer[csv.length][csv[0].length];
		for(int i = 0; i < csv.length; i++) {
			Integer[] d_line = new Integer[csv[0].length];
			for(int j = 0; j < csv[0].length; j++) {
				d_line[j] = Integer.parseInt(csv[i][j]);
			}
			res[i] = d_line;
		}
		 return res;
	}
	
	public static void printArr(Object[][] res) {
		for(int i = 0; i<res.length; i++) {
			for(int j = 0; j<  res[0].length; j++) {
				System.out.print(res[i][j] + "\t");
			} System.out.println();
		}System.out.println();
	}
	
	public static void checkProbLess1(double[] probarr) {
		for(double i : probarr) assert i <= 1;
	}
	
	public static void checkProbLess1(double[][] probarr) {
		/*for(double[] arr : probarr) {
			for(double i : arr) assert i <= 1;
		}*/
		for(int i = 0; i < probarr.length; i++) {
			for(int j = 0; j < probarr[i].length; j++) {
				if(probarr[i][j] > 1) throw new IllegalArgumentException("probarr[" + i + "][" + j + "] = " + probarr[i][j] + " > 1");
			}
		}
	}
}
