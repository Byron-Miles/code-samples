#ifndef UNEIF_H
#define UNEIF_H

///////////////////////////////////////////////////////
// UNEIF.h
// Used to read and write a UNEIF file to
// and from a set of values and vectors
// Uses a huffman code table to convert to and
// from bit codes for effcient storage of data
//
// Written by: Byron Miles
// Last Updated: 01/08/2011
//

#include <vector>
#include <string>
#include <fstream>

#include "Huffman.h"

class UNEIF
{
private:
	unsigned int m_width, m_height; // The width, height
	std::vector<unsigned int> *m_pR; // Red vector
	std::vector<unsigned int> *m_pG; // Green vector
	std::vector<unsigned int> *m_pB; // Blue vecor
	std::vector<unsigned int> *m_pSymbols; // Symbols vector
	std::vector<unsigned int> *m_pCounts; // Counts vector
	Huffman *m_pTable; // The huffman code table

public:
	UNEIF();
	~UNEIF();

	bool open(const std::string &filename); // Build UNEIF from a given file
	void close(); // Delete / reset values and data back to 0

	unsigned int width() const;
	unsigned int height() const;

	const std::vector<unsigned int> & rVector() const;
	const std::vector<unsigned int> & gVector() const;
	const std::vector<unsigned int> & bVector() const;

	// Build a UNEIF from the given values and colour vectors
	bool build(unsigned int width, unsigned int height,
		const std::vector<unsigned int> &r, 
		const std::vector<unsigned int> &g, 
		const std::vector<unsigned int> &b); 

	bool writeTo(const std::string &filename); // Write the current values / data to a file

private:
	bool readHeader(std::ifstream &inFile);
	bool readData(std::ifstream &inFile);
	bool checkHeader();
	bool checkData();
};

#endif
