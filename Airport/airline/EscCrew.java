package airline;

import java.sql.Timestamp;
import java.util.List;

public class EscCrew {
	private List<CrewMember> crewMembers;
	private List<Flight> flights;
	private Timestamp startTime;
	private Timestamp endTime;
	private Airport lastAirport;

	public EscCrew(List<CrewMember> crewMembers, Flight flight) {
		super();
		this.crewMembers = crewMembers;
		this.flights.add(flight);
		this.startTime = flight.getDepartureTime();
		this.endTime = flight.getArrivalTime();
		this.lastAirport = flight.getArrivalAirport();
	}

	public Airport getLastAirport() {
		return lastAirport;
	}

	public void setLastAirport(Airport lastAirport) {
		this.lastAirport = lastAirport;
	}

	public void addFlight(Flight flight) {
		this.flights.add(flight);
		endTime = flight.getArrivalTime();
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}

	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}
