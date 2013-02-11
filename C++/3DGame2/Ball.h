#ifndef BALL_H
#define BALL_H

/**
 * The Ball class
 * Defines a ball as a point and a radius with a velocity
 * Provides operations for movement and collision resolution
 **/

/**
 * Written by Byron Miles, 23/5/2011
 **/

class Vector3d;
class Quaternion;

class Ball
{
   private:
      Vector3d *pPosition_; //Center of the ball
      Vector3d *pVelocity_; //Current velocity
      float radius_; //Radius of the ball
      Quaternion *pRotation_; //Holds the roll of the ball
      float circumference_; //2 * PI * radius_

      Ball(const Ball &a) {} //Private copy constructor
      Ball &operator =(const Ball &a) {} //Private assignment operator

   public:
      //Constructors
      //Empty Default
      Ball() : pPosition_(0), pVelocity_(0), radius_(0), pRotation_(0), 
               circumference_(0) {}
      //Position the ball and give it a radius
      Ball(float x, float y, float z, float radius);

      //Destructor
      ~Ball();

      //Accessors
      const Vector3d& position() const;
      const Vector3d& velocity() const;
      float radius() const;
      const Quaternion& rotation() const;

      //Movement
      //Updates the sphere, moves it according to velocity and time given
      void update(float delta);
      //Updates the velocity of the ball based on angles, scaled by delta
      void updateVelocity(float x, float y, float z, float delta);
      //Moves the ball in the direction of the vector * scale amount
      void move(const Vector3d &direction, float scale);

      //CollisionResolution
      //Resolves a collision between the ball and a point
      void resolveCollisionPoint(const Vector3d &point);
      //Resolves a collision between the ball and a plane
      void resolveCollisionPlane(const Vector3d &normal);      
};

#endif

