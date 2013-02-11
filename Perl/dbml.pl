#!/usr/bin/perl
use warnings;
use strict;
use DB_File; #overwrite default dbmopen, allows use of 'exists'

#Comp320 Assignment2 Question 3: dbml.pl
#Byron Miles 220057347

#Prints an entry from the dictionary.
#Warning: If the database file does not exist, it will be created.

my %data; #hash to tie to database

#check for correct command line arguments
if($#ARGV + 1 < 2)
{
   die "Usage: dbmu.pl dictionary word ...\n";
}

#open the database
dbmopen(%data,"$ARGV[0]", 0644) 
  or die "Cannot open DBM:$!";

#print the entry for each word given
for(my $i = 1; $i <= $#ARGV; ++$i)
{
   if(exists $data{$ARGV[$i]})
   {
      print "$ARGV[$i]--->$data{$ARGV[$i]}\n";
   }
   else
   {
      print "No entry for $ARGV[$i]\n";
   }
}

#close database
dbmclose(%data);

