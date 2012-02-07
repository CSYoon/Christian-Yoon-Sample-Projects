// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing players of a game of Monopoly

public class Player 
{
	private static final int defaultMoney = 1500;			// Every player starts out with 1500
	private static final int defaultLoc = 0;				// Every player starts out at the beginning
	private static final int defaultNumUtil = 0;			// Every player starts out owning 0 
															// 	utilities
	private static final int defaultNumRR = 0;				// Every player starts out owning 0 
															// 	railroads
	private static final int defaultNumProp = 0;			// Every player starts out owning 0 total 
															// 	property
	private static final int defaultNumLots = 0;			// Every player starts out owning 0 lots
	
	private final int GO = 200;								// Amount earned when passing Go
	private final int MAXLOCATION = 39;						// Largest location a player can be at
	private final int MAXPROPERTY = 28;						// Max amount of properties able to own

	private int money;										// Amount of money the player has
	private int location;									// Playerís location on the board
	private int numUtilities;								// Number of utilities player owns
	private int numRailroads;								// Number of railroads player owns
	private int numLots;									// Number of lots players own
	private String token;									// Token representing the player
	private Property[] properties;  						// What properties the player owns
	private int numProperties;								// Number of total properties player owns

	public Player(String token)
	// PRE:  token has to be initialized
	// POST: A Player object has been constructed with data members token
	//			set to the values passed in the corresponding parameters
	//			all players start with money = 1500, location = 0,
	//			numUtilities = 0, numRailroads = 0, and numProperties = 0
	//			and has a properties array of type Property the size of MAXPROPERTY
	//			of properties a Player can own.
	{
		properties = new Property[MAXPROPERTY];
		money = defaultMoney;
		location = defaultLoc;
		numUtilities = defaultNumUtil;
		numRailroads = defaultNumRR;
		numProperties = defaultNumProp;
		numLots = defaultNumLots;
		
		this.token = token;
	}
	
	public void MovePlayer(int newLocation)
	// PRE:  newLocation must be initialized
	// POST: Moves the player on the board; resets location to 0 then
	//			continues moving if newLocation exceeds 39, the board array upper bound;
	//			awards $200 for going all the way around the board
	{
		if ((newLocation + GetLocation()) <= MAXLOCATION) // if newLocation(the dice roll amount)
		{													 //  is <= the MAXLOCATION, the move is
			SetLocation(newLocation + GetLocation());		 //  allowed
		}
		else												 // if the newLocation does exceed...
		{
			newLocation = (GetLocation() + newLocation) - (MAXLOCATION + 1);
			System.out.println("EARN $200 FOR PASSING GO"); 
			
			SetMoney(GetMoney() + GO);						 // player gets money for passing Go
			
			SetLocation(newLocation);
		}
	}
	
	public void SetMoney(int money)
	// PRE:  money >= 0 and money is in dollars
	// POST: Sets class member money to value passed in parameter
	{
		this.money = money;
	}
	
	public void SetLocation(int location)
	// PRE:  40 > location >= 0 (board size = 40, means range 0 to 39)
	// POST: Sets class member location to value passed in parameter
	{
		this.location = location;
	}
	
	public void SetNumUtilities(int numUtilities)
	// PRE:  numUtilities >= 0
	// POST: Sets class member numUtilities to value passed in parameter
	{
		this.numUtilities = numUtilities;
	}
	
	public void SetNumRailroads(int numRailroads)
	// PRE:  numRailroads >= 0
	// POST: Sets class member numRailroads to value passed in parameter
	{
		this.numRailroads = numRailroads;
	}
	
	public void SetToken(String token)
	// PRE:  token has to be initialized
	// POST: Sets class member token to value passed in parameter
	{
		this.token = token;
	}
	
	public void SetProperty(Property property, int numProperties)
	// PRE:  0 <= numProperties < MAXPROPERTY and property has to be initialized
	// POST: Adds a property to the array property at index of numProperties
	{
		properties[numProperties] = property;
	}
	
	public void SetNumProperties(int numProperties)
	// PRE:  0 <= numProperties < MAXPROPERTY
	// POST: Sets class member numProperties to value passed in parameter
	{
		this.numProperties = numProperties;
	}
	
	public void SetNumLots(int numLots)
	// PRE:  0<= numLots < max number of lots a player can own
	// POST: Sets class member numLots to value passed in parameter
	{
		this.numLots = numLots;
	}
	

	public Lot[] GetLots()
	// POST: Returns an array of the Lots the player owns
	{
		Lot[] myLots;								 	// array that holds the Lots of the Player
		int counter;								 	// counter to increment index of myLots
		
		myLots = new Lot[numLots];					 	// initializing myLots to size of however many lots
													 	//  Player has
		counter = 0;								 	// initializing counter to start at 0
		
		for(int x = 0; x < properties.length; x++) 	 	// goes through the properties array
		{
			if (properties[x] instanceof Lot)		  	// checks each Property in the properties array
			{										  	//  to see if it is of type Lot
				myLots[counter] = (Lot)properties[x];	// adds the properties[x] to myLots[counter] if it
													 	//  of type Lot
				counter++;							 	// increments counter
			}
		}
		
		return myLots;
	}
	
	public String GetPropertyNames()
	// POST: Returns the names of the properties the player owns
	{
		Property[] myProperties;
		String propNames;
		
		myProperties = GetProperties();
		propNames = "";
		
		for (int x = 0; x < (myProperties.length-1); x++)
		{
			if (myProperties[x] instanceof Property)
			{
				propNames += (myProperties[x].GetName() + ", ");
			}
		}
		
		return "--------------------------\n" + propNames + "\n--------------------------";
	}
	
	public int GetNumLots()
	// POST: Returns value of class member numLots
	{
		return numLots;
	}
	
	public int GetMoney()
	// POST: Returns value of class member money
	{
		return money;
	}
	
	public int GetLocation()
	// POST: Returns current location of Player on the board
	{
		return location;
	}
	
	public int GetNumUtilities()
	// POST: Returns value of class member numUtilities
	//			(number of utilities Player owns)
	{
		return numUtilities;
	}
	
	public int GetNumRailroads()
	// POST: Returns value of class member numRailroads
	//			(number of railroads Player owns)
	{
		return numRailroads;
	}
	
	public Property[] GetProperties()
	// POST: Returns array list of all the properties the player owns in
	//			class member properties
	{
		return properties;
	}
	
	public int GetNumProperties()
	// POST: Returns value of class member numProperties
	//			(number of total property Player owns)
	{
		return numProperties;
	}
	
	public String GetToken()
	// POST: Returns value of class member token
	//			(board piece representing Player)
	{
		return token;
	}
	
	public void DisplayProperties()
	// POST: Outputs all properites owned by Player in the properties array
	{
		for (Property all : properties)			// goes through array and prints out 
		{										//  all properties owned by Player
			System.out.println(all);
		}
	}

	public String toString()
	// POST: Returns a string representation of the token, money, location,
	//			numUtilities (number of utilities), numRailroads (number of railroads), 
	//			and numProperties (number of total property) of the Player
	{
		return "Player " + GetToken() + " has $" + GetMoney()+ " and is at Location " + GetLocation() + 
				"\n[# of Utilities: " + GetNumUtilities() + "; # of Railroads: " + GetNumRailroads() +
				"; # of Lots: " + GetNumLots() +
				"; Total Property: " + GetNumProperties() + "]\n" + 
				GetPropertyNames();
	}
}
