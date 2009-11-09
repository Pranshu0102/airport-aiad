package airport;

import java.util.GregorianCalendar;
import java.util.List;

public class Flight {
	private int flightNumber;
	
	private String departureAirport;
	private String arrivalAirport;
	
	//ou usar timestamp?
	private GregorianCalendar departureTime;
	private GregorianCalendar arrivalTime;
	
	private int busSaleSeats;
	private int econSaleSeats;
	
	private int busActlPax;
	private int econActlPax;
	
	private GregorianCalendar estOffblkDate;
	private GregorianCalendar estOnblkDate;
	
	private GregorianCalendar actlOffblkDate;
	private GregorianCalendar actlOnblkDate;
	
	Aircraft aircraft;
	List<CrewMember> crewMembers;
	
	private int nPassangers;
	private float totalCost;
	
	
	public Flight(int flightNumber, String departureAirport,
			String arrivalAirport, GregorianCalendar departureTime,
			GregorianCalendar arrivalTime, int busSaleSeats, int econSaleSeats,
			int busActlPax, int econActlPax, GregorianCalendar estOffblkDate,
			GregorianCalendar estOnblkDate, GregorianCalendar actlOffblkDate,
			GregorianCalendar actlOnblkDate, Aircraft aircraft,
			List<CrewMember> crewMembers, int nPassangers, float totalCost) {
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
		this.nPassangers = nPassangers;
		this.totalCost = totalCost;
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


	public GregorianCalendar getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(GregorianCalendar departureTime) {
		this.departureTime = departureTime;
	}


	public GregorianCalendar getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(GregorianCalendar arrivalTime) {
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


	public GregorianCalendar getEstOffblkDate() {
		return estOffblkDate;
	}


	public void setEstOffblkDate(GregorianCalendar estOffblkDate) {
		this.estOffblkDate = estOffblkDate;
	}


	public GregorianCalendar getEstOnblkDate() {
		return estOnblkDate;
	}


	public void setEstOnblkDate(GregorianCalendar estOnblkDate) {
		this.estOnblkDate = estOnblkDate;
	}


	public GregorianCalendar getActlOffblkDate() {
		return actlOffblkDate;
	}


	public void setActlOffblkDate(GregorianCalendar actlOffblkDate) {
		this.actlOffblkDate = actlOffblkDate;
	}


	public GregorianCalendar getActlOnblkDate() {
		return actlOnblkDate;
	}


	public void setActlOnblkDate(GregorianCalendar actlOnblkDate) {
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


	public int getnPassangers() {
		return nPassangers;
	}


	public void setnPassangers(int nPassangers) {
		this.nPassangers = nPassangers;
	}


	public float getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	
}
