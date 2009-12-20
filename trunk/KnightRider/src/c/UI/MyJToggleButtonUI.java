package c.UI;

import c.Constants.CConst;
import c.pieces.CP;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JToggleButton;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * position of jtogglebuttons
 *  00 01 02 03 04 05 06 07
 *  08 09 10 11 12 13 14 15
 *  16 17 18 19 20 21 22 23
 *  24 25 26 27 28 29 30 31
 *  32 33 34 35 36 37 38 39
 *  40 41 42 43 44 45 46 47
 *  48 49 50 51 52 53 54 55
 *  56 57 58 59 60 61 62 63  
 *
 */

/**
 * Class Name - MyJToggleButtonUI
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public class MyJToggleButtonUI extends JToggleButton implements ItemListener,CConst
{
    Color darkORlight;
    CP chessp;
    int val;
    //NewJFrame njf;
    ChessBoardUI njf;

 
    //test constructor
    public MyJToggleButtonUI(ChessBoardUI ja)
    {
        super();
        //System.out.println("Created");
        njf=ja;
        setChessp(null);
        addItemListener(this);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

   
    public void setDefaultBgColor()
    {
        this.setBackground(darkORlight);
    }
    public void setHighlightingBgColor()
    {
        this.setBackground(cHighlight);
    }
    public void setLightBgColor()
    {
        this.setBackground(cLight);
        darkORlight = cLight;
    }
    public void setDarkBgColor()
    {
        this.setBackground(cDark);
        darkORlight = cDark;
    }
    public void setValue(int v)
    {
        val = v;
    }

    public int getValue()
    {
        return val;
    }

    public void setChessp(CP p)
    {
        chessp = p;
    }

    public CP getChessp()
    {
        return chessp;
    }


    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
    }
    public void itemStateChanged(ItemEvent e) {
        MyJToggleButtonUI j = (MyJToggleButtonUI) e.getSource();
        if(j.isSelected())
        {
            //System.out.println(j.getChessp().getPieceName() + " is Selected and col is "+j.getChessp().getPieceColor());
            njf.ButtonSelected(j);
        }
        else
        {

            //System.out.println(j.getChessp().getPieceName() + " is DEselected and col is "+j.getChessp().getPieceColor());
            njf.ButtonDeSelected(j);
        }
        
    }

}
