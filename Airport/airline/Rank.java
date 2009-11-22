package airline;

import java.util.List;

public class Rank {
	private String rank;
	private String description;
	private List<AircraftModel> aircraftModels;

	public Rank(String rank, String description,
			List<AircraftModel> aircraftModels) {
		super();
		this.rank = rank;
		this.description = description;
		this.aircraftModels = aircraftModels;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AircraftModel> getAircraftModels() {
		return aircraftModels;
	}

	public void setAircraftModels(List<AircraftModel> aircraftModels) {
		this.aircraftModels = aircraftModels;
	}

}
