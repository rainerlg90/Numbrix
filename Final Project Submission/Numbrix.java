package numbrix;

import java.io.*;
import java.text.ParseException;
import java.util.StringTokenizer;

public class Numbrix {


    public static void main(String[] args) throws IOException, ParseException {
        
//        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
//        
//        try{
//         in = new BufferedReader(new FileReader("C:\\Users\\Rainer\\Desktop\\2013 FALL\\CAP 4621 - Artificial Intelligence\\Project\\InitialBoard.txt")); 
//         
//        }catch (FileNotFoundException e){
//            System.out.println(e.getCause());
//            System.out.println("Error loading initial board");
//        } 
//       
//        int [] boardRows = new int [15];
//        
//        //----------------
//        String text = in.readLine(); 
//        System.out.println(text); 
//
//        StringTokenizer tokenizer = new StringTokenizer(text," "); 
//
//        int boardSize = 0;
//        while (tokenizer.hasMoreElements()){
//            boardRows[boardSize] = Integer.parseInt(tokenizer.nextToken());
//            boardSize++;
//        }
//
//        int []initialBoard = new int[boardSize*boardSize+1];
//        System.arraycopy(boardRows, 0, initialBoard, 1, boardSize);
//        
//        int index = 0;
//        while (in.ready()) { 
//            index++;
//            text = in.readLine(); 
//            System.out.println(text); 
//
//            tokenizer = new StringTokenizer(text," "); 
//            int pos = 0;
//            while (tokenizer.hasMoreElements()){
//                pos++;
//                initialBoard[index*boardSize+pos] = Integer.parseInt(tokenizer.nextToken()); 
//            }
//        }
//        //-----------------------------
        
        
//        Board gameBoard = new Board(initialBoard, boardSize);
        
        
        
        NumbrixGUI gui = new NumbrixGUI();
        gui.setVisible(true);
        
    }
}
