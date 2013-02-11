///////////////////////////////////////////////////////
// Huffman.cpp
// Implements Huffman
//
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <iostream>

#include "Huffman.h"
#include "HuffmanTree.h"

//Huffman
//-------------------------------------------------------------------------------------------------
Huffman::Huffman()
{
	m_pSymbols = new std::vector<unsigned int>;
	m_pCodes = new std::vector<std::vector<char> >;
}
//-------------------------------------------------------------------------------------------------
Huffman::~Huffman()
{
	if(m_pSymbols != NULL)
		delete m_pSymbols;
	if(m_pCodes != NULL)
		delete m_pCodes;
}

// used to find the index of a symbol in the given vector, if it exists
//-------------------------------------------------------------------------------------------------
bool findSymbol(unsigned int symbol, const std::vector<unsigned int> &symbols, size_t &index)
{
	for(size_t i = 0; i < symbols.size(); ++i)
	{
		// Check for the symbol and return the index
		if(symbol == symbols[i])
		{
			index = i;
			return true;
		}
	}

	return false;
}
//-------------------------------------------------------------------------------------------------
void Huffman::sortTable()
{
	size_t shortest; // Size of shortest code
	size_t index; // Index of shortest code
	std::vector<char> codeSwap;
	unsigned int symbolSwap;

	// Selection sort
	for(size_t i = 0; i < m_pCodes->size(); ++i)
	{
		shortest = (*m_pCodes)[i].size();
		index = i;
		// Find the shortest code length
		for(size_t j = i+1; j < m_pCodes->size(); ++j)
		{
			if((*m_pCodes)[j].size() < shortest)
			{
				shortest = (*m_pCodes)[j].size();
				index = j;
			}
		}
		// Swap
		if(index != i)
		{
			codeSwap = (*m_pCodes)[i];
			(*m_pCodes)[i] = (*m_pCodes)[index];
			(*m_pCodes)[index] = codeSwap;
			// Same for symbols
			symbolSwap = (*m_pSymbols)[i];
			(*m_pSymbols)[i] = (*m_pSymbols)[index];
			(*m_pSymbols)[index] = symbolSwap;
		}
	}
}
//-------------------------------------------------------------------------------------------------
void Huffman::countSymbols(const std::vector<unsigned int> &source, std::vector<unsigned int> &symbols, std::vector<unsigned int> &counts)
{
      unsigned int symbol; // Current symbol from source
      size_t index; // Index of symbol in symbols

      for(size_t i = 0; i < source.size(); ++i)
      {
         symbol = source[i];
	 // Check for symbol in symbols vector
	 if(findSymbol(symbol, symbols, index))
	 {
	    counts[index]++;
	 }
	 else
	 {
	    symbols.push_back(symbol);
	    counts.push_back(1);
	 }
      }
}
//-------------------------------------------------------------------------------------------------
bool Huffman::createTable(std::vector<unsigned int> symbols, std::vector<unsigned int> counts)
{
	HuffmanTree tree; // Tree for creating the table
	
	// Build the tree
	tree.buildTree(symbols, counts);
	
	// Get the table from the tree
	if(! tree.getTable((*m_pSymbols), (*m_pCodes)))
	{
		std::cerr << "Error in createTable: tree is empty" << std::cout;
		return false;
	}

	//Sort the table by code length for faster average lookup
	sortTable();

	return true;
}
//-------------------------------------------------------------------------------------------------
bool Huffman::symbolFor(const std::vector<char> &code, unsigned int &symbol)
{
	// Search the table for the code
	for(size_t i = 0; i < m_pCodes->size(); ++i)
	{
		if(code == (*m_pCodes)[i])
		{
			symbol = (*m_pSymbols)[i];
			return true;
		}
		//Early exit if code code.size < m_pCodes[i].size()
		//Warning: Table must be sorted by code size!
		if(code.size() < (*m_pCodes)[i].size())
			return false;
	}
	return false;
}
//-------------------------------------------------------------------------------------------------
bool Huffman::codeFor(const unsigned int &symbol, std::vector<char> &code)
{
	//Search the table for the symbol
	for(size_t i = 0; i < m_pSymbols->size(); ++i)
	{
		if(symbol == (*m_pSymbols)[i])
		{
			code = (*m_pCodes)[i];
			return true;
		}
	}
	return false;
}
