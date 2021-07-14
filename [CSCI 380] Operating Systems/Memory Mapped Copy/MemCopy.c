/*
 * Filename   : MemCopy.c
 * Author     : Nicholas Bellman
 * Course     : CSCI 380
 * Assignment : Lab05 Memory Mapped Copy
 */

/* ===== Include Statements ===== */
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

/* ===== Main ===== */
int
main (int argc, char** argv)
{
    // Opens source file as read only. Prints an error message if failed.
    int sourceFile = open(argv[1], O_RDONLY);
    if (sourceFile < 0)
    {
        fprintf(stderr, "open error - source file: (%s)", strerror(errno));
        exit(-1);
    }

    // Opens destination file as read/write. Prints an error message if failed.
    int destFile = open(argv[2], O_RDWR, 0600);
    if (destFile < 0)
    {
        fprintf(stderr, "open error - destination file: (%s)", strerror(errno));
        exit(-1);
    }


    // Gets the size of 'sourceFile' and checks if there was an error getting
    // the information.
    struct stat fileInfo;
    int fstat_err = fstat(sourceFile, &fileInfo);
    if (fstat_err < 0)
    {
        fprintf(stderr, "fstat error: (%s) --exiting\n", strerror(errno));
        exit(-1);
    }
    off_t size = fileInfo.st_size;

    // Sets the size of 'destFile'. Prints an error message if failed.
    int ft = ftruncate(destFile, size);
    if (ft < 0)
    {
        fprintf(stderr, "ftruncate error: (%s) --exiting\n", strerror(errno));
        exit(-1);
    }


    // Creates a new mapping in virtual memory for the source file.
    void* sourceMap = mmap(NULL, size, PROT_READ, MAP_PRIVATE, sourceFile, 0);
    if (sourceMap < 0)
    {
        fprintf(stderr, "mmap error - source file: (%s)", strerror(errno));
        exit(-1);
    }

    // Creates a new mapping in virtual memory for the destination file.
    void* destMap = mmap(NULL, size, PROT_WRITE, MAP_SHARED, destFile, 0);
    if (destMap < 0)
    {
        fprintf(stderr, "mmap error - destination file: (%s)", strerror(errno));
        exit(-1);
    }


    // Copies the bytes of source to dest.
    memcpy(destMap, sourceMap, size);


    // Unmaps the memory given to the source file and the destination file.
    int sourceUnmap = munmap(sourceMap, size);
    if (sourceUnmap < 0)
    {
        fprintf(stderr, "munmap error - source file: (%s)", strerror(errno));
        exit(-1);
    }

    int destUnmap = munmap(destMap, size);
    if (destUnmap < 0)
    {
        fprintf(stderr, "munmap error - destination file: (%s)", 
                strerror(errno));
        exit(-1);
    }

    // Closes the source file and destination file. Prints an error if either
    // fail.
    int destClose = close(destFile);
    if (destClose < 0)
    {
        fprintf(stderr, "close error - destination file: (%s)", 
                strerror(errno));
        exit(-1);
    }

    int sourceClose = close(sourceFile);
    if (sourceClose < 0)
    {
        fprintf(stderr, "close error - source file: (%s)", strerror(errno));
        exit(-1);
    }

    return 0;
}