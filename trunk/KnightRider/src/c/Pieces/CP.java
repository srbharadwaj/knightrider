package c.pieces;


import c.Constants.CConst;
import c.main.CB;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  81 82 83 84 85 86 87 88
 *  71 72 73 74 75 76 77 78
 *  61 62 63 64 65 66 67 68
 *  51 52 53 54 55 56 57 58
 *  41 42 43 44 45 46 47 48
 *  31 32 33 34 35 36 37 38
 *  21 22 23 24 25 26 27 28
 *  11 12 13 14 15 16 17 18
 */
/**
 * pos/10 = 1 to 8
 * pos%10 = A to H
 */
/**
 *  A8 B8 C8 D8 E8 F8 G8 H8
 *  A7 B7 C7 D7 E7 F7 G7 H7
 *  A6 B6 C6 D6 E6 F6 G6 H6
 *  A5 B5 C5 D5 E5 F5 G5 H5
 *  A4 B4 C4 D4 E4 F4 G4 H4
 *  A3 B3 C3 D3 E3 F3 G3 H3
 *  A2 B2 C2 D2 E2 F2 G2 H2
 *  A1 B1 C1 D1 E1 F1 G1 H1
 */


/**
 *
 * @author suhas
 */
public class CP implements CConst
{
    public final CB cbObj;
    public final String pieceName;
    public final String pieceColor;
    public final int initialPosition;
    public int currentPosition;
    public Vector movesDone = null;
    public Vector movesPossible = null;
    public boolean captured = false;
    //public Vector possibleCapturePositionForPawn = new Vector();

    public int b_currentPosition;
    public Vector b_movesPossible = null;

    public CP(CB o,String pName,String pColor,int iniPos)
    {
        cbObj=o;
        pieceName = pName;
        pieceColor = pColor;
        initialPosition = iniPos;
        setCurrentPosition(iniPos);
        setCapturedFlag(false);

   
        movesDone = new Vector();
        movesPossible = new Vector();

        b_movesPossible = new Vector();
        b_currentPosition = 0;

        //pawn
        if(pName.equals(PAWN))
        {
            calculatePossibleMovesForPawn();
        }

        //rook
        if(pName.equals(ROOK))
        {
            //nothing
        }
        //knight
        if(pName.equals(KNIGHT))
        {
            calculatePossibleMovesForKnight();
        }
        //BISHOP
        if(pName.equals(BISHOP))
        {
            //nothing
        }
        //QUEEN
        if(pName.equals(QUEEN))
        {
            //nothing
        }
        //KING
        if(pName.equals(KING))
        {
            //nothing
        }
    }

    public String getPieceName()
    {
        return this.pieceName;
    }

    public String getPieceColor()
    {
        return this.pieceColor;
    }

    public int getInitialPosition()
    {
        return this.initialPosition;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition()
    {
        return this.currentPosition;
    }

    public void setCapturedFlag(boolean b)
    {
        this.captured = b;
    }

    public boolean isCaptured()
    {
        return this.captured;
    }

    public void backupCurrentPosition()
    {
        this.b_currentPosition = this.currentPosition;
    }

    public void restoreCurrentPosition()
    {
        this.currentPosition = this.b_currentPosition;
    }

    public void backupPossibleMoves()
    {
        this.b_movesPossible.clear();
        for(int i=0;i<this.movesPossible.size();i++)
        {
            this.b_movesPossible.add(this.movesPossible.get(i));
        }
    }

    public void restorePossibleMoves()
    {
        this.movesPossible.clear();
        for(int i=0;i<this.b_movesPossible.size();i++)
        {
            this.movesPossible.add(this.b_movesPossible.get(i));
        }
    }

    //pawn
    public void calculatePossibleMovesForPawn()
    {
         movesPossible.clear();

         int newpos;

         //moves vertically so add 10 for white piece
         if(getPieceColor().equals(WHITE))
         {
            newpos = getCurrentPosition() + 10;
         }
         else
         {
             newpos = getCurrentPosition() - 10;
         }

         boolean isAdded = chkPositionValidityAndAddThePositionForPawn(newpos,false);

         //if its still in initial pos then it can move 2 steps fwd
         if(this.getInitialPosition() == this.getCurrentPosition())
         {
             //to move 2 steps fwd u should also be able to one step fwd
             if(isAdded)
             {
                 //moves vertically so add 20 for white piece
                 if(getPieceColor().equals(WHITE))
                 {
                    newpos = getCurrentPosition() + 20;
                 }
                 else
                 {
                     newpos = getCurrentPosition() - 20;
                 }

                 chkPositionValidityAndAddThePositionForPawn(newpos,false);
             }
         }


         //capture diagonaly
         if(getPieceColor().equals(WHITE))
         {
            newpos = getCurrentPosition() + 11;
            chkPositionValidityAndAddThePositionForPawn(newpos,true);
            newpos = getCurrentPosition() + 9;
            chkPositionValidityAndAddThePositionForPawn(newpos,true);
         }
         else
         {
             newpos = getCurrentPosition() - 11;
             chkPositionValidityAndAddThePositionForPawn(newpos,true);
             newpos = getCurrentPosition() - 9;
             chkPositionValidityAndAddThePositionForPawn(newpos,true);
         }


         //enpassant
         chkPositionValidityAndAddThePositionForPawn(getEnPassentValue(),false);

    }

    //rook
    public void calculatePossibleMovesForRook()
    {
         movesPossible.clear();

         //Move right
         chkPositionValidityAndAddThePosition(1);

         //Move left
         chkPositionValidityAndAddThePosition(-1);

         //Move top/north
         chkPositionValidityAndAddThePosition(10);

         //Move top/south
         chkPositionValidityAndAddThePosition(-10);

    }

    //knight
    public void calculatePossibleMovesForKnight()
    {
        movesPossible.clear();

        int newpos;

        newpos =  getCurrentPosition() + 8;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() + 12;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() + 19;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() + 21;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() - 8;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() - 12;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() - 19;
        chkPositionValidityAndAddThePositionForKnight(newpos);
        newpos =  getCurrentPosition() - 21;
        chkPositionValidityAndAddThePositionForKnight(newpos);
    }

    //bishop
     public void calculatePossibleMovesForBishop()
    {
        movesPossible.clear();

         //Move top right
         chkPositionValidityAndAddThePosition(11);

         //Move bottom left
         chkPositionValidityAndAddThePosition(-11);

         //Move top left
         chkPositionValidityAndAddThePosition(9);

         //Move bottom right
         chkPositionValidityAndAddThePosition(-9);

    }

     //queen
     public void calculatePossibleMovesForQueen()
     {
        //Queen is a combination of rook and bishop
        //So just copy paste the code block of rook and bishop

        movesPossible.clear();

         //Move right
         chkPositionValidityAndAddThePosition(1);

         //Move left
         chkPositionValidityAndAddThePosition(-1);

         //Move top/north
         chkPositionValidityAndAddThePosition(10);

         //Move top/south
         chkPositionValidityAndAddThePosition(-10);

         //Move top right
         chkPositionValidityAndAddThePosition(11);

         //Move bottom left
         chkPositionValidityAndAddThePosition(-11);

         //Move top left
         chkPositionValidityAndAddThePosition(9);

         //Move bottom right
         chkPositionValidityAndAddThePosition(-9);

    }

    //king
    public void calculatePossibleMovesForKing()
    {
         movesPossible.clear();

        int newpos;

        newpos =  getCurrentPosition() + 9;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() + 10;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() + 11;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() - 9;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() - 10;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() - 11;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() + 1;
        chkPositionValidityAndAddThePositionForKing(newpos);
        newpos =  getCurrentPosition() - 1;
        chkPositionValidityAndAddThePositionForKing(newpos);
    }

     public boolean chkPositionValidityAndAddThePositionForPawn(int newpos,boolean capPos)
     {
         if(isValidPosition(newpos))
         {
             //staright fwd one step or 2 steps
             if(!capPos)
             {
                 if(isAnyPiecePresent(newpos))
                 {
                    // dont add the position
                    // coz some piece is already present at that position
                     return false;
                 }
                 else
                 {
                    // add the pos
                    // coz that position is not occupied by any piece
                    movesPossible.add(newpos);
                    return true;
                 }
             }
             //for capturing pieces
             else
             {
                  if(isOppositeColorPiecePresent(newpos,getPieceColor()))
                  {
                     // add the position
                     // coz different color piece is present and cn be captured
                     movesPossible.add(newpos);
                     return true;
                  }
                  else
                  {
                      // dont add the position
                      return false;
                  }

             }
         }
         else
         {
             return false;
         }

     }

     public void chkPositionValidityAndAddThePositionForKnight(int newpos)
     {
        if( isValidPosition(newpos))
        {
            if( isSameColorPiecePresent(newpos, getPieceColor()))
            {
                // dont add the position
                // coz same color piece is present
            }
            else
            {
                // add the pos
                // coz an opposite color piece is present or the pos is empty
                movesPossible.add(newpos);
            }
        }
    }

     public void chkPositionValidityAndAddThePosition(int moveByVal)
     {
        int newpos;
        newpos = getCurrentPosition() + moveByVal;
         while(true)
         {
             //System.out.println("adding"+newpos);
             if(isValidPosition(newpos))
             {
                 if(isOppositeColorPiecePresent(newpos,getPieceColor()))
                 {
                     // add the position
                     // coz different color piece is present and cant go furthur
                     movesPossible.add(newpos);
                     break;
                 }
                 if(isSameColorPiecePresent(newpos,getPieceColor()))
                 {
                     // dont add the position
                     // coz same color piece is present and cant go furthur
                     break;
                 }
                 else
                 {
                     // add the pos
                     // coz that pos is empty
                     movesPossible.add(newpos);
                 }
             }
             else
             {
                 break;
             }
             newpos = newpos+moveByVal;
         }
    }

     public void chkPositionValidityAndAddThePositionForKing(int newpos)
     {
        if( isValidPosition(newpos))
        {
            if( isSameColorPiecePresent(newpos, getPieceColor()))
            {
                // dont add the position
                // coz same color piece is present
            }
            else
            {
                // add the pos
                // coz an opposite color piece is present or the pos is empty
                movesPossible.add(newpos);
            }
        }
    }

     public boolean isValidPosition(int pos)
     {
        /**
         * The position of board is as shown at the starting of the comments
         * in this file, that is the numbers 11,12,...88 are actual integers
         * which represents each of the cells of a chessboard
         * In this case we do a modulus of pos with 10 then the value(reminder)
         * must be within 1 to 8, anything else then its out of position or board
         */

        if((pos < 11) || (pos > 88))
            return false;

        int i = pos%10;
        if((i == 1)|| (i == 2)|| (i == 3)|| (i == 4)|| (i == 5)|| (i == 6)|| (i == 7)|| (i == 8))
            return true;
        else
            return false;
    }

     public boolean isSameColorPiecePresent(int pos, String color)
     {
        for(int i=0;i<cbObj.allPieces.size();i++)
        {
            CP c = (CP) cbObj.allPieces.get(i);
            if(c.getCurrentPosition() == pos)
            {
                if(c.getPieceColor().equals(color))
                {
                    return true;
                }
            }
        }
        return false;
    }

     public boolean isOppositeColorPiecePresent(int pos, String color)
     {
        for(int i=0;i<cbObj.allPieces.size();i++)
        {
            CP c = (CP) cbObj.allPieces.get(i);
            if(c.getCurrentPosition() == pos)
            {
                if(!(c.getPieceColor().equals(color)))
                {
                    return true;
                }
            }
        }
        return false;
    }

     public boolean isAnyPiecePresent(int pos)
     {
        for(int i=0;i<cbObj.allPieces.size();i++)
        {
            CP c = (CP) cbObj.allPieces.get(i);
            if(c.getCurrentPosition() == pos)
            {
                return true;
            }
        }
        return false;
    }

     public boolean isMovePossible(int p)
     {
        for(int i=0;i<movesPossible.size();i++)
        {
            //System.out.println(movesPossible.get(i));
            if(p == movesPossible.get(i))
                return true;
        }
        return false;
    }

     public void printPossibleMoves()
     {
        for(int i=0;i<movesPossible.size();i++)
        {
            System.out.println(movesPossible.get(i));
        }
    }

     public int getEnPassentValue()
     {
        //enpassent
        //if white pawn then cur_pos must be >=51 and <=58
        //if black pawn then cur_pos must be >=41 and <=48

        //the pawn's who will be captured cur_pos+1 or -1 should have a opp color pawn and movesdone==1
        // and tht should be the latest move

        int curPos = getCurrentPosition();
        String pCol = getPieceColor();

        if((pCol.equals(WHITE)) && (curPos>=51) && (curPos<=58))
        {
            for(int j=0;j<cbObj.allPieces.size();j++)
            {
                CP p = (CP) cbObj.allPieces.get(j);
                if((p.getPieceName().equals(PAWN)) &&
                   (p.getPieceColor().equals(BLACK)) &&
                   (p.movesDone.size()==1))
                {
                    if(p.getCurrentPosition() == (curPos+1))
                    {
                        if(cbObj.getLatestMove() == (curPos+1))
                            return curPos+11;
                    }
                    if(p.getCurrentPosition() == (curPos-1))
                    {
                        if(cbObj.getLatestMove() == (curPos-1))
                            return curPos+9;
                    }
                }
            }

        }
        else if ((pCol.equals(BLACK)) && (curPos>=41) && (curPos<=48))
        {
            for(int j=0;j<cbObj.allPieces.size();j++)
            {
                CP p = (CP) cbObj.allPieces.get(j);
                if((p.getPieceName().equals(PAWN)) &&
                   (p.getPieceColor().equals(WHITE)) &&
                   (p.movesDone.size()==1))
                {
                    if(p.getCurrentPosition() == (curPos+1))
                    {
                        if(cbObj.getLatestMove() == (curPos+1))
                            return curPos-9;
                    }
                    if(p.getCurrentPosition() == (curPos-1))
                    {
                        if(cbObj.getLatestMove() == (curPos-1))
                            return curPos-11;
                    }
                }
            }
        }
        else
        {
            //enpassent not possible
            return -1;
        }

        //enpassent not possible
         return -1;
    }
}
