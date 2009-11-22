package airline;

import java.sql.Timestamp;

public class Flight {

	private int flightNumber;
	private Timestamp flightDate;
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	private int econSaleSeats;
	private int busSaleSeats;
	private int econActlSeats;
	private int busActlSeats;
	private Airport departureAirport;
	private Airport arrivalAirport;

	public Flight(int flightNumber, Timestamp flightDate,
			Timestamp departureTime, Timestamp arrivalTime, int econSaleSeats,
			int busSaleSeats, int econActlSeats, int busActlSeats,
			Airport departureAirport, Airport arrivalAirport) {
		super();
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.econSaleSeats = econSaleSeats;
		this.busSaleSeats = busSaleSeats;
		this.econActlSeats = econActlSeats;
		this.busActlSeats = busActlSeats;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Timestamp getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Timestamp flightDate) {
		this.flightDate = flightDate;
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

	public int getEconSaleSeats() {
		return econSaleSeats;
	}

	public void setEconSaleSeats(int econSaleSeats) {
		this.econSaleSeats = econSaleSeats;
	}

	public int getBusSaleSeats() {
		return busSaleSeats;
	}

	public void setBusSaleSeats(int busSaleSeats) {
		this.busSaleSeats = busSaleSeats;
	}

	public int getEconActlSeats() {
		return econActlSeats;
	}

	public void setEconActlSeats(int econActlSeats) {
		this.econActlSeats = econActlSeats;
	}

	public int getBusActlSeats() {
		return busActlSeats;
	}

	public void setBusActlSeats(int busActlSeats) {
		this.busActlSeats = busActlSeats;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

}
