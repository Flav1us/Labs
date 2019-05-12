package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Ignore;
import org.junit.Test;

import main.Attack;

public class TestFileManagement {
	File dir = new File("C:\\Users\\ASUS\\Desktop\\D5_CP1\\test");
	
	@Ignore
	@Test
	public void testExecute() {
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "test.bat");
		pb.directory(dir);
		//TODO https://stackoverflow.com/questions/19103570/run-batch-file-from-java-code/19103715
		// бат-файл создает файл с !парами пар! (вау) ОТ-ШТ с заданой входной разницей. в программе - расшифровать на раунд-наоборот (не xor - Sblock, а sblock-xor) и посчитать совпадения с дифференциалом высокой вероятности.
	}
	
	@Ignore
	@Test
	public void testChar() {
		char c = (char)0xd801;
		System.out.println(c);
	}
	
	@Ignore
	@Test
	public void testWriteReadBytes() throws IOException {
		String fname ="testFileWriter.bin"; 
		OutputStream fw = new FileOutputStream(dir + "\\" +fname);
		byte[] b = new byte[Character.MAX_VALUE * 2];
		for(int i = 0; i < Character.MAX_VALUE; i++) {
			b[2*i+1] = (byte)i;
			b[2*i] = (byte) (i/(Byte.MAX_VALUE - Byte.MIN_VALUE + 1));
		}
		fw.write(b);
		fw.close();
		
		BufferedReader fr = new BufferedReader(new FileReader(dir + "\\" + fname));
		char[] cbuf = new char[Character.MAX_VALUE];
		fr.read(cbuf);
		for(char c = 0; c < Character.MAX_VALUE; c++ ) {
			System.out.println(Integer.toHexString(c) + "\t" + Integer.toHexString(cbuf[c]));

		}
		fr.close();
	}
	
	@Ignore
	@Test
	public void testReadChars() throws IOException {
		InputStream fr = new FileInputStream(dir + "\\" + "testBytes.bin");
		byte[] byte_buff = new byte[16*2];
		
		int b_int;
		
		for(int i = 0; i < 32; i++) {
			b_int = fr.read();
			byte_buff[i] = (byte)b_int;
		}
		
		for(int i = 0; i < byte_buff.length; i++) {
			System.out.println(Integer.toHexString((int)byte_buff[i]));
		}
	}
	
	//@Ignore
	@Test
	public void generateBasicFile() throws IOException {
		String filepath_basic = dir + "\\" + "m_test.bin";
		
		String filepath_different = dir + "\\" + "m_test_different.bin";
		Attack.generateBasicFiles(filepath_basic, filepath_different, (char)0xFFFF);
	}
}

