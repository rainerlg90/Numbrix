/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numbrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Rainer
 */
public class Board {
    public int[] cells;
    public int boardSize;
    
    public Board (){
        this.boardSize = 3;
        this.cells = new int[10];
    }
    
    public Board (int[] cells, int boardSize){
        this.boardSize = boardSize;
        this.cells = cells.clone();
    }
    
    public void clearBoard(){
        int[] tmpcellss = new int[this.cells.length];
        this.cells = tmpcellss;        
    }
    
    public void changeCellAt(int index, int entry){
        cells[index] = entry;
    }
    
    public boolean checkValidMove(){
        
        return true;
    }
    
    public boolean checkGameOver(){
        boolean first=false, last=false;        
        for (int i=1; i<this.cells.length; i++){
            if (cells[i]==0)
                return false;
            else if (cells[i]==1)
                first=true;
            else if (cells[i]==this.boardSize*this.boardSize)
                last=true;
        }
        if (!(first && last))
            return false;        
        else{
            return checkValidBoard();
        }
    }
    
    public boolean checkValidBoard(){
        for (int i=1; i<this.cells.length; i++){
            if (cells[i]==1)
                return checkAround(i);
        }
            
        return false;
    }
    
    public boolean checkAround(double index){
        
        if (cells[(int)index]==(boardSize*boardSize))
            return true;
        else{
            if (index<boardSize || index%(boardSize)!=0)
                if (cells[(int)index]+1==cells[(int)index+1]) //right
                    return checkAround(index+1);
            if (index/(boardSize-1)!=1 && index!=1)
                if (cells[(int)index]+1==cells[(int)index-1]) //left
                    return checkAround(index-1);
            if (index>boardSize)
                if (cells[(int)index]+1==cells[(int)index-boardSize]) //up
                    return checkAround(index-boardSize);
            if (index<=boardSize*(boardSize-1))
                if (cells[(int)index]+1==cells[(int)index+boardSize])  //down
                    return checkAround(index+boardSize);
        }
    
        return false;
    }
    
    public void out(){
        for (int i=0; i<(boardSize*boardSize); i+=boardSize)
            System.out.print("==");
        System.out.println("");
        
        for (int i=0; i<(boardSize*boardSize); i+=boardSize){
            for (int j=0; j<boardSize; j++){
                if (cells[1+i+j]>=10)
                    System.out.print(cells[1+i+j]+" ");
                else
                    System.out.print(cells[1+i+j]+"  ");
            }
            System.out.println("");
        }
        
    }
    
    
    public void solveBoard(){
        
        int [] solvedCell = this.cells;
        
        //Getting initialized cells
        ArrayList<BoardTree.Cell> initialValues = new ArrayList();
        for (int i=1; i<this.cells.length; i++){
            if (cells[i]!=0)
                initialValues.add(new BoardTree.Cell(cells[i], i, 0, null));            
        }
        
        //Sort by values
        Collections.sort(initialValues, new BoardTree.ValueComparator());
        
        //Initialize Tree   
        BoardTree boardTree = new BoardTree(initialValues.get(0)); 
               
        
        BoardTree.Cell currentCell = initialValues.get(0);//current cell
        BoardTree.Cell goalCell = initialValues.get(0); //destination cell

        //Add currentCell to searchPath
        boardTree.searchPath.add(currentCell);

        //Recursive Method 
        int initValueIndex = 0;
        
        //If 1 not in board
        if (initialValues.get(0).value!=1){
            for (int i=1; i<boardSize*boardSize; i++){
                if (cells[i]==0){
                    if (initialValues.get(0).value==1)
                        initialValues.remove(0);
                    initialValues.add(new BoardTree.Cell(1, i, 0, null));
                    Collections.sort(initialValues, new BoardTree.ValueComparator());
                    currentCell = initialValues.get(0);
                    goalCell = initialValues.get(0);
                    if (dfSearch(currentCell, goalCell, boardTree, initialValues, initValueIndex))
                        break;
                }
            }   
        }else
            dfSearch(currentCell, goalCell, boardTree, initialValues, initValueIndex);

    }

    
    //Recursive Method
    public boolean dfSearch(BoardTree.Cell currentCell, BoardTree.Cell goalCell, BoardTree boardTree, ArrayList<BoardTree.Cell> initialValues, int initValueIndex){
        
        //this.out();//debug
        
        //Board Completed
        if (currentCell.value==boardSize*boardSize){ 
            return true;
        }
        
        
        //Partial problem solved
        if (currentCell.value==goalCell.value){
            initValueIndex++;//initialValues.remove(0);
            
            if (initValueIndex<=initialValues.size()-1)//!initialValues.isEmpty()) 
                goalCell = initialValues.get(initValueIndex);
            else{  
                //Place last numbers on the board
                while (setChild(currentCell)==1){
                    currentCell = currentCell.children.get(0);
                    this.cells[currentCell.index]=currentCell.value;
                }
                if (currentCell.value==boardSize*boardSize) 
                    return true;
                else 
                    return false;
            }   
        }
        
        
        //Expand Node (Find children cells)
        setChildren(currentCell, goalCell, 1);
        
        //Dead end
        if (currentCell.children.isEmpty())
            return false;

        
        //Sort children 
        Collections.sort(currentCell.children); 
        
        //Puts value on cell
        this.cells[currentCell.index]=currentCell.value;
        boardTree.searchPath.add(currentCell);
        
        //Recursive step
        for (BoardTree.Cell child: currentCell.children){
            if (dfSearch(child, goalCell, boardTree, initialValues, initValueIndex)){         
                return true;
            }
        }
        
        //Backtrack if not returned true
        this.cells[currentCell.index] = 0;
        boardTree.searchPath.remove(boardTree.searchPath.size()-1);
        if (currentCell.value==goalCell.value)//only for goal cell value
            initValueIndex--;
        
        return false;
    }
    
    
  
    
    
    //Sets posible children of currentCell
    public void setChildren(BoardTree.Cell currentCell, BoardTree.Cell goalCell, int direction){
        int dist;
        int index = currentCell.index; 
        int next_value = currentCell.value+1;
        
        
        //Add children cells
        if (index%(boardSize)!=0){ //right
            if (cells[index+1]==0 || cells[index+1]-currentCell.value==1){//if empty or solution
                BoardTree.Cell child = new BoardTree.Cell(next_value, index+1, 0, currentCell);
                int distance  = findDistance(child, goalCell); 
                if (distance <= (goalCell.value - next_value)){
                    child.hCost =  distance + findFreeCells(child.index);
                    child.parent = currentCell;
                    currentCell.addChild(child);
                }
            }
        }
        if (index%(boardSize)!=1){ //left
             if (cells[index-1]==0 || cells[index-1]-currentCell.value==1){//if empty or solution
                BoardTree.Cell child = new BoardTree.Cell(next_value, index-1, 0, currentCell);
                int distance  = findDistance(child, goalCell); 
                if (distance <= (goalCell.value - next_value)){
                    child.hCost = distance + findFreeCells(child.index);
                    child.parent = currentCell;
                    currentCell.addChild(child);
                }
             }
        }
        if (index>boardSize){ //up
             if (cells[index-boardSize]==0 || cells[index-boardSize]-currentCell.value==1){//if empty or solution
                BoardTree.Cell child = new BoardTree.Cell(next_value, index-boardSize, 0, currentCell);
                int distance  = findDistance(child, goalCell); 
                if (distance <= (goalCell.value - next_value)){
                    child.hCost = distance + findFreeCells(child.index);
                    child.parent = currentCell;
                    currentCell.addChild(child);
                }
             }
        }
        if (index<=boardSize*(boardSize-1)){ //down
             if (cells[index+boardSize]==0 || cells[index+boardSize]-currentCell.value==1){//if empty or solution
                BoardTree.Cell child = new BoardTree.Cell(next_value, index+boardSize, 0, currentCell);
                int distance  = findDistance(child, goalCell); 
                if (distance <= (goalCell.value - next_value)){
                    child.hCost = distance + findFreeCells(child.index);
                    child.parent = currentCell;
                    currentCell.addChild(child);
                }
             }
        }
        
//        if (index<=boardSize*(boardSize-1)){ //down
//             if (cells[index+boardSize]==0){//if empty
//                freeCells = findFreeCells(index+boardSize);
//                hCost = (dist-1)+freeCells;
//                currentCell.addChild(new BoardTree.Cell(next_value, cells[index+boardSize], index+boardSize,hCost, currentCell));
//             }
//        }
    }
    
    //Sets child of currentCell
    public int setChild(BoardTree.Cell currentCell){
        
        int index = currentCell.index; 
        int next_value = currentCell.value+1;
        
        //Add child cell
        if (index%(boardSize)!=0){ //right
            if (cells[index+1]==0){//if empty 
                BoardTree.Cell child = new BoardTree.Cell(next_value, index+1, 0, currentCell);
                currentCell.addChild(child);
                return 1;
            }
        }
        if (index%(boardSize)!=1){ //left
             if (cells[index-1]==0){//if empty
                BoardTree.Cell child = new BoardTree.Cell(next_value, index-1, 0, currentCell);
                currentCell.addChild(child);
                return 1;
             }
        }
        if (index>boardSize){ //up
             if (cells[index-boardSize]==0){//if empty
                BoardTree.Cell child = new BoardTree.Cell(next_value, index-boardSize, 0, currentCell);
                currentCell.addChild(child);
                return 1;
             }
        }
        if (index<=boardSize*(boardSize-1)){ //down
             if (cells[index+boardSize]==0){//if empty
                BoardTree.Cell child = new BoardTree.Cell(next_value, index+boardSize, 0, currentCell);
                currentCell.addChild(child);
                return 1;
             }
        }
        
        return 0;
    }
    
    public int findFreeCells(int index){
        
        int freeCells = 0;

        if (index%boardSize!=0)
            if (cells[(int)index+1]==0) //right
                freeCells++;
        if (index%boardSize!=1)
            if (cells[(int)index-1]==0) //left
                freeCells++;
        if (index>boardSize)
            if (cells[(int)index-boardSize]==0) //up
                freeCells++;
        if (index<=boardSize*(boardSize-1))
            if (cells[(int)index+boardSize]==0)  //down
                freeCells++;
                    
        return freeCells;
    }
    
    
    public int findDistance(BoardTree.Cell currentCell, BoardTree.Cell destCell){
        
        //horizontal distance
        int currentCellCol = (currentCell.index%boardSize==0) 
                ? boardSize
                : (currentCell.index%boardSize);
        int destCellCol = (destCell.index%boardSize==0) 
                ? boardSize
                : (destCell.index%boardSize);
        int hdist = Math.abs(currentCellCol-destCellCol);
        
        
        //vertical distance
        int currentCellRow = (currentCell.index%boardSize==0) 
                ? currentCell.index/boardSize
                : (currentCell.index/boardSize+1);
         int destCellRow = (destCell.index%boardSize==0) 
                ? destCell.index/boardSize
                : (destCell.index/boardSize+1);
        int vdist = Math.abs(currentCellRow-destCellRow);

        return (vdist + hdist);
    }
    
    
}



//  public void aSearch(BoardTree.Cell currentCell, BoardTree.Cell goalCell, BoardTree boardTree){
//        
//        //public ArrayList<Cell> closedCells; //visited Cells
//        //public ArrayList<Cell> openCells;   //unvisited Cells
//        //public ArrayList<Cell> searchPath; //solution path
//        
//        int dist = findDistance(currentCell, goalCell);//cost
//        int index = currentCell.index; 
//        int next_value = currentCell.value+1;
//        
//        boardTree.openCells.add(currentCell);
//        
//        while (!boardTree.openCells.isEmpty()){
//        
//            //Lowest cost item in OPEN
//            currentCell = boardTree.openCells.get(0);
//            
//            //Remove from OPEN / Add to CLOSED
//            boardTree.openCells.remove(0);
//            boardTree.closedCells.add(currentCell);
//            
//            //Expand Node (Add children cells)
//            setChildren(currentCell, goalCell, 1);
//            //Sort children 
//            Collections.sort(currentCell.children); 
//            
//            
//            for (BoardTree.Cell child: currentCell.children){
//                //for (BoardTree.Cell cell:)
//                
//                
//                
//                
//                
//                
//            }
//            //Get lowest cost children
//            currentCell = currentCell.children.get(0);
//            
//            //Break if goal node found
//            if (currentCell.value==goalCell.value)
//                break;
//            
//            
//            boardTree.openCells.remove(0);
//            boardTree.closedCells.add(currentCell);
//            
//            
//            
//            
//            //Add children to openNodes
//            for (BoardTree.Cell child: currentCell.children){
//                boardTree.openCells.add(child);
//            }
//
//    //                //Add children to openCells
//    //                for (BoardTree.Cell child: currentCell.children){
//    //                    for (BoardTree.Cell openCell: boardTree.openCells)//Check for child in open
//    //                        if (openCell.index==child.index && child.hCost <= openCell.hCost)
//    //                            boardTree.openCells.add(child);
//    //                }
//
//            //Sort openCells
//            Collections.sort(boardTree.openCells);
//
//            boardTree.searchPath.add(currentCell);
//
//            boardTree.closedCells.add(currentCell); //add curentCell to closed Cell
//            currentCell = boardTree.openCells.get(0);//set currentCell
//            boardTree.openCells.remove(0);
//        }
//        
//    }



//     public void solve(BoardTree.Cell currentCell, BoardTree.Cell destCell, BoardTree boardTree){
//
//        //BoardTree.Cell currentTmpCell = currentCell.children.get(0); 
//        //currentCell.children.remove(0);
//        
////        if (currentTmpCell.equals(destCell))
////            return;
////        
////        solve(currentTmpCell, destCell);
//    
//    }