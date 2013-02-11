#include "Game.h"
#include "Vector3d.h"
#include "Sphere.h"
#include "Plane.h"
#include "CollisionDetection.h"

/*
 * Written by Byron Miles 6/5/2011
 */

void Game::onLoop(int ct)
{
   m_dt_previous = m_dt_current; //Save previous update step
   m_dt_current = ct; //Update current update step
   m_dt_frame = m_dt_current - m_dt_previous; //Set frame delta time
   float dt = m_dt_frame / 1000.0f;

   m_pKeystate = SDL_GetKeyState(NULL);

   const float ROTATE_SPEED = 100.0f;

   //Process user input
   if(m_pKeystate[SDLK_w])
      m_xRotate -= ROTATE_SPEED * dt;

   if(m_pKeystate[SDLK_s])
      m_xRotate += ROTATE_SPEED * dt;

   if(m_pKeystate[SDLK_a])
      m_yRotate -= ROTATE_SPEED * dt;

   if(m_pKeystate[SDLK_d])
      m_yRotate += ROTATE_SPEED * dt;

   if(m_pKeystate[SDLK_q])
      m_zRotate -= ROTATE_SPEED * dt;

   if(m_pKeystate[SDLK_e])
      m_zRotate += ROTATE_SPEED * dt;

   //Collision Detection and Resolution
   //Sphere vs. Sphere
   for(int i = 0; i < SPHERES; ++i)
   {
      if(m_pSphere[i]->active())
      {
         for(int j = i+1; j < SPHERES; ++j)
         {
            if(m_pSphere[j]->active())
            {
               //If the two spheres intersect
               if(intersectSphereSphere((*m_pSphere[i]), (*m_pSphere[j])))
               {
                  m_pSphere[i]->resolveCollisionSphere();
                  m_pSphere[j]->resolveCollisionSphere();
               }
            }
         }
      }
   }

   float tRemain = dt; //Time remaining this frame
   do{
      float tNextCol = 20000.0f; //Set to ultra high number
      float tToCol; //The time to the collision, returned by cd function
      int sphere; //Index of shpere that will collide next
      int plane; //Index of the plane it will collide with

      for(int i = 0; i < SPHERES; ++i)
      {
         if(m_pSphere[i]->active())
         {
            for(int k = 0; k < PLANES; ++k)
            {
               if(collisionSpherePlane((*m_pSphere[i]), (*m_pPlane[k]), tToCol))
               {
                  if(tToCol < tNextCol)
                  {
                     tNextCol = tToCol;
                     sphere = i;
                     plane = k;
                  }
               }
            }
         }
      }
 
      //Check if collision happens within remaining frame time
      if(tNextCol <= tRemain)
      {
         //Update simulation to time of collision
         for(int i = 0; i < SPHERES; ++i)
         {
            if(m_pSphere[i]->active())
               m_pSphere[i]->update(tNextCol);
         }
         //Resolve the collision
         m_pSphere[sphere]->resolveCollisionPlane(m_pPlane[plane]->normal());
         //Update remaining time
         tRemain -= (tNextCol);
      }
      else //Update to end of time step
      {
         for(int i = 0; i < SPHERES; ++i)
         {
            if(m_pSphere[i]->active())
               m_pSphere[i]->update(tRemain);
         }
         //Finish time step
         tRemain = 0.0f;
      }
   }
   while(tRemain > 0.0f); //Loop until frame time exhausted
}//End onLoop

