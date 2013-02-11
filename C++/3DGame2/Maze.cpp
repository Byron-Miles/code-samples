#include "Maze.h"
#include "Vector3d.h"
#include "Plane.h"
#include "Ball.h"

/**
 * The Maze class
 * Implements the Maze class definition
 **/

/**
 * Written by Byron Miles, 24/5/2011
 **/

//Constants
const float TILT_MAX = 15.0; //Maximum tilt about x or z axes
const float TILT_SPEED = 7.5; //Tilt per second

//Constructor
Maze::Maze() :
   pGoal_(0), pBall_(0), zTilt_(0), xTilt_(0)
{
   pWalls_ = new std::vector<Plane*>();
// pBarriers_ = new std::vector<Barrier*>();
   pCPoints_ = new std::vector<Vector3d*>();
}

//Destructor
Maze::~Maze()
{
   delete pWalls_;
//   delete pBarriers_;
   delete pCPoints_;
   delete pGoal_;
   delete pBall_;
}

//Mutators
void Maze::addWall(const Vector3d& position, const Vector3d& normal)
{
   pWalls_->push_back(new Plane(position, normal));
}


//void addBarrier(const Vector3d& a, const Vector3d& b)
//{
//   pBarriers_->push_back(new Barrier(a, b));
//}

void Maze::addCPoint(const Vector3d& cPoint)
{
   pCPoints_->push_back(new Vector3d(cPoint));
}

void Maze::setGoal(const Vector3d& goal)
{
   if(pGoal_) 
      delete pGoal_; //Avoid potential memory leak

   pGoal_ = new Vector3d(goal);
}

void Maze::setBall(const Vector3d& position, float radius)
{
   if(pBall_)
      delete pBall_; //Avoid potential memory leak

   pBall_ = new Ball(position, radius);
}


//Accessors
const Plane* Maze::getWall(int i)
{
   if(i < 0 || i > pWalls_->size())
      return 0;

   return (*pWalls_)[i];
}

int Maze::walls()
{
   return pWalls_->size();
}

//const Barrier& getBarrier(int i); //Get the specified barrier
//const Vector3d& getCPoint(int i); //Get the specified collision point

const Vector3d& Maze::goal()
{
   return (*pGoal_);
}

const Ball& Maze::ball()
{
   return (*pBall_);
}
 
float Maze::zTilt()
{
   return zTilt_;
}

float Maze::xTilt()
{
   return xTilt_;
}

//Movement
void Maze::tiltPlusZ(float delta)
{
   if(zTilt_ < TILT_MAX)
   {
      zTilt_ += TILT_SPEED * delta;
      if(zTilt_ > TILT_MAX)
         zTilt_ = TILT_MAX;
   }
}

void Maze::tiltPlusX(float delta)
{
   if(xTilt_ < TILT_MAX)
   {
      xTilt_ += TILT_SPEED * delta;
      if(xTilt_ > TILT_MAX)
         xTilt_ = TILT_MAX;
   }
}

void Maze::tiltMinusZ(float delta)
{
   if(zTilt_ > -TILT_MAX)
   {
      zTilt_ -= TILT_SPEED * delta;
      if(zTilt_ < -TILT_MAX)
         zTilt_ = -TILT_MAX;
   }
}

void Maze::tiltMinusX(float delta)
{
   if(xTilt_ > -TILT_MAX)
   {
      zTilt_ -= TILT_SPEED * delta;
      if(zTilt_ < -TILT_MAX)
         zTilt_ = -TILT_MAX;
   }
}

//Loop
void Maze::onLoop(float delta)
{
   pBall_->updateVelocity(Vector3d(xTilt_ / 9.0f, 0.0f, zTilt_ / 9.0f), delta);
}

void Maze::update(float delta)
{
   pBall_->update(delta);
}

//Render
void Maze::render()
{
   pBall_->render();
}

