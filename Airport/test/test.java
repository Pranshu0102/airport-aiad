package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import problems.Event;
import problems.Problem;
import problems.Warning;

import airline.Aircraft;
import airline.AircraftModel;
import airline.Airport;
import airline.CrewMember;
import airline.EscCrew;
import airline.Flight;
import airline.Rank;

public class test {
	Map<String, Aircraft> aircrft;
	Map<String, Rank> rank;
	ArrayList<CrewMember> crewmember;
	Map<String, Airport> airport;
	Map<String, AircraftModel> airModel;
	ArrayList<Flight> flight;
	ArrayList<EscCrew> escCrews;
	ArrayList<Event> events;
	
	Map<String,Problem> problems;

	public static void main(String args[]) {
		test main = new test();
		main.parseFlights();
		main.parseEvents();
		main.analiseEvents();
	}

	private void analiseEvents() {
		String type;
		int delay;
		String description;
		
		int warningAircraft = 50;
		int warningCrew = 25;	
		int warningPax = 35;
		Warning warning;
		
	
		for (int i = 0; i != events.size(); i++) {
			warning = null;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			type = events.get(i).getType();
			delay = events.get(i).getDelay();
			description = events.get(i).getDescription();
			
			if(type.equalsIgnoreCase("aircraft"))
			{
				if(delay<warningAircraft)
				{
					warning= new Warning(type, description, delay);
				}
				else
					System.out.println("PROBLEM AIRCRAFT");
			} else if(type.equalsIgnoreCase("crewmember"))
			{
				if(delay<warningCrew)
				{
					warning= new Warning(type, description, delay);
				}
				else
					System.out.println("PROBLEM CREW ");
			}else
			{
				if(delay<warningPax)
				{
					warning= new Warning(type, description, delay);
				}
				else
					System.out.println("PROBLEM PAX ");
			}
			if(warning!=null)
				warning.print();
		}

	}

	private void parseEvents() {
		ParseExcel parExc = new ParseExcel();
		parExc.openFile("EVENTS.xls");

		events = parExc.getEvents(parExc.getFile().getSheet(0), flight,
				escCrews);

//		for (int i = 0; i != events.size(); i++) {
//			events.get(i).print();
//			System.out.println("----------------------------------------");
//		}

		parExc.closeFile();
	}

	public void parseFlights() {
		// Ler Planeamento.
		ParseExcel parExc = new ParseExcel();
		parExc.openFile("FLIGHTS_2009_09.xls");
		airModel = parExc.getAircraftModels(parExc.getFile().getSheet(2));
		airport = parExc.getAirports(parExc.getFile().getSheet(3));
		aircrft = parExc.getAircrafts(parExc.getFile().getSheet(1), airModel);
		rank = parExc.getRanks(parExc.getFile().getSheet(5), airModel);
		crewmember = parExc.getCrewMembers(parExc.getFile().getSheet(4), rank);

		flight = parExc.getFlights(parExc.getFile().getSheet(0), airport,
				aircrft);
		escCrews = parExc.getEscCrews(flight, crewmember, rank);
		parExc.closeFile();

		for (int i = 0; i != escCrews.size(); i++) {
			escCrews.get(i).print();
			System.out.println("----------------------------------------");
		}
		System.out.println("----------------------------------------");
	}
}
