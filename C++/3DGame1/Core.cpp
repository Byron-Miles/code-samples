#include <iostream>
#include <time.h>

#include "Core.h"
#include "Core_Timer.h"
#include "Game.h"

/*
 * Written by Byron Miles 6/5/2011
 */

Core::Core()
{
   m_drawSurface = NULL;
   m_delta = NULL;
   m_game = NULL;
   
   m_running = true;
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
   SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 24); //Set depth size, 16 24 32, adjust as needed
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
   if((m_drawSurface = SDL_SetVideoMode(SCREEN_WIDTH, SCREEN_HEIGHT, 0, flags)) == NULL)
   {
      return false;
   }
   
   //Vertical Sync? 
   //SDL_GL_SetSwapInterval(1);
   
   //OpenGL setup
   glEnable(GL_DEPTH_TEST);
   glEnable(GL_TEXTURE_2D); //Enable textures
   glEnable(GL_CULL_FACE);
   glFrontFace(GL_CW);
   glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
   glEnable(GL_BLEND); //Enable blending (transparency)
   glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
 
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
   m_game = new Game();
   //Initialise our game instance etc.
   if(!m_game->onInit())
      return false; 

   //Create delta timer
   m_delta = new Core_Timer();
   //Start delta timer
   m_delta->start();

   return true;
}//End onInit 

int Core::onExecute()
{
   if(onInit() == false)
      return -1;

   SDL_Event event;

   //seed random
   srand(time(NULL));
  
   int ft = 0;
   int ct = 0;
   int pt = 0;
 
   while(m_running)
   {
      //Process events
      while(SDL_PollEvent(&event))
      {
         //Quit game event
         if(event.type == SDL_QUIT)
         {
            m_running = false;
         }
         //Process game events
         m_game->onEvent(&event);
      }
      
      //Regulate framerate;
      /*
      pt = ct;
      ct = m_delta->getTicks();
      ft = ct - pt;
      if(ft < (1000 / 60))
      {
         SDL_Delay( (1000 / 60) - ft);
      }
      */
      //Process game loop
      m_game->onLoop(m_delta->getTicks());
      //Render game world
      m_game->onRender();
      //Swap buffers
      SDL_GL_SwapBuffers();
   }
   
   onCleanup();
   
   return 0;
}//End onExecute()

void Core::onCleanup()
{
   //Delete delta timer
   delete m_delta;

   //Cleanup game
   m_game->onCleanup();
   //Delete game
   delete m_game;
   
   //Free our rendering context
   SDL_FreeSurface(m_drawSurface);
   SDL_Quit();
}//End onCleanup()

int main(int argc, char* argv[])
{
   Core game;
   
   return game.onExecute();
}

