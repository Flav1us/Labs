
public class Matrix {
	int m,n;
	double[][] values;
	 //размерности матриц (n - количество строк матрицы А;
	  //m - количество столбцов/строк матрицы А/B; l - количество столбцов матрицы B)
	public Matrix (double[][] values) {
		this.n = values.length;
		this.m = values[0].length;
		this.values = values;
	}
		
	public void print() {
	  for(int i=0; i < n; i++){
	      for(int j = 0; j < m; j++){
	          //System.out.print(this.values[i][j] + (j == m - 1?"\n":"\t"));
	          System.out.format("%,6f"+(j == m - 1?"\n":"\t"),this.values[i][j]);
	      }
	  }
	}

	//умножение матриц
	static Matrix mul(Matrix A, Matrix B) throws IllegalArgumentException {
		if(A.n != B.m) throw new IllegalArgumentException("#rows of A = " + A.n + " != #cols of B = " + B.m);
		int i, j, k, l=B.m;
		Matrix C = new Matrix(new double[A.n][B.m]);
		for(i = 0; i < A.n; i++){
	      for(j = 0; j < l; j++){
	          for(k = 0; k < A.m; k++){
	              C.values[i][j] += A.values[i][k] * B.values[k][j];
	          }
	          //System.out.print(C.values[i][j] + (j == l - 1?"\n":"\t"));
	      }
		}
		return C;
	}
	
	Matrix T() { //транспонирование (transpose)
		double[][] newValues = new double[this.m][this.n];
		for(int i=0; i<this.m; i++) {
			for(int j=0; j<this.n; j++) {
				newValues[i][j] = this.values[j][i];
			}
		}
		return new Matrix(newValues);
	}
	
	static void testMatrix() {
		double[][] av = {{1,2},{2,3},{3,4}}, bv = {{1,2,3},{2,3,1}};
		Matrix.mul(new Matrix(av), new Matrix(bv)).print();
		System.out.println();
		Matrix.mul(new Matrix(av), new Matrix(bv)).T().print();
		
	}
	
	double Snd() { //недиагональная сферическая норма
		double snd = 0;
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				snd+= i!=j ? Math.pow(this.values[i][j], 2) : 0;
			}
		}
	return snd;
	}
	
	double Sd() { //диагональная сферическая норма
		double sd = 0;
		for(int i=0; i<this.n; i++) {
			sd+= Math.pow(this.values[i][i], 2);
		}
	return sd;
	}
	
	double S() { //сферическая норма
		return (this.Sd() + this.Snd()); 
	}
}
