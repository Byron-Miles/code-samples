#include <SDL/SDL.h>

#include "Core_Timer.h"

/*
 * Written by Byron Miles 6/5/2011
 */

Core_Timer::Core_Timer()
{
   //Create a new timer at 0 ticks, stopped and unpaused
   m_startTicks = 0;
   m_pauseTicks = 0;
   m_paused = false;
   m_started = false;
}

//Start the timer
void Core_Timer::start()
{
   m_started = true;
   m_paused = false; //Unpause the timer
   
   m_startTicks = SDL_GetTicks();
}

//Stop the timer
void Core_Timer::stop()
{
   m_started = false;
   m_paused = false;
}

//Pause the timer
void Core_Timer::pause()
{
   if(m_started && !m_paused)
   {
      m_paused = true;
      m_pauseTicks = SDL_GetTicks() - m_startTicks; //Record current ticks
   }
}

//Unpause the timer
void Core_Timer::unpause()
{
   if(m_paused)
   {
      m_paused = false;
      m_startTicks = SDL_GetTicks() - m_pauseTicks; //Remove current ticks
      m_pauseTicks = 0;
   }
}

int Core_Timer::getTicks()
{
   if(m_started)
   {
      if(m_paused)
      {
	 return m_pauseTicks;
      }
      else
      {
	 return SDL_GetTicks() - m_startTicks;
      }
   }
   
   return 0; //Timer isn't running
}

bool Core_Timer::isStarted()
{
   return m_started;
}

bool Core_Timer::isPaused()
{
   return m_paused;
}
