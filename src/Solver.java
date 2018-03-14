import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {

	private Cell[][] puzzle;

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {

		File board = new File("INSERT FILE NAME HERE");
		Solver solver = new Solver();

		solver.loadBoard(board);
		solver.printPuzzle();

		solver.solve();
		solver.printPuzzle();

	}

	/**
	 * Default constructor
	 */
	public Solver(){

	}

	/**
	 *  Method to print out the loaded sudoku board
	 */
	public void printPuzzle(){
		for(Cell[] row : this.puzzle){
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
	public void loadBoard(File sudokuFile) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(sudokuFile);
		this.puzzle = new Cell[9][9];
		int j=0; //columns
		int i=0; //rows
		while (fileScanner.hasNextLine()){
			j=0;
			String line = fileScanner.nextLine();
			String[] splitLine = line.split(",");
			for(String val : splitLine){
				this.puzzle[i][j] = new Cell(Integer.parseInt(val));
				j++;
			}
			i++;

		}

	}

	/**
	 * method to solve the sudoku puzzle thru brute force algorithm
	 *
	 * @throws InterruptedException 
	 */
	public void solve() {
		long beginTime = System.currentTimeMillis();
		flagStarters(this.puzzle);
		Tracker position = new Tracker();
		int row=0;
		int col=0;
		/* --------------- Solving Algorithm ----------------- */
		while(row<8 || col<8 || !checkRow(this.puzzle, this.puzzle[row][col].getValue(), row, col)){
			row = position.getRow();
			col = position.getCol();

			//check if current cell is a starting value.
			if(!this.puzzle[row][col].isStarter()){
				//check if the value is greater than 9, if so, reset to zero and move back a space.
				if(this.puzzle[row][col].getValue()+1 <= 9){
					this.puzzle[row][col].setValue(this.puzzle[row][col].getValue()+1);
					//check row and column --> if good continue to next cell, else increment value by one.
					if(check(this.puzzle, this.puzzle[row][col].getValue(), row, col)){
						//increment one space on the board
						position.moveForward();
						//continue;

					}//else continue; // if there is an issue with checking the row or column, continue and increase the value one more time.

				}else{//move back a space
					this.puzzle[row][col].setValue(0);
					do{
						position.moveBackward();
						
					}while(this.puzzle[position.getRow()][position.getCol()].isStarter());
					//continue;
					
				}
				
			}else{//if the cell is a starter cell, increment one more space on the board
				position.moveForward();
				//continue;
			}
			

		}//end while loop

		/* --------------------------------------------------- */

		long endTime = System.currentTimeMillis();
		System.out.println("Total time to solve: "+(endTime-beginTime)+" ms");

	}//end solve method

	public boolean check(Cell[][] puzzle, int value, int currentRow, int currentCol){

		boolean check = checkRow(puzzle, value, currentRow, currentCol) &&
										checkCol(puzzle, value, currentRow, currentCol) &&
										checkBlock(puzzle, value, currentRow, currentCol);

		return check;
	}

/**
 * Method to check the row of a cell for conflicting values
 * @param puzzle
 * @param value
 * @param currentRow
 * @param currentCol
 * @return
 */
	public boolean checkRow(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
	public boolean checkCol(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
	public boolean checkBlock(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
	public void flagStarters(Cell[][] puzzle){
		for(Cell[] row : puzzle){
			for(Cell cell : row){
				if(cell.getValue() != 0) cell.setStarter(true);
			}
		}
	}

}

