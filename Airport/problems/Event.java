package problems;

import airline.CrewMember;
import airline.Flight;

public class Event {
	private String type;
	private Flight flight;
	private CrewMember crewMember;
	private int numPax;
	private String description;
	private int delay;
	
	public Event(String type, Flight flight, String description, int delay) {
		super();
		this.type = type;
		this.flight = flight;
		this.description = description;
		this.delay = delay;
	}
	public Event(String type, Flight flight, CrewMember crewMember,
			String description, int delay) {
		super();
		this.type = type;
		this.flight = flight;
		this.crewMember = crewMember;
		this.description = description;
		this.delay = delay;
	}
	
	public Event(String type, Flight flight, int numPax,
			String description, int delay) {
		super();
		this.type = type;
		this.flight = flight;
		this.numPax = numPax;
		this.description = description;
		this.delay = delay;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public CrewMember getCrewMember() {
		return crewMember;
	}
	public void setCrewMember(CrewMember crewMember) {
		this.crewMember = crewMember;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public void print() {
		System.out.println("Type: "+ type);
		flight.print();
		if(crewMember != null)
			crewMember.print();
		System.out.println("Description: "+description);
		System.out.println("Delay: "+delay);
		
	}
	public int getNumPax() {
		return numPax;
	}
	public void setNumPax(int numPax) {
		this.numPax = numPax;
	}

}
