//Function definitions for OrderedList

#include <stdexcept>
#include "OrderedList.h"

//Friend insertion operator function
std::ostream & operator<<(std::ostream &os, const OrderedList &rList)
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
std::istream & operator>>(std::istream &is, OrderedList &rList)
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
OrderedList::OrderedList(const OrderedList &rList) : m_pHead(NULL), m_iSize(0)
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
OrderedList::~OrderedList()
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
OrderedList OrderedList::operator+(OrderedList &rList)
{
   //Create a new OrderedList to return
   OrderedList *list = new OrderedList();

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

//Operator[], returns item at subscript i
double OrderedList::operator[](int i) const
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
const OrderedList & OrderedList::operator=(const OrderedList &rList)
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
void OrderedList::insert(double item)
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


//Remove function, removes the i th item from the list
bool OrderedList::remove(int i)
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

//Pop function, removes the first item and copies it to the parameter
bool OrderedList::pop(double &item)
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
      
      return true; //The pop was sucessful
   }
   return false; //The pop failed
}

//Size function, returns the size of the list
int OrderedList::size() const
{
   return m_iSize;
}
