package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	Map<String, Airport> airport ;
	Map<String, AircraftModel> airModel;
	ArrayList<Flight> flight;
	ArrayList<EscCrew> escCrews ;
	
	public static void main(String args[]) {
		test main = new test();
		main.parse();
	}


	
	public void parse ()
	{
		// Ler Planeamento.
		ParseExcel parExc = new ParseExcel();
		
		airModel = parExc.getAircraftModels(parExc.getFile().getSheet(2));
		airport = parExc.getAirports(parExc.getFile().getSheet(3));
		aircrft = parExc.getAircrafts(parExc.getFile().getSheet(1), airModel);
		rank = parExc.getRanks(parExc.getFile().getSheet(5), airModel);
		crewmember = parExc.getCrewMembers(parExc.getFile().getSheet(4), rank);
		
		
		flight = parExc.getFlights(parExc.getFile().getSheet(0), airport, aircrft);
		escCrews = parExc.getEscCrews(flight, crewmember, rank);
		
		for(int i = 0 ; i!= escCrews.size(); i++)
		{
			escCrews.get(i).print();
			System.out.println("----------------------------------------");
		}
	}
}
