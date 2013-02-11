#include "Game.h"
#include "Ball.h"
#include "BarrierY.h"
#include "Vector3d.h"
#include "targa.h" //Defines the TargaImage Class
#include "Quaternion.h"

#include <iostream>

/*
 * Written by Byron Miles 6/5/2011
 */

bool Game::onInit()
{
   const float S = 0.4f; //Scale of the ball and barriers

   glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //Default color buffer value
 
   //Lighting
   //Colors
   const GLfloat whiteLight[] = {1.0f, 1.0f, 1.0f, 1.0f};
   const GLfloat redLight[] = {1.0f, 0.1f, 0.1f, 1.0f};
   const GLfloat greenLight[] = {1.0f, 1.0f, 0.1f, 1.0f};
   const GLfloat blueLight[] = {0.1f, 0.1f, 1.0f, 1.0f};

   glEnable(GL_LIGHT0);
      glLightfv(GL_LIGHT0, GL_AMBIENT, whiteLight);
      glLightfv(GL_LIGHT0, GL_DIFFUSE, whiteLight);
      glLightfv(GL_LIGHT0, GL_SPECULAR, whiteLight);
      glLightf(GL_LIGHT0, GL_LINEAR_ATTENUATION, 0.01f);
      glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 30.0f); //*2 degree cone
      glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 10.0f);

   glEnable(GL_LIGHT1);
      glLightfv(GL_LIGHT1, GL_AMBIENT, redLight);
      glLightfv(GL_LIGHT1, GL_DIFFUSE, redLight);
      glLightfv(GL_LIGHT1, GL_SPECULAR, redLight);
      glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, 0.01f);
      glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 30.0f); //*2 degree cone
      glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 10.0f);

   glEnable(GL_LIGHT2);
      glLightfv(GL_LIGHT2, GL_AMBIENT, greenLight);
      glLightfv(GL_LIGHT2, GL_DIFFUSE, greenLight);
      glLightfv(GL_LIGHT2, GL_SPECULAR, greenLight);
      glLightf(GL_LIGHT2, GL_LINEAR_ATTENUATION, 0.01f);
      glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 30.0f); //*2 degree cone
      glLightf(GL_LIGHT2, GL_SPOT_EXPONENT, 10.0f);

   glEnable(GL_LIGHT3);
      glLightfv(GL_LIGHT3, GL_AMBIENT, blueLight);
      glLightfv(GL_LIGHT3, GL_DIFFUSE, blueLight);
      glLightfv(GL_LIGHT3, GL_SPECULAR, blueLight);
      glLightf(GL_LIGHT3, GL_LINEAR_ATTENUATION, 0.01f);
      glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, 30.0f); //*2 degree cone
      glLightf(GL_LIGHT3, GL_SPOT_EXPONENT, 10.0f);


   //Texture parameters
   TargaImage texture; 

   //Setup the ball
   pBall_ = new Ball(9.4f, S, 9.4f, S); //Bottom right corner
   pBallRoll_->setToRotateAboutY(0.0f); //Give it some initial value
   gluQuadricDrawStyle(pBallQuad_, GLU_FILL);
   gluQuadricNormals(pBallQuad_, GLU_SMOOTH);
   gluQuadricOrientation(pBallQuad_, GLU_OUTSIDE);
   gluQuadricTexture(pBallQuad_, GL_TRUE);
   //Load Ball texture
   glGenTextures(1, &ballTexture_);
   if(!texture.load("data/ball.tga"))
   {
      std::cerr << "Failed to load texture" << std::endl;
      return false;
   }
   glBindTexture(GL_TEXTURE_2D, ballTexture_);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
   glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, texture.getWidth(), texture.getHeight(),
                0, GL_RGB, GL_UNSIGNED_BYTE, texture.getImageData()); 
   texture.unload(); 
   
   //The base of the maze
   pBaseVertices_->push_back(10.0f);
   pBaseVertices_->push_back(0.0f);
   pBaseVertices_->push_back(-10.0f);
   pBaseTexels_->push_back(-10.0f);
   pBaseTexels_->push_back(10.0f);

   pBaseVertices_->push_back(-10.0f);
   pBaseVertices_->push_back(0.0f);
   pBaseVertices_->push_back(-10.0f);
   pBaseTexels_->push_back(-10.0f);
   pBaseTexels_->push_back(-10.0f);

   pBaseVertices_->push_back(10.0f);
   pBaseVertices_->push_back(0.0f);
   pBaseVertices_->push_back(10.0f);
   pBaseTexels_->push_back(10.0f);
   pBaseTexels_->push_back(10.0f);

   pBaseVertices_->push_back(-10.0f);
   pBaseVertices_->push_back(0.0f);
   pBaseVertices_->push_back(10.0f);
   pBaseTexels_->push_back(10.0f);
   pBaseTexels_->push_back(-10.0f);

   //Vertex buffer
   glGenBuffers(2, baseBuffer_); //Generate a buffer for the vertices
   glBindBuffer(GL_ARRAY_BUFFER, baseBuffer_[VERT]); //Bind base vertex buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * pBaseVertices_->size(),
                &(*pBaseVertices_)[0], GL_STATIC_DRAW); //Send vertices to OpenGL
   //Texel buffer
   glBindBuffer(GL_ARRAY_BUFFER, baseBuffer_[TEX]); //Bind base texel buffer
   glBufferData(GL_ARRAY_BUFFER, sizeof(float) * pBaseTexels_->size(),
                &(*pBaseTexels_)[0], GL_STATIC_DRAW); //Send texels to OpenGL

   //Load base texture
   glGenTextures(1, &baseTexture_);
   if(!texture.load("data/base.tga"))
   {
      std::cerr << "Failed to load texture" << std::endl;
      return false;
   }
   glBindTexture(GL_TEXTURE_2D, baseTexture_);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
   glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, texture.getWidth(), texture.getHeight(),
                0, GL_RGB, GL_UNSIGNED_BYTE, texture.getImageData()); 
   texture.unload(); 

   //The Outer Barriers
   pBarriers_->push_back(new BarrierY(-10.0f, 0.0f, -10.0f, 10.0f, 0.0f, -10.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(10.0f, 0.0f, -10.0f, 10.0f, 0.0f, 10.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(10.0f, 0.0f, 10.0f, -10.0f, 0.0f, 10.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(-10.0f, 0.0f, 10.0f, -10.0f, 0.0f, -10.0f, 2.0f*S));

   //The Inner Barriers and collision points
   pBarriers_->push_back(new BarrierY(0.0f, 0.0f, -2.0f, -4.0f, 0.0f, -6.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(-4.0f, S, -6.0f));
   pBarriers_->push_back(new BarrierY(-4.0f, 0.0f, -6.0f, -6.0f, 0.0f, -4.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(-6.0f, S, -4.0f));
   pBarriers_->push_back(new BarrierY(-6.0f, 0.0f, -4.0f, -2.0f, 0.0f, 0.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(-2.0f, 0.0f, 0.0f, -6.0f, 0.0f, 4.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(-6.0f, S, 4.0f));
   pBarriers_->push_back(new BarrierY(-6.0f, 0.0f, 4.0f, -4.0f, 0.0f, 6.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(-4.0f, S, 6.0f));
   pBarriers_->push_back(new BarrierY(-4.0f, 0.0f, 6.0f, 0.0f, 0.0f, 2.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(0.0f, 0.0f, 2.0f, 4.0f, 0.0f, 6.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(4.0f, S, 6.0f));
   pBarriers_->push_back(new BarrierY(4.0f, 0.0f, 6.0f, 6.0f, 0.0f, 4.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(6.0f, S, 4.0f));
   pBarriers_->push_back(new BarrierY(6.0f, 0.0f, 4.0f, 2.0f, 0.0f, 0.0f, 2.0f*S));
   pBarriers_->push_back(new BarrierY(2.0f, 0.0f, 0.0f, 6.0f, 0.0f, -4.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(6.0f, S, -4.0f));
   pBarriers_->push_back(new BarrierY(6.0f, 0.0f, -4.0f, 4.0f, 0.0f, -6.0f, 2.0f*S));
   pCollisionPoints_->push_back(new Vector3d(4.0f, S, -6.0f));
   pBarriers_->push_back(new BarrierY(4.0f, 0.0f, -6.0f, 0.0f, 0.0f, -2.0f, 2.0f*S));

   //Create vertex buffers for barriers
   pBarrierBuffer_ = new std::vector< std::vector<GLuint> >(pBarriers_->size(), 
                                                            std::vector<GLuint>(2));

   //Bind data to buffers
   for(int i = 0; i < pBarrierBuffer_->size(); ++i)
   {
      glGenBuffers(2, &(*pBarrierBuffer_)[i][0]);
      //Vertex buffer
      glBindBuffer(GL_ARRAY_BUFFER, (*pBarrierBuffer_)[i][VERT]);
      glBufferData(GL_ARRAY_BUFFER, sizeof(float) * (*(*pBarriers_)[i]).vertices().size(),
                   &((*(*pBarriers_)[i]).vertices()[0]), GL_STATIC_DRAW);
      //Texel buffer
      glBindBuffer(GL_ARRAY_BUFFER, (*pBarrierBuffer_)[i][TEX]);
      glBufferData(GL_ARRAY_BUFFER, sizeof(float) * (*(*pBarriers_)[i]).texels().size(),
                   &((*(*pBarriers_)[i]).texels()[0]), GL_STATIC_DRAW);
   }

   //Load Barrier texture
   glGenTextures(1, &barrierTexture_);
   if(!texture.load("data/barrier.tga"))
   {
      std::cerr << "Failed to load texture" << std::endl;
      return false;
   }
   glBindTexture(GL_TEXTURE_2D, barrierTexture_);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
   glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, texture.getWidth(), texture.getHeight(),
                0, GL_RGB, GL_UNSIGNED_BYTE, texture.getImageData()); 
   texture.unload(); 
 
   return true;
}

