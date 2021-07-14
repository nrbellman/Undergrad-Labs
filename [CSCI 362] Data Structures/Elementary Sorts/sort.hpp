// Author: Professor William Killian, Nicholas Bellman 
// Date: 2020 Feb 4
// Class: CSCI 362.02 - Data Structures
// Assignment: 2a - Analyzing Sorting Algorithms
//
// Description:
//
// Header file for templated sorting algorithms, specifically
// bubble sort (optimized), insertion sort, and selection sort.
// Includes a 'Statistics' datatype for aggregating counts for
// swaps and comparisons.

#ifndef SORTING_ALGORITHMS_HPP_
#define SORTING_ALGORITHMS_HPP_

#include <vector>

struct Statistics {

  // default constructs swaps and compares to zero
    size_t numSwaps { 0 };
    size_t numCompares { 0 };

};

template <typename T>
void
bubbleSort (std::vector<T>& v, Statistics& s) {
    
    s.numCompares = 0;
    s.numSwaps = 0;
  
    for (size_t i = v.size() - 1; i >= 1; --i) {

        bool didSwap = false;

        for (size_t j = 0; j < i; ++j) {

            ++s.numCompares;
            if (v[j] > v[j + 1]) {

                ++s.numSwaps;
                std::swap (v[j], v[j + 1]);
                
                didSwap = true;

            }

        }

        if (!didSwap) {

            break;

        }

    }

}

template <typename T>
void
insertionSort (std::vector<T>& v, Statistics& s) {

    s.numCompares = 0;
    s.numSwaps = 0;

    for (size_t i = 1; i < v.size(); ++i) {
        
        
        int element = v[i];
        int index = i;
        
        while (index >= 1) { 
            
            ++s.numCompares;
            if (element < v[index - 1]) {

                v[index] = v[index - 1];
                --index;

            }
            else {

                break;

            }

        }
        
        v[index] = element;

    }

}

template <typename T>
void
selectionSort (std::vector<T>& v, Statistics& s) {

    for (size_t i = 0; i < v.size() - 1; ++i) {

        size_t min = i;

        for (size_t j = i + 1; j < v.size(); ++j) {

            ++s.numCompares;
            if (v[j] < v[min]) {

                min = j;

            }

        }
        /* if (i != min) */ {

            ++s.numSwaps;
            std::swap (v[i], v[min]);

        }

    }

}

#endif
