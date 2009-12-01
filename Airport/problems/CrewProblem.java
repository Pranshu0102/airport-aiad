package problems;
public class CrewProblem {
	private String description;
	private int minutesDelay;
	public CrewProblem(String description, int minutesDelay) {
		super();
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	public void print() {
		System.out.println("PROBLEM: Crew "+description+" "+minutesDelay+" min");
		
	}
	
}
