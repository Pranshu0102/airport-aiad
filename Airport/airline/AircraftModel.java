package airline;

public class AircraftModel {
	private String model;
	private String description;
	private float costHour;
	private int nrCabinCrewMembers;
	public AircraftModel(String model, String description, float costHour,
			int nrCabinCrewMembers) {
		super();
		this.model = model;
		this.description = description;
		this.costHour = costHour;
		this.nrCabinCrewMembers = nrCabinCrewMembers;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getCostHour() {
		return costHour;
	}
	public void setCostHour(float costHour) {
		this.costHour = costHour;
	}
	public int getNrCabinCrewMembers() {
		return nrCabinCrewMembers;
	}
	public void setNrCabinCrewMembers(int nrCabinCrewMembers) {
		this.nrCabinCrewMembers = nrCabinCrewMembers;
	}
	
	
}
