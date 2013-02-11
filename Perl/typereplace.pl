#!/usr/bin/perl
use warnings;
use strict;

#Comp320 Assignment2 Question 4: typereplace.pl
#Byron Miles 220057347

#Replaces the type for files in the current directory

#Get the list of files in the current directory, sorted by extension
chomp( my @files = `ls -X -1` );

#Print results
print "Files in the current directory:\n";
print "************************************************************";
for(my $i = 0; $i <= $#files; ++$i)
{
   if($i % 5 == 0) { print("\n"); } #five files per line
   print $files[$i] . "  ";
}
print "\n************************************************************\n";

#Get old and new file type
print "Please enter old file type: \n";
chomp( my $old = <STDIN> );
print "Please enter new file type: \n";
chomp( my $new = <STDIN> );

#rename the files
@files = `rename -v s/.$old/.$new/ *.$old`;

#Print results
print "Type replacement $old--->$new made to the following files:\n";
foreach (@files)
{
   my @replaced = split;
   print "$replaced[0]--->$replaced[3]\n";
}

