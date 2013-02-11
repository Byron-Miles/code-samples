#ifndef TRANSFORM_H
#define TRANSFORM_H

///////////////////////////////////////////////////////
// Transform.h
// Used to do haar wavelet transforms on vectors of 
// floats. It uses blocks of powers of 2.
// 
// Written by: Byron Miles
// Last Updated: 30/08/2011
//

#include <vector>

// Decomposes a source vector into a transformed result vector
bool decompose(std::vector<float> source, std::vector<float> &result, unsigned int blockSize);
// Recomposes a transformed source vector into a result vector
bool recompose(std::vector<float> source, std::vector<float> &result, unsigned int blockSize);

#endif
