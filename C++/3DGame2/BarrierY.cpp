#include "Vector3d.h"
#include "BarrierY.h"
#include "Plane.h"

/**
 * The BarrierY class
 * Implements the BarrierY class definition
 **/

/**
 * Written by Byron Miles, 23/5/2011
 **/

//Constructor
BarrierY::BarrierY(float v1x, float v1y, float v1z, float v3x, float v3y, float v3z, float height) :
   height_(height)
{
   //Calculate the plane
   Vector3d vA = Vector3d(v3x, v3y, v3z) - Vector3d(v1x, v1y, v1z);
   Vector3d vB = Vector3d(v1x, v1y, v1z) - Vector3d(v1x, v1y - height_, v1z); 
   Vector3d normal = cross(vA, vB);
   Vector3d point = Vector3d(v1x + v3x, v1y + v3y, v1z + v3z) / 2.0f;
   //The plane
   pPlane_ = new Plane (point, normal);
   //The radius
   radius_ = vectorMag(vA) / 2.0f;

   //Generate and store triangle strip
   pVertices_ = new std::vector<float>();
   //v0
   pVertices_->push_back(v1x);
   pVertices_->push_back(v1y + height_);
   pVertices_->push_back(v1z);
   //v1
   pVertices_->push_back(v1x);
   pVertices_->push_back(v1y);
   pVertices_->push_back(v1z);
   //v2
   pVertices_->push_back(v3x);
   pVertices_->push_back(v3y + height_);
   pVertices_->push_back(v3z);
   //v3
   pVertices_->push_back(v3x);
   pVertices_->push_back(v3y);
   pVertices_->push_back(v3z);

   //Generate and store texture co-ords
   pTexels_ = new std::vector<float>();
   //v0
   pTexels_->push_back(0.0f);
   pTexels_->push_back(height_);
   //v1
   pTexels_->push_back(0.0f);
   pTexels_->push_back(0.0f);
   //v2
   pTexels_->push_back(radius_ * 2.0f);
   pTexels_->push_back(height_);
   //v3
   pTexels_->push_back(radius_ * 2.0f);
   pTexels_->push_back(0.0f);
}

//Destructor
BarrierY::~BarrierY()
{
   delete pPlane_;
   delete pVertices_;
}

//Accessors
const Plane& BarrierY::plane() const
{
   return (*pPlane_);
}

const std::vector<float>& BarrierY::vertices() const
{
   return (*pVertices_);
}

const std::vector<float>& BarrierY::texels() const
{
   return (*pTexels_);
}

float BarrierY::radius() const
{
   return radius_;
}

float BarrierY::height() const
{
   return height_;
}

