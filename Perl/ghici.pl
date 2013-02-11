#!/usr/bin/perl
use strict;
use warnings;

use constant false => 0;
use constant true => 1;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 4
#Ghici game. The user tries to guess a number between 1 and 1000 (exclusive).

#Note: I have used next; and continue blocks to control the flow of this program
#      as it is a lot neater than the nested if else statements I would have used
#      otherwise.

#Improvements: Ability to play multiple games without restarting the program
#              Tracking of best score for the session

my $lower_limit; #The lower limit (exclusive)
my $upper_limit; #The upper limit (exclusive)

my $input; #Store user input
my $target; #The target number (what the user is trying to guess)

my $count; #A count of the number of guess the user has made
my $best_count = 0; #The best score this session

my $guessed = false; #Flag to know when the number has been guessed
my $play_again = true; #Flag to allow the user to play multiple games


#Improvement: Allow the user to play multiple games
while($play_again)
{
   #Generate random number between 1 and 1000 (exclusive)
   $target = int(rand 998) + 2;

   #Setup the values
   $guessed = false;
   $lower_limit = 1;
   $upper_limit = 1000;
   $count = 0;

   #Start game
   while(!$guessed)
   {
      #Get the user to enter a guess
      print "Please enter an integer between $lower_limit and $upper_limit: ";
      chomp($input = <>);

      #Check for blank or invalid input
      if(($input eq "") || !($input =~ qr/^\d{0,}$/)) 
      {
         print "Please enter only positive integer numbers\n";
         next;
      }

      #Check if the guess is not between the limits
      if($input <= $lower_limit || $input >= $upper_limit)
      {
         print "Please stay between $lower_limit and $upper_limit (exclusive)\n";
         next;
      }
      
      #Note: count only incremented for valid guesses
      $count++;
 
      #Check if the guess was corrent
      if($input == $target)
      {
         print $count == 1 ? "Wow! you got it on the first try!\n" :
                             "Well done! You got it in $count tries.\n";         
         $guessed = true;
         next;
      }
      
      #Adjust the lower / upper limit         
      $input < $target ? $lower_limit = $input : $upper_limit = $input;
   }
   continue
   {
      #Preceding 'next;' statements jump to here
   }
   #End game

   #Improvement: Track best score this session
   if($best_count == 0 || $count < $best_count)
   {
      print $best_count == 0 ? "Now try to beat your score.\n" : 
                               " ** You beat your best score **\n";
      $best_count = $count;
   }
   else
   {
      print "The current best score is: $best_count\n";
   }

   #Play again?
   print "Would you like to play again (y/n)? ";
   chomp($input = <>);
   
   if(!($input =~ qr/y|yes|Y|YES/))
   {
      print "Thankyou for playing\n";
      $play_again = false;
   }
}#End program

