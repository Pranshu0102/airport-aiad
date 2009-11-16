package airport;

import java.util.List;

public class Aircraft {
	
	private String licensePlate;
	private String name;
	private String model;
	
	private int busSaleSeats;
	private int econSaleSeats;
	
	//Se o avião se encontra neste momento em voo
	private boolean onFlight;
	
	//Aeroporto onde se encontra ou para onde se dirige
	private String airport;
		
	private float costHour;

	
	
	
	

	public void list() {
		System.out.println("- License Plate: "+licensePlate);
		System.out.println("- Model: "+model);
		System.out.println("- Cost Hour: "+costHour);

		
	}
	
	
	

}
