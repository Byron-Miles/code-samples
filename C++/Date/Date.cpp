//Definition of Date class functions

//Preprocessor
#include <iostream>
#include "Date.h"

//Ctors
Date::Date(void)
{
  m_iDay = 1;
  m_iMonth = 1;
  m_iYear = 2000;
}

Date::Date(int day, int month, int year)
{
  m_iDay = day;
  m_iMonth = month;
  m_iYear = year;
  
  //validation check
  if(m_iDay > 31 || m_iDay < 1)
    m_iDay = 1;
  
  if(m_iMonth > 12 || m_iMonth < 1)
    m_iMonth = 1;
  
  if(m_iYear < 0)
    m_iYear = 0;
}

//Accessors
int Date::getDay(void)
{
  return m_iDay;
}

int Date::getMonth(void)
{
  return m_iMonth;
}

int Date::getYear(void)
{
  return m_iYear;
}

//Mutators
void Date::setDay(int day)
{
  m_iDay = day;
  
  if(m_iDay > 31 || m_iDay < 1)
    m_iDay = 1;
}

void Date::setMonth(int month)
{
  m_iMonth = month;
  
  if(m_iMonth > 12 || m_iMonth < 1)
    m_iMonth = 1;
}

void Date::setYear(int year)
{
  m_iYear = year;
  
  if(m_iYear < 0)
    m_iYear = 0;
}

//Special Purpose
void Date::displayDate(void)
{
  std::cout << m_iDay << "/" << m_iMonth << "/" << m_iYear;
}
