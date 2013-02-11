#!/usr/bin/perl -T

use strict;
use warnings;

opendir(DIR, './houses') or die "No houses dir?\n";

print join("\t", sort(readdir(DIR)));
closedir(DIR);

