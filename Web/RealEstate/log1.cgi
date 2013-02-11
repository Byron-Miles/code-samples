#!/usr/bin/perl
use warnings;
use strict;
use Mylog;

#Comp320 Assignment2 Question 2: log1.cgi
#Byron Miles 220057347

#Dsplays a basic webpage and uses writeLogEntry (from Mylog.pm)
#to create an log entry every time the page is visited.

#Call writeLogEntry subroutine
writeLogEntry();

#Basic web page, displays a simple message
print <<END_HTML;
Content-type: text/html\n\n
<html>
<head>
<title>Packaged Log File Test</title>
</head>
<body bgcolor=white>
<h1>Salutations Test Subject</h1>
<p>Did you know this webpage is watching you? It's like a cat, so harmless
 and innocent.</p>
<p>Have an enjoyable day.</p>
</body>
</html>
END_HTML

