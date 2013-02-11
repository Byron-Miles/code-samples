import java.util.NoSuchElementException;

public class TestBST
{
	public static void main(String args[])
	{
		BinarySearchTree<Integer, String> bstree = new BinarySearchTree<Integer, String>();
		
		bstree.add(1, "One");
		bstree.add(2, "Two");
		bstree.add(3, "Four");
		
		try{
		for(int i = 1; i < 4; ++i)
		{
			if(bstree.contains(i))
				System.out.println("The tree contains " + bstree.find(i));
		}
		
		System.out.println("Fixing value...");
			
		String fix = bstree.find(3);
		fix = "Three";
		bstree.add(3, fix);
		
		System.out.println(fix.substring(0,1));
		
		for(int i = 1; i < 4; ++i)
		{
			if(bstree.contains(i))
				System.out.println("The tree contains " + bstree.find(i));
		}
		
		System.out.println("Uppercase letters:");
		for(int i = 65; i < 91; ++i)
			System.out.print((char)i);
		System.out.println("\nLowercase letters:");	
		for(int i = 97; i < 123; ++i)
			System.out.print((char)i);
		System.out.println();
		}
		catch(NoSuchElementException nsee)
		{
			System.out.println("Error");
		}
	}
}
