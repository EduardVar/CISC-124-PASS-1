/**
 * Author:	Eduard Varshavsky
 * Date:	January 21, 2019
 * Desc:	This is the Game class that maintains the attributes and methods
 * 			that are used to play a game of hockey 
 */

import java.util.Random;

public class Game
{
	// *** CLASS VARIABLES ***
	
	//Creates a new instance of the Random class called random
	private Random random = new Random();

	//Created constant integers for total bonuses for each position
	private static final int FORWARD_BONUS = 25;
	private static final int DEFENSEMEN_BONUS = 40;
	private static final int GOALIE_BONUS = 60;

	//Created constant integers for the value required to feel poorly or well
	private static final int POORLY = -1, WELL = 1;

	//Created two team objects to hold each team playing a game in
	private Team team1, team2;
	
	//Two integers to store the total power level of a team in game
	private int t1, t2;
	
	//Two integers to store the goals scored of each team
	private int goals1, goals2;
	
	// *** CONSTRUCTOR METHODS ***

	//Requires two Team objects. One for each team playing the game
	//Constructor for playing a game. Automatically runs simulation of game
	public Game(Team team1, Team team2)
	{
		//Sets the object's attributes to those provided in instantiation
		this.team1 = team1;
		this.team2 = team2;
		
		//Simulates a game with current data provided
		playGame();
	}
	
	// *** OBJECT-BEHAVIOUR METHODS ***

	//Doesn't require or return anything
	//Executes methods and light logic to simulate a game being played 
	public void playGame()
	{
		//Adds one to the amount of games each team has played
		team1.incrementGamesPlayed();
		team2.incrementGamesPlayed();

		//Calculates the power totals of each team and then calculates goals
		//that will occur in a regulation game
		calculateRegTotals();
		calculateRegGoals();
		
		//Checks if one team has more goals than the other, or if it's a tie
		if (goals1 > goals2)
		{
			//Team 1 has with more goals. Team 1 gains win, Team 2 gains loss
			team1.incrementWins();
			team2.incrementLosses();
		}
		else if (goals1 < goals2)
		{
			//Team 2 has with more goals. Team 2 gains win, Team 1 gains loss
			team2.incrementWins();
			team1.incrementLosses();
		}
		else
		{
			//Both teams end with same goals. Does overtime by calculating
			//power totals for both overtime teams and initiating overtime
			calculateOverTotal();
			initiateOvertime();
		}
		
		//Added goals for and goals against for team 1 and team 2
		team1.addGoalsFor(goals1);
		team1.addGoalsAgainst(goals2);		
		team2.addGoalsFor(goals2);
		team2.addGoalsAgainst(goals1);
	}
	
	//Doesn't require or  return anything
	//Calculates the power totals of each teams with all factors considered
	public void calculateRegTotals()
	{
		//Generates an array for each team where each element represents how
		//a position is feeling (from -1 poorly, to 1 well)
		int[] team1Feels = {generateNumber(POORLY, WELL + 1), 
				generateNumber(POORLY,WELL+1), generateNumber(POORLY,WELL+1)};
		int[] team2Feels = {generateNumber(POORLY, WELL + 1), 
				generateNumber(POORLY,WELL+1), generateNumber(POORLY,WELL+1)};
		
		//Creates an array of point bonuses for each position (synergizes with
		//team1Feels and team2Feels based on indexes!)
		int[] bonuses = {FORWARD_BONUS, DEFENSEMEN_BONUS, GOALIE_BONUS};

		//Created player arrays to store each team's generated regulation team
		Player[] reg1 = team1.generateRegulation();
		Player[] reg2 = team2.generateRegulation();

		//Iterates through each player in each team's regulation line-up
		for (int i = 0; i < reg1.length; i++)
		{
			//Adds the player's skill level to the total
			t1 += reg1[i].getSkillLevel();
			t2 += reg2[i].getSkillLevel();
		}
		
		//Iterates through each positions current status
		for (int i = 0; i < team1Feels.length; i++)
		{
			//Multiplies positions feel (-1, 0, or 1) by the position's bonus
			t1 += (bonuses[i] * team1Feels[i]);
			t2 += (bonuses[i] * team2Feels[i]);
		}
	}
	
	//Doesn't require or return anything
	//Used to generate and calculate goals scored for each team based on their
	//totals calculated in calculateRegTotal()
	public void calculateRegGoals()
	{
		//Integer divides each teams total by 50, gives 50 sets for scoring
		int fiftySets1 = t1 / 50;
		int fiftySets2 = t2 / 50;
		
		//Iterates through each set of 50 for team 1
		for (int i = 0; i < fiftySets1; i++)
			//Adds a random number of goals to goals1 from 0 to 2 per set of 50
			goals1 += generateNumber(0, 2 + 1);
		
		//Iterates through each set of 50 for team 2
		for (int i = 0; i < fiftySets2; i++)
			//Adds a random number of goals to goals2 from 0 to 2 per set of 50
			goals2 += generateNumber(0, 2 + 1);
		
		//Checks if there is a remainder of points after dividing by 50 for t1
		if (t1 % 50 > 0)
			//Adds a random number of goals to goals1 from 0 to 1
			goals1 += generateNumber(0, 1 + 1);
		
		//Checks if there is a remainder of points after dividing by 50 for t2
		if (t2 % 50 > 0)
			//Adds a random number of goals to goals2 from 0 to 1
			goals2 += generateNumber(0, 1 + 1);
	}
	
	//Doesn't require or return anything
	//This method is used to calculate the totals for each time during an
	//overtime scenario
	public void calculateOverTotal()
	{
		//Resets the totals for each team back to 0
		t1 = 0;
		t2 = 0;
		
		//Created player arrays to store each team's generated overtime team
		Player[] over1 = team1.generateOvertime();
		Player[] over2 = team2.generateOvertime();
		
		//Iterates over each player in each team's overtime line-up
		for (int i = 0; i < over1.length; i++)
		{
			//Adds player's score level to the total of their respective teams
			t1 += over1[i].getSkillLevel();
			t2 += over2[i].getSkillLevel();
		}
	}
	
	//Doesn't require or return anything
	//Used to simulate overtime occurring between the two teams. 
	//calculateOverTotal() should be run before to update totals for overtime
	public void initiateOvertime()
	{
		//Checks if one of the teams total is more than the other, or if 
		//they're the same score
		if (t1 > t2)
		{
			//If team's 1 total is more than team 2's, team 1 gains a goal
			goals1++;
		}
		else if (t1 < t2)
		{
			//If team's 2 total is more than team 1's, team 2 gains a goal
			goals2++;
		}
		else
		{
			//Randomly chooses a number between 1 and 2 to decide which team
			//wins because they are tied by points
			int winner = generateNumber(1, 2 + 1);
			
			//Checks if the winner is team 1
			if (winner == 1)
			{
				//Adds a goal to team 1's goals
				goals1++;
			}
			else
			{
				//Adds a goal to team 2's goals
				goals2++;
			}
		}
		
		//Checks if team 1 has more goals than team 2
		if (goals1 > goals2)
		{
			//Team 1 gains a win, team 2 gains an overtime loss
			team1.incrementWins();
			team2.incrementOvertimeLs();
		}
		else
		{
			//Team 2 gains a win, team 1 gains an overtime loss
			team2.incrementWins();
			team1.incrementOvertimeLs();
		}
	}

	//Requires an integer for the low range and an integer for the high range
	//Returns a random number within the range provided
	//use to create a random number in code
	public int generateNumber(int low, int high)
	{
		return random.nextInt(high - low) + low;
	}
	
	// *** MUTATOR METHODS ***
	
	// *** ACCESSOR METHODS ***
	
}
