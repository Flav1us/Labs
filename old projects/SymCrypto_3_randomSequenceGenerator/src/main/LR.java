package main;

/*********************************
������������ ��������� �3
Բ-43, �. �������, �. �������
*******************************/

public class LR {
//�������� ������� ������
	int reglen;
	boolean[] init;
	int[] generPoly; //�������-��������� ����������� �������� �������� ��������� ������������ ��� �������� �. ��������, x^15+x+1 == {0, 14, 15}.
	
	LR(boolean[] init, int[] generPolynomCoefs) throws IllegalArgumentException {
		this.reglen = init.length;
		boolean foundZero = false;
		for(int i : generPolynomCoefs) {
			if (i==0) foundZero = true;
			if(i>init.length) throw new IllegalArgumentException("init.length = " + init.length + " < deg of generPoly = " + i + " -> doesnt cover required length");
		}
		if(foundZero == false) throw new IllegalArgumentException("error in generPolyCoef. Example:\nx[i+25] = x[i]+x[i+3] == p(x) = x^25 + x^3 + x\nGenerPolyCoef should be like {0, 22, 25}.");
		//if(coef.length != reglen) throw new IllegalArgumentException("coef.length (="+coef.length+")  should be equal to reglen (="+reglen+").");
		//if(init.length != reglen) throw new IllegalArgumentException("init.length (="+init.length+")  should be equal to reglen (="+reglen+").");
		//this.coef = coef.clone();
		this.init = init.clone();
		
		this.generPoly = new int[generPolynomCoefs.length - 1]; //��� ����
		int ctr = 0;
		for(int i=0; i<generPolynomCoefs.length; i++) {
			if(generPolynomCoefs[i] != 0) {
				this.generPoly[ctr] = generPolynomCoefs[i];
				ctr++;
			}
		}
	}
	
	boolean[] generSeq(int seqlen)/* throws IllegalArgumentException */{
		/* ������������������ ����� ��� [|init|,x0,x1,...,x\seqlen-reglen-1/], ����� ����� (seqlen-reglen-1)+1+reglen = seqlen;
		 * ����������� ��������� ���������� �� init, ����� ������� �������������� xi �� ���� � �� ���������. 
		 * x[i+25] = x[i]+x[i+3] == p(x) = x^25 + x^3 + x
		 */
		if(seqlen < reglen) {//throw new IllegalArgumentException("seqlen cannot be < reglen");
			boolean[] seq = new boolean[seqlen];
			for(int i=0; i<seqlen; i++) seq[i] = init[i];
			return seq;
		}
		boolean[] seq = new boolean[seqlen];
		for(int i=0; i<this.init.length; i++) seq[i]=init[i]; //��������� ����������
		boolean x = false;
		for(int i=this.init.length; i<seq.length; i++) {
			for(int j : this.generPoly) {
				//System.out.println(i + " " + x + "^" + "seq["+i+"-"+j+"]");
				x = x ^ seq[i-j];
			}
			seq[i] = x;
			x = false;
		}
		return seq;
	}
	
	public static void testGenerSeq() {
		boolean[] init = {false, false, true}; //001
		int[] gpc = {0, 2, 3}; //x^3 = x + 1
		LR lr = new LR(init, gpc);
		System.out.print("gener polynom coefs:\t");
		for(int i=0; i<lr.generPoly.length; i++) System.out.print(lr.generPoly[i] + " ");
		System.out.println();
		boolean[] seq = lr.generSeq(15);
		for(int i=0; i<seq.length; i++) System.out.print(seq[i]==false?0:1);
		System.out.println(" ?= 001011100101110");
	}
	
	
	
}
