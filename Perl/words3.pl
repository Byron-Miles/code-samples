#!/usr/bin/perl
use strict;
use warnings;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 2.3
#Reads multiple lines and prints the number of words in each line

my @words;

print "Enter sentances, quit with crtl+d:\n";
while(<>) 
{
   @words = split;
   print("Contains " . @words . " word" . (@words == 1 ? "" : "s") . "..\n");
}

