package main;

import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;

public class Xlsx 
{
	//static int N = 5;
	//"src\\main\\java\\main\\graphic.xlsx"
	public static void newFileWithArray(double[][] values, String filename) {
		int N = values.length, M = values[0].length;
		Workbook wb = new XSSFWorkbook();
    	Sheet sh = wb.createSheet("graphic");
        Row[] rows = new Row[N];
        Cell[][] cells = new Cell[N][M];
        for(int i=0; i<N; i++) {
        	rows[i] = sh.createRow(i);
        	for(int j=0; j<M; j++) {
        		cells[i][j] = rows[i].createCell(j);
        		cells[i][j].setCellValue(values[i][j]);
        	}
        }
        FileOutputStream fo = null;
        //File graphic = new File("graphic.xlsx");
        try {
        	fo = new FileOutputStream(filename);
        } catch(FileNotFoundException e) {
        	System.out.println("File not found. Creating new one.");
        	try {
        		fo = new FileOutputStream(new File(filename));
        	}
        	catch(FileNotFoundException ex) {System.out.println("SOMETHING FUCKING WRONG!! " + ex.getMessage());}
        }
        
        try {
			wb.write(fo);
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*public static void updateArray(double[][] values, String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		int N = values.length, M = values[0].length;
		Workbook wb = new XSSFWorkbook(fis);
    	Sheet sh = wb.getSheet("graphic");
        Row[] rows = new Row[N];
        Cell[][] cells = new Cell[N][M];
        for(int i=0; i<N; i++) {
        	rows[i] = sh.createRow(i);
        	for(int j=0; j<M; j++) {
        		cells[i][j] = rows[i].createCell(j);
        		cells[i][j].setCellValue(values[i][j]);
        	}
        }
        FileOutputStream fo = null;
        //File graphic = new File("graphic.xlsx");
        try {
        	fo = new FileOutputStream(filename);
        } catch(FileNotFoundException e) {
        	System.out.println("File not found. Creating new one.");
        	try {
        		fo = new FileOutputStream(new File(filename));
        	}
        	catch(FileNotFoundException ex) {System.out.println("SOMETHING FUCKING WRONG!! " + ex.getMessage());}
        }
        
        try {
			wb.write(fo);
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
}
