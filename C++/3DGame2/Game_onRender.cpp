#include "Game.h"
#include "Vector3d.h"
#include "Ball.h"
#include "BarrierY.h"
#include "Plane.h"
#include "Quaternion.h"
#include "Constants.h"

#include <iostream>

#define BUFFER_OFFSET(i) ((char*)NULL + (i)) //i is offset in bytes

/*
 * Written by Byron Miles 6/5/2011
 */

void Game::onRender()
{
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
   glLoadIdentity();

   //Camera: position, lookat, upvector
   const Vector3d bPos = pBall_->position();
   const float cameraMove = vectorMag(pBall_->velocity());
   gluLookAt(bPos.x, 15.0 + cameraMove, bPos.z + cameraMove + 10.0f, 
             bPos.x, bPos.y, bPos.z, 0.0, 1.0, 0.0);

   //Tilt
   glRotatef(zTilt_, 1.0f, 0.0f, 0.0f);
   glRotatef(xTilt_, 0.0f, 0.0f, -1.0f); //Z axis is 'inverted'
 
   //Lighting
   GLfloat light0Pos[] = {15.0f, 10.0f, 15.0f, 1.0f};
   glLightfv(GL_LIGHT0, GL_POSITION, light0Pos);
   GLfloat light0Dir[] = {-0.65f, -1.0f, -0.65f};
   glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, light0Dir);

   GLfloat light1Pos[] = {15.0f, 10.0f, -15.0f, 1.0f};
   glLightfv(GL_LIGHT1, GL_POSITION, light1Pos);
   GLfloat light1Dir[] = {-0.65f, -1.0f, 0.65f};
   glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, light1Dir);

   GLfloat light2Pos[] = {-15.0f, 10.0f, -15.0f, 1.0f};
   glLightfv(GL_LIGHT2, GL_POSITION, light2Pos);
   GLfloat light2Dir[] = {0.65f, -1.0f, 0.65f};
   glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, light2Dir);

   GLfloat light3Pos[] = {-15.0f, 10.0f, 15.0f, 1.0f};
   glLightfv(GL_LIGHT3, GL_POSITION, light3Pos);
   GLfloat light3Dir[] = {0.65f, -1.0f, -0.65f};
   glLightfv(GL_LIGHT3, GL_SPOT_DIRECTION, light3Dir);

   //The Ball
   glColor3f(1.0f, 1.0f, 1.0f);
   //Texture
   glBindTexture(GL_TEXTURE_2D, ballTexture_);
   glPushMatrix();
      glTranslatef(bPos.x, bPos.y, bPos.z);
      //Rotate the ball to simulate rolling, go go quaternions!
      (*pBallRoll_) *= pBall_->rotation();
      Vector3d axis = pBallRoll_->getRotationAxis();
      glRotatef(pBallRoll_->getRotationAngle() * RTD, axis.z, axis.y, -axis.x);
      //Materials
      GLfloat ballSpec[] = {0.5f, 0.5f, 0.5f, 1.0f};
      glMaterialfv(GL_FRONT, GL_SPECULAR, ballSpec);
      glMaterialf(GL_FRONT, GL_SHININESS, 128.0f);
      //Draw
      gluSphere(pBallQuad_, pBall_->radius(), 20, 20);
      //Reset Materials 
      GLfloat defaultSpec[] = {0.0f, 0.0f, 0.0f, 1.0f};
      glMaterialfv(GL_FRONT, GL_SPECULAR, defaultSpec);
      glMaterialf(GL_FRONT, GL_SHININESS, 0.0f);
   glPopMatrix();

   
   glEnableClientState(GL_VERTEX_ARRAY);
   glEnableClientState(GL_TEXTURE_COORD_ARRAY);

   //The floor
   glColor3f(0.8f, 0.8f, 1.0f);
   //Texture
   glBindTexture(GL_TEXTURE_2D, baseTexture_);
   glBindBuffer(GL_ARRAY_BUFFER, baseBuffer_[TEX]);
   glTexCoordPointer(2, GL_FLOAT, 0, BUFFER_OFFSET(0));
   //Vertices
   glBindBuffer(GL_ARRAY_BUFFER, baseBuffer_[VERT]);
   glVertexPointer(3, GL_FLOAT, 0, BUFFER_OFFSET(0));
   //Materials
   GLfloat floorEmit[] = {0.2f, 0.2f, 0.2f, 1.0f};
   glMaterialfv(GL_FRONT, GL_EMISSION, floorEmit);
   //Draw
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
   //Reset Materials
   GLfloat defaultEmit[] = {0.0f, 0.0f, 0.0f, 1.0f};
   glMaterialfv(GL_FRONT, GL_EMISSION, defaultEmit);


   //The barriers
   glColor3f(1.0f, 0.8f, 0.8f);
   glBindTexture(GL_TEXTURE_2D, barrierTexture_);
   //Materials
   GLfloat barrierAAD[] = {0.6f, 0.6f, 0.6f, 1.0f};
   glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, barrierAAD);
   for(int i = 0; i < pBarrierBuffer_->size(); ++i)
   {  
      //Texture
      glBindBuffer(GL_ARRAY_BUFFER, (*pBarrierBuffer_)[i][TEX]);
      glTexCoordPointer(2, GL_FLOAT, 0, BUFFER_OFFSET(0));
      //Vertices
      glBindBuffer(GL_ARRAY_BUFFER, (*pBarrierBuffer_)[i][VERT]);
      glVertexPointer(3, GL_FLOAT, 0, BUFFER_OFFSET(0));
      //Draw
      glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
   }
   //Reset Materials
   GLfloat defaultAmbient[] = {0.2f, 0.2f, 0.2f, 1.0f};
   GLfloat defaultDiffuse[] = {0.8f, 0.8f, 0.8f, 1.0f};
   glMaterialfv(GL_FRONT, GL_AMBIENT, defaultAmbient);
   glMaterialfv(GL_FRONT, GL_DIFFUSE, defaultDiffuse);

   glDisableClientState(GL_VERTEX_ARRAY);
   glDisableClientState(GL_TEXTURE_COORD_ARRAY);

/*
   //Debug
   //Barrier normals
   for(int i = 0; i < pBarriers_->size(); ++i)
   {
       Vector3d s = (*(*pBarriers_)[i]).plane().position();
       Vector3d e = (*(*pBarriers_)[i]).plane().normal();

       glColor3f(1.0f, 0.0f, 0.0f);
       glBegin(GL_LINES);
          glVertex3f(s.x, s.y, s.z);
          glVertex3f(s.x + e.x, s.y + e.y, s.z + e.z);
       glEnd();
   }
   
   //Ball velocity
   glColor3f(1.0f, 0.0f, 0.0f);
   Vector3d p = pBall_->position();
   Vector3d v = pBall_->velocity();
   glBegin(GL_LINES);
      glVertex3f(p.x, p.y, p.z);
      glVertex3f(p.x + v.x, p.y + v.y, p.z + v.z);
   glEnd();
*/
}

