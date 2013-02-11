#!/usr/bin/lua

--Get the users name
print "Please enter your name:"
name = io.stdin:read()
--Get the users age
print "Please enter you age:"
age = io.stdin:read() % 10

--Print 'hello'..name age % 10 times
for i=1,age do
   print (" "..i.."! Hello, "..name)
end

