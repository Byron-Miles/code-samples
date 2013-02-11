//ZoneTime class
//extends the BetterTime class, adds attribute timeZone and several methods to use it

public class ZoneTime extends BetterTime
{
//Attributes
	//can be AEST, ACST, AWST, ACDT, AWDT, or by default AEDT 
	private String timeZone;
	
//Constructors
	//Default, random time
	public ZoneTime()
	{
		super();
		timeZone = "AEDT";
	}
	//Accepts military time as string, throws invalidTimeException
	public ZoneTime(String militaryTime) throws invalidTimeException
	{
		super(militaryTime);
		timeZone = "AEDT";
	}
	
//Methods
	//Sets the timeZone and adjusts the time accourdingly
	//returns true the zone was a valid one, else returns false
	public boolean setZone(String zone)
	{
		boolean valid = true;
		
		if(zone.equalsIgnoreCase("AEST"))
		{
			timeZone = "AEST";
			updateTime(-60);
		}
		else if(zone.equalsIgnoreCase("ACST"))
		{
			timeZone = "ACST";
			updateTime(-90);
		}
		else if(zone.equalsIgnoreCase("AWST"))
		{
			timeZone = "AWST";
			updateTime(-180);
		}
		else if(zone.equalsIgnoreCase("ACDT"))
		{
			timeZone = "ACDT";
			updateTime(-30);
		}
		else if(zone.equalsIgnoreCase("AWDT"))
		{
			timeZone = "AWDT";
			updateTime(-120);
		}
		else if(zone.equalsIgnoreCase("AEDT"))
		{
			//Do nothing
		}
		else
		{
			valid = false; //a valid timezone was not given
		}
		
		return valid;
	}//End setZone
	
	//Returns the time zone
	public String getZone()
	{
		return timeZone;
	}
	
	//toString, uses toString from Time class and appends the time zone
	public String toString()
	{
		return super.toString() + " " + getZone();
	}
//End class ZoneTime
}