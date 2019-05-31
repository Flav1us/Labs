package main;


public class exec {
	
	static String[] args;
	static Myr module;

	public static void main(String[] args) throws IllegalArgumentException, Exception {
		/*args = new String[4];
		args[0] = "barr";
		args[1] = "pow";
		args[2] = "a0252888e94231225577727b04fad4f6c995";
		args[3] = "c11c85323bf8c8590b49bd5bc96a9812f0d5**e83952abb3be28b2766f96ec9cab9dc72df";
		*/

		exec.args = args;
		long start_timer = System.currentTimeMillis();
		
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
					case "reduce":
						reduceMont();
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



	
	private static void reduceTable() {
		System.out.println("table reduction");
		LookupReducer blr = new LookupReducer(module);
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
			String[] multipliers = args[i].split("\\*\\*");
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
	
	private static void reduceMont() {
		MontReducer mr = new MontReducer(module);
		System.out.println("mont reduce (actually mont pow: arg^1 mod m)");
		Myr a;
		for(int i = 3; i < args.length; i++) {
			try {
				a = new Myr(args[i]);
			} catch(NumberFormatException e) {
				System.out.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(mr.convertOut(mr.pow(mr.convertIn(a), Myr.ONE)).toString());
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
			if((reducable).compareTo(module.multiply(module)) > 0) System.out.println("achtung! arg > mod^2, algorithm might be ineffective");
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
			System.out.println(a.toString()+" ^ "+b.toString() + " mod " + module.toString());
			System.out.println(Myr.longPowBarrett_new(a, b, module));			
		}
	}
	
}
/*
 */
