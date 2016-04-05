package P2;


import java.util.ArrayList;
import java.util.List;

public class MinSignatureMatrix {
	private int[][] minMatrix;
	int cols;
	int rows;
	
	public int[][] getMatrix() {
		return minMatrix;
	}	
	public void setInitialValue(int rows, int cols) {
		this.cols = cols;
		this.rows = rows;	
		this.minMatrix = new int[rows][cols];		
		for(int i=0; i<minMatrix.length; i++) { // iterate through rows
			for(int j=0; j<minMatrix[i].length; j++) { // iterate through columns
				minMatrix[i][j] = Integer.MAX_VALUE;
			}
		}
	}
	
	public int getRows(){
		return rows;
	}
	public int getCols(){
		return cols;
	}
	
	public void setMatrix(int[][] matrix) {
		this.minMatrix = matrix;

	}
	
	public int getMatrixValue(int row, int col) {
		return minMatrix[row][col];
	}
	
	public void setMatrixValue(int row, int col, int value) {
		minMatrix[row][col] = value;
	}
	
	
	public List<Integer> getColumnBand(int fromRow, int toRow, int colNum) {
		List<Integer> columnBand = new ArrayList<Integer>();
		if(fromRow >= 0 && toRow >= 0 && fromRow < toRow) {
			for(int i = fromRow; i <= toRow; i++) {
				columnBand.add(getMatrixValue(i, colNum));
			}
		}
		return columnBand;
	}
	
}








