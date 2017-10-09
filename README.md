# MaxConnect4-Game-Play

README
=======


Programming language used - JAVA


To generate the binary files go to the folder where the source code is present and run the cmd : 
		
		"javac *.java" - will generate the class files in the folder where the source code is present.

To run the application fo to folder "bin" and run the below commands:
=====================================================================

* for interactive mode : 

	java MaxConnect4 interactive inputFile.txt human-next/computer-next depthLevel

* for one-move mode : 

	java MaxConnect4 one-move inputFile.txt outputFile.txt depthLevel


Code Structure:
===============

3 classes:

1. MaxConnect4.java
	
	methods additionally added and modified:
		
		1.1. main()
				if (interactive mode specified in cmd line argument)
					call method playInteractiveMode

				else if ( one-move mode)
					call computerMove
		
		1.2. playInteractiveMode
			this method plays the interactive mode between human and computer
			depending on whether computer-next or humnan-next repective order of below methods is called

		1.3. computerMove
			 this method uses the AiPlayer.depthLimitedPlay() method to use alphaBeta with depth limited alg to determine the column to make the move
			 Saves the current board state after the move is made in a file called computer.txt(for interactive mode) and <outputFileName>.txt based on file-name passed as parameter for one-move mode.

		1.4. humanMove
			this method is used to receive the column number between (1-7) from the user to make its move.
			Any number other than (1-7) or a number that is invalid move(no zeros in that column) will not be accepted, will be prompted again until a valid number is provided
			then we call the playPiece method of GameBoard by passing the column number(1-7) recevied from the user.
			Saves the current board state after the move is made in a file called human.txt

2. ApiPlayer.java

	changes made and methods added:

		2.1. Added two class variables - 
				player - to store the player(1 or 2) for whom the depthLimitedPlay is being called
				int order[]  - array to decide on the order in which the search has to be done, because order matters in Alpha-Beta Algorithm.
								Center ones are first, because they have the highest advantage

		2.1. depthLimitedPlay() - 2 parameters - currentGame, depthLevel
				this method calls MaxValue() to return the column to play with max value

		2.2. maxValue() - 4 parameters - boardState, alpha, beta, depthLevel
				this method makes recursive calls to minValue() method for each of the next possible moves() based on the order we have defined
				pruning will happen based in the algorithm and hence not all possibles moves would be recursively called
				it tests if it has reached the specified depth or a terminal state and if it has then it returns that states evaluation value

		2.3. minValue() - 	4 parameters - boardState, alpha, beta, depthLevel
				this method makes recursive calls to maxValue() method or each of the next possible moves() based on the order we have defined
				pruning will happpen based on the algorithm and hence not all posibles moves would be recursively called
				it tests if it has reached the specified depth or a terminal state and if it has then it returns that states evaluation value

		2.4. eval() - 1 parameter - boardState
				finds difference between scores of two players (player for which move is being made - oppponents)
				the class variable - "player" is used here to determine the current player vs opponent

		2.5. utilityValue() - 1 param - boardState
				0 -> draw (equal scores)
    			100000 -> player wins (current player's score > opponent's score)
    			-100000 -> player loses (current player's score < opponent's score)

3. GameBoard.java

	changes made and methods added:

		3.1. changes to implement if the input file does not exist, a new file will be created with the passed parameter name with an empty board state.

		3.2. in isValidPlay() - changed the upper bound to (i < 7) instead of (i <= 7) because when user enters 8, the call to  isValidPlay(7) will throw an ArrayIndexOutOfBoundException


References:
===========
Jenny Lam. "Heuristics in the game of Connect-K" - http://inside.mines.edu/~huawang/CSCI404_Projects/Project2/connectk.pdf

