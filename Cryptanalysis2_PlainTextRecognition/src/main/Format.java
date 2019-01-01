package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class Format {
	
	public static void format(File src, File dst, char[] alphabet, Map<Character, Character> replace) throws IOException {
		boolean withSpaces = true;
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
			//System.out.print(c);
			
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
}
