/**
 * Author:	Eduard Varshavsky
 * NetID:	18ev
 * Date:	January 21, 2019
 * Desc:	This is the Team class that handles all the attributes and 
 * 			functions required to track a teams players, statistics and actions
 */

//Imports the random class
import java.util.Random;

public class Team
{
	// *** CLASS VARIABLES ***
	
	//Creates a new instance of the Random class called random
	private Random random = new Random();

	
	//Stores constant integers for required players in each position
	public static final int REQ_PLAYERS = 25;
	public static final int REQ_FORWARDS = 13;
	public static final int REQ_DEFENSEMEN = 8;
	public static final int REQ_GOALIES = 4;

	//Stores the team number and team name as an integer and a string
	private int teamNumber;
	private String teamName;

	//Created Player class arrays for each position and the overall team
	private Player[] allPlayers = new Player[REQ_PLAYERS];	
	private Player[] forwards = new Player[REQ_FORWARDS];
	private Player[] defensemen = new Player[REQ_DEFENSEMEN];
	private Player[] goalies = new Player[REQ_GOALIES];
	
	//Made arrays to hold a min and max element for each position skill range
	private int[] fSkillRange, dSkillRange, gSkillRange;
	
	//Created integers for the team statistics (overTimeLs are overtime losses)
	private int gamesPlayed, wins, losses, overTimeLs, goalsFor, goalsAgainst,
	points, difference;
	
	// *** CONSTRUCTOR METHODS ***

	//Requires a team number and name as an integer and String. Also needs int
	//arrays for the skill ranges of each position
	//Constructor for generating random teams, builds team during generation
	public Team(int teamNumber, String teamName, int[] fSkillRange,
			int[] dSkillRange, int[] gSkillRange)
	{
		//Updates instance variables to those of parameters
		this.teamNumber = teamNumber;
		this.teamName = teamName;
		this.fSkillRange = fSkillRange;
		this.dSkillRange = dSkillRange;
		this.gSkillRange = gSkillRange;
		
		//Generates team using ranges and then fills allPlayers using methods
		buildTeam();
		fillAllPlayers();
	}

	//Requires a team number and name as an integer and String. Also requires
	//Players arrays for the the players in each position
	//Overloaded constructor for setting up a predefined team
	public Team(int teamNumber, String teamName, Player[] forwards,
			Player[] defensemen, Player[] goalies)
	{
		//Updates instance variables to those of parameters
		this.teamNumber = teamNumber;
		this.teamName = teamName;
		this.forwards = forwards;
		this.defensemen = defensemen;
		this.goalies = goalies;
		
		//Update allPlayers Player array using fillAllPlayers() method
		fillAllPlayers();
	}
	
	// *** OBJECT-BEHAVIOUR METHODS ***

	//Doesn't require anything
	//Uses skill ranges to create players for each position array. Also creates
	//names and numbers for each player
	public void buildTeam()
	{
		//Array to track player numbers (so there won't be any duplicates)
		int[] jerseys = new int[REQ_PLAYERS];	//The size of an entire team
		
		//For loop that iterates for the amount of players there are in a team
		for (int i = 0; i < REQ_PLAYERS; i++)
		{
			//Stores the jersey int to be added and boolean if number is valid
			int addJersey = 0;
			boolean validNum = false;
			
			//Keeps looping until a valid number is found and validNum is true
			while (validNum == false)
			{
				//Generates a random number from 1 to 99
				int num = generateNumber(1, 99 + 1);
				
				//Checks if generated number is not already taken in jerseys
				if (isNumInArr(jerseys, num) == false)
				{
					//Assigns the jersey to add variable and element in array
					//to the generated number
					jerseys[i] = addJersey = num;
					
					//Sets the validNum boolean to true so the loop ends
					validNum = true;
				}
			}
			
			//Checks index value of i, affects which position array is added to
			if (i < 13)
			{
				//Sets the name and position of the forward player
				String name = "F" + addJersey;
				Position pos = Position.forward;
				
				//Generates a skill level within the forward skill range
				int low = fSkillRange[0];
				int high = fSkillRange[1] + 1; //Add one to include highest val
				int skill = generateNumber(low, high);
				
				//Adds the player with the information at the provided index
				forwards[i] = new Player(addJersey, name, pos, skill);
			}
			else if (i < 21)
			{
				//Sets the name and position of the defense player
				String name = "D" + addJersey;
				Position pos = Position.defenseman;
				
				//Generates a skill level within the defenseman skill range
				int low = dSkillRange[0];
				int high = dSkillRange[1] + 1; //Add one to be inclusive
				int skill = generateNumber(low, high);
				
				//Adds the player with the information but at the remainder of
				//13 to relatively index the beginning of this array
				defensemen[i % 13] = new Player(addJersey, name, pos, skill);
			}
			else
			{
				//Sets the name and position of the goalie player
				String name = "G" + addJersey;
				Position pos = Position.goalie;
				
				//Generates a skill level within the goalie skill range
				int low = gSkillRange[0];
				int high = gSkillRange[1] + 1; //Add one to be inclusive
				int skill = generateNumber(low, high);
				
				//Adds the player with the information but at the remainder of
				//21 to relatively index the beginning of this array
				goalies[i % 21] = new Player(addJersey, name, pos, skill);
			}
		}
	}
	
	//Doesn't require or return anything in this method
	//This method is used to fill in allPlayers array with every position
	//array to simulate an actual team
	public void fillAllPlayers()
	{
		//Loops through every potential player on the team
		for (int i = 0; i < REQ_PLAYERS; i++)
		{
			//Checks what current index in this block
			if (i < 13)
				//Sets current element of allPlayers to element of forwards
				allPlayers[i] = forwards[i];
			else if (i < 21)
				//Sets current element of allPlayers to element of defensemen
				//Uses remainder to start from beginning of defensmen!
				allPlayers[i] = defensemen[i % 13];
			else 
				//Sets current element of allPlayers to element of goalies
				//Uses remainder to start from beginning of goalies!
				allPlayers[i] = goalies[i % 21];
		}
	}
	
	//Doesn't require or return anything
	//This function creates an array of players using players on this team that
	//simulates the active players in a regulation game
	public Player[] generateRegulation()
	{
		//Creates a new Player array for a regulation team
		Player[] regTeam = new Player[22];

		//Instantiates a random Goalie object for the goalie to play this game
		Player randomGoalie = goalies[generateNumber(0, 3 + 1)];
		
		//Iterates through each position in the regulation team
		for (int i = 0; i < regTeam.length; i++)
			//Assigns the position on the regulation team as forward or defense
			//-man until the last element where it puts the random goalie
			regTeam[i] = i < 21 ? allPlayers[i] : randomGoalie;
		
		//Returns the generated regulation team Player array
		return regTeam;
	}
	
	//Doesn't require or return anything
	//This function creates an array of players using players on this team that
	//simulates the active players in an overtime game
	public Player[] generateOvertime()
	{
		//Creates a new Player array for an overtime team
		Player[] overTeam = new Player[4];
		
		//Makes 3 indexes within range of forwards and defensmen in allPlayers
		int[] indexes = randomThree(0, 20);	//Adds + 1 to high range in method
		
		//Instantiates a random Goalie object for the goalie to play this game
		Player randomGoalie = goalies[generateNumber(0, 3 + 1)];
		
		//Iterates through each index of the overtime team
		for (int i = 0; i < overTeam.length; i++)
		{
			//Sets the 3 random forwards/defensemen as the first three elements
			//And the final element (i = 3) as the random goalie
			overTeam[i] = i <= 2 ? allPlayers[indexes[i]] : randomGoalie;
		}
		
		//Returns the generated overtime team Player array
		return overTeam;
	}
	
	//Requires an integer array and an integer number to check
	//Returns boolean found for if the integer was found in the array
	//This function checks if an integer is inside an integer array
	public boolean isNumInArr(int[] arr, int num)
	{
		//Creates a false boolean for if the number was found
		boolean found = false;
		
		//Iterates through the entire array
		for (int j = 0; j < arr.length; j++)
		{
			//Checks if current array element is the same as number searched
			if (arr[j] == num)
			{
				//If it is, sets found to true and breaks out of the for loop
				found = true;
				break;
			}
		}
		
		//Returns the boolean for if the number was found
		return found;
	}
	
	//Requires the range for lowest and highest integer possible
	//Returns a randomly generated number using a Random object
	//Generates random integer exclusive of high (do + 1 to counteract this)
	public int generateNumber(int low, int high)
	{
		//Returns a generated higher number
		return random.nextInt(high-low) + low;
	}
	
	//Requires the range for lowest and highest integer possible
	//Returns an array of randomly generated number using a Random object
	//Generates 3 unique numbers and returns them in an array
	public int[] randomThree(int low, int high)
	{
		//Created integer array to hold three unique integers
		int[] uniqueNums = new int[3];
		
		//Iterates through each element the uniqueNums array
		for (int i = 0; i < uniqueNums.length; i++)
		{
			//Makes a false boolean to store true/false if the number is unique
			boolean isUnique = false;
			
			//Keeps looping until isUnique is true (means the number is unique)
			while (isUnique == false)
			{
				//Generates an integer within the range and adds one to the 
				//highest value to be inclusive
				int num = generateNumber(low, high + 1);
				
				//Checks if the number generated is NOT in uniqueNums
				if (!(isNumInArr(uniqueNums, num)))
				{
					//Add the number at the index of uniqueNums and set 
					//isUnique to true to stop looping
					uniqueNums[i] = num;
					isUnique = true;
				}
			}
		}
		
		//Returns the array of unique integers
		return uniqueNums;
	}
	
	// *** MUTATOR METHODS ***
	
	//Doesn't require any parameters or return anything
	//Adds one to the gamesPlayed integer
	public void incrementGamesPlayed()
	{
		gamesPlayed++;
	}
	
	//Doesn't require any parameters or return anything
	//Adds one to the wins integer
	public void incrementWins()
	{
		wins++;
	}
	
	//Doesn't require any parameters or return anything
	//Adds one to the losses integer
	public void incrementLosses()
	{
		losses++;
	}
	
	//Doesn't require any parameters or return anything
	//Adds one to the over time losses integer
	public void incrementOvertimeLs()
	{
		overTimeLs++;
	}
	
	//Requires an integer for the amount of goals scored. Nothing returned
	//Adds goals to the goalsFor integer
	public void addGoalsFor(int goals)
	{
		goalsFor += goals;
	}
	
	//Requires an integer for the amount of goals scored. Nothing returned
	//Adds goals to the goalsAgainst integer
	public void addGoalsAgainst(int goals)
	{
		goalsAgainst += goals;
	}
	
	// *** ACCESSOR METHODS ***
	
	//Doesn't require anything. Returns the points of a team as an integer
	//Calculates the points using the expression shown below and returns it
	public int getPoints()
	{
		//Gives a value of 2 to each win and value of one to each overtime loss
		points = (wins * 2) + overTimeLs;
		return points;
	}

	//Doesn't require anything. Returns the difference of points as an integer
	//Calculates the points using the expression shown below and returns it
	public int getDifference()
	{
		//Subtracts goals for with goals against to get the difference of goals
		difference = goalsFor - goalsAgainst;
		return difference;
	}
	
	//Doesn't require any parameters. Returns the team number as an integer
	public int getTeamNumber()
	{
		return teamNumber;
	}

	//Doesn't require any parameters. Returns the team name as a string
	public String getTeamName()
	{
		return teamName;
	}
	
	//Doesn't require any parameters. 
	//Returns player array of all the players on team
	public Player[] getAllPlayers()
	{
		return allPlayers;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of games the team played
	public int getGamesPlayed()
	{
		return gamesPlayed;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of wins the team has
	public int getWins()
	{
		return wins;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of regulation losses the team has
	public int getLosses()
	{
		return losses;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of overtime losses the team has
	public int getOverTimeLs()
	{
		return overTimeLs;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of goals for the team has
	public int getGoalsFor()
	{
		return goalsFor;
	}

	//Doesn't require any parameters. 
	//Returns integer for the amount of goals against the team has
	public int getGoalsAgainst()
	{
		return goalsAgainst;
	}
}
