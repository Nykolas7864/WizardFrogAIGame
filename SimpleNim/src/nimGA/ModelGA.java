package nimGA;
import objects.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;




public class ModelGA extends ViewGA{
	
	
	
	static ArrayList<Integer> arr = new ArrayList<Integer>();
	static ViewGA v = new ViewGA();
	
	static String totalCount = v.totalTextField.getText();
	static int counter = Integer.parseInt(totalCount);	
    
    // Game logic
    static int totalMarbles = counter;
    static int turn;
    static int winner = 0;
    
    // Players
    static Player p1;
    static Player p2;
    static Player[] players;
    
    // Import objects
    static Random rand;
    static Scanner in;
    
    // Get number of marbles a player wants to take.
    private static int getMarbles() {
        int numMarbles = 0;
        v.inaction = 1;
        
        try {
            do {
                v.Console.append(" - Select button of number to remove (1-3): ");
                   numMarbles = v.Action();
                
            } while(v.inaction == 1); // between 1-3
        } catch(Exception e) {
            System.out.println(e);
        }
        
        return numMarbles;
    }
    
private static int AITurn(int totalMarbles2) {
	if(v.chromosome[totalMarbles - 1] == 0) {
		Random randy = new Random();
		return (randy.nextInt(3) + 1);
	}
	else {
		return v.chromosome[totalMarbles - 1];
	}
	}
    
    // Main game loop.
    private static void run() {
    	
    	rand = new Random();
        in = new Scanner(System.in);
        
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");
        players = new Player[2];
        
        players[0] = p1;
        players[1] = p2;
        
        //Determines who goes first randomly
        turn = rand.nextInt(2);
    	v.stop = false;
        do {
            for(Player p: players) {
                
                // Handles first turn.
                switch(turn) {
                    case 0:
                        break;
                    case 1:
                        turn = 0;
                        continue;
                }
                
                v.Console.setText("\n" + p.getName() + "'s Turn");
               // v.Console.append("\nTotal Marbles Left: " + totalMarbles);
                int q = getMarbles();
                totalMarbles = totalMarbles - q;
                v.updateState(totalMarbles, p.getName());
                
                // Trigger win condition.
                if(totalMarbles <= 0) {
                   // isWinner(p);
                    break;
                }
            }
        } while(!v.stop);
    }

	// Handle game logic.
    public static void main(String args[]) { 
        while(true) {
        	int currentGen = v.gen;
        	do {
        		v.start(currentGen);
        	} while (v.start == 0);
        	if (v.start == 2) {
        		v.start = 0;
        		continue;
        	}
        	totalMarbles = 15;
        	v.wizardF.setVisible(true);
        	for(int i = 0; i < 14; i++) {
        		v.lily[i].setVisible(true);;
        	}
        	run_AI();
        	v.start = 0;
        }
    }

	private static void run_AI()  {		

		
        p1 = new Player("Player 1");
        p2 = new Player("AI");
        players = new Player[2];
        
        players[0] = p1;
        players[1] = p2;
        
        //Determines who goes first randomly
        if(v.LearningCheck.isSelected()) {
        	turn = 1;
        }
        else {
        	turn = 0;
        }
		v.stop = false;
		// createObj(); //This method was originally only used to create a base descriptive file
		do {
                
                // Handles first turn.
			if(turn == 0) {
				
                	v.Console.setText("\n" + p1.getName() + "'s Turn");
                    // v.Console.append("\nTotal Marbles Left: " + totalMarbles);
                	
                	if (totalMarbles != 0) {
                		int q = getMarbles();
                        totalMarbles = totalMarbles - q;
                        turn = 1;
                	}
                	else {
                		winner = 0;
                	}
                    v.updateState(totalMarbles, p1.getName());
			}
			else {
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("possible error");
				}
                	v.Console.setText("\n" + p2.getName() + "'s Turn");
                	
                	if (totalMarbles != 0) {
                		int p = AITurn(totalMarbles);
                		totalMarbles = totalMarbles - p;
                		turn = 0;
                	
                	}
                	else {
                		winner = 1;
                	}
                	v.updateState(totalMarbles, p2.getName());
                    
            }
        } while(!v.stop);
		System.out.println("game over");
		
		
	}

	private static void Learn(int win) {
		// AI Won
		if (v.winner == 2) {
			int count = 0;
			while (!arr.isEmpty()) {
				
				int m = arr.get(count);
				

				System.out.println("AI Won!");
				

				
				arr.remove(count);
			}			
		}
		
		// AI Lost
		else {
			
			int count = 0;
			while (!arr.isEmpty()) {
				int m = arr.get(count);
								
				System.out.println("AI Lost!");
				
				arr.remove(count);
			}
			
			
		}		
	}



	
}