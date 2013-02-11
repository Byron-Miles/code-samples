//Driver for OrderedList

#include <iostream>
#include "LinkedList.h"
#include "OrderedLinkedList.h"

int main()
{
   //Test Linked lists
   LinkedList *pList1, *pList2, *pList3;
   
   //Insert items into list 1
   pList1 = new LinkedList();
   pList1->insert(5.6);
   pList1->insert(1.2);
   pList1->insert(3.4);
   pList1->insert(6.7);
   pList1->insert(4.5);
   pList1->insert(2.3);
   
   //insert items into list 2
   pList2 = new LinkedList();
   pList2->insert(4.4);
   pList2->insert(1.7);
   pList2->insert(3.5);
   pList2->insert(2.6);
   pList2->insert(6.2);
   pList2->insert(5.3);
   
   //Test copy constructor, make list3 a copy of list1
   pList3 = new LinkedList((*pList1));
   
   //Test insertion operator, print each list
   std::cout << "LinkedLists" << std::endl;
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;

   //Test extraction operator, enter a new value into each list
   std::cout << "\nEnter new value for list 1: ";
   std::cin >> (*pList1);
   std::cout << "\nEnter new value for list 2: ";
   std::cin >> (*pList2);
   std::cout << "\nEnter new values for list 3: ";
   std::cin >> (*pList3);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;

   //Test assignment operator, make list3 = list2
   std::cout << "\n\nList3 = List2" << std::endl;
   (*pList3) = (*pList2);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;
   
   //Test addition operator, make list3 = list1 + list2
   std::cout << "\nList3 = List1 + List2" << std::endl;
   (*pList3) = (*pList1) + (*pList2);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;

   //Test remove function, remove 2 items for each list
   std::cout << "\nRemoving (2,4) (0,3) (1,5)" << std::endl;
   pList1->remove(2);
   pList1->remove(4);
   pList2->remove(0);
   pList2->remove(3);
   pList3->remove(1);
   pList3->remove(5);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;

   //Test pop function, pop 2 items from each list
   std::cout << "\nPopping 2 off each..." << std::endl;
   double item;
   pList1->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList1->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList2->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList2->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList3->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList3->pop(item);
   std::cout << "Popped: " << item << std::endl;
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List3(" << pList3->size() << "): " << (*pList3) << std::endl;

   //Test subscript operator and size function, print each list backwards
   std::cout << "\nList 1 in reverse: ";
   for(int i = pList1->size()-1; i >= 0; --i)
      std::cout << (*pList1)[i] << " ";
   std::cout << "\nList 2 in reverse: ";
   for(int i = pList2->size()-1; i >= 0; --i)
      std::cout << (*pList2)[i] << " ";
   std::cout << "\nList 3 in reverse: ";
   for(int i = pList3->size()-1; i >= 0; --i)
      std::cout << (*pList3)[i] << " ";

   //Test destructor, explicitly delete each list
   delete pList1;
   pList1 = NULL;
   delete pList2;
   pList2 = NULL;
   delete pList3;
   pList3 = NULL;


   
   std::cout << "\n----------------------------------------------" << std::endl;
   std::cout << "Now for OrderedLinkedList" << std::endl;

   //Create the list as OrderedLinkedList
   pList1 = new OrderedLinkedList();
   pList1->insert(5.6);
   pList1->insert(1.2);
   pList1->insert(3.4);
   pList1->insert(6.7);
   pList1->insert(4.5);
   pList1->insert(2.3);

   //insert items into list 2
   pList2 = new OrderedLinkedList();
   pList2->insert(4.4);
   pList2->insert(1.7);
   pList2->insert(3.5);
   pList2->insert(2.6);
   pList2->insert(6.2);
   pList2->insert(5.3);

   //Test copy constructor, make list3 a copy of list1
   OrderedLinkedList *pList4 = new OrderedLinkedList();
   pList4->insert(10.0);

   //Test insertion operator, print each list
   std::cout << "OrderedLinkedLists" << std::endl;
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List4(" << pList4->size() << "): " << (*pList4) << std::endl;

   //Test extraction operator, enter a new value into each list
   std::cout << "\nEnter new value for list 1: ";
   std::cin >> (*pList1);
   std::cout << "\nEnter new value for list 2: ";
   std::cin >> (*pList2);
   std::cout << "\nEnter new values for list 4: ";
   std::cin >> (*pList4);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List4(" << pList4->size() << "): " << (*pList4) << std::endl;

   //Test remove function, remove 2 items for each list
   std::cout << "\nRemoving (2,4) (0,3) (1,5)" << std::endl;
   pList1->remove(2);
   pList1->remove(4);
   pList2->remove(0);
   pList2->remove(3);
   pList4->remove(1);
   pList4->remove(5);
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List4(" << pList4->size() << "): " << (*pList4) << std::endl;

   //Test pop function, pop 2 items from each list
   std::cout << "\nPopping 2 off each..." << std::endl;
   pList1->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList1->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList2->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList2->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList4->pop(item);
   std::cout << "Popped: " << item << std::endl;
   pList4->pop(item);
   std::cout << "Popped: " << item << std::endl;
   std::cout << "List1(" << pList1->size() << "): " << (*pList1) << std::endl;
   std::cout << "List2(" << pList2->size() << "): " << (*pList2) << std::endl;
   std::cout << "List4(" << pList4->size() << "): " << (*pList4) << std::endl;

   //Test subscript operator and size function, print each list backwards
   std::cout << "\nList 1 in reverse: ";
   for(int i = pList1->size()-1; i >= 0; --i)
      std::cout << (*pList1)[i] << " ";
   std::cout << "\nList 2 in reverse: ";
   for(int i = pList2->size()-1; i >= 0; --i)
      std::cout << (*pList2)[i] << " ";
   std::cout << "\nList 4 in reverse: ";
   for(int i = pList4->size()-1; i >= 0; --i)
      std::cout << (*pList4)[i] << " ";

   //Test destructor, explicitly delete each list
   delete pList1;
   pList1 = NULL;
   delete pList2;
   pList2 = NULL;
   delete pList4;
   pList3 = NULL;

   std::cout << "\n               THE END" << std::endl;

return 0;
}
