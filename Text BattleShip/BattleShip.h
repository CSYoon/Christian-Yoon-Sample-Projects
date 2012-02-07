// BattleShip.h - Declarations of BattleShip functions
// Written by Christian Yoon

#pragma once

// Included files
#include <iostream>
#include <vector>
#include <string>
#include <list>
#include <cstdlib>
#include <ctime>

using namespace std;

// Global variables
#define ROWS 8
#define COLUMNS 8
#define SHIP_NUMBER 5

// State of grid block
enum BlockState {EMPTY_WATER, HIT_WATER, UNHIT_SHIP, HIT_SHIP};

// Name of ships stored in Player class
enum Ships {SMALL_SHIP, MED_SHIP, LARGE_SHIP, BATTLE_SHIP, AIRCRAFT_CARRIER};

// Create variable for Grid for each player
typedef BlockState Grid[ROWS][COLUMNS];

// Stores coordinates for each Attack in list
struct Pair
{
	int XCoordinate, YCoordinate;
};

// Class stored in Player class, holds Ship data
class Data
{
public:
		
	// Returns size of ship
	const int GetSize();
	// Returns starting X coordinate
	const int GetX();
	// Returns starting Y coordinate
	const int GetY();
	// Returns direction on grid ship is plotted
	const char GetDirection();
	// Returns number of hits ship has taken
	//const int GetHit();
	// Sets X coordinate
	void SetX(int x);
	// Sets Y coordinate
	void SetY(int y);
	// Sets direction on grid ship is plotted
	void SetDirection(char direction);
	// Sets number of hits ship has taken
	//void SetHits(int hits);
	// Assignment operator for naming the ship
	Data& operator =(Ships ships);
	// Overloaded assignment operator for assigning size
	Data& operator =(int size);
		
private:
		
	// Private variables: Ship name, starting position/direction, size, hits taken
	Ships ShipNumber;
	int Size, XCoordinate, YCoordinate;
	//int Hits;
	char Direction;
};

// Creates a player in the game to play BattleShip
class Player
{
public:
		
	// Creates ships using Data class
	Player(int number);
	// Gets size of specific ship from Data class
	const int GetSize(int shipNumber);
	// Sets coordinates of ship inside Data class
	void SetCoordinates(int x, int y, char direction, int shipNumber);
	//void Hit(int hit, int shipNumber);
	// Index operator
	Data operator [](int index) const;
	
private:
		
	// Private variable: All of the data for each player inside PlayerShips vector
	vector<Data> PlayerShips;
};

// Resets the grids to play game again
void Reset(Grid &gridP, Grid &gridC);
// Prints grid of player
const void Print(Grid grid);
// Prints grid of computer, keeps ships hidden
const void PrintC(Grid grid);
// Manually sets ships to grid
void SetShip(Grid &grid, int size, int shipNumber, Player &player);
// Sets ships to grid at random
void SetRandom(Grid &grid, int size, int shipNumber, Player &player);
// Attacking function
void Attack(Grid &grid, Player target, list<Pair> &theList);
// Artificial intelligence, Computer attacking
void AI(Grid &grid, Player target);
// Adds to ship's Hits counter
//void ShipHit(Player test, Player &target, int attackX, int attackY);
// Checks if ship has been sunk and outputs status
//template <size_t size> const void ShipCheck(Player &check, int (&array)[size]);
// Checks if game is over
bool GameOver(Grid &gridP, Grid &gridC);
// Runs the game, contains all of the variables and processes
void Menu(Grid &gridP, Grid &gridC);