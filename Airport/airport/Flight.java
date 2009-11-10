package airport;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

public class Flight {
	private int flightNumber;
	
	private String departureAirport;
	private String arrivalAirport;
	
	//ou usar timestamp?
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	
	private int busSaleSeats;
	private int econSaleSeats;
	
	private int busActlPax;
	private int econActlPax;
	
	private Timestamp estOffblkDate;
	private Timestamp estOnblkDate;
	
	private Timestamp actlOffblkDate;
	private Timestamp actlOnblkDate;
	
	Aircraft aircraft;
	List<CrewMember> crewMembers;

	private float totalCost;

	public Flight(int flightNumber, String departureAirport,
			String arrivalAirport, Timestamp departureTime,
			Timestamp arrivalTime, int busSaleSeats, int econSaleSeats,
			int busActlPax, int econActlPax, Timestamp estOffblkDate,
			Timestamp estOnblkDate, Timestamp actlOffblkDate,
			Timestamp actlOnblkDate, Aircraft aircraft,
			List<CrewMember> crewMembers) {
		super();
		this.flightNumber = flightNumber;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.busSaleSeats = busSaleSeats;
		this.econSaleSeats = econSaleSeats;
		this.busActlPax = busActlPax;
		this.econActlPax = econActlPax;
		this.estOffblkDate = estOffblkDate;
		this.estOnblkDate = estOnblkDate;
		this.actlOffblkDate = actlOffblkDate;
		this.actlOnblkDate = actlOnblkDate;
		this.aircraft = aircraft;
		this.crewMembers = crewMembers;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBusSaleSeats() {
		return busSaleSeats;
	}

	public void setBusSaleSeats(int busSaleSeats) {
		this.busSaleSeats = busSaleSeats;
	}

	public int getEconSaleSeats() {
		return econSaleSeats;
	}

	public void setEconSaleSeats(int econSaleSeats) {
		this.econSaleSeats = econSaleSeats;
	}

	public int getBusActlPax() {
		return busActlPax;
	}

	public void setBusActlPax(int busActlPax) {
		this.busActlPax = busActlPax;
	}

	public int getEconActlPax() {
		return econActlPax;
	}

	public void setEconActlPax(int econActlPax) {
		this.econActlPax = econActlPax;
	}

	public Timestamp getEstOffblkDate() {
		return estOffblkDate;
	}

	public void setEstOffblkDate(Timestamp estOffblkDate) {
		this.estOffblkDate = estOffblkDate;
	}

	public Timestamp getEstOnblkDate() {
		return estOnblkDate;
	}

	public void setEstOnblkDate(Timestamp estOnblkDate) {
		this.estOnblkDate = estOnblkDate;
	}

	public Timestamp getActlOffblkDate() {
		return actlOffblkDate;
	}

	public void setActlOffblkDate(Timestamp actlOffblkDate) {
		this.actlOffblkDate = actlOffblkDate;
	}

	public Timestamp getActlOnblkDate() {
		return actlOnblkDate;
	}

	public void setActlOnblkDate(Timestamp actlOnblkDate) {
		this.actlOnblkDate = actlOnblkDate;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}

	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	
	public void list()
	{
		System.out.println("--------------------");
		System.out.println("Flight Number: "+flightNumber);
		System.out.println("Departure Airport: "+departureAirport);
		System.out.println("Arrival Airport: "+arrivalAirport); 
		System.out.println("Departure Time: "+departureTime);
		System.out.println("Arrival Time: "+arrivalTime);
		System.out.println("Executive Seats for Sale: "+busSaleSeats);
		System.out.println("Economic Seats for Sale: "+econSaleSeats);
		System.out.println("Executive Seats for Sold: "+busActlPax); 
		System.out.println("Economic Seats for Sold: "+econActlPax);
		System.out.println("New estimate departure time after delay: "+ estOffblkDate);
		System.out.println("New estimate arrival time after delay: "+ estOnblkDate);
		System.out.println("Real departure time after delay: "+ actlOffblkDate);
		System.out.println("Real arrival time after delay: "+ actlOnblkDate);
		System.out.println("--------------------\n");
	}
	
	
	
}
