#include "shell.h"

int main( int argc, char *argv[] )
{
	int quit = 0;		// will be 1 if user chooses to exit shell
	int numArgs = 0;	// number of arguments on command line
	int counter = 1;	// number of commands entered total
	char *tempLine;		// input by user into the shell
	char *cmd[] = calloc( MAX_LINE, sizeof(char*) );		// array of pointers to strings
	pid_t pid;		// pid for fork

	tempLine = calloc(MAX_LINE, sizeof(char));

	do			// run shell interface and handle commands
	{
		printf("CYTB %d@ ", counter);		// shell prompt
		fgets(tempLine, MAX_LINE, stdin);	// get the input line
		counter++;				

		numArgs = SplitString(tempLine, (char ***)&cmd);   // splits the input into an array of strings to evaluate

		SpecialCharacter((char ***)&cmd, &numArgs);	// will insert any string or character substitutions

        if( (strncmp(cmd[0], "exit", 5)) == 0 )	// if command is exit, shell will exit
        {
	        quit = 1;
        }

		pid = fork();
		
		if( pid < 0 )		// forking process failed
		{
			printf("Error occurred in forking process.\n");
		}
		else if( pid == 0 )	// child process
		{
			RunCommand(cmd, numArgs, pid);	
			exit(0);
		}
		else			// parent process
		{
			waitpid(pid, 0, 0);		// wait for child to finish
			RunCommand(cmd, numArgs, pid);
			//if( quit == 1 )		// user entered exit as command
			//{
			//	exit(0);
			//}
		}
		
		printf("quit: %d\n", quit);
	}
	while( quit != 1 );
	
	free(tempLine);			// deallocate memory
	
	return 0;
}

int SplitString( char *tempLine, char ***cmd )
{
	int length;			// length of tempLine up to first '\0'
	int numArgs = 0;		// the total number of arguments typed in by user
	char *tempArg;			// temp string to hold each individual argument

	//tempArg = calloc(MAX_LINE, sizeof(char));
	
	length = strlen(tempLine);
	
	// Replaces end of string with null character
	for( int j = 0; j < length; j++ )
	{
		if( tempLine[j] == '\n' )
		{
			tempLine[j] = ' ';
			j = length;
		}
	}

	// Delete leading spaces

	// Parses string into arguments and populates cmd
	while( (tempArg = strchr(tempLine, ' ')) )
	{
		*tempArg = '\0';
		cmd[numArgs++] = strdup(tempLine);
		tempLine = tempArg + 1;
		while( *tempLine && (*tempLine == ' '))
			tempLine++;
	}
	cmd[numArgs] = NULL;
	
	return numArgs;		// return total number of arguments
}

void RunCommand( char *cmd[], int numArgs, pid_t pid )
{
	char *home;		// home will hold the environment variable for users root directory
	int dirErr;		// directory error has occurred
	
	if ( (pid ==0) && ((strncmp( cmd[0], "#", 1 )) == 0) );				// line is a comment
	else if( (pid ==0) && ((strncmp( cmd[0], "echo", 5 )) == 0) )		// running echo without running actual program 
	{
		for( int i = 1; i < numArgs; i++ )		// print all arguments after the echo command
		{
			printf("%s ", cmd[i]);
		}
		printf("\n");					// add new line after echo is done
	}
	else if( (strncmp( cmd[0], "cd", 3 )) == 0 )		// cd
	{
		if( pid != 0 )				// only need to change directory in parent process
		{
			if( numArgs == 2 )		// directory is to be changed to given name
			{
				dirErr = chdir( cmd[1] );

				if( dirErr == -1 )
				{	
					printf("Directory could not be changed. \n");
				}	
			}
			else if( numArgs == 1 )		// directory is changed to users root directory
			{
				home = calloc( MAX_LINE, sizeof(char) );

				home = getenv( "HOME" );

				dirErr = chdir( home );

				printf("Root Directory.\n");
			
				if( dirErr == -1 )	// function failed
				{
					printf("Directory could not be changed. \n");
				}

				free(home);		
			}
			else				// user entered too many arguments
			{
				printf("Improper number of arguments entered.\n");
			}	
		}
	}
	else if( (pid == 0) && ((strncmp( cmd[0], "exit", 5 )) == 0) )
	{
		printf("Exit shell. \n");
	}
	else							// command can simply be run with execvp
	{
		if( pid == 0 )			// only execute in child, or program will be replaced by command program
		{
			execvp(cmd[0], cmd);
		}
	}
}

void SpecialCharacter( char ***cmd, int *numArgs )
{
	char *tempCmd[] = calloc( MAX_LINE, sizeof(char*) );		// temp variable to hold new commands
	int tempNum = *numArgs;						// num of args in cmd
	int counter = 1;						// counter to insert into tempCmd
	int hasChar = 0;						// will signify if cmd has special character
	char *temp;

	temp = calloc( MAX_LINE, sizeof(char) );
	tempCmd[0] = *cmd[0];

	for(int i = 1;	i < tempNum; i++)
	{
		hasChar = 0;
		temp = cmd[i];

		for( int j = 0; j < strlen(temp); j++ )
		{
			if( (temp[j] == '*') || (temp[j] == '?') )		// if a special char is found, replace
			{
				ReplaceCommand( cmd, (char ***)&tempCmd, i, &counter, j );	// add alphabetically sorted list of commands
				hasChar = 1;
				j = strlen(*cmd[i]);				// exit loop
			}
		}

		if( !hasChar )		// if command doesn't have special char, simply add it to tempCmd
		{
			tempCmd[counter++] = *cmd[i];
		}
	}	

	//printf("before free(cmd): %s\n", tempCmd[1]);
	//free(cmd);
	*cmd = tempCmd;
	
	
	*numArgs = counter;
	//free(tempCmd);
	free(temp);
}

void ReplaceCommand( char ***cmd, char ***tempCmd, int cmdIndex, int *counter, int spIndex)
{
	DIR *dirp;			// will hold open directory
	struct dirent *dp;	//
	char *directory;	// current directory
	regex_t rexp;		// will hold compiled regular expression
	char *temp;			// temp variable to hold cmd
	char *tempRE;		// will hold RE that is built by self
	int j = 0;			// will be index for tempRE
	
	// allocate memory
	directory = calloc( MAX_LINE, sizeof(char) );
	tempRE = calloc( MAX_LINE, sizeof(char) );
	temp = calloc( MAX_LINE, sizeof(char) );
	
	temp = cmd[cmdIndex];

	for(int i = 0; i < strlen(temp); i++)
	{
		if( temp[i] == '?' )
		{
			tempRE[j++] = '.';
		}
		else if( temp[i] == '*' )
		{
			tempRE[j++] = '.';
			tempRE[j++] = '*';
		}
		else if( temp[i] == '.' )
		{
			tempRE[j++] = '[';
			tempRE[j++] = '.';
			tempRE[j++] = ']';
		}
		else
		{
			tempRE[j++] = temp[i];
		}
	}
	
	regcomp( &rexp, tempRE, REG_EXTENDED);	// compiled regExp
	
	// get users current directory and open directory
	directory = getenv("PWD");		
	dirp = opendir( directory );
	
	do
	{
		if( (dp = readdir(dirp)) != NULL )
		{
			if( (regexec(&rexp, dp->d_name, (size_t) 0, NULL, 0)) == 0 ) // I AM MEAN CODE AND I AM BREAKING YOUR PROGRAM.
			{
				tempCmd[*counter++] = strdup(dp->d_name);
			}
		}
	} while( dp != NULL );

	closedir(dirp);			// close directory
	
	// deallocate memory
	free(directory);	
	//free(rexp);
	free(temp);
}



