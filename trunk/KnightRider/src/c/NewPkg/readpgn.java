package c.NewPkg;


import c.Constants.CConst;
import c.Pieces.CP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import c.UI.chessBoardUI;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class Name - readpgn
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public class readpgn implements CConst
{

    int noOfLines;
    Vector allLines = new Vector();
    Vector allTags = new Vector();
    public Vector allMoves = new Vector();

    public static Vector allWMoves = new Vector(0);
    public static Vector allBMoves = new Vector(0);
    boolean startOfComment = false;

    //NewJFrame njf = null;
    chessBoardUI njf = null;

    int count = 0;

    public readpgn(chessBoardUI n,File f)
    {
        njf = n;

        if(f!=null)
        {
            String file = f.getAbsolutePath();

            readFileAndGetAllLines(file);
            seperateTagsAndMoves();
            print();

        chkValidityOfPGNSyntax(allWMoves);
        chkValidityOfPGNSyntax(allBMoves);
        }

    }
  
    void readFileAndGetAllLines(String file)
    {
        String record = null;

        try
        {
            FileReader fr     = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            record = new String();
            while ((record = br.readLine()) != null)
            {
                noOfLines++;
                allLines.add(record);
                System.out.println(record);
            }
        }
        catch (IOException e)
        {
           // catch possible io errors from readLine()
           System.out.println("Uh oh, got an IOException error!");
           e.printStackTrace();
        }
    } // end of readFileAndGetAllLines()

    public void seperateTagsAndMoves()
    {
        for(int i=0;i<allLines.size();i++)
        {
            String s = (String) allLines.get(i);
            
            if(s.startsWith("1."))
            {
                //print(Integer.toString(i));
                getmoves(i);
                break;
            }
            else
            {
                allTags.add(s);
            }
        }
      
    }

    public void getmoves(int l)
    {
        String bORw = "w";
        for(int i=l;i<allLines.size();i++)
        {
            String s = (String) allLines.get(i);
            String[] sm = s.trim().split(" ");
            for(int j=0;j<sm.length;j++)
            {
               // System.out.println(j + "," + sm[j].trim());
                if(sm[j].trim().equals(""))
                {
                //nothing then just continue;
                continue;
                }
                if(sm[j].trim().contains("{"))
                {
                    //Start of comment
                    startOfComment = true;
                    continue;
                }
                if(sm[j].trim().contains("}"))
                {
                    //end of comment
                    startOfComment = false;
                    continue;
                }

                if(!startOfComment)
                {
                    if(sm[j].trim().matches("^[0-9]+."))
                    {
                        bORw = "w";
                        continue;
                    }
                    if(sm[j].trim().matches("\\([0-9]+."))
                    {
                        System.out.println("problem, its a variation "+ sm[j].trim());
                        System.exit(0);
                    }

                    if(sm[j].trim().matches("1-0"))
                    {
                        //White win s
                        allMoves.add("1-0");
                        break;
                    }
                    if(sm[j].trim().matches("0-1"))
                    {
                        //Black wins
                        allMoves.add("0-1");
                        break;
                    }
                    if(sm[j].trim().matches("1/2-1/2"))
                    {
                        //Draw
                        allMoves.add("1/2-1/2");
                        break;
                    }
                    if(sm[j].trim().matches("\\*"))
                    {
                        //Incomplete game
                        allMoves.add("*");
                        break;
                    }
                    if(bORw.equals("w"))
                    {
                        allWMoves.add(sm[j].trim());
                        allMoves.add(sm[j].trim());
                        bORw = "b";
                        continue;
                    }
                    if(bORw.equals("b"))
                    {
                        allBMoves.add(sm[j].trim());
                        allMoves.add(sm[j].trim());
                        bORw = "w";
                        continue;
                    }
                }
            }
        }
    }

    public void print()
    {
        //System.out.println(": "+allWMoves.size() + "," + allBMoves.size());
        for(int k=0;k<allWMoves.size()-1;k++)
        {
            String wm = (String) allWMoves.get(k);
            String bm = (String) allBMoves.get(k);
            //System.out.println(k+1+ ": "+wm.length() + "," + bm.length() + " " + wm+ " " + bm);
        }
    }



       /*
        * 2-pawn  :: e4,f5...
        * 3-castle k-side,other piece move  :: 0-0,Qa6,(2 with chk)
        * 4-pawn promotion,capture,or special mov :: e8=Q f1=R,bxc3,Qxg4,Rgf5,R5g4,(3 with chk)
        * 5-castle q-side, complete piece move :: O-O-O,Kh6h5,N5xe5,Nexd5(4 with chk)
        * 6-complete capture piece move :: Ng2xh4,(5 with chk)
        * 7-(6 with chk)
        */
    public void chkValidityOfPGNSyntax(Vector vm)
    {
        for(int k=0;k<vm.size();k++)
        {
            String wm = (String) vm.get(k);
            int l = wm.length();
            switch(l)
            {
                case 2:
                    if(wm.matches("^[a-h][2-7]+$")){} //pawn move like e4,e5
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
                case 3:
                    if(wm.matches("^[a-h][2-7][+]+$")){} //(case 2 + chk) like e4+
                    else if(wm.matches("^[a-h][2-7][#]+$")){} //(case 2 + chkmate)
                    else if(wm.matches("O-O+$")){} //castle k side like O-O
                    else if(wm.matches("^[RNBQK][a-h][1-8]+$")){} //other piece move like Qb6
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
               case 4:
                    if(wm.matches("O-O[+]+$")){} //(case 3.1 + chk) //castle k side + chk like O-O+
                    else if(wm.matches("^[RNBQK][a-h][1-8][+]+$")){} //(case 3.2 + chk) //other piece move + chk like Qf5+
                    else if(wm.matches("O-O[#]+$")){} //(case 3.1 + chkmate)
                    else if(wm.matches("^[RNBQK][a-h][1-8][#]+$")){} //(case 3.2 + chkmate)
                    else if(wm.matches("^[a-h][18]=[RNBQ]+$")){} //pawn promo like f1=Q
                    else if(wm.matches("^[a-h]x[a-h][1-8]+$")){} //capture by pawn like dxc5
                    else if(wm.matches("^[RNBQK]x[a-h][1-8]+$")){} //capture by piece like Rxe5
                    else if(wm.matches("^[RNBQK][a-h][a-h][1-8]+$")){} //special mov like Ndc3
                    else if(wm.matches("^[RNBQK][1-8][a-h][1-8]+$")){} //special mov like N3g4
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
               case 5:
                    if(wm.matches("^[a-h][18]=[RNBQ][+]+$")){} //(case 4.1 + chk) pawn promo + chk like f1=Q+
                    else if(wm.matches("^[a-h]x[a-h][1-8][+]+$")){} //(case 4.2 + chk) capture by pawn + chk like dxc5+
                    else if(wm.matches("^[RNBQK]x[a-h][1-8][+]+$")){} //(case 4.3 + chk)capture by piece + chk like Rxe5+
                    else if(wm.matches("^[RNBQK][a-h][a-h][1-8][+]+$")){} //(case 4.4 + chk)special mov + chk like Ndc3+
                    else if(wm.matches("^[RNBQK][1-8][a-h][1-8][+]+$")){} //(case 4.5 + chk)special mov + chk like N3g4+
                    else if(wm.matches("^[a-h][18]=[RNBQ][#]+$")){} //(case 4.1 + chkmate)
                    else if(wm.matches("^[a-h]x[a-h][1-8][#]+$")){} //(case 4.2 + chkmate)
                    else if(wm.matches("^[RNBQK]x[a-h][1-8][#]+$")){} //(case 4.3 + chkmate)
                    else if(wm.matches("^[RNBQK][a-h][a-h][1-8][#]+$")){} //(case 4.4 + chkmate)
                    else if(wm.matches("^[RNBQK][1-8][a-h][1-8][#]+$")){} //(case 4.5 + chkmate)
                    else if(wm.matches("O-O-O+$")){} //castle q side like O-O-O
                    else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8]+$")){} //complete piece move like Kh6h5
                    else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8]+$")){} //special piece capture move like N5xe5
                    else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8]+$")){} //special piece capture move like Nexd5
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
                case 6:
                    if(wm.matches("O-O-O[+]+$")){} //(case 5.1 + chk) castle q side like O-O-O+
                    else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8][+]+$")){} //(case 5.2 + chk) complete piece move like Kh6h5+
                    else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8][+]+$")){} //(case 5.3 + chk)special piece capture move like N5xe5+
                    else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8][+]+$")){} //(case 5.4 + chk)special piece capture move like Nexd5+
                    else if(wm.matches("O-O-O[#]+$")){} //(case 5.1 + chkmate)
                    else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8][#]+$")){} //(case 5.2 + chkmate)
                    else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8][#]+$")){} //(case 5.3 + chkmate)
                    else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8][#]+$")){} //(case 5.4 + chkmate)
                    else if(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8]+$")){} //complete piece capture move like Ng2xh4
                    else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ]+$")){} //pawn capture and promotion like exf8=Q
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
                case 7:
                    if(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8][+]+$")){}//(case 6.1 + chk)complete piece capture move + chk like Ng2xh4+
                    else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ][+]+$")){}//(case 6.2 + chk)pawn capture and promotion + chk like exf8=Q+
                    if(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8][#]+$")){}//(case 6.1 + chkmate)
                    else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ][#]+$")){}//(case 6.2 + chkmate)
                    else
                        System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
                default:
                    System.out.println("ERROR!!! len=" + l +" '" + wm + "' dos'nt match regex  index " + k);
                    break;
            }
        }
    }

    public String convertNumValToAlphaCol(int i)
    {
        switch (i)
        {
            case 1:
                return "a";
            case 2:
                return "b";
            case 3:
                return "c";
            case 4:
                return "d";
            case 5:
                return "e";
            case 6:
                return "f";
            case 7:
                return "g";
            case 8:
                return "h";
             default:
                 return "ERROR";
        }
    }

    public int convertAlphaColToNumVal(char c)
    {
        if(c == 'a')
            return 1;
        else if(c == 'b')
            return 2;
        else if(c == 'c')
            return 3;
        else if(c == 'd')
            return 4;
        else if(c == 'e')
            return 5;
        else if(c == 'f')
            return 6;
        else if(c == 'g')
            return 7;
        else if(c == 'h')
            return 8;
        else
            return -1;
    }


    public String convertNumValToNumRow(int i)
    {
         switch (i)
        {
            case 10:
                return "1";
            case 20:
                return "2";
            case 30:
                return "3";
            case 40:
                return "4";
            case 50:
                return "5";
            case 60:
                return "6";
            case 70:
                return "7";
            case 80:
                return "8";
             default:
                 return "ERROR";
         }
    }

    public int convertNumRowToNumVal(char c)
    {

        if(c == '1')
            return 10;
        else if(c == '2')
            return 20;
        else if(c == '3')
            return 30;
        else if(c == '4')
            return 40;
        else if(c == '5')
            return 50;
        else if(c == '6')
            return 60;
        else if(c == '7')
            return 70;
        else if(c == '8')
            return 80;
        else
            return -1;
    }

    public String getPieceNameFromPGN(char c)
    {
        if(c=='R')
            return(ROOK);
        else if(c =='N')
            return(KNIGHT);
        else if(c=='B')
            return(BISHOP);
        else if(c=='Q')
            return(QUEEN);
        else if(c=='K')
            return(KING);
        else
            return"INVALID PIECE";
    }

    private void callFramesMakeMove(String col,Vector v)
    {
         /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */
        myJToggleButton njtb = null,pjtb=null;
        int fVal=0;
        int tVal=0;

        String name=(String) v.get(0);
        String fCol = (String) v.get(1);
        String fRow =(String) v.get(2);
        String tCol = (String) v.get(3);
        String tRow = (String) v.get(4);
        String cap =(String) v.get(5);
        String castle =(String) v.get(6);
        String chk =(String) v.get(7);
        String promo =(String) v.get(8);

        try
        {
            if(castle.equals("KS") && col.equals(WHITE))
                tVal=17;
            else if(castle.equals("QS") && col.equals(WHITE))
                tVal=13;
            if(castle.equals("KS") && col.equals(BLACK))
                tVal=87;
            else if(castle.equals("QS") && col.equals(BLACK))
                tVal=83;
        }
        catch(NullPointerException n)
        {
            tVal = Integer.parseInt(tCol)+Integer.parseInt(tRow);
        }

        if((fCol==null) && (fRow==null))
        {
            fVal = 0;
        }
        else if((fCol!=null) && (fRow==null))
        {
            fVal = Integer.parseInt(fCol);
        }
        else if((fCol==null) && (fRow!=null))
        {
            fVal = Integer.parseInt(fRow);
        }
        else
        {
            fVal = Integer.parseInt(fCol) + Integer.parseInt(fRow);
        }

        for(int i=0;i<njf.jtb.length;i++)
        {
            if(njf.jtb[i].getValue()==tVal)
            {
                njtb=njf.jtb[i];
                break;
            }
        }

        for(int i=0;i<njf.jtb.length;i++)
        {
            CP p = njf.jtb[i].getChessp();
            if((p!=null)&&(p.movesPossible!=null))
            {
                if(p.getPieceName().equals(name))
                {
                    if(p.getPieceColor().equals(col))
                    {
                        if(p.movesPossible.contains(tVal))
                        {
                            int curpos = p.getCurrentPosition();
                            if(fVal==0)
                            {
                                //add
                                pjtb=njf.jtb[i];
                                break;
                            }
                            else if(fVal<10)
                            {
                                if((curpos%10)==fVal)
                                {
                                    //add
                                    pjtb=njf.jtb[i];
                                    break;
                                }
                            }
                            else if((fVal>10)&&(fVal%10==0))
                            {
                                if((curpos/10)==fVal)
                                {
                                    //add
                                    pjtb=njf.jtb[i];
                                    break;
                                }
                            }
                            else
                            {
                                if(curpos==fVal)
                                {
                                    //add
                                    pjtb=njf.jtb[i];
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        njf.makeMove(njtb, pjtb,v);
    }

    private Vector getVector(String name, String fCol, String fRow, String tCol, String tRow, String cap, String castle, String check, String promo)
    {
        Vector v = new Vector();
        v.add(name);
        v.add(fCol);
        v.add(fRow);
        v.add(tCol);
        v.add(tRow);
        v.add(cap);
        v.add(castle);
        v.add(check);
        v.add(promo);

        return v;
    }

    public String convertPGNMoveToGUIFormat(String col)
    {
        if(col.equals(WHITE))
        {
            String s = (count+1)+". "+allWMoves.get(count)+" ";
            parseEachPGNMove(WHITE,(String) allWMoves.get(count));
            return s;
        }
        else
        {
            String s = " "+allBMoves.get(count)+"\n";
            parseEachPGNMove(BLACK,(String) allBMoves.get(count));
            count ++;
            return s;
        }
    }

    private void parseEachPGNMove(String col,String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        Vector v = new Vector();
        int l = m.length();
        switch(l)
            {
                case 2:
                    v = parsePGNMoveLen2(m);
                    System.out.println(v);
                    break;
                case 3:
                     v = parsePGNMoveLen3(m);
                    System.out.println(v);
                    break;
               case 4:
                     v = parsePGNMoveLen4(m);
                    System.out.println(v);
                    break;
               case 5:
                    v = parsePGNMoveLen5(m);
                    System.out.println(v);
                    break;
                case 6:
                    v = parsePGNMoveLen6(m);
                    System.out.println(v);
                    break;
                case 7:
                    v = parsePGNMoveLen7(m);
                    System.out.println(v);
                    break;
                default:
                    System.out.println("ERROR!!! len=" + l +" '" + m + "' dos'nt match regex  index ");
                    break;
        }
        callFramesMakeMove(col,v);
    }

    private Vector parsePGNMoveLen2(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

         //if(wm.matches("^[a-h][2-7]+$")){} //pawn move like e4,e5
        String name = PAWN;
        String fCol = null;
        String fRow = null;
        String tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
        String tRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;

        Vector v = getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo);
        return v;

    }

    private Vector parsePGNMoveLen3(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        /*
         if(wm.matches("^[a-h][2-7][+]+$")){} //(case 2 + chk) like e4+
                    else if(wm.matches("^[a-h][2-7][#]+$")){} //(case 2 + chkmate)
                    else if(wm.matches("O-O+$")){} //castle k side like O-O
                    else if(wm.matches("^[RNBQK][a-h][1-8]+$")){} //other piece move like Qb6
         */

        String name = null;
        String fCol = null;
        String fRow = null;
        String tCol = null;
        String tRow = null;
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;

        if(m.matches("^[RNBQK].+"))
        {
            name = getPieceNameFromPGN(m.charAt(0));

            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));

        }
        else if(m.matches("O-O+$"))
        {
            name = KING;
            castle = "KS";
        }
        else
        {
            name = PAWN;
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));

            if(m.charAt(2)=='+')
                check = "C";
            else
                check = "CM";
        }

        return(getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo));
    }

    private Vector parsePGNMoveLen4(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        /*
         if(wm.matches("O-O[+]+$")){} //(case 3.1 + chk) //castle k side + chk like O-O+
            else if(wm.matches("^[RNBQK][a-h][1-8][+]+$")){} //(case 3.2 + chk) //other piece move + chk like Qf5+
            else if(wm.matches("O-O[#]+$")){} //(case 3.1 + chkmate)
            else if(wm.matches("^[RNBQK][a-h][1-8][#]+$")){} //(case 3.2 + chkmate)
            else if(wm.matches("^[a-h][18]=[RNBQ]+$")){} //pawn promo like f1=Q
            else if(wm.matches("^[a-h]x[a-h][1-8]+$")){} //capture by pawn like dxc5
            else if(wm.matches("^[RNBQK]x[a-h][1-8]+$")){} //capture by piece like Rxe5
            else if(wm.matches("^[RNBQK][a-h][a-h][1-8]+$")){} //special mov like Ndc3
            else if(wm.matches("^[RNBQK][1-8][a-h][1-8]+$")){} //special mov like N3g4
         */

        String name = null;
        String fCol = null;
        String fRow = null;
        String tCol = null;
        String tRow = null;
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;

        if(m.contains("O-O"))
        {
            name = KING;
            castle = "KS";
            if(m.charAt(3)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][a-h][1-8].+"))
        {
            name = getPieceNameFromPGN(m.charAt(0));

            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));

            if(m.charAt(3)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[a-h][18]=[RNBQ]+$"))
        {
            name = PAWN;
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            promo = getPieceNameFromPGN(m.charAt(3));
        }
        else if(m.matches("^[a-h]x[a-h][1-8]+$")) //capture by pawn like dxc5
        {
            name = PAWN;
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            cap = "true";
        }
        else if(m.matches("^[RNBQK]x[a-h][1-8]+$")) //capture by piece like Rxe5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            cap = "true";
        }
        else if(m.matches("^[RNBQK][a-h][a-h][1-8]+$")) //special mov like Ndc3
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
        }
        else if(m.matches("^[RNBQK][1-8][a-h][1-8]+$")) //special mov like N3g4
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
        }

        return(getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo));
    }

    private Vector parsePGNMoveLen5(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        /*
        if(wm.matches("^[a-h][18]=[RNBQ][+]+$")){} //(case 4.1 + chk) pawn promo + chk like f1=Q+
            else if(wm.matches("^[a-h]x[a-h][1-8][+]+$")){} //(case 4.2 + chk) capture by pawn + chk like dxc5+
            else if(wm.matches("^[RNBQK]x[a-h][1-8][+]+$")){} //(case 4.3 + chk)capture by piece + chk like Rxe5+
            else if(wm.matches("^[RNBQK][a-h][a-h][1-8][+]+$")){} //(case 4.4 + chk)special mov + chk like Ndc3+
            else if(wm.matches("^[RNBQK][1-8][a-h][1-8][+]+$")){} //(case 4.5 + chk)special mov + chk like N3g4+
            else if(wm.matches("^[a-h][18]=[RNBQ][#]+$")){} //(case 4.1 + chkmate)
            else if(wm.matches("^[a-h]x[a-h][1-8][#]+$")){} //(case 4.2 + chkmate)
            else if(wm.matches("^[RNBQK]x[a-h][1-8][#]+$")){} //(case 4.3 + chkmate)
            else if(wm.matches("^[RNBQK][a-h][a-h][1-8][#]+$")){} //(case 4.4 + chkmate)
            else if(wm.matches("^[RNBQK][1-8][a-h][1-8][#]+$")){} //(case 4.5 + chkmate)
            else if(wm.matches("O-O-O+$")){} //castle q side like O-O-O
            else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8]+$")){} //complete piece move like Kh6h5
            else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8]+$")){} //special piece capture move like N5xe5
            else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8]+$")){} //special piece capture move like Nexd5
         */

        String name = null;
        String fCol = null;
        String fRow = null;
        String tCol = null;
        String tRow = null;
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;


        if(m.matches("^[a-h][18]=[RNBQ].+"))
        {
            name = PAWN;
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            promo = getPieceNameFromPGN(m.charAt(3));
              if(m.charAt(4)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[a-h]x[a-h][1-8].+")) //capture by pawn like dxc5
        {
            name = PAWN;
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            cap = "true";
            if(m.charAt(4)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK]x[a-h][1-8].+")) //capture by piece like Rxe5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            cap = "true";
            if(m.charAt(4)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][a-h][a-h][1-8].+")) //special mov like Ndc3
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            if(m.charAt(4)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][1-8][a-h][1-8].+")) //special mov like N3g4
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));
            if(m.charAt(4)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("O-O-O+$")) //castle q side like O-O-O
        {
            name = KING;
            castle = "QS";
        }
        else if(m.matches("^[RNBQK][a-h][1-8][a-h][1-8]+$")) //complete piece move like Kh6h5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
        }
        else if(m.matches("^[RNBQK][1-8][x][a-h][1-8]+$")) //special piece capture move like N5xe5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
            cap = "true";
        }
        else if(m.matches("^[RNBQK][a-h][x][a-h][1-8]+$")) //special piece capture move like Nexd5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
            cap = "true";
        }

        return(getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo));
    }

    private Vector parsePGNMoveLen6(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        /*
        if(wm.matches("O-O-O[+]+$")){} //(case 5.1 + chk) castle q side like O-O-O+
            else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8][+]+$")){} //(case 5.2 + chk) complete piece move like Kh6h5+
            else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8][+]+$")){} //(case 5.3 + chk)special piece capture move like N5xe5+
            else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8][+]+$")){} //(case 5.4 + chk)special piece capture move like Nexd5+
            else if(wm.matches("O-O-O[#]+$")){} //(case 5.1 + chkmate)
            else if(wm.matches("^[RNBQK][a-h][1-8][a-h][1-8][#]+$")){} //(case 5.2 + chkmate)
            else if(wm.matches("^[RNBQK][1-8][x][a-h][1-8][#]+$")){} //(case 5.3 + chkmate)
            else if(wm.matches("^[RNBQK][a-h][x][a-h][1-8][#]+$")){} //(case 5.4 + chkmate)
            else if(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8]+$")){} //complete piece capture move like Ng2xh4
            else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ]+$")){} //pawn capture and promotion like exf8=Q

         */

        String name = null;
        String fCol = null;
        String fRow = null;
        String tCol = null;
        String tRow = null;
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;

        
        if(m.matches("O-O-O.+")) //castle q side like O-O-O
        {
            name = KING;
            castle = "QS";
            if(m.charAt(5)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][a-h][1-8][a-h][1-8].+")) //complete piece move like Kh6h5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
            if(m.charAt(5)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][1-8][x][a-h][1-8].+")) //special piece capture move like N5xe5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
            cap = "true";
            if(m.charAt(5)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][a-h][x][a-h][1-8].+")) //special piece capture move like Nexd5
        {
            name = getPieceNameFromPGN(m.charAt(0));
            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(3)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(4)));
            cap = "true";
            if(m.charAt(5)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[RNBQK][a-h][1-8][x][a-h][1-8]+$"))
        {
            name = getPieceNameFromPGN(m.charAt(0));

            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));

            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(4)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(5)));

            cap = "true";
        }
        else if(m.matches("^[a-h][x][a-h][18][=][RNBQ]+$"))
        {
            name = "PAWN";

            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));

            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));

            cap = "true";

            promo = getPieceNameFromPGN(m.charAt(5));
        }


        return(getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo));
    }

    private Vector parsePGNMoveLen7(String m)
    {
        /*
         * The format in which the data must be fetched
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fCol(1/2/3/4/5/6/7/8/null)
         *   fRow(10/20/30/40/50/60/70/80/null)
         *   tCol(1/2/3/4/5/6/7/8/null)
         *   tRow(10/20/30/40/50/60/70/80/null)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */

        /*
        iif(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8][+]+$")){}//(case 6.1 + chk)complete piece capture move + chk like Ng2xh4+
            else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ][+]+$")){}//(case 6.2 + chk)pawn capture and promotion + chk like exf8=Q+
            if(wm.matches("^[RNBQK][a-h][1-8][x][a-h][1-8][#]+$")){}//(case 6.1 + chkmate)
            else if(wm.matches("^[a-h][x][a-h][18][=][RNBQ][#]+$")){}//(case 6.2 + chkmate)

         */

        String name = null;
        String fCol = null;
        String fRow = null;
        String tCol = null;
        String tRow = null;
        String cap = null;
        String castle = null;
        String check = null;
        String promo = null;

        if(m.matches("^[RNBQK][a-h][1-8][x][a-h][1-8].+"))
        {

            name = getPieceNameFromPGN(m.charAt(0));

            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(1)));
            fRow = Integer.toString(convertNumRowToNumVal(m.charAt(2)));

            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(4)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(5)));

            cap = "true";
            if(m.charAt(6)=='+')
                check = "C";
            else
                check = "CM";
        }
        else if(m.matches("^[a-h][x][a-h][18][=][RNBQ].+"))
        {
            name = "PAWN";

            fCol = Integer.toString(convertAlphaColToNumVal(m.charAt(0)));
            
            tCol = Integer.toString(convertAlphaColToNumVal(m.charAt(2)));
            tRow = Integer.toString(convertNumRowToNumVal(m.charAt(3)));

            cap = "true";

            promo = getPieceNameFromPGN(m.charAt(5));
            if(m.charAt(6)=='+')
                check = "C";
            else
                check = "CM";
        }

        return(getVector(name, fCol, fRow, tCol, tRow, cap, castle, check, promo));
    }





}

