//Binary Search Tree Class <Generic, Generic>
 //Stores 2 objects, a key, which is sorted and a value associated with that key
 //Keys are unique, if you try to add an existing key the associated value will be updated

import java.util.NoSuchElementException;

public class BinarySearchTree<K extends Comparable<K>, V>
{
	private class Node
	{
		K key;
		V value;
		Node left;
		Node right;
		
		Node(K k, V v)
		{
			key = k;
			value = v;
			left = null;
			right = null;
		}
	}
	
	Node root = null;
	
	private Node add(K key, V value, Node node)
	{
		if(node == null)
			return new Node(key, value);
		else if(key.compareTo(node.key) == 0)
			node.value = value;
		else if(key.compareTo(node.key) < 0)
			node.left = add(key, value, node.left);
		else if(key.compareTo(node.key) > 0)
			node.right = add(key, value, node.right);
		
		return node;
	}
	
	public void add(K key, V value)
	{
		root = add(key, value, root);
	}
	
	private boolean contains(K key, Node node)
	{
		if(node == null)
			return false;
		else if(key.compareTo(node.key) == 0)
			return true;
		else if(key.compareTo(node.key) < 0)
			return contains(key, node.left);
		else
			return contains(key, node.right);
	}
	
	public boolean contains(K key)
	{
		return contains(key, root);
	}
	
	private V find(K key, Node node)
	{
		if(node == null)
			throw new NoSuchElementException();
		else if(key.compareTo(node.key) == 0)
			return node.value;
		else if(key.compareTo(node.key) < 0)
			return find(key, node.left);
		else
			return find(key, node.right);
	}
	
	public V find(K key)
	{
		return find(key, root);
	}
	
}