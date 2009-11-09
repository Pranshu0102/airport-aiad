package test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import jxl.*;
import jxl.read.biff.BiffException;

public class Main {

	public static void main(String args[]) throws BiffException, IOException {
		Workbook flightsFile = Workbook
				.getWorkbook(new File(
						"/home/canastro/Documentos/FEUP/AIAD/FLIGHTS_2009_09_Planeado_Real.xls"));
		
		Sheet sheet = flightsFile.getSheet(0); 
		
		Cell a1 = sheet.getCell(0,0);
		Cell b2 = sheet.getCell(1,1);
		Cell c2 = sheet.getCell(2,1);

		String stringa1 = a1.getContents();
		String stringb2 = b2.getContents();
		String stringc2 = c2.getContents(); 
		
		System.out.println(stringa1+" "+stringb2+" "+stringc2);
		
		flightsFile.close();
	}

}
