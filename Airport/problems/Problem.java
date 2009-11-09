package problems;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

public class Problem implements Serializable {

	private List<AircraftProblem> airProbs;
	private List<CrewProblem> crewProbs;
	private List<PaxProblem> paxProbs;
	
	private int minutesDelay;
	private float totalCost;
	private String type; // type = {problem, warning}

	public Problem(List<AircraftProblem> airProbs, List<CrewProblem> crewProbs, List<PaxProblem> paxProbs) {
		this.airProbs = airProbs;
		this.crewProbs = crewProbs;
		this.paxProbs = paxProbs;
	}

	

}