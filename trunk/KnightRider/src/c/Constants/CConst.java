package c.Constants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.io.File;

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
 * @author Suhas Bharadwaj
 */
public interface CConst
{   
    String FILENAME = "logs.txt";
    File FNAME=new File(FILENAME);

    String APP_NAME = "Chess-Game";
    String VERSION = "v0.0.1";
    String TITLE = APP_NAME + " " + VERSION;
    String PAWN = "Pawn";
    String ROOK = "Rook";
    String KNIGHT = "Knight";
    String BISHOP = "Bishop";
    String QUEEN = "Queen";
    String KING = "King";
    
    String WHITE = "White";
    String BLACK = "Black";

    int MINPOS = 11;
    int MAXPOS = 88;

    int MINX=0;
    int MAXX=7;
    int MINY=0;
    int MAXY=7;

//TODO : include all gif files
    String whitePawn = "/images/WP.gif";
    String blackPawn = "/images/BP.gif";

    String whiteRook = "/images/WR.gif";
    String blackRook = "/images/BR.gif";

    String whiteKnight = "/images/WN.gif";
    String blackKnight = "/images/BN.gif";

    String whiteBishop = "/images/WB.gif";
    String blackBishop = "/images/BB.gif";

    String whiteQueen = "/images/WQ.gif";
    String blackQueen = "/images/BQ.gif";

    String whiteKing = "/images/WK.gif";
    String blackKing = "/images/BK.gif";

    Color cLight = new Color(102, 255, 102);
    Color cDark = new Color(51, 153, 0);
    Color cHighlight = new Color(153, 153, 0);

}
