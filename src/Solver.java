import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {

	private Cell[][] puzzle;

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {

		File board = new File("INSERT FILE PATH HERE");
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
	 *  Method to print out the loaded sudoku puzzle
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
		while(isNotSolved(this.puzzle, row, col)){
			row = position.getRow();
			col = position.getCol();

			if(!this.puzzle[row][col].isStarter()){

				//check if the value is greater than 9, if so, reset to zero and move back a space.
				if(this.puzzle[row][col].getValue()+1 <= 9){
					this.puzzle[row][col].setValue(this.puzzle[row][col].getValue()+1);

					//check row and column --> if good continue to next cell, else increment value by one.
					if(check(this.puzzle, this.puzzle[row][col].getValue(), row, col)){
						position.moveForward();

					} // if there is an issue with checking the row or column, continue and increase the value one more time.

				}else{//move back a space
					this.puzzle[row][col].setValue(0);
					do{
						position.moveBackward();
						
					}while(this.puzzle[position.getRow()][position.getCol()].isStarter());

				}
				
			}else{//if the cell is a starter cell, increment one more space on the board
				position.moveForward();

			}

		}//end while loop

		/* --------------------------------------------------- */

		long endTime = System.currentTimeMillis();
		System.out.println("Total time to solve: "+(endTime-beginTime)+" ms");

	}

	/**
	 * Method to determine if the loaded puzzle is solved yet.
	 *
	 * @param puzzle
	 * @param row
	 * @param col
	 * @return boolean indicating whether the loaded puzzle is sovled.
	 */
	public boolean isNotSolved(Cell[][]puzzle, int row, int col){

		return row<8 || col<8 || !checkRow(puzzle, puzzle[row][col].getValue(), row, col);

	}


	/**
	 * Method to carry out all three basic validity checks of a puzzle cell
	 * once a value is placed there.
	 *
	 * @param puzzle
	 * @param value
	 * @param currentRow
	 * @param currentCol
	 * @return boolean value indicating whether this value is valid in this cell.
	 */
	private boolean check(Cell[][] puzzle, int value, int currentRow, int currentCol){

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
 * @return boolean value indicating if there are any conflicts.
 */
	private boolean checkRow(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
 * @return boolean value indicating if there are any conflicts.
 */
	private boolean checkCol(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
	 * @return boolean value indicating if there are any conflicts.
	 */
	private boolean checkBlock(Cell[][] puzzle, int value, int currentRow, int currentCol){
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
	private void flagStarters(Cell[][] puzzle){
		for(Cell[] row : puzzle){
			for(Cell cell : row){
				if(cell.getValue() != 0) cell.setStarter(true);
			}
		}
	}

}

