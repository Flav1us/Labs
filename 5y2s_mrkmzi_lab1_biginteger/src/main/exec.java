package main;


public class exec {
	
	static String[] args;
	static Myr module;

	public static void main(String[] args) throws IllegalArgumentException, Exception {
		args = new String[5];
		args[0] = "barr";
		args[1] = "pow";
		args[2] = "5";
		args[3] = "3A**0";
		args[4] = "0**0";
		
		//System.out.println(Integer.parseInt("78", 16));
		
		long start_timer = System.currentTimeMillis();
		exec.args = args;
		
		if (args.length < 4) { 
			System.out.println("At least 4 arguments expected, found " + args.length + ".");
		}
		else {
			try {
				module = new Myr(args[2]);
			} catch (NumberFormatException e) {
				System.out.println("Exception during parsing " + args[2] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				return;
			}
			
			switch(args[0]) { 
				case "barr":
					switch(args[1]) {
					case "pow":
						powBarrett();
						break;
					case "reduce":
						reduceBarrett();
						break;
					default:
						System.out.println("Unable to parse argument " + args[1] + ". Expected reduce or pow.");
						break;
					}
					break;
				case "mont":
					switch(args[1]) {
					case "pow":
						powMont();
						break;
					default:
						System.out.println("Unable to parse argument " + args[1] + ". Only pow accepted.");
						break;
					}
					break;
				case "table":
					switch(args[1]) {
					case "reduce":
						reduceTable();
						break;
					default:
						System.out.println("Unable to parse argument " + args[1] + ". Only reduce accepted.");
						break;
					}
					break;
				default:
					System.out.println("Unable to parse argument " + args[0] + ". Expected barr, mont or table.");
					break;
			}
		}
		
		System.out.println("run time: " + (System.currentTimeMillis() - start_timer) + " ms");
	}

	//private static void validate() {}
	
	private static void reduceTable() {
		//validate();
		System.out.println("table reduction");

		BasicLookupReducer blr = new BasicLookupReducer(module);
		
		Myr reducable;
		for(int i = 3; i < args.length; i++) {
			try {
				reducable = new Myr(args[i]);
			} catch(NumberFormatException e) {
				System.err.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(blr.reduce(reducable).toString());			
		}
	}


	private static void powMont() {
		System.out.println("mont pow");
		MontReducer mr = new MontReducer(module);
		
		Myr a, b;
		for(int i = 3; i < args.length; i++) {
			//System.out.println("arg: " + args[i]);
			String[] multipliers = args[i].split("\\*\\*");
			//for(String str : multipliers) System.out.println(str);
			if(multipliers.length != 2) {
				System.out.println("Error during parsing multipliers: split character is **, expected two arguments in form ab345**CD678");
				return;
			}
			
			try {
				a = new Myr(multipliers[0]);
				b = new Myr(multipliers[1]);
			} catch(NumberFormatException e) {
				System.out.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(mr.convertOut(mr.pow(mr.convertIn(a), b)).toString());			
		}
	}

	private static void reduceBarrett() {
		System.out.println("barrett reduction");
		BarrettReducer brr = new BarrettReducer(module);
		
		Myr reducable;
		for(int i = 3; i < args.length; i++) {
			try {
				reducable = new Myr(args[i]);
			} catch(NumberFormatException e) {
				System.out.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(brr.reduce(reducable).toString());			
		}
	}

	private static void powBarrett() {
		System.out.println("pow barrett");
		Myr a, b;
		for(int i = 3; i < args.length; i++) {
			//System.out.println("arg: " + args[i]);
			String[] multipliers = args[i].split("\\*\\*");
			//for(String str : multipliers) System.out.println(str);
			if(multipliers.length != 2) {
				System.out.println("Error during parsing multipliers: split character is **, expected two arguments in form ab345**CD678");
				return;
			}
			
			try {
				a = new Myr(multipliers[0]);
				b = new Myr(multipliers[1]);
			} catch(NumberFormatException e) {
				System.out.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(Myr.LongPowBarrett(a, b, module));			
		}
	}
	
}
/*
 */
