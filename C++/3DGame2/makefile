#Default make file for core components
CORE = Core.o Core_Timer.o
GAME = Game.o Game_onInit.o Game_onLoop.o Game_onRender.o Game_onEvent.o
OTHR = Ball.o Plane.o BarrierY.o Vector3d.o CollisionDetection.o targa.o Quaternion.o

LDFLAGS = -lSDL -lGL -lGLU

EXEC = game

all: final

final: $(CORE) $(GAME) $(OTHR)
	g++ $(CORE) $(GAME) $(OTHR) $(LDFLAGS) -o $(EXEC)

#Core components
core: Core.cpp
	g++ Core.cpp -c

timer: Core_Timer.cpp
	g++ Core_Timer.cpp -c

#Game components
game: Game.cpp Game.h
	g++ Game.cpp -c

loop: Game_onLoop.cpp Game.h
	g++ Game_onLoop.cpp -c

render: Game_onRender.cpp Game.h
	g++ Game_onLoop.cpp -c

event: Game_onEvent.cpp Game.h
	g++ Game_onEvent.cpp -c

init: Game_onInit.cpp Game.h
	g++ Game_onInit.cpp -c

#Other components
vec3d: Vector3d.cpp Vector3d.h
	g++ Vector3d.cpp -c

ball: Ball.cpp Ball.h
	g++ Ball.cpp -c

plane: Plane.cpp Plane.h
	g++ Plane.cpp -c

coldet: CollisionDetection.cpp CollisionDetection.h
	g++ CollisionDetection.cpp -c

maze: Maze.cpp Maze.h
	g++ Maze.cpp -c

barrier: BarrierY.cpp BarrierY.h
	g++ BarrierY.cpp -c

#baseEntity: Game_BaseEntity.cpp
#	g++ Game_BaseEntity.cpp -c

#entityManager: Game_EntityManager.cpp
#	g++ Game_EntityManager.cpp -c

CLEANALL: 
	rm *.o $(EXEC)
