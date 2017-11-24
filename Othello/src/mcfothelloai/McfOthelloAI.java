/*
 * 
 * author: Cole Frost
 * date: 9/20/2017
 */
package mcfothelloai;

import java.util.ArrayList;

public class McfOthelloAI {

	public int color;
	public char cColor;

	public McfOthelloAI(int color) {
		this.color = color;
		if (color == Game.BLACK) {
			cColor = 'B';
			System.out.println("R B");
		} else if (color == Game.WHITE) {
			cColor = 'W';
			System.out.println("R W");
		}
	}

	// public Move findMove(Board board){
	// ArrayList<Move> test = board.generateMoves(color);
	// if(test.isEmpty()){
	// return new Move(-1,-1);
	// }
	// Random RNG = new Random();
	// int rMove = RNG.nextInt(test.size());
	// return test.get(rMove);
	// }

	public Move findMove(Board currentBoard, int ply, int player, double alpha, double beta, int maxDepth) {
		if (ply >= maxDepth) {
			Move returnMove = new Move(0, 0);
			returnMove.value = currentBoard.evaluate();
			return returnMove;
		} else {
			/*
			 * 1. generate Moves for player 2. if MoveList is empty, add passmove to
			 * movelist 3. bestMove = moveList.get(0) 4. for each move in the move list a.)
			 * newBoard = currentBoard.applyMove(player, move) b.) tempMove =
			 * alphaBeta(newBoard, ply+1, -player, -beta, -alpha, maxDepth) c.) move.value =
			 * -tempMove.value; d.) if moveValue > alpha c0) bestMove = move c1) alpha =
			 * moveValue c2) if alpha > beta return bestMove 5.) return bestMove
			 */
			ArrayList<Move> moves = currentBoard.generateMoves(player);
			if (moves.isEmpty())
				moves.add(new Move(0, 0)); // add pass move if empty
			Move bestMove = moves.get(0);
			for (Move move : moves) {
				Board newBoard = new Board(currentBoard);
				newBoard.applyMove(player, move);
				Move tempMove = findMove(newBoard, ply + 1, -player, -beta, -alpha, maxDepth);
				move.value = -tempMove.value;
				if (move.value > alpha) {
					bestMove = move;
					alpha = move.value;
					if (alpha > beta)
						return bestMove;
				}
			}
			return bestMove;
		}

	}

}
