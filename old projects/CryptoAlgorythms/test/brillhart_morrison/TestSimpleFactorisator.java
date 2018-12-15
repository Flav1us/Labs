package brillhart_morrison;

import static org.junit.Assert.*;

import org.junit.Test;

import brilhardt_morrison.SimpleFactorisator;

public class TestSimpleFactorisator {
	
	@Test
	public void testPowerOfDivisor() {
		assertTrue(SimpleFactorisator.powerOfDivisor(2, 2) == 1);
		assertTrue(SimpleFactorisator.powerOfDivisor(3, 2) == 0);
		assertTrue(SimpleFactorisator.powerOfDivisor(12, 2) == 2);
		assertTrue(SimpleFactorisator.powerOfDivisor(2, 10) == 0);
		assertTrue(SimpleFactorisator.powerOfDivisor(1024, 2) == 10);
	}
	
	@Test
	public void testFactor(){
		int factorizable = -5913180;
		int[] decomposition = SimpleFactorisator.factor(factorizable);
		int compared = 1;
		for(int i = 0; i < SimpleFactorisator.base.length; i++) {
			compared *= Math.pow(SimpleFactorisator.base[i], decomposition[i]);
			//System.out.println(compared);
		}
		assertTrue(compared == factorizable);
	}
}
