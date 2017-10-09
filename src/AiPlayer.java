import java.util.*;
import java.math.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * The constructor essentially does nothing.
 *
 */

public class AiPlayer
{
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    private int player;
    private int order[] = {3,2,4,1,5,0,6};
    private static final int  WIN = 10000;
    private static final int  LOSE = -10000;
    private static final int  DRAW = 0;

    public AiPlayer()
    {
        // nothing to do here
    }

    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     *@param depthLevel The depth upto which the recursion is allowed
     * @return an integer(0-6) indicating which column the AiPlayer would like
     * to play in.
     */
    public int depthLimitedPlay(GameBoard currentGame, int depthLevel ){

        int alpha = -1000000;
        int beta = 1000000;
        int v = alpha;
        int res[] = {alpha,alpha,alpha,alpha,alpha,alpha,alpha};
        this.player = currentGame.getCurrentTurn();
        for(int i : order) {
            if (currentGame.isValidPlay(i)) {
                GameBoard nextState = new GameBoard(currentGame.getGameBoard());
                nextState.playPiece(i);
                v = Math.max(v, minValue(nextState, alpha, beta, depthLevel-1));
                res[i] = v;
            }
        }
        int largestAt=-1;
        int largest = -1000000;
        for (int i : order){
           // System.out.println("The values are : " + i + " : " + res[i]);
            if(currentGame.isValidPlay(i) && res[i] > largest){
                largest=res[i];
                largestAt=i;
            }
        }
        return largestAt;
    }

    private int maxValue(GameBoard boardState, int alpha, int beta, int depthLevel) {

        if(isTerminal(boardState))
            return utilityValue(boardState);
        if( depthLevel == 0 )
            return eval(boardState);

        int v = -1000000;
        for(int i : order){
            if(boardState.isValidPlay(i)){
                GameBoard nextState = new GameBoard(boardState.getGameBoard());
                nextState.playPiece(i);
                v = Math.max(v, minValue(nextState, alpha, beta, depthLevel-1));
                if(v >= beta){
                    return v;
                }
                alpha = Math.max(alpha, v);
            }
        }
        return v;
    }

    private boolean isTerminal(GameBoard boardState) {
        return boardState.getPieceCount()==42 ? true : false;
    }

    private int minValue(GameBoard boardState, int alpha, int beta, int depthLevel){
        if(isTerminal(boardState))
            return utilityValue(boardState);
        if( depthLevel == 0 )
            return eval(boardState);

        int v = 1000000;
        for(int i : order) {
            if (boardState.isValidPlay(i)) {
                GameBoard nextState = new GameBoard(boardState.getGameBoard());
                nextState.playPiece(i);
                v = Math.min(v, maxValue(nextState, alpha, beta, depthLevel-1));
                if (v <= alpha) {
                    return v;
                }
                beta = Math.min(beta, v);
            }
        }
        return v;
    }

    /**
     *
    *   0 -> draw (equal scores)
    *   100000 -> player wins (current player score > opponent's score)
    *   -100000 -> player loses (current player score < opponent's score)
    */
    private int utilityValue(GameBoard boardState){
        int uv = 0;
        if(this.player == 1){
            uv = (boardState.getScore(1) > boardState.getScore(2)) ? WIN :
                    (boardState.getScore(1)==boardState.getScore(2))? DRAW : LOSE;
            return uv;
        }

        uv = (boardState.getScore(2) > boardState.getScore(1)) ? WIN :
                (boardState.getScore(2) == boardState.getScore(1)) ? DRAW : LOSE;
        return uv;

    }

    private int eval(GameBoard boardState){
        int ev = 0;
        if(this.player == 1){
            ev = (boardState.getScore(1)-boardState.getScore(2));
            return ev;
        }
        ev = (boardState.getScore(2)-boardState.getScore(1));
        return ev;
    }
}