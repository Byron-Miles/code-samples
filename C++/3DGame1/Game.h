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

class Sphere;
class Plane;

const int PLANES = 6;
const int SPHERES = 10;
  
class Game
{
   private:
      int m_dt_current; //Time of current update step
      int m_dt_previous; //Time of previous update step
      int m_dt_frame; //Time since previous update step
      Uint8* m_pKeystate; //Array of keystates from SDL
      
      //The cube!
      std::vector<GLfloat> m_verticesTop;
      std::vector<GLfloat> m_verticesSide1;
      std::vector<GLfloat> m_verticesSide2;
      std::vector<GLfloat> m_verticesSide3;
      std::vector<GLfloat> m_verticesSide4;
      std::vector<GLfloat> m_verticesBase;
      GLuint m_vertexBuffer[6];

      //The planes
      Plane *m_pPlane[PLANES];

      //The spheres!
      Sphere *m_pSphere[SPHERES]; 
      GLUquadric *m_pSpQuad[SPHERES];
 
      float m_rotation;
      float m_xRotate;
      float m_yRotate;
      float m_zRotate;

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

