#!/usr/bin/perl
use warnings;
use strict;

#Comp320 Assignment2 Question 5: arrofhash.pl
#Byron Miles 220057347

#Reads a file, compsci, and builds an array of hashes to show the results
#of various queries

#open compsci
open(COMPSCI, "<compsci")
  or die "Couldn't open file 'compsci'";
chomp (my @compsci = <COMPSCI>);

#Empty array reference
my $unitList = [];

#Build unit list
foreach (@compsci)
{

   my @unit = split /, {0,}/;
   push @$unitList, {
                      name => $unit[0],
                      unit => $unit[1],
                      num => $unit[2],
                    };
}

#Queries
#Lecturer and unit from 2nd line of file
print "$unitList->[1]->{name}\n";
print "$unitList->[1]->{unit}\n";

#All Units (and lecturer teaching them), expect those taught by neil
foreach(@$unitList)
{
  unless($_->{name} =~ qr/neil/)
  {
     print "$_->{name}, $_->{unit}\n";
  }
}

#All units and enrollments, sorted ascending by enrollments
foreach(sort {$a->{num} <=> $b->{num}} @$unitList)
{
   print "$_->{unit} - $_->{num}\n";
}

