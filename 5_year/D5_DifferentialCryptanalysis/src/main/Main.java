package main;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		// TODO Auto-generated method stub
		//char[] keys = {'Q','Q','Q','Q','Q','Q','Q'};
		//char e = Heys.encrypt('Q', keys);
		//System.out.println(e);
		
		//byte[] arr = {-0b1111111, -0b1111111, -0b1111111, -0b1111111, -0b1111111, -0b1111111, -0b1111111};
		
		//Util.fileGen("Q".getBytes(StandardCharsets.UTF_16LE), "m");
		//BranchAndBound.precalculation(0.001);
		//System.out.println(BranchAndBound.roundDiffProb('a', 'b'));
		
		//BranchAndBound.singleLine();
		
		char[] key = {0x3864, 0xffff, 0x1234, 0x11ff, 0xaabb, 0x4321, 0x2345};
		//System.out.println(Integer.toHexString((int)Heys.encrypt((char)0b0001, key)));

		System.out.println(Integer.toHexString((int) Heys.encrypt((char)0xeeff, key)));
		
		//System.out.println(BranchAndBound.roundDiffProb((char)0b0000_0000_0000_0001, (char)10001) > 0.000001);
		System.out.println("time taken: " + (System.currentTimeMillis() - t0) + " ms");
	}

}
