package airport;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Flight {
	private int flightNumber;
	
	private Date flightDate;
	
	private String departureAirport;
	private String arrivalAirport;
	
	//ou usar timestamp?
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	
	private int busActlPax;
	private int econActlPax;
	
	Aircraft aircraft;
	List<CrewMember> crewMembers;

	private float totalCost;
	
	
}
