#include "Game.h"
#include "Sphere.h"

void Game::onEvent(SDL_Event* event)
{
   //Key Events
   if(event->type == SDL_KEYDOWN)
   {
      //Spacebar, spawns an extra sphere if there are less than SPHERES active
      if(event->key.keysym.sym == SDLK_SPACE)
      {
         //Check each sphere to see if it is active
         for(int i = 0; i < SPHERES; ++i)
         {
            if(!(m_pSphere[i]->active()))
            {
               m_pSphere[i]->init(); //Re-initialise a sphere
               i = SPHERES; //End the loop
            }
         }
      } //End spacebar
   }//End key events
}  

