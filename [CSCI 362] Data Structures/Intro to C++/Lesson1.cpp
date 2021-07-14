#include <iostream>
#include <string>

using std::cout;
using std::cin;
using std::endl;
using std::string;

void print(int sum)
{

    cout << "The sum is: " << sum << endl;

}

bool ArrayEq(int A[], int B[], int size)
{

    for (int i = 0; i < size; i++)
    {

        if (A[i] != B[i])
        {

            return false;

        }

    }

    return true;

}

struct Student {
    int id;
    bool isGrad;
};

int NumGrads(Student students[], int size)
{

    int numGrads = 0;

    for (int i = 0; i < size; i++)
    {
        if (students[i].isGrad)
        {
            numGrads++;
        }
    }

}

int main()
{

   int A[5] = {1, 2, 3, 4, 6};
   int B[5] = {1, 2, 3, 4, 5};

   cout << ArrayEq(A, B, 5) << endl;

}

