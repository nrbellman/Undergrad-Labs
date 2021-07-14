/**********************************************************************************************************************
*
*   Author:         Nicholas Bellman
*   Course:         CSCI 362 - Data Structures
*   Date Revised:   2020-25-03
*   Assingment:     The Sieve of Eratosthenes
*   Description:    Implementation of the Sieve of Eratosthenes algorithm for finding prime numbers using a set and a 
*                   vector.
*
**********************************************************************************************************************/

// Local includes
#include "Timer.hpp"

// System includes
#include <cstdlib>
#include <iostream>
#include <set>
#include <string>
#include <vector>

// Using declarations
using std::set;
using std::string;
using std::vector;

/*********************************************************************************************************************/

// Return the set of primes between 2 and N.
// Use a set to implement the sieve.
set<unsigned>
sieveSet (unsigned N)
{

    set<unsigned> primes;

    // Populates the set with values from 2 to N
    for (int i = 2; i <= N; ++i)
    {
        primes.insert(i);
    }


    set<unsigned>::iterator m = primes.begin();
    unsigned p = *m;

    while (p * p <= N)
    {
        for (int i = p * p; i <= *--primes.end(); i += p)
        {
            primes.erase(i);
        }

        m = ++m;
        p = *m;
    }

    return primes;

};

// Return the set of primes between 2 and N.
// Use a vector to implement the sieve.
// After filtering out the composites, put the primes in a set
//   to return to the caller. 
set<unsigned>
sieveVector (unsigned N)
{
    vector<bool> primeVec(N+1, true);

    for (int i = 2; i <= N; ++i)
    {
        if (primeVec[i] == false) // Skips any numbers already marked as composite.
        {
            continue;
        }
        else
        {
            for (int j = i * i; j <= N; j += i) // Marks multiples of j as composite.
            {
                primeVec[j] = false;
            }
        }
    }

    set<unsigned> primeSet;
    for (int i = 2; i <= N; ++i)
    {
        if (primeVec[i] == true)
        {
            primeSet.insert(i);
        }
    }

    return primeSet;
};

int
main (int argc, char* argv[])
{
    string arg1 (argv[1]);
    string arg2 (argv[2]);

    Timer<> timer;

    set<unsigned> sieve;
    int count;

    unsigned long N = std::stoul (arg2);

    if (arg1 == "set")
    {
        timer.start();
        sieve = sieveSet(N);
        count = sieve.size();
        timer.stop();
        
        //Output
        std::cout << "Pi[" << N << "] = " << count << " (using a set)\n";
        std::cout << "Time: " << timer.getElapsedMs() << " ms\n";
    }

    if (arg1 == "vector")
    {
        timer.start();
        sieve = sieveVector(N);
        count = sieve.size();
        timer.stop();
        
        //Output
        std::cout << "Pi[" << N << "] = " << count << " (using a vector)\n";
        std::cout << "Time: " << timer.getElapsedMs() << " ms\n";
    }

}

/**********************************************************************************************************************
*
*   N        10,000,000    20,000,000    40,000,000
*   ===============================================
*   set       27562.3ms     58664.2ms      122828ms
*   vector    1313.23ms     2571.03ms     5093.18ms
*
*   The vector implementation of the Sieve of Eratosthenes was by far quicker than the set implementation, the former
*   taking anywhere from ~1 second to ~5 seconds on average compared to ~30 seconds to ~2 minutes on average for the
*   latter.
*
*   One possible reason for the discrepancy is that the vector implementation isn't dealing with removal of elements.
*   Instead, it is only marking which indexes are composite numbers.
*
**********************************************************************************************************************/