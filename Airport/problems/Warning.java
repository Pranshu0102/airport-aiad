package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import airline.Flight;

public class Warning implements Serializable {

	private String type; // type = {problem, warning}
	private String description;
	private int minutesDelay;
	private Flight flight;
	
	public Warning(Flight flight, String type, String description, int minutesDelay) {
		super();
		this.flight = flight;
		this.type = type;
		this.description = description;
		this.minutesDelay = minutesDelay;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinutesDelay() {
		return minutesDelay;
	}
	public void setMinutesDelay(int minutesDelay) {
		this.minutesDelay = minutesDelay;
	}
	public void print() {
		System.out.println("WARNING: "+type+" "+description+" "+minutesDelay+" min");
		
	}
	
	
}