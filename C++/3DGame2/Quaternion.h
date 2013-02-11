#ifndef QUATERNION_H
#define QUATERNION_H

/**
 * A Quaternion class
 * Defines a quaternion as w, x, y, and z values
 * Provides operations on quaternions
 **/

/**
 * Written by Byron Miles, 31/5/2011
 * Inspired by Quaternion.h, page 205-206 '3D Math Primer', Dunn and Parberry, 2002
 **/

class Vector3d;

class Quaternion
{
   public:
      float w, x, y, z; //Left public to make certain things (I/O) easier

   //Operations
   //Set to Identity
   void identity() { w = 1.0f; x, y, z = 0.0f; }
   //Set the quaternion to a specific rotation, all use radians
   void setToRotateAboutX(float theta);
   void setToRotateAboutY(float theta);
   void setToRotateAboutZ(float theta);
   //Vector must be normalised, please ensure it is
   void setToRotateAboutAxis(const Vector3d& axis, float theta);
   //Normalise the quaternion, to stave off floating point error creep
   void normalise();

   //Cross Product
   Quaternion operator *(const Quaternion &a) const;
   Quaternion& operator *=(const Quaternion &a);

   //Extract and return the rotation angle and axis
   float getRotationAngle() const; //As a radian
   Vector3d getRotationAxis() const;
};

#endif
 
