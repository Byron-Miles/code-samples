#include "Game.h"
#include "Sphere.h"
#include "Vector3d.h"

#include "Plane.h"

#define BUFFER_OFFSET(i) ((char*)NULL + (i)) //i is offset in bytes

/*
 * Written by Byron Miles 6/5/2011
 */

void Game::onRender()
{
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
   glLoadIdentity();

   //Setup
   glTranslatef(0.0f, 0.0f,-2.0f);
   glRotatef(m_xRotate, 1.0f, 0.0f, 0.0f);
   glRotatef(m_yRotate, 0.0f, 1.0f, 0.0f);
   glRotatef(m_zRotate, 0.0f, 0.0f, 1.0f);
 
   //The Spheres
   for(int i = 0; i < SPHERES; ++i)
   {
      if(m_pSphere[i]->active())
      {
         Vector3d color = m_pSphere[i]->color();
         glColor4f(color.x, color.y, color.z, 1.0f);

         glPushMatrix();
           Vector3d trans = m_pSphere[i]->position();
           glTranslatef(trans.x, trans.y, trans.z);
           gluSphere(m_pSpQuad[i], m_pSphere[i]->radius(), 20, 20);
         glPopMatrix();
      }
   }
   
   //The cube
   //Top
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[0]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   //Lines
   glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
   glBegin(GL_LINES);
      glArrayElement(0);
      glArrayElement(1);
      glArrayElement(0);
      glArrayElement(2);
      glArrayElement(1);
      glArrayElement(3);
      glArrayElement(2);
      glArrayElement(3);
   glEnd();
   //Face
   glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

   //Side1
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[1]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   //Lines
   glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
   glBegin(GL_LINES);
      glArrayElement(0);
      glArrayElement(1);
      glArrayElement(0);
      glArrayElement(2);
      glArrayElement(2);
      glArrayElement(3);
   glEnd();
   //Wall
   glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
 
   //Side2
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[2]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   //Lines
   glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
   glBegin(GL_LINES);
      glArrayElement(0);
      glArrayElement(2);
      glArrayElement(2);
      glArrayElement(3);
   glEnd();
   //Wall
   glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

   //Side3
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[3]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   //Lines
   glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
   glBegin(GL_LINES);
      glArrayElement(1);
      glArrayElement(3);
      glArrayElement(2);
      glArrayElement(3);
   glEnd();
   //Wall
   glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

   //Side4
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[4]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   //Lines
   glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
   glBegin(GL_LINES);
      glArrayElement(0);
      glArrayElement(1);
   glEnd();
   //Wall
   glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

   //Base
   glBindBuffer(GL_ARRAY_BUFFER, m_vertexBuffer[5]);
   glVertexPointer(3, GL_FLOAT, 0, 0);
   glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

   //Debug
   //Plane Normals
   /* 
   for(int i = 0; i < PLANES; ++i)
   {
     
      Vector3d p = m_pPlane[i]->position();
      Vector3d n = m_pPlane[i]->normal();

      glColor4f(1.0f,0.0f,0.0f,1.0f);
      glBegin(GL_LINES);
        glVertex3f(p.x, p.y, p.z);
        glVertex3f(n.x, n.y, n.z);
      glEnd();
      
      glColor4f(1.0f,0.0f,1.0f,1.0f);
      glBegin(GL_POINTS);
         glVertex3f(n.x,n.y,n.z);
      glEnd();
  }
         
  //Sphere Velocity
   for(int i = 0; i < SPHERES; ++i)
   {
     
      Vector3d p = m_pSphere[i]->position();
      Vector3d v = m_pSphere[i]->velocity();

      glColor4f(1.0f,0.0f,0.0f,1.0f);
      glBegin(GL_LINES);
        glVertex3f(p.x, p.y, p.z);
        glVertex3f(p.x + v.x, p.y + v.y, p.z + v.z);
      glEnd();
      
      glColor4f(1.0f,0.0f,1.0f,1.0f);
      glBegin(GL_POINTS);
         glVertex3f(v.x,v.y,v.z);
      glEnd();
   }
   */
}

