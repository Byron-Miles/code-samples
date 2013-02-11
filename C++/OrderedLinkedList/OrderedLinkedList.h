#ifndef ORDEREDLINKEDLIST_H
#define ORDEREDLINKEDLIST_H

#include "LinkedList.h"

//Inherites from LinkedList
class OrderedLinkedList : public LinkedList
{
   //Friend operator functions, friends are not inherited
   friend std::ostream & operator<<(std::ostream &, const OrderedLinkedList &); //Insert
   friend std::istream & operator>>(std::istream &, OrderedLinkedList &); //Extract
      
   public:
       //Constructors, constructors are not inherited
       OrderedLinkedList(); //Default constructor
       OrderedLinkedList(const OrderedLinkedList &); //Copy constructor
       
       //Destructor, destructors are not inherited
       ~OrderedLinkedList();
             
       //Operator functions, assignment operator not inherited
       virtual OrderedLinkedList operator+(OrderedLinkedList &); //Merge two OrderedLinkedLists
       const OrderedLinkedList & operator=(const OrderedLinkedList &); //Assignment
       
       //General functions
       virtual void insert(double); //Inserts an item
};

#endif
