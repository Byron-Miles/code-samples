//Complex.cpp

#include "Complex.h"

//Constructor
Complex::Complex(double real, double imaginary)
   : m_dReal(real),
   m_dImaginary(imaginary)
{}

//Insertion operator
std::ostream & operator<<(std::ostream &os, const Complex &a)
{
   return os << '(' << a.m_dReal << ',' << a.m_dImaginary << ')';
}

//Extraction
std::istream & operator>>(std::istream &is, Complex &a)
{
   is.ignore(); // (
   is >> a.m_dReal; //real
   is.ignore(); // ,
   is >> a.m_dImaginary; //imaginary
   is.ignore(); // )
   return is;
}

//Addition operator
Complex Complex::operator+(const Complex &a) const
{
   return Complex(m_dReal + a.m_dReal,
                  m_dImaginary + a.m_dImaginary);
}

//Subtraction operator
Complex Complex::operator-(const Complex &a) const
{
   return Complex(m_dReal - a.m_dReal,
                  m_dImaginary - a.m_dImaginary);
}

//Multiplication operator
Complex Complex::operator*(const Complex &a) const
{
   return Complex(m_dReal * a.m_dReal - m_dImaginary * a.m_dImaginary, //r = r*r - i*i
                  m_dReal * a.m_dImaginary + m_dImaginary * a.m_dReal); //i = r*i + i*r
}

//Equality operator
bool Complex::operator==(const Complex &a) const
{
   return (m_dReal == a.m_dReal && m_dImaginary == a.m_dImaginary);
}

//Inequality operator
bool Complex::operator!=(const Complex &a) const
{
   return !(m_dReal == a.m_dReal && m_dImaginary == a.m_dImaginary);
}

