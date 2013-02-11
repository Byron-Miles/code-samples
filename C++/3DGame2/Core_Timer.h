#ifndef CORE_TIMER_H
#define CORE_TIMER_H

/*
 * Written by Byron Miles 6/5/2011
 */

class Core_Timer
{
   private:
      int m_startTicks;
      int m_pauseTicks;
      bool m_paused;
      bool m_started;
      
   public:
      Core_Timer();
      
      void start();
      void stop();
      void pause();
      void unpause();
      
      int getTicks();
      
      bool isStarted();
      bool isPaused();
      
};

#endif

   


