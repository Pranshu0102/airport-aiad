package airline;

public class Aircraft {
	private String licensePlate;
	private AircraftModel model;
	private Boolean aircraftAvaiable;
	private Airport baseAirport;
	public Aircraft(String licensePlate, AircraftModel model,
			Boolean aircraftAvaiable, Airport baseAirport) {
		super();
		this.licensePlate = licensePlate;
		this.model = model;
		this.aircraftAvaiable = aircraftAvaiable;
		this.baseAirport = baseAirport;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public AircraftModel getModel() {
		return model;
	}
	public void setModel(AircraftModel model) {
		this.model = model;
	}
	public Boolean getAircraftAvaiable() {
		return aircraftAvaiable;
	}
	public void setAircraftAvaiable(Boolean aircraftAvaiable) {
		this.aircraftAvaiable = aircraftAvaiable;
	}
	public Airport getBaseAirport() {
		return baseAirport;
	}
	public void setBaseAirport(Airport baseAirport) {
		this.baseAirport = baseAirport;
	}
	
	
}
