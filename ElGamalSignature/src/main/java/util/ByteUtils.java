package util;

import java.nio.ByteBuffer;

public class ByteUtils {
    //private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    

   /* public static byte[] longToBytes(long x) {
    	buffer.clear();
    	buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
    	buffer.clear();
        buffer.put(bytes, 0, bytes.length);
       // System.out.println("bif: " + buffer.getLong());
       // buffer.position(0);
        buffer.flip();//need flip
        //System.out.println(buffer.getLong());
        return buffer.getLong();
    }*/
    
    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }
    
	public static byte[] intToBytes(int value) {
		//System.out.println("hey + " + value);
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}
	
	public static int bytesToInt(byte[] ba) {
		assert ba.length == 4;
		//System.out.println("arr: " + ba[0] + "\t" + ba[1] + "\t" + ba[2] + "\t" + ba[3] + "\t");
		//System.out.println(((Byte.toUnsignedInt(ba[0]))<<24) + " " +  ((Byte.toUnsignedInt(ba[1]))<<16)  + " " +  Integer.toHexString(((Byte.toUnsignedInt(ba[2]))<<8))  + " " +  ((Byte.toUnsignedInt(ba[3]))));
		return ((Byte.toUnsignedInt(ba[0])<<24) + (Byte.toUnsignedInt(ba[1])<<16) + (Byte.toUnsignedInt(ba[2])<<8) + (Byte.toUnsignedInt(ba[3])));
		 
	}
    
    public static byte[] invert(byte[] arg) {
    	byte[] res = new byte[arg.length];
    	for(int i = 0; i < res.length; i++) res[i] = arg[arg.length - 1 - i];
    	return res;
    }
    

}
