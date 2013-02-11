#ifndef SPHERE_H
#define SPHERE_H

#include <stdlib.h>

/**
 * A Sphere class
 * Defines a sphere as a point and a radius
 * Provides operations for spheres
 **/

/**
 * Written by Byron Miles, 2/5/2011
 **/

class Vector3d;

class Sphere
{
   private:
      Vector3d *pPosition_; //Center of the sphere
      Vector3d *pVelocity_; //Current velocity
      Vector3d *pColor_; //Gives the sphere a color
      float radius_; //Radius of the sphere
      bool active_; //If the sphere is active or not
   
      Sphere(const Sphere &a) {} //Private copy constructor
      Sphere &operator =(const Sphere &a) {} //Private assignmet operator

   public:
      //Constructors
      //Empty Default
      Sphere() : pPosition_(NULL), radius_(0) {}
      //From a Vector and a float
      Sphere(const Vector3d & position, float radius);
      //From four floats (x, y, z, r)
      Sphere(float x, float y, float z, float radius);

      //Destructor
      ~Sphere();

      //Accessors
      const Vector3d& position() const;
      const Vector3d& velocity() const;
      const Vector3d& color() const;
      float radius() const;
      bool active() const;

      //Functions
      //Initialise the sphere, generates a velocity and makes it active
      void init();
      //Updates the sphere, moves it according to velocity and time given
      void update(float delta);
     
      //Resolves a collision between it and another sphere
      void resolveCollisionSphere();
      //Resolves a collision between it and a plane
      void resolveCollisionPlane(const Vector3d &normal);      
};

#endif

