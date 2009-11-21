package airline;

public class CrewMember {
	private Long memberNumber;
	private String name;
	private Rank rank;
	private float costHour;
	private float costPerDiem;
	private int workHoursDay;
	private int workHoursWeek;
	private int workHoursMonth;
	private Boolean isOnFlight;
	private Airport baseAirport;
	private int totalAirHours;
	public CrewMember(Long memberNumber, String name, Rank rank,
			float costHour, float costPerDiem, int workHoursDay,
			int workHoursWeek, int workHoursMonth, Boolean isOnFlight,
			Airport baseAirport, int totalAirHours) {
		super();
		this.memberNumber = memberNumber;
		this.name = name;
		this.rank = rank;
		this.costHour = costHour;
		this.costPerDiem = costPerDiem;
		this.workHoursDay = workHoursDay;
		this.workHoursWeek = workHoursWeek;
		this.workHoursMonth = workHoursMonth;
		this.isOnFlight = isOnFlight;
		this.baseAirport = baseAirport;
		this.totalAirHours = totalAirHours;
	}
	public Long getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(Long memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	public float getCostHour() {
		return costHour;
	}
	public void setCostHour(float costHour) {
		this.costHour = costHour;
	}
	public float getCostPerDiem() {
		return costPerDiem;
	}
	public void setCostPerDiem(float costPerDiem) {
		this.costPerDiem = costPerDiem;
	}
	public int getWorkHoursDay() {
		return workHoursDay;
	}
	public void setWorkHoursDay(int workHoursDay) {
		this.workHoursDay = workHoursDay;
	}
	public int getWorkHoursWeek() {
		return workHoursWeek;
	}
	public void setWorkHoursWeek(int workHoursWeek) {
		this.workHoursWeek = workHoursWeek;
	}
	public int getWorkHoursMonth() {
		return workHoursMonth;
	}
	public void setWorkHoursMonth(int workHoursMonth) {
		this.workHoursMonth = workHoursMonth;
	}
	public Boolean getIsOnFlight() {
		return isOnFlight;
	}
	public void setIsOnFlight(Boolean isOnFlight) {
		this.isOnFlight = isOnFlight;
	}
	public Airport getBaseAirport() {
		return baseAirport;
	}
	public void setBaseAirport(Airport baseAirport) {
		this.baseAirport = baseAirport;
	}
	public int getTotalAirHours() {
		return totalAirHours;
	}
	public void setTotalAirHours(int totalAirHours) {
		this.totalAirHours = totalAirHours;
	}
	
	
}
