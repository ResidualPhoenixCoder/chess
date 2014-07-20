package com.markchess.model.board;

class Knight extends Piece {
	private static final long serialVersionUID = -4178990857225713306L;

	public Knight(int currRow, BoardCol currCol, Turn side) {
		super(currRow, currCol, side);
	}
	
	public Class<?> getPieceType() {
		return this.getClass();
	}

	@Override
	public AMove getMove(Board b, int row, BoardCol col, Turn turn) {
		return new KnightMove(b, this.mCurrRow, this.mCurrCol, row, col, turn);
	}

	private class KnightMove extends AMove {

		public KnightMove(Board b, int srcRow, BoardCol srcCol, int destRow,
				BoardCol destCol, Turn turn) {
			super(b, srcRow, srcCol, destRow, destCol, turn);
		}

		@Override
		protected boolean validateRegularMoves() {
			return false;
		}
		
		@Override
		protected boolean validateSpecialMoves() {
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
