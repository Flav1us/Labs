package test;

import org.junit.Test;

import BBS.BBS;
import bm.BM;

public class Gen_test {
	@Test
	public void BMtest() {
		long t0 = System.currentTimeMillis();
		System.out.println(new BM().genRandInt(10000));
		System.out.println(System.currentTimeMillis() - t0);
		t0 = System.currentTimeMillis();
		System.out.println(new BBS().genRandInt(10000));
		System.out.println(System.currentTimeMillis() - t0);
	}
}
