/*
  Filename   : Coins.c
  Author     : Gary M. Zoppetti, Nicholas Bellman
  Course     : CSCI 380-01
  Assignment : N/A
  Description: Determine minimum number of coins to make change
*/   

/************************************************************/
// System includes

#include <stdio.h>
#include <stdlib.h>

// Not used in this program
#include <string.h>
#include <stdbool.h>

/************************************************************/
// Local includes

/************************************************************/
// Function prototypes/global vars/typedefs

struct Change
{
  int quarters;
  int dimes;
  int nickels;
  int pennies;
  int half_dollars;
};

typedef struct Change Change_t; 

void
printIntro ();

int
promptForCents ();

Change_t
makeChange (int cents);

void
printChange (const Change_t* change);

/************************************************************/

int      
main ()
{        
  printIntro ();

  int cents = promptForCents ();

  printf ("Making change for %d cents:\n", cents);
  Change_t change = makeChange (cents);
  printChange (&change);

  return EXIT_SUCCESS; 
}

/************************************************************/

void
printIntro ()
{
  printf ("This program will determine the minimum number\n");
  printf ("  of quarters, dimes, nickels, and pennies\n");
  printf ("  for a given number of cents.\n\n");
}

/************************************************************/

int
promptForCents ()
{
  printf ("Please enter number of cents ==> ");
  int cents;
  scanf ("%d", &cents);
  
  return cents;
}

/************************************************************/

Change_t
makeChange (int cents)
{
  Change_t change;
  change.half_dollars = cents / 50;
  int centsLeft = cents % 50;
  change.quarters = centsLeft / 25;
  centsLeft = centsLeft % 25;
  change.dimes = centsLeft / 10;
  centsLeft = centsLeft % 10;
  change.nickels = centsLeft / 5;
  change.pennies = centsLeft % 5;

  return change;
}

/************************************************************/

void
printChange (const Change_t* change)
{
  const char* penny = "penny";
  
  printf ("  %d half dollar(s)\n", change->half_dollars);
  printf ("  %d quarter(s)\n", change->quarters);
  printf ("  %d dime(s)\n", change->dimes);
  printf ("  %d nickel(s)\n", change->nickels); 
  printf ("  %d %s\n", change->pennies, (change->pennies == 1) ? penny : "pennies");
  printf ("\n"); 
}

/************************************************************/
