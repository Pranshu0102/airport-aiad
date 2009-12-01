package problems;

public class PaxProblem {
	private String description;
	private int minutesDelay;
	public PaxProblem(String description, int minutesDelay) {
		super();
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	public void print() {
		System.out.println("PROBLEM: Pax "+description+" "+minutesDelay+" min");
		
	}
}
