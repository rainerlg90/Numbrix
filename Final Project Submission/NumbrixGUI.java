package numbrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.*;


public class NumbrixGUI extends JFrame{
    
    private static final String TITLE="Numbrix";
    private static final int WIDTH=700;
    private static final int HEIGHT=600;
    
    private javax.swing.JPanel boardPanel;
    private JTextField[] cells;
    
    private javax.swing.JFrame jFrameInstructions;
    private javax.swing.JFrame jFrameAbout;
    public  javax.swing.JFrame frame;
    
    private javax.swing.JTextArea jTextAreaAbout;
    private javax.swing.JTextArea jTextAreaInstructions;
    
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemNewGame;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemInstructions;
    private javax.swing.JMenuItem jMenuItemAbout;
    
    private javax.swing.JFrame jFrameFileChooser;
    private javax.swing.JFileChooser jFileChooser;
    
    private javax.swing.JFrame jFrameSolvedBoard;
    private javax.swing.JPanel jPanelSolvedBoard;
    private JTextField[] solvedCells;
    
    private javax.swing.JButton jButtonNEWGAME;
    private javax.swing.JButton jButtonPAUSE;
    private javax.swing.JButton jButtonRESET;
    private javax.swing.JButton jButtonSOLVE;
    private javax.swing.JLabel jLabelTIMER;
    private javax.swing.JPanel jPanelSideBar;
    
    private javax.swing.JLabel jLabelClock;
    public GameTimer gameTimer;
    //public Thread runner;
    
    private boolean gameOver;
    public Board gameInitialBoard;
    public Board gameBoard;
    
    
    
    public NumbrixGUI () throws ParseException{//Board gameBoard) throws ParseException{
        
        //Board
        this.gameBoard = new Board();
        
        //Container
        setTitle(TITLE);
	setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	
        //About Frame
        jFrameAbout = new javax.swing.JFrame();
        jFrameAbout.setResizable(false);
        jFrameAbout.setPreferredSize(new Dimension(500,300));
        
        jTextAreaAbout = new javax.swing.JTextArea();
        jTextAreaAbout.setBackground(new java.awt.Color(240, 240, 240));
        jTextAreaAbout.setColumns(20);
        jTextAreaAbout.setRows(10);
        jTextAreaAbout.setText("\t\tNumbrix Version 1\n\nClass Project for Artificial Intelligence course.\nUniversity of Florida\nCISE\n\n\n\n\nby Rainer Ledesma\nhttp://www.cise.ufl.edu/~rledesma/\n");
        jTextAreaAbout.setFont(new java.awt.Font("Agency FB", 1, 20)); // NOI18N
        jTextAreaAbout.setEditable(false);
        jTextAreaAbout.setPreferredSize(new Dimension(500,300));
        
        javax.swing.GroupLayout jFrameAboutLayout = new javax.swing.GroupLayout(jFrameAbout.getContentPane());
        jFrameAbout.getContentPane().setLayout(jFrameAboutLayout);
        jFrameAboutLayout.setHorizontalGroup(
            jFrameAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameAboutLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jTextAreaAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrameAboutLayout.setVerticalGroup(
            jFrameAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameAboutLayout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jTextAreaAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        //
        
        //Instructions Frame
        jFrameInstructions = new javax.swing.JFrame();
        jFrameInstructions.setResizable(false);
        jFrameInstructions.setPreferredSize(new Dimension(500,300));
        
        jTextAreaInstructions = new javax.swing.JTextArea();
        jTextAreaInstructions.setBackground(new java.awt.Color(240, 240, 240));
        jTextAreaInstructions.setColumns(20);
        jTextAreaInstructions.setRows(10);
        jTextAreaInstructions.setText("\t\tNumbrix\n" 
                                       + "The object of Numbrix is to fill the board with sequence of consecutive \n"
                                       + "numbers from 1 to the maximun cell count. The numbers must fillow a \n"
                                       + "horizontal or vertical path (no diagonals)\n");
        jTextAreaInstructions.setFont(new java.awt.Font("Agency FB", 1, 20)); // NOI18N
        jTextAreaInstructions.setEditable(false);
        jTextAreaInstructions.setPreferredSize(new Dimension(500,300));
        
        javax.swing.GroupLayout jFrameInstructionsLayout = new javax.swing.GroupLayout(jFrameInstructions.getContentPane());
        jFrameInstructions.getContentPane().setLayout(jFrameInstructionsLayout);
        jFrameInstructionsLayout.setHorizontalGroup(
            jFrameInstructionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameInstructionsLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jTextAreaInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrameInstructionsLayout.setVerticalGroup(
            jFrameInstructionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameInstructionsLayout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jTextAreaInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        //
        
        //File Chooser
        jFrameFileChooser = new javax.swing.JFrame();
        jFileChooser = new javax.swing.JFileChooser();
         jFileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jFileChooserActionPerformed(evt);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "File Exception");
                }
            }
        });
        
        javax.swing.GroupLayout jFrameFileChooserLayout = new javax.swing.GroupLayout(jFrameFileChooser.getContentPane());
        jFrameFileChooser.getContentPane().setLayout(jFrameFileChooserLayout);
        jFrameFileChooserLayout.setHorizontalGroup(
            jFrameFileChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
            .addGroup(jFrameFileChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameFileChooserLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jFileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(18, 18, 18)))
        );
        jFrameFileChooserLayout.setVerticalGroup(
            jFrameFileChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
            .addGroup(jFrameFileChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameFileChooserLayout.createSequentialGroup()
                    .addGap(27, 27, 27)
                    .addComponent(jFileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(32, 32, 32)))
        );
        //
        
        //SidePanel
        jPanelSideBar = new javax.swing.JPanel();
        jButtonRESET = new javax.swing.JButton();
        jButtonSOLVE = new javax.swing.JButton();
        jButtonNEWGAME = new javax.swing.JButton();
        jLabelTIMER = new javax.swing.JLabel();
        jButtonPAUSE = new javax.swing.JButton();
        
        jButtonRESET.setText("RESET");
        jButtonRESET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRESETActionPerformed(evt);
            }
        });
        jButtonSOLVE.setText("SOLVE");
        jButtonSOLVE.setEnabled(false);
        jButtonSOLVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButtonSOLVEActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(NumbrixGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jButtonNEWGAME.setText("NEW GAME");
        jButtonNEWGAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNEWGAMEActionPerformed(evt);
            }
        });
        jLabelTIMER.setText("TIMER");
        jLabelTIMER.setFont(new java.awt.Font("Agency FB", 1, 18));
        jButtonPAUSE.setText("PAUSE");
        jButtonPAUSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPAUSEActionPerformed(evt);
            }
        });
        jLabelClock = new javax.swing.JLabel();
        jLabelClock.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabelClock.setText("10 : 99 : 60");

        javax.swing.GroupLayout jPanelSideBarLayout = new javax.swing.GroupLayout(jPanelSideBar);
        jPanelSideBar.setLayout(jPanelSideBarLayout);
        jPanelSideBarLayout.setHorizontalGroup(
            jPanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRESET, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSOLVE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonNEWGAME, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jButtonPAUSE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelSideBarLayout.createSequentialGroup()
                        .addGroup(jPanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTIMER)
                            .addComponent(jLabelClock, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelSideBarLayout.setVerticalGroup(
            jPanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTIMER)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClock, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonPAUSE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(jButtonNEWGAME)
                .addGap(18, 18, 18)
                .addComponent(jButtonRESET)
                .addGap(18, 18, 18)
                .addComponent(jButtonSOLVE)
                .addContainerGap())
        );
        ////////
        
        //Menu
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNewGame = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemInstructions = new javax.swing.JMenuItem();
        jMenuItemAbout = new javax.swing.JMenuItem();
        
        jMenu1.setText("File");
        jMenu2.setText("Edit");

        jMenuItemNewGame.setText("New Game");
        jMenuItemExit.setText("Exit");
        jMenuItemInstructions.setText("Instructions");
        jMenuItemAbout.setText("About Numbrix");
        
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        jMenu1.add(jMenuItemNewGame);
        jMenu1.add(jMenuItemExit);
        jMenu2.add(jMenuItemInstructions);
        jMenu2.add(jMenuItemAbout);
        
        setJMenuBar(jMenuBar1);
        
        jMenuItemNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewGameActionPerformed(evt);
            }
        });
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuItemInstructions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInstructionsActionPerformed(evt);
            }
        });
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        //
        
        
        //SolvedBoardPanel
        jPanelSolvedBoard = new javax.swing.JPanel();
        jPanelSolvedBoard.setBackground(new java.awt.Color(255, 255, 255));
        
        //Solved Board Frame
        jFrameSolvedBoard = new javax.swing.JFrame();
        jFrameSolvedBoard.setTitle("Numbrix Solution");
        javax.swing.GroupLayout jFrameSolvedBoardLayout = new javax.swing.GroupLayout(jFrameSolvedBoard.getContentPane());
        jFrameSolvedBoard.getContentPane().setLayout(jFrameSolvedBoardLayout);
        jFrameSolvedBoardLayout.setHorizontalGroup(
            jFrameSolvedBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jFrameSolvedBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelSolvedBoard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        jFrameSolvedBoardLayout.setVerticalGroup(
            jFrameSolvedBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jFrameSolvedBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelSolvedBoard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );
        
        
        
        
        //BoardPanel
	boardPanel = new javax.swing.JPanel();
        boardPanel.setBackground(new java.awt.Color(255, 255, 255));
        //Clock
        gameTimer = new GameTimer(jLabelClock);
        this.gameTimer.start();
        this.gameTimer.run();
        
        //Initialize
        //init();
        
        
        //Container Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanelSideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
    }
    

    //Initialize Board
    public void init()
    {
        //Create cells and handlers
        cells = new JTextField[gameBoard.boardSize*gameBoard.boardSize+1];
        
        //Redraw Panel
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(gameBoard.boardSize, gameBoard.boardSize)); //Set layout
        
        JTextFilter TextFilter = new JTextFilter(3);
        JTextDocumentListener JTextDocFilter = new JTextDocumentListener();
        
        for(int i=1; i<=gameBoard.boardSize*gameBoard.boardSize; i++)
        {            
            cells[i] = new JTextField();
            
            ((AbstractDocument)cells[i].getDocument()).setDocumentFilter(TextFilter);
            ((AbstractDocument)cells[i].getDocument()).addDocumentListener(JTextDocFilter);
            ((AbstractDocument)cells[i].getDocument()).putProperty("index", i);

            cells[i].setHorizontalAlignment(JTextField.CENTER);
            cells[i].setFont(new Font("Agency FB", Font.BOLD, 24));

            //Add elements to the grid content pane
            boardPanel.add(cells[i]);
        }
        
        //Initialize booleans
        gameOver=false;
        
        //Clear Board
        for(int i=1; i<=(gameBoard.boardSize*gameBoard.boardSize); i++)
        {   
            String ch = Integer.toString(this.gameBoard.cells[i]);
            char chr = '-';
            if (ch.compareTo("0")==0 || ch==Character.toString(chr)){
                cells[i].setText("");
            }
            else{
                cells[i].setText(ch);
                cells[i].setBackground(Color.lightGray);
            }
        }
        //gameBoard.out();
        
        setVisible(true);
        this.boardPanel.repaint();
        
        this.gameTimer.reset();
        jButtonSOLVE.setEnabled(true);
        
    }

    public void jMenuItemNewGameActionPerformed(java.awt.event.ActionEvent evt) {        
         //Custom button text
        Object[] optionPane = {"Load Board from File", "Random Board"};
        int selection = JOptionPane.showOptionDialog(frame,
            "Please select an option: ",            
            "New Board",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            optionPane,
            optionPane[1]);


        if (selection==0){
            this.jFrameFileChooser.pack();
            this.jFrameFileChooser.setVisible(true);
            jFileChooser.enable();
            jFileChooser.setVisible(true);
        }else{
            //random board
        }
    }
    
    public void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {                                           
        System.exit(0);
    } 
 
    public void jMenuItemInstructionsActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.jFrameInstructions.setVisible(true);
        this.jFrameInstructions.pack();
    } 
    
    public void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.jFrameAbout.setVisible(true);
        this.jFrameAbout.pack();
    } 
    
    private void jButtonRESETActionPerformed(java.awt.event.ActionEvent evt) { 
        System.out.println("reseting");
        for (int i=0; i<=gameBoard.boardSize*gameBoard.boardSize; i++)
            gameBoard.cells[i] = gameInitialBoard.cells[i];
        
        init();
    }
    
    private void jButtonSOLVEActionPerformed(java.awt.event.ActionEvent evt) throws IOException { 
        System.out.println("solving");
        
        
                //DEBUG
//                BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Rainer\\Desktop\\2013 FALL\\CAP 4621 - Artificial Intelligence\\Project\\InitialBoard.txt"));
//
//                try{
//                in = new BufferedReader(new FileReader("C:\\Users\\Rainer\\Desktop\\2013 FALL\\CAP 4621 - Artificial Intelligence\\Project\\InitialBoard.txt")); 
//
//                }catch (FileNotFoundException e){
//                    System.out.println(e.getCause());
//                    System.out.println("Error loading initial board");
//                } 
//
//                int [] boardRows = new int [15];
//
//                //----------------
//                String text = in.readLine(); 
//
//                StringTokenizer tokenizer = new StringTokenizer(text," "); 
//
//                int boardSize = 0;
//                while (tokenizer.hasMoreElements()){
//                    boardRows[boardSize] = Integer.parseInt(tokenizer.nextToken());
//                    boardSize++;
//                }
//
//                int []newBoard = new int[boardSize*boardSize+1];
//                System.arraycopy(boardRows, 0, newBoard, 1, boardSize);
//
//                int index = 0;
//                while (in.ready()) { 
//                    index++;
//                    text = in.readLine(); 
//
//                    tokenizer = new StringTokenizer(text," "); 
//                    int pos = 0;
//                    while (tokenizer.hasMoreElements()){
//                        pos++;
//                        newBoard[index*boardSize+pos] = Integer.parseInt(tokenizer.nextToken()); 
//                    }
//                }
//                
//                this.jFrameFileChooser.setVisible(false);
//               // this.boardPanel.s
//                
//                gameInitialBoard = new Board(newBoard, boardSize);
//                gameBoard = new Board(newBoard, boardSize);
//                init();
//        
                //END DEBUG
        
        long startTime = System.nanoTime();
        
        this.gameBoard.solveBoard();
        
        long endTime = System.nanoTime();
        double time = (endTime-startTime)/100000000.0;
        System.out.println("Excecution time: "+time+" seconds");
        
        
        //jPanelSolvedBoard
        jPanelSolvedBoard.removeAll();
        jPanelSolvedBoard.setLayout(new GridLayout(gameBoard.boardSize, gameBoard.boardSize)); //Set layout
        
        solvedCells = new JTextField[gameBoard.boardSize*gameBoard.boardSize+1];
        
        for(int i=1; i<=gameBoard.boardSize*gameBoard.boardSize; i++)
        {            
            solvedCells[i] = new JTextField();

            solvedCells[i].setHorizontalAlignment(JTextField.CENTER);
            solvedCells[i].setFont(new Font("Agency FB", Font.BOLD, 24));

            //Add elements to the grid content pane
            jPanelSolvedBoard.add(solvedCells[i]);
            
            String ch = Integer.toString(this.gameBoard.cells[i]);
            solvedCells[i].setText(ch);
            solvedCells[i].setEditable(false);
        }
        
        //gameBoard.out();
        jPanelSolvedBoard.setVisible(true);
        this.jPanelSolvedBoard.repaint();
        this.jFrameSolvedBoard.setVisible(true);
        this.jFrameSolvedBoard.pack();
        
        //
  
    }
    
    
    private void jButtonPAUSEActionPerformed(java.awt.event.ActionEvent evt) { 
        if (jButtonPAUSE.getText() == "PAUSE"){
            this.jButtonPAUSE.setText("RESUME");
            this.gameTimer.pause();
        }else if (jButtonPAUSE.getText() == "RESUME"){
            this.jButtonPAUSE.setText("PAUSE");
            this.gameTimer.resume();
        }
    }
    
    private void jButtonNEWGAMEActionPerformed(java.awt.event.ActionEvent evt) {  
        
        //Custom button text
        Object[] optionPane = {"Load Board from File", "Random Board"};
        int selection = JOptionPane.showOptionDialog(frame,
            "Please select an option: ",            
            "New Board",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            optionPane,
            optionPane[1]);


        if (selection==0){
            this.jFrameFileChooser.pack();
            this.jFrameFileChooser.setVisible(true);
            jFileChooser.enable();
            jFileChooser.setVisible(true);
        }else{
            //random board
        }

    }
    
    public void jFileChooserActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException, IOException {
        File initialBoard = this.jFileChooser.getSelectedFile();
        InputStream inputStream = new FileInputStream(initialBoard);
        Scanner scanner = new Scanner(inputStream);
        
        if (!initialBoard.getName().toLowerCase().contains("initialboard")){//.toLowerCase().compareTo("initialboard")!=0){
            JOptionPane.showMessageDialog(frame, "Wrong File \n Please select InitialBoard.txt", "Eror", JOptionPane.ERROR_MESSAGE);
        }else{
            
                BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));

                try{
                in = new BufferedReader(new FileReader(initialBoard.getAbsolutePath())); 

                }catch (FileNotFoundException e){
                    System.out.println(e.getCause());
                    System.out.println("Error loading initial board");
                } 

                int [] boardRows = new int [15];

                //----------------
                String text = in.readLine(); 

                StringTokenizer tokenizer = new StringTokenizer(text," "); 

                int boardSize = 0;
                while (tokenizer.hasMoreElements()){
                    boardRows[boardSize] = Integer.parseInt(tokenizer.nextToken());
                    boardSize++;
                }

                int []newBoard = new int[boardSize*boardSize+1];
                System.arraycopy(boardRows, 0, newBoard, 1, boardSize);

                int index = 0;
                while (in.ready()) { 
                    index++;
                    text = in.readLine(); 

                    tokenizer = new StringTokenizer(text," "); 
                    int pos = 0;
                    while (tokenizer.hasMoreElements()){
                        pos++;
                        newBoard[index*boardSize+pos] = Integer.parseInt(tokenizer.nextToken()); 
                    }
                }
                
                this.jFrameFileChooser.setVisible(false);
               // this.boardPanel.s
                
                gameInitialBoard = new Board(newBoard, boardSize);
                gameBoard = new Board(newBoard, boardSize);
                init();
        }            
    }
        
    
    class GameTimer implements Runnable{
        public int sec;
        public int min;
        public int hour;
        public javax.swing.JLabel text; 
        public Thread runner;
        public volatile boolean running;
        
        GameTimer(javax.swing.JLabel text){
            sec = min = hour = 0;
            this.text = text;
            running = false;
        }
        
        public void run(){
            while (runner==Thread.currentThread() && running){//running){   
                sec++;
                    if (sec==60){
                        sec=0;
                        min++;
                        if (min==60){
                            min=0;
                            hour++;
                        }
                    }
                redraw();
                                                                //define thread task
                try
                    {
                    Thread.sleep(1000);
                    }
                    catch(InterruptedException e)
                        {
                            System.out.println("Thread failed");
                        }

            }
        }
     
        public void start(){
             running = true;
             if(runner == null) 
                 runner = new Thread(this);
             
             runner.start();
        }
        
        public void redraw(){
            text.setText(Integer.toString(hour)+':'+Integer.toString(min)+':'+Integer.toString(sec));
        }
        
        public void pause(){
            //running = false;
//            try {
//                runner.wait();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(NumbrixGUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
       
        public void resume(){
            running = true;
        }
        
        public void reset(){
            sec = min = 0;
        }
        
    
    }
    
    
    //Document Listener
    protected class JTextDocumentListener implements DocumentListener {
        
        public javax.swing.JFrame frame;
        
        public void insertUpdate(DocumentEvent e) {
            try {
                updateBoard(e);
            } catch (BadLocationException ex) {
                Logger.getLogger(NumbrixGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void removeUpdate(DocumentEvent e) {
            try {
                updateBoard(e);
            } catch (BadLocationException ex) {
                Logger.getLogger(NumbrixGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void changedUpdate(DocumentEvent e) {
            try {
                updateBoard(e); 
            } catch (BadLocationException ex) {
                Logger.getLogger(NumbrixGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private void updateBoard(DocumentEvent e) throws BadLocationException{
            Document doc = e.getDocument();
            int index = (int)doc.getProperty("index");
            String valueString = doc.getText(0, doc.getLength());
            if (doc.getLength()==0)
                valueString = "0"; 
            int value = Integer.parseInt(valueString);
            gameBoard.changeCellAt(index, value);
            //gameBoard.out();
            if (gameBoard.checkGameOver()){
                JOptionPane.showMessageDialog(frame, "NUMBRIX COMPLETED!!!");
            }
                    
        }
    } 
    
    

    
    //Document Filter
    class JTextFilter extends DocumentFilter{   
        private int maxTextLength;

        public JTextFilter(int maxTextLength) {
            this.maxTextLength = maxTextLength;
        }

        private boolean verifyText(String text) {
         return text.length() <= maxTextLength+1;
        }
        
        
        @Override
        public void insertString(DocumentFilter.FilterBypass fp
                , int offset, String string, AttributeSet aset)
                                    throws BadLocationException
        {
            
            Document doc = fp.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            StringBuilder sb = new StringBuilder(oldText);
            sb.insert(offset, string);
            
            
            int len = string.length();
            boolean isValidInteger = true;

            for (int i = 0; i < len; i++)
            {
                if (!Character.isDigit(string.charAt(i)))
                {
                    isValidInteger = false;
                    break;
                }
            }
            if (isValidInteger && verifyText(sb.toString()))
                super.insertString(fp, offset, string, aset);
            else
                Toolkit.getDefaultToolkit().beep();            
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fp, int offset
                        , int length, String string, AttributeSet aset)
                                            throws BadLocationException
        {
            Document doc = fp.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            StringBuilder sb = new StringBuilder(oldText);
            sb.replace(offset, offset + length, oldText);
            
            int len = string.length();
            boolean isValidInteger = true;

            for (int i = 0; i < len; i++)
            {
                if (!Character.isDigit(string.charAt(i)))
                {
                    isValidInteger = false;
                    break;
                }
            }
            if (isValidInteger && verifyText(sb.toString())){
                super.replace(fp, offset, length, string, aset);
            }
            else
                Toolkit.getDefaultToolkit().beep();
        }
    } 

}
