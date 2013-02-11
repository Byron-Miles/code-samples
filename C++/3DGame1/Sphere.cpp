#include "Sphere.h"
#include "Vector3d.h"

#include <iostream>

/**
 * A Plane class
 * Defines a plane as a point and a normal
 * Provides operations for planes
 **/

/**
 * Written by Byron Miles, 2/5/2011
 **/

//Constructors
//From a Vector and a float
Sphere::Sphere(const Vector3d & position, float radius) : 
   radius_(radius)
{
   pPosition_ = new Vector3d(position);
   pVelocity_ = new Vector3d(0, 0, 0);
   pColor_ = new Vector3d(0, 0, 0);
   active_ = false;
}
 
//From four floats (x, y, z, r)
Sphere::Sphere(float x, float y, float z, float radius) :
   radius_(radius)
{
   pPosition_ = new Vector3d(x, y, z);
   pVelocity_ = new Vector3d(0, 0, 0);
   pColor_ = new Vector3d(0, 0, 0);
   active_ = false;
}

//Destructor
Sphere::~Sphere()
{
   if(pPosition_ != NULL)
      delete pPosition_;
   if(pVelocity_ != NULL)
      delete pVelocity_;
   if(pColor_ != NULL)
      delete pColor_;
}

//Accessors
const Vector3d& Sphere::position() const
{
   return (*pPosition_);
}

const Vector3d& Sphere::velocity() const
{
   return (*pVelocity_);
}

const Vector3d& Sphere::color() const
{
   return (*pColor_);
}

float Sphere::radius() const
{
   return radius_;
}

bool Sphere::active() const
{
   return active_;
}


//Functions
//Initialise the sphere, generates a velocity and makes it active
void Sphere::init()
{
   //Generate random radius
   radius_ = rand() / (50.0f * RAND_MAX) + 0.05f; //0.05 to 0.10
   //A little bit extra stop in spawning on the wall
   float offRadius = radius_ + 0.0005f;

   //Generate random position, adjust for radius
   float p = rand() / float(RAND_MAX) - 0.5;
   if(p > 0.0f)
      p -= offRadius; 
   else
      p += offRadius;
   pPosition_->x = p;

   p = rand() / float(RAND_MAX) - 0.5;
   if(p > 0.0f)
      p -= offRadius;
   else
      p += offRadius;
   pPosition_->y = p;
  
   p = rand() / float(RAND_MAX) - 0.5;
   if(p > 0.0f)
      p -= offRadius;
   else
      p += offRadius;
   pPosition_->z = p;
 
   //Generate random velocity
   pVelocity_->x = rand() / float(RAND_MAX) - 0.5f; //-0.5f to 0.5f
   pVelocity_->y = rand() / float(RAND_MAX) - 0.5f;
   pVelocity_->z = rand() / float(RAND_MAX) - 0.5f;

   //Generate random color
   pColor_->x = random() / float(RAND_MAX);
   pColor_->y = random() / float(RAND_MAX);
   pColor_->z = random() / float(RAND_MAX);

   //Set sphere to active
   active_ = true;
}
     
//Updates the sphere, moves it according to velocity and time given
void Sphere::update(float delta)
{
   Vector3d move = (*pVelocity_) * delta;   
   (*pPosition_) += move;
}
     
//Resolves a collision between it and another sphere
void Sphere::resolveCollisionSphere()
{
   active_ = false;
}

//Resolves a collision between it and a plane
void Sphere::resolveCollisionPlane(const Vector3d& normal)
{
   //R = 2 * (-I . N) * N + I
   float spd = vectorMag((*pVelocity_)); //Save speed
   pVelocity_->normalise(); //Normalise

   //Calculate resultant vector
   float IdotN = 2 * dot(-(*pVelocity_), normal);
   Vector3d R = (normal * IdotN) + (*pVelocity_);
   R.normalise();

   //Update velocity vector
   pVelocity_->x = R.x * spd;
   pVelocity_->y = R.y * spd;
   pVelocity_->z = R.z * spd;
}

