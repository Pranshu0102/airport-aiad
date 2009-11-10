package airport;

public class CrewMember {
	
	private int memberNumber;
	private Rank rank;
	private String currentAirport;
	private boolean onFlight;
	
	public CrewMember(int memberNumber, Rank rank) {
		super();
		this.memberNumber = memberNumber;
		this.rank = rank;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public String getCurrentAirport() {
		return currentAirport;
	}

	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}

	public boolean isOnFlight() {
		return onFlight;
	}

	public void setOnFlight(boolean onFlight) {
		this.onFlight = onFlight;
	}

	
	
}
