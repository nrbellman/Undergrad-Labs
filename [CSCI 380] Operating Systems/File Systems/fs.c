/*
  Filename    : fs.c
  Author      : Nicholas Bellman
  Course      : CSCI 380
  Assignment  : File Systems
  Description : An implementation of a UNIX-like file system with only one root
                directory and no subdirectories.
*/

#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>

#define FS_NUM_BLOCKS    128
#define FS_MAX_FILE_SIZE 8
#define FS_MAX_FILENAME  16
#define FS_MAX_FILES     16
#define FS_BLOCK_SIZE    1024

struct fs_t
{
  int fd;
};

struct inode {
  char name[16];        //file name
  int size;             // file size (in number of blocks)
  int blockPointers[8]; // direct block pointers
  int used;             // 0 => inode is free; 1 => in use
};

// open the file with the above name
void
fs_open (struct fs_t *fs, char diskName[16])
{
  fs->fd = open(diskName, O_RDWR);
  if (fs->fd < 0)
  {
    fprintf(stderr, "File system open error: %s -- exiting\n", strerror(errno));
    exit(1);
  }

  printf("'%s' initialized.\n", diskName);
}

// close and clean up all associated things
void
fs_close (struct fs_t *fs)
{
  close(fs->fd);
  printf("File closed.\n");
}

// create a file with this name and this size
void
fs_create (struct fs_t *fs, char name[16], int size)
{
  if (size > FS_MAX_FILE_SIZE)
  {
    printf("File size too big.\n");
    return;
  }
  // high level pseudo code for creating a new file

  // Step 1: check to see if we have sufficient free space on disk by
  // reading in the free block list. To do this:
  // - move the file pointer to the start of the disk file.
  lseek(fs->fd, 0, SEEK_SET);

  // - Read the first 128 bytes (the free/in-use block information)
  char freeBlocks[FS_NUM_BLOCKS];
  read(fs->fd, freeBlocks, FS_NUM_BLOCKS);

  // - Scan the list to make sure you have sufficient free blocks to
  //   allocate a new file of this size
  int count = 0;
  for (int i = 0; i < FS_NUM_BLOCKS; ++i)
  {
    if (freeBlocks[i] == 0)
    {
      ++count;
    }
  }

  if (size > count)
  {
    printf("Not enough free space.\n");
    return;
  }
  
  // Step 2: we look  for a free inode on disk
  // - Read in a inode
  struct inode node;
  lseek(fs->fd, 128, SEEK_SET);

  // - check the "used" field to see if it is free
  int index = -1;
  for (int i = 0; i < 16; ++i)
  {
    if (!strcmp(node.name, name))
    {
      printf("File name must be unique.\n");
      return;
    }

    read(fs->fd, &node, sizeof(node));
    if (node.used == 0)
    {
      index = i;
      break;
    }
  }

  if (index < 0)
  {
    printf("Unable to find a free node.\n");
    return;
  }
  // - If not, repeat the above two steps until you find a free inode
  // - Set the "used" field to 1
  node.used = 1;

  // - Copy the filename to the "name" field
  strncpy(node.name, name, FS_MAX_FILENAME);

  // - Copy the file size (in units of blocks) to the "size" field
  node.size = size;

  // Step 3: Allocate data blocks to the file
  // - Scan the block list that you read in Step 1 for a free block
  // - Once you find a free block, mark it as in-use (Set it to 1)
  // - Set the blockPointer[i] field in the inode to this block number.
  // - repeat until you allocated "size" blocks
  for (int i = 0; i < size; ++i)
  {
    for (int j = 0; j < FS_NUM_BLOCKS; ++j)
    {
      if (freeBlocks[j] == 0)
      {
        freeBlocks[j] = 1;
        node.blockPointers[i] = j;
        break;
      }
    }
  }

  // Step 4: Write out the inode and free block list to disk
  // - Move the file pointer to the start of the file
  lseek(fs->fd, 0, SEEK_SET);
  
  // - Write out the 128 byte free block list
  write(fs->fd, freeBlocks, 128);
  
  // - Move the file pointer to the position on disk where this inode was stored
  lseek(fs->fd, 128 + (56 * index), SEEK_SET);
  
  // - Write out the inode
  write(fs->fd, &node, sizeof(node));
}

// Delete the file with this name
void
fs_delete (struct fs_t *fs, char name[16])
{

  // Step 1: Locate the inode for this file
  //   - Move the file pointer to the 1st inode (129th byte)
  lseek(fs->fd, 128, SEEK_SET);

  //   - Read in a inode
  struct inode node;
  int foundIndex = -1;

  for (int i = 0; i <= FS_MAX_FILES; ++i)
  {
    if (i == FS_MAX_FILES)
    {
      printf("File not found.\n");
    }

    read(fs->fd, &node, sizeof(node));
    if (node.used == 1)
    {
      if (!strcmp(node.name, name))
      {
        foundIndex = i;
        break;
      }
    }
  }

  //   - If the iinode is free, repeat above step.
  //   - If the iinode is in use, check if the "name" field in the
  //     inode matches the file we want to delete. IF not, read the next
  //     inode and repeat
  if (foundIndex < 0)
  {
    printf("File not found for deletion.\n");
    return;
  }

  // Step 2: free blocks of the file being deleted
  // Read in the 128 byte free block list (move file pointer to start
  //   of the disk and read in 128 bytes)
  // Free each block listed in the blockPointer fields
  char freeBlocks[FS_NUM_BLOCKS];
  lseek(fs->fd, 0, SEEK_SET);
  read(fs->fd, freeBlocks, FS_NUM_BLOCKS);
  for (int i = 0; i < node.size; ++i)
  {
    freeBlocks[node.blockPointers[i]] = 0;
  }

  // Step 3: mark inode as free
  // Set the "used" field to 0.
  node.used = 0;

  // Step 4: Write out the inode and free block list to disk
  // Move the file pointer to the start of the file
  lseek(fs->fd, 0, SEEK_SET);

  // Write out the 128 byte free block list
  write(fs->fd, freeBlocks, 128);

  // Move the file pointer to the position on disk where this inode was stored
  lseek(fs->fd, 128 + (56 * foundIndex), SEEK_SET);

  // Write out the inode
  write(fs->fd, &node, sizeof(node));
}

// List names of all files on disk
void
fs_ls (struct fs_t *fs)
{
  // Step 1: read in each inode and print!
  // Move file pointer to the position of the 1st inode (129th byte)
  lseek(fs->fd, 128, SEEK_SET);

  // for each inode:
  //   read it in
  //   if the inode is in-use
  //     print the "name" and "size" fields from the inode
  // end for
  struct inode node;
  for (int i = 0; i < FS_MAX_FILES; ++i)
  {
    read(fs->fd, &node, sizeof(node));
    if (node.used == 1)
    {
      printf("%16s %6dB\n", node.name, node.size * FS_BLOCK_SIZE);
    }
  }
}

// read this block from this file
void
fs_read (struct fs_t *fs, char name[16], int blockNum, char buf[1024])
{
  // Step 1: locate the inode for this file
  //   - Move file pointer to the position of the 1st inode (129th byte)
  lseek(fs->fd, 128, SEEK_SET);
  struct inode node;

  //   - Read in a inode
  for (int i = 0; i <= FS_MAX_FILES; ++i)
  {
    if (i == FS_MAX_FILES)
    {
      printf("Could not find.\n");
    }

    read(fs->fd, &node, sizeof(node));

  //   - If the inode is in use, compare the "name" field with the above file
    if (node.used == 1)
    {
      if (!strcmp(node.name, name))
      {
        if (blockNum >= node.size)
        {
          printf("Invalid blockNum size.\n");
          return;
        }
        
        int actualBlockNum = node.blockPointers[blockNum];
        lseek(fs->fd, actualBlockNum * FS_BLOCK_SIZE, SEEK_SET);
        read(fs->fd, buf, FS_BLOCK_SIZE);
        break;
      }
    }

    lseek(fs->fd, sizeof(node), SEEK_CUR);

  //   - If the file names don't match, repeat
  }
  // Step 2: Read in the specified block
  // Check that blockNum < inode.size, else flag an error
  // Get the disk address of the specified block
  // move the file pointer to the block location
  // Read in the block! => Read in 1024 bytes from this location into the buffer
  // "buf"
}

// write this block to this file
void
fs_write (struct fs_t *fs, char name[16], int blockNum, char buf[1024])
{

  // Step 1: locate the inode for this file
  // Move file pointer to the position of the 1st inode (129th byte)
  // Read in a inode
  // If the inode is in use, compare the "name" field with the above file
  // If the file names don't match, repeat

  // Step 2: Write to the specified block
  // Check that blockNum < inode.size, else flag an error
  // Get the disk address of the specified block
  // move the file pointer to the block location
  // Write the block! => Write 1024 bytes from the buffer "buf" to this location

  lseek(fs->fd, 128, SEEK_SET);
  struct inode node;

  for (int i = 0; i <= FS_MAX_FILES; ++i)
  {
    if (i == FS_MAX_FILES)
    {
      printf("Could not find.\n");
      return;
    }

    read(fs->fd, &node, sizeof(node));
    if (node.used == 1)
    {
      if (!strcmp(node.name, name))
      {
        if (blockNum >= node.size)
        {
          printf("Invalid blockNum size.");
          return;
        }

        int actualBlockNum = node.blockPointers[blockNum];
        lseek(fs->fd, actualBlockNum * FS_BLOCK_SIZE, SEEK_SET);
        write(fs->fd, buf, FS_BLOCK_SIZE);
        break;
      }
    }

    lseek(fs->fd, sizeof(node), SEEK_CUR);
  }
}

// REPL entry point
void
fs_repl ()
{
  // Get and open disk name
  struct fs_t fs = {.fd = 0};
  char diskName[16];
  fgets(diskName, sizeof(diskName), stdin);
  strtok(diskName, "\n");
  fs_open(&fs, diskName);

  // Create a dummy buffer of 1's
  char dummybuf[FS_BLOCK_SIZE];
  for (int i = 0; i < FS_BLOCK_SIZE; ++i)
  {
    dummybuf[i] = 1;
  }

  char command[32];
  while (fgets(command, sizeof(command), stdin))
  {
    strtok(command, "\n");
    int size;
    char str[16];

    if (sscanf(command, "C %16s %d", str, &size) == 2)
    {
      printf("Creating file %s.\n", str);
      fs_create(&fs, str, size);
    }
    else if (sscanf(command, "D %16s", str) == 1)
    {
      printf ("Deleting file %s.\n", str);
      fs_delete(&fs, str);
    }
    else if (strcmp(command, "L") == 0)
    {
      printf("Listing all files:\n");
      fs_ls(&fs);
    }
    else if (sscanf(command, "R %16s %d", str, &size) == 2)
    {
      printf("Reading file %s.\n", str);
      fs_read(&fs, str, size, dummybuf);
    }
    else if (sscanf(command, "W %16s %d", str, &size) == 2)
    {
      printf("Writing file %s.\n", str);
      fs_write(&fs, str, size, dummybuf);
    }
    else
    {
      printf ("Invalid command.\n");
    }
  }
}
