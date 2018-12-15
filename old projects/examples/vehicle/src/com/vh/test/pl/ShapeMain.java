package com.vh.test.pl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShapeMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Square sq = new Square();
		Circle ci = new Circle();
		
		List list = new ArrayList();
		list.add(sq);
		list.add(ci);

		List lList = new LinkedList();

		for(int i = 0; i < list.size(); i++) {
			ShapeMain.print((Shape)list.get(i) );
		}
		
		ShapeMain.print((Object)sq);
		
	}	

	static void print(Shape sh) {
		sh.draw();
	}
	
	static void print(int i, Shape sh) {
		sh.draw();
	}

	static void print(Object o) {
		
		System.out.println(((Circle)o).hashCode());
		
		((Circle)o).draw();
	}

}
