#!/usr/bin/perl
use warnings;
use strict;
use DB_File; #overwrite default dbmopen, allows use of 'exists'

#Comp320 Assignment2 Question 3: dbmu.pl
#Byron Miles 220057347

#Updates exists entries in the dictionary.
#Warning: If the database file does not exist, it will be created.

my %data; #hash to tie to database
my $input; #hold user input

#check for correct command line arguments
if($#ARGV + 1 < 1)
{
   die "Usage: dbmu.pl dictionary\n";
}

#open the database
dbmopen(%data,"$ARGV[0]", 0644) 
  or die "Cannot open DBM: $!";

#delete words
print "Please enter a word to update (english):\n";
while($input = <STDIN>)
{
   #process input
   chomp($input);
   if(exists $data{$input})
   {
      print "Updateing entry: $input--->$data{$input}\n";
      print "Please enter its new Romanian equivalent:\n";
      chomp(my $update = <STDIN>);
      $data{$input} = $update;
   }
   else
   {
      print "No entry for $input\n";
   }
   #re-prompt
   print "Please enter a word to update (english):\n";
}
#close database
dbmclose(%data);

