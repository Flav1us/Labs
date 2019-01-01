package main;

import java.util.Map;
import java.util.Map.Entry;

public class Util {
	
	static <K,V> void printMap(Map<K, V> map) {
		for(Entry<K,V> i : map.entrySet()) {
			System.out.println(/*"key: " + */i.getKey() + "\t" + i.getValue());
		}
	}
	
	static void printBiFreqAsTable(Map<String, Integer> BF, char[] alphabet) {
		//int ctr = 0;
		//while (ctr < 32*32) {
			//
			System.out.print("\n\t");
			for(int i=0; i<alphabet.length; i++) System.out.print(alphabet[i] + "\t");
			System.out.println();
			for(int i=0; i<alphabet.length; i++) {
				System.out.print(alphabet[i]+"\t");
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
}
