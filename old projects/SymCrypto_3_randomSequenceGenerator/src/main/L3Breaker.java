package main;

/*********************************
 омпьютерный практикум є3
‘≤-43, ј. ћалышко, ¬. Ћещенко
*******************************/

import java.util.ArrayList;

public class L3Breaker {
	public static int llen = 27;
	public static int[] lpoly = {0, 22, 25, 26, 27};
	//public static String sz = "10101010100010000001001011001101001011010110011011011010001000111101101000000010000110110011110100011100111101111111111001000001110100000100111111101010010010001111101011010110001001111111011100110111101000010010110101010101001100010100111001101111100000100011101011010101000001111110101011101010010101000100000101000000001111100011101110100100111000010010011101001011101001000011110010010011001101100100000110101010100010011110101000000000010010101100010111000101110100000101001000101111010110110000111011011011001111000010011010001011111010101111001110011111001111011100111000011000111100011110110111110100000110011001000100101011111011101110111011001111011110010110010111011000000111100110111010011000010001101011110011111010111100101110100000110101001101101110010011000111110000100110001100010101001011000100101001100101110101111100111111001100100101010010100010000011110010100010001101011011001111110101001001001011010001011000001000000001011110110100000000100110000010110011000000011001111001110011100111101000011101100101101110011001110111110100101101011000100010010010110110010000100110100111000101110010010100101011011011110000001101100010000101011000000001010110000000010101011100110000100101011101100000001111101101000000001100011011001010101000111110111100001101111101111100101100111000000111010100100100010110111011000110000001101011011011011001010010011010100101111010111000110100101010011111100101110101011001111010101101100001001001101110000110100100000001000001110101010010000011111111001000101101011101101011100110010101011100111111101000111111001101001001111101110100111000110001111001100100100001011101001011001100100111010010000010010100011101100010001000110000101011100101100100111010001110001001010110010011111111101010010101101101011001100001000011001000100110110010111000011101011111000100100111101110111010111010000100110010100001010000111011010011010011100111001101100111111110100010001101101010000011001010001110110000111101001110001110010010100001111000000010101110111111011010001101001101010000000011010010101000011001";
	static String sz = RandSeqGen.sz;
	public static boolean[] z;
	
	//l*res - полученный результат фильтрации; l* - массив, содержащий послдовательность, сгенерированную LRє*. l*i - итератор l*res.
	static boolean[][] filter(ArrayList<boolean[]> l1res, ArrayList<boolean[]>l2res/*, int[] l3poly, int l3regSize*/) throws Exception {
		System.out.println("Breaking L3");
		System.out.println("lres lengths: " + l1res.size() + " " + l2res.size());
		double twopowllen = Math.pow(2, llen);
		long start_timer = System.currentTimeMillis();
		z = RandSeqGen.sToBA(sz);
		int zlen = z.length;
		boolean[][] res = new boolean[3][];
		boolean[] /*l1, l2, l3,*/ x;
		LR lr1 = new LR(new boolean[L1Breaker.llen], L1Breaker.lpoly);
		LR lr2 = new LR(new boolean[L2Breaker.llen], L2Breaker.lpoly);
		LR lr3 = new LR(new boolean[L3Breaker.llen], L3Breaker.lpoly);
		//по всем возмжным парам из l1res, l2res:
		for(int l1i=0; l1i<l1res.size(); l1i++) {
			//l1 = l1res.get(l1i);
			lr1.init = l1res.get(l1i);
			System.out.println("l1res.get(l1i) = " + l1res.get(l1i));
			//l1 = lr1.generSeq(zlen);
			for(int l2i=0; l2i<l2res.size(); l2i++) {
				//System.out.print(".");
				//l2 = l2res.get(l2i);
				lr2.init = l2res.get(l2i);
				//l2 = lr2.generSeq(zlen);
				res[2] = getL3Init(lr1, lr2, lr3);
				if(res[2] != null) {
					res[0] = lr1.init;
					res[1] = lr2.init;
					return res;
 				}
				//перебираем все возможные состо€ни€ третьего регистра:
				/*x = new boolean[llen];
				long timer = System.currentTimeMillis();
				l3iteration:
				for(int i=1; i<twopowllen; i++) {
					//if((i+1)% 1000 == 0) timer = System.currentTimeMillis();
					//TODO счЄтчик
					//if(i%)System.out.print(".");
					if((i+1)% 1000 == 0) System.out.println(System.currentTimeMillis() - timer);
					for(int j=llen-1; j>=0; j--) { //от конца к началу, если 0 то мен€ем на 1 и заканчиваем, если 1 - мен€ем на 0 и продолжаем
						if(x[j]==false) {
							x[j] = true;
							break;
						}
						x[j] = false;
					}
					//тело перебора: провер€ем правильность, сравнива€ сгенерированную ƒжиффи последовательность с эталонной z:
					lr3.init = x;
					//System.out.println(x.length + "\t" + lr3.reglen);
					
					l3 = lr3.generSeq(zlen);
					//System.out.println(i);
					for(int k=0; k<zlen; k++) {
						//[i]?
						if(((l3[k]&l1[k]) ^ ((!l3[k])&l2[k])) != z[k] ) {
							continue l3iteration;
						}
					}
					//сюда попадаем, если все символы совпали
					boolean[][] possibleResult = new boolean[3][];
					possibleResult[0] = lr1.init.clone();
					possibleResult[1] = lr2.init.clone();
					possibleResult[2] = lr3.init.clone();
					System.out.println("\npossible result found:");
					for(int l=0; l<3; l++) RandSeqGen.printBoolArr(possibleResult[l]);
					res = possibleResult;
				}*/
				
			}
		}
		if(res==null) throw new Exception("no possible init vectors of L3 found");
		return res;
	}
		
	
	
	public static boolean[] getL3Init(LR l1, LR l2, LR l3) throws IllegalArgumentException{
		//возвращает null, если l1, l2, z несовместимы
		boolean check = false; //проверка корректности заполнени€ l1, l2
		for(int i=0; i<l1.init.length; i++) if(l1.init[i]) check = true;
		if(!check) throw new IllegalArgumentException("zero init vector is not allowed");
		check = false;
		for(int i=0; i<l2.init.length; i++) if(l2.init[i]) check = true;
		if(!check) throw new IllegalArgumentException("zero init vector is not allowed");
		
		boolean[][] rawIndexes = getUndefinedIndexes(l1.init, l2.init); //есть возможность не исключить из перебора часть значений за счет того, что если, например, l1.init[i]==1 != l2.init[i]==0, z[i]==1 то l3.init[i] должно однозначно иметь значение l1.init[i].
		//кроме того, провер€ет, возможен ли вообще данный вариант
		if(rawIndexes == null) return null;
		int undefIndexLength = 0; //длина перебора
		for(int i=0; i<rawIndexes[0].length; i++) if(!rawIndexes[0][i]) undefIndexLength++;
		System.out.println("uil " + undefIndexLength);
		//boolean[] l3i = rawIndexes[1].clone();
		boolean[] x = new boolean[undefIndexLength];
		boolean[] l3seq;
		boolean[] l1seq = l1.generSeq(z.length);
		boolean[] l2seq = l2.generSeq(z.length);
		int ctr = 0;
		for(int i=0; i<(int)Math.pow(2, undefIndexLength)-1; i++) { //-1 - чтоб не ушло в 100...0
			for(int j=undefIndexLength-1; j>=0; j--) { //от конца к началу, если 0 то мен€ем на 1 и заканчиваем, если 1 - мен€ем на 0 и продолжаем
				if(x[j]==false) {
					x[j] = true;
					break;
				}
				x[j] = false;
			}
			ctr=0; 
			for(int j=0; j<rawIndexes[0].length; j++) {
				if(!rawIndexes[0][j]) {
					rawIndexes[1][j] = x[ctr];
					ctr++;
				}
			}
			l3.init = rawIndexes[1];
			l3seq = l3.generSeq(z.length);
			//System.out.println("checking");
			//System.out.println(RandSeqGen.boolArrToString(l3.init));
			if(checkIfInitIsOk(l1seq, l2seq, l3seq)) return rawIndexes[1];
		}
		System.out.println("total not found");
		return null;
	}
	
	public static boolean[][] getUndefinedIndexes(boolean[] l1i, boolean[] l2i) {
		/*for(int i=0; i<l1i.length; i++) System.out.print(l1i[i] + " ");
		System.out.println();
		for(int i=0; i<l2i.length; i++) System.out.print(l2i[i] + " ");
		System.out.println();*/
		boolean definitelyFalse = false;
		//ArrayList<Integer> undef = new ArrayList<>();
		boolean[][] rawInit = new boolean[2][llen]; //первый - индексы, которые однозначно определимы, отмечены true. второй - значени€ l3.init с неоднозначными точками
		// выставим всЄ нул€ми. идем по l1 и l2. если значение на шаге совпадают, проверим, чтобы они совпадали с z. если не совпадают, можем определить l3[i].
		for(int i=0; i<Math.min(l1i.length, l2i.length); i++) {
			if(l1i[i]==l2i[i]) { //если знчение€ равны
				if(z[i] != l1i[i]) { //но ни одно из них не совпадает с z[i]
					System.out.println("definitely false: " + i);
					System.out.println(RandSeqGen.boolArrToString(z));
					System.out.println(RandSeqGen.boolArrToString(l1i));
					System.out.println(RandSeqGen.boolArrToString(l2i));
					definitelyFalse = true; //то они €вно не подход€т
					break;
				}
				else continue;//иначе мы ничего не можем сказать, пол€ остаютс€ false, их перебираем
			}
			rawInit[0][i] = true; //значени€ разные - можем однозначно определить l3[i]
			if(l1i[i] == false) {
				if(z[i] == false) { //l1[i] и z[i] совпадают,
					rawInit[1][i] = true; //то выбран l1[i]
				}
				else {
					rawInit[1][i] = false; //иначе z[i] совпадает с l2[i] и выбран он
				}
			}
			else /*if l1[i]==true*/{
				if(z[i] == false) {
					rawInit[1][i] = false;
				}
				else {
					rawInit[1][i] = true;
				}
			}
		}
		if(definitelyFalse == true) return null; //функци€ возвращает null, если данные заполнени€ несовместимы, то есть существует i: l1[i]==l2[i]!=z[i]
		//int[] res = new int[undef.size()];
		//for(int i=0; i<res.length; i++) res[i] = undef.get(i);
		for(int i=0; i<rawInit.length; i++) {
			for(int j=0; j<rawInit[0].length; j++) {
				System.out.print(rawInit[i][j]==false?0:1);
			} System.out.println();
		}
		return rawInit;
	}
	
	public static boolean checkIfInitIsOk(boolean[] l1seq, boolean[] l2seq, boolean[] l3seq) {
		for(int k=0; k<z.length; k++) {
			if(((l3seq[k]&l1seq[k]) ^ ((!l3seq[k])&l2seq[k])) != z[k] ) {
				return false;
			}
		}
		return true;
	}
	
	public static void testCheck() {
		LR l1 = new LR(RandSeqGen.stringToBoolArr(RandSeqGen.key[0]), RandSeqGen.l1dumPoly);
		LR l2 = new LR(RandSeqGen.stringToBoolArr(RandSeqGen.key[1]), RandSeqGen.l2dumPoly);
		LR l3 = new LR(RandSeqGen.stringToBoolArr(RandSeqGen.key[2]), RandSeqGen.l3dumPoly);
		L3Breaker.z = RandSeqGen.jiffie(l1, l2, l3, 200);
		boolean[] l1seq = l1.generSeq(z.length);
		boolean[] l2seq = l2.generSeq(z.length);
		boolean[] l3seq = l3.generSeq(z.length);
		System.out.println(checkIfInitIsOk(l1seq, l2seq, l3seq));
	}
	
	public static void testGetUndefinedIndexes() {
		L3Breaker.z = RandSeqGen.sToBA(sz);
		//boolean[] zB = L3Breaker.z;
		String sl1 = "11000111";
		boolean[] l1 = RandSeqGen.sToBA(sl1);
		String sl2 = "00101000";
		boolean[] l2 = RandSeqGen.sToBA(sl2);
		for(int i=0; i<l1.length; i++) System.out.print(l1[i] + " ");
		System.out.println();
		for(int i=0; i<l2.length; i++) System.out.print(l2[i] + " ");
		System.out.println();
		boolean[][] raw = getUndefinedIndexes(l1, l2);
		if(raw == null) {
			System.out.println("cannot be");
			return;
		}
		System.out.println("z: " + sz);
		for(int i=0; i<raw.length; i++) {
			for(int j=0; j<raw[0].length; j++) {
				System.out.print(raw[i][j]==false?0:1);
			} System.out.println();
		}
		//L3Breaker.z = zB;
	}
	
	public static void testGetL3Init() {
		int seqlen = 200;
		String[] keys = RandSeqGen.key.clone();
		LR l1 = new LR(RandSeqGen.sToBA(keys[0]), RandSeqGen.l1dumPoly);
		LR l2 = new LR(RandSeqGen.sToBA(keys[1]), RandSeqGen.l2dumPoly);
		LR l3 = new LR(RandSeqGen.sToBA(keys[2]), RandSeqGen.l3dumPoly);
		L3Breaker.z = RandSeqGen.jiffie(l1, l2, l3, seqlen);
		System.out.println("z: " + RandSeqGen.boolArrToString(z));
		boolean[] res = getL3Init(l1, l2, l3);
		if(res == null) System.out.println("NotFound");
		System.out.println(keys[2]);
		System.out.println(RandSeqGen.boolArrToString(res));
	}
}
