package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import main.Criterion_1_0;

public class TestF {
	@Test
	public void test() throws IOException {
		File test1 = new File("resources\\test1.txt");
		File test2 = new File("resources\\test2.txt");
		assertFalse(Criterion_1_0.isPlainText(test1));
		assertTrue(Criterion_1_0.isPlainText(test2));
	}
}
