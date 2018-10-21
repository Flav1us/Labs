package main;

public class Main {

	public static void main(String[] args) {
			int n = parseArg(args[0]);
			//System.out.println("n = " + n);
			int factor;
			try {
				factor = new Shor(n).run();
				System.out.println(String.format("%s | %s", factor, n));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private static int parseArg(String arg) {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			System.err.println("Integer expected, but got " + arg);
			System.err.println(e.getMessage());
			System.exit(1);
			return 0;
		}
	}

}