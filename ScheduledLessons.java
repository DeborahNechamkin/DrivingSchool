

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduledLessons
{
	private int LessonID;
	private Client client;
	private DrivingInstructor instructor ;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public ScheduledLessons(int LessonID,Client client,DrivingInstructor instructor,LocalDate date,LocalTime startTime,LocalTime endTime) throws InvalidLessonException
	{
		int sum = 0;
		for (ScheduledLessons l : instructor.getLessons())
		{
			if (l.instructor.equals(instructor))
			{
				sum++;
			}
		}
		if (sum >=5)
		{
			throw new InvalidLessonException();
		}
		
		if (startTime.isBefore(LocalTime.parse("8:00:00")) || startTime.isAfter(LocalTime.parse("20:00:00")))
		{
			throw new InvalidLessonException();
		}
		this.LessonID = LessonID;
		this.client = client;
		this.instructor = instructor ;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		
		client.addBalance(50);
	}
	
	public int getLessonTime()
	{
		return (int) MINUTES.between(startTime, endTime);
	}
	
	public LocalDate getDate()
	{
		return date;
	}
	
	public LocalTime getStartTime()
	{
		return startTime;
	}
	
	public LocalTime getEndTime()
	{
		return endTime;
	}
	
	public Client getClient()
	{
		return client;
	}
}
