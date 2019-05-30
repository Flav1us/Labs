package main;


public class exec {
	
	static String[] args;
	static Myr module;

	public static void main(String[] args) throws IllegalArgumentException, Exception {
		/*args = new String[4];
		args[0] = "barr";
		args[1] = "reduce";
		args[2] = "eefefe1124142";
		args[3] = "fffffdffddfffaaaaaaa1111111111113231";*/
		

		//args[3] = "222222ad";//"2**10000";
		//args[4] = "123df";
	
		//System.out.println(Integer.parseInt("78", 16));
		
		exec.args = args;
		long start_timer = System.currentTimeMillis();
		/*System.out.println("args:");
		for(int i = 0; i < args.length; i++) {
			System.out.println((i+1) + ":\t" + args[i]);
		}*/
		
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
