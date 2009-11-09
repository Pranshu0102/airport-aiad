package airport;

import java.util.GregorianCalendar;
import java.util.List;

public class Flight {
	private int flightNumber;
	
	Aircraft aircraft;
	List<CrewMember> crewMembers;
	
	private int nPassangers;
	private float totalCost;
	
	private GregorianCalendar departure;
	private GregorianCalendar arrival;
	
	public Flight(int flightNumber, Aircraft aircraft, List<CrewMember> crewMembers, int passangers, float totalCost, GregorianCalendar departure, GregorianCalendar arrival) {
		super();
		this.flightNumber = flightNumber;
		this.aircraft = aircraft;
		this.crewMembers = crewMembers;
		nPassangers = passangers;
		this.totalCost = totalCost;
		this.departure = departure;
		this.arrival = arrival;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public GregorianCalendar getArrival() {
		return arrival;
	}

	public void setArrival(GregorianCalendar arrival) {
		this.arrival = arrival;
	}

	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}

	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
	}

	public GregorianCalendar getDeparture() {
		return departure;
	}

	public void setDeparture(GregorianCalendar departure) {
		this.departure = departure;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getNPassangers() {
		return nPassangers;
	}

	public void setNPassangers(int passangers) {
		nPassangers = passangers;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	
	
	
	
	
}
