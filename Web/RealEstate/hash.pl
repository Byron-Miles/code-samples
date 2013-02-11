#!/usr/bin/perl -T

use strict;
use warnings;

opendir(HOUSE_DIR, './houses') or die "No houses directory\n";

my %houses;

foreach (readdir(HOUSE_DIR))
{
   unless($_ =~ qr/^\./)
   {
      print $_."\n";
      $houses{$_} = {mainpic => $_."/Main.jpg",
                     description => $_."/Description.txt",
                     pictures => [$_."/Pic1.jpg", $_."/Pic2.jpg"]};
   }
}
               
 #= ( house1 => { mainpic => "MainPcture.jpg",
  #                         description => "Very nice house",
   #                        pictures => ["pic1", "pic2", "pic3"]},
    #           house2 => { mainpic => "MyPic.gif",
     #                      description => "Ok house",
      #                     pictures => ["pic4", "pic5"]});

closedir(HOUSE_DIR);

foreach my $house (keys %houses)
{
   print "Main pic: ", $houses{$house}{mainpic}, "\n";
   print "Descript: ", $houses{$house}{description}, "\n";
   foreach my $pic (@{$houses{$house}{pictures}})
   {
      print "Picture: ", $pic, "\n";
   }
}

                         
