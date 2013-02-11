#include "Plane.h"
#include "Vector3d.h"

/**
 * A Plane class
 * Defines a plane as a point and a normal
 * Where p . n = d
 **/

/**
 * Written by Byron Miles, 2/5/2011
 **/

//Constructors
//From two vectors
Plane::Plane(const Vector3d &position, const Vector3d &normal)
{ 
   pPosition_ = new Vector3d(position);
   pNormal_ = new Vector3d(normal);
   pNormal_->normalise(); //Ensure it is a unit vector
   d_ = dot((*pPosition_), (*pNormal_));
}

//From six floats
Plane::Plane(float px, float py, float pz, float nx, float ny, float nz)
{
   pPosition_ = new Vector3d(px, py, pz);
   pNormal_ = new Vector3d(nx, ny, nz);
   pNormal_->normalise(); //Ensure it is a unit vector
   d_ = dot((*pPosition_), (*pNormal_));
}

//Destructor
Plane::~Plane()
{
   if(pPosition_ != NULL)
      delete pPosition_;
   if(pNormal_ != NULL)
      delete pNormal_;
}

//Accessors
const Vector3d& Plane::position() const
{
   return (*pPosition_);
}

const Vector3d& Plane::normal() const
{
   return (*pNormal_);
}
      
float Plane::d() const
{
   return d_;
}

float Plane::distance(const Vector3d &p)
{
   //dist = p . n - d; as n is a unit vector
   return dot(p, (*pNormal_)) - d_;
}

