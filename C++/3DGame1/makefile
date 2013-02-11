#Default make file for core components
CORE = Core.o Core_Timer.o
GAME = Game.o Game_onInit.o Game_onLoop.o Game_onRender.o Game_onEvent.o
OTHR = Sphere.o Plane.o Vector3d.o CollisionDetection.o

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
game: Game.cpp
	g++ Game.cpp -c

loop: Game_onLoop.cpp
	g++ Game_onLoop.cpp -c

render: Game_onRender.cpp
	g++ Game_onLoop.cpp -c

event: Game_onEvent.cpp
	g++ Game_onEvent.cpp -c

init: Game_onInit.cpp
	g++ Game_onInit.cpp -c

#Other components
vec3d: Vector3d.cpp
	g++ Vector3d.cpp -c

sphere: Sphere.cpp
	g++ Sphere.cpp -c

plane: Plane.cpp
	g++ Plane.cpp -c

coldet: CollisionDetection.cpp
	g++ CollisionDetection.cpp -c

#baseEntity: Game_BaseEntity.cpp
#	g++ Game_BaseEntity.cpp -c

#entityManager: Game_EntityManager.cpp
#	g++ Game_EntityManager.cpp -c

CLEANALL: 
	rm *.o $(EXEC)
