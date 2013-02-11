#ifndef BARRIER_Y_H
#define BARRIER_Y_H

#include <vector>

/**
 * The BarrierY class
 * Defines a Barrier rotated about the Y axis
 * Holds a plane limited by height and width and
 * a vector of floats defining a triangle strip for rendering
 **/

/**
 * Written by Byron Miles, 27/5/2011
 **/

class Plane;

class BarrierY
{
   private:
      Plane *pPlane_; //The plane
      std::vector<float> *pVertices_; //Triangle strip co-ords
      std::vector<float> *pTexels_; //Texture co-ords
      float radius_; //Width of plane of x / z axes
      float height_; //Height of plane of y axis

      BarrierY(const BarrierY &a) {} //Private copy constructor
      BarrierY &operator =(const BarrierY &a) {} //Private assignment operator

   public:
      //Constructors
      //Empty Default
      BarrierY() : pPlane_(0), pVertices_(0), radius_(0), height_(0) {}
      //Bottom two vertices of trianlge strip and height
      BarrierY(float v1x, float v1y, float v1z, float v3x, float v3y, float v3z, float height);
      
      //Destructor
      ~BarrierY();

      //Accessors
      const Plane& plane() const;
      const std::vector<float>& vertices() const;
      const std::vector<float>& texels() const;
      float radius() const;
      float height() const;
};

#endif
      
