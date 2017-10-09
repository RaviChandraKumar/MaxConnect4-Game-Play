import java.util.Scanner;
/**
 *
 * This class controls the game play for the Max Connect-Four game. 
 *
 */

public class MaxConnect4
{
    public static void main(String[] args)
    {
        // check for the correct number of arguments
        if( args.length != 4 )
        {
            System.out.println("Four command-line arguments are needed:\n"
                    + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                    + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function( 0 );
        }

        // parse the input arguments
        String game_mode = args[0].toString();				// the game mode
        String input = args[1].toString();					// the input game file
        int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search

        // create and initialize the game board
        GameBoard currentGame = new GameBoard( input );

        AiPlayer calculon = new AiPlayer();

        int playColumn = 99;				//  the players choice of column to play
        boolean playMade = false;			//  set to true once a play has been made
        
        // get the output file name
        String output = args[2].toString();				// the output game file
        System.out.print("\nMaxConnect-4 game\n");

        /////////////   one-move mode ///////////
        System.out.print("game state before move:\n");

        //print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                ", Player2 = " + currentGame.getScore( 2 ) + "\n " );

        if( game_mode.equalsIgnoreCase( "interactive" ) )
        {
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("human-next")){

                playInteractiveMode(args[2].toString(), currentGame, calculon, playColumn, output, depthLevel);
            }
            else {
                System.out.println("\n" + args[2].toString() + " is an unrecognized player.  \n try again. \n");
                exit_function(0);
            }

            return;
        }

        else if( !game_mode.equalsIgnoreCase( "one-move" ) )
        {
            System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
            return;
        }



        // ****************** this chunk of code makes the computer play for one-move mode
        if( currentGame.getPieceCount() < 42 )
        {
            computerMove(currentGame, calculon, depthLevel, output);
        }
        else
        {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
        }

        //************************** end computer play

        return;

    } // end of main()

    private static void playInteractiveMode(String startPlayer, GameBoard currentGame, AiPlayer calculon, int playColumn, String output, int depthLevel) {

        Scanner inputFromUser;
        int current_player;
        String humanOutput = "human.txt";
        String computerOutput = "computer.txt";

        if (startPlayer.equalsIgnoreCase("computer-next")) {

            while(currentGame.getPieceCount() < 42){

                computerMove(currentGame, calculon, depthLevel, computerOutput);

                if(currentGame.getPieceCount() == 42)
                    break;

                humanMove(currentGame, humanOutput);

            }
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;

        }
        else
        {
            while(currentGame.getPieceCount() < 42){

                humanMove(currentGame, humanOutput);

                if(currentGame.getPieceCount() == 42)
                    break;

                computerMove(currentGame, calculon, depthLevel, computerOutput);

            }
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }
    }

    private static void computerMove(GameBoard currentGame, AiPlayer calculon, int depthLevel, String computerOutput) {
        int current_player;
        int playColumn;
        current_player = currentGame.getCurrentTurn();
        playColumn = calculon.depthLimitedPlay(currentGame, depthLevel);

        // play the piece
        currentGame.playPiece( playColumn );

        // display the current game board
        System.out.println("move " + currentGame.getPieceCount()
                + ": Player " + current_player
                + ", column " + (playColumn+1));
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();

        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                ", Player2 = " + currentGame.getScore( 2 ) + "\n " );

        currentGame.printGameBoardToFile( computerOutput );

    }

    private static void humanMove(GameBoard currentGame, String humanOutput) {
        Scanner inputFromUser;
        int current_player;
        int playColumn;
        System.out.println("\n Human's turn (1-7) :\n");
        inputFromUser = new Scanner(System.in);
        current_player = currentGame.getCurrentTurn();

        do {
            playColumn = inputFromUser.nextInt();
        } while (!currentGame.isValidPlay(playColumn-1));

        currentGame.playPiece( playColumn-1 );

        // display the current game board
        System.out.println("move " + currentGame.getPieceCount()
                + ": Player " + current_player
                + ", column " + playColumn);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();

        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                ", Player2 = " + currentGame.getScore( 2 ) + "\n " );

        currentGame.printGameBoardToFile( humanOutput );

    }

    /**
     * This method is used when to exit the program prematurly.
     * @param value an integer that is returned to the system when the program exits.
     */
    private static void exit_function( int value )
    {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit( value );
    }
} // end of class connectFour