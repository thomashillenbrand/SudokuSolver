
public class Cell {
	private int value;
	private boolean isStarter;
	
	public Cell(int value){
		this.setValue(value);
	}
	

	public boolean isStarter() {
		return isStarter;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	public void printCell(){
		System.out.print("  "+this.value+"  ");
	}
}
