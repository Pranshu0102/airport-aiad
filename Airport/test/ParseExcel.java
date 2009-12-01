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

import problems.Event;

import airline.Aircraft;
import airline.AircraftModel;
import airline.Airport;
import airline.CrewMember;
import airline.EscCrew;
import airline.Flight;
import airline.Rank;

import jxl.*;
import jxl.read.biff.BiffException;

public class ParseExcel {

	private Workbook flightsFile = null;

	public ParseExcel() {
	}

	public Workbook getFile() {
		return flightsFile;
	}

	public void openFile(String fileStr) {
		try {
			flightsFile = Workbook.getWorkbook(new File(fileStr));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeFile() {
		flightsFile.close();
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

	public ArrayList<EscCrew> getEscCrews(ArrayList<Flight> flights,
			ArrayList<CrewMember> crewMembers, Map<String, Rank> mapRanks) {

		ArrayList<EscCrew> escCrews = new ArrayList<EscCrew>();
		Flight flight;
		EscCrew escCrew;
		for (int i = 0; i != flights.size(); i++) {
			flight = flights.get(i);

			int escCrewId = avaiableCrew(escCrews,
					flight.getDepartureAirport(), flight.getDepartureTime());

			if (escCrewId != -1) {
				escCrew = escCrews.get(escCrewId);

				if (!escCrews.get(escCrewId).isWorkTimeLimitReached()) {
					Long timeEnd = flight.getDepartureTime().getTime();
					Long timeEndLastFlight = escCrews.get(escCrewId)
							.getEndTime().getTime();

					escCrew.setWorkTime(escCrew.getWorkTime()
							+ Math.abs(timeEnd - timeEndLastFlight));
					escCrew.addFlight(flight);
					escCrew.setLastAirport(flight.getArrivalAirport());
					escCrews.remove(escCrewId);
					escCrews.add(escCrewId, escCrew);
					// Erro que d� aqui tem a ver com a m� cria�ao de
					// crews, crews sem elementos....
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
				escCrew = createNewEscCrew(flight, crewMembers, mapRanks);
				escCrews.add(escCrew);

			}

		}
		return escCrews;
	}

	public EscCrew createNewEscCrew(Flight flight,
			ArrayList<CrewMember> crewMembers, Map<String, Rank> mapRanks) {
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
			// voo e verifico se esse pilot est� livre a essa hora
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
		if (escMembers.get(1) == null) {
			System.out.println("aqui");
		}
		EscCrew escCrew = new EscCrew(escMembers, flight);
		Long timeEnd = flight.getDepartureTime().getTime();
		Long timeStart = flight.getArrivalTime().getTime();
		escCrew.setWorkTime(Math.abs(timeEnd - timeStart));

		return escCrew;
	}

	public int avaiableCrew(ArrayList<EscCrew> escCrews,
			Airport departureAirport, Timestamp departureTime) {
		EscCrew escCrew = null;
		for (int i = 0; i != escCrews.size(); i++) {
			// Falta aqui verificar se eles estao ah mais de 5 dias em voos e
			// t�m que retornar a casa
			escCrew = escCrews.get(i);
			if (escCrew.getLastAirport() == departureAirport
					&& escCrew.getEndTime().before(departureTime)) {
				return i;
			}
		}
		return -1;
	}

	public Map<String, Aircraft> getAircrafts(Sheet sheet,
			Map<String, AircraftModel> mapModels) {
		Map<String, Aircraft> mapAircrafts = new HashMap<String, Aircraft>();
		for (int i = 1; i != sheet.getRows(); i++) {
			String model = sheet.getCell(0, i).getContents();
			AircraftModel aircraftModel = mapModels.get(model);

			String licensePlate = sheet.getCell(1, i).getContents();

			Aircraft aircraft = new Aircraft(licensePlate, aircraftModel);
			mapAircrafts.put(licensePlate, aircraft);
		}
		return mapAircrafts;
	}

	public Map<String, Rank> getRanks(Sheet sheet,
			Map<String, AircraftModel> mapModels) {
		Map<String, Rank> mapRanks = new HashMap<String, Rank>();
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
		return mapRanks;
	}

	public ArrayList<CrewMember> getCrewMembers(Sheet sheet,
			Map<String, Rank> mapRanks) {
		ArrayList<CrewMember> crewMembers = new ArrayList<CrewMember>();
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
		return crewMembers;
	}

	public Map<String, Airport> getAirports(Sheet sheet) {

		Map<String, Airport> mapAirports = new HashMap<String, Airport>();
		for (int i = 1; i != sheet.getRows(); i++) {
			String airportCode = sheet.getCell(0, i).getContents();
			String name = sheet.getCell(1, i).getContents();
			String city = sheet.getCell(2, i).getContents();
			String country = sheet.getCell(3, i).getContents();

			Airport airport = new Airport(airportCode, name, city, country);
			mapAirports.put(airportCode, airport);

		}
		return mapAirports;
	}

	public Map<String, AircraftModel> getAircraftModels(Sheet sheet) {

		Map<String, AircraftModel> mapModels = new HashMap<String, AircraftModel>();

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
		return mapModels;
	}

	public ArrayList<Flight> getFlights(Sheet sheet,
			Map<String, Airport> mapAirports, Map<String, Aircraft> mapAircrafts) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
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
		return flights;
	}

	public ArrayList<Event> getEvents(Sheet sheet, ArrayList<Flight> flights,
			ArrayList<EscCrew> escCrews) {

		ArrayList<Event> events = new ArrayList<Event>();
		Flight flight = null;
		EscCrew escCrew;
		CrewMember crewMember = null;
		Event event = null;

		for (int i = 1; i != sheet.getRows(); i++) {

			String type = sheet.getCell(0, i).getContents();
			String[] identification = sheet.getCell(1, i).getContents().split(
					";");
			String description = sheet.getCell(2, i).getContents();
			int delay = Integer.parseInt(sheet.getCell(3, i).getContents());
			
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(identification[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			for (int j = 0; j != flights.size(); j++) {
				if (flights.get(j).getFlightDate().compareTo(parsedDate) == 0
						&& flights.get(j).getFlightNumber() == Integer
								.parseInt(identification[1])) {
					flight = flights.get(j);
				}
			}

			if (type.equalsIgnoreCase("crewMember")) {
				for (int j = 0; j != escCrews.size(); j++) {
					escCrew = escCrews.get(j);
					if (escCrew.getFlights().contains(flight)) {
						crewMember = escCrew.getCrewMembers().get(
								Integer.parseInt(identification[2]));
					}
				}
				event = new Event(type, flight, crewMember, description, delay);
			} else {
				event = new Event(type, flight, description, delay);
			}
			events.add(event);
		}
		return events;
	}
}
