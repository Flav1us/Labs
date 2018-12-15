package main;


public class exec {

	public static void main(String[] args) throws IllegalArgumentException, Exception {
		long start_timer = System.currentTimeMillis();
	
		
		
		String a1 = "8B7D8F9987015258B7D8F9987015258B7D8F998726325A05037738B4551D2EB810CEF905DBEF78037738B4551D2EB810CEF9057D8F9987015258B7D8F998726325A05037738B4551D2EB810CEF905DBEF7807D8F9987015258B7D8F998726325A05037738B4551D2EB810CEF905DBEF7807D8F9987015258B7D8F998726325A05037738B4551D2EB810CEF905DBEF780";
		String b1 = "BC29E29CA40F5A4726325A05037738B4551D2EB810CEF905DBEF7850BB14069CBC29E29CA40F5A4726325A05037738B4551D2EB810CEF905DBEF7850BB14069CB810CEF905DBEF7850BB14069CBC29E29CA40F5A4726325A05037738B4551D2EB810CEF905DBEF7850BB1406B810CEF905DBEF7850BB14069CBC29E29CA40F5A4726325A05037738B4551D2EB810CEF905DBEF7850BB1406";

		Myr A = new Myr(a1);
		Myr B = new Myr(b1);

		System.out.println("evkl result: " + Myr.gcdEvkl(A, B));
		System.out.println("stein result: " + Myr.gcdStein(A, B));
		
		/*
		//System.out.println(NBGal.one().toString());
		Gal.testMul();
		//Gal.testPow();
		Gal.testInverse();
		 
		NBGal.testMultiplication();
		NBGal.testMultiplicativeMatrix();
		NBGal.testRiseToPow2();
		NBGal.testInverse();
		*/
		
		/*
		System.out.println("");
		String a1 = "8B7D8F9987015259895DA102AA4B01BCE3E540B265860F0763CA0AFCDF1A4B3B";
		String b1 = "BC29E29CA40F5A4726325A05037738B4551D2EB810CEF905DBEF7850BB14069C";
		String n = "F9B02C332DB40D46A531CEFBBC3E718A0AC9515C1382F1824700C6C85896E7D2";
		Myr A = new Myr(a1);
		Myr B = new Myr(b1);
		Myr N = new Myr(n);
		
		String T = Myr.LongPowBarrett(A, B, N).toString().toUpperCase();
		while (T.charAt(0) == '0') T = T.substring(1);
		System.out.println("LongPowBarrett = " + T);
		System.out.println("DE49D6C412B74A4E6A59FA7A43A8EDDD6F7F05EA9DF54C1CFC8061A29E44A9D5".equals(T.toString().toUpperCase().substring(T.toString().length()-a1.length())));
		
	*/
		/*String A = "14D80CDD914F2C9FE44BFC5917A3B3939F431DC54DEA1BEDC017F53812FF7BF97B25FFE567DCA290D50215EF29FAE694A537E6421D5976E6A7534C50A866BF50";
		String B = "BEBE7C433689A851B7A3E24294431590C25CD2F75735B33189668A07F428B798";*/
		/*System.out.println("A+B = " + Myr.LongAdd(new Myr(A), new Myr(B)).toString().toUpperCase());
		System.out.println("A-B = " + Myr.LongSub(A, B).toString().toUpperCase());*/
		//System.out.println("A/B = " + Myr.LongDiv(new Myr(A), new Myr(B)).Q.toString().toUpperCase());
		/*Myr T = Myr.LongDiv(Myr.LongMul(A, B), B).Q;
		System.out.println("(A*B)/B = " + T.toString().toUpperCase());
		System.out.println("A =       " + A.toString().toUpperCase());
		System.out.println(a.equals(T.toString().toUpperCase().substring(T.toString().length()-a.length())));	//substring - если вдруг в “ будут нули в начале.
		
		*/
		
		System.out.println("зан€ло веремени: " + (System.currentTimeMillis() - start_timer) + " ms");
	}
	
}
/*
 */
