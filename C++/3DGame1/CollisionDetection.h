#ifndef COLLISIONDETECTION_H
#define COLLISIONDETECTION_H

/**
 * Collision Detection Functions
 * Defines tests for:
 * Intersection of two Spheres
 * Collision Prediction of a Sphere and a Plane
 **/

/**
 * Written by Byron Miles, 5/5/2011
 **/

class Sphere;
class Plane;

//Detect the static intersection of two Spheres
bool intersectSphereSphere(const Sphere& s1, const Sphere &s2);

//Plane and Sphere collision perdiction test
//Returns time to collision in toc
bool collisionSpherePlane(const Sphere &s, const Plane &p, float &toc);

#endif

