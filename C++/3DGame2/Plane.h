#ifndef PLANE_H
#define PLANE_H

#include <stdlib.h>

/**
 * A Plane class
 * Defines a plane as a point and a normal
 * Provides operations for planes
 **/

/**
 * Written by Byron Miles, 2/5/2011
 **/

class Vector3d;

class Plane
{
   private:
      Vector3d *pPosition_; //Center of the plane
      Vector3d *pNormal_; //Unit vector normal
      float d_; //Distance from origin

      Plane(const Plane &a) {} //Private copy constructor
      Plane &operator =(const Plane &a) {} //Private assignment operator

   public:
      //Constructors
      //Empty default
      Plane() : pPosition_(NULL), pNormal_(NULL), d_(0) {}
      //From two vectors
      Plane(const Vector3d &position, const Vector3d &normal); 
      //From six floats
      Plane(float ox, float oy, float oz, float nx, float ny, float nz);

      //Destructor
      ~Plane();

      //Accessors
      const Vector3d& position() const;
      const Vector3d& normal() const;
      float d() const;

      //Functions
      //The distance from a point to the plane
      float distance(const Vector3d &p);
};//End class Plane

#endif

