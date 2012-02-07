// main.cpp - main entry point for BattleShip code implementation
// Written by Christian Yoon
/* ----------------------Important notes--------------------------
I had tried to implement some sort of counter that would
tell the player when he/she sunk an enemy ship. Sadly,
I could not get the functions to work, so I just left them out
as they weren't really important, nor one of my goals. Also,
I decided to comment out the throw/catch part of the game,
even though it was one of my goals, because starting the game over
every time there was a mis-input seemed kind of ridiculous.
I still left them in, so that my goal was met; they're just not
actually being used. Finally, the GUI I had in mind for my goals
was to use the console as a sort of text GUI. I met this goal, and
I believe it to be valid. The grids appear for the player, and the 
game is graphically, albeit simply, presented. That all said,
Enjoy playing Battleship.
----------------------------------------------------------------*/

#include "BattleShip.h"

void main()
{
	//try
	//{
		Grid gridP, gridC;

		Reset(gridP, gridC);

		Menu(gridP, gridC);
	//}

	/*catch (char* error)
	{
		cout << "Error Occurred: " << error << endl;
	}*/

	system("pause");
}