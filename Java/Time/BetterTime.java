//BetterTime class
//Extends the Time class, adding the a method to update the time by X minutes

public class BetterTime extends Time
{
//Constructors
	//Default, random time
	public BetterTime()
	{
		super();
	}
	//Accepts military time as string, throws invalidTimeException
	public BetterTime(String militaryTime) throws invalidTimeException
	{
		super(militaryTime);
	}
	
//Methods
	//Updates the time by adjustment minutes
	public void updateTime(int adjustment) 
	{
		int hours = getHours();
		int minutes = getMinutes();
		
		hours += adjustment/60;
		minutes += adjustment%60;
		
		//move time forward one hour if minutes >= 60
		if(minutes >= 60)
		{
			hours++;
			minutes -= 60;
		}
		//move time back one hour if total < 0
		else if(minutes < 0)
		{
			hours--;
			minutes += 60;
		}
		//remove blocks of 24 hours as nessecary
		while(hours >= 24)
			hours -= 24;
		//add blocks of 24 hours as nessecary
		while(hours < 0)
			hours += 24;
		 
		//Set the new time
		setHours(hours);
		setMinutes(minutes);
	}//End updateTime
//End class BetterTime
}