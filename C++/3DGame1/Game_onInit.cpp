#include <stdlib.h>

#include "Game.h"
#include "Sphere.h"
#include "Plane.h"

/*
 * Written by Byron Miles 6/5/2011
 */

bool Game::onInit()
{
   glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //Default color buffer value

   //The cube
   //Top
   m_verticesTop.push_back(0.5f); //X
   m_verticesTop.push_back(0.5f); //Y
   m_verticesTop.push_back(-0.5f); //Z

   m_verticesTop.push_back(-0.5f);
   m_verticesTop.push_back(0.5f);
   m_verticesTop.push_back(-0.5f);

   m_verticesTop.push_back(0.5f);
   m_verticesTop.push_back(0.5f);
   m_verticesTop.push_back(0.5f);

   m_verticesTop.push_back(-0.5f);
   m_verticesTop.push_back(0.5f);
   m_verticesTop.push_back(0.5f);

   //Side 1
   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(-0.5f);
   m_verticesSide1.push_back(-0.5f);

   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(-0.5f);

   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(-0.5f);
   m_verticesSide1.push_back(0.5f);

   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(0.5f);
   m_verticesSide1.push_back(0.5f);

   //Side 2
   m_verticesSide2.push_back(-0.5f);
   m_verticesSide2.push_back(0.5f);
   m_verticesSide2.push_back(-0.5f);

   m_verticesSide2.push_back(0.5f);
   m_verticesSide2.push_back(0.5f);
   m_verticesSide2.push_back(-0.5f);

   m_verticesSide2.push_back(-0.5f);
   m_verticesSide2.push_back(-0.5f);
   m_verticesSide2.push_back(-0.5f);

   m_verticesSide2.push_back(0.5f);
   m_verticesSide2.push_back(-0.5f);
   m_verticesSide2.push_back(-0.5f);

   //Side 3
   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(0.5f);
   m_verticesSide3.push_back(-0.5f);

   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(-0.5f);

   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(0.5f);
   m_verticesSide3.push_back(0.5f);

   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(-0.5f);
   m_verticesSide3.push_back(0.5f);

   //Side 4
   m_verticesSide4.push_back(-0.5f);
   m_verticesSide4.push_back(-0.5f);
   m_verticesSide4.push_back(0.5f);

   m_verticesSide4.push_back(0.5f);
   m_verticesSide4.push_back(-0.5f);
   m_verticesSide4.push_back(0.5f);

   m_verticesSide4.push_back(-0.5f);
   m_verticesSide4.push_back(0.5f);
   m_verticesSide4.push_back(0.5f);

   m_verticesSide4.push_back(0.5f);
   m_verticesSide4.push_back(0.5f);
   m_verticesSide4.push_back(0.5f);

   //Base    
   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(-0.5f);

   m_verticesBase.push_back(0.5f);
   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(-0.5f);

   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(0.5f);

   m_verticesBase.push_back(0.5f);
   m_verticesBase.push_back(-0.5f);
   m_verticesBase.push_back(0.5f);

   //Bind data to buffers
   glGenBuffers(6, m_vertexBuffer); //Generate a buffer for the vertices

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[0]); //Bind the top vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesTop.size(),
                &m_verticesTop[0], GL_STATIC_DRAW); //Send the data to OpenGL

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[1]); //Bind the side1 vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesSide1.size(),
                &m_verticesSide1[0], GL_STATIC_DRAW); //Send the data to OpenGL

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[2]); //Bind the side2 vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesSide2.size(),
                &m_verticesSide2[0], GL_STATIC_DRAW); //Send the data to OpenGL

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[3]); //Bind the side3 vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesSide3.size(),
                &m_verticesSide3[0], GL_STATIC_DRAW); //Send the data to OpenGL

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[4]); //Bind the side4 vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesSide4.size(),
                &m_verticesSide4[0], GL_STATIC_DRAW); //Send the data to OpenGL

   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[5]); //Bind the base vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * m_verticesBase.size(),
                &m_verticesBase[0], GL_STATIC_DRAW); //Send the data to OpenGL


   //The spheres
   for(int i = 0; i < SPHERES; ++i)
   {
      m_pSpQuad[i] = gluNewQuadric();
      m_pSphere[i] = new Sphere(0.0, 0.0, 0.0, 0.0); //Dummy values
      m_pSphere[i]->init(); //Generate radius, position and velocity
   }

   //The planes
   m_pPlane[0] = new Plane( 0.0f, 0.5f, 0.0f,  0.0f,-1.0f, 0.0f); //Top
   m_pPlane[1] = new Plane( 0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f); //Side1
   m_pPlane[2] = new Plane( 0.0f, 0.0f,-0.5f,  0.0f, 0.0f, 1.0f); //Side2
   m_pPlane[3] = new Plane(-0.5f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f); //Side3
   m_pPlane[4] = new Plane( 0.0f, 0.0f, 0.5f,  0.0f, 0.0f,-1.0f); //Side4
   m_pPlane[5] = new Plane( 0.0f,-0.5f, 0.0f,  0.0f, 1.0f, 0.0f); //Bottom

   m_rotation = 1.0f;
   m_xRotate = 0.0f;
   m_yRotate = 0.0f;
   m_zRotate = 0.0f;

   glEnableClientState(GL_VERTEX_ARRAY);

   return true;
}

