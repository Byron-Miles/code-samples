#ifndef LINKEDLIST_H
#define LINKEDLIST_H

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

class LinkedList
{
   //Friend operator functions
   friend std::ostream & operator<<(std::ostream &, const LinkedList &); //Insert
   friend std::istream & operator>>(std::istream &, LinkedList &); //Extract
      
   public:
       //Constructors
       LinkedList(): m_pHead(NULL), m_iSize(0) {}; //Defualt constructor
       LinkedList(const LinkedList &); //Copy constructor
       
       //Destructor
       ~LinkedList();
             
       //Operator functions
       virtual LinkedList operator+(LinkedList &); //Merge two LinkedLists
       double operator[] (int)const; //Subscript operator returns rvalue
       const LinkedList & operator=(const LinkedList &); //Assignment
       
       //General functions
       virtual void insert(double); //Inserts an item
       bool remove(int); //Remove the i th item
       bool pop(double &); //Pop the first item
       int size() const; //Returns the size of the list
       
   protected:
      Node *m_pHead; //The first item in the list
      int m_iSize; //The number of items in the list
};

#endif
