/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package c.UI;

import c.Constants.CConst;
import c.NewPkg.readpgn;
import c.main.CB;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

/**
 * Class Name - chessBoardUI
 * Description - 
 *
 * @author suhas
 */
public class chessBoardUI extends JFrame implements ActionListener,ItemListener,CConst
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
    public myJToggleButton jtb[];
    public myJToggleButton prevButSel;


    public String castleWhichSide = null;
    public int noOfButSelected=0;
    public String whoseTurn = WHITE;
    boolean highlight = true;
    public readpgn t = null;
    public String turn = null;
    public int noOfMoves = 0;


    public chessBoardUI(CB b)
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
        assignCps();

    }

    public JPanel processCenterPanel()
    {
        cP.add(resetChessContainerUI(),BorderLayout.CENTER);
        cP.add(createButPanel(),BorderLayout.SOUTH);

        return cP;
    }

    public JPanel processSouthPanel()
    {
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
        JSplitPane jSplitPane1 = new JSplitPane();
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Moves"));
        
        txtAreaMoves.setColumns(15);
        txtAreaMoves.setEditable(false);
        txtAreaMoves.setLineWrap(true);
        txtAreaMoves.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAreaMoves);

        JScrollPane jpM = new JScrollPane();
        jpM.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Games"));

        jSplitPane1.setTopComponent(jpM);
        jSplitPane1.setBottomComponent(jScrollPane1);

        eP.add(jSplitPane1);

        return eP;
        
    }

    public JPanel processWestPanel()
    {
       /* wP.setLayout(new GridLayout(2,1));
        //JScrollPane jpM = new JScrollPane();
        //jpM.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Captured Pieces"));
        wP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Captured Pieces"));
        wP.add(whiteCapPanel);
        wP.add(blackCapPanel);

        //wP.add(jpM);
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
        //miAbout.addActionListener(this);
       
        
       
        menuH.add(miHelp);
        menuH.add(miAbout);
      
        
        menuBar.add(menuH);
        return menuBar;
    }

    public JPanel resetChessContainerUI()
    {
        jtb = new myJToggleButton[64];
        int c = 0;
        int counter=0;
        int mtbVal=81;
        for(int i=0;i<64;i++)
        {
            jtb[i] = new myJToggleButton(this);
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

        //Add black pawns
        for(int i=8;i<=15;i++)
        {
            jtb[i].setIcon(new ImageIcon(getClass().getResource(blackPawn)));
        }

        //Add White pawns
        for(int i=48;i<=55;i++)
        {
            jtb[i].setIcon(new ImageIcon(getClass().getResource(whitePawn)));
        }

        //Addblack rooks
        jtb[0].setIcon(new ImageIcon(getClass().getResource(blackRook)));
        jtb[7].setIcon(new ImageIcon(getClass().getResource(blackRook)));

        //Add black knights
        jtb[1].setIcon(new ImageIcon(getClass().getResource(blackKnight)));
        jtb[6].setIcon(new ImageIcon(getClass().getResource(blackKnight)));

        //add black bishops
        jtb[2].setIcon(new ImageIcon(getClass().getResource(blackBishop)));
        jtb[5].setIcon(new ImageIcon(getClass().getResource(blackBishop)));

        //add black queen
        jtb[3].setIcon(new ImageIcon(getClass().getResource(blackQueen)));

        //add black king
        jtb[4].setIcon(new ImageIcon(getClass().getResource(blackKing)));


        //add white rooks
        jtb[56].setIcon(new ImageIcon(getClass().getResource(whiteRook)));
        jtb[63].setIcon(new ImageIcon(getClass().getResource(whiteRook)));

        //add white knights
        jtb[57].setIcon(new ImageIcon(getClass().getResource(whiteKnight)));
        jtb[62].setIcon(new ImageIcon(getClass().getResource(whiteKnight)));

        //add white bishops
        jtb[58].setIcon(new ImageIcon(getClass().getResource(whiteBishop)));
        jtb[61].setIcon(new ImageIcon(getClass().getResource(whiteBishop)));

        //add white queen
        jtb[59].setIcon(new ImageIcon(getClass().getResource(whiteQueen)));

        //add white king
        jtb[60].setIcon(new ImageIcon(getClass().getResource(whiteKing)));
 
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
        first.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);
        last.addActionListener(this);

        butJPanel.add(first);
        butJPanel.add(prev);
        butJPanel.add(next);
        butJPanel.add(last);
        return butJPanel;
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //createAndShowGUI();
                //new chessBoardUI(null);
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Next"))
        {
             nextOrLastButtonIsClicked((JButton) e.getSource(),false);
        }
        else if(e.getActionCommand().equals("Last"))
        {
             nextOrLastButtonIsClicked((JButton) e.getSource(),true);
             next.setEnabled(false);
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
                t = new readpgn(this,fileChooser.getSelectedFile());
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
                    Logger.getLogger(chessBoardUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

    public void nextOrLastButtonIsClicked(JButton j,boolean loop)
    {
        while(true)
        {
            noOfMoves++;

            String s = "";
            try
            {
                if(turn.equals(WHITE))
                {
                s = t.convertPGNMoveToGUIFormat(WHITE);
                turn = BLACK;
                }
                else if(turn.equals(BLACK))
                {
                s = t.convertPGNMoveToGUIFormat(BLACK);
                turn = WHITE;
                }
            }
            catch(NullPointerException n)
            {
                s = t.convertPGNMoveToGUIFormat(WHITE);
                turn = BLACK;
            }
            txtAreaMoves.append(s);
            if(noOfMoves == (t.allMoves.size()-1))
            {
                j.setEnabled(false);
                last.setEnabled(false);
                txtAreaMoves.append((String) t.allMoves.get(t.allMoves.size()-1));
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

    public void ButtonSelected(myJToggleButton newButSel)
    {
        noOfButSelected++;

        /*  chk whose turn is it
            the selected button's chessp's color must be same else deselect that button
        */
        //one move done
        if(noOfButSelected==1)
        {
            if(whoseTurn.equals(WHITE))
            {
                try
                {
                    if(newButSel.getChessp().getPieceColor().equals(WHITE))
                    {
                        System.out.println("Good its a white piece");
                        prevButSel = newButSel;
                        if(highlight)
                        {
                            HighlightPossibleMoves(newButSel.getChessp());
                        }
                    }
                    else
                    {
                        System.out.println("Its not a white piece");
                        newButSel.setSelected(false);
                    }
                }
                catch(NullPointerException npe)
                {
                    System.out.println("Its not a white piece");
                    newButSel.setSelected(false);
                }
            }
            else
            {
                try
                {
                    if(newButSel.getChessp().getPieceColor().equals(BLACK))
                    {
                        System.out.println("Good its a black piece");
                        prevButSel = newButSel;
                        if(highlight)
                        {
                            HighlightPossibleMoves(newButSel.getChessp());
                        }
                    }
                    else
                    {
                        System.out.println("Its not a black piece");
                        newButSel.setSelected(false);
                    }
                }
                catch(NullPointerException npe)
                {
                    System.out.println("Its not a black piece");
                    newButSel.setSelected(false);
                }
            }
        }

        //two moves done
        if(noOfButSelected==2)
        {
            makeMove(newButSel,prevButSel,null);
        }

    }

    public void ButtonDeSelected(myJToggleButton jtbut)
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

    private String chkIfPawnPromoted(myJToggleButton but)
    {
        CP pi = but.getChessp();
        System.out.println(pi.getPieceName());
        System.out.println(pi.getCurrentPosition()/10);
        if((pi.getPieceName().equals(PAWN))&&(pi.getCurrentPosition()>80)&&(pi.getCurrentPosition()<89))
        {
            System.out.println("Yes promote white pawn");
            pawnPromoDialogUI dia = new pawnPromoDialogUI(this,true,WHITE);
            dia.setVisible(true);
            System.out.println("selected is : "+ dia.selectedPiece);
            processPromotedPawn(but,dia.selectedPiece,WHITE);
            return dia.selectedPiece;
        }
        else if((pi.getPieceName().equals(PAWN))&&(pi.getCurrentPosition()>10)&&(pi.getCurrentPosition()<19))
        {
            System.out.println("Yes promote black pawn");
             pawnPromoDialogUI dia = new pawnPromoDialogUI(this,true,BLACK);
            dia.setVisible(true);
            System.out.println("selected is : "+ dia.selectedPiece);
            processPromotedPawn(but,dia.selectedPiece,BLACK);
            return dia.selectedPiece;
        }
        return null;
    }

    public void processPromotedPawn(myJToggleButton but, String piecename, String col)
    {
        cbo.allPieces.removeElement(but.getChessp());
        CP pPromo = new CP(this.cbo,piecename,col,but.getValue());
        cbo.allPieces.add(pPromo);
        but.setChessp(pPromo);

        if((piecename.equals(ROOK)) && col.equals(WHITE))
            but.setIcon(new ImageIcon(getClass().getResource(whiteRook)));
        else if((piecename.equals(KNIGHT)) && col.equals(WHITE))
            but.setIcon(new ImageIcon(getClass().getResource(whiteKnight)));
        else if((piecename.equals(BISHOP)) && col.equals(WHITE))
            but.setIcon(new ImageIcon(getClass().getResource(whiteBishop)));
        else if((piecename.equals(QUEEN)) && col.equals(WHITE))
            but.setIcon(new ImageIcon(getClass().getResource(whiteQueen)));
        else if((piecename.equals(ROOK)) && col.equals(BLACK))
            but.setIcon(new ImageIcon(getClass().getResource(blackRook)));
        else if((piecename.equals(KNIGHT)) && col.equals(BLACK))
            but.setIcon(new ImageIcon(getClass().getResource(blackKnight)));
        else if((piecename.equals(BISHOP)) && col.equals(BLACK))
            but.setIcon(new ImageIcon(getClass().getResource(blackBishop)));
        else if((piecename.equals(QUEEN)) && col.equals(BLACK))
            but.setIcon(new ImageIcon(getClass().getResource(blackQueen)));

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

    public boolean isCastlingAttempted(CP oldCP, int newposition)
    {
        castleWhichSide = null;
        if(oldCP.getPieceName().equals(KING))
        {
            int i = newposition-oldCP.getCurrentPosition();
            //int captureButPos = oldCP.getCurrentPosition()-newposition;
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

        myJToggleButton kCurBut=null;
        myJToggleButton rCurBut=null;
        myJToggleButton newkCurBut=null;
        myJToggleButton newrCurBut=null;


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
            else if (curpos == newkCurrPos)
                newkCurBut = jtb[i];
            else if (curpos == newrCurrPos)
                newrCurBut = jtb[i];
        }


        //complete button switch by changing icons and deselecting it
        newkCurBut.setIcon(kCurBut.getIcon());
        newrCurBut.setIcon(rCurBut.getIcon());
        kCurBut.setIcon(null);
        rCurBut.setIcon(null);
        newkCurBut.setSelected(false);
        newrCurBut.setSelected(false);
        kCurBut.setSelected(false);
        rCurBut.setSelected(false);

        //set the chesspiece of the buttons accordingly
        newkCurBut.setChessp(kCurBut.getChessp());
        newrCurBut.setChessp(rCurBut.getChessp());
        kCurBut.setChessp(null);
        rCurBut.setChessp(null);

        //add the pos to movesdone
        newkCurBut.getChessp().movesDone.add(newkCurrPos);

        //set the piece new position as current pos
        newkCurBut.getChessp().setCurrentPosition(newkCurrPos);
        newrCurBut.getChessp().setCurrentPosition(newrCurrPos);

    }

    public void completeEnPassentMove(int currentPosition, int newposition, int captureButPos)
    {
        int butCurPos = currentPosition;
        int butNewPos = newposition;
        int capButPos = captureButPos;

        System.out.println(butCurPos+" "+butNewPos+ " "+capButPos);
        myJToggleButton mjtbbutCurPos=null;
        myJToggleButton mjtbbutNewPos=null;
        myJToggleButton mjtbcapButPos=null;

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

        //complete button switch by changing icons and deselecting it
        mjtbbutNewPos.setIcon(mjtbbutCurPos.getIcon());
        mjtbbutCurPos.setIcon(null);
        mjtbcapButPos.setIcon(null);
        mjtbbutNewPos.setSelected(false);
        mjtbbutCurPos.setSelected(false);
        mjtbcapButPos.setSelected(false);

        //set the chesspiece of the buttons accordingly
        //but before setting the chesspiece for the enpassented/captured button
        //process the enpassented/captured piece
        CP p1 = mjtbcapButPos.getChessp();
        processCapturedPiece(p1);

        mjtbbutNewPos.setChessp(mjtbbutCurPos.getChessp());
        mjtbbutCurPos.setChessp(null);
        mjtbcapButPos.setChessp(null);

        //add the pos to movesdone
        mjtbbutNewPos.getChessp().movesDone.add(butNewPos);

        //set the piece new position as current pos
        mjtbbutNewPos.getChessp().setCurrentPosition(butNewPos);

    }

    private void processCapturedPiece(CP p)
    {
        //yes piece is captured
        p.setCapturedFlag(true);
        cbo.capturedPieces.add(p);
        cbo.allPieces.removeElement(p);
        if(p.getPieceColor().equals(WHITE))
        {
            String s = null;
            JLabel lb = new JLabel();
            if(p.getPieceName().equals(PAWN))
                s = whitePawn;
            if(p.getPieceName().equals(ROOK))
                s = whiteRook;
            if(p.getPieceName().equals(KNIGHT))
                s = whiteKnight;
            if(p.getPieceName().equals(BISHOP))
                s = whiteBishop;
            if(p.getPieceName().equals(QUEEN))
                s = whiteQueen;
            lb.setIcon(new ImageIcon(getClass().getResource(s)));
            whiteCapPanel.add(lb);
        }
        else
        {
            String s = null;
            JLabel lb = new JLabel();
            if(p.getPieceName().equals(PAWN))
                s = blackPawn;
            if(p.getPieceName().equals(ROOK))
                s = blackRook;
            if(p.getPieceName().equals(KNIGHT))
                s = blackKnight;
            if(p.getPieceName().equals(BISHOP))
                s = blackBishop;
            if(p.getPieceName().equals(QUEEN))
                s = blackQueen;
            lb.setIcon(new ImageIcon(getClass().getResource(s)));
            blackCapPanel.add(lb);
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

    public void makeMove(myJToggleButton newBut,myJToggleButton prevBut,Vector v)
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
        boolean chk = false;
        String promo =null;

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

                //complete button switch by changing icons and deselecting it
                newBut.setIcon(prevBut.getIcon());
                prevBut.setIcon(null);
                newBut.setSelected(false);
                prevBut.setSelected(false);

                //set the chesspiece of the buttons accordingly
                newBut.setChessp(prevButCP);
                prevBut.setChessp(null);


                //add the pos to movesdone
                newBut.getChessp().movesDone.add(newpos);

                //set the piece new position as current pos
                newBut.getChessp().setCurrentPosition(newpos);

                if(v==null)
                {
                    //chk pawn promo
                    promo = chkIfPawnPromoted(newBut);
                }
                else if(v.get(8)!=null)
                {
                    this.processPromotedPawn(newBut,(String) v.get(8), newBut.getChessp().getPieceColor());
                }
            }
            //move done
            //so claculate all possible moves again
            //cbo.calculateAllPossibleMoves();
             //castling
            //cbo.calculateIfCastlingIsPossible();

            //change 'whoseturn'
            if(whoseTurn.equals(WHITE))
            {
                whoseTurn = BLACK;
            }
            else
            {
                whoseTurn = WHITE;
            }

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
            }
            else
            {
                cbo.calculateIfCastlingIsPossible();
            }

            //todo: cal chkmate and stalemate
            cbo.printAll();
            
            if(v==null)
            {
                String m = calculatePGNMovesFromGUIMoves(col,name,fPos,tPos,cap,castle,chk,promo);
                //System.out.println("Move is : "+ m );
                txtAreaMoves.append(m);
                if(col.equals(BLACK))
                    txtAreaMoves.append("\n");

            }
        }
        else
        {
            System.out.println("Its not a valid move");
            newBut.setSelected(false);
            prevBut.setSelected(false);
        }
    }

    public String calculatePGNMovesFromGUIMoves(String col, String name, int fPos, int tPos, Boolean cap, String castle, Boolean chk, String promo)
    {
        /*
         * The format is
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
        t=new readpgn(this,null);
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
                if(chk)
                {
                    s=s+"+";
                }
            }
            else if(castle.equals("QS"))
            {
                s=s+"O-O-O";
                if(chk)
                {
                    s=s+"+";
                }
            }
            return s;
        }

        if(promo!=null)
        {
            if(cap)
            {
                s=s+t.convertNumValToAlphaCol(fPos%10)+"x"+t.convertNumValToAlphaCol(tPos%10)+(tPos/10)+"=";
            }
            else
            {
                s=s+t.convertNumValToAlphaCol(tPos%10)+(tPos/10)+"=";
            }
            if(promo.equals(QUEEN))
                s=s+"Q";
            else if(promo.equals(ROOK))
                s=s+"R";
            else if(promo.equals(KNIGHT))
                s=s+"N";
            else if(promo.equals(BISHOP))
                s=s+"B";

            if(chk)
            {
                s=s+"+";
            }
            return s;
        }
        if(name.equals(PAWN))
        {
            if(cap)
            {
                s=s+t.convertNumValToAlphaCol(fPos%10)+"x"+t.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            else
            {
                s=s+t.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            if(chk)
            {
                s=s+"+";
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
                s=s+t.convertNumValToAlphaCol(fPos%10)+(fPos/10)+"x"+t.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            else
            {
                s=s+t.convertNumValToAlphaCol(fPos%10)+(fPos/10)+t.convertNumValToAlphaCol(tPos%10)+(tPos/10);
            }
            if(chk)
            {
                s=s+"+";
            }
            return s;
        }
    }

    private void saveContentsToPGNFile(String file) throws IOException
    {
        FileWriter fr     = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();

        br.write("[Event \""+TITLE+"\"]\n");
        br.write("[Date \""+df.format(d)+"\"]\n");
        br.write("[White \"Suhas\"]\n");
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

}
