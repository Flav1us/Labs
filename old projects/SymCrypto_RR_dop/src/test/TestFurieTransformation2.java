package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.CoordinateFunction;

public class TestFurieTransformation2 {

	@Test
	public void test() {
		//fail("Not yet implemented");
		System.out.println("HELLO1");
	}
	
	@Test
	public void testFFT() {
		int[] t = {-1,-1,-1,-1,1,1,1,1};
		int[] res = CoordinateFunction.fastFurieTransform(t, 3);
		int[]rest = {0,-2,2,4,0,-2,-2,-2};
		for(int i=0; i<rest.length; i++) {
			assertTrue("invalid logic in FFT",res[i] == rest[i]);
		}
		
		
	}

}
