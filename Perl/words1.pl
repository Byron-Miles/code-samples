#!/usr/bin/perl
use strict;
use warnings;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 2.1
#Reads a line and prints the number of words in the line

my @words;

print "Enter a sentance:\n";
$_ = <STDIN>;
@words = split;
print("Contains " . @words . " word" . (@words == 1 ? "" : "s") . "..\n");

