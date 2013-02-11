#!/usr/bin/php
<?php
$i = 1;
while($line = fgets(STDIN))
{
  print "$i $line";
  $i++;
}
?>
