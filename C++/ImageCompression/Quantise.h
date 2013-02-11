#ifndef QUANTISE_H
#define QUANTISE_H

///////////////////////////////////////////////////////
// Transform.h
// Used to quantise a soucre vector of floats into a 
// result vector of unsigned ints.
// Range is difference between the lower and upper boundries
// of the source, and must even.
// E.g. from -255 to 255 would be 512
// Step is the scaling factor.
// E.g. a step of 2 will yield values such as 2, 4, 6, 8, ...
//
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <vector>

// Quantise the source vector into the result vector
bool quantise(std::vector<float> source, std::vector<unsigned int> &result, int range, int step);
// De-quantise the source vector into a result vector
bool dequantise(std::vector<unsigned int> source, std::vector<float> &result, int range, int step);

#endif
