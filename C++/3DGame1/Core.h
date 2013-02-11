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
      
   private:
      bool m_running;
      
      SDL_Surface* m_drawSurface; //Our rendering context
      Core_Timer* m_delta; //Our main timer for onLoop
      Game* m_game; //Instance of our game

   public:
      Core();
      int onExecute();
      
   public:
      bool onInit();
      void onCleanup();
};

#endif
