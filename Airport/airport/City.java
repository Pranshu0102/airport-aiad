package airport;

public class City {
	private String cityName;
	private String Country;
	
	public City(String cityName, String country) {
		super();
		this.cityName = cityName;
		Country = country;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}
	
	
	
}
