package airline;

import java.sql.Timestamp;
import java.util.List;

public class EscCrew {
	private List<CrewMember> crewMembers;
	private Flight flight;
	private Timestamp startTime;
	private Timestamp endTime;
	public EscCrew(List<CrewMember> crewMembers, Flight flight,
			Timestamp startTime, Timestamp endTime) {
		super();
		this.crewMembers = crewMembers;
		this.flight = flight;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}
	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
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
