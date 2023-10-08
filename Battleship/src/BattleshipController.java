import javax.swing.JFrame;

public class BattleshipController {

	public static void main(String[] args) {
		//start the games view with a made model
		BattleshipModel gameModel = new BattleshipModel();
		BattleshipView gameView = new BattleshipView(gameModel);
		
		
	}
	
}
