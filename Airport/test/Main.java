package test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import airport.Aircraft;
import airport.CrewMember;
import airport.Flight;

import jxl.*;
import jxl.read.biff.BiffException;

public class Main {
	
	private List<Flight> listFlight;
	private HashMap<String, Aircraft> mapAircraft;
	private List<CrewMember> listCrewMember;

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//O Ficheiro XML vai ter 3 sheets
		//Sheet 0 - Lista de Voos
		//Sheet 1 - Aviões
		//Sheet 2 - Crew Members
		
		
		mapAircraft  = new HashMap<String, Aircraft>();
		Sheet sheet = flightsFile.getSheet(1);
		mapAircraft = getAircrafts(sheet);
		/*
		listCrewMember = new ArrayList<CrewMember>();
		sheet = flightsFile.getSheet(2);
		listCrewMember = getCrewMembers(sheet);
		*/
		listFlight = new ArrayList<Flight>();
		sheet = flightsFile.getSheet(0);
		listFlight = getFlights(sheet);

		flightsFile.close();
	}

	private List<CrewMember> getCrewMembers(Sheet sheet) {
		// TODO Auto-generated method stub
		return null;
	}

	private HashMap<String, Aircraft> getAircrafts(Sheet sheet) {
		HashMap<String, Aircraft> map = new HashMap<String, Aircraft>();
		System.out.println(sheet.getRows());
		for (int i = 1; i != sheet.getRows(); i++) {
			
			String licensePlate = sheet.getCell(0, i).getContents();
			String model = sheet.getCell(1, i).getContents();
			Float costHour = Float.parseFloat(sheet.getCell(2, i).getContents());
			
			Aircraft aircraft = new Aircraft(licensePlate, model, costHour);
			//aircraft.list();
			
			map.put(licensePlate, aircraft);
		}
		return map;
	}

	private List<Flight> getFlights(Sheet sheet) {
		List<Flight> list = new ArrayList<Flight>();
		System.out.println(sheet.getRows());
		for (int i = 1; i != sheet.getRows(); i++) {
			int flightNumber = Integer.parseInt(sheet.getCell(1, i).getContents());

			String departureAirport = sheet.getCell(2, i).getContents();
			String arrivalAirport = sheet.getCell(3, i).getContents();

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

			//find object Aircraft by license plate
			Aircraft aircraft = mapAircraft.get(sheet.getCell(5, i).getContents());
			
			//find object CrewMember by member number
			
			
			Flight f = new Flight(flightNumber, departureAirport,
					arrivalAirport, departureTime, arrivalTime, busSaleSeats,
					econSaleSeats, busActlPax, econActlPax, estOffblkDate,
					estOnblkDate, actlOffblkDate, actlOnblkDate, aircraft, null);
			f.list();
			
			list.add(f);
		}
		
		return list;
	}

}
