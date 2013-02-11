#ifndef PPM_H
#define PPM_H

///////////////////////////////////////////////////////
// PPM.h
// Used to read and write a 'P6' formatted PPM file to
// and from a set of values (width, height, maxrgb) and
// colour vectors
//
// Written by: Byron Miles
// Last Updated: 28/08/2011
//

#include <vector>
#include <string>
#include <fstream>

class PPM
{
private:
	std::string m_format; // The PPM format type. e.g. 'P6'
	unsigned int m_width, m_height; // The width, height
	unsigned int m_maxrgb; // The maximum value each colour, note: only up to '255' is supported
	std::vector<unsigned char> *m_pR; // Red vector
	std::vector<unsigned char> *m_pG; // Green vector
	std::vector<unsigned char> *m_pB; // Blue vecor

public:
	PPM();
	~PPM();

	bool open(const std::string &filename); // Build PPM from given file
	void close(); // Delete and reset values / data back to 0

	const std::string & format() const;
	unsigned int width() const;
	unsigned int height() const;
	unsigned int maxrgb() const;
	
	const std::vector<unsigned char> & rVector() const;
	const std::vector<unsigned char> & gVector() const;
	const std::vector<unsigned char> & bVector() const;

	// Build a PPM from the given values and colour vectors
	bool build(const std::string &format, unsigned int width, unsigned int height, unsigned int maxrgb, 
		const std::vector<unsigned char> &r, 
		const std::vector<unsigned char> &g, 
		const std::vector<unsigned char> &b); 

	bool writeTo(const std::string &filename); // Write the current values / data to a 'P6' formatted file

private:
	bool readHeader(std::ifstream &inFile);
	bool readData(std::ifstream &inFile);
	bool checkHeader();
	bool checkData();
};

#endif
