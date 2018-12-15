
public class exe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] values = {{7.41,1.13,0.93,1.22},{1.13,8.31,1.30,0.16},{0.93,1.30,5.42,2.10},{1.22,0.16,2.10,11.10}};
		 Matrix input = new Matrix(values);
		 System.out.println("Basic matrix:");
		 input.print();
		 System.out.println("Snd = " + input.Snd());
		 System.out.println();
		 Iter.iterate(input);
		 
		 
		 /*Matrix T = Iter.compT(input, 3,0);
		 System.out.println(input.Snd()+ input.Sd());
		 System.out.println("AS = " + input.S());
		 System.out.println("ASnd = " + input.Snd());
		 input.print();
		 System.out.println();
		 Matrix nA = Matrix.mul(Matrix.mul(T.T(),input), T);
		 System.out.println("nAS = " + nA.S());
		 System.out.println("nASnd = " + nA.Snd());
		 nA.print();*/
		 //Matrix.testMatrix();
	}

}
