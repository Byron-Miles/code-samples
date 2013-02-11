/**
 * Comp280 Assigment2 question 8.
 * Byron Miles 220057347
 *
 * A process creates two child processes:
 * The first creates two grandchild processes
 * The second prints the pid of it's parent
 * Each process also prints 'Hello i'm pid()'
 **/

#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <stdio.h>

int main()
{
   pid_t control; //To seperate child1 from child2 and grandchild1
   pid_t parent = getpid(); //Save pid of parent
   int status, i;
   
   //Create the first child process
   control = fork();
   //Create the second child process
   if(getpid() == parent)
   {
      fork();
   }
   
   //Make the parent wait for both children
   if(getpid() == parent)
   {
      for(i = 0; i < 2; ++i)
      {
	 //Re-wait if interrupted
	 while((wait(&status) == -1) && (errno == EINTR));
      }
   }
   
   //Code for children
   if(getpid() != parent)
   {
      //Code for child1, doesn't have control set
      if(!control)
      {
	 //Create first grandchild
	 control = fork();
	 //Create second grandchild
	 if(control) //Note: grandchild1 doesn't have control set
	    fork();
      }
      //Code for child2, does have control set
      else
      {
	 printf("I'm child 2, pid %ld, my parents pid in hexadecimal is: %X\n",
	 (long)getpid(), getppid());
      }
   }
      
   //Message for all processes
   printf("Hello, my pid is: %ld\n",(long)getpid());
}