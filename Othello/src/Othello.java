/*
 * Othello.java
 *
 * Created on Jan 20, 2011, 4:35:24 PM
 *
 * With Java version 1.6.0_22
 *
 * By Michael Boselowitz
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class Othello extends JApplet
{
    private OthelloPanel gameBoard;
    private JFrame theFrame;
    private Boolean gamePlay = false, chat = false;
    private int me, turn = 0;
    private Socket connection;
    private BufferedReader read;
    private PrintWriter write;  
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ChatJTextField;
    private javax.swing.JScrollPane ChatTextJScrollPane;
    private javax.swing.JTextArea ChatTextJTextArea;
    private javax.swing.JMenuItem ExitJMenuItem;
    private javax.swing.JMenu FileJMenu;
    private javax.swing.JLabel PlayerJLabel;
    private javax.swing.JLabel PlayerLabelJLabel;
    private javax.swing.JButton SendJButton;
    private javax.swing.JLabel StatusJLabel;
    private javax.swing.JLabel StatusLabelJLabel;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
	
    @Override
    public void init()
    {
        gameBoard = new OthelloPanel();
        theFrame = new JFrame("Othello");
        theFrame.add(gameBoard);
        gameBoard.setVisible(false);
        initComponents();
        SendJButton.addActionListener(new SendButtonActionListener());
        ExitJMenuItem.addActionListener(new MenuItemActionListener());
        Thread networking = new Thread(new NetworkThread());
        networking.start();
        theFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        theFrame.addWindowListener(
                new WindowAdapter()
                {
            @Override
                    public void windowClosing(WindowEvent e)
                    {
                        write.println(OC.CMD);
                        write.println("STOP");
                        theFrame.setVisible(false);
                        setVisible(true);
                        setSize(150,150);
                        add(new JLabel("Program Finished"), BorderLayout.CENTER);
                    }
                });
    }
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
		
        ChatJTextField = new javax.swing.JTextField();
        ChatTextJScrollPane = new javax.swing.JScrollPane();
        ChatTextJTextArea = new javax.swing.JTextArea();
        SendJButton = new javax.swing.JButton();
        PlayerLabelJLabel = new javax.swing.JLabel();
        PlayerJLabel = new javax.swing.JLabel();
        StatusLabelJLabel = new javax.swing.JLabel();
        StatusJLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileJMenu = new javax.swing.JMenu();
        ExitJMenuItem = new javax.swing.JMenuItem();
		
        theFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        theFrame.setBackground(new java.awt.Color(204, 204, 204));
        theFrame.setResizable(false);
		
        ChatTextJTextArea.setColumns(20);
        ChatTextJTextArea.setEditable(false);
        ChatTextJTextArea.setRows(3);
        ChatTextJTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ChatTextJTextArea.setFocusable(false);
        ChatTextJScrollPane.setViewportView(ChatTextJTextArea);
		
        SendJButton.setText("Send");
		
        PlayerLabelJLabel.setText("You are player: ");
		
        StatusLabelJLabel.setText("Status info:");
		
        FileJMenu.setText("File");
		
        ExitJMenuItem.setText("Exit");
        FileJMenu.add(ExitJMenuItem);
		
        jMenuBar1.add(FileJMenu);
		
        theFrame.setJMenuBar(jMenuBar1);
		
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(theFrame.getContentPane());
        theFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
								  layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								  .add(layout.createSequentialGroup()
									   .addContainerGap()
									   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
											.add(layout.createSequentialGroup()
												 .add(PlayerLabelJLabel)
												 .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												 .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
													  .add(PlayerJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
													  .add(StatusJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
											.add(StatusLabelJLabel))
									   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
											.add(layout.createSequentialGroup()
												 .add(ChatJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 163, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												 .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												 .add(SendJButton))
											.add(ChatTextJScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
									   .addContainerGap())
								  );
        layout.setVerticalGroup(
								layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
									 .addContainerGap(545, Short.MAX_VALUE)
									 .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										  .add(layout.createSequentialGroup()
											   .add(ChatTextJScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
											   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
											   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
													.add(SendJButton)
													.add(ChatJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
										  .add(layout.createSequentialGroup()
											   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
													.add(PlayerLabelJLabel)
													.add(PlayerJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
											   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
											   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
													.add(StatusLabelJLabel)
													.add(StatusJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
									 .addContainerGap())
								);
		
        theFrame.pack();
    }// </editor-fold>//GEN-END:initComponents

    private synchronized void setTurn(int val)
    {
        turn = val;
    }
    private synchronized int getTurn()
    {
        return turn;
    }
    private synchronized void flipTurn()
    {
        if(turn == 0)
            turn = 1;
        else if(turn == 1)
            turn = 0;
        else
            turn = -1;
    }
    private int opponent()
    {
        if(me == 0)
            return 1;
        else if(me == 1)
            return 0;
        else
            return -1;
    }
    private class NetworkThread implements Runnable
    {
        public void run()
        {
            try
            {
                connection = new Socket(InetAddress.getByName(
                        JOptionPane.showInputDialog(null, "Enter Server Name")), OC.PORT);
                read = new BufferedReader(new BufferedReader(new InputStreamReader(connection.getInputStream())));
                write = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), true);
                me = Integer.parseInt(read.readLine());
                if(me == 0)
                    PlayerJLabel.setText("Black");
                else if(me == 1)
                    PlayerJLabel.setText("White");
                else
                    PlayerJLabel.setText("Error");

                theFrame.setVisible(true);

                while(true)
                {
                    String input = read.readLine();
                    if(input.equals(OC.INFO))
                    {
                        StatusJLabel.setText(read.readLine());
                    }
                    else if(input.equals(OC.CMD))
                    {
                        input = read.readLine();
                        if(input.equals("START"))
                        {
                            gameBoard.setVisible(true);
                            gamePlay = true;
                            chat = true;
                        }
                        else if(input.equals("STOP"))
                        {
                            gamePlay = false;
                            chat = false;
                            JOptionPane.showMessageDialog(null, "Your opponent quit!");
                            System.exit(0);
                        }
                    }
                    else if(input.equals(OC.MOVE))
                    {
                        read.readLine();
                    }
                    else if(input.equals(OC.FLIP))
                    { 
                        ArrayList<Point> flips = new ArrayList<Point>();
                        String[] in = read.readLine().split("[|]");
                        for(int i=0; i<in.length; i++)
                            flips.add(new Point(Integer.parseInt(in[i].split("[,]")[0]),
                                    Integer.parseInt(in[i].split("[,]")[1])));
                        for(int i=0; i<flips.size(); i++)
                            gameBoard.setBoard((int)flips.get(i).getX(), (int)flips.get(i).getY(), opponent());
                        Thread flipThread = new Thread(new OthelloFlip(flips, opponent()));
                        flipThread.start();
                    }
                    else if(input.equals(OC.TURN))
                    {
                        ArrayList<Point> validMoves = gameBoard.findMove();
                        if(validMoves.isEmpty())
                        {
                            read.readLine();    //Clear buffer
                            write.println(OC.TURN);
                            write.println(getTurn());
                        }
                        else
                            setTurn(Integer.parseInt(read.readLine()));
                    }
                    else if(input.equals(OC.OVER))
                    {
                        gamePlay = false;
                        StatusJLabel.setText("Game Over!");
                        JOptionPane.showMessageDialog(null, read.readLine());
                    }
                    else if(input.equals(OC.MSG))
                    {
                        ChatTextJTextArea.append(read.readLine() + "\n");
                    }
                    else
                        System.err.println("Did not understand message");
                }
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
                JOptionPane.showMessageDialog(null, "Problems with connection!");
                System.exit(-1);
            }
        }
    }
    public class OthelloFlip implements Runnable
    {
        ArrayList<Point> flips;
        int who;
        public OthelloFlip(ArrayList<Point> f, int player)
        {
            flips = f;
            who = player;
        }
        public void run()
        {
            for(int i=0; i<flips.size(); i++)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException e){}
                gameBoard.paintboard[(int)flips.get(i).getX()][(int)flips.get(i).getY()] = who;
                theFrame.repaint();
            }
            if(gameBoard.boardFull()) //Count
            {
                gamePlay = false;
                int[] count = gameBoard.boardCount();
                StatusJLabel.setText("Game Over!");
                if(count[2] == 0)
                    JOptionPane.showMessageDialog(null, 
                            "Black: " + count[0] + " White: " + count[1] + " Black Wins!");
                else if(count[2] == 1)
                    JOptionPane.showMessageDialog(null, 
                            "Black: " + count[0] + " White: " + count[1] + " White Wins!");
                else
                    JOptionPane.showMessageDialog(null, 
                            "Black: " + count[0] + " White: " + count[1] + " Tie Game!");  
            }
            else if(gameBoard.allTaken() != -1) //Winner
            {
                gamePlay = false;
                StatusJLabel.setText("Game Over!");
                if(gameBoard.allTaken() == 0)
                    JOptionPane.showMessageDialog(null, 
                            "Black Wins! Clean Sweep!");
                else if(gameBoard.allTaken() == 1)
                    JOptionPane.showMessageDialog(null, 
                            "White Wins! Clean Sweep!");
                
            }
        }
    }
    public class SendButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(chat)
            {
                if(me == 0)
                {
                    ChatTextJTextArea.append("<Black> " + ChatJTextField.getText() + "\n");
                    write.println(OC.MSG);
                    write.println("<Black> " + ChatJTextField.getText());
                }
                else if(me == 1)
                {
                    ChatTextJTextArea.append("<White> " + ChatJTextField.getText() + "\n");
                    write.println(OC.MSG);
                    write.println("<White> " + ChatJTextField.getText());
                }
                ChatJTextField.setText("");
            }
        }
    }
    public class MenuItemActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                write.println(OC.CMD);
                write.println("STOP");
                System.exit(0);
            }
            catch(Exception e1){}
        }
    }
    private class OthelloPanel extends javax.swing.JPanel
    {
        private int blocksize = 65, boardsize = (blocksize * 8) + 1;
        private int[][] board; //-1 for not set, 0 for black, 1 for white
        private int[][] paintboard;
        private OthelloPanel()
        {
            this.setSize(boardsize, boardsize);
            this.setLocation(10, 10);
            this.addMouseListener(new MouseListener());
            board = new int[8][8];
            paintboard = new int[8][8];
            for(int i=0; i<8; i++)
                for(int j=0; j<8; j++)
                {
                    setBoard(i, j, -1);
                    paintboard[i][j] = -1;
                }
            setBoard(3, 3, 1);
            setBoard(3, 4, 0);
            setBoard(4, 3, 0);
            setBoard(4, 4, 1);
            paintboard[3][3] = 1;
            paintboard[3][4] = 0;
            paintboard[4][3] = 0;
            paintboard[4][4] = 1;
        }
        @Override
        public void paintComponent(Graphics g)
        {
            ArrayList<Point> possibleMoves = findMove();
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.BLACK);
            for(int i=0; i<8; i++)
                for(int j=0; j<8; j++)
                {
                    g2d.drawRect(i*blocksize, j*blocksize, blocksize, blocksize);
                    if(paintboard[i][j] == 0)
                    {
                        g2d.setColor(Color.BLACK);
                        g2d.fillOval((i*blocksize)+1, (j*blocksize)+1, blocksize-1, blocksize-1);
                    }
                    else if(paintboard[i][j] == 1)
                    {
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval((i*blocksize)+1, (j*blocksize)+1, blocksize-1, blocksize-1);
                        g2d.setColor(Color.BLACK);
                    }
                }
            if(getTurn() == me)
            {
                g2d.setColor(Color.GRAY);
                for(int i=0; i<possibleMoves.size(); i++)
                    g2d.fillRect(((int)possibleMoves.get(i).getX() * blocksize)+1,
                            ((int)possibleMoves.get(i).getY() * blocksize)+1, blocksize-1, blocksize-1);
            }
        }
        private synchronized void setBoard(int x, int y, int val)
        {
            board[x][y] = val;
        }
        private synchronized int getBoard(int x, int y)
        {
            return board[x][y];
        }
        private ArrayList<Point> findMove()
        {
            ArrayList<Point> moves = new ArrayList<Point>();
            for(int i=0; i<8; i++)
                for(int j=0; j<8; j++)
                {
                    if(getBoard(i, j) == me)
                    {
                        if(j>0 && getBoard(i, j-1) == opponent())
                            for(int l=j-1; l>=0; l--)  //Go North
                            {
                                if(getBoard(i, l) == -1)
                                {
                                    moves.add(new Point(i, l));
                                    break;
                                }
                                else if(getBoard(i, l) == me)
                                    break;
                            }
                        if(i<7 && j>0 && getBoard(i+1, j-1) == opponent())
                            for(int l=i+1, m=j-1; l<8 && m>=0; l++, m--)  //Go North East
                            {
                                if(getBoard(l, m) == -1)
                                {
                                    moves.add(new Point(l, m));
                                    break;
                                }
                                else if(getBoard(l, m) == me)
                                    break;
                            }
                        if(i<7 && getBoard(i+1, j) == opponent())
                            for(int l=i+1; l<8; l++)  //Go East
                            {
                                if(getBoard(l, j) == -1)
                                {
                                    moves.add(new Point(l, j));
                                    break;
                                }
                                else if(getBoard(l, j) == me)
                                    break;
                            }
                        if(i<7 && j<7 && getBoard(i+1, j+1) == opponent())
                            for(int l=i+1, m=j+1; l<8 && m<8; l++, m++)  //Go South East
                            {
                                if(getBoard(l, m) == -1)
                                {
                                    moves.add(new Point(l, m));
                                    break;
                                }
                                else if(getBoard(l, m) == me)
                                    break;
                            }
                        if(j<7 && getBoard(i, j+1) == opponent())
                            for(int l=j+1; l<8; l++)  //Go South
                            {
                                if(getBoard(i, l) == -1)
                                {
                                    moves.add(new Point(i, l));
                                    break;
                                }
                                else if(getBoard(i, l) == me)
                                    break;
                            }
                        if(i>0 && j<7 && getBoard(i-1, j+1) == opponent())
                            for(int l=i-1, m=j+1; l>=0 && m<8; l--, m++)  //Go South West
                            {
                                if(getBoard(l, m) == -1)
                                {
                                    moves.add(new Point(l, m));
                                    break;
                                }
                                else if(getBoard(l, m) == me)
                                    break;
                            }
                        if(i>0 && getBoard(i-1, j) == opponent())
                            for(int l=i-1; l>=0; l--)  //Go West
                            {
                                if(getBoard(l, j) == -1)
                                {
                                    moves.add(new Point(l, j));
                                    break;
                                }
                                else if(getBoard(l, j) == me)
                                    break;
                            }
                        if(i>0 && j>0 && getBoard(i-1, j-1) == opponent())
                            for(int l=i-1, m=j-1; l>=0 && m>=0; l--, m--)  //Go North West
                            {
                                if(getBoard(l, m) == -1)
                                {
                                    moves.add(new Point(l, m));
                                    break;
                                }
                                else if(getBoard(l, m) == me)
                                    break;
                            }
                    }
                }

            return moves;
        }
        private ArrayList<Point> findFlip(int x, int y)
        {
            ArrayList<Point> flips = new ArrayList<Point>(),
                tmpflips = new ArrayList<Point>();
            flips.add(new Point(x, y));
            for(int l=y-1; l>=0; l--)  //Go North
            {
                if(getBoard(x, l) == opponent())
                    tmpflips.add(new Point(x, l));
                else if(getBoard(x, l) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=x+1, m=y-1; l<8 && m>=0; l++, m--)  //Go North East
            {
                if(getBoard(l, m) == opponent())
                    tmpflips.add(new Point(l, m));
                else if(getBoard(l, m) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=x+1; l<8; l++)  //Go East
            {
                if(getBoard(l, y) == opponent())
                    tmpflips.add(new Point(l, y));
                else if(getBoard(l, y) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=x+1, m=y+1; l<8 && m<8; l++, m++)  //Go South East
            {
                if(getBoard(l, m) == opponent())
                    tmpflips.add(new Point(l, m));
                else if(getBoard(l, m) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=y+1; l<8; l++)  //Go South
            {
                if(getBoard(x, l) == opponent())
                    tmpflips.add(new Point(x, l));
                else if(getBoard(x, l) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=x-1, m=y+1; l>=0 && m<8; l--, m++)  //Go South West
            {
                if(getBoard(l, m) == opponent())
                    tmpflips.add(new Point(l, m));
                else if(getBoard(l, m) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;

            }
            tmpflips.clear();
            for(int l=x-1; l>=0; l--)  //Go West
            {
                if(getBoard(l, y) == opponent())
                    tmpflips.add(new Point(l, y));
                else if(getBoard(l, y) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            for(int l=x-1, m=y-1; l>=0 && m>=0; l--, m--)  //Go North West
            {
                if(getBoard(l, m) == opponent())
                    tmpflips.add(new Point(l, m));
                else if(getBoard(l, m) == me)
                {
                    flips.addAll(tmpflips);
                    break;
                }
                else
                    break;
            }
            tmpflips.clear();
            return flips;
        }
        private int allTaken()
        {
            int[] count = boardCount();
            if(count[0] == 0 || count[1] == 0)
                return count[2];
            else
                return -1;
        }
        private Boolean boardFull()
        {
            for(int i=0; i<8; i++)
                for(int j=0; j<8; j++)
                    if(getBoard(i, j) == -1)
                        return false;
            return true;
        }
        private int[] boardCount()
        {
            int[] count = {0, 0, -1};   //0 for black, 1 for white, 2 for winner
            for(int i=0; i<8; i++)
                for(int j=0; j<8; j++)
                    if(getBoard(i, j) == 0)
                        count[0]++;
                    else if(getBoard(i, j) == 1)
                        count[1]++;
            if(count[0] > count[1])
                count[2] = 0;
            else if(count[1] > count[0])
                count[2] = 1;
            else
                count[2] = -1;
            return count;
        }
        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(boardsize, boardsize);
        }
        private class MouseListener extends MouseAdapter
        {
            @Override
            public synchronized void mouseClicked(MouseEvent e)
            {
                ArrayList<Point> validMoves = findMove();
                int x = e.getX() / blocksize, y = e.getY() / blocksize;
                if(getTurn() == me && gamePlay && validMoves.contains(new Point(x, y)))
                {
                    flipTurn();
                    ArrayList<Point> flips = findFlip(x, y);
                    for(int i=0; i<flips.size(); i++)
                        gameBoard.setBoard((int)flips.get(i).getX(), (int)flips.get(i).getY(), me);
                    Thread flipThread = new Thread(new OthelloFlip(flips, me));
                    flipThread.start();
                    StringBuilder out = new StringBuilder();
                    for(int i=0; i<flips.size(); i++)
                        out.append((int)flips.get(i).getX()).
                                append(",").append((int)flips.get(i).getY()).append("|");
                    try
                    {
                        write.println(OC.INFO);
                        if(opponent() == 0)
                        {
                            StatusJLabel.setText("Black's Turn");
                            write.println("Black's Turn");
                        }
                        else if(opponent() == 1)
                        {
                            StatusJLabel.setText("White's Turn");
                            write.println("White's Turn");
                        }
                        write.println(OC.FLIP);
                        write.println(out);
                        write.println(OC.TURN);
                        write.println(getTurn());
                    }
                    catch(Exception e2)
                    {
                        JOptionPane.showMessageDialog(null, "Exception in flipping turn");
                        System.exit(-1);
                    }
                }
                else if(getTurn() != me)
                    StatusJLabel.setText("Not your turn!");
                else if(!validMoves.contains(new Point(x, y)))
                    StatusJLabel.setText("Not a valid move!");
            }
        }

    }

    public static void main(String[] args)
    {
        Othello win = new Othello();
        win.init();
    }
}
