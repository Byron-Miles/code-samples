///////////////////////////////////////////////////////
// Quantise.cpp
// Implements Quantise
//
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <iostream>

#include "Quantise.h"

// Mid tread quantiser
//-------------------------------------------------------------------------------------------------
bool quantise(std::vector<float> source, std::vector<unsigned int> &result, int range, int step)
{
	if(range % 2 != 0)
	{
		std::cerr << "Error in quantise: range must be divisible by 2" << std::endl;
		return false;
	}

	for(size_t i = 0; i < source.size(); ++i)
	{
		float value = source[i];
		value += (range / 2) + 0.5f; //Shift
		value /= step; //Scale
		result.push_back((unsigned int)value); //Truncate
	}

	return true;
}


// De-quantise
//-------------------------------------------------------------------------------------------------
bool dequantise(std::vector<unsigned int> source, std::vector<float> &result, int range, int step)
{
	if(range % 2 != 0)
	{
		std::cerr << "Error in dequantise: range must be divisible by 2" << std::endl;
		return false;
	}

	for(size_t i = 0; i < source.size(); ++i)
	{
		int value = (int)source[i];
		value *= step; //Scale
		value -= (range / 2); //Un-shift
		result.push_back((float)value);
	}

	return true;
}
