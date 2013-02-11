//test program for Date class

#include <iostream>
#include "Date.h"

int main()
{
  Date d1;
  Date d2(31,12,2009);
  
  std::cout << "The first date is: ";
  d1.displayDate();
  std::cout << std::endl;
  
  std::cout << "The second date is: ";
  d2.displayDate();
  std::cout << std::endl;
  
  int day, month, year;
  
  std::cout << "Please enter todays date (d m y): ";
  std::cin >> day >> month >> year;
  
  d1.setDay(day);
  d1.setMonth(month);
  d1.setYear(year);
  
  std::cout << "The first date is now: ";
  d1.displayDate();
  std::cout << std::endl;
  
  std::cout << "Have a nice " << d1.getDay() << " day, of the " << d1.getMonth() 
            << " month, in the year " << d1.getYear() << " :)" << std::endl;
}
