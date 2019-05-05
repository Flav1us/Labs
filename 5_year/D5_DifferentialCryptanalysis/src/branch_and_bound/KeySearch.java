package branch_and_bound;

import java.util.List;
import java.util.concurrent.Callable;

import main.Heys;

public class KeySearch implements Callable<Integer> {
	private char range_start, range_end;
	private char input_x;
	private char inp_diff, out_diff;

	public KeySearch(char range_start, char range_end, char input_x, char inp_diff, char out_diff) {
		/**
		 * including start, excluding end
		 */
		if(range_start > range_end) throw new IllegalArgumentException("bad range: start > end: " + range_start + " > " + range_end + ".");
		this.range_end = range_end;
		this.range_start = range_start;
		this.input_x = input_x;
		this.inp_diff = inp_diff;
		this.out_diff = out_diff;
	}

	@Override
	public Integer call() throws Exception {
		char x = input_x;
		int diff_count = 0;
		for (char key = range_start; key < range_end; key ++) {
			// System.out.println((int)key);
			if (Heys.round((char) (x ^ inp_diff), key) == (out_diff ^ (Heys.round(x, key)))) {
				diff_count++;
				// System.out.println("found");
			}
		}
		System.out.println("thread finished task. when see 4 of these input anything to console to proceed");
		return diff_count;
	}

}
