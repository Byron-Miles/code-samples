#include "Quaternion.h"
#include "Vector3d.h"
#include <math.h> //Needed for sin, cos, acos, sqrt


/**
 * Written by Byron Miles, 31/5/2011
 * Inspired by Quaternion.cpp, page 207-215 '3D Math Primer', Dunn and Parberry, 2002
 **/

//Rotations
void Quaternion::setToRotateAboutX(float theta)
{
   float thetaOver2 = theta * 0.5f;
   w = cos(thetaOver2);
   x = sin(thetaOver2);
   y = 0.0f;
   z = 0.0f;
}

void Quaternion::setToRotateAboutY(float theta)
{
   float thetaOver2 = theta * 0.5f;
   w = cos(thetaOver2);
   x = 0.0f;
   y = sin(thetaOver2);
   z = 0.0f;
}

void Quaternion::setToRotateAboutZ(float theta)
{
   float thetaOver2 = theta * 0.5f;
   w = cos(thetaOver2);
   x = 0.0f;
   y = 0.0f;
   z = sin(thetaOver2);
}

void Quaternion::setToRotateAboutAxis(const Vector3d& axis, float theta)
{
   float thetaOver2 = theta * 0.5f;
   float sinThetaOver2 = sin(thetaOver2);
   w = cos(thetaOver2);
   x = axis.x * sinThetaOver2; 
   y = axis.y * sinThetaOver2; 
   z = axis.z * sinThetaOver2; 
}

void Quaternion::normalise()
{
   float mag = (float)sqrt(w*w + x*x + y*y + z*z);
   if(mag > 0.0f)
   {
      float oneOverMag = 1.0f;
      w *= oneOverMag;
      x *= oneOverMag;
      y *= oneOverMag;
      z *= oneOverMag;
   }
   else
   {
      //Problem...
      w = 1.0f;
      x = 0.0f;
      y = 0.0f;
      z = 0.0f;
   }
}

Quaternion Quaternion::operator *(const Quaternion &a) const
{
   Quaternion result;
   result.w = w*a.w - x*a.x - y*a.y - z*a.z;
   result.x = w*a.x + x*a.w + z*a.y - y*a.z;
   result.y = w*a.y + y*a.w + x*a.z - z*a.x;
   result.z = w*a.z + z*a.w + y*a.x - x*a.y;

   return result;
}

Quaternion& Quaternion::operator *=(const Quaternion &a)
{
   *this = *this * a;
   return *this;
}

float Quaternion::getRotationAngle() const
{
   float thetaOver2 = acos(w);
   return thetaOver2 * 2.0f;
}

Vector3d Quaternion::getRotationAxis() const
{
   float sinThetaOver2Sq = 1.0f - w*w;
   if(sinThetaOver2Sq <= 0.0f)
      return Vector3d(1.0f, 0.0f, 0.0f);
  
   float oneOverSinThetaOver2 = 1.0f / sqrt(sinThetaOver2Sq);
   return Vector3d(
      x * oneOverSinThetaOver2,
      y * oneOverSinThetaOver2,
      z * oneOverSinThetaOver2
   );
}

