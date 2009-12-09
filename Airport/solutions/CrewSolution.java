package solutions;

import jade.util.leap.Serializable;
import problems.CrewProblem;
import problems.Problem;
import airline.CrewMember;
import airline.EscCrew;

public class CrewSolution implements Serializable {
	CrewProblem crewProblem;
	String description;
	int cost;
	CrewMember newCrewMember;
	EscCrew oldEscCrew;

	public CrewSolution(CrewProblem crewProblem, String description, int cost,
			CrewMember newCrewMember, EscCrew oldEscCrew) {
		super();
		this.crewProblem = crewProblem;
		this.description = description;
		this.cost = cost;
		this.newCrewMember = newCrewMember;
		this.oldEscCrew = oldEscCrew;
	}
	public CrewSolution(CrewProblem crewProblem, String description, int cost) {
		super();
		this.crewProblem = crewProblem;
		this.description = description;
		this.cost = cost;
	
	}
	
	

	public CrewProblem getCrewProblem() {
		return crewProblem;
	}
	public void setProb(Problem prob) {
		this.crewProblem = crewProblem;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public CrewMember getNewCrewMember() {
		return newCrewMember;
	}
	public void setNewCrewMember(CrewMember newCrewMember) {
		this.newCrewMember = newCrewMember;
	}
	public EscCrew getOldEscCrew() {
		return oldEscCrew;
	}
	public void setOldEscCrew(EscCrew oldEscCrew) {
		this.oldEscCrew = oldEscCrew;
	}
	@Override
	public String toString() {
		if(newCrewMember==null)
			return "CrewSolution [cost=" + cost + ", description=" + description
				+ "]";
		else
			return "CrewSolution [cost=" + cost +", description=" + description
			+", oldCrewMember: "+crewProblem.getCrewMember().getMemberNumber()+", newCrewMember: "+newCrewMember.getMemberNumber()+"]";
	}

}
