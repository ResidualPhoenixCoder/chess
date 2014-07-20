package com.markchess.model.board;

class Rook extends Piece {

	public Rook(int currRow, BoardCol currCol, Turn side) {
		super(currRow, currCol, side);
	}
	
	public Class<?> getPieceType() {
		return this.getClass();
	}

	@Override
	public AMove getMove(Board b, int row, BoardCol col, Turn turn) {
		return new RookMove(b, this.mCurrRow, this.mCurrCol, row, col, turn);
	}

	private class RookMove extends AMove {
		public RookMove(Board b, int srcRow, BoardCol srcCol, int destRow,
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
			//No implementation.
			throw new UnsupportedOperationException();
		}

		@Override
		protected void undoSpecialMove() {
			//No implementation.
			throw new UnsupportedOperationException();
		}
	}
}
