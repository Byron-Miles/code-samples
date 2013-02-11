#include "CollisionDetection.h"
#include "Vector3d.h"
#include "Plane.h"
#include "Ball.h"
#include "BarrierY.h"

/**
 * Written by Byron Miles, 27/5/2011
 **/

bool intersectBallGoal(const Ball& s1, const Ball &s2)
{
   //Squared Distance between the two (save sqrt)
   float dist = distanceSq(s1.position(), s2.position());
   float sumRadi = s1.radius() + s2.radius();

   //Check dist vs. the sum of the two radi squared
   if(dist < (sumRadi * sumRadi))
      return true; //Intersection

   return false; //No Intersection
}

bool intersectBallBarrierY(const Ball &ball, const BarrierY &barr, float &dist)
{
   //Distance between the ball and the plane
   dist = dot(barr.plane().normal(), ball.position()) - barr.plane().d();

   //Test if ball is on front or back of plane
   if(dist >= ball.radius() || dist <= -ball.radius())
      return false;
 
   // a^2 = h^2 - o^2
   float aSq = distanceSq(barr.plane().position(), ball.position()) - (dist * dist);
   //Check distance bounds
   if(aSq > (barr.radius() * barr.radius()))
      return false;
  
   //Check height bounds
   if(ball.position().y - ball.radius() > barr.plane().position().y + barr.height() ||
      ball.position().y + ball.radius() < barr.plane().position().y)
      return false;

   //Ball intersects plane
   return true;
}


bool intersectBallPoint(const Ball &ball, const Vector3d &point, Vector3d &normal,
                        float &dist)
{
   //Calculate the distance between the ball and the point
   dist = distance(ball.position(), point); 
   //No collision
   if(dist > ball.radius())
      return false;

   //Find the normal
   normal = ball.position() - point;
   normal.normalise();

   return true;
}


bool collisionBallBarrierY(const Ball &ball, const BarrierY &barr, float &toc)
{
   Vector3d normal = barr.plane().normal();

   float CdotN = dot(ball.position(), normal);
   //Distance to collision in direction of normal
   CdotN = barr.plane().d() - CdotN + ball.radius(); 
   //Speed of ball in direction of normal
   float DdotN = dot(ball.velocity(), normal);
   //Time of collision
   toc = CdotN / DdotN;

   if(toc < 0.0f)
      return false; //Collision is behind ball
  
   //Check barrier bounds
   //Find Point of Impact
   Vector3d PoI = ball.position() + ball.velocity() * toc;
   Vector3d bPos = barr.plane().position();

   //Check height bounds
   if(PoI.y < bPos.y || PoI.y > bPos.y + barr.height())
      return false;

   //Check width bounds
   float distSq = distanceSq(PoI, bPos);
   if(distSq > (barr.radius() * barr.radius()))
      return false;

   return true;
}

bool collisionBallPoint(const Ball &ball, const Vector3d &point, float &toc)
{
   //Vector between ball and point
   Vector3d e = point - ball.position();
   float rSq = ball.radius() * ball.radius();
   //Don't collide inside the point
   //if(vectorMagSq(e) < rSq)
     // return false;

   //Velocity projected onto that vector
   float EdotV = dot(e, ball.velocity());
   float EdotE = dot(e, e);
 
   //t = e . v - sqrt((e . v)^2 + r^2 - (e . e))
   float root = ((EdotV * EdotV) + rSq) - EdotE;
   if(root < 0.0f)
      return false; //No intersection

   toc = EdotV - sqrt(root);
  
   if(toc < 0.0f)
      return false; //Collision behind ball

   return true;
}

