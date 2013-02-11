#include "Game.h"
#include "Ball.h"
#include "BarrierY.h"
#include "Vector3d.h"
#include "Quaternion.h"

/*
 * Written by Byron Miles 6/5/2011
 */

Game::Game() :
   dt_current_(0), dt_previous_(0), dt_frame_(0), xTilt_(0), zTilt_(0)
{
   //The ball
   pBall_ = new Ball();
   pBallQuad_ = gluNewQuadric();
   ballTexture_ = 0;
   pBallRoll_ = new Quaternion();

   //Floor of the maze
   pBaseVertices_ = new std::vector<GLfloat>();
   pBaseTexels_ = new std::vector<GLfloat>();
   baseTexture_ = 0;

   //The barriers
   pBarrierBuffer_ = 0;
   pBarriers_ = new std::vector<BarrierY*>();
   barrierTexture_ = 0;

   //The collision points
   pCollisionPoints_ = new std::vector<Vector3d*>();
}

void Game::onCleanup()
{
   //The ball
   delete pBall_;
   pBall_ = 0;
   delete pBallRoll_;
   pBallRoll_ = 0;
   gluDeleteQuadric(pBallQuad_);
   glDeleteTextures(1, &ballTexture_);

   //The floor
   glDeleteBuffers(2, baseBuffer_);
   delete pBaseVertices_;
   pBaseVertices_ = 0;
   glDeleteTextures(1, &baseTexture_);

   //The barriers
   //Delete all elements of the vector
   for(int i = 0; i < pBarriers_->size(); ++i)
   {
      glDeleteBuffers(2, &(*pBarrierBuffer_)[i][0]);
      delete (*pBarriers_)[i];
   }
   delete pBarriers_;
   pBarriers_ = 0;
   delete pBarrierBuffer_;
   pBarrierBuffer_ = 0;
   glDeleteTextures(1, &barrierTexture_);

   //The collision points
   for(int i = 0; i < pCollisionPoints_->size(); ++i)
      delete (*pCollisionPoints_)[i];
   delete pCollisionPoints_;
   pCollisionPoints_ = 0;
}

