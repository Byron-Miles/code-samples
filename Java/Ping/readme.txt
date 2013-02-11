Comp320 Prac3 readme
Byron Miles 220057347

To compile the Ping Server and Client either:
make
OR
javac PingServer.java
javac PingClient.java

To run the Ping Server and Client
java PingServer server_port
IN A DIFFERENT TERMINAL WINDOW OR TAB
java PingClient server_ip server_port

for example:
java PingServer 5432
java PingClient 127.0.1.1 5432

Note: I modified the server to print out its port and IP Address.

Note2: If you edit one (or more) of the .java files and have trouble
re-compiling and / or running the ping client / server I suggest you delete
all the .class files and re-compile from scratch.
You can do this easily with 'make clean' followed by 'make'.

