public class BattleshipServer {

	public static void main(String[] args) {
		//start the games view with a made model
		BattleshipModel gameModel = new BattleshipModel();
		BattleshipView gameView = new BattleshipView(gameModel);
		BattleshipController gameController = new BattleshipController(gameModel, gameView);
		gameView.registerController(gameController);
		gameController.RunServer();
	}

}
