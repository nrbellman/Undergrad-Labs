/*
 In general, your program should be split up into different sections

 Section 0: Top Comment Block
   Always include the following:
   - First and last name
   - Last date of modification
   - Course + Section
   - Assignment Name
   - File Description

 Section 1: Includes (must be in the following order)
   1. C++ standard library includes (e.g. <algorithm>)
   2. C library includes (e.g. <math.h> or <cmath>)
   3. System library includes (<name.h> but are not part of the C or C++ standard)
   4. User includes (ones contained in quotes -- e.g. "Timer.hpp")

 Section 2: Preprocessor Definitions
   - Anything that starts with #define (or appropriate guards around it

 Section 3: Compile-time constants, type aliases, using directives, type definitions
   - constexpr Type name = value-expr;
   - using Type = type-expr;
   - using std::cout;
   - declarations of file-scoped struct/class/union

 Section 4: Global variables -- NOTE: use sparingly and ONLY if necessary

 Section 5: Forward declarations for non-template functions
   - A forward declaration is a function without a body.
   - Any default parameters MUST be specified in the forward declaration

 Section 6: Template function implementations
   - Any functions beginning with 'template <...>'

 Section 7: main() function implementation [[[entry point of program]]]
   - main must always have the following signature:
     int main (int argc, char* argv[])

 Section 8: Implementations of non-template functions
   - Must be listed in the SAME ORDER as the forward declarations above
   - REMINDER: whenever you update the parameter types/counts, update
     them in BOTH locations. Otherwise you can get a compiler error!
*/

// Author: Professor William Killian, Nicholas Bellman
// Date: 2020 Feb 4
// Class: CSCI 362.02 - Data Structures
// Assignment: 2a - Analyzing Sorting Algorithms
//
// Tests the complexity of various sorting algorithms on various data sets.


// Includes
////////////////////////////////////////////////////////////////////////////////

// TODO: add include you may use here
#include <algorithm>
#include <iostream>
#include <random>
#include <string>
#include <vector>

#include "sort.hpp"

// Forward declarations
////////////////////////////////////////////////////////////////////////////////

// -- for any helper functions you write


// Main
////////////////////////////////////////////////////////////////////////////////

int
main (int argc, char* argv[]) {
  
    size_t N;
    std::string algorithm;
    char type;
    int seed;

    std::cout << "N         ==> ";
    std::cin >> N;
    std::vector<int> data;

    std::cout << "Algorithm ==> ";
    std::cin >> algorithm;

    std::cout << "Type      ==> ";
    std::cin >> type;

    if(type == 'r') {

        std::cout << "Seed      ==> ";
        std::cin >> seed;
    
        while (seed < 0) {
            std::cout << "Seed      ==> ";
            std::cin >> seed;
        }
    
        std::minstd_rand rng(seed);
        for (int i = 0; i < N; ++i) {

            data.push_back(rng() % 10000);

        }

    }
    else if (type == 'a') {

        for (int i = 0; i < N; ++i) {
      
            data.push_back(i + 1);

        }

    }
    else if (type == 'd') {

        for (int i = N; i > 0; --i) {

            data.push_back(i);

        }

    }

    std::vector<int> dataCopy (data);
    std::sort(dataCopy.begin(), dataCopy.end());

    Statistics stat;
    if (algorithm == "bubble") {

        bubbleSort(data, stat);
    
    }
    else if (algorithm == "insertion") {

        insertionSort(data, stat);
    
    }
    else if(algorithm == "selection") {

        selectionSort(data, stat);
    
    }
   
    std::cout << std::endl;

    std::cout << "# Compares: " << stat.numCompares << std::endl;
    std::cout << "# Swaps   : " << stat.numSwaps << std::endl;

    bool okSwap = std::equal(data.begin(), data.end(), dataCopy.begin());
    std::string okSwapStr;

    if (okSwap == 1) {

        okSwapStr = "yes";

    }
    else {
    
        okSwapStr = "no";

    }

    std::cout << "Sort ok?    " << okSwapStr << std::endl;

}

// Function implementations
////////////////////////////////////////////////////////////////////////////////
