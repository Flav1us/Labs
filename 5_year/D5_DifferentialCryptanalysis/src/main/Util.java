package main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Util {

	public static void fileGen(byte[] arr, String filename) {
		try {
			FileUtils.writeByteArrayToFile(new File("C:\\Users\\ASUS\\Desktop\\D5_CP1\\"+filename+".bin"), arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
