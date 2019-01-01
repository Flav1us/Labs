package main;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class exe{
/*
*�����������������,������������������������������������������������/������������������������/��������.
*����������������������������������������������������������������������
*
*/
	public static void main(String[] args) throws Exception {
		char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
		Map<Character,Character>replace= new HashMap <Character, Character>();
		replace.put('ё','е');
		
		//����� ����� ��������
		/*Map<Integer,Double>IOA=Stat.IOAforEachR(new File("src\\main\\variant.txt"),alphabet);
		Stat.printMap(IOA);
		IOA=Stat.sortByValue(IOA);
		System.out.println();
		Stat.printMap(IOA);
		System.out.println(Stat.decideKeyLength(Stat.IOAforEachR(new File("src\\main\\variant.txt"),alphabet),0));*/
		
		//���� ������
		//Stat.format(new File("src\\main\\needsToBeFormated.txt"), new File("src\\main\\formatedArchmage.txt"), alphabet, replace);
		//Stat.format(new File("src\\main\\temp\\caesarTestBasic.txt"), new File("src\\main\\temp\\caesarTestFormated.txt"), alphabet, replace);
		//Stat.decodeCaesar(new File("src\\main\\temp\\caesarTestformated.txt"), new File("src\\main\\temp\\caesarTestDecoded.txt"), Stat.getSingleCharFrequencies(new File("src\\main\\formatedArchmage.txt"), alphabet), 0);
		
		Stat.decodeVigenere(new File("src\\main\\variant.txt"), new File("src\\main\\DECODED.txt"), new File("src\\main\\needsToBeFormated.txt"));
		System.out.println("done");
		//Stat.showFrequencies();
		
		//Stat.showAllIOA();
		
		//Stat.saveBigramFrequencies();

	}

}


