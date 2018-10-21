package util;

public class Util {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] invert(byte[] arg) {
		byte[] res = new byte[arg.length];;
		for(int i = 0; i<arg.length; i++) {
			res[i] = arg[arg.length - 1 - i];
		}
		return res;
	}
	
	public static int shift13BitsLeft(int arg) {
		return (arg<<13) ^ (arg>>>(32-13));
	}
}
