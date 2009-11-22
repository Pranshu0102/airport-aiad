package airline;

public class Airport {
	private String identification;
	private String name;
	private String city;
	private String country;

	public Airport(String identification, String name, String city,
			String country) {
		super();
		this.identification = identification;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
