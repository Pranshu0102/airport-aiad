package solutions;

import jade.util.leap.Serializable;
import airline.EscCrew;

public class AircraftSolution implements Serializable {
	String description;
	int affectedFlights;
	int cost;
	EscCrew escCrewReplace;

	public AircraftSolution() {
		description = "";
		affectedFlights = -1;
		cost = -1;
	}

	public AircraftSolution(String descricao, int voo_afectado, int custo) {
		this.description = descricao;
		this.affectedFlights = voo_afectado;
		this.cost = custo;
	}
	
	public AircraftSolution(String descricao, int voo_afectado, int custo, EscCrew escCrewReplace) {
		this.description = descricao;
		this.affectedFlights = voo_afectado;
		this.cost = custo;
		this.escCrewReplace = escCrewReplace;
	}
	
	public String getDescricao() {
		return description;
	}

	public int getVoo_afetado() {
		return affectedFlights;
	}

	public int getCusto() {
		return cost;
	}

	public void setDescricao(String descricao) {
		this.description = descricao;
	}

	public void setVoo_afetado(int vooAfetado) {
		affectedFlights = vooAfetado;
	}

	public void setCusto(int custo) {
		this.cost = custo;
	}

	@Override
	public String toString() {
		if(escCrewReplace == null)
			return "AircraftSolution [custo=" + cost + ", descricao=" + description
				+ ", voo_afetado=" + affectedFlights + "]";
		else 
			return "AircraftSolution [custo=" + cost + ", descricao=" + description
			+ ", voo_afetado=" + affectedFlights + ", Avião de substituição: "+escCrewReplace.getFlights().get(0).getAircraft().getLicensePlate()+"]";
	}


}
