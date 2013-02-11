#include <iostream>
#include <vector>

#include "PPM.h"
#include "Transform.h"
#include "Quantise.h"
#include "UNEIF.h"

int main(int argc, char *argv[])
{
        // Check for correct usage
	if(argc != 3)
	{
		std::cout << "Usage: " << argv[0] << " inFile outFile" << std::endl;
		return -1;
	}

        //Enviroment variables...
	unsigned int quantiseRange = 512;
        //You can change these two for different compression results
        //Note: The values in decode must match in order to decode correctly
	unsigned int quantiseStep = 4; // The 'roughness' of the quantisation
	unsigned int transformBlock = 64; // The maximum size of the transform coding blocks, must be x^2

	PPM image;
	if(image.open(argv[1]))
	{
                // Get the header information
		std::string format = image.format();
		int width = image.width();
		int height = image.height();
		int maxrgb = image.maxrgb();
                // Print the header
		std::cout << "PPM Header Info" << std::endl;
		std::cout << "Format: " << format << std::endl;
		std::cout << "Width : " << width << std::endl;
		std::cout << "Height: " << height << std::endl;
		std::cout << "MaxRGB: " << maxrgb << std::endl;
	
                // Get the colour vector
		std::vector<unsigned char> r = image.rVector();
		std::vector<unsigned char> g = image.gVector();
		std::vector<unsigned char> b = image.bVector();		

		//Close the PPM Image
		image.close();
		
                //Convert from char to float form transformation
		std::vector<float> fr;
		std::vector<float> fg;
		std::vector<float> fb;
		for(unsigned int i = 0; i < r.size(); ++i)
		{
			fr.push_back((float)r[i]);
			fg.push_back((float)g[i]);
			fb.push_back((float)b[i]);
		}

		//Transform
		std::vector<float> tfr;
		std::vector<float> tfg;
		std::vector<float> tfb;

		decompose(fr, tfr, transformBlock);
		decompose(fg, tfg, transformBlock);
		decompose(fb, tfb, transformBlock);

		std::cout << "Transformed" << std::endl;

		//Quantise
		std::vector<unsigned int> qr;
		std::vector<unsigned int> qg;
		std::vector<unsigned int> qb;

		quantise(tfr, qr, quantiseRange, quantiseStep);
		quantise(tfg, qg, quantiseRange, quantiseStep);
		quantise(tfb, qb, quantiseRange, quantiseStep);

		std::cout << "Quantised" << std::endl;

		//Write to UNEIF
		UNEIF uneif;
		if(uneif.build(width, height, qr, qg, qb))
                {
		        if(uneif.writeTo(argv[2]))
		                 std::cout << "Written To: " << argv[2] << std::endl;
                }
		uneif.close();
		
        }

	return 0;
}

