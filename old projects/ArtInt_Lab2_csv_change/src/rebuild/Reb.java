package rebuild;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reb {
	public static void main(String args[]) throws IOException {
		String MALE = ",male,";
		String FEMALE = ",female,";
		String path = "C:\\main\\Programs\\ArtInt\\Lab2 fuzzy\\";
		FileReader fr = new FileReader(path+"train.csv");
		BufferedReader br = new BufferedReader(fr);
		String data_line = br.readLine();
		System.out.println(data_line);
		//System.out.println(data_line.substring(1, data_line.length()-1));
		
		FileWriter fw = new FileWriter(path+"remake.csv");
		BufferedWriter bw = new BufferedWriter(fw);
		//bw.write(data_line);
		while((data_line = br.readLine()) != null) {
			bw.write(data_line.substring(1, data_line.length()-1)+"\n");
		}
		
		bw.close();
		fw.close();
		fr.close();
		br.close();
	}
}
