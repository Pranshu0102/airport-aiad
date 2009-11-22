package test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import airline.Aircraft;
import airline.AircraftModel;
import airline.Airport;
import airline.CrewMember;
import airline.Flight;
import airline.Rank;

import jxl.*;
import jxl.read.biff.BiffException;

public class Main {

	private Map<Integer, Flight> mapFlights;
	private Map<String, AircraftModel> mapModels;
	private Map<String, Airport> mapAirports;
	private Map<Long, CrewMember> mapCrewMembers;
	private Map<String, Rank> mapRanks;
	private Map<String, Aircraft> mapAircrafts;

	public static void main(String args[]) {
		Main main = new Main();
		main.parse();
	}

	public Timestamp stringToTimestamp(String value) {
		if (value.equals(""))
			return null;

		DateFormat dateFormat = new SimpleDateFormat("dd MM yy hh:mm:ss");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(parsedDate.getTime());
	}

	public void parse() {

		mapFlights = new HashMap<Integer, Flight>();
		mapModels = new HashMap<String, AircraftModel>();
		mapAirports = new HashMap<String, Airport>();
		mapCrewMembers = new HashMap<Long, CrewMember>();
		mapRanks = new HashMap<String, Rank>();
		mapAircrafts = new HashMap<String, Aircraft>();

		Workbook flightsFile = null;

		try {
			flightsFile = Workbook
					.getWorkbook(new File(
							"/home/canastro/Documentos/FEUP/AIAD/FLIGHTS_2009_09_Planeado_Real_original.xls"));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Sheet sheet;
		sheet = flightsFile.getSheet(2);
		getAircraftModels(sheet);

		sheet = flightsFile.getSheet(1);
		getAircrafts(sheet);

		sheet = flightsFile.getSheet(3);
		getAirports(sheet);

		sheet = flightsFile.getSheet(5);
		getRanks(sheet);

		sheet = flightsFile.getSheet(4);
		getCrewMembers(sheet);

		sheet = flightsFile.getSheet(0);
		getFlights(sheet);

		flightsFile.close();
	}

	private void getAircrafts(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			int model = Integer.parseInt(sheet.getCell(0, i).getContents());
			AircraftModel aircraftModel = mapModels.get(model);

			String licensePlate = sheet.getCell(1, i).getContents();

			Aircraft aircraft = new Aircraft(licensePlate, aircraftModel);
			mapAircrafts.put(licensePlate, aircraft);
		}

	}

	private void getRanks(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			String rankStr = sheet.getCell(0, i).getContents();
			String description = sheet.getCell(1, i).getContents();
			String models = sheet.getCell(2, i).getContents();

			String[] model = models.split(";");

			List<AircraftModel> aircraftModels = new ArrayList<AircraftModel>();
			for (int j = 0; i != model.length; i++)
				aircraftModels.add(mapModels.get(model));

			Rank rank = new Rank(rankStr, description, aircraftModels);

			mapRanks.put(rankStr, rank);

		}

	}

	private void getCrewMembers(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			Long memberNumber = Long.parseLong(sheet.getCell(0, i)
					.getContents());
			String name = sheet.getCell(1, i).getContents();
			String rankStr = sheet.getCell(2, i).getContents();

			Rank rank = mapRanks.get(rankStr);

			float costHour = Float
					.parseFloat(sheet.getCell(3, i).getContents());

			float costPerDiem = Float.parseFloat(sheet.getCell(4, i)
					.getContents());

			CrewMember crewMember = new CrewMember(memberNumber, name, rank,
					costHour, costPerDiem);

			mapCrewMembers.put(memberNumber, crewMember);
		}
	}

	private void getAirports(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			String airportCode = sheet.getCell(0, i).getContents();
			String name = sheet.getCell(1, i).getContents();
			String city = sheet.getCell(2, i).getContents();
			String country = sheet.getCell(3, i).getContents();

			Airport airport = new Airport(airportCode, name, city, country);
			mapAirports.put(airportCode, airport);
		}
	}

	private void getAircraftModels(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			String model = sheet.getCell(0, i).getContents();
			String description = sheet.getCell(3, i).getContents();
			float costHour = Float
					.parseFloat(sheet.getCell(2, i).getContents());
			int nrCabinCrewMembers = Integer.parseInt(sheet.getCell(1, i)
					.getContents());

			AircraftModel mod = new AircraftModel(model, description, costHour,
					nrCabinCrewMembers);
			mapModels.put(model, mod);
		}

	}

	private void getFlights(Sheet sheet) {

		for (int i = 1; i != sheet.getRows(); i++) {
			Timestamp flightDate = stringToTimestamp(sheet.getCell(0, i)
					.getContents());

			int flightNumber = Integer.parseInt(sheet.getCell(1, i)
					.getContents());

			Timestamp departureTime = stringToTimestamp(sheet.getCell(6, i)
					.getContents());
			Timestamp arrivalTime = stringToTimestamp(sheet.getCell(7, i)
					.getContents());

			int busSaleSeats = Integer.parseInt(sheet.getCell(8, i)
					.getContents());
			int econSaleSeats = Integer.parseInt(sheet.getCell(9, i)
					.getContents());

			int busActlSeats = Integer.parseInt(sheet.getCell(10, i)
					.getContents());
			int econActlSeats = Integer.parseInt(sheet.getCell(11, i)
					.getContents());

			String depAirportCode = sheet.getCell(2, i).getContents();
			String arrAirportCode = sheet.getCell(3, i).getContents();
			Airport depAirport = mapAirports.get(depAirportCode);
			Airport arrAirport = mapAirports.get(arrAirportCode);

			String aircraftLicensePlate = sheet.getCell(3, i).getContents();
			Aircraft aircraft = mapAircrafts.get(aircraftLicensePlate);

			Flight flight = new Flight(flightNumber, flightDate, departureTime,
					arrivalTime, econSaleSeats, busSaleSeats, econActlSeats,
					busActlSeats, depAirport, arrAirport);

			mapFlights.put(flightNumber, flight);
		}

	}

}
