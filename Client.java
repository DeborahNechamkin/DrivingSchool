

import java.time.LocalDate;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.DAYS;

public class Client
{
	private String DrivingPermitID;
	private USState DrivingPermitState;
	private DrivingInstructor instructor;
	private ArrayList<RoadTest> roadTests;
	private double balanceOwed;

	public Client(String DrivingPermitID, USState DrivingPermitState, DrivingInstructor instructor,
			ArrayList<RoadTest> roadTests, double balanceOwed)
	{
		this.DrivingPermitID = DrivingPermitID;
		this.DrivingPermitState = DrivingPermitState;
		this.instructor = instructor;
		roadTests = new ArrayList<RoadTest>(1);
		this.balanceOwed = balanceOwed;
	}

	public void addRoadTest(RoadTest test)
	{
		
		roadTests.add(test);
	}

	public int getTotalHours()
	{
		int sum = 0;
		for (ScheduledLessons l : instructor.getLessons())
		{
			if (l.getClient().equals(this))
			{
				sum += l.getLessonTime();
			}
		}
		return sum;
	}

	public double getBalanceOwed()
	{
		return balanceOwed;
	}

	public String getTestResults()
	{
		return roadTests.get(roadTests.size()-1).getTestResults();
	}

	public void addBalance(double d)
	{
		balanceOwed+=d;		
	}
	
	public ArrayList<RoadTest> getRoadTests()
	{
		ArrayList<RoadTest> tests2 = new ArrayList<RoadTest>();
		for (RoadTest t : tests2)
		{
			tests2.add(t);
		}
		
		return tests2;
	}


}
