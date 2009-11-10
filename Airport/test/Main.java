package test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import airport.Aircraft;
import airport.CrewMember;
import airport.Flight;

import jxl.*;
import jxl.read.biff.BiffException;

public class Main {
	
	private List<Flight> listFlight;

	public static void main(String args[]) {
		Main main = new Main();
		main.parse();
	}
	
	public void parse() {
		Workbook flightsFile = null;
		
		
		try {
			flightsFile = Workbook
					.getWorkbook(new File(
							"/home/canastro/Documentos/FEUP/AIAD/FLIGHTS_2009_09_Planeado_Real.xls"));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = flightsFile.getSheet(0);
		listFlight = new ArrayList<Flight>();

		for (int i = 1; i != sheet.getRows(); i++) {
			//System.out.println(sheet.getCell(1, i).getContents());
			int flightNumber = Integer.parseInt(sheet.getCell(1, i).getContents());

			String departureAirport = sheet.getCell(2, i).getContents();
			String arrivalAirport = sheet.getCell(3, i).getContents();

			// Criar função para converter a string em timestamp
			Timestamp departureTime = null;
			Timestamp arrivalTime = null;

			int busSaleSeats = Integer.parseInt(sheet.getCell(8, i).getContents());
			int econSaleSeats = Integer
					.parseInt(sheet.getCell(9, i).getContents());

			int busActlPax = Integer.parseInt(sheet.getCell(10, i).getContents());
			int econActlPax = Integer.parseInt(sheet.getCell(11, i).getContents());

			Timestamp estOffblkDate = null;
			Timestamp estOnblkDate = null;

			Timestamp actlOffblkDate = null;
			Timestamp actlOnblkDate = null;

			Flight f = new Flight(flightNumber, departureAirport,
					arrivalAirport, departureTime, arrivalTime, busSaleSeats,
					econSaleSeats, busActlPax, econActlPax, estOffblkDate,
					estOnblkDate, actlOffblkDate, actlOnblkDate, null, null);
			f.list();
			
			listFlight.add(f);
		}
		// Cell a1 = sheet.getCell(0,0);
		// Cell b2 = sheet.getCell(1,1);
		// Cell c2 = sheet.getCell(2,1);
		//		
		// String stringa1 = a1.getContents();
		// String stringb2 = b2.getContents();
		// String stringc2 = c2.getContents();
		//		
		// System.out.println(stringa1+" "+stringb2+" "+stringc2);

		flightsFile.close();
	}

}
