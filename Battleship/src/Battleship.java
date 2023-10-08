import java.util.Scanner;

public class Battleship {

	public static void main(String[] args) {
		String host;
	    if (args.length == 0) {
	    	host = "127.0.0.1";
	    }
	    else {
	    	host = args[0];
	    }
	    
		//start the games view with a made model
		BattleshipModel gameModel = new BattleshipModel();
		BattleshipView gameView = new BattleshipView(gameModel);
		BattleshipController gameController = new BattleshipController(gameModel, gameView, host);
		gameView.registerController(gameController);
		gameController.RunClient();
	}

}
