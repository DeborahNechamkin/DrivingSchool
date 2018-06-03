

public enum FailType {
	POORSIGNAL(2), LANECONTROL(2),STEERINGCONTROL(2),INEFFECTIVEOBSERVATION(2),
	INADEQUATEOBSERVATION(2),HITSIDEWALK(3), TAILGATE(4);
	private int points;
	
	private FailType(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	 

}
