package main;

import bm.BM;
import maurer.Maurer;

public class Exec {
	private static final String bm_info_format_string = "Generating %d random numbers of %d bits each with BM random generator.\n";
	private static final String maurer_info_format_string = "Generating %d random numbers of %d bits each using Maurer method.\n";
	private static final String expected = "expected three args in form {bm|maurer} {<num of bits>}, {<quantity}, like \"java -jar mrmkzi2.jar bm 4096 10\"";
	private static final String numberformat_exc = "NumberFormatException: args should be positive integers in decimal form";
	
	public static void main(String argv[]) {
		long t0 = System.currentTimeMillis();
		
		if(argv.length < 2) {
			System.out.println(expected);
			return;
		}
		
		String command = argv[0];
		int length, quantity;
		
		try {
			length = Integer.parseInt(argv[1]);
			quantity = Integer.parseInt(argv[2]);
			if(length <= 0 || quantity <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println(numberformat_exc + "\n" + expected);
			return;
		}
		
		switch (command) {
		case "bm":
			bm(length, quantity);
			break;
		case "maurer":
			maurer(length, quantity);
			break;
		}
		
		System.out.println("Time taken: " + (double)(System.currentTimeMillis() - t0)/1000 + " sec.");
	}

	private static void maurer(int l, int q) {
		System.out.format(maurer_info_format_string, q, l);
		Maurer mau = new Maurer();
		Maurer.debug_mode = false;
		for(int i = 0; i < q; i++) {
			System.out.println(mau.recursiveMauer(l).toString(16));
		}
	}

	private static void bm(int l, int q) {
		System.out.format(bm_info_format_string, q, l);
		BM bm = new BM();
		for(int i = 0; i < q; i++) {
			System.out.println(bm.genRandInt(l).toString(16));
		}
	}
}
