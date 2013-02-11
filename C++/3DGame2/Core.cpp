#include <time.h> //Needed for random number generator

#include "Core.h"
#include "Core_Timer.h"
#include "Game.h"

/*
 * Written by Byron Miles 6/5/2011
 */

Core::Core()
{
   pDrawSurface_ = 0;
   pDelta_ = 0;
   pGame_ = 0;
   
   running_ = true;
}

bool Core::onInit()
{ 
   //Initalise SDL
   if(SDL_Init(SDL_INIT_EVERYTHING) < 0)
   {
      return false;
   }
   
   //SDL OpenGL attributes
   SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);
   //Set depth size, 16 24 32, adjust as needed
   SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 24);
   /*
   SDL_GL_SetAttribute(SDL_GL_RED_SIZE,        8);
   SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE,      8);
   SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE,       8);
   SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE,      8);
   SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE,      16);
   SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE,     32);

   SDL_GL_SetAttribute(SDL_GL_ACCUM_RED_SIZE,  8);
   SDL_GL_SetAttribute(SDL_GL_ACCUM_GREEN_SIZE,    8);
   SDL_GL_SetAttribute(SDL_GL_ACCUM_BLUE_SIZE, 8);
   SDL_GL_SetAttribute(SDL_GL_ACCUM_ALPHA_SIZE,    8);
   */
   
   //SDL OpenGL anti-aliasing, optional
   SDL_GL_SetAttribute(SDL_GL_MULTISAMPLEBUFFERS,  1);
   SDL_GL_SetAttribute(SDL_GL_MULTISAMPLESAMPLES,  4);
   
   
   //Create SDL OpenGL window
   //Note: add flag SDL_FULLSCREEN for fullscreen mode
   //Note: 0 means use current bits per pixel
   Uint32 flags = SDL_OPENGL | SDL_HWSURFACE; 
   if((pDrawSurface_ = SDL_SetVideoMode(SCREEN_WIDTH, SCREEN_HEIGHT, 0, flags)) == 0)
   {
      return false;
   }
   
   //Vertical Sync? 
   //SDL_GL_SetSwapInterval(1);
   
   //OpenGL setup
   glEnable(GL_DEPTH_TEST);
   glEnable(GL_TEXTURE_2D); //Enable textures
   glEnable(GL_LIGHTING);
//   glEnable(GL_CULL_FACE);
//   glFrontFace(GL_CW);
   glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
//   glEnable(GL_BLEND); //Enable blending (transparency)
//   glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
 
   //View position and size
   glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
   
   glMatrixMode(GL_PROJECTION);
   glLoadIdentity();
   
   //2D ortho mode, Left, Right, Bottom, Top, Near, Far
   //Note: Set for right hand co-ordinate system?
   //glOrtho(0,SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);

   gluPerspective(52.0f, float(SCREEN_WIDTH) / float(SCREEN_HEIGHT), 1.0f, 100.0f);
   //glFrustum(-1,1,-1,1,1,100);
   
   glMatrixMode(GL_MODELVIEW);
   glLoadIdentity();

   //Create an instane of our game
   pGame_ = new Game();
   //Initialise our game instance etc.
   if(!pGame_->onInit())
      return false; 

   //Create delta timer
   pDelta_ = new Core_Timer();
   //Start delta timer
   pDelta_->start();

   return true;
}//End onInit 

int Core::onExecute()
{
   if(onInit() == false)
      return -1;

   SDL_Event event;

   //seed random
   srand(time(0));
  
   //For controlling framerate
   int dt_frame = 0;
   int dt_current = 0;
   int dt_previous = 0;
 
   while(running_)
   {
      //Process events
      while(SDL_PollEvent(&event))
      {
         //Quit game event
         if(event.type == SDL_QUIT)
         {
            running_ = false;
         }
         //Process game events
         pGame_->onEvent(&event);
      }
      
      //Regulate framerate;
      dt_previous = dt_current;
      dt_current = pDelta_->getTicks();
      dt_frame = dt_current - dt_previous;
      if(dt_frame < (FRAME_RATE))
      {
         SDL_Delay(FRAME_RATE - dt_frame);
      }
      
      //Process game loop
      pGame_->onLoop(pDelta_->getTicks());
      //Render game world
      pGame_->onRender();
      //Swap buffers
      SDL_GL_SwapBuffers();
   }
   
   onCleanup();
   
   return 0;
}//End onExecute()

void Core::onCleanup()
{
   //Delete delta timer
   delete pDelta_;

   //Cleanup game
   pGame_->onCleanup();
   //Delete game
   delete pGame_;
   
   //Free our rendering context
   SDL_FreeSurface(pDrawSurface_);
   SDL_Quit();
}//End onCleanup()

int main(int argc, char* argv[])
{
   Core game;
   
   return game.onExecute();
}

