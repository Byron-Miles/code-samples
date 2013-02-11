#!/usr/bin/perl
use strict;
use warnings;

my %hewo = (saying => ["hello", "world"]);

$hewo{bob} = ["ii", "net"];

foreach $_ (keys %hewo)
{
   print @{$hewo{$_}};
}

