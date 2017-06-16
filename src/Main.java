import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		File board = new File("sudokuBoard.txt");
		
		Cell[][] easyPuzzle = loadBoard(board);
		printSudoku(easyPuzzle);
		
		Cell[][] solution = solve(easyPuzzle);
		
		//printSudoku(solution);
		//Hello comments


	}
	
/**
 * Method to print out the sudoku board 
 **/
	public static void printSudoku(Cell[][] board){
		for(Cell[] row : board){
			for(Cell cell : row){
				cell.printCell();
			}
			System.out.println();
		}
		
	}

/**
 * Method to load sudoku board form a text file into an array of Cells
 **/
			
	public static Cell[][] loadBoard(File sudokuFile) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(sudokuFile);
		Cell[][] sudoku = new Cell[9][9];
		int j=0; //columns
		int i=0; //rows
		while (fileScanner.hasNextLine()){
			j=0;
			String line = fileScanner.nextLine();
			String[] splitLine = line.split(",");
			for(String val : splitLine){
				sudoku[i][j] = new Cell(Integer.parseInt(val));
				j++;
			}
			i++;
			
		}
		
		return sudoku;
	}

/**
 * method to solve the sudoku puzzle thru brute force algorithm
 * @param puzzle
 * @return
 * @throws InterruptedException 
 */
	public static Cell[][] solve(Cell[][] puzzle) throws InterruptedException{
		long beginTime = System.currentTimeMillis();
		flagStarters(puzzle);
		int row = 0;
		int col = 0;
		/* --------------- Solving Algorithm ----------------- */
		while(row<10 && col<10){
			//check if current cell is a starting value.
			if(!puzzle[row][col].isStarter()){
				puzzle[row][col].setValue(puzzle[row][col].getValue()+1);
				//check row and column --> if good conitnue to next cell, else increment value by one.
				if(checkRow(puzzle, puzzle[row][col].getValue(), row, col) &&
						checkCol(puzzle, puzzle[row][col].getValue(), row, col)){
					
					//increment one spcae on teh board
					col++;
					if(col > 9){ //reset column value and increase row value if las column in row is reached
						col=0;
						row++;
					}
					continue;
					
				}else continue; // if there is an issue with checking the row or column, continue and increase the value one more time.
			
			
			}else col++;
			
		}
		
		/* --------------------------------------------------- */

		long endTime = System.currentTimeMillis();
		System.out.println("Total time to solve: "+(endTime-beginTime)/1000.0+" seconds");
		return puzzle;
	}
	
/**
 * Method to check the row of a cell for conflicting values
 * - Returns false if there is a conflict
 */
	public static boolean checkRow(Cell[][] puzzle, int value, int currentRow, int currentCol){
		for(int i=0; i<10; i++){
			if(i==currentCol) continue;
			if(puzzle[currentRow][i].getValue() == value) return false;
		}
		
		return true;
	}
	
	
/**
 * Method to check the columns of a cell for conflicting values
 */
	public static boolean checkCol(Cell[][] puzzle, int value, int currentRow, int currentCol){
		for(int i=0; i<10; i++){
			if(i==currentRow) continue;
			if(puzzle[currentCol][i].getValue() == value) return false;
		}
		
		return true;
	}
	
/**
 * Method to set the starter flag on the non-zero values so the algorithm skips them
 */
	public static void flagStarters(Cell[][] puzzle){
		for(Cell[] row : puzzle){
			for(Cell cell : row){
				if(cell.getValue() != 0) cell.setStarter(true);
			}
		}
	}
	
}

