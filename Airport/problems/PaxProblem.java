package problems;

import jade.util.leap.Serializable;

public class PaxProblem implements Serializable{
	private String description;
	private int minutesDelay;
	private int num_Pax;
	public PaxProblem(String description, int minutesDelay, int num_Pax) {
		super();
		this.description = description;
		this.minutesDelay = minutesDelay;
		this.num_Pax = num_Pax;
	}
	public int getNumPax()
	{
		return this.num_Pax;
	}
	
	public void setNumPax(int n)
	{
		this.num_Pax = n;
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
		System.out.println("PROBLEM: Pax "+description+". Envolving "+ num_Pax+" passangers with "+minutesDelay+" min");
		
	}
}
