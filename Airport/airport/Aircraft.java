package airport;

import java.util.List;

public class Aircraft {
	private String licensePlate;
	private float costHour;
	private int passengersCapacity;
	private boolean available;
	
	public Aircraft(String licensePlate, float costHour, int passengersCapacity) {
		super();
		this.licensePlate = licensePlate;
		this.costHour = costHour;
		this.passengersCapacity = passengersCapacity;
	}
	
	public float getCostHour() {
		return costHour;
	}
	public void setCostHour(float costHour) {
		this.costHour = costHour;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public int getPassengersCapacity() {
		return passengersCapacity;
	}
	public void setPassengersCapacity(int passengersCapacity) {
		this.passengersCapacity = passengersCapacity;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	

}
