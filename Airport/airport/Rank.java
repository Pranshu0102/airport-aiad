package airport;

public class Rank {
	
	private String description;
	private float costHour;
	
	public Rank(String description, float costHour) {
		super();
		this.description = description;
		this.costHour = costHour;
	}

	public float getCostHour() {
		return costHour;
	}

	public void setCostHour(float costHour) {
		this.costHour = costHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
