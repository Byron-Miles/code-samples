/**
 * Comp280 Assigment2 question 7.
 * Byron Miles 220057347
 *
 * Creates an interger array with the numbers 1 to 10
 * and uses a pointer to step through the array and 
 * print the ints
 **/

#include <stdio.h>

main()
{
   //The array of 10 ints
   int a[10];
   //Pointer to int, initialised to first element of the array
   int *p = a;
   //A counter so we know where we are at
   int i;
   
   //Fill the array by adding an offest value and dereferencing 
   for(i = 0; i < 10; ++i)
      *(p + i) = i + 1; //1 to 10
   
   //Output the array by dereferencing then post incrementing p
   for(i = 0; i < 10; ++i)
      printf("%d\n",*p++);
}