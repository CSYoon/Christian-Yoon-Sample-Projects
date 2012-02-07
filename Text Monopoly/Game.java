// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class that implements the gameplay of Monopoly

import java.util.Scanner;
import java.lang.String;


public class Game
{
	private final static int BOARDSIZE = 40;// Number of spaces in a Monopoly Game

	private BoardLocation[] board;
	private Player[] players;
	private boolean gameState;				// Whether the game is over or not; true for game 
											// 	continuing,
											//	false for game over.
	private int diceRoll1;					// Current value of the first dice roll, rolled by 
											// 	curPlayer
	private int diceRoll2;					// Current value of the second dice roll, rolled by 
											//	curPlayer
	private int curPlayer;					// Index to hold current player of the players array
	
	public Game()
	// POST: creates an instance of the Game class initializing the board and player arrays
	{
		this.BuildBoard();
		this.SetPlayers();
	}
	
	public void BuildBoard()
	// POST:initializes each BoardLocation on the board; declares players array to the size of however 
	//			many people are playing; Sets gameState to true (active game); sets the curPlayer to 0 
	// 		(index of players)		
	{
		board = new BoardLocation[BOARDSIZE];
		players = new Player[2];
		gameState = true;
		curPlayer = 0;
	
		//Initializing all 40 positions of the Monopoly Board in a parallel array
		board[0] = new CornerSquare("GO", 0);									
		board[1] = new Lot("MEDITERRANEAN AVE", 1, 60, 2, 10, 30, 90, 160, 230, "Dark Purple", 50);
		board[2] = new SpecialLocation("COMMUNITY CHEST", 2);
		board[3] = new Lot("BALTIC AVE", 3, 60, 4, 20, 60, 180, 320, 450, "Dark Purple", 50);
		board[4] = new SpecialLocation("INCOME TAX", 4);	
		board[5] = new Railroad("READING RAILROAD", 5, 200);		
		board[6] = new Lot("ORIENTAL AVE", 6, 100, 6, 30, 90, 270, 400, 550, "Light Blue", 50);	
		board[7] = new SpecialLocation("CHANCE", 7);	
		board[8] = new Lot("VERMONT AVE", 8, 100, 6, 30, 90, 270, 400, 550, "Light Blue", 50);	
		board[9] = new Lot("CONNETICUT AVE", 9, 120, 8, 40, 100, 300, 450, 600, "Light Blue", 50);	
		board[10] = new CornerSquare("Jail/Just Visiting", 10);	
		board[11] = new Lot("ST. CHARLES PLACE", 11, 140, 10, 50, 150, 450, 625, 750, "Light Purple", 
								100);	
		board[12] = new Utility("ELECTRIC COMPANY", 12, 150, 0);	
		board[13] = new Lot("STATES AVE", 13, 140, 10, 50, 150, 450, 625, 750, "Light Purple", 100);	
		board[14] = new Lot("VIRGINIA AVE", 14, 160, 12, 60, 180, 500, 700, 900, "Light Purple", 100);
		board[15] = new Railroad("PENNSYLVANIA RAILROAD", 15, 200);
		board[16] = new Lot("ST. JAMES PLACE", 16, 180, 14, 70, 200, 550, 750, 950, "Orange", 100);
		board[17] = new SpecialLocation("COMMUNITY CHEST", 17);
		board[18] = new Lot("TENNESSEE AVE", 18, 180, 14, 70, 200, 550, 750, 950, "Orange", 100);
		board[19] = new Lot("NEW YORK AVE", 19, 200, 16, 80, 220, 600, 800, 1000, "Orange", 100);
		board[20] = new CornerSquare("FREE PARKING", 20);
		board[21] = new Lot("KENTUCKY AVE", 21, 220, 18, 90, 250, 700, 875, 1050, "Red", 150);
		board[22] = new SpecialLocation("CHANCE", 22);	
		board[23] = new Lot("INDIANA AVE", 23, 220, 18, 90, 250, 700, 875, 1050, "Red", 150);
		board[24] = new Lot("ILLINOIS AVE", 24, 240, 20, 100, 300, 750, 925, 1100, "Red", 150);
		board[25] = new Railroad("B & O RAILROAD", 25, 200);
		board[26] = new Lot("ATLANTIC AVE", 26, 260, 22, 110, 330, 800, 975, 1150, "Yellow", 150);
		board[27] = new Lot("VENTNOR AVE", 27, 260, 22, 110, 330, 800, 975, 1150, "Yellow", 150);
		board[28] = new Utility("WATER WORKS", 28, 150, 0);
		board[29] = new Lot("MARVIN GARDENS", 29, 280, 24, 120, 360, 850, 1025, 1200, "Yellow", 150);
		board[30] = new CornerSquare("GO TO JAIL", 30);
		board[31] = new Lot("PACIFIC AVE", 31, 300, 26, 130, 390, 900, 1075, 1250, "Green", 200);
		board[32] = new Lot("NO. CARONLINA AVE", 32, 300, 26, 130, 390, 900, 1075, 1250, "Green", 200);
		board[33] = new SpecialLocation("COMMUNITY CHEST", 33);
		board[34] = new Lot("PENNSYLVANIA AVE", 34, 320, 28, 150, 450, 1000, 1200, 1400, "Green", 200);
		board[35] = new Railroad("SHORT LINE RAILROAD", 35, 200);
		board[36] = new SpecialLocation("CHANCE", 36);
		board[37] = new Lot("PARK PLACE", 37, 350, 35, 175, 500, 1100, 1300, 1500, "Dark Blue", 200);
		board[38] = new SpecialLocation("LUXURY TAX", 38);
		board[39] = new Lot("BOARDWALK", 39, 400, 50, 200, 600, 1400, 1700, 2000, "Dark Blue", 200);
	}

	public void SetPlayers()
	// POST: initializes each data member of the array to a different token of the monopoly game
	{
		players[0] = new Player("Battleship");
		players[1] = new Player("Thimble");
		//players[2] = new Player("Car");
		//players[3] = new Player("Dog");
	}
	
	public void SetCurPlayer(int curPlayer)
	// PRE: 0 < curPlayer < (players.length()-1)
	// POST: sets class member curPlayer to value passed in parameter
	{
		this.curPlayer = curPlayer;
	}
	
	public void SetGameState(boolean gameState)
	// PRE: gameState has to be initialized
	// POST: sets class member gameState to value passed in parameter
	{
		this.gameState = gameState;
	}
	
	public void RollDice()
	// POST: “Rolls” two “die” and saves their values to the class members diceRoll1 and dice rol
	{
		int diceRoll1;								// variable to hold first dice roll
		int diceRoll2;								// variable to hold second dice roll
		
		diceRoll1 = (int)(Math.random() * 6) + 1;	// uniform random generation to determine diceRoll1
		diceRoll2 = (int)(Math.random() * 6) + 1;	// uniform random generation to determine diceRoll2
		
		SetDiceRoll(diceRoll1, diceRoll2);		// Sets the class members diceRoll1 and diceRoll2
													//  to values passed in parameter
													
		
		players[GetCurNum()].MovePlayer(GetDiceRoll1() + GetDiceRoll2());
													// moves the current player to total of the dice rolls
		
		System.out.println("You rolled a " + GetDiceRoll1() + " and a " + GetDiceRoll2());
													// outputs dice rolls to console
	}
	
	public void SetDiceRoll(int diceRoll1, int diceRoll2)
	// PRE: diceRoll1 and diceRoll2 >= 1 && <=6
	// POST:sets class members diceRoll1 and diceRoll2 to the values passed in the parameter
	//		 in order to save the current dice roll totals of the current instance
	//		 Also sets diceroll values for calculating rent for Utilities
	{
		this.diceRoll1 = diceRoll1;
		this.diceRoll2 = diceRoll2;
	}
	
	public void RandomizeOrder()
	// PRE:	players array has to be initialized
	// POST: randomly alters the players array to generate a random order to determine order of 
	//			gameplay
	{
		int pos1;											// position in players array being swapped
		int pos2;											// position in players array where players[pos1]
															//  is being swapped to
		Player temp;										// temp Player variable to hold instance of
															//  a Player class to use for swapping
		

	
		for (int x = 0; x < 10; x++)						// number of times random number generation is 
		{													// 	called to randomize order of players
	
			pos1 = (int)Math.round(Math.random() * (players.length-1));	// random index generation of 
																			// which player 
																			//  is being swapped
			pos2 = (int)Math.round(Math.random() * (players.length-1));	// random index generation of 
																			//  where players[pos1] is being 
																			// 	swapped to 
			temp = players[pos1];
			players[pos1] = players[pos2];
			players[pos2] = temp;
		}
	}
	
	public void FastStart()
	// PRE:	players and board has to be initialized
	// POST: initially gives players some property to hasten the Game
	//
	{
		((Lot)board[27]).SetPropOwner(players[1]);
		((Lot)board[16]).SetPropOwner(players[1]);
		((Utility)board[12]).SetPropOwner(players[1]);
		
		((Railroad)board[35]).SetPropOwner(players[0]);
		((Lot)board[37]).SetPropOwner(players[0]);
		((Lot)board[9]).SetPropOwner(players[0]);
	}
	
	public int RunActionsMenu(String[] actions)
	// (Method provided by Doug Hogan http://php.scripts.psu.edu/djh300/cmpsc221/RunActionsMenu.txt)
	// PRE:  Each element of actions contains a sentence telling a user a possible option.
	// POST:  A menu has been displayed to the console listing each action and the user has been 
	//        prompted to choose one (with error handling).  actions[FCTVAL] is the action chosen.
    {
		int choice;                                      // # of action chosen, corresponds to 
                                                         //   index in actions
		Scanner input = new Scanner(System.in);          // for reading from console

		for(int i = 0; i < actions.length; i++)          // show choices
		{
          System.out.printf("%d. %s\n", i, actions[i]); 
		}

       do                                                // read in, require valid choice 
       {
          System.out.print("Enter number of your choice: ");
          choice = input.nextInt();
       } while(choice >= actions.length || choice < 0);   

       return choice; 
   }
   
 	public void DisplayGameMenu()
    // PRE: players, curPlayer, and boards array have to be initialized
    // POST:Displays the game menu for the current player and possible moves pertaining 
	//		 to the choice chosen, opening up submenus if applicable
    {
		String[] menu;							// array holding menu choices for current player
		int choice;								// variable to hold choice selection from menu
		
		Scanner input = new Scanner(System.in);	// allows capturing of data from console
		
		menu = new String[5];					// declare size of menu array to however many choices
		menu[0] = "Roll the Dice";				
		menu[1] = "Manage Your Houses/Hotels";
		menu[2] = "View Board";
		menu[3] = "View Player Status";
		menu[4] = "End Game";
		
		System.out.println("===================================");
		PlayerStatus();
		System.out.println("===================================");

		System.out.println("*************** Player " + ().GetToken() + "'s Turn ***************");
		
		choice = RunActionsMenu(menu);			// calls method to number the values in the array 
													// 	and outputs som oft 
		
		switch (choice)
		{
			case 0:
			{
				RollDice();
				
				System.out.println("You landed on.. ");
				System.out.println(board[players[GetCurNum()].GetLocation()]);
				
				choice = RunActionsMenu(board[players[GetCurNum()].GetLocation()].GetPlayerActions(players[GetCurNum()]));
				if (choice == 0)
				{
					if (board[players[GetCurNum()].GetLocation()].GetPlayerActions(players[GetCurNum()])[0].contains("Buy"))
					{
						if (players[GetCurNum()].GetMoney() > ((Property)board[players[GetCurNum()].GetLocation()]).GetCost())
						{
							((Property)board[players[GetCurNum()].GetLocation()]).BuyProperty(players[GetCurNum()]);
							
							System.out.println("Player " + players[GetCurNum()].GetToken() + 
												" has succesfully bought " + board[players[GetCurNum()].GetLocation()].GetName());
						}
						else
						{
							System.out.println("You don't have enough money to buy " + board[players[GetCurNum()].GetLocation()].GetName());
						}
					}
					else if (board[players[GetCurNum()].GetLocation()].GetPlayerActions(players[GetCurNum()])[0].contains("Pay"))
					{
						if (board[players[GetCurNum()].GetLocation()] instanceof Utility)
						{
							((Utility)board[players[GetCurNum()].GetLocation()]).SetDiceRoll(GetDiceRoll1() + GetDiceRoll2());
						}
						((Property)board[players[GetCurNum()].GetLocation()]).PayRent(players[GetCurNum()]);
						
						if (players[GetCurNum()].GetMoney() < 0)
						{
							System.out.println("Player " + players[GetCurNum()].GetToken() + " has gone bankrupt");
							EndGame();
						}
					}
					else if (board[players[GetCurNum()].GetLocation()].GetName().contains("CHANCE"))
					{
						((SpecialLocation)board[players[GetCurNum()].GetLocation()]).GenerateCommChance(players[GetCurNum()]);
					}
					else if (board[players[GetCurNum()].GetLocation()].GetName().contains("COMMUNITY"))
					{
						((SpecialLocation)board[players[GetCurNum()].GetLocation()]).GenerateCommChance(players[GetCurNum()]);						
					}
					else if (board[players[GetCurNum()].GetLocation()].GetName().contains("LUXURY"))
					{
						((SpecialLocation)board[players[GetCurNum()].GetLocation()]).LuxTax(players[GetCurNum()]);

						System.out.println("You have to pay $75 for Luxury Tax(-$75)");
					}
					else if (board[players[GetCurNum()].GetLocation()].GetName().contains("INCOME"))
					{
						((SpecialLocation)board[players[GetCurNum()].GetLocation()]).IncoTax(players[GetCurNum()]);
						
						System.out.println("You have to pay $200 for Income Tax(-$200)");						
					}
				}
				
				ReRoll();								// checks if player rolled doubles and re rolls
				
				break;
			}
			case 1:
			{
				ManageProperty(players[GetCurNum()]);	// ManageProperty function to handle buying/selling 
														// 	of properties that current player owns.
				
				DisplayGameMenu();						// recursion call, since it's still the current 
														// 	players turn
				
				break;
			}
			case 2:
			{
				PrintBoard();							// displays the entire board
				
				DisplayGameMenu();						// recursion call, since it's still the current 
														// 	players turn
				
				break;
			}
			case 3:
			{
				System.out.println(players[GetCurNum()]);// displays current player info
				
				DisplayGameMenu();
				
				break;
			}
			case 4:
			{
				EndGame();								// ends the game
				
				break;
			}
		}
    }
	
	public void ReRoll()
	// PRE: diceRoll1 and diceRoll2 have to be initialized
	// POST:Checks to see if the current diceRoll1 and diceRoll2 equal each other
	// 		 to determine if current player rolls again for rolling doubles
	{
		if (GetDiceRoll1() == GetDiceRoll2())
		{
			System.out.println("YOUR TURN AGAIN, SINCE YOU ROLLED A DOUBLE");
			
			DisplayGameMenu();					// displays the main menu for the current player
												//  since it's his/her turn again
		}		
	}
	
	public void EndGame()
	// PRE: gameState = true
	// POST:ends the game by setting gameState = false
	{
		SetGameState(false);					// sets gameState to false
		
		System.out.println("Thank you for playing Monopoly");
	}

	public void ManageProperty(Player player)
	// PRE: player has to be initialized
	// POST: Gives user the option of buying/selling improvements for the lots he/she owns
	{
		String[] menu;							// menu of choices 
		int choice;								// variable to hold choice from menu
		int selection;
		int buySellAmount;
		Lot[] myLots;

		Scanner input = new Scanner(System.in); // for reading from console
		
		menu = new String[3];					// declare array size for however many choices
		myLots = player.GetLots();				// gets all the Lots player owns
		
		for (int x = 0; x < myLots.length; x++)				//outputs 
		{
			System.out.println("(" + x + ")" + myLots[x]);
		}
		
		System.out.println("**********************************************");
		System.out.println("Select what property you want to manage: ");
		
		selection = input.nextInt();
		
		while(selection > (myLots.length-1))
		{
			System.out.println("############\nYou have entered an index that is not in your Lots, please 
									reselect\n############");
			System.out.println("Select what property you want to manage: ");
		
			selection = input.nextInt();
		}
				
		menu[0] = "Buy House(s)/Hotel";
		menu[1] = "Sell House(s)/Hotel";
		menu[2] = "Back to Main Menu";
				
		choice = RunActionsMenu(menu);

		switch (choice)
		{
			case 0:
			{
				System.out.println("How many houses/hotels do you want to buy?(Max " + (5-
											myLots[selection].GetImprovements()) + ")");
				buySellAmount = input.nextInt();
				
				if (player.GetMoney() > (((Lot)myLots[selection]).GetImproveCost() * buySellAmount))
				{

					((Lot)board[myLots[selection].GetLocation()]).ImproveProp(buySellAmount);
					
					System.out.println(board[myLots[selection].GetLocation()]);
					
					System.out.println(player);
				}
				else
				{
					System.out.println("You don't have enough money to that many houses/hotels \n");
					
					ManageProperty(player);
				}
				
				break;
			}
			case 1:
			{
				System.out.println("How many houses/hotels do you want to sell?(Max " + 
										myLots[selection].GetImprovements() + ")");
				buySellAmount = input.nextInt();
				
				if (buySellAmount > ((Lot)myLots[selection]).GetImprovements())
				{
					System.out.println("You don't have that many houses/hotels on that property \n");
					
					ManageProperty(player);
				}
				else
				{
					((Lot)board[myLots[selection].GetLocation()]).SellImprovements(buySellAmount);	
				}
				
				break;
			}
			case 2:
			{
				DisplayGameMenu();
			
				break;
			}
		}
	}
	
	public void DisplayLogo()
	//POST: Prints out an ASCII art of the monopoly logo
	{
		String string1 = "	   _____                                     .__      ";
		String string2 = "	  /     \\   ____   ____   ____ ______   ____ |  | ___.__.";
		String string3 = "	 /  \\ /  \\ /  _ \\ /    \\ /  _ \\\\____ \\ /  _ \\|  |<   |  |";
		String string4 = "	/    Y    (  <_> )   |  (  <_> )  |_> >  <_> )  |_\\___  |";
		String string5 = "	\\____|__  /\\____/|___|  /\\____/|   __/ \\____/|____/ ____|";
		String string6 = " 	        \\/            \\/       |__|               \\/     ";
		
		System.out.println(string1);   
		System.out.println(string2);
		System.out.println(string3);
		System.out.println(string4);
		System.out.println(string5);
		System.out.println(string6);
	}

	public Player GetCurPlayer()
	// POST: Returns the player whose turn it is
	{
		return players[curPlayer];
	}
	
	public int GetCurNum()
	// POST: Returns the index of the current player in the players array
	{
		return curPlayer;
	}
   
    public boolean GetGameState()
	// POST: Returns if the game is still going on or not
	{
		return gameState;
	}
   
	public int GetDiceRoll1()
	// PRE: diceRoll1 and diceRoll2 >= 1 && <=6
	// POST: Returns the value of the first die
	{
		return diceRoll1;
	}

	public int GetDiceRoll2()
	// PRE: diceRoll1 and diceRoll2 >= 1 && <=6
	// POST: Returns the value of the second die
	{
		return diceRoll2;
	}
	
	public void PrintBoard()
	// POST: string representation of the description of each BoardLocation of the Monopoly game
	//			(color of property, cost to buy, rent cost, owner, # of improvements)
	{
		for ( BoardLocation cur : board)	// Goes through whole board array
		{
			System.out.println(cur + "\n");
		}
	}
	
	public void PlayerStatus()
	// POST: string representation of the the current state of each player.
	//		  (money, location, and number of properties owned)
	{
		for (Player cur : players)			// Goes through whole players array
		{
			System.out.println(cur);
		}
	}
}
