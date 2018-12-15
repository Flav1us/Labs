package main;

import java.io.*;
import java.util.*;

public class Stat {
	//Архимаг в исходном варианте
	static final String myTextPathSrc = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\mytextOriginal.txt";
	//отформатированный
	static final String myTextPathDst = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\mytextFormated.txt";
	//для кодирования Виженером
	static final String textToEncrypt = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\textToEncrypt.txt";
	static final String encryptedText = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\encryptedText.txt";
	//basic text length: 1117718 symbols
	static char[] alph = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
	static char oddChar1 = 'ё', replacingChar1 = alph[5];
	static char oddChar2 = 'ъ', replacingChar2 = alph[28];
	static Map<Character, Integer> singleCharFreq = new HashMap<Character, Integer>();
	static Map<String, Integer> bigramFreq = new HashMap<String, Integer>();
	static Map<String, Integer> uBigramFreq = new HashMap<String, Integer>();
	static Map<Character, Double> htable1 = new HashMap<Character, Double>();
	static Map<String, Double> htable2 = new HashMap<String, Double>();
	static Map<String, Double> htable3 = new HashMap<String, Double>();
	static double h1=0, h2=0, h3=0;
	
	static final double h_inf = 1.811; //энтропия из coolPinkProgram для 30 символов (h30: 1.455 < h30 < 2.167)
	
	
	 static void formatTextFile(String src, String dst, boolean withSpaces) throws IOException {
		 if (withSpaces) Stat.alph[26] = ' ';
		 else Stat.alph[26] = 'ъ';
		 
		BufferedReader br = new BufferedReader (new FileReader(new File(src)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dst)));
		
		int readChar; // .read читает код символа
		char c;
		int breakctr = 0;
		boolean found, prevIsSpace = false;
		while((readChar=br.read()) != -1) {
			breakctr++;
			if (breakctr > 10000000) {
				//на случай бесконечного цикла
				System.out.println("\"while\" is too long");
				break;
			}
			found = false; //для избежания излишнего поиска в алфавите, после нахождения нужной буквы прерываем поиск.
			c = Character.toLowerCase((char)readChar); //превращаем прочитанный код символа собственно в символ
			if(c == oddChar1)  { // 33 буквы алфавита ужимаем в 32 путём замены "ё" на "е".
				bw.write(replacingChar1);
				prevIsSpace = false;
				continue; // нашли "ё" - не надо перебирать алфавит.
			}
			else if(c == oddChar2 && withSpaces) { //во втором режиме заменяем "ъ" на "ь", чтобы освободить место под пробел  алфавите
				bw.write(replacingChar2);
				prevIsSpace = false;
				continue;
			}
			for(int i=0; i<Stat.alph.length; i++) {
				if(c == Stat.alph[i] && found == false) {
					bw.write(c);
					if (c == ' ') prevIsSpace = true;
					else prevIsSpace = false;
					found = true;
				}
			}
			if (!found && !prevIsSpace && withSpaces) {
				bw.write(' ');
				prevIsSpace = true;
			}
		}
		//breakctr = 0;
		br.close();
		bw.close();	
	 }
	 
	 static void getFrequencies() throws IOException {
		 char c1, c2;
		 String bi;
		 for(int i=0; i<Stat.alph.length; i++) {
			 Stat.singleCharFreq.put(Stat.alph[i], 0);
		 }
		 for(int i=0; i<Stat.alph.length; i++) {
			 for(int j=0; j<Stat.alph.length; j++) {
				 //заполняем хэшмап всевозможными биграммами
				 Stat.bigramFreq.put(Character.toString(Stat.alph[i]).concat(Character.toString(Stat.alph[j])), 0);
				 Stat.uBigramFreq.put(Character.toString(Stat.alph[i]).concat(Character.toString(Stat.alph[j])), 0);
			 }
		 }
		 BufferedReader br = new BufferedReader (new FileReader(Stat.myTextPathDst));
		 c1=(char)br.read();
		 Stat.singleCharFreq.put(c1, Stat.singleCharFreq.get(c1)+1);
		 int t;
		 int ctr = 0;
		 while((t=br.read()) != -1) {
			 c2 = (char)t;
			 Stat.singleCharFreq.put(c2, Stat.singleCharFreq.get(c2)+1);
			 bi=Character.toString(c1).concat(Character.toString(c2));
			 Stat.bigramFreq.put(bi, Stat.bigramFreq.get(bi) + 1);
			 if (ctr % 2 == 0) {
				 Stat.uBigramFreq.put(bi, Stat.uBigramFreq.get(bi) + 1);
			 }
			 ctr++;
			 c1 = c2;
		 }
		 br.close();
	 }
	 
	 static void printResults() {
		 /*for (Map.Entry entry : Stat.singleCharFreq.entrySet()) {
			    System.out.println("Key:  " + entry.getKey() + "  Value: " + entry.getValue());
		 }
		 System.out.println("\n------------\n");*/
		 /*for (Map.Entry entry : Stat.bigramFreq.entrySet()) {
			    System.out.println("Key:  " + entry.getKey() + "  Value: "  + entry.getValue());
		 }*/
		 System.out.println("\n------------\n");
		 //System.out.println("h1 = " + h1 + "\t- один символ\nh2 = " + h2 + "\t- два с пересечением\nh3 = " + h3 + "\t- два без пересечения");
		 System.out.format("h1 = %.5f\t- один символ%nh2 = %.5f\t- два с пересечением%nh3 = %.5f\t- два без пересечения%nR  = %.5f\t- избыточность", h1, h2, h3, getRedundancy(h1));
	 }
	 
	 static void getEntropy() {
		 h1 = 0; h2 = 0; h3=0;
		 int totalCharacters = 0, totalBigrams;
		 try { 
			getFrequencies();
			BufferedReader br = new BufferedReader (new FileReader(Stat.myTextPathDst));
			while(br.read()!=-1) {
				totalCharacters++;
			}
			//System.out.println(totalCharacters);
			totalBigrams = totalCharacters - 1;
			br.close();
			double p;
			
			//энтропия одного символа
			for(Map.Entry<Character, Integer> entry : Stat.singleCharFreq.entrySet()) {
				if(entry.getValue() > 0) {
					p = (double)entry.getValue()/(double)totalCharacters;
					//System.out.println(entry.getValue() + "/" + totalCharacters);
					//System.out.println(p);
					htable1.put(entry.getKey(), -p*log(2, p));
					Stat.h1 += htable1.get(entry.getKey());
					//System.out.println(h1);

				}
			}
			
			//энт. двух с пересечением
			for(Map.Entry<String, Integer> entry : Stat.bigramFreq.entrySet()) {
				if(entry.getValue() > 0) {
					p = (double)entry.getValue()/(double)totalBigrams;
					htable2.put(entry.getKey(), -p*log(2, p));
					Stat.h2 += htable2.get(entry.getKey());
					//System.out.println(h2);
				}
			}
			Stat.h2 /= 2;
			
			//двух без пересечения
			for(Map.Entry<String, Integer> entry : Stat.uBigramFreq.entrySet()) {
				if(entry.getValue() > 0) {
					p = (double)entry.getValue()/(double)(totalBigrams/2);
					htable3.put(entry.getKey(), -p*log(2, p));
					Stat.h3 += htable3.get(entry.getKey());
					//System.out.println(h2);
				}
			}
			Stat.h3 /= 2;
			
		 }
		 catch(IOException e) {
		 	System.out.println(e.getMessage());
		 }
	}
		 
	 
	 static double log(double b, double x) { //log[b]a
		 return Math.log(x)/Math.log(b);
	 }
	 
	 static double getRedundancy(double entropy) {
		 return 1-(h_inf/entropy);
	 }
		
	 static void encrypt(String src, String dst, String key) throws Exception {
		 BufferedReader br = null;
		 BufferedWriter bw = null;
		 try {
			br = new BufferedReader (new FileReader(new File(src)));
			bw = new BufferedWriter(new FileWriter(new File(dst)));
		 }
		 catch (IOException e) {
			 System.out.println("Encryption: Couldn't create BufferedReader or BufferedWriter:");
			 System.out.println(e.getMessage());
		 }
		 System.out.println("\n-----------");
		 System.out.println("Key length = " + key.length());
		 int[] key_arr = new int[key.length()];
		 //конвертация ключа в массив целых числе для сложения с ОТ
		 try {
			 key_arr = Stat.keyIntoIntArr(key);
		 }
		 catch (Exception e) {
			 System.out.println("Error: " + e.getMessage());
		 }
		 /*for(int i=0; i<key_arr.length; i++) {
			 System.out.print(key_arr[i] + " ");
		 }
		 System.out.println();*/
		 
		 char c; // читаемый символ
		 int index=0; //по идее не должен быть инициализирован, но не компилится :с
		 int ic; //читаемый код символа
		 boolean found_flag = false;
		 for(int infctr=0; infctr<500000; infctr++) {
			 try {
				 ic = br.read();
			 } catch (IOException e) {
				 e.printStackTrace();
				 break;
			 }
			 if(ic == -1) break; //конец текста
			 c = (char)ic;
			 
			 found_flag = false; //проверка, есть ли символ в алфавите
			 for(int i=0; i<Stat.alph.length; i++) {
				 if(c == Stat.alph[i]) {
					 index = i;
					 found_flag = true;
					 break;
				 }
			 }
			 if(found_flag == false)  throw new Exception("Errror: Read character not found in alphabet!");
			 
			 bw.write( Stat.alph[ (index + key_arr[infctr % key.length()]) % 32] ); //зашифрование
			 System.out.print(Stat.alph[ (index + key_arr[infctr % key.length()]) % 32]);
			 //if(infctr % 400 == 0) System.out.println();
			 
			 
			 if(infctr >= 499999) System.out.println("ACHTIONG! TOO MANY ITERATIONS!");
		 
		 }
		 bw.close();
		 br.close();
		 
		 
	 }
	 
	 static int[] keyIntoIntArr(String key) throws Exception {
		 //System.out.println("1");
		 int[] arr = new int[key.length()];
		 keyIteration:
		 for(int i=0; i<key.length(); i++) {
			 //System.out.println(2);
			 
			 for(int j=0; j<Stat.alph.length; j++) {
				 //System.out.println(Stat.alph.length);
				 //System.out.println(key.charAt(i) + " != " + Stat.alph[j]);
				 if(key.charAt(i) == Stat.alph[j]) {
					 //System.out.println(key.charAt(i) != Stat.alph[j]);
					 arr[i] = j;
					 continue keyIteration;
				 }
				 
			 }
			 throw new Exception(i + "th character in the key doesn't match with any character in alphabet.");
		 }
		 return arr;
	 }
	 
	 static double getIndexOfAccordance(String src) throws IOException {
		 //System.out.println(src);
		 //System.out.println("here");
		 int text_size = 0; 
		 Map<Character, Integer> numOfChars = new HashMap<Character, Integer>();
		 char c1;
		 for(int i=0; i<Stat.alph.length; i++) {
			 numOfChars.put(Stat.alph[i], 0);
		 }
		 BufferedReader br = new BufferedReader(new FileReader(new File(src)));
		 //br.mark(0);
		 //br.reset();
		 //System.out.println(br.read());
		 int t;
		 while((t=br.read()) != -1) {
			 //System.out.println("here1");
			 //System.out.println((char)t);
			 c1 = (char)t;
			 //System.out.println(c1);
			 numOfChars.put(c1, numOfChars.get(c1)+1);
			 text_size++;
		 }
		 //System.out.println(text_size);
		 //br.close();
		 double sum = 0;
		 int k=0;
		 for(int i = 0; i<Stat.alph.length; i++) {
			 k = numOfChars.get(Stat.alph[i]);
			 sum += k*(k-1);
		 }
		 br.close();
		 return sum/(text_size*(text_size-1));
	 }
	 
	 static void decrypt(String src, String dst) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(src));
		 BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dst)));
		 ArrayList<Double> list = new ArrayList<Double>();
		 //ArrayList<Character> text = new ArrayList<Character>();
		 
		 int t, ctr;
		 for(int r = 1; r<=30; r++) {
			 //System.out.println("r = " + r);
			 br = new BufferedReader(new FileReader(src));
			 bw = new BufferedWriter(new FileWriter(new File(dst)));
			 /*for (t=br.read(), ctr = 0; t!= -1; ctr++) {
				 System.out.println(ctr);
				if(ctr % r == 0) bw.write((char)t); 
				if (ctr > 100000)  {
					bw.close();
					System.out.println("Too many iterations in decrypt()");
					break;
				}
			 }*/
			 ctr = 0;
			 while((t=br.read()) != -1) {
				 ctr++;
				 if(ctr%r == 0 && ctr < 100000) {
					 bw.write((char)t);
				 }
			 }
			 //ctr++;
			 /*if(ctr >= 100000) {
				 System.out.println("Too many iterations in decrypt()");
				 bw.close();
				 br.close();
				 break;
			 }*/
			 br.close();
			 bw.close();
			 list.add(Stat.getIndexOfAccordance(dst));
			 //Collections.sort(list);
			 System.out.format("r = %d,\tIoA = %.2f%n", r, list.get(r-1));
			
		 }
		 
	 }
	 
	 static void caesar(String src, String dst, Map<Character, Integer> frequencies) {
		 
	 }
	 
	 
}
