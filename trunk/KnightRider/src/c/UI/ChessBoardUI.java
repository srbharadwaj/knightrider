/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package c.UI;

import c.Constants.CConst;
import c.main.CB;
import c.newpackage.EachPGNGame;
import c.newpackage.AllPGNGames;
import c.pieces.CP;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

/**
 * Class Name - ChessBoardUI
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public class ChessBoardUI extends JFrame implements ActionListener,ItemListener,CConst
{
    public JPanel mainP = new JPanel(new BorderLayout());
    public JPanel cP = new JPanel(new BorderLayout());
    public JPanel nP = new JPanel(new BorderLayout());
    public JPanel sP = new JPanel(new BorderLayout());
    public JPanel eP = new JPanel(new BorderLayout());
    public JPanel wP = new JPanel(new BorderLayout());

    public JPanel chessBoardContainer = new JPanel(new BorderLayout());
    public JPanel chessBoard = new JPanel(new GridLayout(8,8));
    public JPanel butJPanel = new JPanel(new FlowLayout());

    public JPanel whiteCapPanel = new JPanel(new GridLayout(4,4));
    public JPanel blackCapPanel = new JPanel(new GridLayout(4,4));

    public JTextArea txtAreaMoves = new JTextArea();

    public JButton first = new JButton("First");
    public JButton prev = new JButton("Prev");
    public JButton next = new JButton("Next");
    public JButton last = new JButton("Last");

    public JMenuBar menuBar = new JMenuBar();
    public CB cbo;
    public MyJToggleButtonUI jtb[];
    public MyJToggleButtonUI prevButSel;


    public String castleWhichSide = null;
    public int noOfButSelected=0;
    public String whoseTurn = WHITE;
    private boolean highlight = true;
    public EachPGNGame eachGame = null;
    public AllPGNGames allGames = null;
    //private String turn = null;
    public int noOfMoves = 0;
    public int currentGameNo = 1;


    public ChessBoardUI(CB b)
    {    
        cbo = b;

        setJMenuBar(createMenuBar());

        mainP.add(processCenterPanel(),BorderLayout.CENTER);
        mainP.add(nP,BorderLayout.NORTH);
        mainP.add(processSouthPanel(),BorderLayout.SOUTH);
        mainP.add(processEastPanel(),BorderLayout.EAST);
        mainP.add(processWestPanel(),BorderLayout.WEST);


        setContentPane(mainP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(760, 660);
        //Display the window.
        setVisible(true);
        setResizable(false);
        setTitle(TITLE);
        //assignCps();
        resetChessBoardUI(false);

        first.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);
        last.addActionListener(this);

    }

    public void resetChessBoardUI(boolean b)
    {
        if(b)
        {
            processCenterPanel();
            txtAreaMoves.setText("");
        }
        prevButSel=null;
        castleWhichSide = null;
        noOfButSelected=0;
        whoseTurn = WHITE;
        //highlight = true;
        //eachGame = null;
        //turn = null;
        //noOfMoves = 0;
        assignCps();
    }

    public JPanel processCenterPanel()
    {
        cP.removeAll();
        cP.revalidate();
        cP.add(resetChessContainerUI(),BorderLayout.CENTER);
        cP.add(createButPanel(),BorderLayout.SOUTH);

        return cP;
    }

    public JPanel processSouthPanel()
    {
        sP.removeAll();
        sP.revalidate();
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Log"));
        JTextArea jTextArea2 = new JTextArea();
        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        sP.add(jScrollPane2);

        return sP;

    }

    public JPanel processEastPanel()
    {
        eP.removeAll();
        eP.revalidate();
        JSplitPane jSplitPane1 = new JSplitPane();
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        JScrollPane jpMoves = new JScrollPane();
        jpMoves.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Moves"));
        
        txtAreaMoves.setColumns(15);
        txtAreaMoves.setEditable(false);
        txtAreaMoves.setLineWrap(true);
        txtAreaMoves.setWrapStyleWord(true);
        jpMoves.setViewportView(txtAreaMoves);

        JScrollPane jpGames = new JScrollPane();
        jpGames.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Games"));
        jpGames.setViewportView(new GamesPanelUI(this));

        jSplitPane1.setTopComponent(jpGames);
        jSplitPane1.setBottomComponent(jpMoves);

        eP.add(jSplitPane1);

        return eP;
        
    }

    public JPanel processWestPanel()
    {
        wP.removeAll();
        wP.revalidate();
       /* wP.setLayout(new GridLayout(2,1));
        //JScrollPane jpGames = new JScrollPane();
        //jpGames.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Captured Pieces"));
        wP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Captured Pieces"));
        wP.add(whiteCapPanel);
        wP.add(blackCapPanel);

        //wP.add(jpGames);
*/
        return(wP);

    }

    public JMenuBar createMenuBar()
    {
        JMenu menuOpt = new JMenu("Game");
        menuOpt.setMnemonic(KeyEvent.VK_G);
        JMenuItem miR = new JMenuItem("Open");
        miR.setMnemonic(KeyEvent.VK_O);
        miR.addActionListener(this);

        JMenuItem miM = new JMenuItem("Save");
        miM.setMnemonic(KeyEvent.VK_S);
        miM.addActionListener(this);

        JSeparator js1 = new JSeparator();

        JMenuItem miE = new JMenuItem("Exit");
        miE.setMnemonic(KeyEvent.VK_E);
        miE.addActionListener(this);

        menuOpt.add(miR);
        menuOpt.add(miM);
        menuOpt.add(js1);
        menuOpt.add(miE);
        menuBar.add(menuOpt);

        JMenu menuOptions = new JMenu("Options");
        menuOptions.setMnemonic(KeyEvent.VK_O);
        JCheckBoxMenuItem jcbMi = new JCheckBoxMenuItem("Flip Board");
        JCheckBoxMenuItem jcbM = new JCheckBoxMenuItem("HighLight Possible Moves");
        jcbM.setSelected(true);
        menuOptions.add(jcbMi);
        menuOptions.add(jcbM);
        jcbMi.addItemListener(this);
        jcbM.addItemListener(this);
        menuBar.add(menuOptions);


        JMenu menuH = new JMenu("Help");
        menuH.setMnemonic(KeyEvent.VK_H);
        JMenuItem miHelp = new JMenuItem("Help Contents");
        //miHelp.setIcon(getImageIcon(HELP));
        // miHelp.addActionListener(this);
        JMenuItem miAbout = new JMenuItem("About");
        //miAbout.setIcon(getImageIcon(ABOUT));
        miAbout.addActionListener(this);
       
        menuH.add(miHelp);
        menuH.add(miAbout);
         
        menuBar.add(menuH);
        return menuBar;
    }

    public JPanel resetChessContainerUI()
    {
        chessBoardContainer.removeAll();
        chessBoardContainer.revalidate();
        chessBoard.removeAll();
        chessBoard.revalidate();
        jtb = new MyJToggleButtonUI[64];
        int c = 0;
        int counter=0;
        int mtbVal=81;
        for(int i=0;i<64;i++)
        {
            jtb[i] = new MyJToggleButtonUI(this);
            jtb[i].setText("");
            jtb[i].setValue(mtbVal);
            if(i%2==c)
            {
                //light
                jtb[i].setLightBgColor();
            }
            else
            {
                //dark
                jtb[i].setDarkBgColor();
            }
           counter++;
           if(counter==8)
           {
               if(c==0)
               {
                    c=1;
               }
               else
               {
                   c=0;
               }
               counter=0;
               mtbVal=mtbVal-17;
           }
           else
           {
               mtbVal++;
           }
             chessBoard.add(jtb[i]);
        }

        setPieceUI();
        chessBoardContainer.add(chessBoard,BorderLayout.CENTER);

        chessBoardContainer.add(addA_Hpanel(true),BorderLayout.NORTH);

        return chessBoardContainer;
    }

    public JPanel addA_Hpanel(Boolean straight)
    {

        JPanel jpN = new JPanel();
        jpN.setLayout(new GridLayout(1,8));

        JToggleButton l1 = new JToggleButton("a");
        JToggleButton l2 = new JToggleButton("b");
        JToggleButton l3 = new JToggleButton("c");
        JToggleButton l4 = new JToggleButton("d");
        JToggleButton l5 = new JToggleButton("e");
        JToggleButton l6 = new JToggleButton("f");
        JToggleButton l7 = new JToggleButton("g");
        JToggleButton l8 = new JToggleButton("h");

        l1.setEnabled(false);
        l2.setEnabled(false);
        l3.setEnabled(false);
        l4.setEnabled(false);
        l5.setEnabled(false);
        l6.setEnabled(false);
        l7.setEnabled(false);
        l8.setEnabled(false);

        if(straight)
        {
            jpN.add(l1);
            jpN.add(l2);
            jpN.add(l3);
            jpN.add(l4);
            jpN.add(l5);
            jpN.add(l6);
            jpN.add(l7);
            jpN.add(l8);
        }
        else
        {
            jpN.add(l8);
            jpN.add(l7);
            jpN.add(l6);
            jpN.add(l5);
            jpN.add(l4);
            jpN.add(l3);
            jpN.add(l2);
            jpN.add(l1);
        }

        return jpN;
      
    }

    private JPanel createButPanel()
    {
        butJPanel.removeAll();
        butJPanel.revalidate();

        butJPanel.add(first);
        butJPanel.add(prev);
        butJPanel.add(next);
        butJPanel.add(last);
        return butJPanel;
    }

    /*public static void main(String[] args)
    {
        //Schedule getEachGame job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //createAndShowGUI();
                //new ChessBoardUI(null);
            }
        });
    }*/

    public void setEachPGNGame(int gameNo)
    {
        eachGame = (EachPGNGame) allGames.pgnGames.get(gameNo-1);
        cbo.resetCB();
        this.resetChessBoardUI(true);
        prev.setEnabled(false);
        first.setEnabled(false);
        next.setEnabled(true);
        last.setEnabled(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Prev"))
        {
            System.out.println("prev pressed"+noOfMoves);
            int m = noOfMoves-1;
            noOfMoves=0;
            eachGame.count=0;
            cbo.resetCB();
            this.resetChessBoardUI(true);
            while(noOfMoves<m)
            {
                nextOrLastButtonIsClicked((JButton) e.getSource(),false);
            }
             next.setEnabled(true);
            last.setEnabled(true);
            if(m==0)
            {
                prev.setEnabled(false);
                first.setEnabled(false);
            }
        }
        else if(e.getActionCommand().equals("First"))
        {
            System.out.println("first pressed");
            noOfMoves=0;
            eachGame.count=0;
            cbo.resetCB();
            this.resetChessBoardUI(true);
            next.setEnabled(true);
            last.setEnabled(true);
            prev.setEnabled(false);
            first.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Next"))
        {
            System.out.println("next pressed");
             nextOrLastButtonIsClicked((JButton) e.getSource(),false);
             prev.setEnabled(true);
            first.setEnabled(true);
        }
        else if(e.getActionCommand().equals("Last"))
        {
             nextOrLastButtonIsClicked((JButton) e.getSource(),true);
             next.setEnabled(false);
             prev.setEnabled(true);
            first.setEnabled(true);
        }
        else if(e.getActionCommand().equals("Open"))
        {
            // if (isFileChanged())
            //     askForSaving();
            JFileChooser fileChooser = new JFileChooser();
            //fileChooser.setCurrentDirectory(new java.io.File(fileChooserDir));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(new PGNFileFilter());
            int choice = fileChooser.showOpenDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION)
            {
                System.out.println(fileChooser.getSelectedFile());
                allGames = new AllPGNGames(this,fileChooser.getSelectedFile());
                setEachPGNGame(currentGameNo);
                //loadFile(fileChooser.getSelectedFile());
                //fileName = fileChooser.getSelectedFile().getName();
                // setTitle(fileName);
                //fileIsChanged = false;
                
            }
        }
        else if(e.getActionCommand().equals("Save"))
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(new PGNFileFilter());
            int choice = fileChooser.showSaveDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) 
            {
                String file = fileChooser.getSelectedFile()+".pgn";
                System.out.println(file);
                try {
                    saveContentsToPGNFile(file);
                } catch (IOException ex) {
                    Logger.getLogger(ChessBoardUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if(e.getActionCommand().equals("About"))
        {
            System.out.println("ABOUT,,,");
            new AboutBoxUI(this,true).setVisible(true);
        }

    }

    public void itemStateChanged(ItemEvent e)
    {
        JMenuItem j = (JMenuItem) e.getSource();
        if(j.getText().equals("Flip Board"))
        {
        if(j.isSelected())
            reverseBoard(BLACK);
        else
            reverseBoard(WHITE);
        }

        if(j.getText().equals("HighLight Possible Moves"))
        {
            setHighlightValue(j.isSelected());
        }
    }

    /*public void nextOrLastButtonIsClicked(JButton j,boolean loop)
    {
        while(true)
        {
            noOfMoves++;
            //System.out.println("nextOrLastButtonIsClicked");
            String s = "";
            try
            {
                if(turn.equals(WHITE))
                {
                    s = eachGame.convertPGNMoveToGUIFormat(WHITE);
                    turn = BLACK;
                }
                else if(turn.equals(BLACK))
                {
                    s = eachGame.convertPGNMoveToGUIFormat(BLACK);
                    turn = WHITE;
                }
            }
            catch(NullPointerException n)
            {
                s = eachGame.convertPGNMoveToGUIFormat(WHITE);
                turn = BLACK;
            }
            txtAreaMoves.append(s);
           // System.out.println(noOfMoves +" : nextOrLastButtonIsClicked : "+eachGame.allBandWMoves.size());
            if(noOfMoves == (eachGame.allBandWMoves.size()-1))
            {
                j.setEnabled(false);
                last.setEnabled(false);
                txtAreaMoves.append((String) eachGame.allBandWMoves.get(eachGame.allBandWMoves.size()-1));
                break;
            }
            if(!loop)
            {
                break;
            }
        }
    }*/

     public void nextOrLastButtonIsClicked(JButton j,boolean loop)
    {
        while(true)
        {
            noOfMoves++;
            //System.out.println("nextOrLastButtonIsClicked");
            String s = "";
            try
            {
                if(whoseTurn.equals(WHITE))
                {
                    s = eachGame.convertPGNMoveToGUIFormat(WHITE);
                    whoseTurn = BLACK;
                }
                else if(whoseTurn.equals(BLACK))
                {
                    s = eachGame.convertPGNMoveToGUIFormat(BLACK);
                    whoseTurn = WHITE;
                }
            }
            catch(NullPointerException n)
            {
                s = eachGame.convertPGNMoveToGUIFormat(WHITE);
                whoseTurn = BLACK;
            }
            txtAreaMoves.append(s);
           // System.out.println(noOfMoves +" : nextOrLastButtonIsClicked : "+eachGame.allBandWMoves.size());
            if(noOfMoves == (eachGame.allBandWMoves.size()-1))
            {
                j.setEnabled(false);
                last.setEnabled(false);
                txtAreaMoves.append((String) eachGame.allBandWMoves.get(eachGame.allBandWMoves.size()-1));
                break;
            }
            if(!loop)
            {
                break;
            }
        }
    }

    static class PGNFileFilter extends javax.swing.filechooser.FileFilter
    {
        public String getDescription() {
            return "PGN Files (*.pgn)";
        }
        public boolean accept(java.io.File file) {
            return file.exists() && file.canRead() && (file.getName().endsWith(".pgn") || file.isDirectory());
        }
    }

    public void setHighlightValue(boolean b)
    {
        highlight=b;
    }

    public void assignCps()
    {
        for(int i=0;i<cbo.allPieces.size();i++)
        {
            CP cp1 = (CP) cbo.allPieces.get(i);
            for(int j=0;j<64;j++)
            {
                if(jtb[j].getValue()== cp1.getInitialPosition())
                {
                    jtb[j].setChessp(cp1);
                    break;
                }
            }
        }
    }

    public void ButtonSelected(MyJToggleButtonUI newButSel)
    {
        noOfButSelected++;

        /*  chk whose turn is it
            the selected button's chessp's color must be same else deselect that button
        */
        //one move done
        if(noOfButSelected==1)
        {
            try
            {
                if(newButSel.getChessp().getPieceColor().equals(whoseTurn))
                {
                    System.out.println("Good its a "+ whoseTurn +" piece");
                    prevButSel = newButSel;
                    if(highlight)
                    {
                        HighlightPossibleMoves(newButSel.getChessp());
                    }
                }
                else
                {
                    System.out.println("Its not a "+whoseTurn+" piece");
                    newButSel.setSelected(false);
                }
            }
            catch(NullPointerException npe)
            {
                System.out.println("Its not a "+whoseTurn+" piece");
                newButSel.setSelected(false);
            }
        }

        //two moves done
        if(noOfButSelected==2)
        {
            makeMove(newButSel,prevButSel,null);
        }

    }

    public void ButtonDeSelected(MyJToggleButtonUI jtbut)
    {
        noOfButSelected--;
        if(highlight)
        {
            undoHighlightPossibleMoves();
        }
    }

    public void HighlightPossibleMoves(CP p)
    {
        for(int i=0;i<p.movesPossible.size();i++)
        {
            for(int j=0;j<64;j++)
            {
                if(jtb[j].getValue()== p.movesPossible.get(i))
                {
                    jtb[j].setBackground(cHighlight);
                    break;
                }
            }
        }
    }

    public void undoHighlightPossibleMoves()
    {
        for(int j=0;j<64;j++)
        {
            jtb[j].setDefaultBgColor();
        }
    }

    private String chkIfPawnPromoted(MyJToggleButtonUI but)
    {
        CP pi = but.getChessp();
        System.out.println(pi.getPieceName());
        System.out.println(pi.getCurrentPosition()/10);
        if((pi.getPieceName().equals(PAWN))&&(pi.getCurrentPosition()>80)&&(pi.getCurrentPosition()<89))
        {
            System.out.println("Yes promote white pawn at "+pi.getCurrentPosition());
            PawnPromoDialogUI dia = new PawnPromoDialogUI(this,true,WHITE);
            dia.setVisible(true);
            System.out.println("selected is : "+ dia.selectedPiece);
            processPromotedPawn(but,dia.selectedPiece,WHITE);
            return dia.selectedPiece;
        }
        else if((pi.getPieceName().equals(PAWN))&&(pi.getCurrentPosition()>10)&&(pi.getCurrentPosition()<19))
        {
            System.out.println("Yes promote black pawn at "+pi.getCurrentPosition());
             PawnPromoDialogUI dia = new PawnPromoDialogUI(this,true,BLACK);
            dia.setVisible(true);
            System.out.println("selected is : "+ dia.selectedPiece);
            processPromotedPawn(but,dia.selectedPiece,BLACK);
            return dia.selectedPiece;
        }
        return null;
    }

    public void processPromotedPawn(MyJToggleButtonUI but, String piecename, String col)
    {
        cbo.allPieces.removeElement(but.getChessp());
        CP pPromo = new CP(this.cbo,piecename,col,but.getChessp().getCurrentPosition());
        cbo.allPieces.add(pPromo);
    }

    public boolean isEnPassentAttempted(CP oldCP, int newposition)
    {
        if(oldCP.getPieceName().equals(PAWN))
        {
            if( cbo.isCurPosOfSomePiece(newposition) == null)
            {
                int oldCPCurPos = oldCP.getCurrentPosition();
                int i = newposition-oldCPCurPos;
                //11,9,-11,-9
                if(i==11)
                {
                    completeEnPassentMove(oldCP.getCurrentPosition(),newposition,oldCPCurPos+1);
                    return true;
                }
                else if(i==9)
                {
                    completeEnPassentMove(oldCP.getCurrentPosition(),newposition,oldCPCurPos-1);
                    return true;
                }
                else if(i==-11)
                {
                    completeEnPassentMove(oldCP.getCurrentPosition(),newposition,oldCPCurPos-1);
                    return true;
                }
                else if(i==-9)
                {
                    completeEnPassentMove(oldCP.getCurrentPosition(),newposition,oldCPCurPos+1);
                    return true;
                }
            }
        }
        else
        {
            return false;
        }

        return false;
    }

    public void completeEnPassentMove(int currentPosition, int newposition, int captureButPos)
    {
        int butCurPos = currentPosition;
        int butNewPos = newposition;
        int capButPos = captureButPos;

        System.out.println(butCurPos+" "+butNewPos+ " "+capButPos);
        MyJToggleButtonUI mjtbbutCurPos=null;
        MyJToggleButtonUI mjtbbutNewPos=null;
        MyJToggleButtonUI mjtbcapButPos=null;

        for(int i=0;i<jtb.length;i++)
        {
            int curpos = jtb[i].getValue();

            if(curpos == butCurPos)
                mjtbbutCurPos = jtb[i];
            else if (curpos == butNewPos)
                mjtbbutNewPos = jtb[i];
            else if (curpos == capButPos)
                mjtbcapButPos = jtb[i];
        }

        //set the chesspiece of the buttons accordingly
        //but before setting the chesspiece for the enpassented/captured button
        //process the enpassented/captured piece
        CP p1 = mjtbcapButPos.getChessp();
        processCapturedPiece(p1);

        ///
        mjtbbutCurPos.getChessp().setCurrentPosition(newposition);
    }

    public boolean isCastlingAttempted(CP oldCP, int newposition)
    {
        castleWhichSide = null;
        if(oldCP.getPieceName().equals(KING))
        {
            int i = newposition-oldCP.getCurrentPosition();
            
            if(i==2)
            {
                completeCastlingMove(oldCP.getCurrentPosition(),"kingside");
                castleWhichSide = "KS";
                return true;
            }
            else if(i==-2)
            {
                completeCastlingMove(oldCP.getCurrentPosition(),"queenside");
                castleWhichSide = "QS";
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private void completeCastlingMove(int curPos, String whichSide)
    {
        int kCurrPos=0;
        int rCurrPos=0;
        int newkCurrPos=0;
        int newrCurrPos=0;

        MyJToggleButtonUI kCurBut=null;
        MyJToggleButtonUI rCurBut=null;

        if(whichSide.equals("kingside"))
        {
            kCurrPos = curPos;
            rCurrPos = curPos+3;
            newkCurrPos = curPos+2;
            newrCurrPos = curPos+1;
        }
        else
        {
            kCurrPos = curPos;
            rCurrPos = curPos-4;
            newkCurrPos = curPos-2;
            newrCurrPos = curPos-1;
        }

        for(int i=0;i<jtb.length;i++)
        {
            int curpos = jtb[i].getValue();

            if(curpos == kCurrPos)
                kCurBut = jtb[i];
            else if (curpos == rCurrPos)
                rCurBut = jtb[i];
        }
        ///
        kCurBut.getChessp().setCurrentPosition(newkCurrPos);
        rCurBut.getChessp().setCurrentPosition(newrCurrPos);
        ///
    }

    private void processCapturedPiece(CP p)
    {
        //yes piece is captured
        p.setCapturedFlag(true);
        cbo.capturedPieces.add(p);
        cbo.allPieces.removeElement(p);
    }

    public void makeMove(MyJToggleButtonUI newBut,MyJToggleButtonUI prevBut,Vector v)
    {
        /*
         * The format in data has to be fetched is
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fPos(11-88)
         *   tPos(11-88)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */
        String col=null;
        String name=null;
        int fPos = -1;
        int tPos = -1;
        boolean cap = false;
        String castle =null;
        String check = null;
        String promo =null;

        boolean chk = false;
        int newpos = newBut.getValue();
        CP prevButCP = prevBut.getChessp();

        col=prevButCP.getPieceColor();
        name=prevButCP.getPieceName();
        fPos=prevButCP.getCurrentPosition();
        tPos=newpos;


        if(prevButCP.isMovePossible(newpos))
        {
            System.out.println("Can be moved");
            cbo.allMoves.add(newpos);

            //chk if castling attempted
            if(isCastlingAttempted(prevButCP,newpos))
            {
                castle = castleWhichSide;
                System.out.println("Castling done");
            }
            //chk if enpassent attempted
            else if(isEnPassentAttempted(prevButCP,newpos))
            {
                cap=true;
                System.out.println("EnPassent done");
            }
            else
            {
                 // if pice is captured
                //remove tht piece from 'allPieces'
                //add tht piece to 'capturedPieces'
                CP cpCap = cbo.isCurPosOfSomePiece(newpos);
                if(cpCap!=null)
                {
                    cap= true;
                    processCapturedPiece(cpCap);
                }
                ///
                prevButCP.movesDone.add(newpos);
                prevButCP.setCurrentPosition(newpos);
                ////
                ///
                if(v==null)
                {
                    //chk pawn promo
                    promo = chkIfPawnPromoted(prevBut);
                }
                else if(v.get(8)!=null)
                {
                    this.processPromotedPawn(prevBut,(String) v.get(8), prevBut.getChessp().getPieceColor());
                }
                ///
            }
           
            //change 'whoseturn'
            if(whoseTurn.equals(WHITE))
            {
                whoseTurn = BLACK;
            }
            else
            {
                whoseTurn = WHITE;
            }

             //move done
            //so claculate all possible moves again
            //New
            cbo.calculateMoves(whoseTurn);

            if(cbo.isKingInCheck(whoseTurn))
            {
                if(whoseTurn.equals(WHITE))
                {
                    System.out.println("WHITE IS IN CHECK");
                }
                else
                {
                    System.out.println("BLACK IS IN CHECK");
                }
                chk=true;
                check="C";
            }
            else
            {
                cbo.calculateIfCastlingIsPossible();
            }

            //cbo.printAll();
            
            if(chk)
            {
                //if it return true then chkmate
                if(isCheckMate(whoseTurn))
                {
                    System.out.println("CHECKMATE..."+whoseTurn+" loses");
                    check="CM";
                }
            }
            else
            {
                //if it returns true then stalemate
                if(isCheckMate(whoseTurn))
                {
                    System.out.println("STALEMATE... its a draw");
                    check="SM";
                }
            }
           
            ///
            //change UI
            setPieceUI();
            ///

            if(v==null)
            {
                String m = calculatePGNMovesFromGUIMoves(col,name,fPos,tPos,cap,castle,check,promo);
                //System.out.println("Move is : "+ m );
                txtAreaMoves.append(m);
                if(col.equals(BLACK))
                    txtAreaMoves.append("\n");

                if(check!=null)
                {
                    if(check.equals("SM"))
                    {
                        //stalemate - draw
                        txtAreaMoves.append("1/2-1/2");
                        JOptionPane.showMessageDialog(this,"StaleMate.. its a draw","Game Over...",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(check.equals("CM"))
                    {
                        if(whoseTurn.equals(WHITE))
                        {
                            //white loses
                            txtAreaMoves.append("0-1");
                            JOptionPane.showMessageDialog(this,"CheckMate...\nBlack Wins!!!","Game Over...",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            //black loses
                            txtAreaMoves.append(" 1-0");

                            JOptionPane.showMessageDialog(this,"CheckMate...\nWhite Wins!!!","Game Over...",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Its not a valid move");
            newBut.setSelected(false);
            prevBut.setSelected(false);
        }
    }

    public String calculatePGNMovesFromGUIMoves(String col, String name, int fPos, int tPos, Boolean cap, String castle, String chk, String promo)
    {
        /*
         * The format is
         * [ color(B/W),
         *   name(P/R/N/B/Q/K),
         *   fPos(11-88)
         *   tPos(11-88)
         *   cap(T/F),
         *   castle(KS/QS/null),
         *   chk(C/CM/SM/null)
         *   promo(R/N/B/Q/null)
         * ]
         *
         */
        AllPGNGames g = new AllPGNGames(this,null);
            
        System.out.println("col is : "+ col +"name is : "+ name +"fPos is : "+ fPos +"tPos is : "+ tPos +"cap is : "+ cap +"castle is : "+ castle +"chk is : "+ chk +"promo is : "+ promo);
        String s = "";
        if(col.equals(WHITE))
        {
            s = s+((cbo.allMoves.size()/2)+1)+". ";
        }
        else
        {
            s=s+" ";
        }
        if(castle!=null)
        {
            if(castle.equals("KS"))
            {
                s=s+"O-O";
                if(chk!=null)
                {
                    if(chk.equals("C"))
                    {
                        s=s+"+";
                    }
                    else if(chk.equals("CM"))
                    {
                        s=s+"#";
                    }
                }
            }
            else if(castle.equals("QS"))
            {
                s=s+"O-O-O";
                if(chk!=null)
                {
                    if(chk.equals("C"))
                    {
                        s=s+"+";
                    }
                    else if(chk.equals("CM"))
                    {
                        s=s+"#";
                    }
                }
            }
            return s;
        }

        if(promo!=null)
        {
            if(cap)
            {
                s=s+g.convertNumValToAlphaCol(fPos%10)+"x"+g.convertNumValToAlphaCol(tPos%10)+(tPos/10)+"=";
            }
            else
            {
                s=s+g.convertNumValToAlphaCol(tPos%10)+(tPos/10)+"=";
            }
            if(promo.equals(QUEEN))
                s=s+"Q";
            else if(promo.equals(ROOK))
                s=s+"R";
            else if(promo.equals(KNIGHT))
                s=s+"N";
            else if(promo.equals(BISHOP))
                s=s+"B";

            if(chk!=null)
            {
                if(chk.equals("C"))
                {
                    s=s+"+";
                }
                else if(chk.equals("CM"))
                {
                    s=s+"#";
                }
            }
            return s;
        }
        if(name.equals(PAWN))
        {
            if(cap)
            {
                s=s+g.convertNumValToAlphaCol(fPos%10)+"x"+g.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            else
            {
                s=s+g.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            if(chk!=null)
            {
                if(chk.equals("C"))
                {
                    s=s+"+";
                }
                else if(chk.equals("CM"))
                {
                    s=s+"#";
                }
            }
            return s;
        }
        else
        {
             if(name.equals(QUEEN))
                s=s+"Q";
            else if(name.equals(ROOK))
                s=s+"R";
            else if(name.equals(KNIGHT))
                s=s+"N";
            else if(name.equals(BISHOP))
                s=s+"B";
            else
                s=s+"K";
            if(cap)
            {
                s=s+g.convertNumValToAlphaCol(fPos%10)+(fPos/10)+"x"+g.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            else
            {
                s=s+g.convertNumValToAlphaCol(fPos%10)+(fPos/10)+g.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            if(chk!=null)
            {
                if(chk.equals("C"))
                {
                    s=s+"+";
                }
                else if(chk.equals("CM"))
                {
                    s=s+"#";
                }
            }
            return s;
        }
    }

    private void reverseBoard(String col)
    {
        chessBoardContainer.removeAll();
        chessBoardContainer.revalidate();

        if(col.equals(BLACK))
        {
            chessBoard.removeAll();
            chessBoard.revalidate();
            for(int i=63;i>=0;i--)
            {
                chessBoard.add(jtb[i]);
            }
            chessBoardContainer.add(addA_Hpanel(false),BorderLayout.NORTH);
        }
        else
        {
            chessBoard.removeAll();
            chessBoard.revalidate();
            for(int i=0;i<64;i++)
            {
                chessBoard.add(jtb[i]);
            }
            chessBoardContainer.add(addA_Hpanel(true),BorderLayout.NORTH);
        }
        chessBoard.repaint();

        chessBoardContainer.add(chessBoard,BorderLayout.CENTER);
        chessBoardContainer.repaint();

    }

    private void saveContentsToPGNFile(String file) throws IOException
    {
        FileWriter fr     = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();

        br.write("[Event \""+TITLE+"\"]\n");
        br.write("[Date \""+df.format(d)+"\"]\n");
        br.write("[White \"Suhas Bharadwaj\"]\n");
        br.write("[Black \"Player1\"]\n");
        br.write("[Result \"?\"]\n");

        String[] mov = txtAreaMoves.getText().split("\n");

        for(int i=0;i<mov.length;i++)
        {
            br.write(mov[i]);
            br.write(" ");
            if((i%5==0)&&(i!=0))
            {
                br.newLine();
            }
        }
        br.close();

    }

    public boolean isCheckMate(String col)
    {
        for(int i=0;i<cbo.allPieces.size();i++)
        {
            CP c = (CP) cbo.allPieces.get(i);
            if(col.equals(c.getPieceColor()))
            {
                if(c.movesPossible.size()>0)
                    return false;
            }
        }
        return true;
    }

    public void setPieceUI()
    {
        for(int i=0;i<jtb.length;i++)
        {
            jtb[i].setChessp(null);
            jtb[i].setIcon(null);
            jtb[i].setSelected(false);
        }
        for(int i=0;i<cbo.allPieces.size();i++)
        {
            int pos = -1;
            CP p = (CP) cbo.allPieces.get(i);
            int pPos = p.getCurrentPosition();
            String pName = p.getPieceName();
            String pColor = p.getPieceColor();

            if(pPos>80 && pPos<90)
            {
                //-81
                pos=pPos-81;
            }
            else if(pPos>70 && pPos<80)
            {
                //-63
                pos=pPos-63;
            }
            else if(pPos>60 && pPos<70)
            {
                //-45
                pos=pPos-45;
            }
            else if(pPos>50 && pPos<60)
            {
                //-27
                pos=pPos-27;
            }
            else if(pPos>40 && pPos<50)
            {
                //-9
                pos=pPos-9;
            }
            else if(pPos>30 && pPos<40)
            {
                //9
                pos=pPos+9;
            }
            else if(pPos>20 && pPos<30)
            {
                //27
                pos=pPos+27;
            }
            else if(pPos>10 && pPos<20)
            {
                //45
                pos=pPos+45;
            }

            jtb[pos].setChessp(p);

            if((pName.equals(ROOK)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whiteRook)));
            else if((pName.equals(KNIGHT)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whiteKnight)));
            else if((pName.equals(BISHOP)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whiteBishop)));
            else if((pName.equals(QUEEN)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whiteQueen)));
            else if((pName.equals(KING)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whiteKing)));
            else if((pName.equals(PAWN)) && pColor.equals(WHITE))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(whitePawn)));
            else if((pName.equals(ROOK)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackRook)));
            else if((pName.equals(KNIGHT)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackKnight)));
            else if((pName.equals(BISHOP)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackBishop)));
            else if((pName.equals(QUEEN)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackQueen)));
            else if((pName.equals(KING)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackKing)));
            else if((pName.equals(PAWN)) && pColor.equals(BLACK))
                jtb[pos].setIcon(new ImageIcon(getClass().getResource(blackPawn)));
        }
    }
}
