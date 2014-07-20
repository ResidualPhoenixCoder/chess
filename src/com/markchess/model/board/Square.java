package com.markchess.model.board;

import java.io.Serializable;

public class Square implements Serializable {
    private static final long serialVersionUID = -90811719167427601L;
    private Piece mPiece;

    private final int mRowPos;
    private final BoardCol mColPos;

    private int mColor;

    public Square(final Piece piece, final int rowPos, final BoardCol colPos, final int color) {
        this(rowPos, colPos, color);
        setPiece(piece);
    }

    public Square(final int rowPos, final BoardCol colPos, final int color) {
        mRowPos = rowPos;
        mColPos = colPos;
        mColor = color;
        mPiece = null;
    }

    public Piece getPiece() {
        return mPiece;
    }

    public void setPiece(final Piece piece) {
        mPiece = piece;
    }

    public BoardCol getColumnPosition() {
        return mColPos;
    }

    public int getRowPosition() {
        return mRowPos;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(final int color) {
        mColor = color;
    }

    public boolean hasPiece() {
        return mPiece != null;
    }

    @Override
    public String toString() {
        return mColPos.toString() + mRowPos;
    }
}
