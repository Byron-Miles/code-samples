#!/usr/bin/perl
use warnings;
use strict;
use DB_File; #overwrite default dbmopen, allows use of 'exists'

#Comp320 Assignment2 Question 3: dbmp.pl
#Byron Miles 220057347

#Prints the entire contents of the dictionary, sorted by english words.
#Warning: If the database file does not exist, it will be created.

my %data; #hash to tie to database

#check for correct command line arguments
if($#ARGV + 1 < 1)
{
   die "Usage: dbmp.pl dictionary\n";
}

#open the database
dbmopen(%data,"$ARGV[0]", 0644) 
  or die "Cannot open DBM:$!";

#print each entry
foreach(sort(keys %data))
{
   print "$_--->$data{$_}\n";
}

#close database
dbmclose(%data);

