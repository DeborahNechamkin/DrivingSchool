

import java.time.LocalDate;
import java.util.ArrayList;
public class Employee extends Person
{
	private int EmployeeID;
	private LocalDate HireDate;
	private EmployeeType[] employeeType;
	private Employee reportsTo;
	
	//Constructor receiving FirstName, LastName, address, BirthDate, HireDate and HourlyPayRate
	public Employee(String FirstName, String LastName, Address address, String BirthDate, String cellPhone, String emailAddress, LocalDate HireDate)
	{
		super (FirstName, LastName, address, BirthDate);
		this.setCellPhone(cellPhone);
		this.setEmailAddress(emailAddress);
	}
	
	//returns EmployeeId
	public int getEmployeeId()
	{
		return EmployeeID;
	}
	
	//returns a toString
	@Override
	public String toString()
	{
		StringBuffer string = new StringBuffer();
		string.append(super.toString());
		string.append(" Employee ID: " + EmployeeID + "  ");
		string.append("Date Hired: " + HireDate + ". ");
		
		return string.toString();
	}
	
	//receives an object and compares it based on EmployeeID
	public int compareTo(Employee e)
	{
		if (this.EmployeeID == e.EmployeeID)
			return 0;
		else if (this.EmployeeID < e.EmployeeID)
			return -1;
		else 
			return 1;
	}

	//receives an object and returns true if its equal and false if its not
	@Override
	public boolean equals(Object o)
	{
		Employee e =  (Employee) o;
		if (this.EmployeeID == e.EmployeeID)
			return true;
		return false;
	}
	
}