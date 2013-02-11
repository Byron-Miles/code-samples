///////////////////////////////////////////////////////
// Transform.cpp
// Implements Transform
//
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <iostream>

#include "Transform.h"

// Decomposes the source vector into the result vector
//-------------------------------------------------------------------------------------------------
bool decompose(std::vector<float> source, std::vector<float> &result, unsigned int blockSize)
{
	std::vector<float> av; // Set of average values
	std::vector<float> dv; // Set of difference values
	std::vector<float> block; // Block to transform

	unsigned int sourceSize = source.size();
	unsigned int blockStep = 0; // Block of source to transform

	while(blockStep < sourceSize)
	{	
		// Adjust blockSize to highest power of 2 avaliable for remaining source elements
		while(sourceSize - blockStep < blockSize)
			blockSize /= 2;

		//Check blockSize is a power of two
		if(blockSize % 2 != 0 && blockSize != 1)
		{
			std::cerr << "Error in transform: blockSize must be a power of 2" << std::endl;
			return false;
		}

		for(unsigned int i = blockStep; i < blockStep + blockSize; ++i)
		{
			block.push_back(source[i]);
		}

		unsigned int size = block.size();

		// Transform the block
		while(size > 1)
		{
			for(unsigned int i = 0; i < size; i+=2) //Jump two at a time
			{
				float a = (block[i] + block[i+1]) / 2.0f;
				float d = (block[i] - block[i+1]) / 2.0f;

				av.push_back(a);
				dv.push_back(d);
			}

			for(unsigned int i = 0; i < av.size(); ++i)
			{
				block[i] = av[i];
			}

			// Divide the size by 2
			size /= 2;
		
			for(unsigned int i = 0, j = av.size(); i < dv.size(); ++i, ++j)
			{
				block[j] = dv[i];
			}
	
			// Clear the average and difference values
			av.clear();
			dv.clear();
		}//End while

		// Append the block to the result
		for(unsigned int i = 0; i < block.size(); ++i)
		{
			result.push_back(block[i]);
		}

		// Update the step and clear the block vector
		blockStep += block.size();
		block.clear();
	}//End while

	return true;
}

// Recomposes the source vector into the result vector
//-------------------------------------------------------------------------------------------------
bool recompose(std::vector<float> source, std::vector<float> &result, unsigned int blockSize)
{
	std::vector<float> av; // Set of average values
	std::vector<float> dv; // Set of difference values
	std::vector<float> block; // Block to transform

	unsigned int sourceSize = source.size();
	unsigned int blockStep = 0; // Block of source to transform

	while(blockStep < sourceSize)
	{	
		// Adjust blockSize to highest power of 2 avaliable for remaining source elements
		while(sourceSize - blockStep < blockSize)
			blockSize /= 2;

		//Check blockSize is a power of two
		if(blockSize % 2 != 0 && blockSize != 1)
		{
			std::cerr << "Error in transform: blockSize must be a power of 2" << std::endl;
			return false;
		}

		for(unsigned int i = blockStep; i < blockStep + blockSize; ++i)
		{
			block.push_back(source[i]);
		}

		unsigned int size = 1;

		// Transform the block
		while(size < block.size())
		{
			for(unsigned int i = 0; i < size; ++i)
			{
				av.push_back(block[i]);
			}

			// Multiply the size by 2
			size *= 2;

			for(unsigned int i = av.size(); i < size; ++i)
			{
				dv.push_back(block[i]);
			}

			for(unsigned int i = 0, j = 0; i < size; i+=2, j++) //Jump two at a time
			{
				float a = (av[j] + dv[j]);
				float d = (av[j] - dv[j]);

				block[i] = a;
				block[i+1] = d;
			}
			
			// Clear the average and difference values
			av.clear();
			dv.clear();
		}//End while

		// Append the block to the result
		for(unsigned int i = 0; i < block.size(); ++i)
		{
			result.push_back(block[i]);
		}

		// Update the step and clear the block vector
		blockStep += block.size();
		block.clear();
	}//End while

	return true;
}
