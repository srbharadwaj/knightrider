package c.newpackage;


import c.Constants.CConst;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import c.UI.ChessBoardUI;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class Name - AllPGNGames
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public class AllPGNGames implements CConst
{

    int noOfLines;
    Vector allLines = new Vector();
    public Vector pgnGames = new Vector();
    ChessBoardUI njf = null;

    public AllPGNGames(ChessBoardUI n,File f)
    {
        njf = n;

        if(f!=null)
        {
            String file = f.getAbsolutePath();

            readFileAndGetAllLines(file);
            
            getEachGame();

        }

    }

    public void getEachGame()
    {
        int tCount = 0;
        int mCount = 0;
        EachPGNGame pg = null;
        Vector lines = new Vector();
        for(int i=0;i<allLines.size();i++)
        {
            String s = (String) allLines.get(i);
            if(s.startsWith("["))
            {
                tCount++;
                if((tCount-mCount) == 1)
                {
                    if((lines.size()!=0)&&(pg!=null))
                    {
                        pg.setLines(lines);
                        lines.clear();
                    }
                    pg = null;
                    pg = new EachPGNGame(njf,pgnGames.size()+1);
                    pgnGames.add(pg);
                }
            }
            if(s.startsWith("1."))
            {
                mCount++;
                if(mCount>tCount)
                {
                    if((lines.size()!=0)&&(pg!=null))
                    {
                        pg.setLines(lines);
                        lines.clear();
                    }
                    pg = null;
                    pg = new EachPGNGame(njf,pgnGames.size()+1);
                    pgnGames.add(pg);
                }
                tCount = mCount;
            }
            lines.add(s);
        }
        if((lines.size()!=0)&&(pg!=null))
        {
            pg.setLines(lines);
            lines.clear();
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
                //System.out.println(record);
            }
            fr.close();
            br.close();
        }
        catch (IOException e)
        {
           // catch possible io errors from readLine()
           System.out.println("Uh oh, got an IOException error!");
           e.printStackTrace();
        }
    } // end of readFileAndGetAllLines()

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

}

