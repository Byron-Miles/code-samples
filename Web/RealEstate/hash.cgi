#!/usr/bin/perl -T

use strict;
use warnings;

my %houses = ( house1 => { mainpic => "MainPcture.jpg",
                           description => "Very nice house",
                           pictures => ["pic1", "pic2", "pic3"]},
               house2 => { mainpic => "MyPic.gif",
                           description => "Ok house",
                           pictures => ["pic4", "pic5"]});

foreach my $house (keys %houses)
{
   print "Main pic: ", $houses{$house}{mainpic}, "\n";
   print "Descript: ", $houses{$house}{description}, "\n";
   foreach my $pic (@{$houses{$house}{pictures}})
   {
      print "Picture: ", $pic, "\n";
   }
}

                         
