package ca.uwaterloo.connect4optm;

public class GameUtils {
	public enum PieceType {
		Player1, Player2, Empty
	}

	// Characters used to represent the human, computer, and open spots
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	public static final int OPEN_SPOT = 0;

	public static int mScreenWidth = -1;
	public static int mScreenHeight = -1;

	public static final int NUMBER_FINAL_DISK = 4;

	public static final int NUMBER_OF_PATHS = 100;

	public static int players_Num = 0;

	public static final String PREFS_NAME = "MyPrefsFile";
	public static final String PREFS_SOUND = "sound";
	public static final String PREFS_PLAY = "player";
	public static final String PREFS_DIFF = "difficulty";
	public static final String PREFS_TURN = "turn";
	public static final String PREFS_ALG = "algorithm";
	public static final String PREFS_SIZE = "size";

	
	//Minimax constants
	public static final int[] MINIMAX_DIFFICULTY_LEVEL = {3, 5, 7};

	
	public static int easyLevel = 0;
	public static int initPlayers = 0;
	public static int goFst = 0;
	public static int algoFst = 0;
	public static int szFst = 0;

	// public static final int ANIMATION_FALLING_TIME = 1200;
	public static final int ANIMATION_FALLING_TIME = 960;
	
	// Demo?
	public static final boolean DEMO = true;

}

enum Players {
	ONE_PLAYER, TWO_PLAYER;

}

class PlayersNum {
	Players playersNum;

	public PlayersNum(Players playersTurn) {
		this.playersNum = playersTurn;
	}

	public int getNumPlayers() {
		switch (playersNum) {
		case ONE_PLAYER:
			return 0;

		default:
			return 1;

		}
	}

}
