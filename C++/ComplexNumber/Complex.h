//Complex.h

#ifndef COMPLEX_H
#define COMPLEX_H

#include <iostream>

class Complex
{
   friend std::ostream & operator<<(std::ostream &, const Complex &); //insertion
   friend std::istream & operator>>(std::istream &, Complex &); //extraction
   
   public:
      Complex(double = 0.0, double = 0.0); //constructor
      //Math operators
      Complex operator+(const Complex &) const; //addition
      Complex operator-(const Complex &) const; //subtraction
      Complex operator*(const Complex &) const; //multiplication
      //Equality operators
      bool operator==(const Complex &) const;
      bool operator!=(const Complex &) const;
      
   private:
      double m_dReal; //real part
      double m_dImaginary; //imaginary part

}; //end of Class Complex

#endif
