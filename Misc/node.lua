#!/usr/bin/lua

--node function
function node(n)
   print ("This is node "..n)
   if(math.random(0,1) == 1) then
      return true;
   else
      return false;
   end
end

--Seed random
math.randomseed(os.time())

--Start of control flow
while(node(1)) do
   if(node(2)) then
      if(node(3)) then
         node(5)
      else
         node(6)
      end
      node(7)
   else
      node(4)
   end
   node(8)
end
node(9)
--End

