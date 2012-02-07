// BattleShip.cpp - Implementation of BattleShip functions
// Written by Christian Yoon

#include "BattleShip.h"

const int Data::GetSize()
{
	return Size;
}

const int Data::GetX()
{
	return XCoordinate;
}

const int Data::GetY()
{
	return YCoordinate;
}

const char Data::GetDirection()
{
	return Direction;
}

/*const int Data::GetHit()
{
return Hits;
}*/

void Data::SetX(int x)
{
	XCoordinate = x;
}

void Data::SetY(int y)
{
	YCoordinate = y;
}

void Data::SetDirection(char direction)
{
	Direction = direction;
}

/*void Data::SetHits(int hits)
{
Hits = hits;
}*/

Data& Data::operator =(Ships ships)
{
	ShipNumber = ships;
	return *this;
}

Data& Data::operator =(int size)
{
	Size = size;
	return *this;
}

Player::Player(int number)
{

	Data shipData;

	for (int i = 0; i < number; i++)
	{
		shipData = (i, i + 2);
		PlayerShips.push_back(shipData);
		//PlayerShips[i].SetHits(0);
	}
}

const int Player::GetSize(int shipNumber)
{
	return PlayerShips[shipNumber].GetSize();
}

void Player::SetCoordinates(int x, int y, char direction, int shipNumber)
{
	PlayerShips[shipNumber].SetX(x);
	PlayerShips[shipNumber].SetY(y);
	PlayerShips[shipNumber].SetDirection(direction);
}

/*void Player::Hit(int hit, int shipNumber)
{
PlayerShips[shipNumber].SetHits(hit);
}*/

Data Player::operator [](int index) const
{
	return PlayerShips[index];
}

void Reset(Grid &gridP, Grid &gridC)
{
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLUMNS; j++)
		{
			gridP[i][j] = EMPTY_WATER;
			gridC[i][j] = EMPTY_WATER;
		}
	}
}

const void Print(Grid grid)
{
	for (int i = 0; i < ROWS; i++)
	{
		cout << i + 1 << " ";
		for (int j = 0; j < COLUMNS; j++)
		{
			switch (grid[i][j])
			{
			case EMPTY_WATER:
				cout << ". ";
				break;
			case HIT_WATER:
				cout << "~ ";
				break;
			case UNHIT_SHIP:
				cout << "S ";
				break;
			case HIT_SHIP:
				cout << "X ";
				break;
			}
		}
		cout << endl;
	}
	cout << "  A B C D E F G H\n";
}

const void PrintC(Grid grid)
{
	for (int i = 0; i < ROWS; i++)
	{
		cout << i + 1 << " ";
		for (int j = 0; j < COLUMNS; j++)
		{
			switch (grid[i][j])
			{
			case EMPTY_WATER:
				cout << ". ";
				break;
			case HIT_WATER:
				cout << "~ ";
				break;
			case UNHIT_SHIP:
				cout << ". ";
				break;
			case HIT_SHIP:
				cout << "X ";
				break;
			}
		}
		cout << endl;
	}
	cout << "  A B C D E F G H\n";
}

void SetShip(Grid &grid, int size, int shipNumber, Player &player)
{
	int y, x;
	char xTemp, yTemp, direction;
	bool validPlacement = false, temp = true;

	// Loops to ensure that ships is placed properly
	while (validPlacement == false)
	{
		y = 0;
		x = 100;
		xTemp = 'z';
		yTemp = 'z';
		direction = 'z';
		temp = false;

		// To make sure program doesn't crash for inputting char instead of int
		// Sets Y coordinate
		while (yTemp - 48 > COLUMNS || yTemp - 48 < 1)
		{
			cout << "Input y coordinate of ship (1-8): ";
			cin >> yTemp;
			if (yTemp - 48 > COLUMNS || yTemp - 48 < 1)
				cout << "Invalid input. Please input an integer from 1 to 8.\n";
			// throw "Invalid input of char for int variable or placed off of grid.";
		}

		// converts char input to int for grid coordinate
		switch (yTemp - 49)
		{
		case 0:
			y = 0;
			break;
		case 1:
			y = 1;
			break;
		case 2:
			y = 2;
			break;
		case 3:
			y = 3;
			break;
		case 4:
			y = 4;
			break;
		case 5:
			y = 5;
			break;
		case 6:
			y = 6;
			break;
		case 7:
			y = 7;
			break;
		}

		// To make sure program doesn't crash for inputting char instead of int
		// Sets X coordinate
		while (xTemp > ROWS + '`' || xTemp < 'a')
		{
			cout << "Input x coordinate of ship (a-h): ";
			cin >> xTemp;
			if (xTemp > ROWS + '`' || xTemp < 'a')
				cout << "Invalid input. Please input a letter from a to h.\n";
			// throw "Invalid input. Placed off of grid.";
		}

		// converts char input to int for grid coordinate
		switch (xTemp)
		{
		case 'a':
			x = 0;
			break;
		case 'b':
			x = 1;
			break;
		case 'c':
			x = 2;
			break;
		case 'd':
			x = 3;
			break;
		case 'e':
			x = 4;
			break;
		case 'f':
			x = 5;
			break;
		case 'g':
			x = 6;
			break;
		case 'h':
			x = 7;
			break;
		}

		// Sets direction
		while (direction != 'u' && direction != 'd' && direction != 'l' && direction != 'r')
		{
			cout << "Input direction of ship (u, d, l, r): ";
			cin >> direction;
			if (direction != 'u' && direction != 'd' && direction != 'l' && direction != 'r')
				cout << "Invalid input. Please input u, d, l, or r.\n";
			// throw "Invalid input. Direction goes off of grid.";
		}

		// Needs to make sure size of ship does not go over the edge of the grid when direction from starting point is chosen
		switch (direction)
		{
		case 'u':
			if (y - size < 0)
			{
				cout << "Ship cannot be placed here. Please try again.\n";
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y - i][x] == UNHIT_SHIP)
					{
						cout << "Ship already placed here. Please try again.\n";
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}

				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y - i][x] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'd':
			if (y + size > COLUMNS)
			{
				cout << "Ship cannot be placed here. Please try again.\n";
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y + i][x] == UNHIT_SHIP)
					{
						cout << "Ship already placed here. Please try again.\n";
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y + i][x] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'l':
			if (x - size < 0)
			{
				cout << "Ship cannot be placed here. Please try again.\n";
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y][x - i] == UNHIT_SHIP)
					{
						cout << "Ship already placed here. Please try again.\n";
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y][x - i] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'r':
			if (x + size > ROWS)
			{
				cout << "Ship cannot be placed here. Please try again.\n";
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y][x + i] == UNHIT_SHIP)
					{
						cout << "Ship already placed here. Please try again.\n";
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y][x + i] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		}
	}

	// Saves the ships coordinates inside Player class 
	player.SetCoordinates(x, y, direction, shipNumber);
}

void SetRandom(Grid &grid, int size, int shipNumber, Player &player, time_t* randomizer = 0)
{
	int x, y, directionTemp;
	char direction;
	bool temp = false, validPlacement = false;
	// Sets randomizer to value, needed to input time due to setting computer/human plots identically when both were randomly set
	srand((unsigned)time(randomizer));

	while (validPlacement == false)
	{
		// Coordinates and direction are randomized
		x = rand() % 8;
		y = rand() % 8;
		directionTemp = rand() % 4;

		switch (directionTemp)
		{
		case 0:
			direction = 'u';
			break;
		case 1:
			direction = 'd';
			break;
		case 2:
			direction = 'l';
			break;
		case 3:
			direction = 'r';
			break;
		}

		// Needs to make sure size of ship does not go over the edge of the grid when direction from starting point is chosen
		switch (direction)
		{
		case 'u':
			if (y - size < 0)
			{
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y - i][x] == UNHIT_SHIP)
					{
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y - i][x] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'd':
			if (y + size > COLUMNS - 1)
			{
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y + i][x] == UNHIT_SHIP)
					{
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y + i][x] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'l':
			if (x - size < 0)
			{
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y][x - i] == UNHIT_SHIP)
					{
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y][x - i] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		case 'r':
			if (x + size > ROWS - 1)
			{
			}
			else
			{
				for(int i = 0; i < size; i++)
				{
					if (grid[y][x + i] == UNHIT_SHIP)
					{
						temp = false;
						break;
					}
					else
					{
						temp = true;
					}
				}
				if (temp == true)
				{
					for(int i = 0; i < size; i++)
						grid[y][x + i] = UNHIT_SHIP;
					validPlacement = true;
				}
			}
			break;
		}
	}

	// Saves the ships coordinates inside Player class
	player.SetCoordinates(x, y, direction, shipNumber);
}

void Attack(Grid &grid, Player target, list<Pair> &theList)
{
	Pair myPair;
	int y, x;
	char yTemp, xTemp;
	bool validAttack = false;

	// To make sure the attack is not on an alreay bombed block or off the grid
	while (validAttack == false)
	{
		y = 0;
		x = 100;
		yTemp = 'z';
		xTemp = 'z';

		// To make sure program doesn't crash for inputting char instead of int
		// Sets Y coordinate of attack
		while (yTemp - 48 > COLUMNS || yTemp - 48 < 1)
		{
			cout << "Input y coordinate of attack (1-8): ";
			cin >> yTemp;
			if (yTemp - 48 > COLUMNS || yTemp - 48 < 1)
				cout << "Invalid input. Please input an integer from 1 to 8.\n";
			// throw "Invalid input of char for int variable or coordinate off of grid.";
		}

		// converts char input to int for attack coordinate
		switch (yTemp - 49)
		{
		case 0:
			y = 0;
			break;
		case 1:
			y = 1;
			break;
		case 2:
			y = 2;
			break;
		case 3:
			y = 3;
			break;
		case 4:
			y = 4;
			break;
		case 5:
			y = 5;
			break;
		case 6:
			y = 6;
			break;
		case 7:
			y = 7;
			break;
		}

		// To make sure program doesn't crash for inputting char instead of int
		// Sets X coordinate of attack
		while (xTemp > ROWS + '`' || xTemp < 'a')
		{
			cout << "Input x coordinate of attack (a-h): ";
			cin >> xTemp;
			if (xTemp > ROWS + '`' || xTemp < 'a')
				cout << "Invalid input. Please input a letter from a to h.\n";
			// throw "Invalid input. Coordinate off of grid.";
		}

		// converts char input to int for attack coordinate
		switch (xTemp)
		{
		case 'a':
			x = 0;
			break;
		case 'b':
			x = 1;
			break;
		case 'c':
			x = 2;
			break;
		case 'd':
			x = 3;
			break;
		case 'e':
			x = 4;
			break;
		case 'f':
			x = 5;
			break;
		case 'g':
			x = 6;
			break;
		case 'h':
			x = 7;
			break;
		}

		// Once attack placement is valid, changes the block state accordingly
		switch (grid[y][x])
		{
		case EMPTY_WATER:
			cout << "Miss. Nice try...\n";
			grid[y][x] = HIT_WATER;
			validAttack = true;
			break;
		case HIT_WATER:
			cout << "You already shot here. Pick another location.\n";
			break;
		case UNHIT_SHIP:
			cout << "Hit! Nice Shot.\n";
			grid[y][x] = HIT_SHIP;
			validAttack = true;
			//ShipHit(target, target, x, y);
			break;
		case HIT_SHIP:
			cout << "You already shot here. Pick another location.\n";
			break;
		}
	}

	// Saves attack to list so that it can be viewed later
	myPair.XCoordinate = x;
	myPair.YCoordinate = y;
	theList.push_front(myPair);
}

void AI(Grid &grid, Player target)
{
	int x, y;
	bool validAttack = false;
	srand((unsigned)time(0));

	while (validAttack == false)
	{
		// Attacks randomly for now...
		x = rand() % 8;
		y = rand() % 8;

		switch (grid[y][x])
		{
		case EMPTY_WATER:
			cout << "Computer missed!\n";
			grid[y][x] = HIT_WATER;
			validAttack = true;
			break;
		case HIT_WATER:
			break;
		case UNHIT_SHIP:
			cout << "Computer Hit!\n";
			grid[y][x] = HIT_SHIP;
			validAttack = true;
			//ShipHit(target, target, x, y);
			break;
		case HIT_SHIP:
			break;
		}
	}
}

/*void ShipHit(Player test, Player &target, int attackX, int attackY)
{
int x, y;
char direction;

for (int i = 0; i < SHIP_NUMBER; i++)
{
x = target[i].GetX();
y = target[i].GetY();
direction = target[i].GetDirection();

switch (direction)
{
case 'u':
for(int j = 0; j < target.GetSize(i); j++)
{
if ((y - j) == attackY && x == attackX)
{
test.Hit(target[i].GetHit() + 1, i);
break;
}
}
break;
case 'd':
for(int j = 0; j < target.GetSize(i); j++)
{
if ((y + j) == attackY && x == attackX)
{
test.Hit(target[i].GetHit() + 1, i);
break;
}
}
break;
case 'l':
for(int j = 0; j < target.GetSize(i); j++)
{
if (y == attackY && (x - j) == attackX)
{
test.Hit(target[i].GetHit() + 1, i);
break;
}
}
break;
case 'r':
for(int j = 0; j < target.GetSize(i); j++)
{
if (y == attackY && (x + j) == attackX)
{
test.Hit(target[i].GetHit() + 1, i);
break;
}
}
break;
}
}
}*/

/*template <size_t size> const void ShipCheck(Player &check, int (&array)[size])
{
typedef Ships sunk;
int shipNumber = 0;

for (; shipNumber < SHIP_NUMBER; shipNumber++)
{
if (check[shipNumber].GetHit() == check[shipNumber].GetSize())
{
switch (shipNumber)
{
case 0:
if (array[shipNumber] != 0)
{
cout << "Small Ship Sunk!\n";
array[shipNumber] += 1;
}
break;
case 1:
if (array[shipNumber] != 0)
{
cout << "Medium Ship Sunk!\n";
array[shipNumber] += 1;
}
break;
case 2:
if (array[shipNumber] != 0)
{
cout << "Large Ship Sunk!\n";
array[shipNumber] += 1;
}
break;
case 3:
if (array[shipNumber] != 0)
{
cout << "Battle Ship Sunk!\n";
array[shipNumber] += 1;
}
break;
case 4:
if (array[shipNumber] != 0)
{
cout << "Aircraft Carrier Sunk!\n";
array[shipNumber] += 1;
}
break;
}
}
}
}*/

bool GameOver(Grid &gridP, Grid &gridC)
{
	int count1 = 0, count2 = 0;
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLUMNS; j++)
		{
			// Counts all of the blocks that still contain a ship that is unhit for both players
			if (gridP[i][j] == UNHIT_SHIP)
				count1++;
			if (gridC[i][j] == UNHIT_SHIP)
				count2++;
		}
	}

	// If either player has no more ships, the opposing player wins
	if(count1 == 0)
	{
		cout << "Computer wins. Nice try.\n";
		return false;
	}
	else
	{
		if (count2 == 0)
		{
			cout << "You win! Good job.\n";
			return false;
		}
		else
			return true;
	}
}

void Menu(Grid &gridP, Grid &gridC)
{
	// All of the variables used in the game
	Player human(SHIP_NUMBER), computer(SHIP_NUMBER);
	list<Pair> humanList;
	int choice1 = 0, choice2 = 0, choice3 = 0;
	char replay, xTemp, choice3Temp = 'z';
	//int checkListP[SHIP_NUMBER] = {0, 0, 0, 0, 0};
	//int checkListC[SHIP_NUMBER] = {0, 0, 0, 0, 0};
	bool loop1 = true, loop2 = true, loop3 = true, loop4 = true, loop5 = true, loop6 = true;
	srand((unsigned)time(0));
	time_t random;

	// Reset grid just as a precaution
	Reset(gridP, gridC);

	// In order for the SetRandom function to work, create a random variable that creates different player/computer grids
	random = rand() % 2;

	cout << "Welcome to Battle Ship.\n";

	while (loop1 == true) 
	{
		// Player's options
		cout << "To play a game, input 1.\nTo read How to Play, input 2.\nTo quit, input 3.\n";
		cin >> choice1;

		// Reinitializing loop checkers
		loop2 = true;
		loop3 = true;
		loop4 = true;
		loop5 = true;
		loop6 = true;

		switch (choice1) 
		{
		case 1:
			cout << "Input 1 to manually input ship positions, or input 2 to have it done randomly.\n";
			cin >> choice2;
			break;
		case 2:
			// Rules and mechanics of the game
			cout << "--------------Rules------------\n";
			cout << "This game of BattleShip is played between a player(you) and the computer. There are 5 ships for each player which are set on a grid.\n";
			cout << "The game is turn based, with each player choosing a coordinate on the grid to 'shoot'.";
			cout << "The aim is to 'sink' all of the enemy ships, and the game ends when either player's ships are all sunk.\n";
			cout << "------------The Grid-----------\n";
			cout << "The grid is on a two dimensional coordinate plane with the X-axis labeled from A-H, and the Y-axis labled from 1-8.\n";
			cout << "------Symbols on the Grid------\n";
			cout << "'.' is empty water, '~' is water that has been bombed, 'S' is a Ship, 'X' is a Ship that has been bombed.\n";
			cout << "------------Attacking----------\n";
			cout << "When it is your turn, you will input X and Y coordinates.\n";
			cout << "You cannot shoot spots you have already shot, and you cannot shoot off the grid.\n";
			cout << "----------Sample Grid----------" << endl;
			Print(gridP);
			cout << "-------------Ships-------------\n";
			cout << "There are 5 ships: Small ship, 2 Blocks, Medium ship, 3 Blocks, Large Ships, 4 Blocks, Battle Ship, 5 Blocks, Aircraft Carrier, 6 Blocks.\n";
			cout << "This gives a total of 20 Blocks to shoot.\n";
			cout << "-------------------------------\n";
			cout << "Good luck and Have Fun!\n";
			break;
		case 3:
			loop1 = false;
			loop2 = false;
			cout << "Thanks for Playing!\n";
			break;
		default:
			cout << "Please input a valid choice(1-3).\n";
			break;
		}

		// Player choosing to manually or randomly set ships on grid
		while (loop2 == true)
		{
			switch (choice2) 
			{
			case 0:
				loop2 = false;
				loop5 = false;
				break;
			case 1:
				for (int i = 0; i < SHIP_NUMBER; i++)
				{
					SetShip(gridP, human.GetSize(i), i, human);
					Print(gridP);
				}
				loop2 = false;
				break;
			case 2:
				for (int i = 0; i < SHIP_NUMBER; i++)
				{
					SetRandom(gridP, human.GetSize(i), i, human, &random);
				}
				cout << "---Player Grid---" << endl;
				Print(gridP);
				loop2 = false;
				break;
			default:
				cout << "Please input a valid choice, 1 or 2.\n";
				break;
			}
		}

		if (loop1 == true && loop5 == true)
		{
			// Pause is here to allow random to run it's course and truly randomize the grids
			// Skipping this step made the two grids identical
			system("pause");
			// Set the computer grid
			for (int i = 0; i < SHIP_NUMBER; i++)
			{
				SetRandom(gridC, computer.GetSize(i), i, computer, &random);
			}
			cout << "--Computer Grid--" << endl;
			PrintC(gridC);

			// Allows for player to check previous attacks
			while (loop3 == true)
			{
				choice3 = 0;
				cout << "--Player attack--\n";
				loop4 = true;
				while (loop4 == true)
				{
					cout << "To see your previous attacks, input 1. Input 0 to skip.\n";
					cin >> choice3Temp;

					switch (choice3Temp - 48)
					{
					case 0:
						choice3 = 0;
						loop4 = false;
						break;
					case 1:
						choice3 = 1;
						loop4 = false;
						break;
					default:
						cout << "Please insert 1 or 0.\n";
						// throw "Invalid input. Char input for int variable or number not 0 or 1.";
						break;
					}
				}

				switch (choice3)
				{
				case 0:
					break;
				case 1:
					if (humanList.begin() == humanList.end())
						cout << "No previous attacks.\n";
					for (list<Pair>::iterator pos = humanList.begin(); pos != humanList.end(); pos++)
					{ 
						switch (pos->XCoordinate)
						{
						case 0:
							xTemp = 'A';
							break;
						case 1:
							xTemp = 'B';
							break;
						case 2:
							xTemp = 'C';
							break;
						case 3:
							xTemp = 'D';
							break;
						case 4:
							xTemp = 'E';
							break;
						case 5:
							xTemp = 'F';
							break;
						case 6:
							xTemp = 'G';
							break;
						case 7:
							xTemp = 'H';
							break;
						}
						cout << "Shot at " << pos->YCoordinate + 1 << ", " << xTemp << ".\n";
					}
					break;
				}
				// Attacking, being attacked, checking for game over
				// Most of the game takes place here
				Attack(gridC, computer, humanList);
				//ShipCheck(computer, checkListC);
				loop3 = GameOver(gridP, gridC);
				system("pause");
				cout << "-Computer attack-\n";
				AI(gridP, human);
				//ShipCheck(human, checkListP);
				loop3 = GameOver(gridP, gridC);
				cout << "---Player Grid---" << endl;
				Print(gridP);
				cout << "--Computer Grid--" << endl;
				PrintC(gridC);
			}

			// After the game is finished, player can play again.
			while (loop6 == true)
			{
				cout << "Play again (y or n)?\n";
				cin >> replay;

				if (replay == 'y' || replay == 'Y')
				{
					Reset(gridP, gridC);
					loop1 = true;
					loop6 = false;
				}
				else
				{
					if (replay == 'n' || replay == 'N')
					{
						loop1 = false;
						loop6 = false;
						cout << "Thanks for playing!\n";
					}
					else
						cout << "Please enter y or n.\n";
				}
			}
		}
	}
}