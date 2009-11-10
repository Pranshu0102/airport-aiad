package airport;

import java.util.List;

public class Aircraft {
	
	private String licensePlate;
	private String model;
	
	//Se o avião se encontra neste momento em voo
	private boolean onFlight;
	
	//Aeroporto onde se encontra ou para onde se dirige
	private String airport;
	
	private float costHour;

	public Aircraft(String licensePlate, String model, float costHour) {
		super();
		this.licensePlate = licensePlate;
		this.model = model;
		this.costHour = costHour;
	}

	public void list() {
		System.out.println("--------------------");
		System.out.println("License Plate: "+licensePlate);
		System.out.println("Model: "+model);
		System.out.println("Cost Hour: "+costHour);
		System.out.println("--------------------");
		
	}
	
	
	

}
