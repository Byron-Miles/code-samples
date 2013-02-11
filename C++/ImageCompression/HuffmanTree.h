#ifndef HUFFMAN_TREE_H
#define HUFFMAN_TREE_H

///////////////////////////////////////////////////////
// HuffmanTree.h
// Used to build a huffman tree and conversion table
// to convert symbols into bit codes and vise versa
//
// Written by: Byron Miles
// Last Updated: 1/09/2011
//

///////////////////////////////////////////////////////
// Node class
// Building blocks of the huffman tree
//

#include <vector>

class Node
{
private:
	unsigned int m_symbol;
	unsigned int m_count;
	Node* m_pLeftChild;
	Node* m_pRightChild;
	//Private copy constructor
	Node(const Node &n) {};

public:
	Node(unsigned int count, unsigned int symbol = 0, Node *leftChild = NULL, Node *rightChild = NULL);
	~Node();

	unsigned int symbol() const;
	unsigned int count() const;
	bool isChild() const;
	bool hasLeftChild() const;
	bool hasRightChild() const;
	const Node & leftChild() const;
	const Node & rightChild() const;

	bool operator<(const Node& n) const; // Used by std::priority_queue for sorting
};

///////////////////////////////////////////////////////
// HuffmanTree class
// Uses Node to build a huffman coding tree
// Can also build a translation table
//
class HuffmanTree
{
private:
	Node *m_pTree;

public:
	HuffmanTree();
	~HuffmanTree();

	// Builds the code tree from the given symbols and counts
	void buildTree(std::vector<unsigned int> &symbols, std::vector<unsigned int> &counts);
	// Builds the code table from the tree into the given vectors
	bool getTable(std::vector<unsigned int> &symbols, std::vector<std::vector<char> > &codes);
};

#endif
