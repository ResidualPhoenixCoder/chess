package com.markchess.model.board;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.markchess.control.GameController;

public class PawnTest extends AndroidTestCase {
    @Mock private Board testBoard;
    @Mock private GameController testControl;
    @Mock private BoardCol testBoardCol;
    @Mock private Turn testTurn;
    @Mock private Square testSquare;
    @Mock private Pawn.PawnMove testMove;

    private Pawn testPawn;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        testPawn = new Pawn(Mockito.anyInt(), testBoardCol, testTurn);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    private void testGetPieceType() {
        assertSame("Classes must be the same.", Pawn.class, testPawn.getClass());
    }

    @SmallTest
    private void testGetMove() {
        fail("Pawn test getMove not implemented yet.");
    }

    @SmallTest
    private void testDoubleStepped() {

    }

    private void pawnStepAndCheck(final int step) {
        this.testBoard.reset();
        final int blackPawnRow = Board.blackPawnRow;
        final int whitePawnRow = Board.whitePawnRow;

        final String nullErrorMsg = "Null square for ";
        final String sameErrorMsg = "Pawn not the same for ";

        for(int i = 0; i < BoardCol.values().length; i++) {
            final Piece prevWhitePawn = this.testBoard.getSquare(whitePawnRow, BoardCol.values()[i]).getPiece();
            assertNotNull(nullErrorMsg + " White @ " + BoardCol.values()[i] + whitePawnRow, prevWhitePawn);

            this.testControl.move(whitePawnRow, BoardCol.values()[i], whitePawnRow - step, BoardCol.values()[i]);
            assertSame(sameErrorMsg + " White @ " + BoardCol.values()[i] + (whitePawnRow - step), testBoard.getSquare(whitePawnRow - step, BoardCol.values()[i]).getPiece(), prevWhitePawn);

            final Piece prevBlackPawn = this.testBoard.getSquare(blackPawnRow, BoardCol.values()[i]).getPiece();
            assertNotNull(nullErrorMsg + " Black @ " + BoardCol.values()[i] + blackPawnRow, prevBlackPawn);

            this.testControl.move(blackPawnRow, BoardCol.values()[i], blackPawnRow + step, BoardCol.values()[i]);
            assertSame(sameErrorMsg + " Black @ " + BoardCol.values()[i] + (blackPawnRow + step), testBoard.getSquare(blackPawnRow + step, BoardCol.values()[i]).getPiece(), prevBlackPawn);
        }
    }
}
