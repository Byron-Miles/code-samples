#ifndef VECTOR3D_H
#define VECTOR3D_H

#include <math.h> //For sqrt()

/**
 * A 3D Vector class
 * Defines a vector as x, y, and z values
 * Provides operations on vectors
 **/

/**
 * Written by Byron Miles, 9/4/2011
 * Inspired by Vector3.h, page 70-72 '3D Math Primer', Dunn and Parberry, 2002
 **/

class Vector3d
{
   public:
      //Member variables
      float x, y, z; //Nothing gained by making them private, kept names simple


      //Constructors
      Vector3d() {} //Empty Default
      Vector3d(const Vector3d &a) : x(a.x), y(a.y), z(a.z) {} //Copy
      Vector3d(float nx, float ny, float nz) : x(nx), y(ny), z(nz) {} //Given values


      //General Operators
      //Assignment
      Vector3d &operator =(const Vector3d &a)
      {
         x = a.x; y = a.y; z = a.z;
         return *this;
      }

      //Equal (v==a)
      bool operator ==(const Vector3d &a) const
      {
         return x==a.x && y==a.y && z==a.z;
      }

      //Not equal (v!=a)
      bool operator !=(const Vector3d &a) const
      {
         return x!=a.x || y!=a.y || z!=a.z;
      }
      

      //+ - * / Operators
      //Unary minus(-v), negate the vector
      Vector3d operator -() const
      {
         return Vector3d(-x, -y, -z);
      }

      //Binary minus(v-a), subtract two vectors
      Vector3d operator -(const Vector3d &a) const
      {
         return Vector3d(x - a.x, y - a.y, z - a.z);
      }
      
      //Binary plus(v+a), add two vectors
      Vector3d operator +(const Vector3d &a) const
      {
         return Vector3d(x + a.x, y + a.y, z + a.z);
      }

      //Multiplication by a scalar (v*s)
      Vector3d operator *(float s) const
      {
         return Vector3d(x*s, y*s, z*s);
      }

      //Devision by a scalar (v/s)
      Vector3d operator /(float s) const
      {
         //Note: Does NOT check for devide by zero
         float oneOverS = 1.0f / s;
         return Vector3d(x*oneOverS, y*oneOverS, z*oneOverS);
      }


      //Combined Assignment Operators
      //Plus equals (v+=a)
      Vector3d &operator +=(const Vector3d &a)
      {
         x += a.x; y += a.y; z += a.z;
         return *this;
      }

      //Minus equals (v-=a)
      Vector3d &operator -=(const Vector3d &a)
      {
         x -= a.x; y -= a.y; z -= a.z;
         return *this;
      }

      //Times equals a scalar (v*=s)
      Vector3d &operator *=(float s)
      {
         x *= s; y *= s; z *= s;
         return *this;
      }

      //Devide equals a scalar (v/=s)
      Vector3d &operator /=(float s)
      {
         //Note: Does NOT check for devide by zero
         float oneOverS = 1.0f / s;
         x *= oneOverS; y *= oneOverS; z *= oneOverS;
         return *this;
      }


      //Member functions
      //Set to zero vector
      void zero() 
      {
        x = y = z = 0.0f;
      }

      //Normalise the vector
      void normalise()
      {
         float magSq = x*x + y*y + z*z;
         //Check for devide by zero
         if(magSq != 0.0f && magSq != 1.0f)
         {
            float oneOverMag = 1.0f / sqrt(magSq);
            x *= oneOverMag;
            y *= oneOverMag;
            z *= oneOverMag;
         }
      }
};//End of Vector3d class


//Nonmember functions
//Dot product
float dot(const Vector3d &a, const Vector3d &b);

//Cross product
Vector3d cross(const Vector3d &a, const Vector3d &b);

//Magnitude
float vectorMag(const Vector3d &a);

//Magnitude squared, saves the sqrt if you don't need it
float vectorMagSq(const Vector3d &a);

//Distance between two points
float distance(const Vector3d &a, const Vector3d &b);

//Distance between two points squard, saves the sqrt if you don't need it
float distanceSq(const Vector3d &a, const Vector3d &b);

//The resultant unit vector from given vector
Vector3d unit(const Vector3d &a);

#endif

