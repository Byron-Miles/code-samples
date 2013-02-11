#ifndef COLLISIONDETECTION_H
#define COLLISIONDETECTION_H

/**
 * Collision Detection and Prediction Functions
 **/

/**
 * Written by Byron Miles, 29/5/2011
 **/

class Ball;
class BarrierY;
class Vector3d;

//Detect the static intersection of a ball and a goal
bool intersectSphereSphere(const Ball &s1, const Ball &s2);

//Detect the static intersection of a ball and a point
//Returns the normal between them in normal and the distance between them in dist
bool intersectBallPoint(const Ball &ball, const Vector3d &point, Vector3d &normal,
                        float &dist);

//Detect the static intersection of a ball and a barrierY
//Returns distance between the two in dist
bool intersectBallBarrierY(const Ball &ball, const BarrierY &barr, float &dist);


//Predict the collision of a ball and a barrierY
//Returns time to collision in toc
bool collisionBallBarrierY(const Ball &s, const BarrierY &p, float &toc);

//Predict the collision of a ball and a point
//Returns time of collision in toc
bool collisionBallPoint(const Ball &ball, const Vector3d &point, float &toc);

#endif

