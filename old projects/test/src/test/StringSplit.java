package test;

public class StringSplit {
	public static void main(String[] args) {
		String s = "1,0,3,\"Braund, Mr. Owen Harris\",male,22,1,0,A/5 21171,7.25,,S";
		String[] ss = s.split(",");
		for(String st : ss) System.out.print(st + "_\t_");
	}
}
