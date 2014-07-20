package com.markchess.model.board;

class Pawn extends Piece {
    private static final long serialVersionUID = 7182852480681941248L;

    public Pawn(final int srcRow, final BoardCol srcCol, final Turn side) {
        super(srcRow, srcCol, side);
    }

    public Class<?> getPieceType() {
        return getClass();
    }

    @Override
    public AMove getMove(final Board b, final int destRow, final BoardCol destCol, final Turn turn) {
        return new PawnMove(b, mCurrRow, mCurrCol, destRow, destCol, turn);
    }

    public boolean doubleStepped() {
        return Math.abs(mCurrRow - mPrevRow) == 2 && mCurrCol == mPrevCol;
    }

    /* package */ class PawnMove extends AMove {
        private boolean mSingleStep = false;
        private boolean mAttack = false;
        private boolean mEnPassant = false;
        private boolean mDoubleStep = false;
        private boolean mPawnPromotion = false;
        private Class<?> mPromotionType = Queen.class;

        private final int mStartPos = (mSide == Turn.WHITE) ? Board.whitePawnRow
                : Board.blackPawnRow;
        private final int mEndPos = (mSide == Turn.WHITE) ? Board.whitePawnEndRow
                : Board.blackPawnEndRow;

        public PawnMove(final Board b, final int srcRow, final BoardCol srcCol, final int destRow, final BoardCol destCol, final Turn turn, final Class<?> promotionType) {
            this(b, srcRow, srcCol, destRow, destCol, turn);

            if(promotionType == Rook.class || promotionType == Knight.class || promotionType == Bishop.class) {
                mPromotionType = promotionType;
            } else {
                mPromotionType = Queen.class;
            }
        }

        public PawnMove(final Board b, final int srcRow, final BoardCol srcCol, final int destRow,
                final BoardCol destCol, final Turn turn) {
            super(b, srcRow, srcCol, destRow, destCol, turn);
        }

        @Override
        protected boolean validateRegularMoves() {
            //TODO Validate that there is no piece occupying a space between the source and destination.
            //Do this under each behavior as it is dependent on the behavior that is occurring.  For instance,
            //single-step will only need to check the square in front of it.

            /*
             * This needs to verify that all moves are valid including special
             * moves.
             */
            boolean valid = false;

            //Single-Step
            if(mSrcCol == mDestCol && Math.abs(mDestRow - mSrcRow) == 1) {
                mSingleStep = mBoard.getSquare(mDestRow, mDestCol).getPiece() == null;
            }

            //Double-Step
            mDoubleStep = (mDestCol.ordinal() == mSrcCol.ordinal()) && mCurrRow == mSrcRow
                    && Math.abs(mDestRow - mSrcRow) == 2;

            //Attack
            if(Math.abs(mDestCol.ordinal() - mSrcCol.ordinal()) == 1 && mDestRow - mSrcRow == 1) {
                mAttack = mBoard.getSquare(mDestRow, mDestCol).getPiece() != null && ((mDestPiece != null) ? mDestPiece.getSide() != mSrcPiece.getSide() : true);
            }

            //En Passant
            if(Math.abs(mDestCol.ordinal() - mSrcCol.ordinal()) == 1 && (mDestRow - mSrcRow == 1)) {
                final Piece sidePiece = mBoard.getSquare(mSrcRow, mDestCol).getPiece();
                if(sidePiece != null && sidePiece instanceof Pawn) {
                    mEnPassant = ((Pawn)sidePiece).doubleStepped();
                }
            }

            valid = mSingleStep || mDoubleStep || mAttack || mEnPassant;

            return valid;
        }

        @Override
        protected boolean validateSpecialMoves() {
            //Promotion
            mPawnPromotion = (mDestRow == mEndPos);
            return mPawnPromotion;
        }

        @Override
        protected void executeSpecialMove() {
            if(mPawnPromotion) {
                //Need to know what piece we're promoting to.
                Piece promotion = null;
                try {
                    promotion = (Piece) mPromotionType.getConstructor(Board.class, int.class, BoardCol.class, Turn.class).newInstance(this.mBoard, this.mDestRow, this.mDestCol, this.mSrcPiece.getSide());
                } catch (final Exception e) {
                    promotion = new Queen(mDestRow, mDestCol, mSrcPiece.getSide());
                }
                mBoard.getSquare(mDestRow, mDestCol).setPiece(promotion);

                //Preserve state of the pawn and destination piece here.
                mSrcPiece.setPreviousRow(mSrcRow);
                mSrcPiece.setPreviousCol(mSrcCol);
                mSrcPiece.setCurrentRow(-1);
                mSrcPiece.setCurrentCol(null);

                if(mDestPiece != null) {
                    mPrevDestCol = mDestPiece.getPreviousCol();
                    mPrevDestRow = mDestPiece.getPreviousRow();
                    mDestPiece.setPreviousRow(mDestRow);
                    mDestPiece.setPreviousCol(mDestCol);
                    mDestPiece.setCurrentRow(-1);
                    mDestPiece.setCurrentCol(null);
                }
            } else {
                execute();
            }
        }

        @Override
        protected void undoSpecialMove() {
            if(mPawnPromotion) {
                /*
                 * Do you need anything special to undo the promotion move?  No, all promotions go
                 * back to being a pawn in the reverse direction.
                 */

                //Restore state of the pawn and destination piece here.
                mBoard.getSquare(mDestRow, mDestCol).setPiece(mDestPiece);
                mBoard.getSquare(mSrcRow, mSrcCol).setPiece(mSrcPiece);

                mSrcPiece.setCurrentRow(mSrcPiece.getPreviousRow());
                mSrcPiece.setCurrentCol(mSrcPiece.getPreviousCol());
                mSrcPiece.setPreviousRow(mPrevDestRow);
                mSrcPiece.setPreviousCol(mPrevDestCol);

                //Restore destination piece state.
                if(mDestPiece != null) {
                    mDestPiece.setCurrentRow(mDestPiece.getPreviousRow());
                    mDestPiece.setCurrentCol(mDestPiece.getPreviousCol());
                    mDestPiece.setPreviousRow(mPrevDestRow);
                    mDestPiece.setPreviousCol(mPrevDestCol);
                }
            } else {
                undo();
            }
        }
    }
}
