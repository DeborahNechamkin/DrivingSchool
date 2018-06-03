

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

public class RoadTest
{
	private int roadTestID;
	private Client client;
	private LocalDate testDate;
	private String testResults;
	private FailType failReasons;
	
	public RoadTest(int roadTestID, Client client, LocalDate testDate, String testResults, FailType failReasons) throws InvalidRoadTestException
	{
		try
		{
			if (client.getBalanceOwed()>100)
			{
				throw new InvalidRoadTestException("Balance too high");
			}
			
			else if (client.getRoadTests().size()!=1)
				{
					LocalDate lastTest = client.getRoadTests().get(client.getRoadTests().size()-1).getDate();
					if (DAYS.between(lastTest, testDate)<30)
					{
						throw new InvalidRoadTestException("Invalid Date");
					}
				}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.roadTestID = roadTestID;
		this.client = client;
		this.testDate = testDate;
		this.testResults = testResults;
		this.failReasons = failReasons;
	}

	public String getTestResults()
	{
		return testResults;
	}

	public LocalDate getDate()
	{
		return testDate;
	}
}
