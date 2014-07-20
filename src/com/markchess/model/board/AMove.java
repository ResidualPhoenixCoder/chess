package com.markchess.model.board;

/**
 * @author Mark Labrador
 * 
 */
public abstract class AMove {
    protected Board mBoard;

    protected int mSrcRow;
    protected int mPrevSrcRow;
    protected int mDestRow;
    protected int mPrevDestRow;

    /*COLUMNS*/
    protected BoardCol mSrcCol;
    protected BoardCol mPrevSrcCol;
    protected BoardCol mDestCol;
    protected BoardCol mPrevDestCol;

    protected Piece mSrcPiece;
    protected Piece mDestPiece;

    protected boolean mIsValid;
    protected boolean mSpecialMove;

    public AMove(final Board board, final int srcRow, final BoardCol srcCol, final int destRow, final BoardCol destCol, final Turn turn) {
        mBoard = board;
        mSrcRow = srcRow;
        mDestRow = destRow;

        mSrcCol = srcCol;
        mDestCol = destCol;

        mSrcPiece = board.getSquare(srcRow, srcCol).getPiece();
        mDestPiece = board.getSquare(destRow, destCol).getPiece();

        //Deciding whether the validation runs depends on whose turn it is.
        if(this.mSrcPiece != null && mSrcPiece.getSide() == turn) {
            mSpecialMove = validateSpecialMoves();
            mIsValid = validateRegularMoves() || mSpecialMove;
        } else {
            mSpecialMove = mIsValid = false;
        }
    }

    public boolean isValid() {
        return mIsValid;
    }

    public boolean isChecked() {
        return mBoard.getKing(mSrcPiece.getSide()).isCheck();
    }

    /**
     * Calculates whether the move is valid.
     */
    protected abstract boolean validateRegularMoves();

    /*
     * SPECIAL MOVE METHODS
     * 
     * The move is special, if it requires a different set of actions than
     * moving to the destination square e. g. pawn promotion.
     */
    protected boolean isSpecialMove() {
        return mSpecialMove;
    }

    protected abstract boolean validateSpecialMoves();

    protected abstract void executeSpecialMove();

    protected abstract void undoSpecialMove();

    public void execute() {
        if (isSpecialMove()) {
            executeSpecialMove();
        } else {
            mBoard.getSquare(mSrcRow, mSrcCol).setPiece(null);
            mBoard.getSquare(mDestRow, mDestCol)
            .setPiece(mSrcPiece);

            //Save the previous location information.
            mPrevSrcRow = mSrcPiece.getPreviousRow();
            mPrevSrcCol = mSrcPiece.getPreviousCol();
            mSrcPiece.setPreviousRow(mSrcRow);
            mSrcPiece.setPreviousCol(mSrcCol);
            mSrcPiece.setCurrentRow(mDestRow);
            mSrcPiece.setCurrentCol(mDestCol);

            /*
             * Indicate that a piece has moved off the board by setting its
             * current row and current column to -1 and null, respectively.
             */
            if (mDestPiece != null) {
                mPrevDestRow = mDestPiece.getPreviousRow();
                mPrevDestCol = mDestPiece.getPreviousCol();
                mDestPiece.setPreviousRow(mDestRow);
                mDestPiece.setPreviousCol(mDestCol);
                mDestPiece.setCurrentRow(-1);
                mDestPiece.setCurrentCol(null);
            }
        }
    }

    public void undo() {
        if (isSpecialMove()) {
            undoSpecialMove();
        } else {
            mBoard.getSquare(mSrcRow, mSrcCol).setPiece(mSrcPiece);
            mBoard.getSquare(mDestRow, mDestCol).setPiece(mDestPiece);

            //Restore the source piece's previous state.
            mSrcPiece.setPreviousRow(mPrevSrcRow);
            mSrcPiece.setPreviousCol(mPrevSrcCol);
            mSrcPiece.setCurrentRow(mSrcRow);
            mSrcPiece.setCurrentCol(mSrcCol);

            //If there was a piece at the destination, restore the destination piece's previous state.
            if (mDestPiece != null) {
                mDestPiece.setPreviousRow(mPrevDestRow);
                mDestPiece.setPreviousCol(mPrevDestCol);
                mDestPiece.setCurrentRow(mDestRow);
                mDestPiece.setCurrentCol(mDestCol);
            }
        }
    }

    /* UTILITIES */
    /* package */ void setIsValid(final boolean isValid) {
        mIsValid = isValid;
    }
}
