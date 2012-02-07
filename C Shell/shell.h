#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <dirent.h>
#include <regex.h>

#define MAX_LINE 50

// splits command line arguments into an array of strings
int SplitString( char *tempLine, char ***cmd );

// runs the cmd entered by the user with all the arguments
void RunCommand( char *cmd[], int numArgs, pid_t pid );

// detects if a special character is present in a command
void SpecialCharacter( char ***cmd, int *numArgs );

// if a special character is present int a command, the command is replaced by matching files
void ReplaceCommand( char ***cmd, char ***tempCmd, int index, int *counter, int spIndex );

