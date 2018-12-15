package main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import polyBasis.Gal;
import polyBasis.Myr;
//исследование (n,n)-Bf, 6 семестр, симметричная криптография
public class BooleanFunction {
	
	//static int n = 16; //test
	static int n = 15;
	//static int N = 57, M = 56; //test (example var 13)
	static int N = 2175/*==0x87F*/, M = 2176/*==0x880*/;
	//static final int N = 3/*==0x87F*/, M = 3/*==0x880*/; //test
	
	boolean[][] tableOfAccordance;
	CoordinateFunction[] coordinateFunctions;
	String instanceName;
	String storedTOAFilename;
	String storedDiffTableFilename;
	double maxDiff; //максимальная дифференциальная вероятность
	int[] diffTable = null; //тут все дифференциальные вероятности (суммы)
	int deg; 
	int[] avgSpreading;
	double[] avgSpreadingDeviation;
	
	
	public BooleanFunction(String instanceName) {
		this.instanceName = instanceName;
		//createTableOfAccordance();
		storedTOAFilename = "tableOfAccordance_"+instanceName+".bin";
		storedDiffTableFilename = "diffTable_"+instanceName+".bin";
	}
	
	void init(char type, boolean recalculateTOA/*, boolean recalculateDiffTable*/) {
		//Второй параметр говорит, пересчитыватьь ли таблицу соответствия, или взять сохранённую из памяти (storedTOAFilename).
		if (recalculateTOA == true) createTableOfAccordance(type);
		retrieveTOA();
		/*if (recalculateDiffTable == true) createTableOfAccordance(type);
		retrieveTOA();*/
		System.out.println("calculating CoordinateFunctions");
		createCoordinateFunctions();
		this.deg = this.deg();
		//System.out.println("calculating avgSpreadingDeviation");
		this.avgSpreading = this.avgSpreading();
		this.avgSpreadingDeviation = this.avgSpreadingDeviation();
	}
	
	void initTest() {
		String s;
		this.tableOfAccordance = new boolean[(int)Math.pow(2, n)][n];
		for(int i=0; i<(int)Math.pow(2,n); i++) {
			//System.out.println("kappa");
			//boolean[] x = new boolean[n], fx = new boolean[n];
			boolean[] x = new boolean[n];
			s = BooleanFunction.lpad(Integer.toBinaryString(i), n);
			//System.out.println(" " + s);
			for(int j=0; j<n; j++) {
				//x[j] = s.charAt(n-j-1)=='0' ? false : true;
				x[j] = s.charAt(j)=='0' ? false : true;
			}
			this.tableOfAccordance[i] = x;
		}
		createCoordinateFunctions();
		this.deg = this.deg();
		//System.out.println("calculating avgSpreadingDeviation");
		this.avgSpreading = this.avgSpreading();
		this.avgSpreadingDeviation = this.avgSpreadingDeviation();
	}
	
	void retrieveTOA() {
		this.tableOfAccordance = retrieveBoolArr(this.storedTOAFilename);
	}
	
	void retrieveDifftable() {
		this.diffTable = retrieveIntArr(this.storedDiffTableFilename);
	}
	
	void createCoordinateFunctions() {
		this.coordinateFunctions = new CoordinateFunction[n];
		//Map<boolean[], boolean[]> sortedTOA = BooleanFunction.sortByKey(this.tableOfAccordance);
		//int ctr = 0;
		boolean[][] vals = new boolean[n][(int)Math.pow(2, n)];
		//for(Map.Entry<boolean[], boolean[]> i : sortedTOA.entrySet()) {
		for(int i=0; i<Math.pow(2, n); i++) {
			for(int j=0; j<n; j++) {	
				//this.coordinateFunctions[j].values[ctr] = i.getValue()[j];
				//vals[j][ctr] = i.getValue()[j];
				vals[j][i] = this.tableOfAccordance[i][j];
				//System.out.print((vals[j][ctr] == true)?'1':'0');
			}
			//ctr++;
		}
		//System.out.print("Creating coordinate functions: ");
		for(int i=0; i<n; i++) {
			this.coordinateFunctions[i] = new CoordinateFunction(vals[i], n);
			//System.out.print('.');
		}
		//System.out.println();
	}
	
	/*void printCoordinateFunctions() {
		System.out.println();
		for(int i=0; i<n; i++) {
			for(int j=0; j<(int)Math.pow(2,n); j++) {
//				System.out.print(this.coordinateFunctions[i][j]==false ? 0 : 1);
				if(j%1000==0)System.out.print('.');
			}
			System.out.println();
		}
	}*/
	
	void createTableOfAccordance(char type) throws IllegalArgumentException {
		if (type != 'f' && type != 'g') throw new IllegalArgumentException("Illegal type. Should be 'f' or 'g'.");
		System.out.println("Wait 32 dots:");
		//Map<boolean[], boolean[]> tableOA = new HashMap<>();
		boolean[][] tableOA = new boolean[(int)Math.pow(2, n)][n];
		String s;
		for(int i=0; i<(int)Math.pow(2,n); i++) {
			//System.out.println("kappa");
			//boolean[] x = new boolean[n], fx = new boolean[n];
			boolean[] x = new boolean[n];
			s = BooleanFunction.lpad(Integer.toBinaryString(i), n);
			//System.out.println(" " + s);
			for(int j=0; j<n; j++) {
				//x[j] = s.charAt(n-j-1)=='0' ? false : true;
				x[j] = s.charAt(j)=='0' ? false : true;
			}
			
			//System.out.println(s);
			/*for(int j = n - s.length()+1; j<n; j++) {
				x[j] = s.charAt(n-j)=='0' ? false : true;
				//fx[j] = s.charAt(n-j-1)=='0' ? false : true;
			}
			for(int j=0; j<n-s.length(); j++) {
				x[j] = false;
				//fx[j] = false;
			}*/
			//fx = f(x);
			//tableOA.put(x, fx);
			//System.out.println(f(x)[n]);
			if (type == 'f') /*tableOA.put(x, f(x))*/ tableOA[i] = f(x);
			else if (type == 'g') /*tableOA.put(x, g(x))*/ tableOA[i] = g(x);
			else throw new IllegalArgumentException("LABEL SHOULDNT APPEAR HERE! Illegal type. Should be 'f' or 'g'.");
			if(i%1000 == 1) System.out.print('.');
		}
		System.out.println();
		try {
			saveBoolArr(tableOA, storedTOAFilename);
		} catch (IOException e) {
			System.out.println("couldn't save tableOfAccordance:\n" + e.getMessage());
		}
	}
	
	boolean[] f(boolean[] x) {
		boolean[] fx = new boolean[n];
		Gal x1 = Gal.booleanArrToGal(x);
		//System.out.println(" " + x1.toString());
		//System.out.println(x1.toPolyString());
		x1 = x1.pow(new Myr(Integer.toHexString(N)));
		//System.out.println("" + x1.toString());
		fx = x1.toBooleanArr();
		return fx;
	}
	
	boolean[] g(boolean[] x) {
		boolean[] gx = new boolean[n];
		Gal x1 = Gal.booleanArrToGal(x);
		x1 = x1.pow(new Myr(Integer.toHexString(M)));
		gx = x1.toBooleanArr();
		return gx;
	}
	
	void printTableOfAccordance() {
		//String s;
		//Map<boolean[], boolean[]> sortedTOA = BooleanFunction.sortByKey(this.tableOfAccordance);
		//System.out.println(sortedTOA.size());
		//int ctr = 0;
		//for(Map.Entry<boolean[], boolean[]> e : sortedTOA.entrySet()) {
		for(int j=0; j<Math.pow(2, n); j++) {
			//ctr++; System.out.println(ctr);
			//for(int i = 0; i<n; i++) {
			System.out.print(BooleanFunction.lpad(Integer.toBinaryString(j), n));
			//}
			System.out.print("\t");
			for(int i = 0; i<n; i++) {
				System.out.print(this.tableOfAccordance[j][i]==false ? 0 : 1);
			}
			System.out.print("\n");
		}
	}
	
	/*void checkTOA() {
		String s1;
		//for(Map.Entry<boolean[], boolean[]> e : this.tableOfAccordance.entrySet()) {
			for(int i=0; i<(int)Math.pow(2,n); i++) {
				//if(i%1024 == 1) System.out.print('.');
				s1 = BooleanFunction.lpad(Integer.toBinaryString(i), 15);
				System.out.println(s1 + "\t" + this.tableOfAccordance.get(new Gal(s1).toBooleanArr()));
			}
			System.out.println("OK");
		//}
	}*/
	
	public int deg() {
		//В предположении, что степень БФ - это максимум из степеней координатных функций
		int deg = 0;
		for(CoordinateFunction cf : this.coordinateFunctions) {
			if(cf.deg > deg) deg = cf.deg;
		}
		return deg;
	}
	
	public double[] avgSpreadingDeviation() {
		double[] avgSD = new double[n];
		int[] avgSpr = this.avgSpreading();
		for(int i=0; i<n; i++) avgSD[i] = Math.abs(avgSpr[i] - n*Math.pow(2, n-1))/(n*Math.pow(2, n-1));
		return avgSD;
	}
	
	public void printAvgSprDev() {
		for(int i=0; i<this.avgSpreadingDeviation.length; i++) {
			System.out.format(i + ":\t%.2f", this.avgSpreadingDeviation[i]*100);
			System.out.print("%\n");
		}
	}
	
	//coordinate functions average spreading deviation
	public void printCFAvgSprDev() {
		System.out.println("Coordinate functions average spreading deviation:");
		for(int i=0; i<this.coordinateFunctions.length; i++) {
			System.out.print(i+":  ");
			for(int j=0; j<this.coordinateFunctions[i].avgSpreadingDeviation.length; j++) {
				System.out.format("%.2f", this.coordinateFunctions[i].avgSpreadingDeviation[j]*100);
				System.out.print("%\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	int[] avgSpreading() {
		//TODO TEST
		int k = 0;
		int[] avgSpr = new int[n];
		//int twoPowN = (int)Math.pow(2, n);
		String t1;
		boolean[] b1, b2, b;
		//int ctr = 0;
		for(int i=0; i<n; i++) {
			//for(Map.Entry<boolean[], boolean[]> e : BooleanFunction.sortByKey(this.tableOfAccordance).entrySet()) {
			for(int j=0; j<Math.pow(2, n); j++) {	
				//System.out.println(j);
				t1 = BooleanFunction.lpad(Integer.toBinaryString(j), n);
				b1 = BooleanFunction.stringToBoolArr(t1);
				b2 = b1.clone();
				b2[i] = b2[i] ^ true;
				//System.out.println("b1: " + boolArrAsString(b2) + " " +Integer.parseInt(BooleanFunction.boolArrAsString(b2), 2)/* + "\tb2:" + boolArrAsString(xorArrays(this.tableOfAccordance[j], this.tableOfAccordance[Integer.parseInt(BooleanFunction.boolArrAsString(b2), 2)]))*/);
				b = xorArrays(this.tableOfAccordance[j], this.tableOfAccordance[Integer.parseInt(BooleanFunction.boolArrAsString(b2), 2)]);
				for(int l =0; l<b.length; l++) k+= ((b[l]==true)?1:0);
				//ctr++;
			}
			avgSpr[i]=k;
			k=0;
		}
		return avgSpr;
		/*int k = 0;
		int twoPowN = (int)Math.pow(2, n);
		String t1, t2;
		//String s;
		char[] chArr;
		for(int i=0; i<twoPowN; i++) {
			t1 = BooleanFunction.lpad(Integer.toBinaryString(i), n);
			chArr = t1.toCharArray();
			chArr[varNum] = ((t1.charAt(varNum)=='1') ? '0' : '1');
			t2 = String.valueOf(chArr);
			//System.out.println(t1 + " " + t2 + " " + this.values[Integer.parseInt(t1, 2)] + " " + this.values[Integer.parseInt(t2, 2)]);
			k+=(this.values[Integer.parseInt(t1, 2)] ^ this.values[Integer.parseInt(t2, 2)])==false ? 0 : 1;
		}
		return k;*/
	}
	
	//Strict Avalance Effect степени 0
	
	//Максимум диференциальной вероятности
	public double maxDiffProb(boolean recalculateDiffTable) {
		//количество дифференциалов
		int[] quant = new int[(int)Math.pow(2, n)-1];
		int temp = 0;
		System.out.println("Counting diffs:\n");
		int max = 0;
		if(!recalculateDiffTable) {
			this.retrieveDifftable();
			for(int i=0; i<this.diffTable.length; i++) {
				if(this.diffTable[i] > max) max = this.diffTable[i];
			}
			return max/(Math.pow(2, n));
		}
		for(int i=1; i<Math.pow(2, n); i++) { 
			if((i+1)%1000 == 0) System.out.print('.');
			temp = maxDiff(BooleanFunction.stringToBoolArr(BooleanFunction.lpad(Integer.toBinaryString(i),n)));
			quant[i-1] = temp;
			 /*quant[j] += temp[j]*/ if(temp > max) max = temp;
			// System.out.println(temp);
		}
		System.out.println();
		try {
			saveIntArr(quant, storedDiffTableFilename);
		} catch (IOException e) {
			System.out.println("Trouble while saving diffTable:");
			e.printStackTrace();
		}
		return (double)max/*(quant)*//(Math.pow(2, n));
	}
	
	//перебор по всем х, поиск количества DaF(x): ячейке массива соответствует кол-во таких х.
	int maxDiff(boolean[] a) throws IllegalArgumentException {
		if(a.length != n) throw new IllegalArgumentException("invalid array length: should be n");
		boolean notZero = false;
		for(boolean b : a) {
			if(b == true) notZero = true;
		}
		if(notZero == false) throw new IllegalArgumentException("invalid array: cannot be 00...0");
		int[] nod = new int[(int)Math.pow(2, n)];
		//String t;
		int index2;
		boolean[] index1, Dax;
		for(int i=0; i<Math.pow(2, n); i++) {
			index1 = xorArrays(a, BooleanFunction.stringToBoolArr(BooleanFunction.lpad(Integer.toBinaryString(i), n)));
			index2 = Integer.parseInt(BooleanFunction.boolArrAsString(index1),2);
			Dax = xorArrays(this.tableOfAccordance[index2], this.tableOfAccordance[i]);
			nod[Integer.parseInt(boolArrAsString(Dax), 2)]++;
		}
		//System.out.println();
		return max(nod);
	}
	
	public static int max(int[] arr) {
		if(arr.length == 0) return (Integer) null;
		if(arr.length == 1) return arr[0];
		int max = arr[0];
		for(int i=1; i<arr.length; i++) {
			if(arr[i] > max) max = arr[i];
		}
		return max;
	}
	
	public boolean hasSAEofZero() {
		for(CoordinateFunction cf : this.coordinateFunctions) {
			if(!cf.hasSAE) return false;
		}
		return true;
	}
	
	public boolean hasSAEInAvg() {
		int c = n*(int)Math.pow(2, n-1);
		for(int t : this.avgSpreading) {
			if (t != c) return false;
		}
		return true;
	}
	
	public static boolean[] stringToBoolArr(String s) throws IllegalArgumentException {
		boolean[] b = new boolean[s.length()];
		for(int i=0; i<b.length; i++) {
			if(s.charAt(i) == '0') b[i] = false;
			else if(s.charAt(i) == '1') b[i] = true;
			else throw new IllegalArgumentException("String should contain '0' or '1'. Found '" + s.charAt(i)+ "'.");
		}
		return b;
	}
	
	public static String boolArrAsString(boolean[] b) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<b.length; i++) {
			//System.out.print(b[i]==false?'0':'1');
			s.append(b[i] == false ? '0' : '1');
		}
		//System.out.println();
		return s.toString();
	}
	
	public boolean[] xorArrays(boolean[] a, boolean[] b) throws IllegalArgumentException {
		if(a.length != b.length) throw new IllegalArgumentException("Array sizes should be equal: " + a.length + " != " + b.length);
		boolean[] res = new boolean[a.length];
		for(int i=0; i<res.length; i++) {
			res[i] = a[i] ^ b[i];
		}
		return res;
	}
	
	public static String lpad(String s, int n) {
		StringBuilder s1 = new StringBuilder();
		for(int i=0; i<n-s.length(); i++) {
			s1.append('0');
		}
		s1.append(s);
	    return s1.toString();  
	}
	
	//public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	public static Map<boolean[], boolean[]> sortByValue(Map<boolean[], boolean[]> map) {
	    return map.entrySet()
	              .stream()
	              //.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .sorted(Map.Entry.comparingByValue(new LexicographicComparator()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
	public static Map<boolean[], boolean[]> sortByKey(Map<boolean[], boolean[]> map) {
	    return map.entrySet()
	              .stream()
	              //.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .sorted(Map.Entry.comparingByKey(new LexicographicComparator()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
	
	public static void saveMap(Map<boolean[], boolean[]> map, String filename) throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(map);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("looks like file not found:\n" + e.getMessage());
		}
	}
	
	
	public static Map<boolean[], boolean[]> retrieveMap(String filename) {
		Map<boolean[], boolean[]> map = null;
		try {
	         FileInputStream fileIn = new FileInputStream(filename);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         map = (Map<boolean[], boolean[]>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Map class not found");
	         c.printStackTrace();
	         return null;
	      }
		return map;
	}
	
	public static void saveBoolArr(boolean[][] arr, String filename) throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(arr);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("looks like file not found:\n" + e.getMessage());
		}
	}
	
	
	public boolean[][] retrieveBoolArr(String filename) {
		boolean[][] arr = null;
		try {
	         FileInputStream fileIn = new FileInputStream(filename);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         arr = (boolean[][])in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Map class not found");
	         c.printStackTrace();
	         return null;
	      }
		return arr;
	}
	
	public static void saveIntArr(int[] arr, String filename) throws IOException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(arr);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("looks like file not found:\n" + e.getMessage());
		}
	}
	
	
	public int[] retrieveIntArr(String filename) {
		int[] arr = null;
		try {
	         FileInputStream fileIn = new FileInputStream(filename);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         arr = (int[])in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Int[] class not found");
	         c.printStackTrace();
	         return null;
	      }
		return arr;
	}
	
	
}


class LexicographicComparator implements Comparator<boolean[]> {
    @Override
    public int compare(boolean[] a, boolean[] b) {
        for(int i=0; i<a.length; i++) {
        //for(int i=a.length-1; i>=0; i--) {
        	if (a[i] == false && b[i] == true) return -1;
        	else if (a[i] == true && b[i] == false) return 1;
        }
        return 0;
    }
}
