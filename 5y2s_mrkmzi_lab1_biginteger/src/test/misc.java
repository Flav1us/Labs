package test;

import java.util.Arrays;

import org.junit.Test;

public class misc {
	@Test
	public void test() {
		int[] arr = {1, 2, 3, 4 ,5};
		int[] cpy = Arrays.copyOfRange(arr, 4, 5);
		for(int t : cpy) System.out.println(t);
	}
}
