/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcfothelloai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author RH209s
 */
public class Game {

	public static final int BLACK = -1;
	public static final int WHITE = 1;
	public static final int BORDER = -2;
	public static final int EMPTY = 0;

	public static void main(String[] args) {

		Board board = new Board();

		// initialize color

		Scanner keyboard = new Scanner(System.in);
		int color = 0;
		while (color == 0) {
			System.out.println("C Please input color");
			String input = keyboard.nextLine();
			if (input.equals("I B")) {
				color = BLACK;
			} else if (input.equals("I W")) {
				color = WHITE;
			}
		}

		// create an AI of that color
		McfOthelloAI AI = new McfOthelloAI(color);

		int turn = BLACK;
		/*
		 * while(!board.gameOver()){ board.BoardDraw();
		 * 
		 * ArrayList<Move> test = board.generateMoves(turn); if (test.isEmpty()) {
		 * System.out.println("C test is empty"); } else { System.out.println("C " +
		 * Arrays.toString(test.toArray())); }
		 * 
		 * if(turn == AI.color){ Move nextMove = AI.findMove(board); if(nextMove.r<0 ||
		 * nextMove.c<0){ System.out.println(AI.cColor + "  "); }else {
		 * System.out.println(AI.cColor + " " + nextMove.getC() + " " +
		 * nextMove.getR()); board.applyMove(turn, nextMove); } }else{ Move nextMove =
		 * AI2.findMove(board); if(nextMove.r<0 || nextMove.c<0){
		 * System.out.println(AI2.cColor + "  "); }else { System.out.println(AI2.cColor
		 * + " " + nextMove.getC() + " " + nextMove.getR()); board.applyMove(turn,
		 * nextMove); } } turn = turn*-1; } board.BoardDraw();
		 * System.out.println(board.countBlack());
		 */

		// starting BLACK alternate turns
		while (!board.gameOver()) {
			board.BoardDraw();

			// get and print out the list of legal moves
			ArrayList<Move> moveList = board.generateMoves(turn);
			System.out.println("C " + Arrays.toString(moveList.toArray()));

			if (turn == AI.color) {
				Move nextMove = AI.findMove(board, EMPTY, BLACK, BLACK, turn, 6);
				if (nextMove.r < 0 || nextMove.c < 0) {
					System.out.println(AI.cColor + "  ");
				} else {
					System.out.println(AI.cColor + " " + nextMove.getC() + " " + nextMove.getR());
					board.applyMove(turn, nextMove);
				}
			} else {
				String input = keyboard.nextLine();
				if (input.equals("B") || input.equals("W")) {
					// pass turn
				} else {
					String[] arr = input.split(" ");
					if (board.applyMove(turn, new Move(Integer.valueOf(arr[2]), Integer.valueOf(arr[1])))) {

					} else {
						System.out.println("C Invalid output");
						break;
					}

				}
			}
			turn = turn * -1;
		}
		System.out.println(board.countBlack());
		keyboard.close();
	}
}
