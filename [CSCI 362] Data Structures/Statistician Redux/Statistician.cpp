/***************************************************
 
 Name: Nicholas Bellman
 Course: CSCI362 - Section 02
 Date: 2020-01-30
 Assignment: Statistician Redux
 Description: Accumulates various statistics about a vector of floats.
			  Statistics include: maximum, minimum, sum, and average.
 
 ***************************************************/

// TODO: other includes go here

#include "Statistician.h"
#include <iostream>
#include <numeric>
#include <algorithm>

// Function Implementations
// **************************************************

// Finds the largest value in the passed vector
// Assumes nums is not empty
float
maximum (const std::vector<float>& nums)
{

	return *std::max_element(nums.begin(), nums.end());

}


// Finds the largest value in the passed vector
// Assumes nums is not empty
float
minimum (const std::vector<float>& nums)
{

	return *std::min_element(nums.begin(), nums.end());

}


// Finds the sum of values from the passed vector
// Should return zero if the vector is empty
float
sumOfValues (const std::vector<float>& nums)
{
	
	return std::accumulate(nums.begin(), nums.end(), 0.0f);

}


// Finds the average of all values from the passed vector
// assumes nums is not empty
float
average (const std::vector<float>& nums)
{
	
	float sum = 0.0;
	int size = nums.size();

	for (int i = 0; i < size; i++)
	{

		sum += nums[i]; 

	}

	return sum / size;

}

// Creates and returns a new vector. Reads in count number
// of values from the user by prompting for each one
// should return an empty vector if count <= 0
std::vector<float>
populate (int count)
{
	
	std::vector<float> result;

	if (count > 0) 
	{

		for (int i = 0; i < count; i++) 
		{

			float value;

			std::cout << "Enter value ==> ";
			std::cin >> value;

			result.push_back(value);

		}

	}

	return result;

}
