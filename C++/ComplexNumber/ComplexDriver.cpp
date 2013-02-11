//ComplexDriver

#include "Complex.h"

int main()
{
   Complex x;
   Complex y(4.3,8.2);
   Complex z(3.3,1.1);

   //Output
   std::cout << "X: " << x << "  Y: " << y << "  Z: " << z << std::endl;

   //Addition
   std::cout << "X + Y: " << (x + y) << std::endl;

   //Subtraction
   std::cout << "X - Z: " << (x - z) << std::endl;

   //Multiplaction
   std::cout << "Z * Y: " << (z * y) << std::endl;

   //Input
   std::cout << "Enter a new X \"(r,i)\": ";
   std::cin >> x;
   
   //Equality
   if(x == y)
      std::cout << "X now equals Y" << std::endl;
   else
      std::cout << "X does not eqaul Y" << std::endl;

   //Inequality
   if(x != z)
      std::cout << "X does not eqaul Z" << std::endl;
   else
      std::cout << "X now equals Z" << std::endl;

   return 0;
}
