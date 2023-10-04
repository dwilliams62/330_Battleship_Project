import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard;
	private JPanel [][] userBoard;
	private JPanel [][] gap;
	private BattleshipModel model;
	BattleshipView(BattleshipModel gameModel){
		
		super("BattleShip Game");
		
		setLayout(new GridLayout(30,30));
		enemyBoard = new JButton[10][10];
		ButtonHandler handler = new ButtonHandler();
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyBoard[i][j] = new JButton("");
				//(enemyBoard[i][j]).setSize(5, 7);
				enemyBoard[i][j].setEnabled(true);
				
				add(enemyBoard[i][j]);
				enemyBoard[i][j].addActionListener(handler);
			}
		}
		
		gap = new JPanel[10][10];
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gap[i][j] = new JPanel();
				//gap[i][j].setSize(5, 7);
				//gap[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				add(gap[i][j]);
			}
		}

		//setLayout(new GridLayout(10,10));
		userBoard = new JPanel[10][10];
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				userBoard[i][j] = new JPanel();
				//userBoard[i][j].setSize(5, 7);
				userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				add(userBoard[i][j]);
			}
		}
		model = gameModel;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,700);
		setVisible(true);
	}
	


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

}
