/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package c.UI;

import c.Constants.CConst;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


/**
 * Class Name - PawnPromoDialogUI
 * Description - 
 *
 * @author Suhas Bharadwaj
 */
public class PawnPromoDialogUI extends JDialog implements CConst
{
    public String selectedPiece = QUEEN;
    public  JButton bB;
    public  JButton bN;
    public  JButton bQ;
    public  JButton bR;
    public  JLabel jLabel1;

    public PawnPromoDialogUI (java.awt.Frame parent, boolean modal,String col)
    {
        super(parent, modal);
        initComponents();
        if(col.equals(WHITE))
        {
            bQ.setIcon(new ImageIcon(getClass().getResource(whiteQueen)));
            bR.setIcon(new ImageIcon(getClass().getResource(whiteRook)));
            bN.setIcon(new ImageIcon(getClass().getResource(whiteKnight)));
            bB.setIcon(new ImageIcon(getClass().getResource(whiteBishop)));
        }
        else
        {
            bQ.setIcon(new ImageIcon(getClass().getResource(blackQueen)));
            bR.setIcon(new ImageIcon(getClass().getResource(blackRook)));
            bN.setIcon(new ImageIcon(getClass().getResource(blackKnight)));
            bB.setIcon(new ImageIcon(getClass().getResource(blackBishop)));
        }
    }

    public void initComponents()
    {
        bQ = new  JButton();
        bR = new  JButton();
        bN = new  JButton();
        bB = new  JButton();
        jLabel1 = new  JLabel("Promote pawn to: ");

        JPanel mainPanel= new JPanel(new GridLayout(2,1));
        JPanel jL = new JPanel(new GridLayout(1,1));
        JPanel butPanel = new JPanel(new FlowLayout());

        jL.add(jLabel1);
        butPanel.add(bQ);
        butPanel.add(bR);
        butPanel.add(bN);
        butPanel.add(bB);

        mainPanel.add(jL);
        mainPanel.add(butPanel);

        bQ.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                selectedPiece = QUEEN;
                dispose();
            }
        });

        bR.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                 selectedPiece = ROOK;
            dispose();
            }
        });

        bN.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                selectedPiece = KNIGHT;
                 dispose();
            }
        });

        bB.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                selectedPiece = BISHOP;
                dispose();
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(50,50,screenSize.width,screenSize.height);

        setSize(400,200);
        setContentPane(mainPanel);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pawn Promotion");

    }
}
