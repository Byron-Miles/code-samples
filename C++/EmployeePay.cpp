//Program 5.21
#include <iostream>

void managersPay()
{
   std::cout << "\nManagers pay:\n";
   //Get weekly salary
   std::cout << "Weekly salary: $";
   double pay;
   std::cin >> pay;
   
   //results
   if(std::cin.good())
      std::cout << "The managers pay is: $" << pay << "\n" << std::endl;           
}

void hourlyWorkersPay()
{
   std::cout << "\nHourly workers pay:\n";
   //Get hourly rate
   std::cout << "Hourly rate: $";
   double rate;
   std::cin >> rate;
   //Get hours worked
   std::cout << "Hours worked: ";
   double hours;
   std::cin >> hours;
   
   //calculate pay
   double pay;
   //check for overtime
   if(hours > 40.0)
   {
      pay += rate * 40; //standard rate 
      pay += (rate * 1.5) * (hours - 40); //overtime rate
   }
   //no overtime
   else
      pay += rate * hours; //standard rate

   //results
   if(std::cin.good())
      std::cout << "The hourly workers pay is: $" << pay << "\n" << std::endl;
}

void commissionWorkersPay()
{
   std::cout << "\nCommission workers pay:\n";
   //Get weekly sales
   std::cout << "Gross weekly sales: $";
   int sales;
   std::cin >> sales;
   
   //calculate pay
   double pay;
   pay += sales * 0.057 + 250.0; 
   
   //results
   if(std::cin.good())
      std::cout << "The commission workers pay is: $" << pay << "\n" << std::endl;
}

void pieceWorkersPay()
{
   std::cout << "\nPiece workers pay:\n";
   //Get ammount per item
   std::cout << "Ammount per item: $";
   double ammount;
   std::cin >> ammount;
   //Get items made
   std::cout << "Number of items made: ";
   int items;
   std::cin >> items;
   
   //calculate pay
   double pay;
   pay += ammount * items; //standard rate 
   
   //results
   if(std::cin.good())
      std::cout << "The piece workers pay is: $" << pay << "\n" << std::endl;
}

void checkInputBuffer()
{
   if(!std::cin.good())
   {
      char rubbish[100];
      std::cout << "Input Error!\n" << std::endl;
      std::cin.clear();
      std::cin.getline(rubbish, 100, '\n');
   }
}


int main()
{
   bool quit = false; //exit sentinal
   int input; //user input value
   
   while(!quit)
   {
      checkInputBuffer();
      
      //Menu
      std::cout << "Select worker type\n";
      std::cout << "[1] Manager\n";
      std::cout << "[2] Hourly Worker\n";
      std::cout << "[3] Commission Worker\n";
      std::cout << "[4] Piece Worker\n";
      std::cout << "[5] Quit program" << std::endl;   
      std::cout << ": "; 
      std::cin >> input;
      
      //select apporiate employees type
      switch (input)
      {
         case 1:
            managersPay();
            break;
         case 2:
            hourlyWorkersPay();
            break;
         case 3:
            commissionWorkersPay();
            break;
         case 4:
            pieceWorkersPay();
            break;
         case 5:
            quit = true;
            break;
         default:
            std::cout << "Invalid selection" << std::endl;
      }
   }//end while    
   return 0;
} 