package main;

//import java.io.IOException;
import java.util.ArrayList;

public class exe {

	public static void main(String[] args) throws Exception {
		//������ �������� formatTextFile ��������� �� ������� ������� � ��������. ��� false ������ ���� - ������ ����.
		Stat.formatTextFile(Stat.myTextPathSrc,Stat.myTextPathDst, false);
		Stat.getEntropy();
		Stat.printResults();
		//Stat.formatTextFile(Stat.myTextPathSrc,Stat.myTextPathDst, true);
		/*Stat.getEntropy();
		Stat.printResults();
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("��");
		keys.add("���");
		keys.add("����");
		keys.add("�����");
		keys.add("��������������");
		for(String key : keys) {
			Stat.encrypt(Stat.textToEncrypt, Stat.encryptedText, key);
			System.out.format("%n%.6f - ������ ������������", Stat.getIndexOfAccordance(Stat.encryptedText));
		}*/
		System.out.println("\n---------------\n");
		
		//System.out.println(Stat.alph.length);
		/*System.out.println("\ndone");
		String fileToDecrypt = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\textToDecrypt.txt"; //�� variants
		String tempDecryptedFile = "C:\\main\\Programs\\JavaWorkspace\\SymCrypt_1\\src\\main\\tempDecryptedFile.txt"; //��� ������ r-� ����� � ����� �� 1 �� 30
		System.out.format("%n%.6f - ������ ������������ ����� ��������%n", Stat.getIndexOfAccordance(fileToDecrypt));
		Stat.decrypt(fileToDecrypt, tempDecryptedFile);*/
		//System.out.println(Stat.getIndexOfAccordance(tempDecryptedFile));
		
	}

}

/*
 * cool pink program:
 * h10: 1.755 < h10 < 2.459
 * h20: 1.421 < h20 < 2.080
 * h30: 1.455 < h30 < 2.167
 */
