package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	//by: Malyshko, Dykii
	//variant 6: criterion 1.0-1.3, 3.0
	static char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', ' ', 'ы', 'ь', 'э', 'ю', 'я'};
	static Map<Character, Character> replace = new HashMap<Character, Character>() {{
		put('ё', 'е');
		put('ъ', 'ь');
	}};
	static File src = new File("resources\\hpmor_untouched.txt"); //changed txt file encoding: 3000kB -> 6400kB
	static File formated = new File("resources\\hpmor_formated.txt");

	public static void main(String[] args) throws IOException {
		
		long time0 = System.currentTimeMillis();
		
		//Format.format(src, formated, alphabet, replace); //uncomment for re-formating
		
		Map<Character, Integer> scfreq = Frequencies.getSingleCharFrequencies(formated, alphabet);
		Util.printMap(scfreq);
		
		Map<String, Integer> bifreq = Frequencies.getBigramFrequencies(formated, alphabet, true);
		Util.printBiFreqAsTable(bifreq, alphabet);
		
		List<String> A_prh = Frequencies.getProhibitedBigrams(bifreq);
		
		System.out.println("prohibited:");
		for(String bi : A_prh) System.out.print(bi + "\t");
		System.out.println();
		


		System.out.println("time taken: " + (System.currentTimeMillis() - time0) + " ms");
	}


	
	
	
}
