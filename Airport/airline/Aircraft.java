package airline;

public class Aircraft {
	private String licensePlate;
	private AircraftModel model;


	public Aircraft(String licensePlate, AircraftModel model) {
		super();
		this.licensePlate = licensePlate;
		this.model = model;
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

}
