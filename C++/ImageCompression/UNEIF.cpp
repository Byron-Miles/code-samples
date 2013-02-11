///////////////////////////////////////////////////////
// UNEIF.cpp
// Implements UNEIF
//
// Written by: Byron Miles
// Last Updated: 01/08/2011
//

#include "UNEIF.h"

#include <iostream>
#include <bitset>
#include <queue>

// Default constructor
//-------------------------------------------------------------------------------------------------
UNEIF::UNEIF() : m_width(0), m_height(0)
{
	m_pR = NULL;
	m_pG = NULL;
	m_pB = NULL;
	m_pSymbols = NULL;
	m_pCounts = NULL;
	m_pTable = NULL;
}
// Destructor
//-------------------------------------------------------------------------------------------------
UNEIF::~UNEIF()
{
	if(m_pR != NULL)
		delete m_pR;
	if(m_pG != NULL)
		delete m_pG;
	if(m_pB != NULL)
		delete m_pB;
	if(m_pSymbols != NULL)
		delete m_pSymbols;
	if(m_pCounts != NULL)
		delete m_pCounts;
	if(m_pTable != NULL)
		delete m_pTable;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::open(const std::string &filename)
{
	std::ifstream inFile;
	// Open the input file in binary mode
	inFile.open(filename.c_str(), std::ios::binary);

	// Check file open ok
	if(!inFile)
	{
		std::cerr << "Error in open: could not open file '" << filename << "'" << std::endl;
		return false;
	}

	// Initialise symbols and counts
	m_pSymbols = new std::vector<unsigned int>();
	m_pCounts = new std::vector<unsigned int>();

	//Read and check the header
	if(!readHeader(inFile))
	{
		std::cerr << "Error in open: file format not supported" << std::endl;
		inFile.close();
		close(); // Prevent 'inconsistant state'
		return false;
	}

	// Build the huffman table
	m_pTable = new Huffman();
	m_pTable->createTable((*m_pSymbols), (*m_pCounts));

	// Initialise data
	m_pR = new std::vector<unsigned int>;
	m_pG = new std::vector<unsigned int>;
	m_pB = new std::vector<unsigned int>;

	// Read and check the data
	if(!readData(inFile))
	{
		std::cerr << "Error in open: data is corrupt." << std::endl;
		inFile.close();
		close(); //Prevent 'inconsistant state'
		return false;
	}

	// Close the file
	inFile.close();

	return true;
}
//-------------------------------------------------------------------------------------------------
void UNEIF::close()
{
	// Delete the data
	if(m_pR)
	{
		delete m_pR;
		m_pR = NULL;
	}
	if(m_pG)
	{
		delete m_pG;
		m_pG = NULL;
	}
	if(m_pB)
	{
		delete m_pB;
		m_pB = NULL;
	}
	if(m_pSymbols)
	{
		delete m_pSymbols;
		m_pSymbols = NULL;
	}
	if(m_pCounts)
	{
		delete m_pCounts;
		m_pCounts = NULL;
	}
	if(m_pTable)
	{
		delete m_pTable;
		m_pTable = NULL;
	}
	// Set values back to 0
	m_width = 0;
	m_height = 0;
}

// Accessors
//-------------------------------------------------------------------------------------------------
unsigned int UNEIF::width() const
{
	return m_width;
}
//-------------------------------------------------------------------------------------------------
unsigned int UNEIF::height() const
{
	return m_height;
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned int> & UNEIF::rVector() const
{
	return (*m_pR);
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned int> & UNEIF::gVector() const
{
	return (*m_pG);
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned int> & UNEIF::bVector() const
{
	return (*m_pB);
}

// Build a UNEIF from the given values and colour vectors
//-------------------------------------------------------------------------------------------------
bool UNEIF::build(unsigned int width, unsigned int height,
		const std::vector<unsigned int> &r, 
		const std::vector<unsigned int> &g, 
		const std::vector<unsigned int> &b)
{
	// Copy the header values
	m_width = width;
	m_height = height;

	// Copy each of the vectors
	m_pR = new std::vector<unsigned int>(r);
	m_pG = new std::vector<unsigned int>(g);
	m_pB = new std::vector<unsigned int>(b);

	// Allocate storage for other data
	m_pSymbols = new std::vector<unsigned int>();
	m_pCounts = new std::vector<unsigned int>();
	m_pTable = new Huffman();
	
	// Build Huffman code table
	m_pTable->countSymbols((*m_pR), (*m_pSymbols), (*m_pCounts));
	m_pTable->countSymbols((*m_pG), (*m_pSymbols), (*m_pCounts));
	m_pTable->countSymbols((*m_pB), (*m_pSymbols), (*m_pCounts));
	m_pTable->createTable((*m_pSymbols), (*m_pCounts));

	// Check that the values are valid
	if(!checkHeader())
	{
		std::cerr << "Error in build: invalid header values" << std::endl;
		close(); // Prevent inconsistant state
		return false;
	}

	// Check that the data is valid
	if(!checkData())
	{
		std::cerr << "Error in build: invalid data" << std::endl;
		close(); //Prevent inconsistant state
		return false;
	}

	return true;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::writeTo(const std::string &filename)
{
	// Check the header values are valid
	if(!checkHeader())
	{
		std::cerr << "Error in writeTo: invalid header values" << std::endl;
		return false;
	}

	// Check the data is valid
	if(!checkData())
	{
		std::cerr << "Error in writeTo: invalid data" << std::endl;
		return false;
	}

	std::ofstream outFile;
	// Open output file in binary mode
	outFile.open(filename.c_str(), std::ios::binary);

	// Check file open ok
	if(!outFile)
	{
		std::cerr << "Error in writeTo: could not open or create file '" << filename << "'" << std::endl;
		return false;
	}

	// Write header values
	outFile << m_width << " " << m_height << std::endl;
	outFile << m_pSymbols->size() << std::endl;
	// Write the symbol list
	for(size_t i = 0; i < m_pSymbols->size(); ++i)
	{
		outFile << (*m_pSymbols)[i];
		if(i + 1 < m_pSymbols->size())
			outFile << " ";
		else
			outFile << std::endl;
	}
	// Write the counts list
	for(size_t i = 0; i < m_pCounts->size(); ++i)
	{
		outFile << (*m_pCounts)[i];
		if(i + 1 < m_pCounts->size())
			outFile << " ";
		else
			outFile << std::endl;
	}

	// Setup buffers
	std::bitset<8> charBuffer; // Read individual bits in a char
	std::queue<bool> buffer; // Buffer
    std::vector<char> code; // Code vector
	char c; // Char for write
	size_t size = m_width * m_height;
	size_t index = 0;

	// Read all values from colour vectors
	while(index < size)
	{
		// Read enough bits into buffer to make a char
		while(index < size && buffer.size() < 8)
		{
			//Red
			if(m_pTable->codeFor((*m_pR)[index], code))
			{
				// Push the code onto the buffer
				for(size_t i = 0; i < code.size(); ++i)
				{
					if(code[i] == '1')
						buffer.push(true);
					else
						buffer.push(false);
				}
			}
			else
			{
				std::cerr << "Error in write: unable to find code for '" << (*m_pR)[index] << std::endl;
				outFile.close();
				return false;
			}
			//Green
			if(m_pTable->codeFor((*m_pG)[index], code))
			{
				// Push the code onto the buffer
				for(size_t i = 0; i < code.size(); ++i)
				{
					if(code[i] == '1')
						buffer.push(true);
					else
						buffer.push(false);
				}
			}
			else
			{
				std::cerr << "Error in write: unable to find code for '" << (*m_pG)[index] << std::endl;
				outFile.close();
				return false;
			}
			//Blue
			if(m_pTable->codeFor((*m_pB)[index], code))
			{
				// Push the code onto the buffer
				for(size_t i = 0; i < code.size(); ++i)
				{
					if(code[i] == '1')
						buffer.push(true);
					else
						buffer.push(false);
				}
			}
			else
			{
				std::cerr << "Error in write: unable to find code for '" << (*m_pB)[index] << std::endl;
				outFile.close();
				return false;
			}

			++index; // Move to next colour value
		}//While

		// Write from buffer to file
		while(buffer.size() >= 8)
		{
			charBuffer.reset(); // Reset the char buffer to 00000000
			//Construct an 8 bit set to make a char
			for(size_t i = 0; i < 8; ++i)
			{
				if(buffer.front())
					charBuffer.set(i);
				buffer.pop(); //Next bit
			}
			// Convert bits to char
			c = (unsigned char)charBuffer.to_ulong(); 
			// Write to file
			outFile.write(&c, 1);
		}
	}//While
	
	//Handle any left over bits in the buffer, create one last char
	if(!buffer.empty())
	{
		charBuffer.reset(); // Reset the char buffer to 00000000
		//Construct an 8 bit set to make a char
		for(size_t i = 0; i < 8 && !buffer.empty(); ++i)
		{
			if(buffer.front())
				charBuffer.set(i);
			buffer.pop(); //Next bit
		}
		// Convert bits to char
		c = (unsigned char)charBuffer.to_ulong(); 
		// Write to file
		outFile.write(&c, 1);
	}

	// Close the file
	outFile.close(); 

	return true;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::readHeader(std::ifstream &inFile)
{
	//Header format is:
	//WIDTH HEIGHT
	//NUMBER_OF_SYMBOLS
	//SYMBOLS
	//COUNTS

	unsigned int count; // Number of symbols and counts to read
	unsigned int value; // Read symbol / count into

	inFile >> m_width;
	inFile >> m_height;
	inFile >> count;
	
	//Read in the symbols
	for(unsigned int i = 0; i < count; ++i)
	{
		inFile >> value;
		m_pSymbols->push_back(value);
	}
	//Read in the counts
	for(unsigned int i = 0; i < count; ++i)
	{
		inFile >> value;
		m_pCounts->push_back(value);
	}
	
	// Check that all the values are valid
	if(!checkHeader())
	{
		std::cerr << "Error in readHeader: invalid header values" << std::endl;
		return false;
	}

	inFile.ignore(); // Absorb single white space after last count

	return true;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::readData(std::ifstream &inFile)
{
	std::bitset<8> charBuffer; // Read individual bits in a char
	std::queue<bool> buffer; // Buffer
	unsigned int symbol; // Collect symbol in
	bool gotSymbol; // Switch for if symbol was found
	std::vector<char> code; // Build up code
	char c; // Char for read
	size_t count = 0; // Count of symbols read
	size_t size = m_width * m_height * 3; // 3 colurs per pixel
	
	//Switch values for which colour is written next
	enum read {RED, GREEN, BLUE};
	read colour = RED; 

	// Read till end of file
	while(!inFile.eof() && count < size)
	{
		// Prevent the buffer from getting to big
		while(!inFile.eof() && buffer.size() < 512)
		{
			// Read a character
			inFile.read(&c, 1);
			// Prevent double 'read' of last char
			if(!inFile.eof())
			{
				// Convert to bits
				charBuffer = std::bitset<8>(c);
				// Populate the buffer with the bits
				for(size_t i = 0; i < 8; ++i)
				{
					if(charBuffer.test(i))
						buffer.push(true);
					else
						buffer.push(false);
				}
			}
		}
		//Build a code from the buffer, don't read more than size
		while(count < size && !buffer.empty())
		{
			gotSymbol = false;
			do
			{
				if(buffer.front())
					code.push_back('1');
				else
					code.push_back('0');
				buffer.pop();
				gotSymbol = m_pTable->symbolFor(code, symbol);
			}while(!gotSymbol && !buffer.empty());

			if(gotSymbol)
			{
				// Output the symbol
				switch(colour)
				{
				case RED:
					m_pR->push_back(symbol);
					colour = GREEN;
					break;

				case GREEN:
					m_pG->push_back(symbol);
					colour = BLUE;
					break;

				case BLUE:
					m_pB->push_back(symbol);
					colour = RED;
					break;
				}
				++count; // Increase count of symbols read
				code.clear(); // Reset the code
			}
		}//While
	}//While

	//Check the data
	if(!checkData())
	{
		std::cerr << "Error in readData: invalid data" << std::endl;
		return false;
	}

	return true;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::checkHeader()
{
	// Width and height
	if(m_width <= 0 || m_height <= 0)
	{
		std::cerr << "Error in checkHeader: width and height must be greater than 0" << std::endl;
		return false;
	}
	//Symbols and counts size
	if(m_pSymbols->size() != m_pCounts->size())
	{
		std::cerr << "Error in checkHeader: symbols and counts must be of equal size" << std::endl;
		return false;
	}
	//Count values
	for(size_t i = 0; i < m_pCounts->size(); ++i)
	{
		if((*m_pCounts)[i] <= 0)
		{
			std::cerr << "Error in checkHeader: count value must be greater or equal to 1" << std::endl;
			return false;
		}
	}

	return true;
}
//-------------------------------------------------------------------------------------------------
bool UNEIF::checkData()
{
	unsigned int size = m_width * m_height;
	// Check data in not NULL and of correct length
	if(m_pR == NULL || m_pR->size() != size)
	{
		std::cerr << "Error in checkData: Red vector is NULL or contains incorrect number of elements" << std::endl;
		return false;
	}
	if(m_pG == NULL || m_pG->size() != size)
	{
		std::cerr << "Error in checkData: Green vector is NULL or contains incorrect number of elements" << std::endl;
		return false;
	}
	if(m_pB == NULL || m_pB->size() != size)
	{
		std::cerr << "Error in checkData: Blue vector is NULL or contains incorrect number of elements" << std::endl;
		return false;
	}

	return true;
}
