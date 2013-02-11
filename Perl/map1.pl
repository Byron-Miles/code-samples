#!/usr/bin/perl
use strict;
use warnings;

#Comp315 Assignment1 
#Byron Miles 220057347
#Question 3
#Uses the map function in various contexts to produce output


my @input_numbers = (1, 1, 3, 5, 8, 13, 21, 34, 55, 89, 144);

#map a list of the input number plus 100
my @plus100 = map($_ + 100, @input_numbers);
print "@plus100\n";

#map a hash with keys = input numbers, value = input numbers squared
my %squares = map {$_ => $_ ** 2} @input_numbers;
for $_ (keys %squares)
{
  print $_ . " => " . $squares{$_} . "\n";
}
  
#map a list of the digits of input numbers ending in 4 eg. 34 => (3 4)
my @endsIn4 = map($_ =~ qr/\d{0,}4/ ? split//,$_ : ( ), @input_numbers);
print "@endsIn4\n";

