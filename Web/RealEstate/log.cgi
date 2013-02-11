#!/usr/bin/perl
use warnings;
use strict;

#Comp320 Assignment2 Question 1: log.cgi
#Byron Miles 220057347

#Displays a basic webpage and creates a log entry in 'cgi.log'
#every time the page is visited.

sub writeLogEntry
{
   #Initialise log file name
   my $log = "cgi.log";

   #Initialse script file name
   my $script = $0;

   #Create local versions of the enviroment variables;
   my $host = $ENV{HTTP_HOST}; #Server hosting the page
   my $addr = $ENV{REMOTE_ADDR}; #Visitors IP address
   my $agent = $ENV{HTTP_USER_AGENT}; #Visitors browser info
   my $time = localtime; #Current local time

   #Open log file in append mode
   open(LOG, ">>$log")
     or die "Couldn't open $log: $!";

   #Output variable to log using *!* as the delimiter
   print LOG "$script *!* $host *!* $addr *!* $agent *!* $time\n";
  
   #Close the log file
   close(LOG);
}

#Call writeLogEntry subroutine
&writeLogEntry;

#Basic web page, displays a simple message
print <<END_HTML;
Content-type: text/html\n\n
<html>
<head>
<title>Log File Test</title>
</head>
<body bgcolor=white>
<h1>Greetings Test Subject</h1>
<p>Your visit to this lovely piece of cyber space has been logged.</p>
<p>Have wonderful day.</p>
</body>
</html>
END_HTML

