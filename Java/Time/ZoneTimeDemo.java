public class ZoneTimeDemo
{
	public static void main (String [] args) throws invalidTimeException
	{	
		//Variables
		String selection; //Temp store for user input
		
		//Objects
		ZoneTime now = new ZoneTime();
		
		
		//Welcome message
		System.out.println("Welcome to the Australian time zone conversion demo");
		
		//Repeat untill quit
		do{
			//Menu
			do{
				System.out.println();
				System.out.println("Please enter one of the following:");
				System.out.println(" [##:##]  = A military time");
				System.out.println(" [random] = A randomly generated time");
				System.out.println(" [q]      = Exit the program");
				selection = Get.stringValue(" : ");
			
				if(selection.equalsIgnoreCase("random"))
					now = new ZoneTime();
				else if(selection.equalsIgnoreCase("q"))
					System.out.println("Have a nice day");
				else 
				{
					try
					{
						now = new ZoneTime(selection);
					}
					catch(invalidTimeException e)
					{
						System.out.println(e.getMessage());
						selection = "invalid";
					}
				}
			}while(selection.equals("invalid"));
			//End of menu
			
			if(!selection.equalsIgnoreCase("q")) //If not quitting
			{
				//Print current time in AEDT
				System.out.print("\nThe time is: ");
				System.out.println(now);
				
				
				//Enter new timezone
				do{
					System.out.println();
					System.out.println("Please enter a new time zone:");
					System.out.println("AEST, AEDT, ACST, ACDT, AWST, AWDT");
					selection = Get.stringValue(": ");
					
					if(!now.setZone(selection))
					{
						System.out.println("Error: Invalid time zone");
						selection = "invalid";
					}
				}while(selection.equals("invalid"));
				
				//Print new time in selected time zone
				System.out.print("\nThe time is: ");
				System.out.println(now);
			}//End if
		//End repeat untill quit
		}while(!selection.equalsIgnoreCase("q"));
	}//End main method
//End class TimeDemo 
}