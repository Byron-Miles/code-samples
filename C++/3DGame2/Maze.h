#ifndef MAZE_H
#define MAZE_H

#include <vector>

/**
 * The Maze class
 * Defines a maze as a list of outer walls, an implicit floor, a list of
 * barriers, a list of collision points, a goal and a ball.
 * The maze can be tilted about the X and Z axes (to a limit)
 * Provides methods to add components to the maze and tilt it.
 **/

/**
 * Written by Byron Miles, 24/5/2011
 **/

class Vector3d;
class Plane;
//class Barrier;
class Ball;

class Maze
{
   private:
      std::vector<Plane*> *pWalls_; //The outside walls
      //std::vector<Barrier*> *pBarriers_; //The inner barriers
      std::vector<Vector3d*> *pCPoints_; //The collision points, corners
      Vector3d *pGoal_; //The goal
      Ball *pBall_; //The ball

      float zTilt_; //Current z tilt;
      float xTilt_; //Current x tilt;
      
      Maze(const Maze &a) {} //Private copy constructor
      Maze &operator =(const Maze &a) {} //Private assignment operator

   public:
     //Constructors
     //Default, creates lists etc. use addX() functions to add components
     Maze();

     //Destructor
     ~Maze();

     //Mutators
     //Add a wall to the list
     void addWall(const Vector3d& position, const Vector3d& normal);
     //Add a barrier
    // void addBarrier(const Vector3d& a, const Vector3d& b); 
     //Add collision point
     void addCPoint(const Vector3d& cPoint);
     //Set the goal
     void setGoal(const Vector3d& goal);
     //Set the ball
     void setBall(const Vector3d& position, float radius);

     //Accessors
     const Plane* getWall(int i); //Get wall i
     int walls(); //Number of walls
     //const Barrier& getBarrier(int i); //Get the specified barrier
     //const Vector3d& getCPoint(int i); //Get the specified collision point
     const Vector3d& goal(); //Get the goal
     const Ball& ball(); //Get the ball
 
     float zTilt(); //Get the current z tilt
     float xTilt(); //Get the current x tilt

     //Mutators
     //Tilt the board about z and/or x axis by CONST * delta
     void tiltPlusZ(float delta);
     void tiltPlusX(float delta);
     void tiltMinusZ(float delta);
     void tiltMinusX(float delta);
 
     //Loop
     //Update components of the maze at start of loop
     void onLoop(float delta);
     //Upadte components of the maze during the loop
     void update(float delta);
     //Render
     void render();
};

#endif

