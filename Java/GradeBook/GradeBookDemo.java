import java.util.*; //needed for scanner
import java.io.*; //needed for File and IO Exception 

public class GradeBookDemo
{
	public static void main(String[] args) throws FileNotFoundException
	{
		//Objects
		Scanner keyboard = new Scanner(System.in); //Scanner for reading input
		File inputFile; //File for opening student info file
		Scanner classList; //Scanner for reading student info file
		GradeBook testScores; //GradeBook for holding students names and test scores
				
		//Variables
		String selection; //String for temp. holding user selected options
		String file; //String for holding name of student info file
		int i; //Int used for array index a.k.a student number
		
		//Display welcome
		System.out.println("Welcome to the GradeBook demo");
		
		//Select default or custom mode
		do{
			System.out.print("Do you want to use default settings [y,n]? ");
			
			selection = keyboard.nextLine();
		}while(Check.isYesNo("v",selection));

		
		//Default settings
		if(Check.isYes(selection))
		{
			file = "StudentInfo.txt";
			testScores = new GradeBook();
		}
		//Custom settings
		else
		{
			int classSize;			
			
			do{
				System.out.print("\nHow many students are in the class[>0]? ");	
				try{
					classSize = keyboard.nextInt();
				}
				catch(InputMismatchException e) 
				{
					classSize = -1; //Invalidate class size
				}
				//Clear buffer
				keyboard.nextLine();
			//Validate classSize
			}while(Check.isGreaterThan("v",classSize,0));
			
			//Get name of custom input file
			System.out.print("\nEnter the name of the student info file: ");
			selection = keyboard.nextLine();
			
			//Set custom settings
			file = selection;
			testScores = new GradeBook(classSize,4);
		}//End custom settings
		
		
		//Initlise input file
		inputFile = new File(file);
		//Check if input file exists
		while(!(inputFile.exists()))
		{
			//Get new / correct file name
			System.out.println("\nError: student info file \"" + file + "\" does not exist");
			System.out.print("Re-enter file name or q to quit: ");
			selection = keyboard.nextLine();
				
			//quit program if user enters "q"
			if(selection.equals("q"))
				System.exit(1);
					
			//Reinitlise input file
			file = selection;
			inputFile = new File(file);
		}//End check input file exists
		
			
		//Initlise Scanner classList to read the input file
		classList = new Scanner(inputFile);	
		//Read the input file
		try{
			for(i=0;classList.hasNext();i++)
			{
				testScores.setName(i,classList.nextLine());
				for(int n=0; n<4; n++)
					testScores.setTestScore(n,i,classList.nextDouble());
				if(classList.hasNext())
					classList.nextLine(); //Clear end of line character after 4th test score
			}
		}
		catch(InvalidStudentNumberException e)
		{
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println("Please check the number of entries in the student info file \"" + file + "\"");
		}
		catch(InvalidTestScoreException e)
		{
			System.out.println();			
			System.out.println(e.getMessage());
			System.out.println("Please check the student info file \"" + file + "\" for errors");
			System.exit(1);			
		}
		catch(InvalidTestNumberException e)
		{
			System.out.println();
			System.out.println("Error in code: Test does not exist");
			System.exit(1);
		}
		catch(InputMismatchException e)	
		{
			System.out.println();
			System.out.println("Error in student info file \"" + file +"\"");
			System.out.println("Please ensure format is as follows:");
			System.out.println("Student Name");
			System.out.println("Test1 Score");
			System.out.println("Test2 Score");
			System.out.println("Test3 Score");
			System.out.println("Test4 Score");
			System.exit(1);
		}
		catch(NoSuchElementException e)
		{
			System.out.println();
			System.out.println("Error: Data missing from student info file \"" + file +"\"");
			System.out.println("Please ensure format is as follows:");
			System.out.println("Student Name");
			System.out.println("Test1 Score");
			System.out.println("Test2 Score");
			System.out.println("Test3 Score");
			System.out.println("Test4 Score");
			System.exit(1);
		}
		//Close the student info file
		classList.close();		
		
		
		//Display the results
		System.out.println(testScores);
			
	}//End main method
//End class GradeBookDemo
}