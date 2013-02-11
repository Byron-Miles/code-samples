//Recursive interger length

#include <iostream>

int intergerLength(int num)
{
   //If there is more than a single digit left
   if(num / 10 != 0)
   {
      //Return 1 + the remaining number of digits
      return 1 + intergerLength(num / 10);
   }
   //Base case
   else
   {
      //Return 1 for the single digit that remains
      return 1;
   }
}

int main()
{
   int num;
   std::cout << "Enter an interger: ";
   std::cin >> num;

   std::cout << "The length of " << num << " is ";
   std::cout << intergerLength(num) << std::endl;

   return 0;
}
