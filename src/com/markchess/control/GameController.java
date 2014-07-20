package com.markchess.control;

import com.markchess.model.board.AMove;
import com.markchess.model.board.Board;
import com.markchess.model.board.BoardCol;
import com.markchess.model.board.MoveManager;
import com.markchess.model.board.Square;
import com.markchess.model.board.Turn;

public class GameController {
	private Board b;
	private MoveManager manager;
	
	public GameController(Board b) {
		this.b = b;
		this.manager = new MoveManager();
		//TODO Register error event with the MoveManager.
	}
	
	public void move(int srcRow, BoardCol srcCol, int destRow, BoardCol destCol) {
		Square source = b.getSquare(srcRow, srcCol);
		if(source.getPiece() != null) {
			AMove move = source.getPiece().getMove(this.b, destRow, destCol, source.getPiece().getSide());
			manager.executeMove(move);
			/*
			 * If an error occurs in the move, the game controller should have registered an event with the
			 * manager to handle it. 
			 *  
			 * TODO This means the manager needs to provide a method for registering a custom listener 
			 * it will trigger. 
			 */
		}
	}
	
	public void undo() {
		if(manager.isUndoAvailable()) {
			manager.undo();
		}
	}
}
