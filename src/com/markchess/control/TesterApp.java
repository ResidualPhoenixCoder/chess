package com.markchess.control;

import java.util.Scanner;

import com.markchess.model.board.Board;
import com.markchess.model.board.BoardCol;

public class TesterApp {
	private static final String delim = " ";
	private static final String[] keywords = { "resign", "draw", "y", "n", "q",
			"r", "b", "n" };
	private Board testBoard;
	private GameController control;

	public static void main(String[] args) {
		Board testBoard = new Board();
		GameController control = new GameController(testBoard);
		TesterApp app = new TesterApp(testBoard, control);
		app.run();
	}

	public TesterApp(Board testBoard, GameController control) {
		this.testBoard = testBoard;
		this.control = control;
	}

	public void run() {
		System.out.println(this.testBoard.printBoard());
		Scanner sc = new Scanner(System.in);
		String input = "";
		while (!input.equals("resign")) {
			/*
			 * TODO Pawn promotion move. The pawn promotion should only be
			 * registered in the moves list, if it is valid. 
			 * TODO Draw offer.
			 * TODO Draw accept. 
			 * TODO Regular moves.
			 */
			System.out.println("Enter move:");
			input = sc.nextLine();
			if (input.equals("resign"))
				break;

			String[] move = input.split(TesterApp.delim);
			Position srcPos = null;
			Position destPos = null;
			if (move.length == 3) {
				// TODO After a draw is offered, the next player must be offered
				// a draw.
				if (validPosition(move[0]) && validPosition(move[1])
						&& validKeyword(move[2])) {
					srcPos = parsePosition(move[0]);
					destPos = parsePosition(move[1]);
					control.move(srcPos.getRow(), srcPos.getCol(),
							destPos.getRow(), destPos.getCol());
				} else {
					invalidMove();
				}
			} else if (move.length == 2) {
				if (validPosition(move[0]) && validPosition(move[1])) {
					srcPos = parsePosition(move[0]);
					destPos = parsePosition(move[1]);
					control.move(srcPos.getRow(), srcPos.getCol(),
							destPos.getRow(), destPos.getCol());
				} else {
					invalidMove();
				}
			} else {
				invalidMove();
			}
			System.out.println(this.testBoard.printBoard());
		}
	}

	private void invalidMove() {
		System.out.println("Invalid move.  Please try again.");
	}

	private boolean validPosition(String input) {
		return input.matches("^[a-hA-H][1-8]$");
	}

	private Position parsePosition(String input) {
		int row = Integer.parseInt(input.substring(1, 2)) - 1;
		BoardCol col = BoardCol.valueOf(input.substring(0, 1));
		Position pos = new Position(row, col);
		return pos;
	}

	private boolean validKeyword(String input) {
		for (int i = 0; i < keywords.length; i++) {
			if (keywords[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	private class Position {
		private int row;
		private BoardCol col;

		public Position(int row, BoardCol col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public BoardCol getCol() {
			return this.col;
		}
	}
}
