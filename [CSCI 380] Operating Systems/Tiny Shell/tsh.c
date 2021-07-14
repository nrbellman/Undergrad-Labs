/*
 * tsh - A tiny shell program
 *
 * Filename   : tsh.c
 * Author     : Nicholas Bellman
 * Course     : CSCI 380
 * Assignment : Lab05 Shell
 *
 *
 * Most of the work is handled by the SIGCHLD handler. The SIGTSTP handler and 
 * the SIGINT handler intercepted their respective signals and handed them off
 * to the running process, which would then be dealt by the SIGCHLD handler. In
 * the SIGCHLD handler, I first saved 'errno' into a separate variable in the
 * event that 'errno' got set to another value during execution. After execution
 * 'errno' was reset to its previous value. I then waited on any child processes
 * and looped until no more changes were detected by 'waitpid'.
 *
 * For the options I passed to 'waitpid', I used WNOHANG so any processes
 * running in the background wouldn't cause the shell to stall. WUNTRACED was
 * used so the child handler would detect any changes caused by SIGTSPT.
 *
 * Waiting for a foregorund job was done with an infinite loop that would break
 * whenever sigsuspend detected a change in the child.
 */

/*
 *******************************************************************************
 * INCLUDE DIRECTIVES
 *******************************************************************************
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include <errno.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

/*
 *******************************************************************************
 * TYPE DEFINITIONS
 *******************************************************************************
 */

typedef void handler_t (int);

/*
 *******************************************************************************
 * PREPROCESSOR DEFINITIONS
 *******************************************************************************
 */

// max line size 
#define MAXLINE 1024 
// max args on a command line 
#define MAXARGS 128

/*
 *******************************************************************************
 * GLOBAL VARIABLES
 *******************************************************************************
 */

// defined in libc
extern char** environ;   

// command line prompt 
char prompt[] = "tsh> ";

// for composing sprintf messages
char sbuf[MAXLINE];

// PID of the foreground process
volatile pid_t g_runningPid = 0;
// PID of the suspended process
volatile pid_t g_suspendedPid = 0;

// Signal Blockers
sigset_t mask;
sigset_t prev;

/*
 *******************************************************************************
 * FUNCTION PROTOTYPES
 *******************************************************************************
 */

int
parseline (const char* cmdline, char**argv);

void
sigint_handler (int sig);

void
sigtstp_handler (int sig);

void
sigchld_handler (int sig);

void
sigquit_handler (int sig);

void
unix_error (char* msg);

void
app_error (char* msg);

handler_t*
Signal (int signum, handler_t* handler);


void
eval (const char* input);

int
builtin_cmd (char** args);

void
waitfg (const pid_t pid);

/*
 *******************************************************************************
 * MAIN
 *******************************************************************************
 */

int
main (int argc, char** argv)
{
  /* Redirect stderr to stdout */
  dup2 (1, 2);

  /* Install signal handlers */
  Signal (SIGINT, sigint_handler);   /* ctrl-c */
  Signal (SIGTSTP, sigtstp_handler); /* ctrl-z */
  Signal (SIGCHLD, sigchld_handler); /* Terminated or stopped child */
  Signal (SIGQUIT, sigquit_handler); /* quit */

  /* TODO -- shell goes here*/
  while (1)
  {
    // Creates an array of size MAXLINE that is used to store input.
    char input[MAXLINE];
    // Checks if the input is the end of file, either naturally or through
    // ctrl-d. 
    if (feof(stdin))
    {
      break;
    }

    // Prints the prompt.
    printf(prompt);
    // Ensures that input is non-NULL.
    if (fgets(input, MAXLINE, stdin) == NULL)
    {
      break;
    }

    eval(input);

    // CLears out any unused data in stdout.
    fflush(stdout);
  }

  /* Quit */
  exit (0);
}

// Evaluates input from the user. If the quit command is recieved, returns 0.
void
eval (const char* input)
{
  // Creates an array of length MAXARGS which is used to hold arguments.
  char* args[MAXARGS];
  int bg = parseline(input, args);

  // Checks if the line inputted is blank.
  if (args[0] == NULL)
  {
    return;
  }
  // Checks if the command is one built into the shell. If it is, the command
  // is run from within builtin_cmd.
  if (builtin_cmd(args))
  {
    return;
  }

  // Blocks SIGCHILD 
  sigemptyset(&mask);
  sigaddset(&mask, SIGCHLD);
  sigprocmask(SIG_BLOCK, &mask, &prev);

  // Forks a new process for the command to run in.
  g_runningPid = fork();
  // Checks for an error while forking.
  if (g_runningPid < 0)
  {
    fprintf(stderr, "fork error: (%s) -- quitting\n", strerror(errno));
    exit(-1);
  }
  // Checks if the process is the child.
  if (g_runningPid == 0)
  {
    // Sets the process group.
    setpgid(0, 0);
    // Unblocks SIGCHILD
    sigprocmask(SIG_SETMASK, &prev, NULL);

    // Runs the given command.
    int ret = execve(args[0], args, environ);
    // Checks if exec failed. If so, prints an error message.
    if (ret < 0)
    {
      fprintf(stderr, "%s: Command not found\n", *args);
      exit(1);
    }

  }

  // Unblocks signal.
  sigprocmask(SIG_SETMASK, &prev, NULL);

  // If the process is not running in the background, waits for it to finish.
  if (!bg)
  {
    waitfg(g_runningPid);
  }
  else
  {
    printf("(%d) %s", g_runningPid, input);
    fflush(stdout);
  }

}

// Checks if the entered command is built in. If so, the command is run and 1 is
// returned. Otherwise, 0 is returned.
int
builtin_cmd (char** args)
{
  // Checks if the command that was input is the 'quit' command.
  if (strcmp(args[0], "quit") == 0)
  {
    exit(0);
  }

  // Checks if the command that was input is the 'fg' command.
  if (strcmp(args[0], "fg") == 0)
  {
    if (g_suspendedPid > 0)
    {
      // Sets the running PID to the one that was suspended.
      g_runningPid = g_suspendedPid;
      // Resets the suspended PID.
      g_suspendedPid = 0;

      // Continues the bg process.
      kill(-g_runningPid, SIGCONT);
      waitfg(g_runningPid);
    }

    return 1;
  }

  return 0;
}

// Waits for the completion of the given process.
void
waitfg (const pid_t pid)
{
  // Waits until a signal is recieved.
  while (g_runningPid != 0)
  {
    sigsuspend(&prev);
  }

}

/*
*  parseline - Parse the command line and build the argv array.
*
*  Characters enclosed in single quotes are treated as a single
*  argument.
*
*  Returns true if the user has requested a BG job, false if
*  the user has requested a FG job.
*/
int
parseline (const char* cmdline, char** argv)
{
  static char array[MAXLINE]; /* holds local copy of command line*/
  char* buf = array;          /* ptr that traverses command line*/
  char* delim;                /* points to first space delimiter*/
  int argc;                   /* number of args*/
  int bg;                     /* background job?*/

  strcpy (buf, cmdline);
  buf[strlen (buf) - 1] = ' ';  /* replace trailing '\n' with space*/
  while (*buf && (*buf == ' ')) /* ignore leading spaces*/
    buf++;

  /* Build the argv list*/
  argc = 0;
  if (*buf == '\'')
  {
    buf++;
    delim = strchr (buf, '\'');
  }
  else
  {
    delim = strchr (buf, ' ');
  }

  while (delim)
  {
    argv[argc++] = buf;
    *delim = '\0';
    buf = delim + 1;
    while (*buf && (*buf == ' ')) /* ignore spaces*/
      buf++;

    if (*buf == '\'')
    {
      buf++;
      delim = strchr (buf, '\'');
    }
    else
    {
      delim = strchr (buf, ' ');
    }
  }
  argv[argc] = NULL;

  if (argc == 0) /* ignore blank line*/
    return 1;

  /* should the job run in the background?*/
  if ((bg = (*argv[argc - 1] == '&')) != 0)
  {
    argv[--argc] = NULL;
  }
  return bg;
}

/*
 *******************************************************************************
 * SIGNAL HANDLERS
 *******************************************************************************
 */

/*
*  sigchld_handler - The kernel sends a SIGCHLD to the shell whenever
*      a child job terminates (becomes a zombie), or stops because it
*      received a SIGSTOP or SIGTSTP signal. The handler reaps all
*      available zombie children, but doesn't wait for any other
*      currently running children to terminate.
*/
void
sigchld_handler (int sig)
{
  // Saves the errno.
  int o_errno = errno;

  // Stores the status and PID information.
  int w_status;
  pid_t w_PID;

  // Loops indefinitely to check for and reap any zombie children.
  while ((w_PID = waitpid(-1, &w_status, WNOHANG | WUNTRACED) > 0))
  {
    // Checks that the process exited normally. If there is an error, a message
    // is printed.
    if (WIFSIGNALED(w_status))
    {
      printf("Job (%d) terminated by signal %d\n", w_PID, 
             WTERMSIG(w_status));
      fflush(stdout);
    }

    if (WIFSTOPPED(w_status))
    {
      printf("Job (%d) stopped by signal %d\n", w_PID, WSTOPSIG(w_status));
      fflush(stdout);
      g_suspendedPid = g_runningPid;
    }

    // Resets the running PID.
    g_runningPid = 0;
  }

  // Resets errno to its previous value.
  errno = o_errno;

  return;
}

/*
 *  sigint_handler - The kernel sends a SIGINT to the shell whenever the
 *     user types ctrl-c at the keyboard.  Catch it and send it along
 *     to the foreground job.
 */
void
sigint_handler (int sig)
{
  //Checks if there is a job running in the foreground.
  if (g_runningPid > 0)
  {
    // Sends the SIGINT signal to the foreground process.
    kill(-g_runningPid, SIGINT);
  }

  return;
}

/*
 *  sigtstp_handler - The kernel sends a SIGTSTP to the shell whenever
 *      the user types ctrl-z at the keyboard. Catch it and suspend the
 *      foreground job by sending it a SIGTSTP.
 */
void
sigtstp_handler (int sig)
{
  //Checks if there is a job running in the foreground.
  if (g_runningPid > 0)
  {
    // Sends the SIGTSTP signal to the foreground process.
    kill(-g_runningPid, SIGTSTP);
  }

  return;
}

/*
 *******************************************************************************
 * HELPER ROUTINES
 *******************************************************************************
 */


/*
 * unix_error - unix-style error routine
 */
void
unix_error (char* msg)
{
  fprintf (stdout, "%s: %s\n", msg, strerror (errno));
  exit (1);
}

/*
*  app_error - application-style error routine
*/
void
app_error (char* msg)
{
  fprintf (stdout, "%s\n", msg);
  exit (1);
}

/*
*  Signal - wrapper for the sigaction function
*/
handler_t*
Signal (int signum, handler_t* handler)
{
  struct sigaction action, old_action;

  action.sa_handler = handler;
  sigemptyset (&action.sa_mask); /* block sigs of type being handled*/
  action.sa_flags = SA_RESTART;  /* restart syscalls if possible*/

  if (sigaction (signum, &action, &old_action) < 0)
    unix_error ("Signal error");
  return (old_action.sa_handler);
}

/*
*  sigquit_handler - The driver program can gracefully terminate the
*     child shell by sending it a SIGQUIT signal.
*/
void
sigquit_handler (int sig)
{
  printf ("Terminating after receipt of SIGQUIT signal\n");
  exit (1);
}
