
public class Tracker {
	
	private int row;
	private int col;
	
	public Tracker(){
		int row = 0;
		int col = 0;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public void setRow(int row){
		this.row=row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void setCol(int col){
		this.col = col;
	}
	
	public void moveForward(){
		if(this.getCol()+1 <= 8){
			this.setCol(this.getCol()+1);
		}else{
			this.setCol(0);
			this.setRow(this.getRow()+1);
		}
	}
	
	public void moveBackward(){
		if(this.getCol()-1 >= 0){
			this.setCol(this.getCol()-1);
		}else{
			this.setCol(8);
			this.setRow(this.getRow()-1);
		}
	}

}
