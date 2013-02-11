#include "CollisionDetection.h"
#include "Vector3d.h"
#include "Sphere.h"
#include "Plane.h"


/**
 * Written by Byron Miles, 2/5/2011
 **/

//Sphere and Sphere intersection test
//Good for fast enough frame rates
bool intersectSphereSphere(const Sphere& s1, const Sphere &s2)
{
   //Squared Distance between the two spheres(save sqrt)
   float dist = distanceSq(s1.position(), s2.position());
   float sumRadi = s1.radius() + s2.radius();

   //Check dist vs. the sum of the two radi squared
   if(dist <= (sumRadi * sumRadi))
      return true; //Intersection

   return false; //No Intersection
}

//Plane and Sphere collision perdiction test
//Returns time to collision in toc
bool collisionSpherePlane(const Sphere &s, const Plane &p, float &toc)
{
   Vector3d sDirection = unit(s.velocity());

   float CdotN = dot(s.position(), p.normal());
   CdotN = p.d() - CdotN + s.radius();

   float DdotN = dot(sDirection, p.normal());

   toc = CdotN / DdotN;
   if(toc < 0.0f) //The collision is behind the sphere
     return false;

   return true;
}

