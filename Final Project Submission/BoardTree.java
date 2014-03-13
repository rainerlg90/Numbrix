/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numbrix;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Rainer
 */
public class BoardTree {
    public Cell root;
    public ArrayList<Cell> closedCells; //visited Cells
    public ArrayList<Cell> openCells;   //unvisited Cells
    public ArrayList<Cell> searchPath; //solution path
    
    public BoardTree(){
    
    }
    
    
    public BoardTree(Cell root) {
        this.root = new Cell(root.value, root.index, 0, null);//root; //new Cell(root.value, root.index);   
        root.parent = null;
        root.hCost = 0;
        searchPath = new ArrayList();
        //closedCells.add(this.root);
    }
    
    public static class Cell implements Comparator<Cell>, Comparable<Cell>{
        public int value;
        public int index;
        public Cell parent;
        public ArrayList<Cell> children;
        public int hCost;
        
        public Cell(){
        
        }
        
        /*
        public Cell(int cellValue, int cellIndex){
            value = cellValue;
            index = cellIndex;
        }
        */
        
        public Cell(int cellValue, int boardIndex, int heuristicCost, Cell cellParent){
            value = cellValue;
            index = boardIndex;
            hCost = heuristicCost;
            children = new ArrayList();
            parent = cellParent;
            
        }
        
        public void addChild(Cell childCell){
            children.add(childCell);
        }
        
        //Overriding the compareTo method
        public int compareTo(Cell cell){
            return this.hCost - cell.hCost;
        }


        //Overriding the compare method to sort values
        public int compare(Cell a, Cell b){
            return a.hCost - b.hCost;
        }
    
    }
    
     public static class ValueComparator implements Comparator<Cell>{
        public int compare(Cell a, Cell b){
            return a.value - b.value;
        }
     }
    
    
}
