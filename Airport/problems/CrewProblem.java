package problems;
import airline.CrewMember;
import jade.util.leap.Serializable;

public class CrewProblem implements Serializable {
	private String description;
	private int minutesDelay;
	private CrewMember crewMember;
	
	public CrewProblem(CrewMember crewMember, String description, int minutesDelay) {
		super();
		this.crewMember = crewMember;
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	public String getDescription() {
		return description;
	}
	public int getMinutesDelay() {
		return minutesDelay;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMinutesDelay(int minutesDelay) {
		this.minutesDelay = minutesDelay;
	}
	public void print() {
		System.out.println("PROBLEM: Crew "+description+" "+minutesDelay+" min");
		
	}
	
}
