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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import airline.Aircraft;
import airline.AircraftModel;
import airline.Airport;
import airline.CrewMember;
import airline.EscCrew;
import airline.Flight;
import airline.Rank;

import jxl.*;
import jxl.read.biff.BiffException;

public class Main {

	private ArrayList<Flight> flights;
	private Map<String, AircraftModel> mapModels;
	private Map<String, Airport> mapAirports;
	private ArrayList<CrewMember> crewMembers;
	private Map<String, Rank> mapRanks;
	private Map<String, Aircraft> mapAircrafts;
	private ArrayList<EscCrew> escCrews;

	public static void main(String args[]) {
		Main main = new Main();
		main.parse();
	}

	private Date stringToDate(String value) {
		if (value.equals(""))
			return null;

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

	public Timestamp stringToTimestamp(String value) {
		if (value.equals(""))
			return null;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(parsedDate.getTime());
	}

	public void parse() {

		flights = new ArrayList<Flight>();
		mapModels = new HashMap<String, AircraftModel>();
		mapAirports = new HashMap<String, Airport>();
		crewMembers = new ArrayList<CrewMember>();
		mapRanks = new HashMap<String, Rank>();
		mapAircrafts = new HashMap<String, Aircraft>();
		escCrews = new ArrayList<EscCrew>();

		Workbook flightsFile = null;

		try {
			flightsFile = Workbook.getWorkbook(new File("FLIGHTS_2009_09.xls"));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Sheet sheet;
		sheet = flightsFile.getSheet(2);
		getAircraftModels(sheet);
		System.out.println("Aircraft Models imported... ");

		sheet = flightsFile.getSheet(1);
		getAircrafts(sheet);
		System.out.println("Aircrafts Imported...");

		sheet = flightsFile.getSheet(3);
		getAirports(sheet);
		System.out.println("Airports  Imported... ");

		sheet = flightsFile.getSheet(5);
		getRanks(sheet);
		System.out.println("Ranks imported... ");

		sheet = flightsFile.getSheet(4);
		getCrewMembers(sheet);
		System.out.println("Crew Members imported... ");

		sheet = flightsFile.getSheet(0);
		getFlights(sheet);
		System.out.println("Flights imported...");

		addEscCrews();

		for(int i = 0 ; i!= escCrews.size(); i++)
			escCrews.get(i).print();
		flightsFile.close();
	}

	private void addEscCrews() {
		// 1.
		// Abrir primeiro voo
		Flight flight;
		EscCrew escCrew;
		for (int i = 0; i != flights.size(); i++) {
			flight = flights.get(i);

			int escCrewId = avaiableCrew(flight.getDepartureAirport(), flight
					.getDepartureTime());

			if (escCrewId != -1) {
				escCrew = escCrews.get(escCrewId);
				
				if (!escCrews.get(escCrewId).isWorkTimeLimitReached()) {
					Long timeEnd = flight.getDepartureTime().getTime();
					Long timeStart = flight.getArrivalTime().getTime();
					
					escCrew.setWorkTime(escCrew.getWorkTime()+Math.abs(timeEnd-timeStart));
					escCrew.addFlight(flight);
					escCrew.setLastAirport(flight.getArrivalAirport());
					escCrews.remove(escCrewId);
					escCrews.add(escCrewId, escCrew);
					//Erro que dá aqui tem a ver com a má criaçao de crews, crews sem elementos....
				} else if (flight.getArrivalAirport() == escCrew
						.getCrewMembers().get(1).getBaseAirport()) {
					// ja voltou ah base, posso apagar toda a listagem de voos e
					// considera-los aptos a viajar outra vez?
					escCrew.setWorkTime(0L);
					escCrew.addFlight(flight);
					escCrew.setLastAirport(flight.getArrivalAirport());
					escCrews.remove(escCrewId);
					escCrews.add(escCrewId, escCrew);
					
				}
			} else {
				escCrew = createNewEscCrew(flight);
				escCrews.add(escCrew);
				
			}

		}

	}

	private EscCrew createNewEscCrew(Flight flight) {
		AircraftModel aircraftModel = flight.getAircraft().getModel();
		int numberCabinCrewMembers = aircraftModel.getNrCabinCrewMembers();

		Rank pilotRank = null;
		Rank crewMembersRank = null;

		CrewMember crewMember;
		ArrayList<CrewMember> escMembers = new ArrayList<CrewMember>();

		int nCabinCrew = 0;
		int nPilots = 0;

		// provavelmente vai dar erro aqui, se calhar vou ter de passar Rank
		// para ArrayList

		Set set = mapRanks.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry me = (Map.Entry) it.next();
			if (((Rank) me.getValue()).getAircraftModels().contains(
					aircraftModel)
					&& !((String) me.getKey()).equalsIgnoreCase("2"))
				pilotRank = ((Rank) me.getValue());
			if (((String) me.getKey()).equalsIgnoreCase("2"))
				crewMembersRank = ((Rank) me.getValue());
		}

		int k = 0;
		while ((k != crewMembers.size())
				&& (nPilots < 2 || nCabinCrew < numberCabinCrewMembers)) {

			crewMember = crewMembers.get(k);

			// Aqui em vez de ter um boolean, vou enviar a hora de partida do
			// voo e verifico se esse pilot está livre a essa hora
			if (crewMember.getAvaiable(flight)) {
				if (crewMember.getRank() == pilotRank && nPilots < 2) {
					escMembers.add(crewMember);
					if (crewMember.getBaseAirport() == null)
						crewMember.setBaseAirport(flight.getDepartureAirport());
					crewMember.setLastFlight(flight);
					nPilots++;
				} else if (crewMember.getRank() == crewMembersRank
						&& nCabinCrew < numberCabinCrewMembers) {
					escMembers.add(crewMember);
					if (crewMember.getBaseAirport() == null)
						crewMember.setBaseAirport(flight.getDepartureAirport());
					crewMember.setLastFlight(flight);
					nCabinCrew++;
				}
			}
			k++;
		}

		// System.out.println("Flight nr: " + flight.getFlightNumber() +
		// " Date: "
		// + flight.getFlightDate() + ":");
		// for (int i = 0; i != escMembers.size(); i++) {
		// System.out.println(escMembers.get(i).getName() + " "
		// + escMembers.get(i).getRank().getDescription());
		// }
		// System.out.println("------");
		if(escMembers.get(1)==null)
		{
			System.out.println("aqui");
		}
		EscCrew escCrew = new EscCrew(escMembers, flight);
		Long timeEnd = flight.getDepartureTime().getTime();
		Long timeStart = flight.getArrivalTime().getTime();
		escCrew.setWorkTime(Math.abs(timeEnd-timeStart));
		
		return escCrew;
	}

	private int avaiableCrew(Airport departureAirport, Timestamp departureTime) {
		EscCrew escCrew = null;
		for (int i = 0; i != escCrews.size(); i++) {
			// Falta aqui verificar se eles estao ah mais de 5 dias em voos e
			// têm que retornar a casa
			escCrew = escCrews.get(i);
			if (escCrew.getLastAirport() == departureAirport
					&& escCrew.getEndTime().before(departureTime)) {
				return i;
			}
		}
		return -1;
	}

	private void getAircrafts(Sheet sheet) {
		for (int i = 1; i != sheet.getRows(); i++) {
			String model = sheet.getCell(0, i).getContents();
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
			for (int j = 0; j != model.length; j++)
				aircraftModels.add(mapModels.get(model[j]));

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

			crewMembers.add(crewMember);
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
			Date flightDate = stringToDate(sheet.getCell(0, i).getContents());

			int flightNumber = Integer.parseInt(sheet.getCell(1, i)
					.getContents());

			String depAirportCode = sheet.getCell(2, i).getContents();
			String arrAirportCode = sheet.getCell(3, i).getContents();
			Airport depAirport = mapAirports.get(depAirportCode);
			Airport arrAirport = mapAirports.get(arrAirportCode);

			Timestamp departureTime = stringToTimestamp(sheet.getCell(4, i)
					.getContents());
			Timestamp arrivalTime = stringToTimestamp(sheet.getCell(5, i)
					.getContents());

			int busSaleSeats = Integer.parseInt(sheet.getCell(6, i)
					.getContents());
			int econSaleSeats = Integer.parseInt(sheet.getCell(7, i)
					.getContents());

			int busActlSeats = Integer.parseInt(sheet.getCell(8, i)
					.getContents());
			int econActlSeats = Integer.parseInt(sheet.getCell(9, i)
					.getContents());

			String aircraftLicensePlate = sheet.getCell(10, i).getContents();
			Aircraft aircraft = mapAircrafts.get(aircraftLicensePlate);

			Flight flight = new Flight(flightNumber, flightDate, departureTime,
					arrivalTime, econSaleSeats, busSaleSeats, econActlSeats,
					busActlSeats, depAirport, arrAirport, aircraft);

			flights.add(flight);

		}

	}

}
