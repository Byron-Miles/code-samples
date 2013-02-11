//Generic Linked Queue Class

public class LinkedQueue<E>
{
	private class Node
	{
		E object;
		Node next;
		
		Node(E obj, Node n)
		{
			object = obj;
			next = n;
		}
	}
	
	private Node first = null;
	private Node last = null;
	private int size = 0;
	
	public void enqueue(E obj)
	{
		if(first != null)
		{
			last.next = new Node(obj, null);
			last = last.next;
		}
		else
		{
			last = new Node(obj, null);
			first = last;
		}
		
		++size;
	}
	
	public E dequeue()
	{
		E obj = first.object;
		first = first.next;
		--size;
		
		if(first == null)
			last = null;
			
		return obj;
	}
	
	public boolean empty()
	{
		return (first == null);
	}
	
	public E peek()
	{
		return first.object;
	}
	
	public E peek(int i)
	{
		Node n = first;
		
		for(;i > 0; --i)
			n = n.next;
				
		return n.object;
	}
	
	public int size()
	{
		return size;
	}
}
