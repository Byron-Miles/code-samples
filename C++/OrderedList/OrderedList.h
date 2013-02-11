#ifndef ORDEREDLIST_H
#define ORDEREDLIST_H

#include <iostream>

class Node
{
   public:
      //Constructors
      Node(): m_pNext(NULL) {}; //Default
      Node(double item): m_pNext(NULL), m_dItem(item) {}; //Initilises item
      double m_dItem; //Data item
      Node *m_pNext; //Pointer to next Node
};

class OrderedList
{
   //Friend operator functions
   friend std::ostream & operator<<(std::ostream &, const OrderedList &); //Insert
   friend std::istream & operator>>(std::istream &, OrderedList &); //Extract
      
   public:
       //Constructors
       OrderedList(): m_pHead(NULL), m_iSize(0) {}; //Defualt constructor
       OrderedList(const OrderedList &); //Copy constructor
       
       //Destructor
       ~OrderedList();
             
       //Operator functions
       OrderedList operator+(OrderedList &); //Merge two OrderedLists
       double operator[] (int)const; //Subscript operator returns rvalue
       const OrderedList & operator=(const OrderedList &); //Assignment
       
       //General functions
       void insert(double); //Inserts an item
       bool remove(int); //Remove the i th item
       bool pop(double &); //Pop the first item and copy it to the parameter
       int size() const; //Returns the size of the list
       
   private:
      Node *m_pHead; //The first item in the list
      int m_iSize; //The number of items in the list
};

#endif
