package lab2;

public class L2Task2 { //критерий пустых ящиков
	public int n;
	public int r;
	double[] sample;
	double[] edges;
	int[] freq;
	double zg = 1.645; // https://math.semestr.ru/corel/table-laplas.php
	
	public boolean regenerate(int n) {
		this.n = n;
		r = n/2;
		sample = util.SampleGenerator.uniform(0, 1, n);
		frequencies();
		boolean res = criteria();
		//System.out.println(res);
		return res;
	}
	
	public void frequencies() {
		edges = new double[r+1];
		edges[0] = 0;
		for(int i = 1; i < edges.length; i++) {
			edges[i] = edges[0] + i*1.0/r;
		}

		//for(double i : edges) System.out.println(i);
		freq = new int[r];
		for(double i : sample) checkIn(i);
	}
	
	private void checkIn(double d) {
		for(int i=1; i<edges.length; i++) {
			if(d <= edges[i]) {
				freq[i-1]++;
				break;
			}
		}
	}
	
	private int Mu() {
		int ctr = 0;
		for(int i = 0; i<freq.length; i++) {
			if(freq[i] == 0) ctr++;
		}
		return ctr;
	}
	

	private boolean criteria() {
		double ro = n/r;
		double ero = Math.pow(Math.E, -ro);
		double k = r * ero + zg*Math.sqrt(r*ero*(1-(1+ro)*ero));
		System.out.println(Mu() + " " + k);
		return Mu() < k;
	}
}
