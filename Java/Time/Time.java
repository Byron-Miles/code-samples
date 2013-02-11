//Represents time in hours and minutes using 
//the customary conventions

import java.util.Random; //needed for random time

public class Time
{
//Atributes
	//hours in 24 hour format
	private int hours;
	//minutes	
	private int minutes;
	//AM, PM, NOON, MIDNIGHT
	private String extension;	
	
//Constructors
	//Default, sets a random time in 24hr format
	public Time()
	{
		Random randomTime = new Random();
		setHours(randomTime.nextInt(24));
		setMinutes(randomTime.nextInt(60));
	}
	
	//Takes a string, validates it (##:##), throws invalidTimeException 
	public Time(String militaryTime) throws invalidTimeException 
	{
		//Validate the format of militaryTime String
		validateFormat(militaryTime);
		
		//Extract and set hours and minutes
		setHours(extractHours(militaryTime));
		setMinutes(extractMinutes(militaryTime));
	}

//Methods
	//Validates the format of String militaryTime (##:##), 
	// throws invalidTimeException if format is invalid
	public void validateFormat(String militaryTime) throws invalidTimeException
	{	
		//Check to make sure something was entered
		if (militaryTime == null)
			throw new invalidTimeException();
		//Check to make sure there are 5 characters
		if (militaryTime.length() != 5)
			throw new invalidTimeException(militaryTime);
		//Check to make sure the colon is in the correct spot
		if (militaryTime.charAt(2) != ':')
			throw new invalidTimeException(militaryTime);
		//Check to make sure all other characters are digits
		if (!Character.isDigit(militaryTime.charAt(0)))
			throw new invalidTimeException(militaryTime);
		if (!Character.isDigit(militaryTime.charAt(1)))
			throw new invalidTimeException(militaryTime);
		if (!Character.isDigit(militaryTime.charAt(3)))
			throw new invalidTimeException(militaryTime);
		if (!Character.isDigit(militaryTime.charAt(4)))
			throw new invalidTimeException(militaryTime);
	}//End validateMilitaryTime
	
	//Extracts, validates and returns the hours from String militaryTime
	//throws invalidTimeException if hours > 23
	public int extractHours(String militaryTime) throws invalidTimeException
	{
		int hrs = Integer.parseInt(militaryTime.substring(0,2));
		
		if(hrs > 23)
			throw new invalidTimeException(militaryTime);
			
		return hrs;
	}
	
	//Extracts, validates and returns the minutes from String militaryTime
	//throws invalidTimeException if minutes > 59
	public int extractMinutes(String militaryTime) throws invalidTimeException
	{
		int mins = Integer.parseInt(militaryTime.substring(3,5));
		
		if(mins > 59)
			throw new invalidTimeException(militaryTime);
			
		return mins;
	}
	
	//Sets the hours
	protected void setHours(int hrs)
	{
		hours = hrs;
	}
	
	//Sets the minutes
	protected void setMinutes(int mins)
	{
		minutes = mins;
	}
	
	//Sets the extension
	protected void setExtension(String ext)
	{
		extension = ext;
	}
	
	//Returns hours
	public int getHours()
	{
		return hours;
	}
	
	//Returns minutes
	public int getMinutes()
	{
		return minutes;
	}
	
	//Returns the extension
	public String getExtension()
	{
		return extension;
	}
	 
	//Works out the extension, based on 24hr time
	public void extension()
	{
		if(hours < 12) //Morning
			setExtension("AM");
		else //Afternoon 
			setExtension("PM");
		if(hours == 12 && minutes == 0) //12:00 Noon
			setExtension("NOON");
		else if(hours == 0 && minutes == 0) //12:00 Midnight
			setExtension("MIDNIGHT");
	}
	
	//Returns coverted hours (24hr to 12hr)
	public int convertedHours()
	{
		int hrs = getHours();
		
		//afternoon
		if(hrs > 12)
			hrs -= 12;
		//midnight
		if(hrs == 0)
			hrs = 12;
		
		return hrs;
	}//End convert
		
	public String toString()
	{
		extension(); //Set AM, PM, NOON, MIDNIGHT
		String zero = "";
		if (getMinutes() < 10)
			zero = "0";
		
		return convertedHours() + ":" + zero + getMinutes() + " " + getExtension();
	}//End toString
//End class Time
}