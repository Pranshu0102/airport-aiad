package airport;

public class Airport {
	private City city;
	private String name;
	
	public Airport(City city, String name) {
		super();
		this.city = city;
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
