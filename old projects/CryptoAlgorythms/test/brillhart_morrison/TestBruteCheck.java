package brillhart_morrison;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brilhardt_morrison.BruteCheck;
import brilhardt_morrison.BMController;

public class TestBruteCheck {
	@Test
	public void testIsSumCoefsEven() {
		BMController c = new BMController(25511);
		c.process();
		for(int i=1; i < c.pt.cStep; i++) {
			try {
				assertTrue(BruteCheck.isSumCoefsEven(c.pt, i, i));
			}
			catch (IllegalArgumentException e) {}
		}
		assertTrue(BruteCheck.isSumCoefsEven(c.pt, 4, 10));
		assertFalse(BruteCheck.isSumCoefsEven(c.pt, 5, 10));
	}
	
	@Test
	public void testEukl() {
		assertTrue(BruteCheck.eukl(2, 4) == 2);
		assertTrue(BruteCheck.eukl(10, 4) == 2);
		assertTrue(BruteCheck.eukl(12, 6) == 6);
		assertTrue(BruteCheck.eukl(20, 5) == 5);
		assertTrue(BruteCheck.eukl(128, 2) == 2);
		assertTrue(BruteCheck.eukl(128, 64) == 64);
		//assertTrue(BruteCheck.eukl(545, 0) == 545);
		assertTrue(BruteCheck.eukl(132, 1) == 1);
	}
}
