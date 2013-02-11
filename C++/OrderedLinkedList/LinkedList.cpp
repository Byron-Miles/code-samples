//Function definitions for OrderedList

#include <stdexcept>
#include "LinkedList.h"

//Friend insertion operator function
std::ostream & operator<<(std::ostream &os, const LinkedList &rList)
{
   //Temporary pointer to the current Node
   Node *temp = rList.m_pHead;
   //Output each item in list
   while(temp != NULL)
   {
      os << temp->m_dItem << " "; //Output current item plus a space
      temp = temp->m_pNext; //Go to the next item
   }
   //Return the ostream
   return os;
}

//Friend extraction operator
std::istream & operator>>(std::istream &is, LinkedList &rList)
{
   //Temporary double to hold value of input
   double value;
   
   //Read in new value
   is >> value;
   //If the input is good
   if(is.good())
      rList.insert(value); //Insert the new value
   //Return the istream
   return is;
}

//Copy Constructor
LinkedList::LinkedList(const LinkedList &rList) : m_pHead(NULL), m_iSize(0)
{
   //If the list to be copied is not empty
   if(rList.m_pHead != NULL)
   {
      //Temporary pointer to the Node to be copies
      Node *next = rList.m_pHead;

      //Create the head of the new list
      m_pHead = new Node(next->m_dItem);
      next = next->m_pNext; //Move to next node
      
      //Temporary pointer to the Node being created
      Node *temp = m_pHead;

      //Copy each additional Node in rList into the new list
      while(next != NULL)
      {
         //Create a new node with the item from rList
         temp->m_pNext = new Node(next->m_dItem);
         temp = temp->m_pNext; //Move to temp next node
         next = next->m_pNext; //Move to next next node
      }
      m_iSize = rList.m_iSize; //Set the size
   }
}

//Destructor
LinkedList::~LinkedList()
{
   //If the list is not empty
   if(m_pHead != NULL)
   {
      //Temporary pointers to Node for traversing list and clearing it
      Node *temp = m_pHead;
      Node *next = m_pHead->m_pNext;

      //Destroy the contents of this
      while(temp != NULL)
      {
         delete temp; //Delete the current Node
         temp = next; //Move to the next Node
         if(next != NULL)
            next = next->m_pNext; //Update next
      }
   }
}

//Operactor+, merges two list into a new one
LinkedList LinkedList::operator+(LinkedList &rList)
{
   //Create a new Linked copied from this
   LinkedList *list = new LinkedList((*this));

   //If the rList is not empty
   if(rList.m_pHead != NULL)
   {
      //Temporary Node for traversing rList
      Node *temp = rList.m_pHead;
      
      //Insert each item in rList
      while(temp != NULL)
      {
         list->insert(temp->m_dItem); //Insert the item
         temp = temp->m_pNext; //Move temp to the next Node
      }
      list->m_iSize = m_iSize + rList.m_iSize; //Set the list size
   }
   //Return a copy of newly created list
   return (*list);
}

//Operator[], returns item at subscript i
double LinkedList::operator[](int i) const
{
   //Temporary pointer to Node to traverse list
   Node *temp = m_pHead;
      
   //If the index is invalid
   if(i >= m_iSize)
   {
      //Throw an out of range exception
      throw std::out_of_range("Invalid List Index"); 
   }
   else
   {
      //Traverse list to find item at i
      for(int n = 0; n < i; ++n)
         temp = temp->m_pNext; //Move to next Node
   }
   return temp->m_dItem; //Return item at i
}
   
//Operator=, assigns rhs to lhs
const LinkedList & LinkedList::operator=(const LinkedList &rList)
{
   //Check that they aren't the same and that rList is not empty
   if(this != &rList && rList.m_pHead != NULL)
   {
      //Temporary pointers to Node for traversing lists
      Node *temp = m_pHead;
      Node *next = m_pHead->m_pNext;

      //Destroy the contents of this
      while(temp != NULL)
      {
         delete temp; //Delete the current Node
         temp = next; //Move to the next Node
         if(next != NULL)
            next = next->m_pNext; //Update next
      }
      m_pHead = NULL; //Rest head to NULL

      //Reassign next to the item being copied
      next = rList.m_pHead;
      
      //Create a new head of this
      m_pHead = new Node(next->m_dItem);
      next = next->m_pNext; //Move temp to next node
      
      //Reassign temp the the node to be created
      temp = m_pHead;
      
      //Copy each additional Node in rList into the new list
      while(next != NULL)
      {
         //Create a new node with the item from rList
         temp->m_pNext = new Node(next->m_dItem);
         temp = temp->m_pNext; //Move to temp next node
         next = next->m_pNext; //Move to next next node
      }
      m_iSize = rList.m_iSize; //Set the size
   }
   //Return reference to this
   return *this;
}

//Insert function, inserts the item at the end of the list
void LinkedList::insert(double item)
{
   //If the list is empty
   if(m_pHead == NULL)
      m_pHead = new Node(item); //Create a new head
   else //Place the item at the end of the list
   {
      //Temporary pointers to NOde to traverse the list
      Node *temp = m_pHead;
      Node *next = m_pHead->m_pNext;
      
      //Move to the end of the list
      while(next != NULL)
      {
         temp = next; //Update temp
         next = next->m_pNext; //Move to next node
      }
      //Add the item to the end
      temp->m_pNext = new Node(item);
   }
   ++m_iSize; //Increase the size of the list by one
}

//Remove function, removes the i th item from the list
bool LinkedList::remove(int i)
{
   //If there are items in the list
   if(m_pHead != NULL)
   {
      //Temporary pointers to Node for traversing the list
      Node *temp = m_pHead;
      Node *prev = NULL;

      //Find the i th Node
      for(int n = 0; n < i && temp != NULL; ++n)
      {
         prev = temp; //Keep a reference to the previous Node
         temp = temp->m_pNext; //Move to next Node
      }

      //If the item is valid, remove it and return true
      if(temp != NULL)
      {
         //If it's the first item
         if(prev == NULL)
         {
            m_pHead = temp->m_pNext; //Assign a new head
            delete temp; //Delete the item
         }
         else //It's not the first item
         {
            prev->m_pNext = temp->m_pNext; //Link the previous item to the next
            delete temp; //Delete the item
         }
          --m_iSize; //Reduce the size of the list by one
         return true; //The remove succeded
      }
   }
   return false; //The remove failed
}

//Pop function, removes the first item
bool LinkedList::pop(double &item)
{
   //If the list has any items
   if(m_pHead != NULL)
   {
      //Temporary reference to the old head for deletion
      Node *temp = m_pHead;
      
      //Copy the item
      item = m_pHead->m_dItem;
      
      //Update the head
      m_pHead = m_pHead->m_pNext; //Assign the new head
      delete temp; //Delete the old head
      --m_iSize; //Decrease the size of the list by one
      
      return true; //The pop was successful
   }
   return false; //The pop failed
}

//Size function, returns the size of the list
int LinkedList::size() const
{
   return m_iSize;
}
