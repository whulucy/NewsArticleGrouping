package P2;
import java.util.List;
public class LSHFilter {
	private MinSignatureMatrix minMatrix;
	private int bBands;
	private int rRows;
	
	public LSHFilter(MinSignatureMatrix minMatrix) {
		this.minMatrix = minMatrix;
		int numRowsMinMatrix = minMatrix.getRows();
		int[] rowAndBand = adjustRandB(2, numRowsMinMatrix/2, numRowsMinMatrix);
		this.rRows = rowAndBand[0];
		this.bBands = rowAndBand[1];
	}
	public int[] adjustRandB(int r, int b, int numRowsSigMatrix) {
		int[] rowAndBand = new int[2];
		int newR = r;
		int newB = b;
		double point5Sim = probAtLeastOneBandIdentical(0.4, newR, newB);
		double point7Sim = probAtLeastOneBandIdentical(0.7, newR, newB);
		int iterationCt = 0;
		while(Math.abs(point7Sim - point5Sim) < 0.3 && iterationCt < 10) {
			Debug.print("r, B: " + newR + ", " + newB);
			Debug.print("point7Sim, point5Sim: " + point7Sim + ", " + point5Sim);
			Debug.print("Math.abs(point7Sim - point5Sim): " + Math.abs(point7Sim - point5Sim));
			newR++;
			newB = numRowsSigMatrix/newR;
			iterationCt++;
			point5Sim = probAtLeastOneBandIdentical(0.5, newR, newB);
			point7Sim = probAtLeastOneBandIdentical(0.7, newR, newB);
		}
		Debug.print("r, B: " + newR + ", " + newB);
		Debug.print("point7Sim, point5Sim: " + point7Sim + ", " + point5Sim);
		Debug.print("Math.abs(point7Sim - point5Sim): " + Math.abs(point7Sim - point5Sim));
		rowAndBand[0] = newR;
		rowAndBand[1] = newB;
		return rowAndBand;
	}
	public double probAtLeastOneBandIdentical(double s, int r, int b) {
		return 1-Math.pow((1-Math.pow(s, r)),b);
	}
	public MinSignatureMatrix getSignatureMatrix() {
		return minMatrix;
	}
	public void setSignatureMatrix(MinSignatureMatrix minMatrix) {
		this.minMatrix = minMatrix;
	}
	public int getbBands() {
		return bBands;
	}
	public void setbBands(int bBands) {
		this.bBands = bBands;
	}
	public int getrRows() {
		return rRows;
	}
	public void setrRows(int rRows) {
		this.rRows = rRows;
	}
	public String toString() {
		return "LSH Filter: \nNumber of Bands b = " + bBands 
				+ "\nNumber of Rows r = " + rRows;
	}
	public int hashColumnToBucket(List<Integer> columnToHash) {
		String concat = "";
		for(Integer value:columnToHash) {
			concat += value + "/";
		}
		return concat.hashCode();
	}
}