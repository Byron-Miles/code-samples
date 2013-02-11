//Date class definition, stores a date using a day, month and year

#ifndef DATE_H
#define DATE_H

class Date
{
  private:
    int m_iDay,
        m_iMonth,
        m_iYear;
	
  public:
    //Ctors
    Date(void);
    Date(int day, int month, int year);
    //Accessors
    int getDay(void);
    int getMonth(void);
    int getYear(void);
    //Mutators
    void setDay(int);
    void setMonth(int);
    void setYear(int);
    //Special Purpose
    void displayDate(void);
};
#endif

 