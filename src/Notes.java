

public class Notes
{
	//GENERAL DESCRIPTION
	/*
	 * Team will have to finish at least 8th in conference to get to playoffs
	 * Eastern conference - 16 teams (don't worry about western)
	 * 	Every eastern conference team plays two games against every other team
	 *  in same conference (one as visitor and one as a host)
	 * 
	 * If at the end of regulation time, both teams are tied, game goes goes to 
	 * overtime (first to score wins)
	 * 
	 * Winning team gets 2 points, losing team gets 0 points (REGULATION end)
	 * Winning team gets 2 points, losing team gets 1 point  (OVERTIME end)
	 * 
	 * 13 forwards, 8 defensemen, 4 goalies
	 * 
	 * Players on leafs already have skill levels
	 * Other team's players will have each player skill level randomly generated
	 * based on ranges provided by each team
	 * 
	 * Other team's players will be generated UNIQUE identifier number between
	 * 1 and 99
	 * 
	 * Name Fn (forwards) Dn (defenseman) or Gn(goalie) where n is unique 
	 * identifier generated for player (Ex: Forward player 42 is called F42)
	 * 
	 * 
	 * OUTCOME OF GAMES
	 * 	- Add up skill level of all forwards and defensemen in each two teams
	 * 	  and call these totals P1 and P2
	 *  - Randomly select one of the 4 goalies in each team (G1 and G2)
	 *  - Total skill level for teams as: T1 = P1 + G1 and T2 = P2 + G2
	 *  - Randomly decide if forward, defensemen and goalie play well, poorly,
	 *    or at their specified skill level (Add, subtract, or nothing w bonus)
	 *  - Forward Bonus: 25, Defensemen bonus: 40, Goalie Bonus: 60
	 *  
	 *  - During Regulation time: each team will randomly score 0, 1, or 2 goals
	 *    for each 50 points computed in total skill level (T1 or T2) and will
	 *    score 0 or 1 goals for remainder fraction of 50 points
	 *  - During Overtime: each team will randomly select 3 players among forwards
	 *    and defensemen and will randomly select one goalie (Recalculate total
	 *    skill level with now a 4 player team). Team with biggest 4 person score
	 *    will score goal and win the game (If skill levels are equal, program
	 *    will randomly decide who scores first goal
	 *    
	 *    
	 *    DOES FEELING BONUSES APPLY IF DURING OVERTIME (WHAT IF ALL FORWARDS/
	 *    ALL DEFENSEMEN)?	Overtime, bonuses don't apply
	 *    
	 *    CHARACTER LIMIT PER LINE? 80
	 *    
	 *    
	 *    
	 *    REGENERATE TEAMS EVERY NEW SIMULATION (leafs stay the same)? 
	 *    
	 *    ALLOWED TO USE while (true)?	- Helps with clean code
	 *    
	 *    
	 *    DO WE HAVE TO ANSWER QUESTION 2 OF ANALYSIS IF THEY MADE THE PLAYOFFS?
	 */
}
