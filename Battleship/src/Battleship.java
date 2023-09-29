import java.util.Scanner;

public class Battleship {

	public static void main(String[] args) {
		//initialize player1 as mybattleship and player2 as oppbattleship
        BattleshipModel myBattleship = new BattleshipModel();
        BattleshipModel oppBattleship = new BattleshipModel();
        
        //set the ships placement to some random spots with random strings for testing
        String[] placeMyCarrier = {"A1", "B1", "C1", "D1", "E1"};
        String[] placeMyBattleship = {"A2", "B2", "C2", "D2"};
        String[] placeMyCruiser = {"A3", "B3", "C3"};
        String[] placeMySubmarine = {"A4", "B4", "C4"};
        String[] placeMyDestroyer = {"A5", "B5"};
        
        myBattleship.PlaceCarrierShip(placeMyCarrier);
        myBattleship.PlaceBattleshipShip(placeMyBattleship);
        myBattleship.PlaceCruiserShip(placeMyCruiser);
        myBattleship.PlaceSubmarineShip(placeMySubmarine);
        myBattleship.PlaceDestroyerShip(placeMyDestroyer);
        
        String[] placeOppCarrier = {"F6", "G6", "H6", "I6", "J6"};
        String[] placeOppBattleship = {"F7", "G7", "H7", "I7"};
        String[] placeOppCruiser = {"F8", "G8", "H8"};
        String[] placeOppSubmarine = {"F9", "G9", "H9"};
        String[] placeOppDestroyer = {"F10", "G10"};
        
        oppBattleship.PlaceCarrierShip(placeOppCarrier);
        oppBattleship.PlaceBattleshipShip(placeOppBattleship);
        oppBattleship.PlaceCruiserShip(placeOppCruiser);
        oppBattleship.PlaceSubmarineShip(placeOppSubmarine);
        oppBattleship.PlaceDestroyerShip(placeOppDestroyer);
        
        //for getting the input from the command line
        Scanner in = new Scanner(System.in);
        String input = " ";
        String result = " ";
        
    	System.out.print("LET'S PLAY SOME BATTLESHIP\n");
    	System.out.print("Player 1 goes first!\n");
        
    	//Continuously loops between player 1 and 2
        while (input != "QUIT") {
        	//read input
        	System.out.print("Player 1 Shot:");
        	input = in.nextLine();
        	
        	//check if its a hit and act accordingly
        	result = oppBattleship.checkHit(input);
        	
        	//if it's a hit, check if player1 won, and if they have end the game
        	if (result == "HIT") {
        		System.out.print("That's a hit!\n");
        		result = oppBattleship.checkWinner();
        		if (result == "YOU LOST") {
        			System.out.print("Player 2 Loses!\n");
        			System.exit(0);
        		}
        	}
        	
        	//if it's a duplicate shot, the player wasted their turn
        	else if (result == "DUPE") {
        		System.out.print("YOU ALREADY SHOT THERE IDIOT!\n");
        	}
        	
        	//anything else is a miss and the game continues on
        	else {
        		System.out.print("That's a miss!\n");
        	}
        	
        	//the game then prints the opponents health after the shot was taken
        	System.out.print("Player 2 Health: ");
        	System.out.print("Carrier: " + oppBattleship.GetCarrierStatus() + " Battleship: " + 
        			oppBattleship.GetBattleshipStatus() + " Cruiser: " + oppBattleship.GetCruiserStatus() + 
        			" Submarine: " + oppBattleship.GetSubmarineStatus() + " Destroyer: " + 
        			oppBattleship.GetDestroyerStatus() + "\n");
        	
        	//same stuff but for the opponent
        	System.out.print("Player 2 Shot:");
        	input = in.nextLine();
        	result = myBattleship.checkHit(input);
        	if (result == "HIT") {
        		System.out.print("That's a hit!\n");
        		result = myBattleship.checkWinner();
        		if (result == "YOU LOST") {
        			System.out.print("Player 1 Loses!\n");
        			System.exit(0);
        		}
        	}
        	else if (result == "DUPE") {
        		System.out.print("YOU ALREADY SHOT THERE IDIOT!\n");
        	}
        	else {
        		System.out.print("That's a miss!\n");
        	}
        	
        	System.out.print("Player 1 Health: ");
        	System.out.print("Carrier: " + myBattleship.GetCarrierStatus() + " Battleship: " + 
        			myBattleship.GetBattleshipStatus() + " Cruiser: " + myBattleship.GetCruiserStatus() + 
        			" Submarine: " + myBattleship.GetSubmarineStatus() + " Destroyer: " + 
        			myBattleship.GetDestroyerStatus() + "\n");
        }
	}

}
