/*GradeBook Class*/
/*
Stores a students name and test scores in arrays.
The arrays index number is used as the student number.
Has methods to:
set the students name
set the students test score
get the students name
get the students average score
get the students letter grade bassed on their average test score
*/ 

import java.text.DecimalFormat; //need for DecimalFormat object

public class GradeBook
{	
	//Declare arrays
	private String[] name;
	private double[][] tests;
	private char[] grade;	
	
	//No arg constructor, used by default for classes up to 5 students
	public GradeBook()
	{
		name = new String[5];
		tests = new double[4][5];
		grade = new char[5];
	}
	
	//Constructor that accepts the number of students in the class as an int
	public GradeBook(int classSize, int NumberOfTests)
	{
		name = new String[classSize];
		tests = new double[NumberOfTests][classSize];
		grade = new char[classSize];
	}
	
	//Sets the students name, accepts student number (>=0, <=class size) and student name
	public void setName(int studentNumber, String studentName) throws InvalidStudentNumberException
	{
		if(studentNumber < 0 || studentNumber > name.length-1)
			throw new InvalidStudentNumberException(0,(name.length-1));
		
		name[studentNumber] = studentName;
	}
	
	//Sets the test score of the specified test
	//accepts test number (>=1, <=4)
	//student number (>=0, <=class size)
	//and score (>=0, <=100)
	public void setTestScore(int testNumber, int studentNumber, double score) throws InvalidStudentNumberException, InvalidTestScoreException, InvalidTestNumberException
	{
		if(studentNumber < 0 || studentNumber > name.length-1)
			throw new InvalidStudentNumberException(0,(name.length-1));
		
		if(score < 0 || score > 100)
			throw new InvalidTestScoreException();
		
		if(testNumber < 0 || testNumber > tests.length-1)
			throw new InvalidTestNumberException(1,(tests.length-1));
			
		tests[testNumber][studentNumber] = score;
	}
		
	//Returns the students name, accepts student number (>=0, <=class size)
	public String getName(int studentNumber) throws InvalidStudentNumberException
	{
		if(studentNumber < 0 || studentNumber > name.length-1)
			throw new InvalidStudentNumberException(0,(name.length-1));
		
		return name[studentNumber];
	}
	
	//Returns average test score for given student, accepts student number (>=0, <=class size)
	//Used in getLetterGrade
	public double getAverage(int studentNumber) throws InvalidStudentNumberException
	{
		if(studentNumber < 0 || studentNumber > name.length-1)
			throw new InvalidStudentNumberException(0,(name.length-1));

		double average = 0;
		
		for(int n=0;n<tests.length;n++)
			average += tests[n][studentNumber];
		
			average = average/tests.length;
				
		return average;
	}
	
	//Returns letter grade for given student, accepts student number (>=0, <=class size)
	//Uses getAverage to work out letter grade 
	public char getLetterGrade(int studentNumber) throws InvalidStudentNumberException
	{
		if(studentNumber < 0 || studentNumber > name.length-1)
			throw new InvalidStudentNumberException(0,(name.length-1));
		
		double average = getAverage(studentNumber);
		
		if(average >= 85)
			grade[studentNumber] = 'A';
		else if(average >= 75)
			grade[studentNumber] = 'B';
		else if(average >= 65)
			grade[studentNumber] = 'C';
		else if(average >= 50)
			grade[studentNumber] = 'D';
		else
			grade[studentNumber] = 'F';
			
		return grade[studentNumber];
	}

	public String toString()
	{
		//DecimalFormat for displaying average score as 2 digit number with only 1 decimal place
		DecimalFormat output = new DecimalFormat("00.0"); 
		
		StringBuilder line;
		System.out.println();
		
		for(int i=0; i<name.length; i++)
		{
			try{
				line = new StringBuilder();
				//Build line
				line.insert(0,"Name: ");
				line.append(getName(i));
				while(line.length()<40)
					line.append(" ");
				line.append("Average: ");
				line.append(output.format(getAverage(i)));
				line.append("  Grade: ");
				line.append(getLetterGrade(i));
				//Display line
				System.out.println(line);
			}
			catch(InvalidStudentNumberException e)
			{
				//Display end of list when finished
			}
			
		}//End for loop
		return "End of List\n";	
	}
//End class GradeBook 
}