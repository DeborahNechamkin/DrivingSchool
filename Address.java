

public class Address
{

private String street;
private String city;
private USState state;
private String zipcode;

//Constructor receiving street, city, state and zipcode
public Address (String street, String city, USState state, String zipcode)
{
	
	this(street, city, state.name(), zipcode);
}

//Constructor receiving street, city, state, zipcode
public Address (String street, String city, String state, String zipcode)
{	
	this.street = street;
	this.city = city;
	
	int position = state.indexOf(" ");
	if (position >= 0)
		state = state.substring(0, position) + state.substring(position+1);
	
	this.state = USState.valueOf(state.toUpperCase());
	this.zipcode = zipcode;
}

//returns street
public String getStreet()
{
	return street;
}

//returns city
public String getCity()
{
	return city;
}

//returns state
public USState getState()
{
	return state;
}

//returns zipcode
public String getZipcode()
{
	return zipcode;
}

//returns toString
@Override
public String toString()
{
	StringBuilder tostring = new StringBuilder();
	tostring.append(street + ", ");
	tostring.append(city + ", ");
	tostring.append(state.getAbbrev() + " ");
	tostring.append(zipcode + ".");
	return tostring.toString();
}
}