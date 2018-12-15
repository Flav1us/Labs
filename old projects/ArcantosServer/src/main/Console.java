package main;

import java.io.IOException;

public class Console {
	public static String RESULT = "/tmp/scan-result.txt";
	
	private static void Execute(String command) {
		try {
			Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
			Log.log("successfully executed '" + command + "' from src/main/Console.Execute");
		}
		catch(IOException e) {
			Log.log("unable to execute \"" + command + "\" from src/main/Console.Execute");
		}
	}
	
	public static void scan(String target_webpage) {
		Execute("perl ~/Ark/nikto/program/nikto.pl -host " + target_webpage + " > " + RESULT);
	}
	
	public static void testExecuteShellCommand() {
		String default_command = "echo \"hello\" >> /tmp/temp.txt";
		try {
			Runtime.getRuntime().exec(new String[]{"bash", "-c", default_command});
			Log.log("test execution complete successfully");
		} catch (IOException e) {
			Log.log("unable to execute test shell command");
		}
	}
}
