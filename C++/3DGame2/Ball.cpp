#include "Ball.h"
#include "Vector3d.h"
#include "Constants.h"
#include "Quaternion.h"

#include <iostream>

/**
 * The Ball class
 * Implements the Ball class definition
 **/

/**
 * Written by Byron Miles, 23/5/2011
 **/

//Constructor
Ball::Ball(float x, float y, float z, float radius) : 
   radius_(radius)
{
   circumference_ = 2.0f * PI * radius_;
   pPosition_ = new Vector3d(x, y, z);
   pVelocity_ = new Vector3d(0.0f, 0.0f, 0.0f);
   pRotation_ = new Quaternion();
}
 
//Destructor
Ball::~Ball()
{
   delete pPosition_;
   delete pVelocity_;
   delete pRotation_;
}

//Accessors
const Vector3d& Ball::position() const
{
   return (*pPosition_);
}

const Vector3d& Ball::velocity() const
{
   return (*pVelocity_);
}

float Ball::radius() const
{
   return radius_;
}

const Quaternion& Ball::rotation() const
{
   return (*pRotation_);
}

//Movement
void Ball::update(float delta)
{
   Vector3d move = (*pVelocity_) * delta;   
   (*pPosition_) += move;
  
   pRotation_->setToRotateAboutAxis(unit(move), 
                  vectorMag(move / circumference_ * 360.0f) * DTR); 
}
     
void Ball::updateVelocity(float x, float y, float z, float delta)
{
   //Calculate friction
   float mag = vectorMag(*pVelocity_);
   if(mag != 0.0f)
   {
      //Apply friction
      Vector3d friction = (*pVelocity_) / mag * -COF;
      (*pVelocity_) += friction * delta;
   }

   //Calculate the acceleration from the angles
   Vector3d acceleration = Vector3d(G*sin(x*DTR), G*sin(y*DTR), G*sin(z*DTR)); 
   //Increase the velocity by acceleration over time
   (*pVelocity_) += acceleration * delta;
}

void Ball::move(const Vector3d &direction, float scale)
{
   pPosition_->x += direction.x * scale;
   pPosition_->y += direction.y * scale;
   pPosition_->z += direction.z * scale;
} 

//Collision Resolution
void Ball::resolveCollisionPoint(const Vector3d &point)
{
   //Get the vector from the point to the ball and normalise
   Vector3d normal = Vector3d((*pPosition_) - point);
   normal.normalise();
   //Reslove the collision as if the ball hit a plane with that normal
   resolveCollisionPlane(normal);
}

void Ball::resolveCollisionPlane(const Vector3d &normal)
{
   //R = 2 * (-I . N) * N + I
   float spd = vectorMag((*pVelocity_)); //Save speed
   pVelocity_->normalise(); //Normalise

   //Calculate resultant vector, losing velocity in the process
   float IdotN = COR * dot(-(*pVelocity_), normal);
   Vector3d R = (normal * IdotN) + (*pVelocity_);

   //Update velocity vector
   pVelocity_->x = R.x * spd;
   pVelocity_->y = R.y * spd;
   pVelocity_->z = R.z * spd;
}
     
