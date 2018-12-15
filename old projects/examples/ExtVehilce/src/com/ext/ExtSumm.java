package com.ext;

import com.vh.test.Summ;

public class ExtSumm implements Summ {

	public int plus(int arg0, int arg1) {
		System.out.println("Its sum for ExtVehicle");
		return arg0 + arg1;
	}

}
