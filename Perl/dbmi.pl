#!/usr/bin/perl
use warnings;
use strict;
use DB_File; #overwrite default dbmopen, allows use of 'exists'

#Comp320 Assignment2 Question 3: dbmi.pl
#Byron Miles 220057347

#Creates / Opens a dictionary database and inserts new entries.
#Note: Existing entries for the same key will be overwriten

my %data; #hash to tie to database
my $input; #hold user input

#check for correct command line arguments
if($#ARGV + 1 < 1)
{
   die "Usage: dbmi.pl dictionary\n";
}

#open / create the database
dbmopen(%data,"$ARGV[0]", 0644) 
  or die "Cannot open DBM:$!";

#insert words
print "Please enter english=romanian:\n";
while($input = <STDIN>)
{
   #process input
   chomp($input);
   my @entry = split(/=/, $input);
   #add entry to dictionary
   $data{$entry[0]} = $entry[1];
   #re-prompt
   print "Please enter english=romanian:\n"; 
}
#close database
dbmclose(%data);

