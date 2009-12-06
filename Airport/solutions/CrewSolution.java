package solutions;

import jade.util.leap.Serializable;

public class CrewSolution implements Serializable{
	String description;
	int cost;
	
	
	public CrewSolution()
	{
	this.description="";
	this.cost = -1;
	}
	
	public CrewSolution(String description, int cost)
	{
		this.description = description;
		this.cost = cost;
		
	}

	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "CrewSolution [cost=" + cost + ", description=" + description
				+ "]";
	}
	
}
