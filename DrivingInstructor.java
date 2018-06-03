

import java.time.LocalDate;
import java.util.ArrayList;

public class DrivingInstructor
{
	private String DriversLicenseID;
	private USState LicenseState;
	private LocalDate ExpirationDate;
	private String InstructorLicenseID;
	private LocalDate DateIssued;
	private ArrayList<ScheduledLessons> lessons;
	private Car car;
	
	public DrivingInstructor(String DriversLicenseID, USState LicenseState, LocalDate ExpirationDate, String InstructorLicenseID, LocalDate DateIssued, ArrayList<ScheduledLessons> lessons, Car car)
	{
		this.DriversLicenseID = DriversLicenseID;
		this.LicenseState = LicenseState;
		this.ExpirationDate = ExpirationDate;
		this.InstructorLicenseID = InstructorLicenseID;
		this.DateIssued = DateIssued;
		this.lessons = lessons;
		this.car = car;
	}
	
	public int getTotalHoursTought()
	{
		int sum = 0;
		for (ScheduledLessons l : lessons)
		{
			sum+= l.getLessonTime();
		}
		return sum;
	}
	
	public int getTotalHoursTaught(LocalDate startDate, LocalDate endDate)
	{
		int sum = 0;
		for (ScheduledLessons l : lessons)
		{
			if (!startDate.isBefore(l.getDate())  && !endDate.isAfter(l.getDate()))
			sum+= l.getLessonTime();
		}
		return sum;
	}
	

	public ArrayList<ScheduledLessons> getLessons()
	{
		ArrayList<ScheduledLessons> lessons2 = new ArrayList<ScheduledLessons>();
		for (ScheduledLessons l : lessons)
		{
			lessons2.add(l);
		}
		
		return lessons2;
	}

}
