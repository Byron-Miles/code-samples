//Program 4.19

#include <iostream>

int main()
{
   int number = 0; //the number input by the user
   int largest = 0; //the largest number input so far
   int secondLargest = 0; //the second largest number input so far
   
   for(int c = 0; c < 10; c++)
   {
      std::cout << "[" << (c+1) << "]Please enter a number: ";
      std::cin >> number;
      
      //Check if it's the largest number so far
      if(number >= largest)
      {
         //demote the previous largest to second largest   
         secondLargest = largest;
         //assign the new largest
         largest = number;
      }
      //Check if it's the second largest number so far
      else if(number > secondLargest)
      {
         secondLargest = number;
      }
   }
   
   //results
   std::cout << "The largest number you entered was: " << largest << std::endl;
   std::cout << "The second largest number you entered was: " << secondLargest << std::endl;
   
   return 0;
}
