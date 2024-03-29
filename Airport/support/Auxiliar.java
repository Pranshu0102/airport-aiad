package support;

import java.util.ArrayList;

import problems.Problem;
import jade.util.leap.Serializable;
import airline.*;

public class Auxiliar implements Serializable{
	@Override
	public String toString() {
		return "Aux [escc=" + escc + ", problem=" + problem + "]";
	}

	Problem problem;
	ArrayList<EscCrew> escc;
	public Auxiliar()
	{
	this.problem= null;
	this.escc = null;
	}
	
	public Auxiliar(Problem problem, ArrayList<EscCrew> escc)
	{
		this.problem = problem;
		this.escc = escc;
	}

	public Problem getProblem() {
		return problem;
	}

	public ArrayList<EscCrew> getEscc() {
		return escc;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setEscc(ArrayList<EscCrew> escc) {
		this.escc = escc;
	}
}
