package main;

import com.sun.jna.Library;
import com.sun.jna.Native;


public class Creator {
	 public interface Kernel32 extends Library {
	        boolean B
	        
	        eep(int frequency, int duration);
	    }
	    private static Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
}
