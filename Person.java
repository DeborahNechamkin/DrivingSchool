

import java.time.LocalDate;

public class Person
{
 private int PersonID;
 private String FirstName;
 private String LastName;
 private Address address;
 private LocalDate BirthDate;
 private String cellPhone;
 private String emailAddress;
 
 //Constructor receiving FirstName, LastName, address and String BirthDate
 public Person(String FirstName, String LastName, Address address, String BirthDate)
 {
	this(FirstName, LastName, address, LocalDate.parse(BirthDate)); 
 }

 //Constructor receiving FirstName, LastName, address and LocalDate BirthDate
 public Person(String FirstName, String LastName, Address address, LocalDate BirthDate)
 {
	this.FirstName = FirstName;
	this.LastName = LastName;
	this.address = address;
	this.BirthDate = BirthDate;		
 }
 
 //returns FirstName
 public String getFirstName()
 {
	 return FirstName;
 }
 
 //returns LastName
 public String getLastName()
 {
	 return LastName;
 }
 
 //returns address
 public Address getAddress()
 {
	 return address;
 }
 
 //returns BirthDate
 public LocalDate getBirthDate()
 {
	 return BirthDate;
 }
 
 //sets LastName
 public void setLastName(String LastName)
 {
	 this.LastName = LastName;
 }
 
 //sets address
 public void setAddress(Address address)
 {
	 this.address = address;
 }
 
 public void setCellPhone(String cell)
 {
	 cellPhone = cell;
 }
 
 public void setEmailAddress(String email)
 {
	 emailAddress = email;
 }
 
 //returns toString
 @Override
 public String toString()
 {
	 StringBuilder tostring = new StringBuilder();
	 tostring.append(FirstName + " " + LastName);
	 tostring.append(": Birth Date: " + BirthDate);
	 tostring.append(". Address: " + address);
	 return tostring.toString();
 }
 
 //receives an object and compares it based on LastName, FirstName and BirthDate
 public int compareTo(Person p)
 {
	int comp1 =  LastName.compareToIgnoreCase(p.LastName);
	int comp2 =  FirstName.compareToIgnoreCase(p.FirstName);
	int comp3 =  BirthDate.compareTo(p.BirthDate);
	
	if (comp1 == 0)
	{
		if (comp2 < 0) // if a is before b
			return -1;
		else if (comp2 > 0) //if a is after b
			return 1;
		else 
		{
			if (comp3 < 0)
				return -1;
			else if (comp3 > 0)
				return 1;
			else return 0;
		}
	}
	return comp1;
 }
 
 //receives an object and returns true if its equal and false if its not
@Override
  public boolean equals(Object o)
 {
	 if (this == o)
			 return true;
	 if (o == null)
		 	return false;
	 if (o instanceof Person)
	 {
	 Person other = (Person) o;
	 if (!FirstName.equals(other.FirstName))
		 return false;
	 if (!LastName.equals(other.LastName))
		 return false;
	 if (!BirthDate.equals(other.BirthDate))		
		 return false;
	 return true; 
	 }
	 else 
		 return false;
 }
}