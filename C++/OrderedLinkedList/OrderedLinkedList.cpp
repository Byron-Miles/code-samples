//Function definitions for OrderedList

#include <stdexcept>
#include "OrderedLinkedList.h"

//Friend insertion operator function
std::ostream & operator<<(std::ostream &os, const OrderedLinkedList &rList)
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
std::istream & operator>>(std::istream &is, OrderedLinkedList &rList)
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

//Default Constructor
OrderedLinkedList::OrderedLinkedList()
{
   m_pHead = NULL;
   m_iSize = 0 ;
}

//Copy Constructor
OrderedLinkedList::OrderedLinkedList(const OrderedLinkedList &rList)
{
   m_pHead = NULL;
   m_iSize = 0;
   
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
OrderedLinkedList::~OrderedLinkedList()
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
OrderedLinkedList OrderedLinkedList::operator+(OrderedLinkedList &rList)
{
   //Create a new OrderedList to return
   OrderedLinkedList *list = new OrderedLinkedList();

   //If both lists to be merged aren't empty
   if(m_pHead != NULL && rList.m_pHead != NULL)
   {
      //Temporary Nodes for traversing the existing lists
      Node *lhs = m_pHead;
      Node *rhs = rList.m_pHead;
   
      //Create the head of the new list from the lowest item
      if(lhs != NULL && (rhs == NULL || lhs->m_dItem <= rhs->m_dItem))
      {
         list->m_pHead = new Node(lhs->m_dItem); //lhs head
         lhs = lhs->m_pNext; //Move lhs to next node
      }
      else
      {
         list->m_pHead = new Node(rhs->m_dItem); //rhs head
         rhs = rhs->m_pNext; //Move rhs to next node
      }
      
      //Temporary Node for adding to the new list
      Node *temp = list->m_pHead;
      
      //Insert each additional item in lhs and rhs in to list
      while(lhs != NULL || rhs != NULL)
      {
         //While the lhs is less than or equal to the rhs
         while(lhs != NULL && (rhs == NULL || lhs->m_dItem <= rhs->m_dItem))
         {
            temp->m_pNext = new Node(lhs->m_dItem); //Add lhs item
            lhs = lhs->m_pNext; //Move lhs to next Node
            temp = temp->m_pNext; //Move temp to the next Node
         }
         //While the rhs is less than or equal to the lhs
         while(rhs != NULL && (lhs == NULL || rhs->m_dItem <= lhs->m_dItem))
         {
            temp->m_pNext = new Node(rhs->m_dItem); //Add rhs item
            rhs = rhs->m_pNext; //Move rhs to next Node
            temp = temp->m_pNext; //Move temp to the next Node
         }
      }
      list->m_iSize = m_iSize + rList.m_iSize; //Set the list size
   }
   //Return a copy of newly created list
   return (*list);
}

//Operator=, assigns rhs to lhs
const OrderedLinkedList & OrderedLinkedList::operator=(const OrderedLinkedList &rList)
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

//Insert function, inserts the item into the correct position in the list
void OrderedLinkedList::insert(double item)
{
   //Temporary pointer to Node
   Node *temp = m_pHead;
   
   //If the list is empty or the item is to become the new head
   if(m_pHead == NULL || item <= m_pHead->m_dItem)
   {
      m_pHead = new Node(item); //Create a new head
      m_pHead->m_pNext = temp; //Link to the rest of the list
   }
   else //The list is not empty or the item goes in the 'middle' or end
   {
      //Move temp to the next Node
      temp = m_pHead->m_pNext;
      //Temporary pointer to Node to keep reference to previous Node
      Node *prev = m_pHead;
      
      //Traverse the list until the end or an insertion point is found
      while(temp != NULL && item > temp->m_dItem)
      {
         //Move to next node
         prev = temp;
         temp = temp->m_pNext;
      }
      //The end of the list was found
      if(temp == NULL)
      {
         //Add the item to the end
         prev->m_pNext = new Node(item);
      }
      //An insertion point was found
      else
      {
         Node *ins = new Node(item); //Create a temporary pointer to it
         ins->m_pNext = temp; //Link the next item in the list
         prev->m_pNext = ins; //Link to the newly inserted item
      }
   }
   ++m_iSize; //Increase the size of the list by one
}
