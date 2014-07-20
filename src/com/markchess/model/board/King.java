package com.markchess.model.board;

class King extends Piece {

	public King(int srcRow, BoardCol srcCol, Turn side) {
		super(srcRow, srcCol, side);
	}

	public boolean isCheck() {
		boolean result = false;
		//TODO Calculate whether the King is in check.
		return result;
	}
	
	public boolean isCheckMate() {
		boolean result = false;
		//TODO Calculate whether the King is in checkmate.
		return result;
	}
	
	public boolean isStaleMate() {
		return false;
	}
	
	public Class<?> getPieceType() {
		return this.getClass();
	}

	@Override
	public AMove getMove(Board b, int row, BoardCol col, Turn turn) {
		return new KingMove(b, this.mCurrRow, this.mCurrCol, row, col, turn);
	}
	
	private class KingMove extends AMove {
		public KingMove(Board b, int srcRow, BoardCol srcCol, int destRow,
				BoardCol destCol, Turn turn) {
			super(b, srcRow, srcCol, destRow, destCol, turn);
		}

		@Override
		public boolean validateRegularMoves() {
			return false;
		}
		
		@Override
		public boolean validateSpecialMoves() {
			return false;
		}

		@Override
		protected boolean isSpecialMove() {
			return false;
		}

		@Override
		protected void executeSpecialMove() {
		}

		@Override
		protected void undoSpecialMove() {
		}
	}
}
