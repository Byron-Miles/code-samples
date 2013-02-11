#include <vector>

#include "Game.h"
#include "Vector3d.h"
#include "Plane.h"
#include "Ball.h"
#include "BarrierY.h"
#include "CollisionDetection.h"

#include <iostream>

/*
 * Written by Byron Miles 27/5/2011
 */

void Game::onLoop(int dt_current)
{
   const float TILT_MAX = 15.0f;
   const float TILT_SPEED = 25.0f;

   dt_previous_ = dt_current_; //Save previous update step
   dt_current_ = dt_current; //Update current update step
   dt_frame_ = dt_current_ - dt_previous_; //Set frame delta time
   float delta = dt_frame_ / 1000.0f;

 //   std::cout << "Frame time: " << dt_frame_ << std::endl;

   pKeystate_ = SDL_GetKeyState(0);

   //Process user input
   if(pKeystate_[SDLK_w])
   {
      zTilt_ -= TILT_SPEED * delta;
      if(zTilt_ < -TILT_MAX)
         zTilt_ = -TILT_MAX;
   }

   if(pKeystate_[SDLK_s])
   {
      zTilt_ += TILT_SPEED * delta;
      if(zTilt_ > TILT_MAX)
         zTilt_ = TILT_MAX;
   }

   if(pKeystate_[SDLK_a])
   {
      xTilt_ -= TILT_SPEED * delta;
      if(xTilt_ < -TILT_MAX)
         xTilt_ = -TILT_MAX;
   }

   if(pKeystate_[SDLK_d])
   {
      xTilt_ += TILT_SPEED * delta;
      if(xTilt_ > TILT_MAX)
         xTilt_ = TILT_MAX;
   }

//Collison Detection
/****************************************************************************
 * Uses intersection tests to determined if the ball has penetrated a barrier
 * or collision point. If it has the ball is moved out of the barrier / point
 * and it's velocity reflected. It works well for high frame rates, but I have
 * also included a check / loop so that the ball is not moved more than it's
 * radius at a time; this prevents it from passing through barriers / points
 * 'between' frames.
 ***************************************************************************/

   //Calculate dt to move ball by it's radius
   float magV = vectorMag(pBall_->velocity());
   float dtMove = 1.0f / (magV / pBall_->radius());
   //Calculate number of steps required and dt for each step
   int steps = (int)(delta / dtMove) + 1;
   float dtStep = delta / (float)steps;
 
   for(int i = 0; i < steps; ++i)
   {
      //Update the velocity of the the ball, physics and all that..
      pBall_->updateVelocity(xTilt_, 0.0f, zTilt_, dtStep);
      pBall_->update(dtStep);

      //Collision Detection
      float dist;
      Vector3d normal;
      //Ball Plane intersection test, done first for better corner behaviour
      for(int i = 0; i < pBarriers_->size(); ++i)
      {
         if(intersectBallBarrierY((*pBall_), (*(*pBarriers_)[i]), dist))
         {
            normal = (*(*pBarriers_)[i]).plane().normal();
            //Move the ball back out of the barrier
            pBall_->move(normal, pBall_->radius() - dist + 0.0001f);
            //Resolve the collision
            pBall_->resolveCollisionPlane(normal);
         }
      }

      //Ball point intersection test
      for(int i = 0; i < pCollisionPoints_->size(); ++i)
      {
         if(intersectBallPoint((*pBall_), (*(*pCollisionPoints_)[i]), normal, dist))
         {
            //Move the ball off the point
            pBall_->move(normal, pBall_->radius() - dist + 0.0001f);
            //Resolve the collision
            pBall_->resolveCollisionPlane(normal);
         }
      }
   }

//Collision Prediction
/*******************************************************************************
 * This is commented out as while it mostly works great for planes on their own
 * it doesn't work so well for collision points. The reason for this is that if
 * the ball is close enough to the point and builds up enough velocity (through
 * acceleration due to gravity) it can actually pass through the point.
 * 
 * This was also an issue with planes, but I managed to fix that simply by moving
 * the ball outwards from the plane by a small amount, this, and everything else I
 * tried didn't work for points. As such I now use collision detection.
 ******************************************************************************/
/*
   //Update velocity, acceleration due to gravity
   pBall_->updateVelocity(xTilt_, 0.0f, zTilt_, delta);

   //Collision Detection and Resolution
   float dtRemain = delta; //Time remaining this frame
   const int INVALID = -1;

   do{
      float dtNextCol = 20000.0f; //Set to ultra high number
      float dtToCol; //The time to the collision, returned by cd function
      int barrier = INVALID; //The barrier the ball collided with
      int point = INVALID; //The point the ball collided with

      //Ball vs. Barriers
      for(int i = 0; i < pBarriers_->size(); ++i)
      {
         if(collisionBallBarrierY((*pBall_), (*(*pBarriers_)[i]), dtToCol))
         {
            if(dtToCol < dtNextCol)
            {
               dtNextCol = dtToCol; //Update time to next collision
               barrier = i; //Keep track of barrier
            }
         }
      }

      //Ball vs. Collision Points
      for(int i = 0; i < pCollisionPoints_->size(); ++i)
      {
         if(collisionBallPoint((*pBall_), (*(*pCollisionPoints_)[i]), dtToCol))
         {
            if(dtToCol < dtNextCol)
            {
               dtNextCol = dtToCol; //Update time to next collision
               point = i; //Keep track of point
            }
         }
      }

      //Check if collision happens within remaining frame time
      if(dtNextCol <= dtRemain)
      {
         pBall_->update(dtNextCol);

         //Note: Attempt to resolve in reverse order to checking
         if(point != INVALID) //Resolve collision with point
         {
            //Move the ball off the point
            pBall_->move(pBall_->position() - (*(*pCollisionPoints_)[point]), 0.0001f);
            //Resolve the collision
            pBall_->resolveCollisionPoint((*(*pCollisionPoints_)[point]));
         }
         else //Resolve collision with barrier plane
         {
            //Move the ball of the plane
            pBall_->move((*(*pBarriers_)[barrier]).plane().normal(), 0.0001f);
            //Resolve the collision
            pBall_->resolveCollisionPlane((*(*pBarriers_)[barrier]).plane().normal());
         }
         //Update remaining time
         dtRemain -= dtNextCol;
      }
      else //Update to end of time step
      {  
         pBall_->update(dtRemain); //Move the ball

         //Finish time step
         dtRemain = 0.0f;
      }
   }
   while(dtRemain > 0.0f); //Loop until frame time exhausted
*/

}//End onLoop

