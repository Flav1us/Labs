package brillhart_morrison;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brilhardt_morrison.BMController;

public class TestPTable {
	@Test
	public void testPTable() {
		int n = 25511;
		BMController c = new BMController(n);
		c.process();
		assertTrue(c.pt.table[0][1] == 159);
		assertTrue(c.pt.table[1][1] == 159);
		assertTrue(c.pt.table[2][1] == -230);
		
		assertTrue(c.pt.table[0][2] == 1);
		assertTrue(c.pt.table[1][2] == 160);
		assertTrue(c.pt.table[2][2] == 89);
		
		assertTrue(c.pt.table[0][3] == 2);
		assertTrue(c.pt.table[1][3] == 479);
		assertTrue(c.pt.table[2][3] == -158);
	}
}
