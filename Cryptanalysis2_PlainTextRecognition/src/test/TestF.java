package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import main.Criterion_1_0;
import main.Criterion_1_1;
import main.Criterion_1_2;
import main.Criterion_1_3;
import main.Format;
import main.Frequencies;
import main.Main;

public class TestF {
	Map<String, Double> cr_frq = new HashMap<String, Double>() {{
		put("ьь", 0.0);
		put("ъъ", 0.0);
		put("ьъ", 0.0);
		put("ъь", 0.0);
	}};
	int k_p = 5;
	File test_formated = new File("resources\\test_formated.txt");
	File basic = new File("resources\\hpmor_formated.txt");
	File test_false = new File("resources\\test1.txt");
	File test_true = new File("resources\\test2.txt");

	@Test
	public void test() throws IOException {

		assertFalse(Criterion_1_0.isPlainText(test_false));
		assertFalse(Criterion_1_1.isPlainText(test_false, k_p));
		assertFalse(Criterion_1_2.isPlainText(test_false, cr_frq));
		assertFalse(Criterion_1_3.isPlainText(test_false, Frequencies.A_prh_frq(Frequencies.getBigramQuantities(basic, Main.alphabet, true))));
		
		assertTrue(Criterion_1_0.isPlainText(test_true));
		assertTrue(Criterion_1_1.isPlainText(test_true, k_p));
		assertTrue(Criterion_1_2.isPlainText(test_true, cr_frq));
		assertTrue(Criterion_1_3.isPlainText(test_true, Frequencies.A_prh_frq(Frequencies.getBigramQuantities(basic, Main.alphabet, true))));
		
	}
	

}
