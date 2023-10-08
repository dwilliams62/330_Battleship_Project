import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.text.html.ImageView;

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard; //Enemy Board at top
	private JPanel [][] userBoard; //User board at bottom
	private JPanel [][] gap; // space in between to interact with palyer
	private JButton auto; // automatic button to place ships
	private BattleshipModel model; // using model
	ImageIcon buttonIcon = new ImageIcon("Wave2.png"); // importing image
	BattleshipView(BattleshipModel gameModel){
		super("BattleShip Game"); // title bar
		
		setLayout(new GridLayout(30,30)); // grid layout is 30 by 30
		enemyBoard = new JButton[10][10]; // 100 buttons for enemy board ( 10 * 10)
		ButtonHandler handler = new ButtonHandler(); // button handler
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyBoard[i][j] = new JButton(buttonIcon); 
				enemyBoard[i][j].setEnabled(true);
				
				add(enemyBoard[i][j]);
				enemyBoard[i][j].addActionListener(handler);
			}
		}
		
		ButtonHandle handle = new ButtonHandle(); // This is for automatic placement
		gap = new JPanel[10][10]; // 100 element space
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gap[i][j] = new JPanel();
				
				// Setting button only at the end and not using other space for the button
				// or it will only add the blank space
				if (i > 8 && j > 8) {
					auto = new JButton();
					add(auto);
					auto.addActionListener(handle);
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
		userBoard = new JPanel[10][10];
		// This is the idea to put in the view that if automatic is not selected then there are no ships 
		// yet and we need to place the ships. Further, we can add the drag and drop and if the number of 
		// ships are 0 left then we can make the boolean ships as true and start the game.
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				userBoard[i][j] = new JPanel();
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
	private class ButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if (event.getSource() == enemyBoard[i][j]) {
						enemyBoard[i][j].setEnabled(false);
					}
				}
			}
		}
	}
	
	// button handler (handle) for the automatic button
	private class ButtonHandle implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
					if (event.getSource() == auto) {
						auto.setEnabled(false);
						
						if("automatic".equals(event.getActionCommand())){
							//ships = true;
							userBoard = new JPanel[10][10];
							for(int i = 0; i < 10; i++) {
								for (int j = 0; j < 10; j++) {
									userBoard[i][j] = new JPanel();
									userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.red));
									add(userBoard[i][j]);
								}
							}
						}
					}
				}
	}
}
