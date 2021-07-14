/*
 * Filename   : MySystem.c
 * Author     : Nicholas Bellman
 * Course     : CSCI 380
 * Semester   : Spring 2021
 * Assignment : Lab07 Redirection
 */

/* =========================== Include Statements =========================== */

// errno
#include <errno.h>

// open()
#include <fcntl.h>

// fprintf(), printf()
#include <stdio.h>

// exit()
#include <stdlib.h>

// strerror()
#include <string.h>

// wait()
#include <sys/wait.h>

// close(), dup2(), execv(), fork(), wait()
#include <unistd.h>

/* =============================== Prototypes =============================== */

int mysystem (char* command);

/* ========================================================================== */

int 
main(int argc, char** argv)
{
    // Makes sure that there is a command to execute.
    if (argc != 2)
    {
        fprintf(stderr, "Format : ./MySystem <command>\n");
        exit(-1);
    }
    
    int exitStatus = mysystem(argv[1]);
    printf("Exit Status: %d\n", exitStatus);

    return 0;
}

int 
mysystem(char* command)
{
    // Forks a child process and stores the child's process id in a variable.
    pid_t pid = fork();
    // Checks for an error during the fork.
    if (pid < 0)
    {
        fprintf(stderr, "Fork Error : (%s)\n", strerror(errno));
        exit(-1);
    }
    
    // Code to run in the child process.
    if (pid == 0)
    {
        // Opens the destination file for write only, creates a new file if none
        // exists.
        int fd = open("System.out", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
        if (fd < 0)
        {
            fprintf(stderr, "Open Error : (%s)\n", strerror(errno));
            exit(-1);
        }

        // Redirects stdout to System.out.
        dup2(fd, STDOUT_FILENO);

        // Closes System.out
        close(fd);
        
        // Creates an argument array to pass into execv.
        char* const argv[] = {"/bin/sh", "-c", command, NULL};
        // Runs the command.
        int execError = execv("/bin/sh", argv);
        // Checks if exec failed.
        if (execError < 0)
        {
            fprintf(stderr, "Exec Error : (%s)\n", strerror(errno));
            exit(-1);
        }
    }
    
    // Wait on child.
    int wstatus;
    waitpid(pid, &wstatus, 0);
    
    // Checks if the child exited normally and returns the exit status.
    if (WIFEXITED(wstatus))
    {
        return WEXITSTATUS(wstatus);
    }
    
    // Checks if the child exited abnormally and returns the status returned by
    // the shell (the terminating signal).
    if (WIFSIGNALED(wstatus))
    {
        return WTERMSIG(wstatus);
    }

    return 0;
}
