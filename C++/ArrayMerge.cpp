//Merge

#include <iostream>

void merge(double a[], int l1, int l2)
{
   //Variable
   int i1 = 0; //Current index of first set of elements
   int i2 = l1; //Current index of second set of elements
   int arraySize = l1 + l2; //The total size of the array
   int nextPlace = 0; //The next place to move the remaining smallest element to
   double newArray[arraySize];

   //Process all the elements
   while(nextPlace < arraySize)
   {
      if(i1 < l1) //First part has elements left to process
      {
         if(i2 < arraySize) //Second part has elements left to process
         {
            if(a[i1] < a[i2]) //If first element less than second element
            {
               //Move the current element in the first part into the next empty place
               newArray[nextPlace++] = a[i1++];
            }
            else //Second element is smaller (or equal) to the first
            {
               //Move the current element in the second part into the next empty place
               newArray[nextPlace++] = a[i2++];
            }
         }
         else //Second part has no elements left
         {
            newArray[nextPlace++] = a[i1++];
         }
      }
      else //First part has no elements left
      {
         newArray[nextPlace++] = a[i2++];
      }
   }//End while
/*
   //Print the new array
   for(int i = 0; i < arraySize; ++i)
      std::cout << newArray[i] << " ";
   std::cout << std::endl;
   */
}

int main()
{
   double a[] = {1,3,5,6,7,9,2,4,6.1,8,10};
   std::cout << "Original    : ";
   for(int i = 0; i < 11; ++i)
   {
      std::cout << a[i] << " ";
   }
   std::cout << std::endl;

   merge(a, 6, 5);

   std::cout << "After merge : ";
   for(int i = 0; i < 11; ++i)
   {
      std::cout << a[i] << " ";
   }
   std::cout << std::endl;
   
   return 0;
}