import java.io.*;
import java.util.*;

public class Concordance
{
	private static int vectorIndex(char c)
	{
		int i;
		i = (int)c;
		if(i >= 97 && i <= 122)
			i -= 97;
		else if(i >= 65 && i <= 90)
			i -= 65;
			
		return i;
	}
	
	private static LinkedQueue<String> extractWords(String input)
	{
		int n, i, j;
		boolean ok;
		LinkedQueue<String> words = new LinkedQueue<String>();
		
		i = 0; n = 0;
		ok = true;
		
		while(i < input.length())
		{
			//While n is an invalid character, move i to next character
			while(ok && i < input.length())
			{
				n = (int)input.charAt(i);	
				if(!((n >= 97 && n <= 122) || (n >= 65 && n <= 90)))
					++i;
				else
					ok = false;
			}
			
			ok = true;
			j = i+1;
			
			//While n is a valid character, move j to the next character
			while(ok && j < input.length())
			{
				n = (int)input.charAt(j);
				if((n >= 97 && n <= 122) || (n >= 65 && n <= 90))
					++j;
				else
					ok = false;
			}
			
			//If the 'word' is at least 2 characters long
			if(j > i+1)
				words.enqueue(input.substring(i,j).toLowerCase());
			
			ok = true;
			i = j+1;
		}
		
		return words;
	}
	
	public static void main (String args[]) throws FileNotFoundException
	{
		Vector<BinarySearchTree<String, LinkedList<Integer>>> concordance;
		File inputFile;
		Scanner fileReader;
		LinkedList<Integer> lineList;
		LinkedQueue<String> words;
		String fileName;
		String line;
		String word;
		Integer lineNumber;
		
		do{//Repeat program till user quits
			do{
				fileName = Get.stringValue("Enter the name of the file to scan (q to quit): ");
				inputFile = new File(fileName);
				if(!inputFile.exists() && !fileName.equals("q"))
					System.out.println("Error: File \"" + fileName + "\" does not exist");
			}while(!inputFile.exists() && !fileName.equals("q"));
			
			if(!fileName.equals("q")) //If does not quit
			{
				fileReader = new Scanner(inputFile);
				concordance = new Vector<BinarySearchTree<String, LinkedList<Integer>>>(26);
				lineNumber = 0;
				
				for(int i = 0; i < concordance.capacity(); ++i)
					concordance.add(new BinarySearchTree<String, LinkedList<Integer>>());
				
				//Read each line in the file
				while(fileReader.hasNextLine())
				{
					++lineNumber;
					line = fileReader.nextLine();
					words = extractWords(line);
					//Read each word in the file
					while(!words.empty())
					{
						word = words.dequeue();
						int n = vectorIndex(word.charAt(0));
										
						if(concordance.elementAt(n).contains(word))
							lineList = concordance.elementAt(n).find(word);
						else
							lineList = new LinkedList<Integer>();
						
						lineList.add(lineNumber);
						concordance.elementAt(n).add(word, lineList); 
					}
				}
				
				//Search for words
				do{//Reapeat search until user quits
					line = Get.stringValue("Which word would you like to search for (q to quit): ");
					
					if(!line.equals("q"))
					{
						words = extractWords(line);
						//Search for each word 
						while(!words.empty())
						{
							word = words.dequeue();
							int n = vectorIndex(word.charAt(0));	
							
							if(concordance.elementAt(n).contains(word))
							{
								lineList = concordance.elementAt(n).find(word);
								
								System.out.print("The word \"" + word + "\" appears on line");
								if(lineList.size() == 1)
									System.out.print(" " + lineList.get(0));
								else
								{
									System.out.print("s: ");
									for(int i = 0; i < lineList.size(); ++i)
									{
										System.out.print(lineList.get(i));
										if(i != lineList.size()-1)
											System.out.print(", ");
									}	
								}
								System.out.println();
							}
							else
								System.out.println("The word \"" + word + "\" does not appear in the file \"" + fileName + "\"");
						}
					}
				}while(!line.equals("q")); //End repeat search
			}//End if user does not quit
		}while(!fileName.equals("q")); //End repeat program		
	}
}