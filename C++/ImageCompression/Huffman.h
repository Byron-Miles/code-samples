#ifndef HUFFMAN_H
#define HUFFMAN_H

///////////////////////////////////////////////////////
// Huffman.h
// Uses huffman tree to build a code table from red,
// green and blue source vectors.
// Provides methods for converting between symbols
// and their respective codes and vise versa
//
// Written by: Byron Miles
// Last Updated: 1/09/2011
//

#include <vector>

class Huffman
{
private:
	std::vector<unsigned int> *m_pSymbols; // Symbols in the table
	std::vector<std::vector<char> > *m_pCodes; // Codes for the symbols

public:
	Huffman();
	~Huffman();

	// Create a list of the symbols and counts for the r, g, b vectors
	void countSymbols(const std::vector<unsigned int> &source, std::vector<unsigned int> &symbols, std::vector<unsigned int> &counts);
	// Create a code table for the given symbol and count vectors
	// Note: vectors are passed by value as copies will be destroyed
	bool createTable(std::vector<unsigned int> symbols, std::vector<unsigned int> counts);
	// Get the symbol for a given code, if it exists
	bool symbolFor(const std::vector<char> &code, unsigned int &symbol);
	// Get the code for a given symbol, if it exists
	bool codeFor(const unsigned int &symbol, std::vector<char> &code);
	
private:
	// Sorts the table for faster average lookup
	void sortTable();
};

#endif
