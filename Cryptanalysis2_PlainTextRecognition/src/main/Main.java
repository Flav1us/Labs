package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	//by: Malyshko, Dykii
	//variant 6: criterion 1.0-1.3, 3.0
	public static char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', ' ', 'ы', 'ь', 'э', 'ю', 'я'};
	public static Map<Character, Character> replace = new HashMap<Character, Character>() {{
		put('ё', 'е');
		put('ъ', 'ь');
	}};
	static File src = new File("resources\\hpmor_untouched.txt"); //changed txt file encoding: 3000kB -> 6400kB
	static File formated = new File("resources\\hpmor_formated.txt");

	public static void main(String[] args) throws IOException {
		
		long time0 = System.currentTimeMillis();
		

		testForEachL();
		

		System.out.println("time taken: " + (System.currentTimeMillis() - time0) + " ms");
	}
	
	public static void testForEachL() throws IOException {
		File test_src = new File("resources\\voina-i-mir_formated.txt");
		BufferedReader test_src_fr = new BufferedReader(new FileReader(test_src));
		
		File text = new File("resources\\text.txt");
		
		BufferedReader unenc_fr = new BufferedReader(new FileReader(text));
		File text_enc = new File("resources\\text_enc.txt");
		
		BufferedReader enc_fr = new BufferedReader(new FileReader(text_enc));

		int a = 7, b = 2;
		int kp = 3;
		
		char[] cbuf;
		int[][] errors;
		Criterion_1_1 cr = new Criterion_1_1(formated);
		int len = 1000;
		int num = 1000;
		cbuf = new char[len];
		errors = new int[2][2];
		for(int i = 0; i < num; i++) {
			test_src_fr.read(cbuf);
			System.out.println(String.valueOf(cbuf).substring(0, 10));
			//System.out.println(cbuf);
			BufferedWriter unenc_fw = new BufferedWriter(new FileWriter(text));
			unenc_fw.write(cbuf);
			unenc_fw.close();
			char[] enc = Encrypt.affine(cbuf);
			BufferedWriter enc_fw = new BufferedWriter(new FileWriter(text_enc));
			enc_fw.write(enc);
			enc_fw.close();
			c11_test(text, text_enc, errors, cr, kp);
		}
		System.out.println("errors:\n"+errors[0][0]+"\t"+errors[0][1]+"\n"+errors[1][0]+"\t"+errors[1][1]);

			
		test_src_fr.close();
		
		unenc_fr.close();
		
		enc_fr.close();
	}

	private static void c30_test(File text, File text_enc, int[][] errors, Criterion_3_0 cr) throws IOException {
		if (cr.isPlainText(text)) {
			errors[0][0]++;
		} else {
			errors[1][0]++;
		}
		if (cr.isPlainText(text_enc)) {
			errors[0][1]++;
		} else {
			errors[1][1]++;
		}
	}

	private static void c13_test(File text, File text_enc, int[][] errors, Criterion_1_3 cr) throws IOException {
		if (cr.isPlainText(text)) {
			errors[0][0]++;
		} else {
			errors[1][0]++;
		}
		if (cr.isPlainText(text_enc)) {
			errors[0][1]++;
		} else {
			errors[1][1]++;
		}
	}

	private static void c12_test(File text, File text_enc, int[][] errors, Criterion_1_2 cr) throws IOException {
		if (cr.isPlainText(text)) {
			errors[0][0]++;
		} else {
			errors[1][0]++;
		}
		if (cr.isPlainText(text_enc)) {
			errors[0][1]++;
		} else {
			errors[1][1]++;
		}
		
	}

	private static void full_test1_1() throws IOException {
		File test_src = new File("resources\\voina-i-mir_formated.txt");
		BufferedReader test_src_fr = new BufferedReader(new FileReader(test_src));
		
		File text = new File("resources\\text.txt");
		
		BufferedReader unenc_fr = new BufferedReader(new FileReader(text));
		File text_enc = new File("resources\\text_enc.txt");
		
		BufferedReader enc_fr = new BufferedReader(new FileReader(text_enc));

		int a = 7, b = 2;
		int kp = 3;
		
		char[] cbuf;
		int[][] errors;
		Criterion_1_1 cr = new Criterion_1_1(formated);
		int len = 10;
		int num = 1000;
		cbuf = new char[len];
		errors = new int[2][2];
		for(int i = 0; i < num; i++) {
			test_src_fr.read(cbuf);
			//System.out.println(cbuf);
			BufferedWriter unenc_fw = new BufferedWriter(new FileWriter(text));
			unenc_fw.write(cbuf);
			unenc_fw.close();
			//char[] enc = Encrypt.affine(cbuf, a, b);
			char[] enc = Encrypt.affine(cbuf);
			BufferedWriter enc_fw = new BufferedWriter(new FileWriter(text_enc));
			enc_fw.write(enc);
			enc_fw.close();
			c11_test(text, text_enc, errors, cr, kp);
		}
		System.out.println("errors:\n"+errors[0][0]+"\t"+errors[0][1]+"\n"+errors[1][0]+"\t"+errors[1][1]);

			
		test_src_fr.close();
		
		unenc_fr.close();
		
		enc_fr.close();
	}

	private static void c11_test(File text, File text_enc, int[][] errors, Criterion_1_1 cr, int kp) throws IOException {
		if (cr.isPlainText(text, kp)) {
			errors[0][0]++;
		} else {
			errors[1][0]++;
		}
		if (cr.isPlainText(text_enc, kp)) {
			errors[0][1]++;
		} else {
			errors[1][1]++;
		}
	}

	private static void c10_test(File text, File text_enc, int[][] errors, Criterion_1_0 cr10) throws IOException {
		if (cr10.isPlainText(text)) {
			errors[0][0]++;
		} else {
			errors[1][0]++;
		}
		if (cr10.isPlainText(text_enc)) {
			errors[0][1]++;
		} else {
			errors[1][1]++;
		}
	}

	private static void A_prh() throws IOException {
		//Format.format(src, formated, alphabet, replace); //uncomment for re-formating
		
		Map<Character, Integer> scfreq = Frequencies.getSingleCharFrequencies(formated, alphabet);
		Util.printMap(scfreq);
		
		Map<String, Integer> bifreq = Frequencies.getBigramQuantities(formated, alphabet, true);
		Util.printBiFreqAsTable(bifreq, alphabet);
		
		List<String> A_prh = Frequencies.getProhibitedBigrams(bifreq);
		
		System.out.println("prohibited:");
		for(String bi : A_prh) System.out.print(bi + "\t");
		System.out.println();
	}


	
	
	
}
