#include "Game.h"

/*
 * Written by Byron Miles 6/5/2011
 */

Game::Game()
{
   m_dt_current = 0;
   m_dt_previous = 0;
   m_dt_frame = 0;
}

void Game::onCleanup()
{
   glDisableClientState(GL_VERTEX_ARRAY);
   glDeleteBuffers(6, m_vertexBuffer);
}

