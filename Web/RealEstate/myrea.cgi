#!/usr/bin/perl -T

use strict;
use warnings;
use CGI qw(:standard);


#Build houses hash
#Open houses directory
opendir(HOUSE_DIR, './houses') or die "No 'houses' directory\n";

#Read houses dir, and add entry for each folder
my %houses;
foreach (readdir(HOUSE_DIR))
{
   unless($_ =~ qr/^\./) #Don't read hidden folders / files
   {
      #Add anon hash reference under each house name
      $houses{$_} = {mainpic => $_."/Main.jpg",
                     description => $_."/Description.txt",
                     pictures => [$_."/Pic1.jpg", $_."/Pic2.jpg"]};
   }
}
closedir(HOUSE_DIR);



#Start of HTML section
#Headers etc.
print header(), start_html("Student First Real Estate");
print h1("Welcome!");
print h2("To Student First Real Estate");
print p("The first real estate for students");

#Page param and url, controls what is shown
my $page = param('page');
my $url = url(-relative=>1);

#Links
foreach (keys %houses)
{
   unless($_ eq $page)
   {
      print a({-href=>"$url?page=$_"}, "$_"), "#####";
   }
}

#Front page
unless($page)
{

   print table(Tr(td(img('Welcome.jpg')), 
                  td("To see a house click on its link")));
}

#House page
else 
{
   #Main image and description
   print table(th(td("$page")),
               Tr(td(img($houses{$page}{mainpic})),
                  td($houses{$page}{description})));

   #Additional images
   my $pictures;
   foreach (@{$houses{$page}{pictures}})
   {
      $pictures .= td(img($_));
   }
   print table(Tr($pictures));
}

#print a({-href=>"$url?page=3"}, "Page 3"), br();

#foreach my $name (param())
#{
#   my $value = param($name);
#   print p("The value of $name is $value");
#}

#my $value = param('page');
#print p("The page is $value");

#Print house info
#foreach my $house (keys %houses)
#{
 #  print "Main pic: ", $houses{$house}{mainpic}, "\n";
  # print "Descript: ", $houses{$house}{description}, "\n";
   #foreach my $pic (@{$houses{$house}{pictures}})
   #{
    #  print "Picture: ", $pic, "\n";
   #}
#}

#print end_html;

