#include "Vector3d.h"

/**
 * A 3D Vector class
 * Defines a vector as x, y, and z values
 * Provides operations on vectors
 **/

/**
 * Written by Byron Miles, 9/4/2011
 * Inspired by Vector3.h, page 70-72 '3D Math Primer', Dunn and Parberry, 2002
 **/


//Nonmember functions
//Dot product
float dot(const Vector3d &a, const Vector3d &b)
{
   return a.x*b.x + a.y*b.y + a.z*b.z;
}

//Cross product
Vector3d cross(const Vector3d &a, const Vector3d &b)
{
   return Vector3d(
         a.y*b.z - a.z*b.y,
         a.z*b.x - a.x*b.z,
         a.x*b.y - a.y*b.x
   );
}

//Magnitude
float vectorMag(const Vector3d &a)
{
   return sqrt(a.x*a.x + a.y*a.y + a.z*a.z);
}

//Magnitude squared, saves the sqrt if you don't need it
float vectorMagSq(const Vector3d &a)
{
   return a.x*a.x + a.y*a.y + a.z*a.z;
}

//Distance between two points
float distance(const Vector3d &a, const Vector3d &b)
{
   float dx = a.x - b.x;
   float dy = a.y - b.y;
   float dz = a.z - b.z;
   return sqrt(dx*dx + dy*dy + dz*dz);
}

//Distance between two points squard, saves the sqrt if you don't need it
float distanceSq(const Vector3d &a, const Vector3d &b)
{
   float dx = a.x - b.x;
   float dy = a.y - b.y;
   float dz = a.z - b.z;
   return dx*dx + dy*dy + dz*dz;
}

//The resultant unit vector from given vector
Vector3d unit(const Vector3d &a)
{     
   float magSq = a.x*a.x + a.y*a.y + a.z*a.z;
   //Check for devide by zero
   if(magSq != 0.0f)
   {
      float oneOverMag = 1.0f / sqrt(magSq);
      float x = a.x * oneOverMag;
      float y = a.y * oneOverMag;
      float z = a.z * oneOverMag;
      return Vector3d(x, y, z);
   }
   return Vector3d(0.0f,0.0f,0.0f);
}

