/*****************************
  Компьютерный практикум №1
  ФІ-43, А. Малышко, В. Лещенко
 *****************************/

package main;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Stat {
	static char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
	static Map<Character, Character> replace = new HashMap<Character, Character>() {{
		put('ё', 'е');
	}};
	/*static char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', ' ', 'ы', 'ь', 'э', 'ю', 'я'};
	static Map<Character, Character> replace = new HashMap<Character, Character>() {{
		put('ё', 'е');
		put('ъ', 'ь');
	}};*/
	
	//shifts - корректировка подбора ключа Цезарей, поправка частотного анализа
	static int[] shifts = {0,4,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//static int[] shifts = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	static void format() throws IOException {
		File src = new File("src\\main\\needsToBeFormated.txt");
		File dst = new File("src\\main\\formatedArchmage.txt");
		//dst.createNewFile();
		//char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', ' ', 'ы', 'ь', 'э', 'ю', 'я'};
		Map<Character, Character> replace = new HashMap<Character, Character>();
		replace.put('ё', 'е');
		replace.put('ъ', 'ь');
		Stat.format(src, dst, alphabet, replace);
		//dst.createNewFile();
	}
	
	static <K,V> void printMap(Map<K, V> map) {
		for(Entry<K,V> i : map.entrySet()) {
			System.out.println(/*"key: " + */i.getKey() + "\t" + i.getValue());
		}
	}
	
	static void printBiFreqAsTable(Map<String, Integer> BF) {
		//int ctr = 0;
		//while (ctr < 32*32) {
			//
			for(int i=0; i<alphabet.length; i++) {
				//System.out.println(alphabet[i]+"\t");
				for(int j=0; j<alphabet.length; j++) {
					for(Map.Entry<String, Integer> ent : BF.entrySet()) {
						if(ent.getKey().equals(Character.toString(alphabet[i])+Character.toString(alphabet[j]))) {
							System.out.print(ent.getValue() + "\t");
							//System.out.println("kappa");
						}
					}
				}
				System.out.println();
			}
		//}
	}
	
	static void showFrequencies() throws IOException {
		/*char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', ' ', 'ы', 'ь', 'э', 'ю', 'я'};
		Map<Character, Character> replace = new HashMap<Character, Character>();
		replace.put('ё', 'е');
		replace.put('ъ', 'ь');*/
		Stat.format(new File("src\\main\\needsToBeFormated.txt"), new File("src\\main\\formatedArchmage.txt"), alphabet, replace);
		Map<Character, Integer> sChFreq = Stat.getSingleCharFrequencies(new File("src\\main\\formatedArchmage.txt"), alphabet);
		sChFreq = Stat.sortByValue(sChFreq);
		Stat.printMap(sChFreq);
		System.out.println("-----------------");
		Map<String, Integer> bFreq = Stat.getBigramFrequencies(new File("src\\main\\formatedArchmage.txt"), alphabet, false);
		Stat.printBiFreqAsTable(bFreq);
		System.out.println("-----------------");
		Stat.printMap(bFreq);
		System.out.println("-----------------");
		Map<String, Integer> boFreq = Stat.getBigramFrequencies(new File("src\\main\\formatedArchmage.txt"), alphabet, true);
		Stat.printBiFreqAsTable(boFreq);
		System.out.println("-----------------");
		Stat.printMap(boFreq);
		System.out.println("-----------------");
	}
	
	static void format(File src, File dst, char[] alphabet, Map<Character, Character> replace) throws IOException {
		//File result = new File("src\\main\\formatedFile.txt");
		boolean withSpaces = false;
		for(int i=0; i<alphabet.length; i++) {
			if(alphabet[i] == ' ') withSpaces = true;
		}
		BufferedReader br = new BufferedReader (new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dst));
		int readChar; // .read читает код символа
		char c;
		int breakctr = 0;
		boolean found, prevIsSpace = true;
		while((readChar=br.read()) != -1) {
			breakctr++;
			if (breakctr > 10000000) {
				//на случай бесконечного цикла
				System.out.println("\"while\" is too long");
				break;
			}
			found = false; //для избежания излишнего поиска в алфавите, после нахождения нужной буквы прерываем поиск.
			
			c = Character.toLowerCase((char)readChar); //превращаем прочитанный код символа собственно в символ
			
			for(Entry<Character, Character> entry : replace.entrySet()) {
				if(c == entry.getKey())  { // 33 буквы алфавита ужимаем в 32 путём замены "ё" на "е".
					bw.write(entry.getValue());
					prevIsSpace = false;
					continue; // нашли "ё" - не надо перебирать алфавит.
				}
			}
			
			for(int i=0; i<alphabet.length; i++) {
				if(c == alphabet[i] && found == false) {
					//System.out.print(c);
					switch(c){
						case ' ':
							if(prevIsSpace) break;
							bw.write(c);
							prevIsSpace = true;
							break;
						default:
							bw.write(c);
							prevIsSpace = false;
					}
					//bw.write(c);
					/*if (c == ' ') prevIsSpace = true;
					else prevIsSpace = false;*/
					found = true;
				}
			}
			if (!found && !prevIsSpace && withSpaces) {
				bw.write(' ');
				prevIsSpace = true;
			}
		}
		br.close();
		bw.close();
		//return result;
	}
	
	
	static Map<Character, Integer> getSingleCharFrequencies(File src, char[] alphabet) throws IOException {
		Map<Character, Integer> frequencies = new HashMap<Character, Integer>();
		char c;
		 for(int i=0; i<alphabet.length; i++) {
			 frequencies.put(alphabet[i], 0);
		 }
		 BufferedReader br = new BufferedReader (new FileReader(src));
		 //c1=(char)br.read();
		 //frequencies.put(c1, frequencies.get(c1)+1);
		 int t;
		 while((t=br.read()) != -1) {
			 c = (char)t;
			 //System.out.println(c);
			 frequencies.put(c, frequencies.get(c)+1);
		 }
		 br.close();
		return frequencies;
	}
	
	
	static Map<String, Integer> getBigramFrequencies(File src, char[] alphabet, boolean overlay) throws IOException {
		 Map<String, Integer> frequencies = new HashMap<String, Integer>();
		 char c1, c2;
		 String bi;
		 for(int i=0; i<alphabet.length; i++) {
			 for(int j=0; j<alphabet.length; j++) {
				 //заполняем хэшмап всевозможными биграммами
				 frequencies.put(Character.toString(alphabet[i]).concat(Character.toString(alphabet[j])), 0);
			 }
		 }
		 BufferedReader br = new BufferedReader (new FileReader(src));
		 int t;
		 int ctr = 0;
		 c1=(char)br.read();
		 while((t=br.read()) != -1) {
			 c2 = (char)t;
			 bi=Character.toString(c1).concat(Character.toString(c2));
			 if(overlay == true) frequencies.put(bi, frequencies.get(bi) + 1); //если без наложения, суём всё
			 else if (ctr % 2 == 0) { //если "с", суём кажую вторую
				 frequencies.put(bi, frequencies.get(bi) + 1);
			 }
			 ctr++;
			 c1 = c2;
		 }
		 br.close();
		 return frequencies;
	}
	
	
	static void saveBigramFrequencies() throws IOException {
		File src = new File("src\\main\\needsToBeFormated.txt");
		File dst = new File("src\\main\\bigram_frequencies\\formated.txt");
		Stat.format(src, dst, alphabet, replace);
		//without spaces:
		//without overlay:
		
		Map<String, Integer> UBFreqWoS = Stat.getBigramFrequencies(dst, alphabet, false);
		//with overlay:
		Map<String, Integer> OBFreqWoS = Stat.getBigramFrequencies(dst, alphabet, true);
		Stat.alphabet[26] = ' ';
		Stat.replace.put('ъ', 'ь');
		Stat.format(src, dst, alphabet, replace);
		//with spaces:
		//without overlay:
		Map<String, Integer> UBFreqS = Stat.getBigramFrequencies(dst, alphabet, false);
		//with overlay
		Map<String, Integer> OBFreqS = Stat.getBigramFrequencies(dst, alphabet, true);
		UBFreqWoS = Stat.sortByValue(UBFreqWoS);
		OBFreqWoS = Stat.sortByValue(OBFreqWoS);
		UBFreqS = Stat.sortByValue(UBFreqS);
		OBFreqS = Stat.sortByValue(OBFreqS);
		Stat.saveMap(UBFreqWoS, new File("src\\main\\bigram_frequencies\\NoSpacesNOOverlay.txt"));
		Stat.saveMap(OBFreqWoS, new File("src\\main\\bigram_frequencies\\NoSpacesOverlay.txt"));
		Stat.saveMap(UBFreqS, new File("src\\main\\bigram_frequencies\\SpacesNOOverlay.txt"));
		Stat.saveMap(OBFreqS, new File("src\\main\\bigram_frequencies\\SpacesOverlay.txt"));
		Stat.alphabet[26] = 'ъ';
		Stat.replace.remove('ъ');
	}
	
	static void saveMap(Map<String, Integer> src, File dst) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(dst));
		//char[] cbuf;
		for(Map.Entry<String, Integer> i : src.entrySet()) {
			StringBuilder t = new StringBuilder();
			t.append(i.getKey()).append(":").append(i.getValue()).append(',');
			bw.write(t.toString().toCharArray());
		}
		bw.close();
	}
	
	
	static double getIndexOfAccordance(File src, char[] alphabet) throws IOException {
		 int text_size = 0; 
		 Map<Character, Integer> numOfChars = new HashMap<Character, Integer>();
		 char c1;
		 for(int i=0; i<alphabet.length; i++) {
			 numOfChars.put(alphabet[i], 0);
		 }
		 BufferedReader br = new BufferedReader(new FileReader(src));
		 int t;
		 while((t=br.read()) != -1) {
			 c1 = (char)t;
			 //System.out.println(c1);
			 numOfChars.put(c1, numOfChars.get(c1)+1);
			 text_size++;
		 }
		 double sum = 0;
		 int k=0;
		 for(int i = 0; i<alphabet.length; i++) {
			 k = numOfChars.get(alphabet[i]);
			 sum += k*(k-1);
		 }
		 br.close();
		 return sum/(text_size*(text_size-1));
	 }
	
	
	static void rthSymbol(File src, File dst, int r, int shift) throws IOException { //shift - начальный сдвиг, льбрасываем первые shift символов
		BufferedReader br = new BufferedReader(new FileReader(src));
		//File dst = new File("src\\main\\rthSymbol.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(dst));
		int ctr = 0, t;
		for(int i=0; i<shift; i++) br.read();
		while((t=br.read()) != -1) {
		 ctr++;
		 	if(ctr%r == 0 && ctr < 1000000) {
		 		bw.write((char)t);
		 	}
		 	if(ctr > 1000000) System.out.println("too many operations while selecting rth symbol");
		}
		br.close();
		bw.close();
		//return dst;
	}
	
	static Map<Integer, Double> IOAforEachR(File src, char[] alphabet) throws IOException {
		Map<Integer, Double> IOA = new HashMap<Integer, Double>();
		File tmp = new File("src\\main\\temp\\tempIOAforEachR.txt");
		for(int r=1; r<=30; r++) {
			rthSymbol(src, tmp, r, 0);
			IOA.put(r, Stat.getIndexOfAccordance(tmp, alphabet));
			//IOA2.put(Stat.getIndexOfAccordance(rthSymbol(src, r), alphabet),r);
		}
		return IOA;
	}
	
	static int decideKeyLength(Map<Integer, Double> IOA, int defaultFromTop) {
		//System.out.println(IOA);
		ArrayList<Integer> val = new ArrayList(Stat.sortByValue(IOA).keySet());
		
		System.out.println("decided Vigenere key length:" + val.get(defaultFromTop));
		return val.get(0);
	}
	
	static void decodeCaesar(File src, File dst, Map<Character, Integer> langFreq, int which_letter) throws IOException {
		//whichLetter - если самая часто встречаемая буква не подошла (whichLetter = 0 не подошло), выбираем следующую по частоте - whichletter = 1
		//читаем из файла с текстом, зашифрованным Цезарем
		BufferedReader br = new BufferedReader(new FileReader(src));
		//File dst = new File("src\\main\\tempForCaesar.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(dst));
		//тут - отсортированные по убыванию количества в большом текстовом файле буквы
		ArrayList<Character> langMostCommonLetters = new ArrayList<Character>(Stat.sortByValue(langFreq).keySet());
		Map<Character, Integer> srcFreq = Stat.getSingleCharFrequencies(src, alphabet);
		//тут - из файла с Цезарем
		ArrayList<Character> srcMostCommonLetters = new ArrayList<Character>(Stat.sortByValue(srcFreq).keySet());
		char langMCL = langMostCommonLetters.get(0), srcMCL = srcMostCommonLetters.get(which_letter); //language most common (or prefered) letter, source file most common letter
		//System.out.println("srcMCL = "+srcMCL);
		int langMCLpos=-1, srcMCLpos=-1; //position in alphabet
		//находим буквы в алфавите
		langMCLpos = Stat.posInAlph(langMCL);
		srcMCLpos = Stat.posInAlph(srcMCL);
		if(langMCLpos == -1 || srcMCLpos == -1) System.err.println("ERROR: CHARACTER NOT FOUND in decodeCaesar");
		int key = mod32(srcMCLpos - langMCLpos); // ключ шифра Цезаря
		System.out.println("key: " + alphabet[key] + "\t("+key+")");
		int t;
		while((t = br.read()) != -1) {
			bw.write(alphabet[mod32(posInAlph((char)t) - key)]); // расшифрование
		}
		br.close();
		bw.close();
		//return dst;
	}
	
	static int posInAlph(char c) {
		for(int i=0; i<alphabet.length; i++) { //находим буквы в алфавите
			if(c == alphabet[i]) return i;
		}
		System.err.println("ERROR: CHARACTER NOT FOUND in alphabet (decodeCaesar)");
		return -1; //should never come here
	}
	
	static void decodeVigenere(File src, File dst, File langFreqSrc) throws IOException {
		//File res = new File("src\\main\\DECODED.txt");
		File formated = new File("src\\main\\decode\\formated.txt");
		Stat.format(langFreqSrc, formated, alphabet, replace);
		File variant = new File("src\\main\\variant.txt");
		int key_length = Stat.decideKeyLength(Stat.IOAforEachR(variant, alphabet), 0);
		BufferedWriter bw = new BufferedWriter(new FileWriter(dst));
		ArrayList<File> /*caesarsInSrc = new ArrayList<File>(),*/ decodedCaesars = new ArrayList<File>();
		//корректировка цезарей
		for(int i = 0; i < key_length; i++) {
			System.out.print(i + ":\t");
			File rthSymbol = new File("src\\main\\decode\\i"+i+".txt"); 
			Stat.rthSymbol(new File("src\\main\\variant.txt"), rthSymbol, key_length, i);
			File decodedCaesar = new File("src\\main\\decode\\decodedCaesar"+i+".txt");
			Stat.decodeCaesar(rthSymbol, decodedCaesar, Stat.getSingleCharFrequencies(formated, alphabet), shifts[i]);
			decodedCaesars.add(decodedCaesar);
		}
		ArrayList<BufferedReader> abr = new ArrayList<BufferedReader>() {{
			for(int i=0; i<key_length; i++) {
				add(new BufferedReader(new FileReader("src\\main\\decode\\decodedCaesar"+i+".txt")));
			}
		}};
		int t = 0;
		text_reading:
		while(t != -1) {
			for(int i=0; i<key_length; i++){
				if((t = abr.get(i).read()) != -1) {
					bw.write(t);
				}
				else break text_reading;
			}
		}
		for(BufferedReader br : abr) {
			br.close();
		}
		bw.close();
		//return res;
	}
	
	//индексы соответствия для 
	 static void encrypt(File src, File dst, String key) throws Exception {
		 BufferedReader br = null;
		 BufferedWriter bw = null;
		 try {
			br = new BufferedReader (new FileReader(src));
			bw = new BufferedWriter(new FileWriter(dst));
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
			 key_arr = Stat.keyIntoIntArr(key, alphabet);
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
			 for(int i=0; i<alphabet.length; i++) {
				 if(c == alphabet[i]) {
					 index = i;
					 found_flag = true;
					 break;
				 }
			 }
			 if(found_flag == false)  throw new Exception("Errror: Read character not found in alphabet!");
			 
			 bw.write( alphabet[ (index + key_arr[infctr % key.length()]) % 32] ); //зашифрование
			 System.out.print(alphabet[ (index + key_arr[infctr % key.length()]) % 32]);
			 //if(infctr % 400 == 0) System.out.println();
			 
			 
			 if(infctr >= 499999) System.out.println("ACHTIONG! TOO MANY ITERATIONS!");
		 
		 }
		 bw.close();
		 br.close();
		 
		 
	 }
	
	 static int[] keyIntoIntArr(String key, char[] alphabet) throws Exception {
		 //System.out.println("1");
		 int[] arr = new int[key.length()];
		 keyIteration:
		 for(int i=0; i<key.length(); i++) {
			 //System.out.println(2);
			 
			 for(int j=0; j<alphabet.length; j++) {
				 //System.out.println(alphabet.length);
				 //System.out.println(key.charAt(i) + " != " + alphabet[j]);
				 if(key.charAt(i) == alphabet[j]) {
					 //System.out.println(key.charAt(i) != alphabet[j]);
					 arr[i] = j;
					 continue keyIteration;
				 }
				 
			 }
			 throw new Exception(i + "th character in the key doesn't match with any character in alphabet.");
		 }
		 return arr;
	 }
	 
	 static Map<Integer, Integer> getAllIOA(String[] keys, ArrayList<File> files) throws IOException {
		 Map result = new HashMap<Integer, Integer>();
		 int k = 0;
		 for(File i : files) {
			 result.put(k, Stat.getIndexOfAccordance(i, alphabet));
			 k++;
		 }
		 k=0;
		 return result;
	 }
	 
	 static ArrayList<File> encryptSet(String[] keys, File src) throws Exception {
		 ArrayList<File> res = new ArrayList<File>();
		 File[] files = new File[10];
		 for(int i = 0; i < keys.length; i++) {
			 files[i] = new File("src\\main\\allIOA\\"+i+".txt");
			 Stat.encrypt(src, files[i], keys[i]);
			 System.out.println();
			 System.out.println(files[i].toPath().toString());
			 res.add(files[i]);
		 }
		 if (res==null) throw new Exception("empty key set");
		 return res;
	 }
	
	 static void showAllIOA() throws Exception {
		 File src = new File("src\\main\\allIOA\\src.txt");
		 String[] keys = {"уж", "бык", "утка", "цапля", /*"чтозазверь",*/ "выхухляндия"};
		 ArrayList<File> files = Stat.encryptSet(keys, src);
		 Map<Integer, Integer> forPrint = Stat.getAllIOA(keys, files);
		 Stat.printMap(forPrint);
	 }
	 
	 
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
	static int mod32(int arg) {
		while(arg<0) arg+=32;
		return arg%32;
	}
	
}
