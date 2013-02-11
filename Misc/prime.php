#!/usr/bin/php
<?php

for ($i = 0; $i < 100; $i++) 
{
  if(isPrime($i)) 
  {
    echo "$i is prime\n";
  }
}

/**
 * returns true if a number is prime, otherwise false
 * Note: 0 and 1 are not considered prime for reasons to complex to explain here
 **/
function isPrime($number) 
{
  //Special cases of 0, 1 and 2
  //Fix: num != 2 && num % 2 does equal zero, ie. if num is divisible by 2
  if( ($number != 2 && $number % 2 == 0) || $number == 1)
  {
    //Fix: return false instead of true
    return false; 
  }

  //The rest
  /**List of fixes:
   * 1: $1 wasn't being incremented, thus the infinite loop
   * 2: what was with the if(($i & 1) == 0)... it just makes no sense, I'm
        guessing it was a test to not devided by 0.. but yeah
   * 3: Counting down instead of up, it's just a bit easier that way
   * 4: started $i at floor($number / 2), as anything greater is not going to go
        evenly into $number, also lets 2 through as 2/2 = 1.
  **/
  for($i = floor($number / 2); $i > 1; $i--)
  {
    if(($number % $i) == 0 ) 
    {
      return false;
    }
  }
  return true;
}
?>