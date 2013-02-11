///////////////////////////////////////////////////////
// PPM.cpp
// Implements PPM 
//
// Written by: Byron Miles
// Last Updated: 28/08/2011
//

#include <iostream>

#include "PPM.h"

// Default constructor
//-------------------------------------------------------------------------------------------------
PPM::PPM() : m_width(0), m_height(0), m_maxrgb(0)
{
	m_format = "";
	m_pR = NULL;
	m_pG = NULL;
	m_pB = NULL;
}

// Destructor
//-------------------------------------------------------------------------------------------------
PPM::~PPM()
{
	if(m_pR)
		delete m_pR;
	if(m_pG)
		delete m_pG;
	if(m_pB)
		delete m_pB;
}
	

// Open the given file
//-------------------------------------------------------------------------------------------------
bool PPM::open(const std::string &filename)
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

	// Read and check the header
	if(!readHeader(inFile))
	{
		std::cerr << "Error in open: file format not supported" << std::endl;
		close(); // Prevent 'inconsistant state'
		return false;
	}

	// Allocate storage space for the data
	unsigned int size = m_width * m_height;
	m_pR = new std::vector<unsigned char>(size);
	m_pG = new std::vector<unsigned char>(size);
	m_pB = new std::vector<unsigned char>(size);

	// Read and check the data
	if(!readData(inFile))
	{
		std::cerr << "Error in open: data is corrupt." << std::endl;
		close(); // Prevent 'inconsistant state'
		return false;
	}

	// Close the file
	inFile.close();

	return true;
}

// Delete any data and reset values
//-------------------------------------------------------------------------------------------------
void PPM::close()
{
	// Delete data
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
	//Set values back to 0
	m_format = "";
	m_width = 0;
	m_height = 0;
	m_maxrgb = 0;
}

//Accessors
//-------------------------------------------------------------------------------------------------
const std::string & PPM::format() const
{
	return m_format;
}
//-------------------------------------------------------------------------------------------------
unsigned int PPM::width() const
{
	return m_width;
}
//-------------------------------------------------------------------------------------------------
unsigned int PPM::height() const
{
	return m_height;
}
//-------------------------------------------------------------------------------------------------
unsigned int PPM::maxrgb() const
{
	return m_maxrgb;
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned char> & PPM::rVector() const
{
	return (*m_pR);
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned char> & PPM::gVector() const
{
	return (*m_pG);
}
//-------------------------------------------------------------------------------------------------
const std::vector<unsigned char> & PPM::bVector() const
{
	return (*m_pB);
}

// Build a PPM from given attributes
//-------------------------------------------------------------------------------------------------
bool PPM::build(const std::string &format, unsigned int width, unsigned int height, unsigned int maxrgb, 
		const std::vector<unsigned char> &r, 
		const std::vector<unsigned char> &g, 
		const std::vector<unsigned char> &b)
{
	// Copy the header values
	m_format = format;
	m_width = width;
	m_height = height;
	m_maxrgb = maxrgb;

	// Check that the values are valid
	if(!checkHeader())
	{
		std::cerr << "Error in build: invalid header values" << std::endl;
		close(); // Prevent inconsistant state
		return false;
	}

	// Copy each of the vectors
	m_pR = new std::vector<unsigned char>(r);
	m_pG = new std::vector<unsigned char>(g);
	m_pB = new std::vector<unsigned char>(b);

	// Check that the data is valid
	if(!checkData())
	{
		std::cerr << "Error in build: invalid data" << std::endl;
		close(); // Prevent inconsistant state
		return false;
	}

	return true;
}

// Write current contents to file
//-------------------------------------------------------------------------------------------------
bool PPM::writeTo(const std::string &filename)
{
	// Cheak the header values are valid
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
	outFile << m_format << std::endl;
	outFile << "# Created by PPM.cpp" << std::endl;
	outFile << m_width << " " << m_height << std::endl;
	outFile << m_maxrgb << std::endl;


	// Iterate through vectors and write data
	char c;
	unsigned int size = m_width * m_height;
	for(unsigned int i = 0; i < size; ++i)
	{
		c = (*m_pR)[i];
		outFile.write(&c, 1);
		c = (*m_pG)[i];
		outFile.write(&c, 1);
		c = (*m_pB)[i];
		outFile.write(&c, 1);		
	}

	// Close the file
	outFile.close();

	return true;
}

//Read the header and check it is the correct format
//-------------------------------------------------------------------------------------------------
bool PPM::readHeader(std::ifstream &inFile)
{
	// Parts of the header to be read
	enum read {FORMAT, WIDTH, HEIGHT, MAXRGB, END_OF_HEADER};
	read part = FORMAT;

	while(part != END_OF_HEADER)
	{
		// Check for eof
		if(inFile.eof())
		{
			std::cerr << "Error in readHeader: unexpected end of file" << std::endl;
			return false;
		}

		// Skip any white space
		if(isspace(inFile.peek()))
		{
			inFile.ignore();
			continue; // Restart loop
		}

		// Skip any comments
		if(inFile.peek() == '#')
		{
			inFile.ignore(65535, '\n'); // Ignore till end of line..
			continue; // Restart loop
		}

		// Read the current part
		switch (part)
		{
		case FORMAT:
			inFile >> m_format;
			// Set next part
			part = WIDTH;
			break;

		case WIDTH:
			inFile >> m_width;
			// Set next part
			part = HEIGHT;
			break;

		case HEIGHT:
			inFile >> m_height;	
			// Set next part
			part = MAXRGB;
			break;

		case MAXRGB:
			inFile >> m_maxrgb;
			// Set next part
			part = END_OF_HEADER;
			break;
		}//End switch
	}//End While	

	// Check that all the values are valid
	if(!checkHeader())
	{
		std::cerr << "Error in readHeader: invalid header values" << std::endl;
		return false;
	}

	inFile.ignore(); // Absorb single whitespace character between maxrgb and data

	return true;
}

// Read the data
//-------------------------------------------------------------------------------------------------
bool PPM::readData(std::ifstream &inFile)
{
	// Read data into r, g and b vectors
	// Note: if << is used data that looks like a white space will be skipped
	char c;
	int length = m_width * m_height;
	for(int i = 0; i < length; ++i)
	{
		// Red
		inFile.read(&c, 1); // read a byte
		// Check for eof
		if(inFile.eof())
		{
			std::cerr << "Error in readData: unexpected end of file" << std::endl;
			return false;
		}
		// Set value
		(*m_pR)[i] = (unsigned char)c;

		// Green
		inFile.read(&c, 1); // read a byte
		// Check for eof
		if(inFile.eof())
		{
			std::cerr << "Error in readData: unexpected end of file" << std::endl;
			return false;
		}
		// Set value
		(*m_pG)[i] = (unsigned char)c;

		// Blue
		inFile.read(&c, 1); // read a byte
		// Check for eof
		if(inFile.eof())
		{
			std::cerr << "Error in readData: unexpected end of file" << std::endl;
			return false;
		}
		// Set value
		(*m_pB)[i] = (unsigned char)c;
	}

	// Check the data is valid
	if(!checkData())
	{
		std::cerr << "Error in readData: invalid data" << std::cout;
		return false;
	}

	return true;
}

// Check all the currect header values are valid
//-------------------------------------------------------------------------------------------------
bool PPM::checkHeader()
{
	if(m_format.size() != 2 || m_format[0] != 'P' || m_format[1] != '6')
	{
		std::cerr << "Error in checkHeader: only PPM format 'P6' is supported" << std::endl;
		return false;
	}

	// Width and height
	if(m_width <= 0 || m_height <= 0)
	{
		std::cerr << "Error in checkHeader: width and height must be greater than 0" << std::endl;
		return false;
	}
	// Max rgb value
	if(m_maxrgb <= 0 || m_maxrgb > 255)
	{
		std::cerr << "Error in checkHeader: maxrgb must be between 1 and 255 inclusive" << std::endl;
		return false;
	}

	return true;
}

//Check the data is consistant with the header values
//-------------------------------------------------------------------------------------------------
bool PPM::checkData()
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
