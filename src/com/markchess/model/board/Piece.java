package com.markchess.model.board;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    private static final long serialVersionUID = -7904843358368004067L;
    protected int mCurrRow;
    protected int mPrevRow;

    protected BoardCol mCurrCol;
    protected BoardCol mPrevCol;
    protected Turn mSide;

    public Piece(final int currRow, final BoardCol currCol, final Turn side) {
        mCurrRow = currRow;
        mPrevRow = -1;

        mCurrCol = currCol;
        mPrevCol = null;

        mSide = side;
    }

    public int getCurrentRow() {
        return mCurrRow;
    }

    public void setCurrentRow(final int row) {
        mCurrRow = row;
    }

    public int getPreviousRow() {
        return mPrevRow;
    }

    public void setPreviousRow(final int row) {
        mPrevRow = row;
    }

    public BoardCol getCurrentCol() {
        return mCurrCol;
    }

    public void setCurrentCol(final BoardCol col) {
        mCurrCol = col;
    }

    public BoardCol getPreviousCol() {
        return mPrevCol;
    }

    public void setPreviousCol(final BoardCol col) {
        mPrevCol = col;
    }

    public Turn getSide() {
        return mSide;
    }

    //TODO If this were implemented here instead of the classes that extend this class, would doing a generic call to "this" refer to the Piece class or the extending class.
    //	public abstract Class<?> getPieceType();

    public abstract AMove getMove(final Board b, final int row, final BoardCol col, final Turn turn);
}
