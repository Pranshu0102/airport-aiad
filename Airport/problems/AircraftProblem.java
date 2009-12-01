package problems;

public class AircraftProblem {
	private String description;
	private int minutesDelay;
	public AircraftProblem(String description, int minutesDelay) {
		super();
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	public void print() {
		System.out.println("PROBLEM: Aircraft "+description+" "+minutesDelay+" min");
		
	}
}
