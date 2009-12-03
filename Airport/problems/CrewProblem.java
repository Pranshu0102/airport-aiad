package problems;

import airline.CrewMember;

public class CrewProblem {
	private String description;
	private int minutesDelay;
	private CrewMember crewMember;
	
	public CrewProblem(CrewMember crewMember, String description, int minutesDelay) {
		super();
		this.crewMember = crewMember;
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	
	public void print() {
		System.out.println("PROBLEM: Crew "+description+" "+minutesDelay+" min");
		
	}
	
}
