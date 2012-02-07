// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 27th, 2010
// Description: Class that tests the functionality of the Monopoly Game Class
//				 
public class GameTestDriver
{
	public static void main(String args[])
	{
		Game monopoly;					// A game of Monopoly in text form
		
		monopoly = new Game();
		
		/*
		System.out.println("INITIAL SETUP");
		monopoly.PlayerStatus();
		monopoly.PrintBoard();*/

		// Choosing to start the game with the Fast Start option to expedite demo and testing		

		System.out.println("F A S T          S T A R T");
		System.out.println("RANDOMIZING ORDER TO DETERMINE WHO GOES FIRST");
		monopoly.RandomizeOrder();
		monopoly.FastStart();

		/*
		System.out.println("******UPDATE********");
		monopoly.PlayerStatus();
		monopoly.PrintBoard();*/
			
		monopoly.DisplayLogo();
		
		while(monopoly.GetGameState())				// Runs the game while game state is true
		{
			monopoly.DisplayGameMenu();
			
			if (monopoly.GetCurNum() == 1)
			{
				monopoly.SetCurPlayer(0);
			}
			else
			{
				monopoly.SetCurPlayer(monopoly.GetCurNum() + 1);
			}
		}
	}
}
