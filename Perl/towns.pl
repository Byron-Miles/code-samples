#!/usr/bin/perl
use strict;
use warnings;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 5
#Reads a file containing countries and towns and makes a list

#Note: The files are assumed to be in the correct format

use constant town => 0;
use constant country => 1;

my %list;

while(<>)
{
   chomp(my @listing = split/, {0,}/);

   #Check if there is a listing for the country
   if(exists($list{$listing[country]}))
   {
      push(@{$list{$listing[country]}}, $listing[town]);
   }
   else
   {
      $list{$listing[country]} = [$listing[town]];
   }
}

#Print catalog in asciibetical order, by country then town
foreach(sort(my @country = keys %list))
{
   print "$_: ", join(' ',sort(my @towns = @{$list{$_}})), " \n";
}

