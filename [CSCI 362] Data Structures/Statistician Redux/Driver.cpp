/***************************************************
  
 Name: Nicholas Bellman
 Course: CSCI362 - Section 02
 Date: 2020-01-30
 Assignment: Statistician Redux
 Description: Collects input from a user to populate a vector of floats.
 
 ***************************************************/

// Include Directives
// **************************************************
#include <iostream>
#include <vector>
// TODO: other includes go here

#include "Statistician.h"

// Using Statements
// **************************************************
using std::cout;
using std::cin;
using std::endl;
// TODO: any extra using statements would go here

// Forward Declarations
// **************************************************

// TODO: any functions you implement AFTER main must be defined here


// Main
// **************************************************

int
main(int argc, char* argv[])
{

    int count;

    cout << "Enter number of values ==> ";
    cin >> count;

    std::vector<float> vec = populate(count);

    cout << "\nThe statistics of all " << count << " values are:\n";
    cout << "  Sum: " << sumOfValues(vec) << endl;
    cout << "  Avg: " << average(vec) << endl;
    cout << "  Min: " << minimum(vec) << endl;
    cout << "  Max: " << maximum(vec) << endl;

}


// Function Implementations
// **************************************************

// TODO: any functions you call within main that are a part of this
// file must be implemented AFTER main
