///////////////////////////////////////////////////////
// HuffmanTree.cpp
// Implements HuffmanTree
//
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <iostream>
#include <queue>

#include "HuffmanTree.h"

//Node
//-------------------------------------------------------------------------------------------------
Node::Node(unsigned int count, unsigned int symbol, Node *leftChild, Node *rightChild)
{
	m_count = count;
	m_symbol = symbol;
	m_pLeftChild = leftChild;
	m_pRightChild = rightChild;
}
//-------------------------------------------------------------------------------------------------
Node::~Node()
{
	if(m_pLeftChild != NULL)
		delete m_pLeftChild;
	if(m_pRightChild != NULL)
		delete m_pRightChild;
}
//-------------------------------------------------------------------------------------------------
unsigned int Node::symbol() const
{
	return m_symbol;
}
//-------------------------------------------------------------------------------------------------
unsigned int Node::count() const
{
	return m_count;
}
//-------------------------------------------------------------------------------------------------
bool Node::isChild() const
{
	// A child has no children
	if(m_pLeftChild == NULL && m_pRightChild == NULL)
		return true;

	return false;
}
//-------------------------------------------------------------------------------------------------
bool Node::hasLeftChild() const
{
	return (m_pLeftChild != NULL);
}
//-------------------------------------------------------------------------------------------------
bool Node::hasRightChild() const
{
	return (m_pRightChild != NULL);
}
//-------------------------------------------------------------------------------------------------
const Node & Node::leftChild() const
{
	return (*m_pLeftChild);
}
//-------------------------------------------------------------------------------------------------
const Node & Node::rightChild() const
{
	return (*m_pRightChild);
}
//-------------------------------------------------------------------------------------------------
bool Node::operator<(const Node& n) const
{
	//Note: > is used for the comparison as we want lower values to
	//		have higher precidence in the std::priority_queue
	return (this->m_count > n.m_count);
}

// Used to compare pointers to node in the std::priority_queue
//-------------------------------------------------------------------------------------------------
struct compareNode : public std::binary_function<Node*, Node*, bool>
{
	bool operator()(const Node *lhs, const Node* rhs) const
	{
		//Note: > is used for the comparison as we want lower values to
		//		have higher precidence in the std::priority_queue
		return (lhs->count() > rhs->count());
	}
};




//HuffmanTree
//-------------------------------------------------------------------------------------------------
HuffmanTree::HuffmanTree()
{
	m_pTree = NULL;
}
//-------------------------------------------------------------------------------------------------
HuffmanTree::~HuffmanTree()
{
	if(m_pTree != NULL)
		delete m_pTree;
}
//-------------------------------------------------------------------------------------------------
void HuffmanTree::buildTree(std::vector<unsigned int> &symbols, std::vector<unsigned int> &counts)
{
	// Use a priority queue with out compareNode function to keep the Nodes in order
	std::priority_queue<Node*, std::vector<Node*>, compareNode> tree;
	Node* node1; // The two lowest frequency nodes
	Node* node2;
	//Turn all the symbols and counts into nodes
	while(!symbols.empty() && !counts.empty())
	{
		tree.push(new Node(counts.back(), symbols.back()));
		counts.pop_back();
		symbols.pop_back();
	}

	//Take the two lowest frequency nodes and create a new parent node from them
	while(!tree.empty())
	{
		node1 = tree.top();
		tree.pop();
		// Last node is top parent node
		if(!tree.empty())
		{
			node2 = tree.top();
			tree.pop();
			// Create parent node, note:  highest (node2) on left, lowest (node1) on right
			tree.push(new Node(node1->count() + node2->count(), 0, node2, node1));
		}
	}

	// Keep a pointer to the top node
	m_pTree = node1;
}

//Recursive function used to build up the table from the tree
//-------------------------------------------------------------------------------------------------
void buildTable(const Node &n, std::vector<char> &c, std::vector<unsigned int> &symbols, std::vector<std::vector<char> > &codes)
{
	//Depth first search...
	if(n.hasLeftChild())
	{
		c.push_back('1');
		buildTable(n.leftChild(), c, symbols, codes);
		c.pop_back();
	}
	if(n.hasRightChild())
	{
		c.push_back('0');
		buildTable(n.rightChild(), c, symbols, codes);
		c.pop_back();
	}
	if(n.isChild())
	{
		symbols.push_back(n.symbol()); // Add the symbol to the table
		codes.push_back(std::vector<char>(c)); // Add code to table
	}
}
//-------------------------------------------------------------------------------------------------
bool HuffmanTree::getTable(std::vector<unsigned int> &symbols, std::vector<std::vector<char> > &codes)
{
	// Make sure the tree is built first
	if(m_pTree == NULL)
	{
		std::cerr << "Error in getTable: Tree must be built first" << std::endl;
		return false;
	}

	std::vector<char> c;
	buildTable((*m_pTree), c, symbols, codes);

	return true;
}

