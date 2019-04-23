package main;


public class exec {
	
	static String[] args;
	static Myr module;

	public static void main(String[] args) throws IllegalArgumentException, Exception {
		long start_timer = System.currentTimeMillis();
		exec.args = args;
		
		if (args.length < 4) {
			System.out.println("At least 4 arguments expected, found " + args.length + ".");
		}
		else {
			try {
				module = new Myr(args[2]);
			} catch (NumberFormatException e) {
				System.err.println("Exception during parsing " + args[2] + "\nExpected hexademical numbers.");
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
			String[] multipliers = args[i].split("^");
			if(multipliers.length != 2) {
				System.err.println("Error during parsing multipliers: split character is ^, expected two arguments in form ab345^CD678");
				return;
			}
			
			try {
				a = new Myr(multipliers[0]);
				b = new Myr(multipliers[1]);
			} catch(NumberFormatException e) {
				System.err.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
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
				System.err.println("Exception during parsing " + args[i] + "\nExpected hexademical numbers.");
				e.printStackTrace();
				continue;
			}
			System.out.println(brr.reduce(reducable).toString());			
		}
	}

	private static void powBarrett() {
		System.out.println("pow barrett");
		// TODO Auto-generated method stub
		
	}
	
}
/*
 */
