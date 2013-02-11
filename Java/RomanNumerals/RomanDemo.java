public class RomanDemo
{
	public static void main(String[] args)
	{
		int decimal;
		String menu, yesno, roman;
		
		System.out.print("Welcome.");
		
		do{//Display menu until user selects quit
			System.out.println("\nPlease select from the follow options:");
			System.out.println("[s]Convert strictly formated roman numberals to a decimal");
			System.out.println("[r]Convert losely formated roman numberals to a decimal");
			System.out.println("[d]Convert a decimal to roman numerials");
			System.out.println("[h]Display help");
			System.out.println("[q]Quit");
			  
			do{
				menu = Get.stringValue(":");
			}while(Check.isStrings("v",menu,"r","s","d","h","q"));
		
			//Converting a roman numberal to to decimal
			if(menu.equalsIgnoreCase("r") || menu.equalsIgnoreCase("s"))
			{
				do{//Repeat while user opts to try another roman numberal
					roman = Get.stringValue("\nPlease enter roman numberals: ");
					
					try
					{
						//Use either lose or strict formatting rules
						if(menu.equalsIgnoreCase("r"))
							decimal = Roman.toDecimal(roman,false); //lose formatting
						else
							decimal = Roman.toDecimal(roman); //strict formatting
					}
					catch(RomanException e)
					{
						System.out.println(e.getMessage());
						decimal = 0;
					}	
					
					//Print output only if numberal was valid
					if(decimal > 0)
					{
						System.out.println("The decimal value of " + roman + " is " + decimal);
						if(!roman.equalsIgnoreCase(Roman.toRoman(decimal)))
							System.out.println("However it is better writen as " + Roman.toRoman(decimal)); 			
					}
					
					do{//Ask if they want to try another roman numberal
						yesno = Get.stringValue("Try another (y/n)? ");
					}while(Check.isYesNo("v",yesno));
					
				}while(Check.isYes(yesno));
			}
			//Converting a decimal to roman numberals
			else if(menu.equalsIgnoreCase("d"))
			{
				do{//Repeat while user opts to try another decimal number
					do{
						decimal = Get.intValue("\nPlease enter a number >= 1: ",0);
					}while(Check.isGreaterThan("Error! There are no roman numerials for numbers less than 1",decimal,0));
				
					System.out.println("The roman numerials for " + decimal + " is " + Roman.toRoman(decimal));
					
					do{//Ask if user wants to try another decimal number
						yesno = Get.stringValue("Try another (y/n)? ");
					}while(Check.isYesNo("v",yesno));
					
				}while(Check.isYes(yesno));
			}
			//Display a help message
			else if(menu.equalsIgnoreCase("h"))
			{
				System.out.println("\n[s]Strict Formatting: You must follow the rules for formating roman numberals exactly.");
				System.out.println("\n[r]Lose Formatting: As long as you only use valid roman numberals your all good");
				System.out.println("Note: If you do not use strict formating in this mode the program will tell you how"); 
				System.out.println("to write the numberals correctly for the given number");
				System.out.println("\n[d]Convert Decimal: The program will convert the decimal number you enter into roman numberals");
			}
		//End the program if user selected quit from menu	
		}while(!menu.equalsIgnoreCase("q"));
	}
}