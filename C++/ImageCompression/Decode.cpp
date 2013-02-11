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
	unsigned int quantiseRange = 512; // The range of quantiser, in this case 512 (+255 to -255)
        //You can change these two for different compression results
        //Note: They must match the values from encode at the time the file was encoded!
	unsigned int quantiseStep = 4; // The 'roughness' of the quantisation
	unsigned int transformBlock = 64; // The maximum size of the transform coding blocks, must be x^2
        // Enable / Disable error correction
        bool correctErrors = true;

        UNEIF uneif;
        if(uneif.open(argv[1]))
        {
                // Get header information
		int width = uneif.width();
		int height = uneif.height();
                // Print the header
                std::cout << "UNEIF Header Info" << std::endl;
                std::cout << "Width : " << width << std::endl;
                std::cout << "Height: " << height << std::endl;

                // Get the colour vectors
		std::vector<unsigned int> qr = uneif.rVector();
		std::vector<unsigned int> qg = uneif.gVector();
		std::vector<unsigned int> qb = uneif.bVector();
               
                // Close the uneif Image
                uneif.close();

		//De-quantise
		std::vector<float> tfr;
		std::vector<float> tfg;
		std::vector<float> tfb;

		dequantise(qr, tfr, quantiseRange, quantiseStep);
		dequantise(qg, tfg, quantiseRange, quantiseStep);
		dequantise(qb, tfb, quantiseRange, quantiseStep);

		std::cout << "De-quantised" << std::endl;

		//Un-transform
		std::vector<float> fr;
		std::vector<float> fg;
		std::vector<float> fb;

		recompose(tfr, fr, transformBlock);
		recompose(tfg, fg, transformBlock);
		recompose(tfb, fb, transformBlock);

		std::cout << "Un-transformed" << std::endl;

		//Error correction
                //Due to quantisation and transformation some values can
                //end up outside the range 0 to 255 leading to 'spots' 
                //on the image, this brings the values back into range
                if(correctErrors)
                {
		        for(unsigned int i = 0; i < fr.size(); ++i)
		        {
			         if(fr[i] < 0.0f)
				      fr[i] = 0.0f;
               			 else if(fr[i] > 255.0f)
				      fr[i] = 255.0f;
   			         if(fg[i] < 0.0f)
				      fg[i] = 0.0f;
			         else if(fg[i] > 255.0f)
				      fg[i] = 255.0f;
			         if(fb[i] < 0.0f)
				      fb[i] = 0.0f;
			         else if(fb[i] > 255.0f)
				      fb[i] = 255.0f;
		        }
                }

                //Convert from float back to char
		std::vector<unsigned char> r;
		std::vector<unsigned char> g;
		std::vector<unsigned char> b;

		for(unsigned int i = 0; i < fr.size(); ++i)
		{
			r.push_back((unsigned char)fr[i]);
			g.push_back((unsigned char)fg[i]);
			b.push_back((unsigned char)fb[i]);
		}

		PPM image;

		// Build new image
                // Assumed to be type 'P6' with maxrbg of 255
		if(image.build("P6", width, height, 255, r, g, b))
		{
			if(image.writeTo(argv[2]))
				std::cout << "Written To: " << argv[2] << std::endl;
		}
                image.close();
         }
}

