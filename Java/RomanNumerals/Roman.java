//Converts Roman NUMBERALs to Decimal and Visa Versa

public class Roman
{
	private static final char NUMBERAL[] = {'M','D','C','L','X','V','I'};
	private static final int VALUE[] = {1000,500,100,50,10,5,1}; 
	private static int index;
	
	//Convert a decimal number to roman NUMBERALs
	public static String toRoman(int decimal)
	{
		String roman = "";
		index = 0;
		
		while(index <= 4)
		{
			//1000, 100, 10
			while(decimal >= VALUE[index])
			{
				roman += NUMBERAL[index];
				decimal -= VALUE[index];
			}
			
			//900, 90, 9
			if(decimal >= VALUE[index] - VALUE[index+2])
			{
				roman += NUMBERAL[index+2];
				roman += NUMBERAL[index];
				decimal -= VALUE[index] - VALUE[index+2];
			}
			
			//500,50,5
			if(decimal >= VALUE[index+1])
			{
				roman += NUMBERAL[index+1];
				decimal -= VALUE[index+1];
			}
			
			//400,40,4
			if(decimal >= VALUE[index+1] - VALUE[index+2])
			{
				roman += NUMBERAL[index+2];
				roman += NUMBERAL[index+1];
				decimal -= VALUE[index+1] - VALUE[index+2];
			}
			//Move down to next factor of ten
			index += 2;
		}
		
		//Add any I's need on the end
		while(decimal >= VALUE[index])
		{
			roman += NUMBERAL[index];
			decimal -= VALUE[index];
		}

		return roman;	
	}//End toRoman
	
	//Shortcut method to use toRoman with needing to sepcify format rules, strict formating assumed
	public static int toDecimal(String roman) throws RomanException
	{
		return toDecimal(roman, true);
	}
	
	//Covert roman numberals to decimal
	public static int toDecimal(String roman, boolean strictly) throws RomanException
	{
		int[] decimal = new int[roman.length()];
		int maxAllowedValue, decimalTotal, count, i;
		boolean valid;
		String temp;
		
		//Convert string to uppercase to make comparisons eaiser
		for(index = 0, temp = ""; index < roman.length(); ++index)
			temp += Character.toUpperCase(roman.charAt(index));
		
		roman = temp; temp = null;
		
		//Iterate through the roman numberial string and fill decimal array with 
		//corisponding values plus check for invalid values
		for(index = 0; index < decimal.length; ++index)
		{
			valid = false;
			
			for(i = 0; i < NUMBERAL.length; ++i)
			{
				if(roman.charAt(index) == NUMBERAL[i])
				{
					decimal[index] = VALUE[i];
					valid = true;
				}
			}
			
			if(!valid)
				throw new RomanException("Error! Unknown numberal: " + roman.charAt(index));
		}
		
		if(strictly)
		{
			//Check for more than 3 instances of 'C', 'X' or 'I' in a row
			for(count = 0, i = 2; i < 7; i += 2)
			{
				index = 0;
				while(index < decimal.length && decimal[index] != VALUE[i])
					++index;
					
				while(index+1 < decimal.length && decimal[index] == decimal[index+1])
				{
					++index;
					++count;
				}
				
				if(count > 2)
					throw new RomanException("Formatting Error! Too many instances of " + NUMBERAL[i] + " in a row"); 
			}
			
			//Check for more than 1 instance of 'D', 'L' or 'V'
			for(count = 0, i = 1; i < 6; i += 2)
			{
				index = 0;
				while(index < decimal.length && decimal[index] != VALUE[i])
					++index;
					
				while(index+1 < decimal.length && decimal[index] == decimal[index+1])
				{
					++index;
					++count;
				}
				
				if(count > 0)
					throw new RomanException("Formatting Error! Too many instances of " + NUMBERAL[i]); 
			}
		}
		
		//Add up the values
		index = 0;
		maxAllowedValue = 1000;
		decimalTotal = 0;
		
		while(index < decimal.length)
		{
			if(strictly)
			{
				//Check that roman numberals are in the right order
				if(decimal[index] > maxAllowedValue)
					throw new RomanException("Formatting Error! " + roman.charAt(index) + " not allowed this late in order");
				if(decimal.length - index != 1 && decimal[index+1] > maxAllowedValue)
					throw new RomanException("Formatting Error! " + roman.charAt(index+1) + " not allowed this late in order");			
			}
			
			//Addition only 
			if(decimal.length - index == 1 || decimal[index] >= decimal[index+1])
			{
				decimalTotal += decimal[index];
				maxAllowedValue = decimal[index];
				++index;
			}
			//Subtraction rule
			else //(decimal[index] < decimal[index+1])
			{
				if(strictly)
				{
					//Check for values of 5
					if(decimal[index] == 5 || decimal[index] == 50 || decimal[index] == 500)
						throw new RomanException("Formatting Error! " + roman.charAt(index) + " cannot be used for subtraction rule");	
					//Check for subtraction law applied correctly
					if(decimal[index] != decimal[index+1]/10 && decimal[index] != decimal[index+1]/5)
						throw new RomanException("Formatting Error! " + roman.charAt(index) + " cannot preceed " + roman.charAt(index+1));
				}
				
				decimalTotal += decimal[index+1] - decimal[index];
				maxAllowedValue = decimal[index] - 1;
				index += 2;
			}
		}
				
		return decimalTotal;
	}
}