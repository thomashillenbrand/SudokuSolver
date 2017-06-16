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
		int rows = 0;
		int cols = 0;
		/* --------------- Solving Algorithm ----------------- */
		while(rows<10 && cols<10){
			
			
		}
		
		/* --------------------------------------------------- */

		long endTime = System.currentTimeMillis();
		System.out.println("Total time to solve: "+(endTime-beginTime)/1000.0+" seconds");
		return puzzle;
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

