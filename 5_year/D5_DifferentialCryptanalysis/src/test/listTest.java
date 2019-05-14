package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class listTest {
	@Test
	public void test() {
		
		Integer[] args2 = { 0b1001100000000001, 0b1001100000000011, 0b1001100000100001, 0b1001100000100011,
				0b1001101000000001, 0b1001101000000011, 0b1001101000100001, 0b1001101000100011, 0b1011100000000001,
				0b1011100000000011, 0b1011100000100001, 0b1011100000100011, 0b1011101000000001, 0b1011101000000011,
				0b1011101000100001, 0b1011101000100011 };
		
		Integer[] args1 = {
				0xba03,
				0xba07,
				0xba43,
				0xba47,
				0xbe03,
				0xbe07,
				0xbe43,
				0xbe47,
				0xfa03,
				0xfa07,
				0xfa43,
				0xfa47,
				0xfe03,
				0xfe07,
				0xfe43,
				0xfe47 };

		
		//List<Integer> list1 = Arrays.asList(args1);
		List<Integer> list2 = Arrays.asList(args2);
		
		for(Integer i : args1) {//list1) {
			if(list2.contains(i)) System.out.println(Integer.toHexString(i));
		}
		
		
	}
}
