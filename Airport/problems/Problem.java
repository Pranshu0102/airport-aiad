package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import airline.Flight;

public class Problem implements Serializable {

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	private List<AircraftProblem> airProbs;
	private List<CrewProblem> crewProbs;
	private List<PaxProblem> paxProbs;
	private Flight flight;
	private float totalCost;

	public Problem(List<AircraftProblem> airProbs, List<CrewProblem> crewProbs,
			List<PaxProblem> paxProbs) {
		this.airProbs = airProbs;
		this.crewProbs = crewProbs;
		this.paxProbs = paxProbs;
	}

	public Problem(Flight flight) {
		this.airProbs = new ArrayList<AircraftProblem>();
		this.crewProbs = new ArrayList<CrewProblem>();
		this.paxProbs = new ArrayList<PaxProblem>();
		this.flight = flight;
	}

	public void addAirProbs(AircraftProblem airprob) {
		airProbs.add(airprob);
	}

	public void addCrewProbs(CrewProblem crewprob) {
		crewProbs.add(crewprob);
	}

	public void addPaxProbs(PaxProblem paxprob) {
		paxProbs.add(paxprob);
	}

	public List<AircraftProblem> getAirProbs() {
		return airProbs;
	}

	public void setAirProbs(List<AircraftProblem> airProbs) {
		this.airProbs = airProbs;
	}

	public List<CrewProblem> getCrewProbs() {
		return crewProbs;
	}

	public void setCrewProbs(List<CrewProblem> crewProbs) {
		this.crewProbs = crewProbs;
	}

	public List<PaxProblem> getPaxProbs() {
		return paxProbs;
	}

	public void setPaxProbs(List<PaxProblem> paxProbs) {
		this.paxProbs = paxProbs;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public void print() {
		System.out.println("Voo: " + flight.getFlightNumber());
		for (int i = 0; i != airProbs.size(); i++)
			airProbs.get(i).print();
		for (int i = 0; i != crewProbs.size(); i++)
			crewProbs.get(i).print();
		for (int i = 0; i != paxProbs.size(); i++)
			paxProbs.get(i).print();
		System.out.println("----------------------------------------");
	}

}