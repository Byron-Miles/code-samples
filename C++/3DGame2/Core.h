#ifndef CORE_H
#define CORE_H

#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>

class Core_Timer;
class Game;

class Core 
{
   private:
      static const int SCREEN_WIDTH = 640;
      static const int SCREEN_HEIGHT = 480;
      static const int FRAME_RATE = 1000 / 60; //1000 / FPS
      
   private:
      bool running_;
      
      SDL_Surface* pDrawSurface_; //Our rendering context
      Core_Timer* pDelta_; //Our main timer for onLoop
      Game* pGame_; //Instance of our game

   public:
      Core();
      int onExecute();
      
   public:
      bool onInit();
      void onCleanup();
};

#endif
