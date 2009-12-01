package problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Problem implements Serializable {

	private List<AircraftProblem> airProbs;
	private List<CrewProblem> crewProbs;
	private List<PaxProblem> paxProbs;

	private String type; // type = {problem, warning}
	private String description;
	private int minutesDelay;
	private float totalCost;
	

	public Problem(String type, String description, int minutesDelay) {
		super();
		this.type = type;
		this.description = description;
		this.minutesDelay = minutesDelay;
		
		this.airProbs = new ArrayList<AircraftProblem>();
		this.crewProbs = new ArrayList<CrewProblem>();
		this.paxProbs = new ArrayList<PaxProblem>();
	}

	public Problem(List<AircraftProblem> airProbs, List<CrewProblem> crewProbs,
			List<PaxProblem> paxProbs) {
		this.airProbs = airProbs;
		this.crewProbs = crewProbs;
		this.paxProbs = paxProbs;
	}

	public void addAirProbs(AircraftProblem airprob)
	{
		airProbs.add(airprob);
	}
	
	public void addCrewProbs(CrewProblem crewprob)
	{
		crewProbs.add(crewprob);
	}
	
	public void addPaxProbs(PaxProblem paxprob)
	{
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

	public int getMinutesDelay() {
		return minutesDelay;
	}

	public void setMinutesDelay(int minutesDelay) {
		this.minutesDelay = minutesDelay;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}