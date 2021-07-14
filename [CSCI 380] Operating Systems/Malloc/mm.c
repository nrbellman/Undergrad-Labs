/*
 * Filename   : mm.c
 * Author     : Nicholas Bellman
 * Course     : CSCI 380
 * Assignment : Malloc Lab
 * Description: Create a basic memory allocator to resemble the C
 *              library's malloc, free, and realloc functions.
 */

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <unistd.h>

#include "memlib.h"
#include "mm.h"

/****************************************************************/
// Useful type aliases

typedef uint64_t word;
typedef uint32_t tag;
typedef uint8_t  byte;
typedef byte*    address; 

/****************************************************************/
// Useful constants

const uint8_t WORD_SIZE = sizeof (word);    // 8 bytes
const uint8_t DWORD_SIZE = WORD_SIZE * 2;  // 16 bytes

static address heap_listp;

/****************************************************************/
// Inline functions

// Get the header tag for block of memory given base ptr
static inline tag*
header(address p)
{
  return (tag *) (p - sizeof(tag));
}

// Check to see if a block of memory is allocated
static inline bool
isAllocated(address p)
{
  return *header(p) & (tag)1;
}

// Get the size of the block in words
static inline uint32_t
sizeOf(address p)
{
  return *header(p) & (tag)(~1);
}

// Return address of the footer
static inline tag*
footer(address p)
{
  return (tag *) (p + (sizeOf(p) * WORD_SIZE) - WORD_SIZE);
}

// Return base ptr of next block
static inline address
nextBlock(address p)
{
  return (address) (footer(p)) + WORD_SIZE;
}

// Get ptr to the previous block's footer
static inline tag*
prevFooter(address p)
{
  return (tag *) (p - WORD_SIZE);
}

// Get pointer to the next blocks' header
static inline tag*
nextHeader(address p)
{
  return (tag *) (header(nextBlock(p)));
}

// Get the base ptr to the previous block
static inline address
prevBlock(address p)
{
  return p - (sizeOf(p - sizeof(tag)) * WORD_SIZE);
}

// Place tag information given tag
static inline void
placeTag(tag* t, uint32_t size, bool alloc)
{
  *t = (tag)(size | alloc);
}

// Make a block
// Param order: baseBtr, size, allocated
static inline void
makeBlock(address p, uint32_t size, bool alloc)
{
  placeTag(header(p), size, alloc);
  placeTag(footer(p), size, alloc);
}

// Toggle whether a block is allocated or not
static inline void
toggleBlock(address p)
{
  *header(p) ^= 1;
  *footer(p) ^= 1;
}

// Get the number of words needed given the space in bytes
// Double word aligned
static inline uint32_t
wordsNeeded(uint32_t space)
{
  uint32_t const OVERHEAD = 2 * sizeof(tag);
  uint32_t const SIZE = space + OVERHEAD;
  uint32_t const NUM_DWORDS = (SIZE + (uint32_t)(DWORD_SIZE - 1)) / DWORD_SIZE;
  uint32_t const NUM_WORDS = NUM_DWORDS * 2;

  return NUM_WORDS;
}

// Given a ptr check to see if the surrounding blocks are free
//    If they are merge them into a single larger free block.
static inline void*
coalesce(address bp)
{
  bool prevAlloc = *(bp - WORD_SIZE) & (tag)1;
  bool nextAlloc = isAllocated(nextBlock(bp));
  uint32_t size = sizeOf(bp);

  // Both surrounding blocks are allocated, nothing to do
  if(prevAlloc && nextAlloc)
  {
    return bp;
  }
  // Only the next block is free
  else if(prevAlloc && !nextAlloc)
  {
    size += sizeOf(nextBlock(bp));
    makeBlock(bp, size, 0);
  }
  // Only the previous block is free
  else if(!prevAlloc && nextAlloc)
  {
    size += sizeOf(prevBlock(bp));
    makeBlock(prevBlock(bp), size, 0);
    bp = prevBlock(bp);
  }
  // Both surrounding blocks are free
  else
  {
    size += sizeOf(prevBlock(bp)) + sizeOf(nextBlock(bp));
    makeBlock(prevBlock(bp), size, 0);
    bp = prevBlock(bp);
  }

  return bp;
}

/****************************************************************/
// Function declarations

void*
extend_heap(uint32_t words);

int
mm_check(void);

uint32_t
wordsNeeded(uint32_t space);

/****************************************************************/
// Non-inline functions

int
mm_init (void)
{
  // Num words of init heap
  const uint32_t numWords = 4;
  // Create init empty heap
  if((heap_listp = mem_sbrk((int)(numWords * WORD_SIZE * 2))) == (void *)-1)
  {
    return -1;
  }
  // Add padding
  heap_listp += WORD_SIZE;
  // Create heap head sentinel tag
  placeTag((tag *)heap_listp, 0, 1);
  //*heap_listp = 0 | 1;
  // Position header to where the basePtr of the first block will be
  heap_listp += WORD_SIZE;
  // Create initial block
  makeBlock(heap_listp, 2 * (numWords - 1), 0);
  // Create heap end sentinel
  placeTag(nextHeader(heap_listp), 0, 1);
  
  return 0;
}

/****************************************************************/

// Extend the heap by the given number of words while maintaining alignment
// Return the address of the free block of memory created by extending the heap
// If blocks are able to be coalesced then the address of the free block
// created by coalescing is returned instead.
void*
extend_heap(uint32_t words)
{
  address bp;

  // Get more space on the heap
  uint32_t size = words * WORD_SIZE;
  if((long)(bp = mem_sbrk((int)size)) == -1)
  {
    return NULL;
  }

  // Make a block of extension size
  // Adjust words to be even num of words
  makeBlock((address)bp, words, 0);
  // Create new epilogue sentinel
  placeTag(nextHeader(bp), 0, 1);

  return coalesce(bp);
}

/****************************************************************/

void*
mm_malloc(uint32_t size)
{
  fprintf(stderr, "allocate block of size %u\n", size);
  // Check if size is 0
  if(size == 0)
  {
    return NULL;
  }

  // Calculate how many words are neededa
  uint32_t newWords = wordsNeeded(size);

  // Keep track of the last block size to possibly save some heap space later
  uint32_t initSize = 0;

  // Loop through the heap looking for space
  for(address p = heap_listp; sizeOf(p) == 0; p = nextBlock(p))
  {
    // If the block is allocated or not big enough then skip it
    if(isAllocated(p) || sizeOf(p) < newWords)
    {
      continue;
    }

    // Get the inital size of the free block
    initSize = sizeOf(p);
    // Make the allocated block
    makeBlock(p, newWords, 1);
    // Set the rest as unallocated
    makeBlock(nextBlock(p), initSize - newWords, 0);

    return p;
  }


  address space = extend_heap((newWords - initSize));
  initSize = sizeOf(space);
  makeBlock(space, newWords, 1);
  makeBlock(nextBlock(space), initSize - newWords, 0);
  return space;
}

/****************************************************************/

void
mm_free (void *ptr)
{
  fprintf(stderr, "free block at %p\n", ptr);
  // Set the header and footer of the block to be unallocated
  toggleBlock(ptr);
  // Coalesce surrounding free blocks
  coalesce(ptr);
}

/****************************************************************/

void*
mm_realloc (void *ptr, uint32_t size)
{
  fprintf(stderr, "realloc block at %p to %u\n", ptr, size);
  // If the ptr is NULL then just malloc
  if(ptr == NULL)
  {
    return mm_malloc(size);
  }
  if(size == 0)
  {
    mm_free(ptr);
    return NULL;
  }

  // Store original ptr
  address original = ptr;

  // Store the amount of free space we have
  uint32_t freeSpace = sizeOf(original);

  // Check to see if surrounding blocks are free for space optimization
  // Prev check
  if(!isAllocated((address)header(original)))
  {
    ptr = prevBlock(original);
    freeSpace += sizeOf(ptr);
  }
  // Next check
  if(!isAllocated(nextBlock(original)))
  {
    freeSpace += sizeOf(nextBlock(original));
  }

  // Get the number of words the new size is
  uint32_t newWords = wordsNeeded(size);

  // Check to see if we have the space in the original, if so then just malloc
  if(newWords <= freeSpace)
  {
    // Copy data to start ptr (in case it moved after manual coalesce check)
    memcpy(ptr, original, sizeOf(original) * WORD_SIZE - WORD_SIZE);
    // Make a new block of new size
    makeBlock(ptr, newWords, 1);
    // Make free block for remaining space if there is any
    int remaining = (int)(freeSpace - newWords);
    if(remaining >= 0)
    {
      makeBlock(nextBlock(ptr), freeSpace - newWords, 0);
    }
    // Return base ptr
    return ptr;
  }

  // If there is no space then find new space
  // Allocate new space
  address newBlock = mm_malloc(size);
  // Copy memory from original block to new block
  memcpy(newBlock, original, sizeOf(original) * WORD_SIZE - WORD_SIZE);
  // Free the old block
  mm_free(original);

  // Return the base ptr
  return newBlock;
}

/****************************************************************/

// Check the consistency of the heap to make sure it is valid
int
mm_check(void)
{
  // Copy starting point for manipualtion
  address ptr = heap_listp;
  // Status checks
  bool lastAlloc = isAllocated(heap_listp);
  address lastFooter = (address)footer(heap_listp);
  // Push ptr up one since we already got stat
  ptr = nextBlock(ptr);

  // Loop through heap and check consistency
  while(sizeOf(ptr) != 0)
  {
    // Check to make sure we don't have uncoalesced blocks
    if(!isAllocated(ptr) && !lastAlloc)
    {
      return 1;
    }

    // Check to make sure the current block isn't overlapping the previous
    if(ptr < lastFooter)
    {
      return 2;
    }

    // Make sure block is double word aligned
    if((long)ptr % 16 != 0)
    {
      printf("%p not alligned\n", ptr);
      return 3;
    }

    lastAlloc = isAllocated(ptr);
    lastFooter = (address)footer(ptr);
    ptr = nextBlock(ptr);
  }

  return 0;
}

/****************************************************************/