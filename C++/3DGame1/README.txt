Comp323 Assignment1
Byron Miles 220057347

Question 5.
The program consists of:
-----------------------------
CollisionDetection.cpp
CollisionDetection.h
Core.cpp
Core.h
Core_Timer.cpp
Core_Time.h
Game.cpp
Game.h
Game_onEvent.cpp
Game_onInit.cpp
Game_onLoop.cpp
Game_onRender.cpp
Plane.cpp
Plane.h
Sphere.cpp
Sphere.h
Vector3d.cpp
Vector3d.h
----------------------------
A makefile is also included

To compile use:
make

To run use:
./game

Please note: You will need a computer with a graphics card capable of 
Vertex Buffer Objects in order to compile and / or run this program.

Contols: 
You can use the W,S,A,D,Q,E keys to rotate the cube in various ways.
You can press SPACEBAR to spawn additional spheres one at a time up to
a maxmimum of 10 spheres in the game world at once.

The program is structured as follows:
The Core handles all the OPENGL and SDL initialisation and runs the main
game loop. It also controls the main game timer and has an instance of the
Game class.

The Game holds and controls all the objects in the game world and is broken
into various parts:
onInit initialises all the game objects and sets everything up with initial
values etc.
onEvent handles events, such as a user pressing the spacebar.
onLoop updates the game world and all the objects in it. This is where
collision dectection, physics, etc. is calculated. It also handles continous
inputs, such as holding down W to rotate the cube.
onRender draws the game world and all the objects in it based their
current state.

Various other class are either objects in the world (like spheres), or 
contain useful functions and formulas like the dot product in Vector3d or
the if and when a collision occurs between a sphere and plane in 
CollisionDetection.


I know the code is somewhat hacked together (with member variables 
named in two different styles, m_var or var_). This is because I wrote some
of the code months ago (and just updated it a bit) and rest I wrote recently.
I didn't have enough time to unify its look and feel.

