package com.markchess.model.board;

import android.graphics.Color;

public class Board {
	public final static int boardsize = 8;
	public final static int darkSquare = Color.BLACK;
	public final static int lightSquare = Color.WHITE;
	public final static int blackAdvancedRow = 0;
	public final static int blackPawnRow = 1;
	public final static int blackPawnEndRow = 7;
	public final static int whiteAdvancedRow = 7;
	public final static int whitePawnRow = 6;
	public final static int whitePawnEndRow = 0;
 
	private Square[][] board;
	
	private King whiteKing;
	private King blackKing;
	
	public Board() {
		this.board = new Square[boardsize][boardsize];
		setup();
	}

	private void setup() {
		for(int col = 0; col < boardsize; col++) {
			for(int row = 0; row < boardsize; row++) {
				board[row][col] = new Square(row, BoardCol.values()[col], ((col % 2) == (row % 2)) ? darkSquare : lightSquare);
			}
		}
		
		for(int col = 0; col < boardsize; col++) {
			board[blackPawnRow][col].setPiece(new Pawn(blackPawnRow, BoardCol.values()[col], Turn.BLACK));			
			board[whitePawnRow][col].setPiece(new Pawn(whitePawnRow, BoardCol.values()[col], Turn.WHITE));
			
			switch(col) {
				case 0:
				case 7:
					board[blackAdvancedRow][col].setPiece(new Rook(blackAdvancedRow, BoardCol.values()[col], Turn.BLACK));
					board[whiteAdvancedRow][col].setPiece(new Rook(whiteAdvancedRow, BoardCol.values()[col], Turn.WHITE));
					break;
				case 1:
				case 6:
					board[blackAdvancedRow][col].setPiece(new Knight(blackAdvancedRow, BoardCol.values()[col], Turn.BLACK));
					board[whiteAdvancedRow][col].setPiece(new Knight(whiteAdvancedRow, BoardCol.values()[col], Turn.WHITE));
					break;
				case 2:
				case 5:
					board[blackAdvancedRow][col].setPiece(new Bishop(blackAdvancedRow, BoardCol.values()[col], Turn.BLACK));
					board[whiteAdvancedRow][col].setPiece(new Bishop(whiteAdvancedRow, BoardCol.values()[col], Turn.WHITE));
					break;
			}
		}
		
		this.blackKing = new King(blackAdvancedRow, BoardCol.values()[3], Turn.BLACK);
		this.whiteKing = new King(whiteAdvancedRow, BoardCol.values()[3], Turn.WHITE);
		board[blackAdvancedRow][3].setPiece(this.blackKing);
		board[whiteAdvancedRow][3].setPiece(this.whiteKing);
		
		board[blackAdvancedRow][4].setPiece(new Queen(blackAdvancedRow, BoardCol.values()[4], Turn.BLACK));
		board[whiteAdvancedRow][4].setPiece(new Queen(whiteAdvancedRow, BoardCol.values()[4], Turn.WHITE));
	}
	
	public King getKing(Turn turn) {
		if(turn == Turn.WHITE) {
			return this.whiteKing;
		} else {
			return this.blackKing;			
		}
	}
	
	public Square getSquare(int row, BoardCol col) {
		return board[row][col.ordinal()];
	}
	
	/*
	 * TEST METHODS
	 */
	public void reset() {
		this.board = new Square[boardsize][boardsize];
		setup();
	}
	
	public String printBoard() {
		String stringBoard = "";
		for (int i = 0; i < Board.boardsize; ++i) {
			stringBoard += (i + 1) + "| ";
			for (int j = 0; j < Board.boardsize; ++j) {
				Square curr = this.getSquare(i, BoardCol.values()[j]);

				if (curr.getPiece() == null) {
					if (curr.getColor() == Board.darkSquare) {
						stringBoard += "xx ";
					} else {
						stringBoard += "   ";
					}
				} else {
					String piece = curr.getPiece().getSide().toString();
					try {
						if (curr.getPiece().getClass().getSimpleName().equals("Pawn")) {
							piece += "P";
						} else if (curr.getPiece().getClass().getSimpleName().equals("Rook")) {
							piece += "R";
						} else if (curr.getPiece().getClass().getSimpleName().equals("Knight")) {
							piece += "N";
						} else if (curr.getPiece().getClass().getSimpleName().equals("Bishop")) {
							piece += "B";
						} else if (curr.getPiece().getClass().getSimpleName().equals("Queen")) {
							piece += "Q";
						} else if (curr.getPiece().getClass().getSimpleName().equals("King")) {
							piece += "K";
						} 
					} catch (Exception e) {
						piece = "ER ";
					}
					stringBoard += piece + " ";
				}
			}
			stringBoard += "\n";
		}
		stringBoard += "=|=========================\n";
		stringBoard += " |  a  b  c  d  e  f  g  h\n";
		return stringBoard;
	}
}
