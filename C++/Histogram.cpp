//Histogram
/*
 * Byron Miles
 * 220057347
 * 16/3/2010
 */

#include <iostream>

/* 
 * Accepts a negative terminated integer array and creates a
 * scaled histogram showing the number of elements in the
 * ranges, 0 to 64, 65 to 128, 129 to 192 and 193 to 255
 */
void histogram(const int *array)
{
   //Variables to hold the count for each range
   int m_0to64 = 0;
   int m_65to128 = 0;
   int m_129to192 = 0;
   int m_193to255 = 0;
   
   //Count the number of array elements in each range
   //Terminates when it finds a negative number
   for(int i = 0; *(array + i) >= 0; ++i)
   {
      if(*(array + i) < 65)
         ++m_0to64;
      else if(*(array + i) < 129)
         ++m_65to128;
      else if(*(array + i) < 193)
         ++m_129to192;
      else if(*(array + i) < 256)
         ++m_193to255;
   }     
   
   //Find the highest count
   int highestCount = m_0to64;
   if(m_65to128 > highestCount)
      highestCount = m_65to128;
   if(m_129to192 > highestCount)
      highestCount = m_129to192;
   if(m_193to255 > highestCount)
      highestCount = m_193to255;
 
   //Calculate the scaling factor
   int scalingFactor = (highestCount / 50) + 1;
   
   //Draw the histogram
   //Scale
   std::cout << "\nScale: " << scalingFactor;

   if(scalingFactor < 10)
      std::cout << "x  ";
   else if(scalingFactor < 100)
      std::cout << "x ";
   else
      std::cout << "x";
   
   std::cout << " |0        |10            |25                      |50" << std::endl;
   std::cout << "-----------------------------------------------------------------" << std::endl;
   int outCount; //The number of *s to be displayed for that bar

   //0 to 64 bar
   std::cout << "0 to 64    ::"; //Note: extra spaces so bars start at same position
   //Calculate the outCount for 0 to 64
   outCount = m_0to64 / scalingFactor;
   //Display the apporiate number of *s
   for(int n = 0; n < outCount; ++n) 
      std::cout << "*";
   std::cout << std::endl; //Move to next line

    //65 to 128 bar
   std::cout << "65 to 128  ::";
   //Calculate the outCount for 65 to 128
   outCount = m_65to128 / scalingFactor;
   //Display the apporiate number of *s
   for(int n = 0; n < outCount; ++n)
      std::cout << "*";
   std::cout << std::endl; //Move to next line
   
    //129 to 192 bar
   std::cout << "129 to 192 ::";
   //Calculate the outCount for 129 to 192
   outCount = m_129to192 / scalingFactor;
   //Display the apporiate number of *s
   for(int n = 0; n < outCount; ++n)
      std::cout << "*";
   std::cout << std::endl; //Move to next line

    //193 to 255 bar
   std::cout << "193 to 255 ::";
   //Calculate the outCount for 193 to 255
   outCount = m_193to255 / scalingFactor;
   //Display the apporiate number of *s
   for(int n = 0; n < outCount; ++n)
      std::cout << "*";
   std::cout << std::endl; //Move to next line

   std::cout << "-----------------------------------------------------------------" << std::endl;
}

int main()
{
   //Welcome message
   std::cout << "Welcome. To end input enter a negative number" << std::endl;

   //Input loop
   int num, index = 0, a[65535];
   do
   {
   std::cout << "Please enter an interger between 0 and 255: ";
   std::cin >> num;
   a[index++] = num;
   }while(num >= 0);

   //Call histogram function
   histogram(a);
   
   return 0;
}
