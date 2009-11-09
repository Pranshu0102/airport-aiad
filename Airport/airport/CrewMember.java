package airport;

public class CrewMember {
	
	private int memberNumber;
	private Rank rank;
	private boolean available;
	
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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	
	
	
}
