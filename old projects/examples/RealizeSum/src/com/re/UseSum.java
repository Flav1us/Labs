package com.re;

import com.ext.ExtSumm;
import com.vh.test.Summ;

public class UseSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExtSumm es1 = new ExtSumm(); 
		
		UseSum us = new UseSum();
		us.superSum(es1);
	}
	
	int superSum( Summ sm) {
		return sm.plus(1, 2);
	}

}
