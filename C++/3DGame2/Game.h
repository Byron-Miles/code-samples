#ifndef GAME_H
#define GAME_H

#define GL_GLEXT_PROTOTYPES //Needed for gl VBO functions

/*
 * Written by Byron Miles 6/5/2011
 */

#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>

#include <vector>

class Ball;
class BarrierY;
class Vector3d;
class Quaternion;

class Game
{
   private:
      static const int VERT = 0; //Vertex index
      static const int TEX = 1; //Texture index

      int dt_current_; //Time of current update step
      int dt_previous_; //Time of previous update step
      int dt_frame_; //Time since previous update step
      Uint8 *pKeystate_; //Array of keystates from SDL
      
      float xTilt_; //Tilt in the x direction, i.e. about the z axes
      float zTilt_; //Tilt in the z direction, i.e. about the x axes

      //The ball
      Ball *pBall_; 
      GLUquadric *pBallQuad_;
      GLuint ballTexture_;
      Quaternion* pBallRoll_;

      //The plane for the base / floor of the maze
      std::vector<GLfloat> *pBaseVertices_;
      std::vector<GLfloat> *pBaseTexels_;
      GLuint baseBuffer_[2];
      GLuint baseTexture_;

      //The barriers for the walls of the maze
      std::vector<BarrierY*> *pBarriers_;
      std::vector< std::vector<GLuint> > *pBarrierBuffer_;
      GLuint barrierTexture_;

      //Collision Points, placed to stop the ball clipping through
      //the edge of barriers
      std::vector<Vector3d*> *pCollisionPoints_;

   public:
     bool onInit();
     void onLoop(int dt);
     void onRender();
     void onEvent(SDL_Event* event);
     void onCleanup();

   public:
      Game();
};

#endif

