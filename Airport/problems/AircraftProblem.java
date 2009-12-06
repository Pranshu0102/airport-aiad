package problems;

import jade.util.leap.Serializable;

public class AircraftProblem implements Serializable{
	private String description;
	private int minutesDelay;
	public AircraftProblem(String description, int minutesDelay) {
		super();
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
		System.out.println("PROBLEM: Aircraft "+description+" "+minutesDelay+" min");
		
	}
}
