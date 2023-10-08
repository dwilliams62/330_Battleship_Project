import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;

//ImageIcon icon = new ImageIcon("Wave2.png");
//userBoard[0][0].setIcon(icon);
//code to retroactively change the image on a jlabel

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard; //Enemy Board at top
	private JLabel [][] userBoard; //User board at bottom
	private JPanel [][] gap; // space in between to interact with player
	private JButton auto; // automatic button to place ships
	private BattleshipModel model; // using model
	ImageIcon buttonIcon = new ImageIcon("Wave2.png"); // importing image
	
	//constructor
	BattleshipView(BattleshipModel gameModel){
		super("BattleShip Game"); // title bar
		
		setLayout(new GridLayout(30,30)); // grid layout is 30 by 30
		
		enemyBoard = new JButton[10][10]; // 100 buttons for enemy board ( 10 * 10)
		EnemyButtonHandler enemyHandler = new EnemyButtonHandler(); // button handler
		char letter = 'A';
		int number = 1;
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyBoard[i][j] = new JButton(buttonIcon); 
				enemyBoard[i][j].setEnabled(true);
				enemyBoard[i][j].setText(letter + Integer.toString(number));
				enemyBoard[i][j].setHorizontalTextPosition(JButton.CENTER);
				enemyBoard[i][j].setVerticalTextPosition(JButton.CENTER);
				enemyBoard[i][j].setMargin(new Insets(0,0,0,0));
				
				add(enemyBoard[i][j]);
				enemyBoard[i][j].addActionListener(enemyHandler);
				number++;
			}
			letter++;
			number = 1;
		}
		
		AutoButtonHandler autoHandler = new AutoButtonHandler(); // This is for automatic placement
		gap = new JPanel[10][10]; // 100 element space
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gap[i][j] = new JPanel();
				
				// Setting button only at the end and not using other space for the button
				// or it will only add the blank space
				if (i == 9 && j == 9) {
					auto = new JButton();
					auto.setText("Auto");
					add(auto);
					auto.addActionListener(autoHandler);
					// I need to work on this part that if the player clicks the button there should be changes
					// in the user board panel and the ships should be on the board.
					auto.setActionCommand("automatic");
				}
				else {
					add(gap[i][j]);
				}
			}
		}
		
		// User board with 100 test area.
		userBoard = new JLabel[10][10];
		// This is the idea to put in the view that if automatic is not selected then there are no ships 
		// yet and we need to place the ships. Further, we can add the drag and drop and if the number of 
		// ships are 0 left then we can make the boolean ships as true and start the game.
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				userBoard[i][j] = new JLabel();
				userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				add(userBoard[i][j]);
			}
		}

		model = gameModel;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,700);
		setVisible(true);
	}
	

	// button handler for the enemy board.
	private class EnemyButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton tempBtn = (JButton)event.getSource();
			//tempBtn.setEnabled(false);
			//check hit on enemy through network
			
			//as a temporary measure for testing, going to check hit on myself
			//check if its a hit and act accordingly
        	String result = model.checkHit(tempBtn.getText());
        	
        	//if it's a hit, check if player1 won, and if they have end the game
        	if (result == "HIT") {
        		tempBtn.setText("X");
        		tempBtn.setForeground(Color.red);
        		result = model.checkWinner();
        		if (result == "YOU LOST") {
        			DisplayResults(result);
        			System.exit(0);
        		}
        	}	
        	else if (result == "DUPE") {
        		return;
        	}
        	else {
        		tempBtn.setText("O");
        	}
		}
	}
	
	// button handler (handle) for the automatic button
	private class AutoButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == auto) {
				auto.setEnabled(false);
				
				if (!model.GetShipPlacementStatus("Carrier")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyCarrier = {"A1", "A2", "A3", "A4", "A5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[0][0].setText("Ca"); userBoard[0][1].setText("Ca");
				    	userBoard[0][2].setText("Ca"); userBoard[0][3].setText("Ca");
				    	userBoard[0][4].setText("Ca");
				    }
				    else if (temp == 2) {
				    	String[] placeMyCarrier = {"B1", "B2", "B3", "B4", "B5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[1][0].setText("Ca"); userBoard[1][1].setText("Ca");
				    	userBoard[1][2].setText("Ca"); userBoard[1][3].setText("Ca");
				    	userBoard[1][4].setText("Ca");
				    }
				    else {
				    	String[] placeMyCarrier = {"C1", "C2", "C3", "C4", "C5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[2][0].setText("Ca"); userBoard[2][1].setText("Ca");
				    	userBoard[2][2].setText("Ca"); userBoard[2][3].setText("Ca");
				    	userBoard[2][4].setText("Ca");
				    }
				}
				
				if (!model.GetShipPlacementStatus("Battleship")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyBattleship = {"E3", "F3", "G3", "H3"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][2].setText("Ba"); userBoard[5][2].setText("Ba");
				    	userBoard[6][2].setText("Ba"); userBoard[7][2].setText("Ba");
				    }
				    else if (temp == 2) {
				    	String[] placeMyBattleship = {"E4", "F4", "G4", "H4"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][3].setText("Ba"); userBoard[5][3].setText("Ba");
				    	userBoard[6][3].setText("Ba"); userBoard[7][3].setText("Ba");
				    }
				    else {
				    	String[] placeMyBattleship = {"E5", "F5", "G5", "H5"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][4].setText("Ba"); userBoard[5][4].setText("Ba");
				    	userBoard[6][4].setText("Ba"); userBoard[7][4].setText("Ba");
				    }
				}
				
				if (!model.GetShipPlacementStatus("Cruiser")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyCruiser = {"A7", "B7", "C7"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[0][6].setText("Cr"); userBoard[1][6].setText("Cr");
				    	userBoard[2][6].setText("Cr");
				    }
				    else if (temp == 2) {
				    	String[] placeMyCruiser = {"A9", "B9", "C9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[0][8].setText("Cr"); userBoard[1][8].setText("Cr");
				    	userBoard[2][8].setText("Cr");
				    }
				    else {
				    	String[] placeMyCruiser = {"B7", "B8", "B9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[1][6].setText("Cr"); userBoard[1][7].setText("Cr");
				    	userBoard[1][8].setText("Cr"); 
				    }
				}
				
				if (!model.GetShipPlacementStatus("Submarine")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMySubmarine = {"E7", "F7", "G7"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[4][6].setText("Su"); userBoard[5][6].setText("Su");
				    	userBoard[6][6].setText("Su");
				    }
				    else if (temp == 2) {
				    	String[] placeMySubmarine = {"E9", "F9", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[4][8].setText("Su"); userBoard[5][8].setText("Su");
				    	userBoard[6][8].setText("Su");
				    }
				    else {
				    	String[] placeMySubmarine = {"G7", "G8", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[6][6].setText("Su"); userBoard[6][7].setText("Su");
				    	userBoard[6][8].setText("Su"); 
				    }
				}
				
				if (!model.GetShipPlacementStatus("Destroyer")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyDestroyer = {"I9", "I10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[8][8].setText("De"); userBoard[8][9].setText("De");
				    }
				    else if (temp == 2) {
				    	String[] placeMyDestroyer = {"J9", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[9][8].setText("De"); userBoard[9][9].setText("De");
				    }
				    else {
				    	String[] placeMyDestroyer = {"I10", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[8][9].setText("De"); userBoard[9][9].setText("De"); 
				    }
				}
			}
		}
	}
	
	public void DisplayResults(String resultMessage) {
		JOptionPane.showMessageDialog(this, resultMessage);
	}
}
