package test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public Timestamp stringToTimestamp (String value)
	{
		if (value.equals(""))
			return null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yy hh:mm:ss");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(parsedDate.getTime());
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
			Timestamp departureTime = stringToTimestamp(sheet.getCell(6, i).getContents());
			Timestamp arrivalTime = stringToTimestamp(sheet.getCell(7, i).getContents());

			int busSaleSeats = Integer.parseInt(sheet.getCell(8, i).getContents());
			int econSaleSeats = Integer
					.parseInt(sheet.getCell(9, i).getContents());

			int busActlPax = Integer.parseInt(sheet.getCell(10, i).getContents());
			int econActlPax = Integer.parseInt(sheet.getCell(11, i).getContents());

			Timestamp estOffblkDate = stringToTimestamp(sheet.getCell(12, i).getContents());
			Timestamp estOnblkDate = stringToTimestamp(sheet.getCell(13, i).getContents());

			Timestamp actlOffblkDate = stringToTimestamp(sheet.getCell(14, i).getContents());
			Timestamp actlOnblkDate = stringToTimestamp(sheet.getCell(15, i).getContents());

			Flight f = new Flight(flightNumber, departureAirport,
					arrivalAirport, departureTime, arrivalTime, busSaleSeats,
					econSaleSeats, busActlPax, econActlPax, estOffblkDate,
					estOnblkDate, actlOffblkDate, actlOnblkDate, null, null);
			f.list();
			
			listFlight.add(f);
		}
		

		flightsFile.close();
	}

}
