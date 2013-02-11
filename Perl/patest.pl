#!/usr/bin/perl

use warnings;
use strict;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 6
#For testing home grown patterns!! 
#Modified so you can test multiple strings a pattern

#Note: Leave the string and pattern blank to exit (i.e. [Enter] twice)

my ( $pattern, $string );

#Get a pattern
print "Enter a pattern: ";
chomp($pattern = <STDIN>);

do
{
   do
   {
      #Get a string
      print "Enter a string to match /$pattern/ : ";
      chomp($string = <STDIN>);
      print $string =~ qr/$pattern/ ? "We have a match!\n" : "Nope!\n";
   }while($string);
  
#Get a new pattern
print "Enter a new pattern: ";
chomp($pattern = <STDIN>);
}while($pattern);

