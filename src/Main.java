import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  TODO:
 *  - create check block method
 */

public class Main {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		File board = new File("sudokuBoard.txt");

		Cell[][] easyPuzzle = loadBoard(board);
		printSudoku(easyPuzzle);

		Cell[][] solution = solve(easyPuzzle);
		printSudoku(solution);


	}

	/**
	 *  Method to print out the sudoku board
	 * @param board
	 */
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
 * @param sudokuFile
 * @return
 * @throws FileNotFoundException
 */
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
	public static Cell[][] solve(Cell[][] puzzle) {
		long beginTime = System.currentTimeMillis();
		flagStarters(puzzle);
		Tracker position = new Tracker();
		int row=0;
		int col=0;
		/* --------------- Solving Algorithm ----------------- */
		while(row<8 || col<8){
			row = position.getRow();
			col = position.getCol();
			
			//System.out.println("Row: "+row);
			//System.out.println("Col: "+col);
			
			//printSudoku(puzzle);
			//check if current cell is a starting value.
			if(!puzzle[row][col].isStarter()){
				//check if the value is greater than 9, if so, reset to zero and move back a space.
				if(puzzle[row][col].getValue()+1 <= 9){
					puzzle[row][col].setValue(puzzle[row][col].getValue()+1);
					//check row and column --> if good continue to next cell, else increment value by one.
					if(checkRow(puzzle, puzzle[row][col].getValue(), row, col) &&
							checkCol(puzzle, puzzle[row][col].getValue(), row, col) && 
							checkBlock(puzzle, puzzle[row][col].getValue(), row, col)){
						//increment one space on the board
						position.moveForward();
						continue;

					}else continue; // if there is an issue with checking the row or column, continue and increase the value one more time.

				}else{//move back a space
					puzzle[row][col].setValue(0);
					do{
						position.moveBackward();
					}while(puzzle[position.getRow()][position.getCol()].isStarter());
					continue;
					
				}
				
			}else{//if the cell is a starter cell, increment one more space on the board
				position.moveForward();
				continue;
			}
			

		}//end while loop

		/* --------------------------------------------------- */

		long endTime = System.currentTimeMillis();
		System.out.println("Total time to solve: "+(endTime-beginTime)+" seconds");
		return puzzle;

	}//end solve method

/**
 * Method to check the row of a cell for conflicting values
 * @param puzzle
 * @param value
 * @param currentRow
 * @param currentCol
 * @return
 */
	public static boolean checkRow(Cell[][] puzzle, int value, int currentRow, int currentCol){
		for(int i=0; i<9; i++){
			if(i==currentCol) continue;
			if(puzzle[currentRow][i].getValue() == value) return false;
		}

		return true;
	}


/**
 * Method to check the current column for value conflicts.
 * @param puzzle
 * @param value
 * @param currentRow
 * @param currentCol
 * @return
 */
	public static boolean checkCol(Cell[][] puzzle, int value, int currentRow, int currentCol){
		for(int i=0; i<9; i++){
			if(i==currentRow) continue;
			if(puzzle[i][currentCol].getValue() == value) return false;
		}

		return true;
	}
	
	/**
	 * method to check the whole block based on the mod of the current row and current col.
	 * @param puzzle
	 * @param value
	 * @param currentRow
	 * @param currentCol
	 * @return
	 */
	public static boolean checkBlock(Cell[][] puzzle, int value, int currentRow, int currentCol){
		int row = currentRow - (currentRow%3);
		int col = currentCol - (currentCol%3);
		
		for(int i=row; i<(row+3); i++){
			for(int j=col; j<(col+3); j++){
				if(i==currentRow && j==currentCol) continue;
				if(puzzle[i][j].getValue()==value) return false;
			}
		}
		
		return true;
	}

	/**
	 * Method to set the starter flag on the non-zero values so the algorithm skips them
	 * @param puzzle
	 */
	public static void flagStarters(Cell[][] puzzle){
		for(Cell[] row : puzzle){
			for(Cell cell : row){
				if(cell.getValue() != 0) cell.setStarter(true);
			}
		}
	}

}

